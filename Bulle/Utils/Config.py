#coding: utf-8
import json

"""
Simple json class that loads server files.
"""
class Json:
    @staticmethod
    def load_file(file_p : str, readasJson : bool = True, encoding='cp932'):
        with open(file_p, encoding=encoding) as F:
            if readasJson:
                data = json.load(F)
            else:
                data = F.read()
        return data
            
    @staticmethod
    def save_file(file_p : str, data) -> None:
        with open(file_p, 'w') as F:
            json.dump(data, F, indent=4)