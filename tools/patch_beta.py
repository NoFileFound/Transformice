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
    
def Findcleftext():
    find = 'pushstring          "$Clef_Beta"'
    for file in files:
        lines = ReadAllLines(file)
        x = 0
        while x < len(lines):
            if find in lines[x]:
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

def makeitwork(a):
    path = GetPathByName(a[0])
    lines = ReadAllLines(GetPathByName(a[0]))
    x = 0
    find = a[1]
    while x < len(lines):
        if find in lines[x]:
            a = 0
            find = ['iffalse', 'iftrue ']
            while 40 > a:
                if find[0] in lines[x-a]:
                    lines[x-a] = lines[x-a].replace(find[0], find[1])
                    print("[#] Clef_Beta enabled!")
                    WriteAllLines(path, lines)
                    return
                a +=1
            print("[#] Clef_Beta aleardy enabled!")
            return            
        x+=1

if __name__ == "__main__":
    P11 = False
    P22 = False
    files = GetAllAsasm()
    P1 = Findcleftext()
    if P1:
        P2 = FindFile(P1)
        if P2:
            makeitwork(P2)