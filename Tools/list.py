import re

def extract_values_from_command(command):
    pattern = re.compile(r'(\w+)=([^\s,()]+(?:\.[^\s,()]+)?(?:[^\s,()]*))')
    
    matches = pattern.findall(command)
    
    values = []
    
    for key, value in matches:
        value = value.strip('()')
        
        if value.isdigit():
            values.append(int(value))
        elif value.replace('.', '', 1).isdigit() and value.count('.') < 2:
            values.append(float(value))
        else:
            if value.lower() == 'true':
                values.append(True)
            elif value.lower() == 'false':
                values.append(False)
            elif value == "''":
                values.append('')
            else:
                try:
                    values.append(eval(value))
                except:
                    values.append(value)
    
    return values

def process_commands(input_string):
    commands = re.findall(r'\b\w+\(?\)', input_string)
    
    all_values = []
    
    for command in commands:
        values = extract_values_from_command(command)
        all_values.append(values)
    
    return all_values

input_string = ("""



""")

command_values = process_commands(input_string)
print(command_values)