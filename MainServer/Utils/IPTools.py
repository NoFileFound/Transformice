
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
    def ColorIP(ip) -> str:
        pass