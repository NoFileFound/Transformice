import os, glob

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
    
def WriteAllLines(path, text):
    WriteAllText(path, "\n".join(map(str, text)))
    
def WriteAllText(path, text):
    w = open(path.replace("\\x", "%"), "w+")
    w.write(text)
    w.close()
    
def FindLoginProtection():
    stringMsg = 'pushstring          "identification"'
    for file in files:
        lines = ReadAllLines(file)
        x = 0
        while x < len(lines):
            if stringMsg in lines[x]:
                return [lines[x-1].split('"')[3]]
            x+=1
    return []
    
def FindKeyProtection():
    stringMsg = 'pushstring          "msg"'
    for file in files:
        lines = ReadAllLines(file)
        x = 0
        while x < len(lines):
            if stringMsg in lines[x]:
                P11 = True
                return [lines[x-1].split('"')[3]]
            x+=1
    return []
    
def FindFile(a):
    find = 'getproperty         QName(PackageNamespace(""), "{0}")'.format(a[0])
    for file in files:
        lines = ReadAllLines(file)
        x = 0
        while x < len(lines):
            if find in lines[x]:
                P22 = True
                return [GetClassNameByFile(file), lines[x]]
            x+=1
    return []

def DesprotectLogin(a):
    path = GetPathByName(a[0])
    lines = ReadAllLines(GetPathByName(a[0]))
    x = 0
    find = ['iftrue', 'iffalse']
    while x < len(lines):
        if find[0] in lines[x]:
            lines[x] = lines[x].replace(find[0]+'  ', find[1])
        elif find[1] in lines[x]:
            lines[x] = lines[x].replace(find[1], find[0])
            WriteAllLines(path, lines)
            return           
        x+=1
    
def DesprotectPackets(a):
    path = GetPathByName(a[0])
    lines = ReadAllLines(GetPathByName(a[0]))
    x = 0
    temp = 'SpaceRemoveXD11'
    while x < len(lines):
        if a[1] in lines[x]:
            x += 5
            while not 'returnvoid' in lines[x]:
                lines[x] = temp
                x +=1
            while temp in lines: lines.remove(temp)
            WriteAllLines(path, lines)
            return
        x+=1

if __name__ == "__main__":
    P11 = False
    P22 = False
    files = GetAllAsasm()
    P1 = FindKeyProtection()
    if P1:
        P2 = FindFile(P1)
        if P2:
            P3 = FindLoginProtection()
            if P3:
                P4 = FindFile(P3)
                if P4:
                    DesprotectLogin(P4)
                    DesprotectPackets(P2)
                    print("[#] OK")