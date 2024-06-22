#coding: utf-8

class Langue:
    @staticmethod
    def getLangueID(lang):
        lang = lang.upper()
        datas = {"EN":0, "FR":1, "RU":2, "BR":3, "ES":4, "CN":5, "TR":6, "VK":7, "PL":8, "HU":9, "NL":10, "RO":11, "ID":12, "DE":13, "E2":14, "AR": 15, "PH":16, "LT":17, "JP":18, "CH":19, "FI":20, "CZ":21, "HR":22, "SK": 23, "BG":24, "LV":25, "HE":26, "IT":27, "ET":28, "AZ":29, "PT":30}
        if lang in datas:
            return datas[lang]
        return 0 # EN
        
    @staticmethod
    def getLangues():
        return [
			"EN", 
			"FR", 
			"RU", 
			"BR", 
			"ES", 
			"CN", 
			"TR", 
			"VK", 
			"PL", 
			"HU", 
			"NL", 
			"RO", 
			"ID", 
			"DE", 
			"E2", 
			"AR", 
			"PH", 
			"LT", 
			"JP", 
			"CH", 
			"FI", 
			"CZ", 
			"SK", 
			"HR", 
			"BG", 
			"LV", 
			"HE", 
			"IT", 
			"ET", 
			"AZ", 
			"PT"
		]