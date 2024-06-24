import asyncio
import sys
from colorconsole import win

class Logger:
    levels = {
        "DEBUG": [1, 5],
        "INFO": [1, 9],
        "WARN": [2, 6],
        "ERROR": [3, 4]
    }
    
    def __init__(self):
        self.win = win.Terminal()

    def log(self, level : str, message : str) -> None:
        color = self.levels[level][1]
        self.win.cprint(color, 0, "[" + level + "] ")
        self.win.cprint(7, 0, message)
            
    def debug(self, message : str) -> None:
        self.log("DEBUG", message)

    def info(self, message : str) -> None:
        self.log("INFO", message)

    def warn(self, message : str) -> None:
        self.log("WARN", message)

    def error(self, message : str) -> None:
        self.log("ERROR", message)