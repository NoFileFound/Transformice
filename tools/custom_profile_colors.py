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

def containsCount(_list, text):
    count = 0
    for item in filter(lambda find: text in find, _list):
        if text in item:
            count += 1
    return count

def FindProfileFile():
    for file in files:
        lines = ReadAllLines(file)
        if (containsCount(lines, "readUTF") == 3 and (containsCount(lines, "readInt") == 2 and containsCount(lines, "readByte") == 2)):
            return [file]
    return ['']

def ProfileReplace(path):
    if not path or path[0] == '':
        return False

    lines = ReadAllLines(path[0])
    find = 'callproperty        QName(PackageNamespace(""), "readByte"), 0'
    for x in range(len(lines)):
        if find in lines[x]:
            if x + 1 >= len(lines):
                return False

            parts = lines[x + 1].split('"')
            if len(parts) <= 3:
                return False

            class_name = parts[3]
            lines[x] = lines[x].replace("readByte", "readInt")
            WriteAllLines(path[0], lines)
            return [class_name]

    return False

def MakeUseful(_class):
    find = 'getproperty         QName(PackageNamespace(""), "{0}")'.format(_class[0])
    temp = 'TEMPTEXT1019910'
    for file in files:
        lines = ReadAllLines(file)
        for x in range(0, len(lines)):
            if find in lines[x]:
                _del = 1
                lines[x-2] = temp
                lines[x+1] = "      convert_i"
                lines[x+2] = "      setlocal2"
                lines[x+3] = ""
                lines[x+4] = "      getlocal2"
                lines[x+5] = "      setlocal3"
                lines[x+6] = ""
                
                x += 7
                while not "jump" in lines[x]:
                    lines[x] = temp
                    x+=1
                    _del+=1
                
                Lcls = lines[x].split("jump")[1]
                Lcls = "L" + Lcls.split("L")[1] + ":"
                x+=1
                while not Lcls in lines[x]:
                    lines[x] = temp
                    x+=1
                    _del+=1
                
                r = 0
                while _del > r:
                    lines.remove(temp)
                    r+=1

                WriteAllLines(file, lines)
                return

if __name__ == "__main__":
    files = GetAllAsasm()
    r = ProfileReplace(FindProfileFile())
    if r:
        MakeUseful(r)
        print("[#] PROFILE COLOR method added.\n")
    else:
        print("[#] PROFILE COLOR method aleardy addded.\n")