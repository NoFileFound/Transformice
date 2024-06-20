import time

class Other:
    @staticmethod
    def parsePlayerName(playerName):
        return (playerName[0] + playerName[1:].lower().capitalize()) if playerName.startswith("*") or playerName.startswith("+") else playerName.lower().capitalize()
        
    @staticmethod
    def randomGen():
        return int(str(time.time()).split('.')[1]) % 1