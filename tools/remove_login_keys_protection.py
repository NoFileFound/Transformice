import os, glob

def GetAllAsasm():
    files = []
    for asasm in glob.glob(GetPathByName("*")):
        files += [asasm]
    return glob.glob(GetPathByName("*"))
    
def GetClassNameByFile(file):
    return file.replace("\\", "/").split("/")[-1].split('.')[0].replace("%", "\\x")
    
def GetPathByName(name):
    return "./158-0/{0}.class.asasm".format(name.replace("\\x", "%"))
    
def ReadAllLines(path):
    return ReadAllText(path).split('\n')

def FindClass(lines, string, count):
    i = 0
    for t in lines:
        if string in t: i+=1
    return i == count
    
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
    
def fixwon():
    for file in files:
        lines = ReadAllText(file)
        if 'Timer' in lines and 'URLLoader' in lines and 'Error' in lines:
            lines = lines.split('\n')
            x = 0
            while x < len(lines):
                if '"Boolean") value True()' in lines[x]:
                    lines[x] = lines[x].replace('"Boolean") value True()','"Number") value Double(0)')
                if 'refid' in lines[x-1] and 'QName(PackageNamespace(""), "int")' in lines[x] and 'returns' in lines[x+1]:
                    f = x
                    while not 'pushscope' in lines[x]: x+=1
                    x+=1
                    rem = 'SD213123123123213'
                    while not 'returnvoid' in lines[x]:            
                        lines[x] = rem
                        x+=1
                    while rem in lines:
                        lines.remove(rem)
                    x = f
                x+=1
                
            WriteAllLines(file,lines)
            return
            
files = GetAllAsasm()
fixwon()
print("[#] OK")