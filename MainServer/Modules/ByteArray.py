#coding: utf-8
import zlib

# Modules
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
        
    def writeUTFBytes(self, __bytes):
       _len = zlib.compress(__bytes.encode())
       self.writeInt(len(_len))
       self.write(_len)
       return self
       
    def writeInt128(self, arg_1):
        local_2 = arg_1 >> 7
        local_3 = True
        local_4 = -1 if arg_1 >= 2147483648 else 0
        
        while local_3:
            local_3 = (local_2 != local_4) or ((local_2 & 1) != ((arg_1 >> 6) & 1))
            self.writeByte((arg_1 & 0x7F) | (128 if local_3 else 0))
            arg_1 = local_2
            local_2 = local_2 >> 7
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