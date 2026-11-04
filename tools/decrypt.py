import os
import re
import shutil
import json
import subprocess

info = {}

def decompress_swf(swf_path):
    result = subprocess.run(["swfdump.exe", "-a", swf_path], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, encoding="utf-8", errors="replace")
    dump = result.stdout
    lines = dump.splitlines()
    match = re.search(
        r'findproperty\s+<q>\[(?:private|public)\](?:NULL)?::([^\r\n]+)'
        r'(?:(?!findproperty).)*?pushstring\s+"([^"]*)"', dump, re.DOTALL
    )

    if not match:
        raise ValueError("Alphabet not found")

    safe_str_name, safe_str = match.groups()
    info["_safestr"] = safe_str_name
    info["alphabet"] = safe_str
    function_values = {}
    function_regex = re.compile(
        r'method\s+<q>\[public\]::Object\s+' 
        r'<q>\[private\]NULL::(.*?)=\(\)\(0 params, 0 optional\)'
    )
    
    value_regex = re.compile(r'\bpush(?:byte|short|int)\s+(\d+)\b')
    for i, line in enumerate(lines):
        match = function_regex.search(line)
        if not match:
            continue

        function_name = match.group(1)
        k = None
        if i + 6 < len(lines):
            value_match = value_regex.search(lines[i + 6])
            if value_match:
                k = int(value_match.group(1))

        if k is None:
            for j in range(i + 1, min(i + 12, len(lines))):
                value_match = value_regex.search(lines[j])
                if value_match:
                    k = int(value_match.group(1))
                    break

        if k is not None and 0 <= k < len(safe_str):
            function_values[function_name] = safe_str[k]

    if not function_values:
        raise ValueError("No obfuscated functions were resolved")

    info["functions"] = function_values
    actual_script = []
    function_call_regex = re.compile(
        r'callproperty\s+<q>\[(?:private|public)\]'
        r'(?:NULL)?::(.*?),\s*0 params'
    )

    for i, line in enumerate(lines[:-1]):
        if "getlocal_0" not in line:
            continue

        match = function_call_regex.search(lines[i + 1])
        if match:
            value = function_values.get(match.group(1))
            if value is not None:
                actual_script.append(value)

    actual_script = "".join(actual_script)
    if not actual_script:
        raise ValueError("Reconstructed script is empty")

    binary_names = {
        match.group(2).rsplit("_", 1)[1]: int(match.group(1))
        for match in re.finditer(r'\bexports\s+(\d+)\s+as\s+"([^"]+)"', dump)
        if "_" in match.group(2)
    }

    print("[#] Exported binaries:", len(binary_names))
    info["binary_names"] = binary_names
    binary_order = [
        binary_names[match.group(1)]
        for match in re.finditer(r'__writeBytes([^_]+)', actual_script)
        if match.group(1) in binary_names
    ]

    print("[#] SWF order:", binary_order)
    info["binary_order"] = binary_order
    if len(binary_order) != len(binary_names):
        raise ValueError("Expected {} binaries, found {}".format(len(binary_names), len(binary_order)))

    result = subprocess.run(["swfbinexport.exe", swf_path], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, errors="replace")
    if result.returncode:
        raise RuntimeError("swfbinexport.exe failed:\n" + result.stderr)

    swf_name = os.path.splitext(os.path.basename(swf_path))[0]
    binary_files = [
        "{}-{}.bin".format(swf_name, binary_id)
        for binary_id in binary_order
    ]
    
    for binary_name in binary_files:
        if not os.path.isfile(binary_name):
            raise FileNotFoundError("Missing exported binary: " + binary_name)

    binary_sizes = {
        str(binary_id): os.path.getsize(binary_name)
        for binary_id, binary_name in zip(binary_order, binary_files)
    }

    info["binary_sizes"] = binary_sizes

    print("[#] Writing the decrypted swf.")
    output_path = swf_path + "_dec.swf"
    with open(output_path, "wb") as output:
        for binary_name in binary_files:
            with open(binary_name, "rb") as source:
                shutil.copyfileobj(source, output)
                
    for binary_name in binary_files:
        os.remove(binary_name)
        
    with open("info.json", "w") as f:
        f.write(json.dumps(info))

if __name__ == "__main__":
    import sys
    if len(sys.argv) < 2:
        print("Usage: decrypt.py <swf_path>")
        sys.exit(1)

    decompress_swf(sys.argv[1])