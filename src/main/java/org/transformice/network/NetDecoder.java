package org.transformice.network;

// Imports
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.psframework.network.PSReceivePacketException;

public final class NetDecoder extends ByteToMessageDecoder {
    private static final byte[] POLICY_REQUEST = "<policy-file-request/>".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
    private static final String POLICY_RESPONSE = "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>\0";
    private static final int MAX_PACKET_LENGTH = 16 * 1024 * 1024; // 16 MB

    /**
     * Decodes incoming network data into packets.
     * @param context The channel handler context.
     * @param buffer The buffer containing the incoming data.
     * @param output The list to which decoded packets are added.
     */
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf buffer, List<Object> output) {
        if (buffer.readableBytes() < 2)
            return;

        // Policy file request.
        if (buffer.readableBytes() < POLICY_REQUEST.length + 1) {
            int readable = buffer.readableBytes();
            boolean matches = true;
            for (int i = 0; i < readable; i++) {
                if (buffer.getByte(buffer.readerIndex() + i) != POLICY_REQUEST[i]) {
                    matches = false;
                    break;
                }
            }

            if (matches)
                return;
        }

        if (buffer.readableBytes() >= POLICY_REQUEST.length + 1) {
            buffer.markReaderIndex();
            boolean isPolicyRequest = true;
            for (byte b : POLICY_REQUEST) {
                if (buffer.readByte() != b) {
                    isPolicyRequest = false;
                    break;
                }
            }

            if (isPolicyRequest && buffer.readByte() == 0) {
                context.writeAndFlush(Unpooled.copiedBuffer(POLICY_RESPONSE, StandardCharsets.UTF_8)).addListener(ChannelFutureListener.CLOSE);
                return;
            }

            buffer.resetReaderIndex();
        }

        // Decode the VLQ packet length.
        buffer.markReaderIndex();
        int length = 0;
        int shift = 0;
        while (true) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }

            int b = buffer.readUnsignedByte();
            if (shift == 28 && (b & 0xF0) != 0)
                throw new PSReceivePacketException("VLQ length field overflow");

            length |= (b & 0x7F) << shift;
            if ((b & 0x80) == 0)
                break;

            shift += 7;
            if (shift > 28)
                throw new PSReceivePacketException("VLQ length field too long");
        }

        if (length < 0 || length > MAX_PACKET_LENGTH)
            throw new PSReceivePacketException("Invalid packet length: " + length);

        if (buffer.readableBytes() < length + 1) {
            buffer.resetReaderIndex();
            return;
        }

        byte[] data = new byte[length + 1];
        buffer.readBytes(data);
        output.add(new NetByteArray(data));
    }
}