#coding: utf-8
import json

"""
Simple json class that loads server files.
"""
class Json:
    def __init__(self, data=""):
        if data is None:
            self.data = {}
        else:
            self.data = data
            
    def getData(self):
        return self.data
        
    def setData(self, data) -> None:
        self.data = data

    def load_file(self, file_p : str, readasJson : bool = True):
        with open(file_p, 'rb') as F:
            if readasJson:
                self.data = json.load(F)
            else:
                self.data = F.read()
        return self.data
            
    def sv_file(self, file_p : str) -> None:
        with open(file_p, 'w') as F:
            json.dump(self.data, F, indent=4)