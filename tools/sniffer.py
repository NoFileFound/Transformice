import asyncio
import io
import pak
from aioconsole import aprint

class PacketLength(pak.Type):
    _default = 0
    _uleb128 = pak.ULEB128.Limited(max_bytes=5)

    @classmethod
    def _unpack(cls, buf, *, ctx):
        return pak.util.to_signed(cls._uleb128.unpack(buf, ctx=ctx), bits=32)

    @classmethod
    async def _unpack_async(cls, reader, *, ctx):
        value = await cls._uleb128.unpack_async(reader, ctx=ctx)
        return pak.util.to_signed(value, bits=32)

    @classmethod
    def _pack(cls, value, *, ctx):
        return cls._uleb128.pack(pak.util.to_unsigned(value, bits=32), ctx=ctx)

Byte = pak.UInt8.big_endian()
UnsignedByte = pak.UInt8.big_endian()
Short = pak.Int16.big_endian()
UnsignedShort = pak.UInt16.big_endian()
Int = pak.Int32.big_endian()
String = pak.PrefixedString(UnsignedShort)

class MainServerInfoPacket(pak.Packet):
    address: String
    ports: UnsignedShort[None]

class HandshakePacket(pak.Packet):
    game_version: Short
    language: String
    connection_token: String
    player_type: String
    browser_info: String
    loader_stage_size: Int
    ccf_data: String
    concatenated_font_name_hash: String
    server_string: String
    referrer: Int
    milliseconds_since_start: Int
    game_name: String

async def forward_clientbound(client_writer, server_reader):
    try:
        while not client_writer.is_closing():
            length = await PacketLength.unpack_async(server_reader)
            data = await server_reader.readexactly(length)
            C, CC = UnsignedByte[2].unpack(data)
            await aprint("Send:", (C, CC), data[2:])
            client_writer.write(PacketLength.pack(length) + data)
            await client_writer.drain()
    except (asyncio.IncompleteReadError, ConnectionError):
        pass

async def new_main_connection(client_reader, client_writer):
    server_reader = None
    server_writer = None
    forward_task = None

    try:
        while not client_writer.is_closing():
            length = await PacketLength.unpack_async(client_reader)
            data = await client_reader.readexactly(length + 1)
            buf = io.BytesIO(data)
            fingerprint = Byte.unpack(buf)
            C, CC = UnsignedByte[2].unpack(buf)
            await aprint("Recv:", (C, CC), data[3:])
            if C == 255 and CC == 255:
                extension_id = String.unpack(buf)

                if extension_id == "main_server_info":
                    info = MainServerInfoPacket.unpack(buf)

                    if not info.ports:
                        raise ValueError("No server ports provided")

                    if forward_task:
                        forward_task.cancel()
                        try:
                            await forward_task
                        except asyncio.CancelledError:
                            pass

                    if server_writer:
                        server_writer.close()
                        await server_writer.wait_closed()

                    server_reader, server_writer = await asyncio.open_connection(
                        info.address,
                        info.ports[0]
                    )

                    forward_task = asyncio.create_task(
                        forward_clientbound(client_writer, server_reader)
                    )

                continue

            if server_writer is None:
                continue

            if C == 28 and CC == 1:
                handshake = HandshakePacket.unpack(buf)
                handshake.loader_stage_size = 0x1FBD
                data = (Byte.pack(fingerprint) +UnsignedByte[2].pack([C, CC]) +handshake.pack())
                length = len(data) - 1

            server_writer.write(PacketLength.pack(length) + data)
            await server_writer.drain()

    except (asyncio.IncompleteReadError, ConnectionError):
        pass

    finally:
        if forward_task:
            forward_task.cancel()
            try:
                await forward_task
            except asyncio.CancelledError:
                pass

        if server_writer:
            server_writer.close()
            await server_writer.wait_closed()

        client_writer.close()
        await client_writer.wait_closed()

async def proxy():
    server = await asyncio.start_server(new_main_connection, "0.0.0.0", 11801)
    try:
        await server.serve_forever()
    finally:
        server.close()
        await server.wait_closed()

if __name__ == "__main__":
    try:
        asyncio.run(proxy())
    except KeyboardInterrupt:
        print("Proxy stopped")