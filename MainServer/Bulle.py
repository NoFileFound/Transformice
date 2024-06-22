# amke it as client handler
import asyncio
from Utils import Config

class Bulle:
    def __init__(self, _server, _cursor):
        self.server = _server
        self.verifed = False
        self.settings = Config.Json().load_file("./Include/Server/server.json")
        self.transport = None
        
    def connection_made(self, transport: asyncio.Transport) -> None:
        self.transport = transport
        self.keep_alive()
        
    def keep_alive(self):
        self.transport.write(b'1000;')
        
    def send_packet(self, code, *args):
        d = []
        for i in list(args):
            d.append(str(i))
        self.transport.write(str(code).encode() + '|'.join(d).encode() + b';')
        
    def data_received(self, packet: bytes) -> None:
        if not self.verifed:
            self.verifed = (packet == self.settings['bulle_auth_code'].encode() + b';')
            if not self.verifed: return self.transport.close()
            self.server.appendBulle(self)
            return
        code = packet[0]
        data = str(packet[1:]).split('|')
        if code == 0:
            self.keep_alive()
        
    def eof_received(self) -> None:
        pass
    
    def connection_lost(self, *args) -> None:
        pass