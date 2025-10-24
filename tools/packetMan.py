import io
import struct
import os

class ByteReader:
    def __init__(self, data: bytes):
        self.stream = io.BytesIO(data)

    def readInt(self):
        return struct.unpack(">i", self.stream.read(4))[0]

    def readByte(self):
        return struct.unpack(">b", self.stream.read(1))[0]

    def readBoolean(self):
        return struct.unpack(">?", self.stream.read(1))[0]

    def readShort(self):
        return struct.unpack(">h", self.stream.read(2))[0]

    def readUnsignedByte(self):
        return struct.unpack(">B", self.stream.read(1))[0]

    def readUnsignedShort(self):
        return struct.unpack(">H", self.stream.read(2))[0]

    def readUnsignedInt(self):
        return struct.unpack(">I", self.stream.read(4))[0]

    def readUTF(self):
        length = self.readUnsignedShort()
        return self.stream.read(length).decode("utf-8")

    def readString(self):
        return self.readUTF()

    def readInt128(self):
        result = 0
        cur = 0x80
        count = 0
        signBits = -1
        while (cur & 0x80) == 0x80 and count < 5:
            cur = self.readUnsignedByte()
            result |= (cur & 0x7F) << (count * 7)
            signBits <<= 7
            count += 1

        if ((signBits >> 1) & result) != 0:
            result |= signBits

        return result

def parse_structure(structure: str):
    tokens = []
    i = 0
    while i < len(structure):
        c = structure[i]
        if c.isspace() or c == ',':
            i += 1
            continue
        elif structure.startswith("for[", i):
            i += 4
            end = structure.index("]", i)
            counter_type = structure[i:end]
            i = end + 1
            assert structure[i] == "(", "Expected '(' after for[...]"
            i += 1
            depth = 1
            start = i
            while depth > 0 and i < len(structure):
                if structure[i] == '(':
                    depth += 1
                elif structure[i] == ')':
                    depth -= 1
                i += 1
            inner = structure[start:i-1]
            tokens.append(("for", counter_type, parse_structure(inner)))
        else:
            start = i
            while i < len(structure) and structure[i].isalpha():
                i += 1
            tokens.append(structure[start:i])
    return tokens

def process_structure(reader: ByteReader, structure, indent=0):
    for element in structure:
        if isinstance(element, tuple) and element[0] == "for":
            _, counter_type, sub = element

            counter_func_name = {
                "int": "readInt",
                "byte": "readByte",
                "short": "readShort",
                "ubyte": "readUnsignedByte",
                "ushort": "readUnsignedShort",
                "uint": "readUnsignedInt",
                "int128": "readInt128",
            }.get(counter_type)

            if not counter_func_name:
                raise ValueError(f"Invalid counter type for loop: {counter_type}")

            counter_func = getattr(reader, counter_func_name)
            count = counter_func()
            print(" " * indent + f"Loop ({counter_type}) = {count}x:")
            for i in range(count):
                print(" " * (indent + 2) + f"Iteration {i+1}:")
                process_structure(reader, sub, indent + 4)

        else:
            func_name = {
                "int": "readInt",
                "byte": "readByte",
                "bool": "readBoolean",
                "short": "readShort",
                "ubyte": "readUnsignedByte",
                "ushort": "readUnsignedShort",
                "uint": "readUnsignedInt",
                "str": "readString",
                "int128": "readInt128",
            }.get(element)

            if not func_name:
                raise ValueError(f"Unknown type: {element}")

            func = getattr(reader, func_name)
            value = func()
            print(" " * indent + f"{element}: {value}")

def showMenu():
    print("""Packet Manager Tool 1.0
    
1. Converts code to packet id.
2. Converts packet id to code.
3. Converts bytearray to structure.
    """)
    optionId = input("Option id: ")
    os.system("cls")
    
    try:
        return int(optionId)
    except:
        return showMenu()
    
def onMenuOption(optionId : int = -1):
    if optionId == 1:
        while True:
            d = int(input("-> "))
            c = d >> 8
            cc = d - (c << 8)
            print(c, cc)
    elif optionId == 2:
        while True:
            C = int(input("C: "))
            CC = int(input("CC: "))
            print(((C << 8) | (CC & 0xFF)))
    elif optionId == 3:
        print("""Available types:
        
1. int -> readInt()
2. byte -> readByte()
3. bool -> readBoolean()
4. short -> readShort()
5. ubyte -> readUnsignedByte()
6. ushort -> readUnsignedShort()
7. uint -> readUnsignedInt()
8. str -> readUTF() or readString()
9. int128 -> readInt128()

Syntax: operator1, operator2, ... operator N
Syntax (loop): for[operator1](operator2, operator 3, ... operator N), operator N+1
        """)
        structure = input("Structure -> ")
        bytecode = eval(input("ByteCode -> ").strip())
        process_structure(ByteReader(bytecode),  parse_structure(structure))
        return
    else:
       onMenuOption(showMenu())
       
if __name__ == "__main__":
    optionId = showMenu()
    onMenuOption(optionId)