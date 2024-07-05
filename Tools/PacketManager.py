#coding: utf-8
import os
import zlib
from ctypes import c_int32
from struct import *

class ByteArray:
    def __init__(self, packet = b""):
        if isinstance(packet, str): 
            packet = packet.encode()
        
        self._bytes = packet

    def __repr__(self):
        return self.__str__()
        
    def __str__(self):
        return self._bytes.decode("latin-1")
        
    def __len__(self):
        return self.getLength()


    # ByteArray Operations
    def clearBuffer(self) -> None:
        self._bytes = b""
        return
    
    def __readBytes(self, length = 1):
        found = ""
        if self.getLength() >= length:
            found = self._bytes[:length]
            self._bytes = self._bytes[length:]
        return found
    
    def write(self, value):
        if isinstance(value, str):
            value = value.encode()
            
        if isinstance(value, int):
            value = chr(value).encode()
            
        self._bytes += value
        return self

    def bytesAvailable(self):
        return self.getLength() > 0
        
    def copy(self):
        return ByteArray(self._bytes)
        
    def getBytes(self):
        return self._bytes

    def getLength(self):
        return len(self._bytes)

    def toByteArray(self):
        return self.getBytes()
        

    # Read
    def readBoolean(self) -> bool:
        value = self.readByte
        return self.readByte() > 0
    
    def readByte(self) -> int:
        value = 0
        if self.getLength() >= 1:
            value = unpack("!B", self.__readBytes(1))[0]
        return value
        
    def readBytes(self, write, _from, to):
        write.writeBytes(self.__readBytes(to+_from)[_from:])
        return write
        
    def readFloat(self) -> float:
        value = 0.0
        if self.getLength() >= 4: 
            value = unpack("!f", self.__readBytes(4))[0]
        return value
        
    def readDouble(self) -> float:
        value = 0.0
        if self.getLength() >= 8: 
            value = unpack("!d", self.__readBytes(8))[0]
        return value
        
    def readShort(self) -> int:
        value = 0
        if self.getLength() >= 2:
            value = unpack("!H", self.__readBytes(2))[0]
        return value

    def readInt(self) -> int:
        value = 0
        if self.getLength() >= 4:
            value = unpack("!I", self.__readBytes(4))[0]
        return value

    def readUnsignedByte(self):
        value = self.readByte()
        if value < 0:
            return 0
    
        return value
   
    def readUnsignedInt(self: int):
        value = self.readInt()
        if value < 0:
            return 0
    
        return value
    
    def readUnsignedShort(self: int):
        value = self.readShort()
        if value < 0:
            return 0
    
        return value
   
    def readUTF(self) -> str:
        value = ""
        if self.getLength() >= 2:
            value = self.__readBytes(self.readShort())
            if isinstance(value, bytes):
                try:
                    value = value.decode()
                except:
                    value = value.decode(encoding = 'unicode_escape')
        return value
    
    def readUTFBytes(self, size):
        value = self._bytes[:int(size)]
        self._bytes = self._bytes[int(size):]
        return value

    def readInt128(self):
        local1 = 0
        local3 = 0
        local4 = -1

        while True:
            local2 = self.readByte()
            local1 = (local1 | ((local2 & 127) << (local3 * 7)))
            local4 = (local4 << 7)
            local3+=1

            if not (((local2 & 128) == 128) and (local3 < 5)):
                break

        if ((local4 >> 1) & local1) != 0:
            local1 = (local1 | local4)

        return local1


    # Write
    def writeBoolean(self, value: bool):
        if value < 0 or value > 2:
            return
        return self.writeByte(int(value))
    
    def writeByte(self, value: int):
        if value == None:
            value = 0
            
        value = int(value)
        if value > 255:
            value = 255

        self.write(pack("!b" if value < 0 else "!B", value))
        return self

    def writeBytes(self, value):
        if isinstance(value, str):
            value = value.encode()
            
        if isinstance(value, list):
            for i in value: self.writeByte(i)
            return self
            
        self._bytes += value
        return self

    def writeFloat(self, value: float):
        if value == None:
            value = 0.0
    
        value = float(value)
        self.write(pack("!f", value))
        return self

    def writeDouble(self, value: float):
        if value == None:
            value = 0.0
    
        value = float(value)
    
        self.write(pack("!d", value))
        return self

    def writeInt(self, value: int):
        if value == None:
            value = 0
        value = int(value)
    
        self.write(pack("!i" if value < 0 else "!I", value))
        return self

    def writeShort(self, value: int):
        if value == None:
            value = 0
            
        value = int(value)
        if value > 65535:
            value = 65535
            
        if value < -32768:
            value = -32768

        self.write(pack("!h" if value < 0 else "!H", value))
        return self

    def writeUnsignedByte(self, value: int):
        if value < 0:
            value = 0
        return self.writeByte(value)
            
    def writeUnsignedInt(self, value: int):
        if value < 0:
            value = 0
        return self.writeInt(value)
            
    def writeUnsignedShort(self, value: int):
        if value < 0:
            value = 0
        return self.writeShort(value)

    def writeUTF(self, value : str):
        if isinstance(value, int): 
            value = str(value).encode()
            
        if isinstance(value, str): 
            value = value.encode()
            
        self.writeShort(len(value))
        self.write(value)
        return self
        
    def writeUTFBytes(self, string):
       _len = zlib.compress(string.encode())
       self.writeInt(len(_len))
       self.write(_len)
       return self
              
    def writeInt128(self, arg_1):
        if arg_1 < 0:
            self.writeByte(arg_1)
            self.writeByte(0)
            return self
    
        arr = []
        while True:
            byte = arg_1 & 0x7F
            arg_1 >>= 7
            
            if arg_1 != 0:
                byte |= 0x80
            arr.append(byte)
            
            if not arg_1:
                break

        # Special tfm cases
        if arr[-1] >= 64 and arr[-1] < 128:
            arr[-1] += 128
            arr.append(0)
            
        for info in arr:
            self.writeByte(info)
        return self
        
    # Decryption
    def compute_keys(self, keys, s):
        s_len = len(s)
        buf = []
        _hash = 5381
        if not isinstance(s, bytes): 
            s = s.encode()
            
        for i in range(20): 
            _hash = (_hash << 5) + _hash + (keys[i] + s[(i % s_len)])
        else:
            for i in range(20):
                _hash ^= c_int32(_hash).value << 13
                _hash ^= c_int32(_hash).value >> 17
                _hash ^= c_int32(_hash).value << 5
                buf.append(c_int32(_hash).value)
            else:
                return buf
    
    def decode_chunks(self, v, n, keys):
        DELTA = 0X9E3779B9
        rounds = 6 + 52 // n
        _sum = rounds*DELTA
        y = v[0]
        for i in range(rounds):
            e = (_sum >> 2) & 3
            for p in range(n - 1, -1, -1):
                z = v[p-1]
                y = v[p] = (v[p] - (((z >> 5 ^ y << 2) + (y >> 3 ^ z << 4)) ^ ((_sum ^ y) + (keys[(p & 3) ^ e] ^ z)))) & 0xffffffff
            _sum = (_sum - DELTA) & 0xffffffff
        return v
    
    def decryptIdentification(self, keys, key="identification"):
        if self.getLength() < 10:
            raise Exception("Invalid packet length")
        barray = ByteArray()
        chunks = []
        for i in range(self.readShort()):
            chunks.append(self.readInt())
        keys = self.compute_keys(keys, key)
        chunks = self.decode_chunks(chunks, len(chunks), keys)
        for chunk in chunks:
            barray.writeInt(chunk)
        self._bytes = barray._bytes
        return self

    def decrypt(self, keys, packetID = None):
        if packetID == None:
            return self.decryptIdentification(keys)
            
        packetID += 1
        keys = self.compute_keys(keys, "msg")    
        self._bytes = bytes(bytearray([(byte^keys[(packetID+i)%20])&0xff for i, byte in enumerate(self._bytes)]))
        return self

