import zlib
import sys
from pathlib import Path

def convert(input_file):
    input_file = Path(input_file)
    output_file = input_file.with_name(input_file.stem + "_uncompressed.swf")
    data = input_file.read_bytes()
    if data[:3] != b"CWS":
        raise ValueError("invalid swf")

    version = data[3]
    length = int.from_bytes(data[4:8], "little")
    body = zlib.decompress(data[8:])
    if len(body) != length - 8:
        raise ValueError("Invalid swf length")

    output = b"FWS" + bytes([version]) + data[4:8] + body
    output_file.write_bytes(output)
    print("OK")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: cws2fws.py <swf_path>")
        sys.exit(1)

    convert(sys.argv[1])