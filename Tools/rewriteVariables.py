import re

names = []
strings = []
data = open('158_scripts.txt','rb').read().split(b'\n')
array = []
class_name = ""
_class = ""
for line in data:
    if b'public class' in line:
        _class = line.decode().split('public class ')[1].split(' ')[0]
    if b'public static var' in line and (b':String = ' in line or b':int = ' in line or b':Number = ' in line) and b'_SafeStr' in line and (not b'= (_SafeStr' in line or b'//' in line) and not b'Math' in line:
        if (b':int = ' in line or b':Number = ' in line) and b'[' in line: continue
        array.append(line.decode()+"############"+_class)
function_name = ""
special_characters = ['\n', '\t', '\r', '\f', '\v', ';', '\\r', '\\n', '\\t', '\\f', '\\v', ' ']

def is_float(string):
    try:
        float(string)
        return not '.0' in str(float(string))
    except ValueError:
        return False

def overwriteFile(fileName):
    with open(fileName, 'rb') as f0:
        content = f0.read()

    for x in range(len(names)):
        n = names[x]
        s =  strings[x]
        r1 = s.encode('utf8')
        #if r1.is
        
        print(f"[REDACTEUR] Remplacement de la string {names[x]} par {s}")
        content = content.replace(n, str(float(s))) if is_float(s) else f'"{s}"' if not s.isdigit() else str(int(s))
    
    for line in content.split('\n'):
        if 'public static var' in line and (':String = ' in line or ':int = ' in line or ':Number = ' in line) and '_SafeStr' in line and (not '= (_SafeStr' in line or '//' in line) and not 'Math' in line:
            if (':int = ' in line or ':Number = ' in line) and '[' in line: continue
            content = content.replace(line+'\n','')
        
        
    with open(fileName, 'wb', encoding="utf-8") as f1:
        f1.write(content)
        
def fixArray():
    for i in range(len(array)):
        class_name = array[i].split('############')[1]
        #print(array[i].split('############'))
        tmp_string = array[i].split('############')[0].replace("public static var ", "").split('=', 1)[1].replace(' ', '', 1).replace('"', '')
        for x in special_characters:
            tmp_string = tmp_string.replace(x,'')
            class_name = class_name.replace(x,'')
        
        if tmp_string[:-1] == ';':
            tmp_string = tmp_string[:-1]
            
        if "//" in tmp_string and ('int' in array[i] or 'Number' in array[i]):
            print(tmp_string)
            tmp_string = tmp_string.split('//', 1)[1]
        
        if '(' in tmp_string and ('int' in array[i] or 'Number' in array[i]):
            print(tmp_string)
            tmp_string = str(eval(tmp_string))
            
        
        names.append(class_name + '.' + array[i].split('############')[0].replace("public static var ", "").replace(' ','').split(':', 1)[0])
        strings.append(tmp_string)
        print(f"[REDACTEUR] Nouveau nom potentiel trouvé : {names[i]} et la string {tmp_string}")
    #print(strings)
    
def RFC(input_string):
    pattern = fr'{class_name}\.{function_name}\((.*?)\)'
    return re.sub(pattern, lambda match: match.group(1).replace('\n', r'"\n"'), input_string)
    
def overwriteFileFunction(fileName):
    with open(fileName, 'rb', encoding="utf-8") as f0:
        content = f0.read()
        
    content = RFC(content)
    
    with open("a.txt", 'wb', encoding="utf-8") as f1:
        f1.write(content)


fixArray()
input("Voulez-vous écraser le fichier?")
overwriteFile("158_scripts.txt")