packet = ByteArray(b'\x05X\x14\x01\x03191;251_61291a+ff8b39+ff8b39+9fd9ae+fff6b3+ffa463+7fb49c+e5e688+ff8b39,9_ffc038+ff8b39+0+1d130e,0,0,0,91_7a3c2b+9fd9ae+e5e688+ff9238+9fd9ae,68_d28e52+fbefd9+662a19+d28e52+cf9046,12,71_9fd9ae+61291a+e5e688+e5e688+d8a654+241306+c75d40+f39f44+dfeb9a+7a3c2b,0,0,0\x08\xff\xff\xff\xff\x02\x00\x05\x00\x00\x03\x00\x00\x00\x00\x00\x00\x03g\x01\x00x\x00`\x01\x00\x14\x00\x10\x00\x00\x02\xc8\x01\x00\x1e\x00\x18\x03\x00\x00\x00\x00\x00\x00(\x0b\x01\x007\x00,\x02\x00\x14\x00\x00\x00\x00\x02\x9c\x01\x00x\x00`\x01\x00\x14\x00\x10\x00\x03\x83/\x01\x01\x90\x01@\x03\x00\x00\x00\x00\x00\x00\x00m\x01\x00\x05\x00\x04\x01\x00\x14\x00\x10\x00\x00\x02O\x01\x00A\x004\x01\x00\x14\x00\x10\x03\x84\x02\xbc')
packet_copy = packet

while True:
    x = input()
    if x == "byte":
        print(packet.readByte())
    elif x == "ubyte":
        print(packet.readUnsignedByte())
    elif x == "short":
        print(packet.readShort())
    elif x == "ushort":
        print(packet.readUnsignedShort())
    elif x == "int":
        print(packet.readInt())
    elif x == "uint":
        print(packet.readUnsignedInt())
    elif x == "utf":
        print(packet.readUTF())
    elif x == "result":
        print(str(packet._bytes))
    elif x == "clear":
        os.system("cls")
    elif x == "restart":
        packet = packet_copy
        os.system("cls")




# b'\x05W\x14\x00\xea5;277_573610+636d3f+965a4b+d9a162+c38687+e8ceaf+6d371a+98664b+c38687+d1891c,0,0,0,83_c38687+c38687+838344+e1b066+a97b4f,76_392005+4d2e09,36_838344+8e5547+603f17+5f5e31+d99125+a66400+cbab67+e8ceaf+ffff95,0,31_965a4b+e8ceaf+e1b066,0,0,0\x07\xff\xff\xff\xff\x02\x00\x05\x00\x00\x03\x00\x00\x00\x00\x00\x00\x02@\x01\x00<\x000\x02\x00\x14\x00\x00\x00\x00\x01\xe3\x01\x00K\x00<\x01\x00\x14\x00\x10\x00\x00(%\x01\x00P\x00@\x01\x00\x14\x00\x10\x00\x00\x02|\x01\x00x\x00`\x01\x00\x14\x00\x10\x00\x00\x08\x9d\x01\x01,\x00\xf0\x03\x00\x00\x00\x00\x00\x00\x03?\x01\x00\xc8\x00\xa0\x02\x00\x14\x00\x00\x03\xac\x02\xcc'