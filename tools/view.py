import os, glob, re, random, asyncio

def GetAllAsasm():
    files = []
    for asasm in glob.glob(GetPathByName("*")):
        files += [asasm]
    return files

def GetClassNameByFile(file):
    return file.replace("\\", "/").split("/")[-1].split('.')[0].replace("%", "\\x")
    
def GetPathByName(name):
    return "./158-0/{0}.class.asasm".format(name.replace("\\x", "%"))
    
def ReadAllLines(path):
    return ReadAllText(path).split('\n')
    
def ReadAllText(path):
    r = open(path.replace("\\x", "%"), encoding='utf-8')
    text = r.read()
    r.close()
    return text

loginKeys = []
def SearchLoginKeys():
    global loginKeys
    loginKeys = []
    loginMethod = None
    for file in files:
        lines = ReadAllLines(file)
        for i, line in enumerate(lines):
            if 'param QName(PackageNamespace(""), "String")' not in line:
                continue

            if i + 4 >= len(lines):
                continue

            if 'param QName(PackageNamespace(""), "\\x' not in lines[i + 4]:
                continue

            for x in range(i + 1, len(lines)):
                if "writeInt" in lines[x] and x > 0:
                    matches = re.findall(r'"([^"]*)"', lines[x - 1])
                    if matches:
                        loginMethod = matches[-1]

                    break

            if loginMethod:
                break

        if loginMethod:
            break

    if not loginMethod:
        print("Login method not found.")
        return

    classMethods = []
    for file in files:
        lines = ReadAllLines(file)
        for i, line in enumerate(lines):
            if 'trait method QName(PackageNamespace(""), "{0}")'.format(loginMethod) not in line:
                continue

            x = i + 1
            while x < len(lines) and "end ; trait" not in lines[x]:
                if x + 3 >= len(lines):
                    break

                if "getlex" in lines[x] and "callproperty" in lines[x + 1]:
                    if "getlex" in lines[x + 2]:
                        className = re.findall(r'"([^"]*)"', lines[x + 2])
                        methodName = re.findall(r'"([^"]*)"', lines[x + 3])
                    elif "bitxor" in lines[x + 2]:
                        className = re.findall(r'"([^"]*)"', lines[x])
                        methodName = re.findall(r'"([^"]*)"', lines[x + 1])
                    else:
                        x += 1
                        continue

                    if className and methodName:
                        classMethods.append((className[-1], methodName[-1]))

                x += 1

    for className, methodName in classMethods:
        classFile = GetPathByName(className)
        try:
            lines = ReadAllLines(classFile)
        except OSError:
            continue

        for i, line in enumerate(lines):
            if 'trait method QName(PackageNamespace(""), "{0}")'.format(methodName) not in line:
                continue

            x = i + 1
            while x + 1 < len(lines) and "end ; trait" not in lines[x]:
                first = re.search(r'\bpush(?:byte|short)\s+(-?\d+)', lines[x])

                if first:
                    secondInt = re.search(r'\bpushint\s+(-?\d+)', lines[x + 1])
                    secondShort = re.search(r'\bpushshort\s+(-?\d+)', lines[x + 1])
                    if secondInt:
                        loginKeys.append(int(first.group(1)) + int(secondInt.group(1)))
                        break

                    if secondShort:
                        loginKeys.append(1 << (int(first.group(1)) + int(secondShort.group(1))))
                        break

                x += 1

    loginKeys = list(dict.fromkeys(loginKeys))

connectionKey = ""
def SearchConnectionKey():
    global connectionKey
    connectionKey = ""
    classProperties = []
    for file in files:
        lines = ReadAllLines(file)
        for i, line in enumerate(lines):
            if "SHA256_faux" not in line:
                continue

            x = i
            while x < len(lines) and "end ; code" not in lines[x]:
                if x + 2 < len(lines):
                    if "getlex" in lines[x] and "getlex" in lines[x + 1] and "getproperty" in lines[x + 2]:
                        className = lines[x + 1].split('"')[-2]
                        propertyName = lines[x + 2].split('"')[-2]
                        pair = (className, propertyName)
                        if pair not in classProperties:
                            classProperties.append(pair)

                x += 1

    for className, propertyName in classProperties:
        classFile = GetPathByName(className)
        for line in ReadAllLines(classFile):
            if "trait slot" not in line:
                continue

            if 'QName(PackageNamespace(""), "{}")'.format(propertyName) not in line:
                continue

            if 'type QName(PackageNamespace(""), "String")' not in line:
                continue

            match = re.search(r'value Utf8\("(.*?)"\)', line)
            if match:
                value = match.group(1)
                if len(value) > 2 and value not in ("Kongregate WEB", "Éxécutable AIR"):
                    connectionKey = value
                    return

ip = ""
ports = []
def SearchPorts():
    global ip, ports
    for file in files:
        for line in ReadAllLines(file):
            if 'pushstring' in line and re.search(r'\d+\.\d+\.\d+\.\d+:\d+(?:-\d+)+', line):
                value = line.split('"')[1]
                ip, portList = value.split(':')
                ports = [int(port) for port in portList.split('-')]
                return

version = 0
def SearchVersion():
    global version
    for file in files:
        lines = ReadAllLines(file)
        for index, line in enumerate(lines):
            if 'end ; code' not in lines[index - 3] and 'end ; body' in lines[index - 2]:
                if 'type QName(PackageNamespace(""), "int") value Double' in line:
                    if 'type QName(PackageNamespace(""), "int") value Double' in lines[index + 1]:
                        v = int(line[-8:-5])
                        if v != 750:
                            version = v
                            break
        else:
            continue
        break

files = GetAllAsasm()
print("Authorization key:", random.randint(1000000000, 9999999999))
SearchConnectionKey()
print("Connection key:", connectionKey)
SearchLoginKeys()
print("Login keys:", loginKeys)
SearchPorts()
print("IP:", ip)
print("Ports:", ports)
print("Packet Keys:", [])
SearchVersion()
print("Version:", version)