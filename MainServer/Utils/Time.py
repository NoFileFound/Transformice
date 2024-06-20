import datetime
import time

class Time:
    @staticmethod
    def getTime():
        return int(int(str(time.time())[:10]))

    @staticmethod
    def getDate():
        return str(datetime.datetime.now()).replace("-", "/").split(".")[0].replace(" ", " - ")
        
    @staticmethod
    def getSecondsDiff(endTimeMillis):
        return int(int(str(time.time())[:10]) - endTimeMillis)
        
    @staticmethod
    def getHoursDiff(endTimeMillis):
        startTime = self.getTime()
        startTime = datetime.datetime.fromtimestamp(float(startTime))
        endTime = datetime.datetime.fromtimestamp(float(endTimeMillis))
        result = endTime - startTime
        seconds = (result.microseconds + (result.seconds + result.days * 24 * 3600) * 10 ** 6) / float(10 ** 6)
        hours = int(int(seconds) / 3600) + 1
        return hours

    @staticmethod
    def getDaysDiff(endTimeMillis):
        startTime = datetime.datetime.fromtimestamp(float(self.getTime()))
        endTime = datetime.datetime.fromtimestamp(float(endTimeMillis))
        result = endTime - startTime
        return result.days + 1
        
    @staticmethod
    def getDiffDays(time):
        diff = time - self.getTime()
        return diff / (24 * 60 * 60)