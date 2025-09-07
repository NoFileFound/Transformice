import subprocess
import re
import json

try:
    subprocess.run("git clone https://github.com/Lucaselut/swfparser.git", shell=True, check=True)
except:
    pass
    
subprocess.run("cd swfparser && python parser.py && move tfm.swf ../158.swf && cls", shell=True, check=True)
input("Please now open 158.swf in AS3 Sorcerer and dump all strings to 158_scripts.txt.\nPress key when you are ready :)")

func_start_pattern = re.compile(r"public function _SafeStr_\d+\(\):\*")
xor_pattern = re.compile(r"_local_1\s*=\s*\(_local_1\s*\^\s*(.+?)\);")
return_pattern = re.compile(r"return\s*\(_local_1\s*\^\s*(.+?)\);")
value_pattern = re.compile(r"public static function (_SafeStr_\d+)\(\):int\s*\{\s*return\s*\((.+?)\);\s*\}", re.MULTILINE)
ip_pattern = re.compile(r"\b(?:\d{1,3}\.){3}\d{1,3}:11801-12801-13801-14801\b")
value_1_pattern = re.compile(r'public static var (\w+):String\s*=\s*"1\."')

with open("158_scripts.txt", "r", encoding="utf-8") as f:
    lines = f.readlines()
    text = "".join(lines)

ip_addresses = {ip for line in lines for ip in ip_pattern.findall(line)}
if ip_addresses:
    ip_with_ports = next(iter(ip_addresses))
    ip, ports_str = ip_with_ports.split(":")
    ports = list(map(int, ports_str.split("-")))

safe_values = {}
for m in value_pattern.finditer(text):
    func_name, expr = m.groups()
    try:
        safe_values[func_name] = eval(expr)
    except Exception:
        safe_values[func_name] = 0

loginkeys = []
in_func = False
for line in lines:
    if not in_func:
        if func_start_pattern.search(line):
            in_func = True
    else:
        match = xor_pattern.search(line) or return_pattern.search(line)
        if match:
            expr = match.group(1).strip("() ")
            if "<<" in expr:
                left, right = map(str.strip, expr.split("<<"))
                left_key = left.split(".")[1].replace("()", "")
                right_key = right.split(".")[1].replace("()", "")
                loginkeys.append(safe_values.get(left_key, 0) << safe_values.get(right_key, 0))
            else:
                func_name = expr.split(".")[1].replace("()", "")
                loginkeys.append(safe_values.get(func_name, 0))

ckey_line = next((l for l in lines if "unescape(Capabilities.serverString)" in l), None)
ckey = None
if ckey_line:
    params = [p.strip() for p in ckey_line.split(',')]
    if len(params) > 3:
        ckey_param = params[3]
        ckey_param = params[3]
        if "(" in ckey_param and ")" in ckey_param:
            inside = ckey_param.split("(", 1)[1].rsplit(")", 1)[0].strip()
        else:
            inside = ckey_param.strip()
        class_name, var_name = inside.split(".")
        pattern = re.compile(rf'public\s+static\s+var\s+{re.escape(var_name)}\s*:\s*String\s*=\s*"([^"]+)"')
        for line in lines:
            m = pattern.search(line)
            if m:
                ckey = m.group(1)
                break
           
value_1_var = next((m.group(1) for line in lines if (m := value_1_pattern.search(line))), None)
if not value_1_var:
    raise ValueError("No variable with value '1.' found")

second_var = None
for line in lines:
    if "public const " in line and "=" in line and "(" in line and ")" in line:
        const_name = line.split("public const ")[1].split(":")[0].strip()
        expr = line.split("=", 1)[1].strip().lstrip("(").rstrip(")")
        parts = [p.strip() for p in expr.split("+")]
        for p in parts:
            if value_1_var in p:
                second_var = next(x for x in parts if x != p).split(".")[-1].strip()
                second_var = "".join(c for c in second_var if c.isalnum() or c == "_")
                const_var = const_name
                break
    if second_var:
        break

if not second_var:
    raise ValueError("Could not find the second variable")

second_var_pattern = re.compile(rf'public var {re.escape(second_var)}:int\s*=\s*(\d+)')
version = next((int(m.group(1)) for line in lines if (m := second_var_pattern.search(line))), None)

data = {
    "version": version,
    "ports": ports,
    "connection_key": ckey,
    "packet_keys": [],
    "login_keys": loginkeys,
    "authorization_key": 1099831313,
    "swf_url": ""
}

with open("swf.json", "w", encoding="utf-8") as f:
    json.dump(data, f, indent=2)
    
print(data)