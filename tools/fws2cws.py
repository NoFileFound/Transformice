import zlib
import sys
from pathlib import Path

def convert(input_file):
    input_file = Path(input_file)
    output_file = input_file.with_name(input_file.stem + "_compressed.swf")
    data = input_file.read_bytes()
    if data[:3] != b"FWS":
        raise ValueError("invalid swf")

    version = data[3]
    length = int.from_bytes(data[4:8], "little")
    if len(data) != length:
        raise ValueError("invalid swf length")

    output = b"CWS" + bytes([version]) + data[4:8] + zlib.compress(data[8:])
    output_file.write_bytes(output)
    print("OK")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: fws2cws.py <swf_path>")
        sys.exit(1)

    convert(sys.argv[1])