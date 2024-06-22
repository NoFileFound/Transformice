#coding: utf-8

class IPTools:
    @staticmethod
    def EncodeIP(ip) -> str:
        ip = '.'.join([hex(int(x) + 256)[3:].upper() for x in ip.split('.')])
        return '#' + ip

    @staticmethod
    def DecodeIP(ip) -> str:
        ip = ip[1:]
        result = []
        for i in ip.split('.'):
            if int(i, 16):
                result.append(int(i, 16))
            else:
                result.append(int(i))
        result = [str(i) for i in result]
        return '.'.join(result)
        
    @staticmethod
    def ColorIP(ip : str) -> str:
        ip = ip.lstrip('#')
        components = ip.split('.')
        concatenated = ''.join(components)
        concatenated = concatenated.ljust(8, '0')[:8]
        # Convert to RGB values
        r = int(concatenated[:2], 16)
        g = int(concatenated[2:4], 16)
        b = int(concatenated[4:6], 16)
        hex_color = f'#{r:02X}{g:02X}{b:02X}'
        return hex_color
        
    @staticmethod
    def GetCountry(ip):
        return "Brazil"