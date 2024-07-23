import json

def process_input_text(input_text):
    lines = input_text.split('\n')
    
    for line in lines:
        line = line.strip()
        if line:
            if ':' in line:
                name_part, value_part = line.split(':', 1)
                name_part = name_part.strip()
                value_part = value_part.strip()
                try:
                    value_part = int(value_part)
                except ValueError:
                    value_part = float(value_part) if '.' in value_part else value_part
                print(json.dumps({"Name": name_part, "indent": 9, "ID": value_part}) + ',')
            else:
                print(json.dumps({"Name": line, "indent": 3}) + ',')

input_text = """

"""

process_input_text(input_text)