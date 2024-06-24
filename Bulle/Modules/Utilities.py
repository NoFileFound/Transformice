#coding: utf-8

# Modules
from Modules.ByteArray import ByteArray

class Utilities:
    def __init__(self, client):
        self.client = client
        self.server = client.server
        
    def getShamanItemCustom(self, code):
        for item in self.client.shamanItems.split(","):
            if "_" in item:
                itemSplited = item.split("_")
                custom = (itemSplited[1] if len(itemSplited) >= 2 else "").split("+")
                if int(itemSplited[0]) == code:
                    packet = ByteArray().writeByte(len(custom))
                    x = 0
                    while x < len(custom):
                        packet.writeInt(int(custom[x], 16))
                        x += 1
                    return packet.toByteArray()
        return chr(0)