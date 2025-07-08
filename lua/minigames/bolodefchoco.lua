math.randomseed(os.time())
local owners = {
	["Chatta#0001"] = true
}

local _TEAM = {
	fs = {
		["Alfiecakes#0000"] = "EN",
		["Arki#0113"] = "BR",
		["Ashbolt#0000"] = "EN",
		["Bapereira#0000"] = "BR",
		["Cursedcorgi#4072"] = "EN",
		["Etyla#0015"] = "EN",
		["Fiend#8212"] = "EN",
		["Holaaae#4729"] = "EN",
		["Iuliluca#0000"] = "RO",
		["Katburger#0015"] = "EN",
		["Kingapysia#0000"] = "PL",
		["Lanadelrey#1407"] = "EN",
		["Lou#3859"] = "HE",
		["Mcsplash#0010"] = "EN",
		["Nectarinel#6133"] = "EN",
		["Mlledebby#0015"] = "FR",
		["Papero#9240"] = "EN",
		["Perlchen#0000"] = "DE",
		["Shinizzz#9648"] = "ES",
		["Silvyna#0020"] = "EN",
		["Tini#0015"] = "BR",
		["Toastyderp#0000"] = "EN",
	},
	mt = {
		["Chatta#0001"] = "JP"
	},
	fc = {
		["Acer#0010"] = "EN",
		["Adami#0010"] = "BR",
		["Aeroman#0010"] = "EN",
		["Alex#8808"] = "RO",
		["Allis#0010"] = "NL",
		["Amy#1453"] = "EN",
		["Apo#0020"] = "EN",
		["Aquawings#2537"] = "BR",
		["Arcanacra#0010"] = "PL",
		["Archaeron#0010"] = "DE",
		["Arki#0113"] = "BR",
		["Asmodan#0010"] = "PH",
		["Bapereira#0000"] = "BR",
		["Batt_mellamy#0015"] = "EN",
		["Bembija#0010"] = "LV",
		["Bemmh#0020"] = "EN",
		["Bijububu#0010"] = "BR",
		["Bodykudo#0000"] = "AR",
		["Bortverde#0015"] = "BR",
		["Brownie#9752"] = "EN",
		["Cagataymylov#0000"] = "AR",
		["Cassette#4972"] = "FR",
		["Cate#0010"] = "BR",
		["Censere#0015"] = "EN",
		["Centr#0015"] = "RU",
		["Charbz#3144"] = "EN",
		["Charlen#0010"] = "EN",
		["Chibi#0015"] = "RO",
		["Chizuko#0000"] = "VK",
		["Christine#0010"] = "EN",
		["Colakatze#0000"] = "DE",
		["Dandelion#0010"] = "RO",
		["Delirium#8208"] = "EN",
		["Demetrian#0010"] = "BR",
		["Distances#0095"] = "NL",
		["Djealvi#0010"] = "ID",
		["Doraemons#0010"] = "CN",
		["Eliterecon#0020"] = "EN",
		["Etoile#0095"] = "EN",
		["Excasr#0010"] = "BR",
		["Exsilium#0010"] = "EN",
		["Eyeground#0000"] = "FR",
		["Faz_x#0010"] = "PL",
		["Fanieh#0010"] = "BR",
		["Felidbr#0000"] = "BR",
		["Frankenshtein#0095"] = "RU",
		["Goondad#0020"] = "EN",
		["Haannq#0010"] = "ES",
		["Harshy#0095"] = "EN",
		["Hosual#0000"] = "PL",
		["Icewolfbob#0095"] = "EN",
		["Ihaya#0095"] = "RU",
		["Ikke#0020"] = "BR",
		["Ildubbio#0010"] = "RO",
		["Irishcow#0095"] = "EN",
		["Iupi#0010"] = "BR",
		["Jackgt20#0010"] = "ES",
		["Jacob#0095"] = "EN",
		["Jealy#0010"] = "EN",
		["Jefitou#0010"] = "ES",
		["Jerry#0095"] = "RU",
		["Jiaxian#0000"] = "MY",
		["Jordy#0010"] = "NL",
		["Karbi#0010"] = "ES",
		["Katburger#0015"] = "EN",
		["Kathryn#8850"] = "EN",
		["Katow#0095"] = "FR",
		["Kim#0010"] = "EN",
		["Kiwrimai#0015"] = "BR",
		["Kloure#0010"] = "EN",
		["Kurt#0010"] = "BR",
		["Lacksas#0000"] = "BR",
		["Lament#0010"] = "PL",
		["Lapinprince#0095"] = "EN",
		["Lexa#0010"] = "TR",
		["Lezzly#0010"] = "ES",
		["Link#0010"] = "EN",
		["Linkaito#0010"] = "FR",
		["Lostchicken#0095"] = "EN",
		["Lucas#0010"] = "BR",
		["Luchreimse#0000"] = "EN",
		["Lummit#0010"] = "BR",
		["Makinit#0095"] = "EN",
		["Makis#0010"] = "ES",
		["Massi#0015"] = "AR",
		["Matekoo#0015"] = "HR",
		["Matterhorn#0010"] = "BR",
		["Mcsplash#0000"] = "EN",
		["Meekoru#0095"] = "VK",
		["Meow#0010"] = "PL",
		["Mervynpeake#0095"] = "EN",
		["Mesmera#0015"] = "PL",
		["Miau#0015"] = "BR",
		["Migotka#0010"] = "PL",
		["Milkycoffee#0015"] = "PL",
		["Minkee#0095"] = "EN",
		["Mishska#0010"] = "RU",
		["Mistle#0010"] = "VK",
		["Miwakiko#0010"] = "FR",
		["Morningstar#0010"] = "BR",
		["Mousey#0010"] = "EN",
		["Mquk#0095"] = "FR",
		["Mrslouzifer#0010"] = "DE",
		["Naiyme#0015"] = "BR",
		["Natiibobr#0000"] = "PL",
		["Nattorei#0010"] = "EN",
		["Nicor22#0000"] = "ES",
		["Noooooooorr#0000"] = "AR",
		["Notearl#0000"] = "DE",
		["Null#0010"] = "EN",
		["Obemice#0095"] = "ES",
		["Old#0010"] = "BR",
		["Ork#0015"] = "BR",
		["Pamots#0010"] = "BR",
		["Papero#9240"] = "EN",
		["Peanut_butter#0015"] = "HU",
		["Philae#0010"] = "FR",
		["Phliperama#0000"] = "BR",
		["Piratearthur#0010"] = "CN",
		["Plisette#0010"] = "EN",
		["Pop#0010"] = "BR",
		["Quavio#0506"] = "IT",
		["Rachel#0010"] = "EN",
		["Ragekitteee#0015"] = "AR", 
		["Reshman#0020"] = "EN",
		["Rkubi#0000"] = "PL",
		["Saintgio#0000"] = "ES",
		["Sam#0095"] = "EN",
		["Samandaira#0000"] = "RU",
		["Santa#0010"] = "NL",
		["Sav#0010"] = "EN",
		["Sebafrancuz#0000"] = "PL",
		["Sebaisseba#0020"] = "EN",
		["Secretive#0010"] = "EN",
		["Sha#0010"] = "TR",
		["Shamousey#0015"] = "EN",
		["Sharpiepoops#0020"] = "EN",
		["Shinyday#0095"] = "RO",
		["Shironinger#0010"] = "ES",
		["Sollyun#0015"] = "BR",
		["Star#8558"] = "BR",
		["Staszekowaty#0010"] = "PL",
		["Sufp#0010"] = "BR",
		["Tachiyukan#0010"] = "EN",
		["Tarmac#0010"] = "RO",
		["Tat#0095"] = "EN",
		["Ted#0015"] = "TR",
		["Tini#0015"] = "BR",
		["Titivillus#0010"] = "TR",
		["Toasteis#7402"] = "EN",
		["Tortuegreen#0000"] = "FR",
		["Turkitutu#0000"] = "AR",
		["Verdomice#0095"] = "ES",
		["Visne#0010"] = "TR",
		["Vulli#0015"] = "EN",
		["Warfenix#0095"] = "ES",
		["Wercade#0095"] = "RU",
		["Wrfg#0000"] = "CN",
		["Xiaojiemei#0095"] = "ID",
		["Yosska#0010"] = "NL",
		["Zoefke#0010"] = "NL"
	},
	sent = {
		["Adami#0010"] = "BR",
		["Amegake#0015"] = "PL",
		["Archaeron#0010"] = "DE",
		["Asmodan#0010"] = "PH",
		["Batt_mellamy#0015"] = "EN",
		["Bembija#0010"] = "LV",
		["Bog#0015"] = "ES",
		["Bortverde#0015"] = "BR",
		["Calysis#0015"] = "EN",
		["Censere#0015"] = "EN",
		["Centr#0015"] = "RU",
		["Charissa#0010"] = "EN",
		["Charlen#0010"] = "EN",
		["Chiara#0010"] = "EN",
		["Chibi#0015"] = "RO",
		["Coska#0015"] = "PL",
		["Doraemons#0010"] = "CN",
		["Dracoleaf#0010"] = "EN",
		["Eagles0ng#0010"] = "RU",
		["Elysium#0015"] = "AR",
		["Etyla#0015"] = "FR",
		["Excasr#0010"] = "BR",
		["Gavin#0015"] = "EN",
		["Grastfetry#0015"] = "BR",
		["Hollya#0015"] = "FR",
		["Hotaru#0015"] = "RO",
		["Inkzooka#0015"] = "EN",
		["Jordy#0010"] = "NL",
		["Katburger#0015"] = "EN",
		["Kiwrimai#0015"] = "BR",
		["Lament#0010"] = "PL",
		["Layora#0015"] = "IT",
		["Link#0010"] = "EN",
		["Linkaito#0010"] = "FR",
		["Massi#0015"] = "AR",
		["Matekooo#0015"] = "HR",
		["Mesmera#0015"] = "PL",
		["Miau#0015"] = "BR",
		["Milkycoffee#0015"] = "PL",
		["Mlledebby#0015"] = "FR",
		["Mrslouzifer#0010"] = "DE",
		["Naiyme#0015"] = "BR",
		["Null#0010"] = "EN",
		["Ork#0015"] = "BR",
		["Peanut_butter#0015"] = "HU",
		["Plubio#0015"] = "ES",
		["Ragekitteee#0015"] = "AR",
		["Sav#0010"] = "EN",
		["Sentihu#0015"] = "HU",
		["Sentinonyme#0015"] = "FR",
		["Sha#0010"] = "TR",
		["Shamousey#0015"] = "EN",
		["Shironinger#0010"] = "ES",
		["Sollyun#0015"] = "BR",
		["Speedy#0015"] = "ES",
		["Tarmac#0010"] = "RO",
		["Ted#0015"] = "TR",
		["Tini#0015"] = "BR",
		["Visne#0010"] = "TR",
		["Vividia#0015"] = "HU",
		["Vulli#0015"] = "EN",
		["Winjid#0015"] = "EN",
		["Wooferx#0015"] = "HU",
		["Xiezi#0010"] = "CN"
	},
	sh = {
		["Awesomz#2736"] = "RO",
		["Blank#3495"] = "EN",
		["Bolodefchoco#0000"] = "BR",
		["Heniyengui#0000"] = "AR",
		["Lobezito#2632"] = "ES",
		["Ori#6575"] = "HE",
		["Tocutoeltuco#0000"] = "ES",
		["Verdomice#0095"] = "ES"
	},
	st = {
		["Adriantal#0000"] = "PL",
		["Ae_86#5182"] = "LV",
		["Barberserk#0000"] = "GR",
		["Bisharch#4886"] = "TR",
		["Bodykudo#0000"] = "AR",
		["Bolodefchoco#0000"] = "BR",
		["Danielthemouse#6206"] = "HE",
		["Derpfacederp#0000"] = "EE",
		["Don#3072"] = "HU",
		["Error_404#0000"] = "AR",
		["Flindix#0095"] = "BR",
		["Heniyengui#0000"] = "AR",
		["Hmiida#0000"] = "AR",
		["Lobezito#2632"] = "ES",
		["Massi#0015"] = "AR",
		["Miiiclaroo#0000"] = "ES",
		["Mquk#0095"] = "FR",
		["Nicor22#0000"] = "ES",
		["Ori#6575"] = "HE",
		["Peanut_butter#0015"] = "HU",
		["Rkubi#0000"] = "PL",
		["Syrius#8114"] = "LV",
		["Teddeeh#0000"] = "HR",
		["Tortuegreen#0000"] = "FR",
		["Tocutoeltuco#0000"] = "ES",
		["Urook#1131"] = "FR",
		["Wassimevicw#0000"] = "AR",
		["Wrfg#0000"] = "CN",
		["Zimmer#9770"] = "ES",
		["Zutto#4451"] = "ES"
	}
}

system.looping = function(f, tick)
	local s = 1000 / tick
	local t = { }

	local bar = 0
	local fooTimer = function()
		bar = bar + 1
		t[bar] = system.newTimer(f, 1000, true)
	end

	for timer = 0, 1000 - s, s do
		system.newTimer(fooTimer, 1000 + timer, false)
	end
	return t
end

math.pythag = function(x1, y1, x2, y2, range)
	return (x1-x2)^2 + (y1-y2)^2 <= (range^2)
end
math.clamp = function(value, min, max)
	return value < min and min or value > max and max or value
end

string.split = function(str, pat)
	local out, counter = { }, 0

	string.gsub(str, pat, function(value)
		counter = counter + 1
		out[counter] = tonumber(value) or value
	end)

	return out
end
string.nick = function(playerName)
	if not string.find(playerName, '#') then
		playerName = playerName .. "#0000"
	end

	return (string.gsub(string.lower(playerName), "%a", string.upper, 1))
end
ui.banner = function(image, aX, aY, n, time)

	time = time or 5
	aX = aX or 100
	aY = aY or 100

	local img = tfm.exec.addImage(image, "&0", aX, aY, n)
	system.newTimer(function()
		tfm.exec.removeImage(img)
	end, time * 1000, false)
end

local pairsByIndexes = function(list, f)
	local out = {}
	for index in next, list do
		out[#out + 1] = index
	end
	table.sort(out, f)

	local i = 0
	return function()
		i = i + 1
		if out[i] ~= nil then
			return out[i], list[out[i]]
		end
    end
end

table.remove = function(list, pos)
	local len, out = #list

	if not pos or pos == len then
		out = list[len]
		list[len] = nil
	elseif pos < len then
		out = list[pos]
		list[pos] = nil

		for i = pos, len do
			list[i] = list[i + 1]
		end
	end
	return out
end
table.random = function(tbl)
	return tbl[math.random(#tbl)]
end

local PLAYERDATA = { }
local modules = { }

modules.plane = function()
	local toDespawn = {}
	do
		local addShamanObject = tfm.exec.addShamanObject
		tfm.exec.addShamanObject = function(...)
			toDespawn[#toDespawn + 1] = {
				addShamanObject(...),
				os.time() + 3000
			}
			return toDespawn[#toDespawn][1]
		end
	end
	text={}
	lang={}
	lang.en={
		clickRunway="Choose a plane.",
		clickRunway2="Click Runway to start",
		help="<J>Welcome to #plane <b>%s</b>! Use the controls to safely fly your plane to different Airports. You are able to fly however you want, but try not to crash!\n\n<ROSE>Commands:\n<font color='#32A9FF'><b>Shift</b> - Takes you back to the starting airport.\n<b>Move Right</b> - Accelerates your plane (your plane must be atleast at 85 speed to fly).\n<b>Move left or E</b> - Decelerates your plane, but try to keep your air speed over 85!\n<b>Jump</b> - Makes your plane go up when you have a speed of 85 or more.\n<b>Down</b> - Makes your plane slightly descend.\n<b>Space</b> - Makes your plane stay within one altitude; your plane will not go up or down.\n<b>F</b> - Makes your speed stay the same / not decreasing or increasing.\n\n<font size='16'><N>CLICK RUNWAY TO PREPARE FOR TAKE OFF.\n\n<font size='12'><J>Made by Fuzzyfirsdog#0000.<VP> Images edited by Blank#3495.",
		descend="DESCEND",
		altitude="ALTITUDE",
		speed="SPEED",
		distance="DISTANCE",
		accelerate="ACCELERATE",
		decelerate="DECELERATE",
		ascend="ASCEND",
		truth="True",
		falso="False",
		runway="Runway",
		close="Close"
	}
	lang.ar={
		clickRunway="Choose a plane",
		clickRunway2="Click Runway to start",
		help="<J>Welcome to #plane <b>%s</b>! Use the controls to safely fly your plane to different Airports. You are able to fly however you want, but try not to crash!\n\n<ROSE>Commands:\n<font color='#32A9FF'><b>Shift</b> - Takes you back to the starting airport.\n<b>Move Right</b> - Accelerates your plane (your plane must be atleast at 85 speed to fly).\n<b>Move left or E</b> - Decelerates your plane, but try to keep your air speed over 85!\n<b>Jump</b> - Makes your plane go up when you have a speed of 85 or more.\n<b>Down</b> - Makes your plane slightly descend.\n<b>Space</b> - Makes your plane stay within one altitude; your plane will not go up or down.\n<b>F</b> - Makes your speed stay the same / not decreasing or increasing.\n\n<font size='16'><N>CLICK RUNWAY TO PREPARE FOR TAKE OFF.\n\n<font size='12'><J>Made by Fuzzyfirsdog#0000.<VP> Images edited by Blank#3495.",
		descend="DESCEND",
		altitude="ALTITUDE",
		speed="SPEED",
		distance="DISTANCE",
		accelerate="ACCELERATE",
		decelerate="DECELERATE",
		ascend="ASCEND",
		truth="True",
		falso="False",
		runway="مدرج المطار",
		close="Close"
	}
	for _, s in next, {'AutoTimeLeft', 'PhysicalConsumables', 'AfkDeath', 'AutoShaman', 'AutoNewGame'} do
		tfm.exec['disable' .. s]()
	end
	tfm.exec.newGame('<C><P Ca="" F="0" H="3600" L="300000" aie="" /><Z><S><S X="2283" o="0" L="3000" Y="3484" H="350" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="400" L="800" Y="3460" H="314" P="0,0,0.3,0.2,0,0,0,0" T="6" /><S X="11839" L="400" Y="3612" H="400" P="0,0,0.3,3,45,0,0,0" T="6" /><S X="17885" o="0" L="3000" Y="3464" H="350" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="28453" o="0" L="3000" Y="3466" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="16208" L="800" Y="3444" H="314" P="0,0,0.1,0.2,0,0,0,0" T="7" /><S X="26914" L="400" Y="3428" H="344" P="0,0,0.3,0.2,0,0,0,0" T="6" /><S X="37994" L="400" Y="3408" H="400" P="0,0,0.05,0.1,0,0,0,0" T="11" /><S X="45602" L="1000" Y="3697" H="1000" P="0,0,0.3,3,40,0,0,0" T="5" /><S X="57719" o="0" L="3000" Y="3432" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="56055" L="400" Y="3411" H="400" P="0,0,0.3,0,0,0,0,0" T="10" /><S X="39695" o="0" L="3000" Y="3419" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="44208" L="74" Y="1769" H="78" P="1,0,0,20,-20,0,900,0" T="3" /><S X="44735" L="74" Y="2197" H="78" P="1,0,0,20,-80,0,900,0" T="3" /><S X="200" o="324650" L="400" Y="3152" H="24" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="407" o="324650" L="24" Y="3221" H="163" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="44495" L="1000" Y="3653" H="1000" P="0,0,0.3,3,40,0,0,0" T="5" /><S X="74655" o="0" L="3000" Y="3415" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="34349" L="1000" Y="3722" H="1000" P="0,0,0.3,3,40,0,0,0" T="5" /><S X="33870" L="1000" Y="3915" H="1000" P="0,0,0.3,3,40,0,0,0" T="5" /><S X="32903" L="1000" Y="3738" H="1000" P="0,0,0.3,3,40,0,0,0" T="5" /><S X="73049" L="400" Y="3410" H="400" P="0,0,0.3,0,0,0,0,0" T="10" /><S X="7900" L="3000" Y="4689" H="3000" P="0,0,0.3,2,0,0,0,0" T="6" /><S X="7203" L="900" Y="3527" H="900" P="0,0,0.3,1,45,0,0,0" T="5" /><S X="9066" L="3000" Y="4681" H="3000" P="0,0,0.3,2,0,0,0,0" T="6" /><S X="9839" L="900" Y="3366" H="900" P="0,0,0.3,1,45,0,0,0" T="5" /><S X="12061" L="3000" Y="4843" H="3000" P="0,0,0.3,2,0,0,0,0" T="6" /><S X="23971" L="3000" Y="4572" H="3000" P="0,0,0.1,1,0,0,0,0" T="7" /><S X="24117" L="3000" Y="4554" H="3000" P="0,0,0.1,1,0,0,0,0" T="7" /><S X="35322" L="3000" Y="3528" H="309" P="0,0,1,1,0,0,0,0" T="5" /><S X="53271" L="3000" Y="3456" H="481" P="0,0,0.3,1,0,0,0,0" T="10" /><S X="53051" L="3000" Y="4851" H="3000" P="0,0,0.3,1,45,0,0,0" T="10" /><S X="80628" L="3000" Y="3528" H="468" P="0,0,0.3,0.2,7,0,0,0" T="6" /><S X="5281" o="0" L="3000" Y="3484" H="350" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="20881" o="0" L="3000" Y="3464" H="350" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="31431" o="0" L="3000" Y="3466" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="42695" o="0" L="3000" Y="3419" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="60719" o="0" L="3000" Y="3432" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="77654" o="0" L="3000" Y="3414" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="95871" o="0" L="3000" Y="3400" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="94177" L="400" Y="3417" H="468" P="0,0,0.3,0.2,0,0,0,0" T="6" /><S X="98867" o="0" L="3000" Y="3400" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="123235" L="400" Y="3416" H="468" P="0,0,0.3,0.2,0,0,0,0" T="6" /><S X="124939" o="0" L="3000" Y="3412" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="127935" o="0" L="3000" Y="3412" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="168567" L="400" Y="3408" H="468" P="0,0,0.3,0.2,0,0,0,0" T="6" /><S X="170267" o="0" L="3000" Y="3408" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="173267" o="0" L="3000" Y="3408" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="214347" o="0" L="3000" Y="3402" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /><S X="217347" o="0" L="3000" Y="3402" H="400" P="0,0,0.3,0.2,0,0,0,0" T="12" /></S><D><DS X="219" Y="3289" /></D><O><O C="15" X="8453" P="0" Y="2564" /><O C="15" X="27032" P="0" Y="2497" /><O C="15" X="33275" P="0" Y="2755" /><O C="15" X="42144" P="0" Y="2646" /></O></Z></C>')
	data={}
	i=9210
	function setData(n)
		i=i+23
		text[n]=lang[tfm.get.room.playerList[n].community] or lang.en
		data[n]=
			{
				inRoom=true,
				stall=false,
				radar={color=math.random(0xFFFFFF),id=i+33},
				lastX=tfm.get.room.playerList[n].x,
				lastY=tfm.get.room.playerList[n].y,
				plane=true,
				acceleration=false,
				speed=0,
				up=false,
				down=false,
				decelerate=false,
				stabilizeSpeed=false,
				speedLim=250,
				using="165d9c30648.png",
				accelerationSpeed=2.5,
				stealth=0,
				s=1,
				id="",
				sonicboom=false,
				id2=""
			}
	end
	a=""
	function addPlanes(n)
		tfm.exec.lowerSyncDelay(n)
		ui.addTextArea(1001, "<p align='center'><b><a href='event:plane1'><J>A340</J></a></b>", n, 200, 3167, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1002, "<p align='center'><b><J><a href='event:plane2'>Cessna Citation</J></a></b>", n, 200, 3190, 190, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1003, "<p align='center'><b><J><a href='event:plane3'>F18</J></a></b>", n, 295, 3213, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1004, "<p align='center'><b><J><a href='event:plane4'>A320</J></a></b>", n, 200, 3213, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1005, "<p align='center'><b><J><a href='event:plane5'>B-737</J></a></b>", n, 200, 3236, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1006, "<p align='center'><b><J><a href='event:plane6'>B-787</J></a></b>", n, 295, 3236, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1007, "<p align='center'><b><J><a href='event:plane7'>A380</J></a></b>", n, 295, 3259, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1008, "<p align='center'><b><J><a href='event:plane8'>B-747</J></a></b>", n, 200, 3259, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1009, "<p align='center'><b><J><a href='event:plane9'>A350 XWB</J></a></b>", n, 200, 3282, 95, 23, 0x000001, 0x000001, 1, false)
		ui.addTextArea(1010, "<p align='center'><b><J><a href='event:plane10'>Concorde</J></a></b>", n, 295, 3282, 95, 23, 0x000001, 0x000001, 1, false)
		if n=="Fuzzyfirsdog#0000" or n=="Bolodefchoco#0000" or n=="Blank#3495" or n=="Sebafrancuz#0000" then
			ui.addTextArea(1011, "<p align='center'><b><J><a href='event:plane11'>SR 71B</J></a></b>", n, 200, 3305, 95, 23, 0x000001, 0x000001, 1, false)
		end
	end
	function showPlanes(n,a)
		if data[n].using=="165d9c30648.png" then -- A340
			tfm.exec.addImage(data[n].using,"%"..n, -415, -170, a)
		elseif data[n].using=="165e4735f1f.png" then -- Cessna Citation
			tfm.exec.addImage(data[n].using,"%"..n, -225, -80, a)
		elseif data[n].using=="165b9ec4f68.png" then -- F18
			tfm.exec.addImage(data[n].using,"%"..n, -225, -122, a)
		elseif data[n].using=="165c581475c.png" then --A320
			tfm.exec.addImage(data[n].using,"%"..n, -385, -160, a)
		elseif data[n].using=="165dea1e6a2.png" then --737
			tfm.exec.addImage(data[n].using,"%"..n, -420, -214, a)
		elseif data[n].using=="165d9c2a1f9.png" then --787
			tfm.exec.addImage(data[n].using,"%"..n, -330, -217, a)
		elseif data[n].using=="165cfe74cca.png" then --A380
			tfm.exec.addImage(data[n].using,"%"..n, -375, -180, a)
		elseif data[n].using=="165cfe53158.png" then --747
			tfm.exec.addImage(data[n].using,"%"..n, -335, -140, a)
		elseif data[n].using=="165d4a31588.png" then --A350XWB
			tfm.exec.addImage(data[n].using,"%"..n, -355, -230, a)
		elseif data[n].using=="165d4a2c6af.png" then --Concorde
			tfm.exec.addImage(data[n].using,"%"..n, -450, -107, a)
		elseif data[n].using=="165d4baf672.png" then --SR 71B
			data[n].id2=tfm.exec.addImage(data[n].using,"%"..n, -307, -125, a)
		end
	end
	function eventNewPlayer(n)
		if not data[n] then
				setData(n)
		end
		ui.addTextArea(97, "", n, 595, 277, 200, 115, 0x005c0b, 0x005c0b, 1, true)
		ui.addTextArea(178, "", n, 595, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(278, "", n, 606, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(378, "", n, 612, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(478, "", n, 620, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(578, "", n, 632, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(678, "", n, 644, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(787, "", n, 658, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(878, "", n, 672, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(978, "", n, 737, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(179, "", n, 708, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(237, "<p align='center'><font color='#FFFFFF'><font size='40'>"..text[n].clickRunway, n, 0, 350, 800, 60, 0x324650, 0x000000, 0.7, true)
		data[n].inRoom=true
		addPlanes(n)
		tfm.exec.respawnPlayer(n)
		tfm.exec.chatMessage(text[n].help,n)
		for i=0,100 do
			tfm.exec.bindKeyboard(n, i, true, true)
		end
	a=n
		for n,player in pairs(tfm.get.room.playerList) do
			showPlanes(n,a)
		end
		showPlanes(n,nil)
	end

	for n,player in pairs(tfm.get.room.playerList) do
		setData(n)
		ui.addTextArea(97, "", n, 595, 277, 200, 115, 0x005c0b, 0x005c0b, 1, true)
		ui.addTextArea(178, "", n, 595, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(278, "", n, 606, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(378, "", n, 612, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(478, "", n, 620, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(578, "", n, 632, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(678, "", n, 644, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(787, "", n, 658, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(878, "", n, 672, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(978, "", n, 737, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(179, "", n, 708, 392, 1, 1, 0x1aff00, 0x000000, 1, true)
		ui.addTextArea(237, "<p align='center'><font color='#FFFFFF'><font size='40'>"..text[n].clickRunway, n, 0, 350, 800, 60, 0x324650, 0x000000, 0.7, true)
		addPlanes(n)
		tfm.exec.chatMessage(text[n].help,n)
		tfm.exec.addImage(data[n].using,"%"..n, -415, -170, nil)
		for i=0,100 do
			tfm.exec.bindKeyboard(n, i, true, true)
		end
	end
	function accelerate(n)
		if not data[n].stabilizeSpeed==true and data[n].speed<=data[n].speedLim and data[n].decelerate==false and data[n].acceleration==true then
			data[n].speed=data[n].speed+data[n].accelerationSpeed
		end
		if data[n].speed>85 and data[n].up==true and data[n].down==false then
			tfm.exec.movePlayer(n,0,0,false,0,-50,false)
		elseif data[n].speed>85 and data[n].up==false and data[n].down==false then
			tfm.exec.movePlayer(n,0,0,false,0,-27,false)
		elseif data[n].speed>85 and data[n].up==false and data[n].down==true then
			tfm.exec.movePlayer(n,0,0,false,0,-13,false)
		end
		if data[n].acceleration==true then
			tfm.exec.movePlayer(n,0,0,false,data[n].speed,0,false)
		end
	end
	function eventKeyboard(n, key, down, x,y)
		if key==16 then
			ui.addTextArea(237, "<p align='center'><font color='#FFFFFF'><font size='40'>"..text[n].clickRunway, n, 0, 350, 800, 60, 0x324650, 0x000000, 0.7, true)
			data[n].speed=0
			data[n].acceleration=false
			tfm.exec.movePlayer(n,215,3287,false,0,0,false)
			ui.removeTextArea(1000,n)
		elseif key==72 then
			ui.addTextArea(228, "<font size='12'>"..string.format(text[n].help,n), n, 27, 105, 529, 270, 0x324650, 0x324650, 1, true)
			ui.addTextArea(113, "<p align='center'><a href='event:close'><font size='13'>"..text[n].close, n, 27, 370, 529, 24, 0x496878, 0x000001, 1, true)
		elseif key==2 then
			data[n].acceleration=true
			data[n].decelerate=false
			data[n].stabilizeSpeed=false
		elseif key==69 then
			data[n].decelerate=true
			data[n].acceleration=false
			data[n].stabilizeSpeed=false
		elseif key==0 then
			data[n].decelerate=true
			data[n].acceleration=false
			data[n].stabilizeSpeed=false
		elseif key==1 then
			data[n].up=true
			data[n].down=false
		elseif key==32 then
			data[n].up=false
			data[n].down=false
		elseif key==70 then
			data[n].decelerate=false
			data[n].stabilizeSpeed=true
		elseif key==3 then 
			data[n].up=false
			data[n].down=true
		end
	end
	function decelerate(n)
		if data[n].decelerate==true and data[n].speed>0 then
			data[n].speed=data[n].speed-7
			tfm.exec.movePlayer(n,0,0,false,data[n].speed,0,false)
		end
		if data[n].speed<0 then
			data[n].speed=0
		end
	end
	function eventLoop()
		for n,player in pairs(tfm.get.room.playerList) do
			if data[n].inRoom==true then
				if data[n].speed>=450 and data[n].sonicboom==false then
					data[n].sonicboom=true
					if data[n].using=="165b9ec4f68.png" then
						data[n].id=tfm.exec.addImage("165e9e21f73.png","%"..n, -300, -167, nil)
					elseif data[n].using=="165d4a2c6af.png" then
						data[n].id=tfm.exec.addImage("165e9e2c348.png","%"..n, -450, -185, nil)
					elseif data[n].using=="165d4baf672.png" then
						data[n].id=tfm.exec.addImage("165e9e262ac.png","%"..n, -307, -120, nil)
						tfm.exec.removeImage(data[n].id2)
					end
				end
				if data[n].speed<450 and data[n].sonicboom==true then
					tfm.exec.removeImage(data[n].id)
					data[n].sonicboom=false
					if data[n].using=="165d4baf672.png" then
						tfm.exec.addImage(data[n].using,"%"..n, -307, -125, a)
					end
				end
				if data[n].stealth==0 then
					if not (tfm.get.room.playerList[n].x<=140) and not (tfm.get.room.playerList[n].y<=0) then
						data[n].lastX=tfm.get.room.playerList[n].x
						data[n].lastY=tfm.get.room.playerList[n].y
						ui.addTextArea(data[n].radar.id, "", nil, math.floor(tfm.get.room.playerList[n].x/1500+595), math.floor(tfm.get.room.playerList[n].y/30+278), 1, 1, data[n].radar.color, 0xFFFFFF, 1, true)
						ui.addTextArea(data[n].radar.id/2.378, "<p align='center'><font size='10'><font color='#FFFFFF'>"..n, nil, math.floor(tfm.get.room.playerList[n].x/1500+480), math.floor(tfm.get.room.playerList[n].y/30+260), 255, 24, 0x324650, 0x000001, 0, true)
					else
						ui.addTextArea(data[n].radar.id, "", nil, math.floor(data[n].lastX/1500+595), math.floor(data[n].lastY/30+278), 1, 1, data[n].radar.color, 0xFFFFFF, 1, true)
					end
				end
			end
			if data[n].stealth==1 then
				if not (tfm.get.room.playerList[n].x<140) and not (tfm.get.room.playerList[n].y<=0) then
						data[n].lastX=tfm.get.room.playerList[n].x
						data[n].lastY=tfm.get.room.playerList[n].y
						ui.addTextArea(data[n].radar.id, "", n, math.floor(tfm.get.room.playerList[n].x/1500+595), math.floor(tfm.get.room.playerList[n].y/30+278), 1, 1, 0xFF0000, 0xFF0000, 1, true)
						ui.addTextArea(data[n].radar.id/2.378, "<p align='center'><font size='10'><font color='#FF0000'>"..n, n, math.floor(tfm.get.room.playerList[n].x/1500+480), math.floor(tfm.get.room.playerList[n].y/30+260), 255, 24, 0x324650, 0x000001, 0, true)
					else
						ui.addTextArea(data[n].radar.id, "", n, math.floor(data[n].lastX/1500+595), math.floor(data[n].lastY/30+278), 1, 1, 0xFF0000, 0xFF0000, 1, true)
					end
			end
			accelerate(n)
			if data[n].decelerate==true and data[n].speed>0 then
				data[n].speed=data[n].speed-7
				tfm.exec.movePlayer(n,0,0,false,data[n].speed,0,false)
			end
			if data[n].speed<0 then
				data[n].speed=0
			end
			if (-tfm.get.room.playerList[n].y+3600<1000) and data[n].speed>85 then
				ui.addTextArea(444, "<p align='center'><font color='#FFFFFF'><font face='verdana'><font size='13'>Terrain! Pull up.\n", n, 369, 36, 133, 28, 0xff0000, 0xFF0000, 1, true)
			end
			if (-tfm.get.room.playerList[n].y+3600>1000) or data[n].speed<85 then
				ui.removeTextArea(444,n)
			end
			if -tfm.get.room.playerList[n].y+3600>352 and data[n].speed<85 and data[n].stall==false then
				data[n].stall=true
			end
			if data[n].stall==true then
				ui.addTextArea(3, "<p align='center'><font color='#FFFFFF'><font face='verdana'><font size='15'>STALL\n", n, 369, 36, 133, 35, 0xff0000, 0xFF0000, 1, true)
				data[n].stall=false
			elseif data[n].speed>85 or -tfm.get.room.playerList[n].y+3600<365 then
				ui.removeTextArea(3,n)
			end
			if data[n].up==true then
				up="<font color='#FF9696'><b>↑"
			end
			if data[n].up==false then
				up="<font color='#FF9696'><b>-"
			end
			if data[n].down==true then
				down="<VP>"..text[n].truth
				up="<font color='#FF9696'><b>↓"
			end
			if data[n].acceleration==true then
				acceleration="<font color='#FF9696'><b>→"
			end
			if data[n].acceleration==false then
				acceleration="<font color='#FF0000'>"..text[n].falso
			end
			if data[n].decelerate==true then
				decelerate="<VP>"..text[n].truth
				acceleration="<font color='#FF9696'><b>←"
			end
			if data[n].decelerate==false then
				decelerate="<font color='#FF9696'><b>-"
			end
			if data[n].stabilizeSpeed==true or (data[n].stabilizeSpeed==false and data[n].decelerate==false and data[n].acceleration==false) then
				decelerate="<font color='#FF0000'>F"..text[n].falso
				acceleration="<font color='#FF9696'><b>-"
				decelerate="<font color='#FF9696'><b>-"
			end
			ui.addTextArea(2, "", n, 9, 27, 316, 51, 0x000001, 0x000001, 1, true)
			ui.addTextArea(0, "<p align='center'><font color='#FFFFFF'><font face='verdana'><font size='13'>SPEED\n"..math.floor(data[n].speed).."\n"..acceleration, n, 121, 32, 93, 60, 0x000000, 0xff0000, 1, true)
			ui.addTextArea(1, "<p align='center'><font color='#FFFFFF'><font face='verdana'><font size='13'>ALTITUDE: \n"..(-tfm.get.room.playerList[n].y+3600).."\n"..up, n, 14, 32, 93, 60, 0x000000, 0xff0000, 1, true)
			ui.addTextArea(13, "<p align='center'><font color='#FFFFFF'><font face='verdana'><font size='13'>DISTANCE\n"..tfm.get.room.playerList[n].x, n, 228, 32, 93, 40, 0x000000, 0xff0000, 1, true)
		end
	end
	function eventPlayerDied(n)
		if data[n].s==0 then
			ui.addTextArea(237, "<p align='center'><font color='#FFFFFF'><font size='40'>"..text[n].clickRunway, n, 0, 350, 800, 61, 0x324650, 0x000000, 0.7, true)
		end
		data[n].speed=0
		data[n].acceleration=false
		tfm.exec.respawnPlayer(n)
		showPlanes(n,nil)
	end

	function eventTextAreaCallback(id,n,a)
		data[n].stealth=0
		data[n].s=0
		if not (a=="runway") then
			ui.addTextArea(237, "<p align='center'><font color='#FFFFFF'><font size='40'>"..text[n].clickRunway2, n, 0, 350, 800, 60, 0x324650, 0x000000, 0.7, true)
			data[n].s=1
			ui.addTextArea(1000, "<p align='center'><b><a href='event:runway'><BV>"..text[n].runway.."</a></b>", n, 295, 3167, 95, 23, 0x000001, 0x000001, 1, false)
		end
		if a=="runway" and tfm.get.room.playerList[n].x<405 then
			tfm.exec.movePlayer(n,855,3284,false,0,0,false)
			ui.removeTextArea(237,n)
		elseif a=="plane1" then
			data[n].accelerationSpeed=3
			data[n].speedLim=250
			data[n].using="165d9c30648.png"
			tfm.exec.killPlayer(n)
		elseif a=="plane2" then
			data[n].speedLim=200
			data[n].accelerationSpeed=3
			data[n].using="165e4735f1f.png"
			tfm.exec.killPlayer(n)
		elseif a=="plane3" then
			data[n].using="165b9ec4f68.png"
			data[n].speedLim=500
			data[n].accelerationSpeed=6.5
			tfm.exec.killPlayer(n)
		elseif a=="plane4" then
			data[n].using="165c581475c.png"
			data[n].speedLim=225
			data[n].accelerationSpeed=3
			tfm.exec.killPlayer(n)
		elseif a=="plane5" then
			data[n].using="165dea1e6a2.png"
			data[n].speedLim=225
			data[n].accelerationSpeed=3
			tfm.exec.killPlayer(n)
		elseif a=="plane6" then
			data[n].using="165d9c2a1f9.png"
			data[n].speedLim=300
			data[n].accelerationSpeed=3.5
			tfm.exec.killPlayer(n)
		elseif a=="plane7" then
			data[n].using="165cfe74cca.png"
			data[n].speedLim=300
			data[n].accelerationSpeed=3.5
			tfm.exec.killPlayer(n)
		elseif a=="plane8" then
			data[n].using="165cfe53158.png"
			data[n].speedLim=300
			data[n].accelerationSpeed=3.5
			tfm.exec.killPlayer(n)
		elseif a=="plane9" then
			data[n].using="165d4a31588.png"
			data[n].speedLim=300
			data[n].accelerationSpeed=3.5
			tfm.exec.killPlayer(n)
		elseif a=="plane10" then
			data[n].using="165d4a2c6af.png"
			data[n].speedLim=550
			data[n].accelerationSpeed=5
			tfm.exec.killPlayer(n)
		elseif a=="plane11" then
			if n=="Blank#3495" or n=="Fuzzyfirsdog#0000" or n=="Bolodefchoco#0000" or n=="Sebafrancuz#0000" then
				data[n].using="165d4baf672.png"
				data[n].speedLim=900
				data[n].accelerationSpeed=8
				tfm.exec.killPlayer(n)
			end
		end
		if a=="close" then
			ui.removeTextArea(113,n)
			ui.removeTextArea(228,n)
		end
	end
	system.disableChatCommandDisplay("s",true)
	function eventChatCommand(n,m)
		if m=="s" and data[n].using=="165d4baf672.png" and data[n].stealth==0 then
			data[n].stealth=1
			tfm.exec.chatMessage("<ROSE>You are now on stealth.",n)
			ui.removeTextArea(data[n].radar.id/2.378,nil)
			ui.removeTextArea(data[n].radar.id,nil)
		elseif m=="s" and data[n].using=="165d4baf672.png" and data[n].stealth==1 then
			data[n].stealth=0
			tfm.exec.chatMessage("<R>You are now visible on the radar.",n)
		end
	end
	function eventPlayerLeft(n)
		data[n].inRoom=false
		ui.removeTextArea(data[n].radar.id,nil)
		ui.removeTextArea(data[n].radar.id/2.378,nil)
	end
end

modules.agario = function()
	tfm.exec.disableAutoShaman()
	tfm.exec.disableAutoNewGame()

	math.inSquare = function(x1,y1,r1,x2,y2,r2)
		return (x1 + r1 > x2 - r2 and x1 - r1 < x2 + r2) and (y1 + r1 > y2 - r2 and y1 - r1 < y2 + r2)
	end

	info = {}
	eventNewPlayer = function(n)
		tfm.exec.chatMessage("<J>Press <B>spacebar</B> to get the enemy and <B>B</B> for boost.", playerName)
		if not info[n] then
			info[n] = {
				n = n, 
				id = tfm.get.room.playerList[n].id,
				isDead = {false,0},
				capture = 0,
				size = 10,
				speed = 5,
				boost = {0,0,0}, -- speed, end, is boosting
				color = math.random(0xFFFFFF),
				coord = {math.random(800),math.random(400)},
				dir = {0,0},
			}
		end
		for k,v in next,{0,1,2,3,32,string.byte("POB",1,3)} do
			system.bindKeyboard(n,v,true,true)
			system.bindKeyboard(n,v,false,true)
		end
	end
	table.foreach(tfm.get.room.playerList,eventNewPlayer)

	eventNewGame = function()
		table.foreach(tfm.get.room.playerList,tfm.exec.killPlayer)
	end

	eventLoop = function(currentTime)
		_G.currentTime = currentTime
	end

	loop = function()
		if currentTime / 1000 > 3 then
			for k,v in next,info do
				if not v.isDead[1] then
					v.coord[1] = v.coord[1] + v.dir[1]
					v.coord[2] = v.coord[2] + v.dir[2]

					if v.coord[1] < 1 then
						v.coord[1] = 800
					elseif v.coord[1] > 800 then
						v.coord[1] = 1
					end

					if v.coord[2] < 1 then
						v.coord[2] = 400
					elseif v.coord[2] > 400 then
						v.coord[2] = 1
					end

					if v.boost[3] == 1 then
						if v.boost[2] > 0 then
							v.boost[2] = v.boost[2] - .5
						else
							v.boost[2] = 0
							v.boost[3] = 0
							v.speed = v.boost[1]
						end
					else
						if v.boost[2] < 4.5 then
							v.boost[2] = v.boost[2] + .05
						end
					end

					ui.addTextArea(v.id or 1000,"",nil,v.coord[1] or 0,v.coord[2] or 0,v.size or 10,v.size or 10,v.color or 1,v.color or 1,.5,true)
					ui.addTextArea(-v.id,"<font size='8' color='#E6FF00'>" .. v.n,nil,v.coord[1] - 10 - #v.n,v.coord[2] - (v.size/2) - 15,nil,nil,1,1,0,true)
					ui.addTextArea(0,string.format("X: %s\nY: %s\nSize: %sx%s\nSpeed: %s\nBoost: %s",v.coord[1],v.coord[2],v.size,v.size,v.speed,v.boost[2]),v.n,0,30,120,100,1,1,0,true)
				else
					if os.time() > v.isDead[2] then
						v.isDead = {false,0}
						v.coord = {math.random(800),math.random(400)}
					end
				end
			end
		end
	end
	system.looping(loop, 10)

	eventKeyboard = function(n,k,d)
		if k == 32 then
			if os.time() > info[n].capture then
				info[n].capture = os.time() + 5000

				for k,v in next,info do
					if n ~= v.n then
						if math.inSquare(v.coord[1],v.coord[2],v.size,info[n].coord[1],info[n].coord[2],info[n].size) then
							info[n].size = info[n].size + (10/100) * v.size
							info[n].speed = info[n].speed + (15/100) * v.speed
							ui.removeTextArea(v.id,nil)
							ui.removeTextArea(-v.id,nil)
							ui.removeTextArea(0,v.n)

							v.isDead = {true,os.time() + 10000}
							v.size = 10
							v.speed = 5

							info[n].capture = 0
							break
						end
					end
				end
			end
		elseif k == string.byte("B") then
			if info[n].boost[2] > .5 then
				info[n].boost[3] = d and 1 or 0
				if d then
					info[n].boost[1] = info[n].speed
					info[n].speed = info[n].speed * 2.5
				else
					info[n].speed = info[n].boost[1]
				end
			end
		elseif k == string.byte("P") then

		elseif k == string.byte("O") then
			ui.showColorPicker(0,n,info[n].color,"Square")
		else
			if k == 0 then
				info[n].dir = {-info[n].speed,0}
			elseif k == 2 then
				info[n].dir = {info[n].speed,0}
			elseif k == 1 then
				info[n].dir = {0,-info[n].speed}
			elseif k == 3 then
				info[n].dir = {0,info[n].speed}
			end
		end
	end

	eventColorPicked = function(i,n,c)
		info[n].color = c
	end

	tfm.exec.newGame('<C><P /><Z><S /><D /><O /></Z></C>')
end

modules.bolo = function()
	table.foreach({"AutoNewGame","AutoTimeLeft","AutoShaman"},function(_,f) tfm.exec["disable"..f]() end)
	tfm.exec.newGame(math.random(100))
	mice = {}
	respawnPoint = {}
	transform = function(n,x,y)
		if not mice[n] then
			mice[n] = {
				id = nil,
				object = 104,
				currentTitle = "Little Cake",
				move = {-40,-30,40,30}
			}
		end
		if respawnPoint[1] == nil and (T and T/1000 > 1) then respawnPoint = {(not tfm.get.room.playerList[n].isDead and tfm.get.room.playerList[n].x or 400),(not tfm.get.room.playerList[n].isDead and tfm.get.room.playerList[n].y or 100)} end
		x = (not x and (tfm.get.room.playerList[n].isDead and l.x or tfm.get.room.playerList[n].x) or x or 400)
		y = (not y and (tfm.get.room.playerList[n].isDead and l.y or tfm.get.room.playerList[n].y) or y or 200)
		local vx,vy = tfm.get.room.playerList[n].isDead and l.vx or 0,tfm.get.room.playerList[n].isDead and l.vy or 0
		tfm.exec.killPlayer(n)
		mice[n].id = tfm.exec.addShamanObject(mice[n].object,x,y,ANGLE,vx,vy)
		ui.addTextArea(mice[n].id,"<p align='center'><font color='#FFF3CE' size='12' face='verdana'>"..n.."\n<font size='11'>«"..mice[n].currentTitle.."»",nil,x-43,y-67,nil,30,1,1,.4,true)
	end

	eventNewGame=function()
		respawnPoint = {}
		table.foreach(tfm.get.room.playerList,tfm.exec.respawnPlayer)
		don = false
	end

	eventNewPlayer=function(n)
		eventLoop(1,1)
		transform(n)
	end

	eventKeyboard=table.foreach({0,1,2,3,32},function(_,k) table.foreach(tfm.get.room.playerList,function(n) system.bindKeyboard(n,k,true,true) end) end) or function(n,k)
		if mice[n] then
			if k < 5 then
				k = k + 1
				if k == 1 or k == 3 then
					tfm.exec.moveObject(mice[n].id,0,0,true,mice[n].move[k])
				end
				if k == 2 or k == 4 then
					tfm.exec.moveObject(mice[n].id,0,0,false,0,mice[n].move[k],true)
				end
			end
			if k == 32 then
				tfm.exec.removeObject(mice[n].id)
				ui.removeTextArea(mice[n].id,nil)
				if mice[n].object == 104 then
					mice[n].object = 207
					mice[n].currentTitle = "Big Cake"
				else
					mice[n].object = 104
					mice[n].currentTitle = "Little Cake"
				end
				transform(n)
			end
			eventLoop(1,1)
		end
	end

	local don = false
	eventLoop=function(T,R,REVIVE) _G.T = T;_G.R = R
		if T>1000 then
			if not don then
				if math.floor(T/1000) == 2 then
					don = true
					table.foreach(tfm.get.room.playerList,function(n)
						tfm.exec.respawnPlayer(n);if mice[n] then ui.removeTextArea(mice[n].id,nil) end;transform(n)
					end)
				end
			end
			for n,v in next,mice do
				if tfm.get.room.playerList[n] then
					l = tfm.get.room.objectList[mice[n].id]
					ANGLE = l.angle or 0
					ui.addTextArea(mice[n].id,"<p align='center'><font color='#FFF3CE' size='12' face='verdana'>"..n.."\n<font size='11'>«"..mice[n].currentTitle.."»",nil,l.x-43,l.y-67,nil,30,1,1,.4,true)
					if l.x > 820 or l.x < -30 or l.y > 390 or REVIVE then
						tfm.exec.removeObject(mice[n].id)
						ui.removeTextArea(mice[n].id,nil)
						transform(n,respawnPoint[1],respawnPoint[2])
					end
				end
			end
		end
	end

	eventChatCommand=function(n,c)
		local p = {}
		for v in c:gmatch('[^%s]+') do
			table.insert(p,v)
		end
		p[1]=p[1]:lower()
		if p[1] == "move" then
			if p[2] and p[3] then
				mice[n].move[tonumber(p[2])] = tonumber(p[3])
			else
				mice[n].move = {-40,-30,40,30}
			end
		end
		if p[1] == "r" then
			eventLoop(T,R,true)
		end
	end
end

modules.outfit = function()
	local shop = {
		-- id, name, cheese, fraise
		head = {
			[0] = { "-", 0, 0 },
			[1] = { "Chapéu de Helicóptero", 500, 0 },
			[2] = { "Chapéu de Palha", 200, 0 },
			[3] = { "Capacete branco", 20, 0 },
			[4] = { "Cartola", 200, 0 },
			[5] = { "Chapéu de Sol", 100, 0 },
			[6] = { "Chapéu Fedora", 500, 0 },
			[7] = { "Capacete de Soldado", 200, 0 },
			[8] = { "Capacete de Mineirador", 300, 0 },
			[9] = { "Chapéu de General", 500, 0 },
			[10] = { "Boina", 100, 0 },
			[11] = { "Bandana do Naruto", 500, 0 },
			[12] = { "Chifres", 200, 0 },
			[13] = { "Aureola", 500, 0 },
			[14] = { "Capacete Vicking", 300, 0 },
			[15] = { "Máscara de Ladrão", 200, 0 },
			[16] = { "Chapéu de Pirata", 300, 0 },
			[17] = { "Chapéu de Bruxa", 200, 0 },
			[18] = { "Chapéu Coco", 300, 0 },
			[19] = { "Chapéu de Enfermeira", 300, 0 },
			[20] = { "Chapéu Policial", 500, 0 },
			[21] = { "Gorro Natalino", 20, 0 },
			[22] = { "Chapéu de Cozinheiro", 300, 0 },
			[23] = { "Tiara de Coelinho", 400, 0 },
			[24] = { "Chapéu de pelo Russo", 50, 0 },
			[25] = { "Chapéu de Cowboy", 250, 0 },
			[26] = { "Chapéu de Limão", 300, 0 },
			[27] = { "Chapéu Mandarim", 800, 0 },
			[28] = { "Rabo de cavalo", 300, 0 },
			[29] = { "Chapéu do Tio Sam", 500, 50 },
			[30] = { "Cabelo da Marge Simpson", 200, 0 },
			[31] = { "Chapéu do Mario", 300, 0 },
			[32] = { "Cabelo do Goku", 800, 0 },
			[33] = { "Chapéu de Aniversário", 150, 0 },
			[34] = { "Chapéu do Asterix", 400, 0 },
			[35] = { "Coroa", 1000, 0 },
			[36] = { "Dreads", 500, 0 },
			[37] = { "Blackpower", 200, 0 },
			[38] = { "Capacete do Faraó", 800, 80 },
			[39] = { "Abóbora", 400, 40 },
			[40] = { "Caveira com cabelo", 800, 100 },
			[41] = { "Chifre de Rena", 0, 50 },
			[42] = { "Boneco de Neve no Rosto", 0, 50 },
			[43] = { "Cabelo Loiro", 200, 0 },
			[44] = { "Chapéu com flores", 250, 0 },
			[45] = { "Cabelo do Elvis Presley", 300, 0 },
			[46] = { "Chapéu Chinês", 100, 0 },
			[47] = { "Cocar", 1500, 0 },
			[48] = { "Chapéu verde de praia", 300, 0 },
			[49] = { "Chapéu de Bobo da Corte", 500, 0 },
			[50] = { "Chapéu do Deadmau5", 400, 0 },
			[51] = { "Chapéu de Aviador", 200, 0 },
			[52] = { "Capacete Mega Man", 400, 40 },
			[53] = { "Chapéu do Viewtiful Joe", 400, 0 },
			[54] = { "Ovo", 50, 0 },
			[55] = { "Cartola de Doende", 100, 0 },
			[56] = { "Chapéu de Peixe", 400, 40 },
			[57] = { "Chapéu de Gato", 400, 40 },
			[58] = { "Aquário", 400, 40 },
			[59] = { "Laço de presente", 300, 60 },
			[60] = { "Cesta de Ovos", 400, 40 },
			[61] = { "Cabelo laranja", 200, 0 },
			[62] = { "Chapéu do Luffy", 300, 40 },
			[63] = { "Cabelo do Sonic", 350, 0 },
			[64] = { "Chapéu Turco", 300, 0 },
			[65] = { "Cabelo Moreno", 200, 40 },
			[66] = { "Chapéu do Link", 300, 40 },
			[67] = { "Tubarão", 400, 40 },
			[68] = { "Crina da Rainbow Dash", 200, 0 },
			[69] = { "Crina da Twillight Sparkle", 200, 0 },
			[70] = { "Crina da AppleJack", 200, 0 },
			[71] = { "Crina da Pinkie Pie", 200, 0 },
			[72] = { "Crina da Rarity", 200, 0 },
			[73] = { "Crina da Fluttershy", 200, 0 },
			[74] = { "Chapéu de Esquimó", 150, 40 },
			[75] = { "Chapéu de Caçador", 50, 0 },
			[76] = { "Sacola de papel", 200, 40 },
			[77] = { "Chapéu Mexicano", 250, 40 },
			[78] = { "Chapéu do Ash Ketchum", 300, 40 },
			[79] = { "Gorro de dormir", 250, 40 },
			[80] = { "Faca sangrenta", 500, 50 },
			[81] = { "Lençol fantasma", 450, 40 },
			[82] = { "Chapéu vampiro", 300, 40 },
			[84] = { "Árvore Natalina", 0, 40 },
			[85] = { "Meia natalina", 0, 40 },
			[86] = { "Turbante", 300, 40 },
			[86] = { "Chapéu contest Krissim", 1000, 100 },
			[87] = { "Máscara de carnaval", 800, 100 },
			[88] = { "Penacho de Palha", 200, 40 },
			[89] = { "Chapéu de pescador", 400, 40 },
			[90] = { "Varinha com queijo", 400, 40 },
			[91] = { "Concha", 400, 40 },
			[92] = { "Chapéu de capitão", 400, 40 },
			[93] = { "Chapéu de marinheiro", 400, 40 },
			[94] = { "Galinha", 700, 40 },
			[96] = { "Cabelo do Shadow", 400, 40 },
			[97] = { "Bolas de sorvete", 400, 40 },
			[98] = { "Juba de Leão", 400, 40 },
			[99] = { "Coroa de Princesa", 200, 40 },
			[101] = { "Chapéu Hokage", 300, 40 },
			[102] = { "Capacete Gladiador", 500, 40 },
			[103] = { "Chapéu de Velas", 250, 40 },
			[104] = { "Cubo de Gelo", 600, 60 },
			[105] = { "Chapéu de Sapo", 100, 10 },
			[106] = { "Chapéu de Pintinho", 300, 20 },
			[107] = { "Chapéu de Panda", 400, 50 },
			[108] = { "Chapéu de Formatura", 500, 40 },
			[109] = { "Côco", 400, 40 },
			[110] = { "Cueca", 20, 0 },
			[111] = { "Chifres de Bóde", 400, 40 },
			[112] = { "Coroa de Folhas", 200, 30 },
			[113] = { "Touca de frio", 300, 40 },
			[114] = { "Ursinho de pelúcia", 800, 100 },
			[115] = { "Chapéu de fitas", 250, 40 },
			[116] = { "Penteado laranja", 600, 40 },
			[117] = { "Sino com asas", 500, 10 },
			[118] = { "Capacete Egípcio", 600, 60 },
			[119] = { "Polvo", 350, 40 },
			[120] = { "Livro aberto", 400, 60 },
			[121] = { "Aranha", 600, 70 },
			[122] = { "Chifre de unicórnio", 250, 40 },
			[123] = { "Capuz", 2000, 100 },
			[124] = { "Cupcake", 500, 50 },
			[125] = { "Gorro do Bob Marley", 1600, 100 },
			[126] = { "Banana", 450, 40 },
			[127] = { "Lenço vermelho", 350, 40 },
			[128] = { "Laço vermelho", 200, 40 },
			[129] = { "Vassoura", 400, 40 },
			[130] = { "Gorro de frio", 400, 40 },
			[131] = { "Queijo", 350, 40 },
			[132] = { "Coroa do Rei", 1000, 100 },
			[133] = { "Chapéu arco-íris", 1200, 100 },
			[134] = { "Chapéu da Marmota", 400, 40 },
			[135] = { "Chapéu do ano novo chinês", 350, 40 },
			[136] = { "Concha de Caracol", 350, 40 },
			[137] = { "Chapéu de Padeiro", 200, 40 },
			[138] = { "Cocar Shaman", 3001, 0 },
			[139] = { "Carcaça de Dinossauro", 1200, 120 },
			[140] = { "Capacete de Astronauta", 1000, 100 },
			[141] = { "Boné da Moranguinho", 800, 80 },
			[142] = { "Capacete Futebol Americano", 500, 40 },
			[143] = { "Boné azul", 300, 40 },
			[144] = { "Gorro da Mamãe Noel", 800, 40 },
			[145] = { "Chapéu Abacaxi", 600, 50 },
			[146] = { "Desentupidor", 250, 40 },
			[147] = { "Castelo de Areia", 250, 40 },
			[148] = { "Chapéu de Peixe Vermelho", 800, 80 },
			[149] = { "Chapéu de Construtor", 300, 40 },
		};
		eye = {
			[0] = { "-", 0, 0 },
			[1] = { "Óculos de Sol", 200, 0 },
			[2] = { "Óculos de Coração", 200, 0 },
			[3] = { "Óculos de SOl mal", 200, 0 },
			[4] = { "Monóculo", 200, 0 },
			[5] = { "Tapa-olho", 300, 0 },
			[6] = { "Máscara de Mergulhador", 800, 0 },
			[7] = { "Óculos 3D", 50, 0 },
			[8] = { "Óculos", 50, 0 },
			[9] = { "Cílios Femininos", 20, 0 },
			[10] = { "Óculos de Listras", 100, 0 },
			[11] = { "Máscara do Ciclope", 200, 0 },
			[12] = { "Máscara Kitsune", 400, 40 },
			[13] = { "Nariz de batata", 0, 0 },
			[14] = { "Máscara de carnaval", 100, 20 },
			[15] = { "Chapéu de Creeper", 400, 40 },
			[16] = { "Bandana Japan Expo", 0, 0 },
			[17] = { "Máscara de Múmia", 500, 50 },
			[18] = { "Olhos com mola", 500, 50 },
			[19] = { "Caveira", 500, 50 },
			[20] = { "Óculos Nerd", 250, 40 },
			[21] = { "Caveira de Dinossauro", 1000, 100 },
			[22] = { "Tapa-olho branco", 250, 40 },
			[23] = { "Protetor de Ski", 500, 50 },
			[24] = { "Óculos de aviador", 450, 40 },
			[25] = { "Arranhão", 300, 40 },
			[26] = { "Máscara do Jason", 700, 60 },
		};
		ear = {
			[0] = { "-", 0, 0 },
			[1] = { "Laço rosa", 100, 0 },
			[2] = { "Aranha", 200, 40 },
			[3] = { "Brinco bola de natal", 0, 10 },
			[4] = { "Flor", 20, 0 },
			[5] = { "Fone de ouvidos", 300, 0 },
			[6] = { "Brinco de coração", 150, 40 },
			[7] = { "Brinco Poisson", 400, 40 },
			[8] = { "Brinco Estrela do Mar", 400, 40 },
			[9] = { "Queijo", 0, 0 },
			[10] = { "Item 4001", 4001, 0 },
			[11] = { "Tapa-ouvidos natalinos", 500, 50 },
			[12] = { "Brinco pirulito de natal", 0, 20 },
			[13] = { "Tiara de Rosa", 200, 40 },
			[14] = { "Tiara de Coelho", 200, 0 },
			[15] = { "Máscara contest Holldine", 600, 60 },
			[16] = { "Brinco patinho", 250, 30 },
			[17] = { "Óculos de espião", 400, 40 },
			[18] = { "Brinco duplo", 40, 0 },
			[19] = { "Laço branco", 100, 10 },
			[20] = { "Parafusos", 250, 40 },
			[21] = { "Flecha", 300, 40 },
			[22] = { "Brinco de caveira", 400, 40 },
			[23] = { "Cristais de gelo", 50, 10 },
			[24] = { "Brinco de coelho", 200, 30 },
			[25] = { "Flor do Havaí", 600, 100 },
			[26] = { "Visco", 400, 40 },
			[27] = { "Espinhos de ferro", 1000, 80 },
			[28] = { "Laço de coração", 600, 40 },
			[29] = { "Brinco de carnaval", 400, 40 },
			[30] = { "Brinco da Deusa Shaman", 250, 40 },
			[31] = { "Ferradura de Ouro", 400, 400 },
			[32] = { "Laço com Sino", 250, 30 },
			[33] = { "Laço de bolinhas de natal", 200, 40 },
			[34] = { "Laço com penas", 600, 80 },
			[35] = { "Laço de Borboleta", 600, 80 },
			[36] = { "Touca-rato de inverno", 500, 80 },
			[37] = { "Brinco de sino dourado", 100, 20 },
		};
		mouth = {
			[0] = { "-", 0, 0 },
			[1] = { "Bigode", 100, 0 },
			[2] = { "Trigo", 25, 0 },
			[3] = { "Gravata borboleta", 150, 0 },
			[4] = { "Charuto", 400, 0 },
			[5] = { "Rosa", 300, 0 },
			[6] = { "Sabre de Luz verde", 300, 0 },
			[7] = { "Sabre de Luz vermelho", 300, 0 },
			[8] = { "Facão", 400, 0 },
			[9] = { "Máscara de Gás", 400, 0 },
			[10] = { "Trevo de 4 folhas", 20, 0 },
			[11] = { "Esqueleto de peixe", 400, 40 },
			[12] = { "Chupeta", 150, 20 },
			[13] = { "Pirulito", 150, 25 },
			[14] = { "Máscara de cirurgião", 50, 0 },
			[15] = { "Abóbora", 250, 40 },
			[16] = { "Nariz de palhaço", 50, 10 },
			[17] = { "Dentes grandes", 40, 20 },
			[18] = { "Picareta Minecraft", 400, 40 },
			[19] = { "Morango", 0, 0 },
			[20] = { "Pincel", 20, 0 },
			[21] = { "Sorvete francês", 60, 0 },
			[22] = { "Osso", 100, 10 },
			[23] = { "Rosquinha", 100, 10 },
			[24] = { "Dentes de vampiro", 200, 40 },
			[25] = { "Chocolate", 100, 20 },
			[26] = { "Biscoito", 100, 20 },
			[27] = { "Caixa de chocolate", 150, 40 },
			[29] = { "Cenoura", 50, 0 },
			[30] = { "Pretzel", 200, 40 },
			[31] = { "Bambu", 200, 40 },
			[32] = { "Leque Japan Expo", 0, 0 },
			[33] = { "Diploma", 300, 40 },
			[34] = { "Sardinha", 400, 40 },
			[35] = { "Bala", 100, 20 },
			[36] = { "Bengala natalino", 200, 40 },
			[37] = { "Cookie", 300, 40 },
			[38] = { "Maçã do amor", 200, 40 },
			[39] = { "Pandeiro", 50, 8 },
			[40] = { "Bico de galinha", 300, 60 },
			[41] = { "Apito", 300, 40 },
			[42] = { "Mochila", 400, 50 },
			[43] = { "Bigode branco", 400, 40 },
			[44] = { "Fondie", 250, 40 },
			[45] = { "Língua", 100, 40 },
			[46] = { "Sanduíche", 500, 50 },
			[47] = { "Corneta", 350, 50 },
			[48] = { "Mamadeira", 250, 40 },
			[49] = { "Pedaço de Torta", 150, 40 },
			[50] = { "Bule", 300, 400 },
			[51] = { "Carta do Dia dos Namorados", 250, 40 },
			[52] = { "Espátula de bolo", 250, 40 },
			[53] = { "Bandeira de Racing", 600, 40 },
			[54] = { "Martelo da Idade Média", 600, 60 },
			[55] = { "Caixa de presente", 400, 50 },
			[56] = { "Shuriken", 450, 50 },
			[57] = { "Barba negra", 400, 40 },
			[58] = { "Regador", 350, 40 },
			[59] = { "Baguete", 250, 40 },
			[60] = { "Coxa de frango", 150, 40 },
			[61] = { "Balde de areia", 300, 40 },
			[62] = { "Martelo", 250, 40 },
			[63] = { "Motoserra", 400, 40 },
		};
		neck = {
			[0] = { "-", 0, 0 },
			[1] = { "Cachecol francês", 200, 0 },
			[2] = { "Lenço vermelho", 200, 0 },
			[3] = { "Barba", 60, 20 },
			[4] = { "Colar de flores", 50, 0 },
			[5] = { "Gravata preta", 80, 0 },
			[6] = { "Cachecol verde", 50, 0 },
			[7] = { "Sino", 150, 20 },
			[8] = { "Barril de bebida", 100, 20 },
			[9] = { "Cachecol de Halloween", 150, 40 },
			[10] = { "Grinalda vermelha", 0, 20 },
			[11] = { "Gravata Borboleta", 200, 20 },
			[12] = { "Guarda-sol", 300, 40 },
			[13] = { "Máquina Fotográfica", 400, 40 },
			[14] = { "Gravata laranja", 200, 40 },
			[15] = { "Medalha", 150, 25 },
			[16] = { "Amuleto turco", 200, 40 },
			[17] = { "Gargantilha", 150, 40 },
			[18] = { "Fone de ouvido", 450, 40 },
			[19] = { "Ombreira", 500, 50 },
			[20] = { "Estrela de Sheriff", 200, 40 },
			[21] = { "Blusa laranja amarrada", 150, 40 },
			[22] = { "Arco-íris", 350, 40 },
			[23] = { "Chapéu de Vitória Régia", 600, 50 },
			[24] = { "Cachecol da Bandeira de Racing", 450, 40 },
			[25] = { "Laço da chapeuzinho vermelho", 150, 35 },
			[26] = { "Lenço ninja", 600, 60 },
			[27] = { "Colar de flores havaianas", 250, 40 },
			[28] = { "Colar de Folhas", 150, 40 },
			[29] = { "Foice", 600, 50 },
			[30] = { "Bóia salva-vidas", 300, 40 },
			[31] = { "Babador de Palhaço", 300, 40 },
			[32] = { "Guirlanda", 250, 40 },
			[33] = { "Doce de canudo", 800, 100 },
		};
		hair_style = {
			[0] = { "-", 0, 0 },
			[1] = { "Cabelo punk", 400, 40 },
			[2] = { "Cabelo bagunçado", 400, 40 },
			[3] = { "Cabelo loiro", 400, 40 },
			[4] = { "Franja marrom", 400, 40 },
			[5] = { "Franja azul", 400, 40 },
			[6] = { "Crina da Apple Bloom", 300, 40 },
			[7] = { "Crina da Scootaloo", 300, 40 },
			[8] = { "Crina da Sweetie Belle", 300, 40 },
			[9] = { "Cabelo Hatsune Miku", 400, 40 },
			[10] = { "Cabelo do Kagamine Rin", 200, 40 },
			[11] = { "Cabelo loiro de franja", 400, 40 },
			[12] = { "Cabelo masculino de periquito", 400, 40 },
			[13] = { "Cabelo da princesa Brave", 400, 40 },
			[14] = { "Blackpower estiloso", 150, 15 },
			[15] = { "Cabelo da Emília", 250, 40 },
			[16] = { "Penteado do Levi", 250, 40 },
			[17] = { "Cabelo da Elsa", 1000, 100 },
			[18] = { "Bobs da Dona Florinda", 200, 40 },
			[19] = { "Cabelo da Deusa Shaman", 1000, 110 },
			[20] = { "Cabelo Dragon Ball", 800, 80 },
			[21] = { "Touca rosa de gato", 900, 100 },
			[22] = { "Coque", 300, 40 },
			[23] = { "Cabelo do IT", 300, 40 },
			[25] = { "Cabelo ajeitado", 250, 40 },
			[26] = { "Cabelo comprido com trança", 400, 60 },
			[28] = { "Cabelo do Arnold", 300, 50 }, -- Checar preço
			[33] = { "Penteado Poney com Coroa", 400, 50 },
			[34] = { "Penteado Poney", 400, 50 },
		};
		tail = {
			[0] = { "-", 0, 0 },
			[1] = { "Diamante", 2000, 200 },
			[2] = { "Estrela", 0, 80 },
			[3] = { "Laço rosa", 1000, 100 },
			[4] = { "Coração", 1000, 150 },
			[5] = { "Ovo de páscoa", 1000, 80 },
			[6] = { "Sol", 1500, 150 },
			[7] = { "Lua", 1500, 150 },
			[8] = { "Círculos dourados", 2000, 200 },
			[9] = { "Abóbora", 1000, 100 },
			[10] = { "Sino", 800, 80 },
			[11] = { "Anéis", 1500, 150 },
			[12] = { "Trevo de 4 folhas", 800, 80 },
			[13] = { "Berimbau", 900, 100 },
			[14] = { "Capacete Egípcio", 1000, 100 },
			[15] = { "Bola de futebol", 1500, 150 },
			[16] = { "Concha", 1000, 100 },
			[17] = { "Meia", 0, 80 },
			[18] = { "Ursinho de Goma", 1000, 100 },
			[19] = { "Penas Shaman", 3000, 200 },
			[20] = { "Nota musical", 1000, 80 },
			[21] = { "Morcego", 1500, 150 },
			[22] = { "Lamparina Chinesa", 1500, 100 },
			[23] = { "Penas de Carnaval", 1500, 120 },
			[24] = { "Anvil God", 1200, 120 },
			[25] = { "Pote de Ouro", 1000, 100 },
			[26] = { "Asa shaman", 1200, 100 },
			[27] = { "Balão", 1000, 120 },
			[28] = { "Planeta", 1000, 80 },
			[29] = { "Bola de Demolição", 800, 80 },
			[30] = { "Rato e Terra", 900, 100 },
		};
		contact_lens = {
			[0] = { "-", 0, 0 },
			[1] = { "Olho laranja", 150, 40 },
			[2] = { "Olho azul", 100, 30 },
			[3] = { "Olho rosa", 150, 40 },
			[4] = { "Olho verde", 100, 30 },
			[5] = { "Olho raivoso vermelho", 250, 100 },
			[6] = { "Olho raivoso verde", 200, 80 },
			[7] = { "Olho raivoso azul", 200, 80 },
			[8] = { "Olho raivoso amarelo", 250, 100 },
			[10] = { "Olho cílios horizontais", 200, 80 },
			[11] = { "Olho azul vidrado", 170, 60 },
			[12] = { "Olho cílios verticais", 120, 30 },
			[13] = { "Olho fechado", 160, 50 },
		},
		hand = {
			[0] = { "-", 0, 0 },
			[1] = { "Nabo", 1200, 200 },
			[2] = { "Pulseira", 1100, 180 },
		},
		fur = {
			[-7] = { "Cor preta", 3000, 150 },
			[-6] = { "Cor amarelo-dourada", 3000, 150 },
			[-5] = { "Cor cinza escuro", 3000, 150 },
			[-4] = { "Cor branca", 3000, 150 },
			[-3] = { "Cor cinza claro", 3000, 150 },
			[-2] = { "Cor marrom", 1000, 50 },
			[-1] = { "Cor bege", 1000, 50 },
			[1] = { "Padrão", 0, 0 },
			[2] = { "Pelo de Porquinho-da-índia", 6000, 300 },
			[3] = { "Pelo de Gato Siamês", 6000, 300 },
			[4] = { "Pelo Acinzentado", 6000, 300 },
			[5] = { "Pelo Branco e marrom com listras pretas", 6000, 300 },
			[6] = { "Pelo de Hamster", 6000, 300 },
			[7] = { "Pelo de Gambá", 8000, 350 },
			[8] = { "Pelo de Tigre", 10000, 400 },
			[9] = { "Pelo de Raposa", 7000, 400 },
			[10] = { "Pelo de Esqueleto", 0, 0 },
			[11] = { "Pelo contest Sovenasark", 7000, 350 },
			[12] = { "Pelo contest Kreature", 6500, 325 },
			[13] = { "Pelo contest Squeakymaus", 6000, 300 },
			[14] = { "Pelo de Rato-das-neves", 6000, 300 },
			[15] = { "Pelo de Panda vermelho", 6000, 400 },
			[16] = { "Pelo de Coelho", 0, 0 },
			[17] = { "Pelo de Zebra", 6000, 300 },
			[18] = { "Pelo de Panda", 6000, 400 },
			[19] = { "Pelo Lunar", 7000, 400 },
			[20] = { "Pelo Solar", 7000, 400 },
			[21] = { "Pelo de Leopardo", 6000, 300 },
			[22] = { "Pelo Selvagem", 6000, 300 },
			[23] = { "Pelo Preto de Tattoo de Esqueleto", 7000, 500 },
			[24] = { "Pelo de Leopardo-das-neves", 7000, 500 },
			[25] = { "Pelo de Coração", 5000, 360 },
			[26] = { "Pelo da Sorte", 7000, 400 },
			[27] = { "Pelo de Tucano", 6000, 350 },
			[28] = { "Pelo de Páscoa", 8000, 450 },
			[29] = { "Pelo Egípcio", 7000, 400 },
			[30] = { "Pelo da Copa 2015", 7000, 400 },
			[31] = { "Pelo de Girafa", 6000, 350 },
			[32] = { "Pelo de Nuvem", 7000, 400 },
			[34] = { "Pelo de Rottweiler", 6000, 350 },
			[36] = { "Pelo de Lêmure", 7000, 400 },
			[38] = { "Pelo de Lêmure-de-cauda-anelada", 6000, 350 },
			[39] = { "Pelo rosa de Cupido", 7000, 400 },
			[41] = { "Pelo de Gazela", 6000, 350 },
			[43] = { "Pelo de Ovo de Páscoa", 7000, 400 },
			[44] = { "Pelo de Pinguim", 6500, 400 },
			[46] = { "Pelo de Música", 7500, 450 },
			[47] = { "Pelo do Tails", 7000, 375 },
			[48] = { "Pelo de Volta às aulas", 8000, 500 },
			[49] = { "Pelo de Vaca", 6000, 400 },
			[50] = { "Pelo de Drácula", 7000, 450 },
			[52] = { "Pelo de Múmia", 8000, 500 },
			[53] = { "Pelo de Esquimó", 6000, 400 },
			[54] = { "Pelo do Hantaro", 7000, 350 },
			[55] = { "Pelo Real", 6500, 400 },
			[56] = { "Pelo de Unicórnio", 7000, 400 },
			[57] = { "Pelo de Dragão", 5500, 350 },
			[58] = { "Pelo do Dia dos Namorados", 7000, 400 },
			[59] = { "Pelo de Carnaval", 7000, 400 },
			[60] = { "Pelo da Água", 6500, 350 },
			[61] = { "Pelo da Deusa Shaman", 8500, 550 },
			[62] = { "Pelo de Irlandês (St.Patrik)", 6500, 350 },
			[63] = { "Pelo de Cozinheiro", 6000, 380 },
			[64] = { "Pelo de Esquilo", 6000, 350 },
			[65] = { "Pelo da Chapeuzinho Vermelho", 5000, 300 },
			[67] = { "Pelo do Vento", 5000, 0 },
			[68] = { "Pelo de Grama", 10000, 0 },
			[69] = { "Pelo de Fogo", 15000, 0 },
			[70] = { "Pelo de Gato", 6000, 350 },
			[71] = { "Pelo Militar", 6000, 350 },
			[72] = { "Pelo de Tubarão", 7000, 400 },
			[73] = { "Pelo Espacial", 7500, 430 },
			[74] = { "Pelo de Joaninha", 6000, 350 },
			[76] = { "Pelo das Olimpíadas", 6000, 400 },
			[77] = { "Pelo de Orca", 6000, 450 },
			[78] = { "Pelo de Lobisomem", 8000, 450 },
			[79] = { "Pelo de Ceifador", 5500, 350 },
			[80] = { "Pelo de Biscoito de Gengibre", 6000, 400 },
			[81] = { "Pelo do Totoro", 7000, 500 },
			[82] = { "Pelo do Tigre Escuro", 6000, 450 },
			[83] = { "Pelo de Morango", 5500, 350 },
			[84] = { "Pelo de Peixe Dourado", 6000, 400 },
			[85] = { "Pelo de Calopsita", 5500, 400 },
			[86] = { "Pelo de Arara Azul", 5500, 400 },
			[87] = { "Pelo da Dory", 5500, 350 },
			[88] = { "Pelo de Lily", 6000, 400 },
			[89] = { "Pelo de Coruja da noite", 5500, 400 },
			[90] = { "Pelo de Elefante", 5500, 400 },
			[91] = { "Pelo de Pato", 5500, 400 },
			[92] = { "Pelo de Yeti", 6000, 400 },
			[93] = { "Pelo de Coruja", 5500, 400 },
			[94] = { "Pelo de Mordecai", 5500, 400 },
			[95] = { "Pelo de Eevee", 5500, 500 },
			[96] = { "Pelo de Carneiro", 5500, 400 },
			[97] = { "Pelo de Gambá claro", 5500, 400 },
		};
	}

	local getLook = function(player)
		local look = tfm.get.room.playerList[player].look

		local fur, items = look:match("(%d+)(.+)")

		local out = { tonumber(fur) }

		for item, colors in items:gmatch("[;,](%d+)([_+%x]*)") do
			local tmp = { id = tonumber(item), colors = { } }

			for c in colors:gmatch("[_+](%x+)") do
				tmp.colors[#tmp.colors + 1] = c
			end
			out[#out + 1] = tmp
		end

		return out
	end

	local displayLook = function(p, n)
		local look = getLook(p)

		local info = {
			[1] = { "Pelo", shop.fur[look[1]] },
			[2] = { "Cabeça", shop.head[look[2].id] },
			[3] = { "Olho", shop.eye[look[3].id] },
			[4] = { "Orelha", shop.ear[look[4].id] },
			[5] = { "Boca", shop.mouth[look[5].id] },
			[6] = { "Pescoço", shop.neck[look[6].id] },
			[7] = { "Penteado", shop.hair_style[look[7].id] },
			[8] = { "Rabo", shop.tail[look[8].id] },
			[9] = { "Lente de contato", shop.contact_lens[look[9].id] },
			[10] = { "Mão", shop.hand[look[10].id] },
		}

		local price = {
			cheese = { 0, 0 },
			fraise = { 0, 0 }
		}

		local y = 0
		for i = 1, 10 do
			if (type(look[i]) == "number" and look[i] or look[i].id) > 0 and info[i][2] then
				local colors = {}
				if type(look[i]) == "table" then
					for c = 1, #look[i].colors do
						colors[#colors + 1] = "<font color='#" .. look[i].colors[c] .. "'>#" .. look[i].colors[c] .. "</font>"
					end
				end

				if info[2][2] == 0 then
					price.cheese[2] = price.cheese[2] + info[i][2][3]
					price.fraise[1] = price.fraise[1] + info[i][2][3]
				else
					price.cheese[1] = price.cheese[1] + info[i][2][2]

					if info[2][3] == 0 then
						price.fraise[2] = price.fraise[2] + info[i][2][2]
					else
						price.fraise[1] = price.fraise[1] + info[i][2][3]
					end
				end

				if #colors > 0 then
					price.cheese[2] = price.cheese[2] + 20
					price.fraise[1] = price.fraise[1] + 20
				end

				local c = table.concat(colors, " - ")
				ui.addTextArea(i, info[i][1] .. " : <V>" .. info[i][2][1] .. "<N>\n<a href='event:'>" .. c, n, 300, 20 + 48 * y, 150, 40, 1, 1, 1, true)
				y = y + 1
			end
		end

		ui.addTextArea(11, "Preço em queijo: <J><B>$" .. price.cheese[1] .. "</B>" .. (price.cheese[2] > 0 and (" <N>+ <R><B>$" .. price.cheese[2] .. "</B>") or "") .. "\n<N>Preço em morango: <R><B>$" .. price.fraise[1] .. "</B>" .. (price.fraise[2] > 0 and (" <N>+ <J><B>$" .. price.fraise[2] .. "</B>") or "") , n, 5, 30, 150, 150, 1, 1, 1, true)
	end

	eventChatCommand = function(n, c)
		c = c:lower():gsub("%a", string.upper, 1)
		if not c:find("#") then c = c .. "#0000" end

		if tfm.get.room.playerList[c] then
			displayLook(c, n)
		end
	end

	eventNewPlayer = function(playerName)
		tfm.exec.chatMessage("<ROSE>Seu look é <B>" .. tfm.get.room.playerList[playerName].look .. "</B>", playerName)
		tfm.exec.chatMessage("<J>Digite !nomeDoJogador para ver o preço do visual.", playerName)
	end
	table.foreach(tfm.get.room.playerList, eventNewPlayer)
end

modules.mess = function()
	table.encrypt=function(a,b,c)if not a or not b or not c or type(a)~='table'or b==''or c==''then return end;local d,e,f,g='','','',''for h in b:gmatch('.')do d=d..h:byte()end;for h in c:gmatch('.')do g=g..h:byte()end;math.randomseed(d)otherSeed=math.random(1000000)local i=pcall(function()for h,j in next,a do if type(j)=='string'or type(j)=='number'then if type(j)=='string'then j=[[']]..j..[[']]end;f=f..' '..j..' '..h:upper()else return end end;f=f..' '..g;for h in f:gmatch('.')do local k=h:byte()+68+math.random(5)otherSeed=otherSeed+h:byte()math.randomseed(otherSeed)if k>=65 and k<=122 and not(k>=91 and k<=96)then k=string.char(k)end;e=e..k end end)math.randomseed(os.time())if not i then return else return e end end;table.decrypt=function(l,b,c)if not l or not b or not c or type(l)~='string'or b==''or c==''then return end;local d,e,m,g='','','',''for h in b:gmatch('.')do d=d..h:byte()end;for h in c:gmatch('.')do g=g..h:byte()end;math.randomseed(d)otherSeed=math.random(1000000)local i=pcall(function()for h in l:gmatch('.')do if h:byte()>=65 and h:byte()<=122 then local k=h:byte()-68-math.random(5)otherSeed=otherSeed+k;math.randomseed(otherSeed)e=e..string.char(k)else m=m..h;if m:len()>=3 then local k=tonumber(m)-68-math.random(5)otherSeed=otherSeed+k;math.randomseed(otherSeed)e=e..string.char(k)m=''end end end end)math.randomseed(os.time())if not i then return else local n,o,p,q,r={},0,0;for h,j in string.gmatch(e,'[^%s]+')do p=p+1 end;for h,j in string.gmatch(e,'[^%s]+')do o=o+1;if o==p and h~=g then return elseif q then if q:sub(-1)==[[']]then n[h:lower()]=q:gsub([[']],'')q=nil else q=q..' '..h end elseif r then n[h:lower()]=r;r=nil elseif h:sub(1,1)==[[']]then q=h else r=h end end;return n end end

	for _, v in next, {'AutoShaman', 'AutoNewGame', 'PhysicalConsumables', 'AutoScore'} do
		tfm.exec['disable' .. v]()
	end

	for _, v in next, {'help', 'load', 'save'} do
		system.disableChatCommandDisplay(v, true)
	end

	local db, dbPassword, key, playersAlive, timer, warnTimer, object, firsted = {}, 'yPYn5Du8asdQCa0Sau789ydu9A78SadQC', 'vnuj83Wg'

	local grounds = {
		{ type = 1, friction = 0, restitution = 0.2, miceCollision = true },
		{ type = 2, friction = 0, restitution = 1.2, miceCollision = true },
		{ type = 3, friction = 0, restitution = 9999, miceCollision = true },
		{ type = 4, friction = 9999, restitution = 0.2, miceCollision = true }
	}

	local textLangue = {
		br = {
			welcomeMessage = '<N2>Bem-vindo ao <B>#mess</B>! Digite <B>!help</B> para mais informações!\n\tDesenvolvido por Mescouleur<font color="#525267" size="9">#0000</font> e Nettoork<font color="#525267" size="9">#0000</font>',
			help = '<N2>• De tempo em tempo os pisos em sua volta se modificarão, deixando o mapa uma bagunça, e logo depois voltarão ao normal. Seu objetivo é levar o queijo até a toca passando pelos obstáculos, isso inclui os pisos e o meep dos outros jogadores.</N2>\n<VP>Para salvar seus pontos, digite !save\nPara carregar seus pontos, digite !load + código',
			saveMsg = "Guarde seu save em um local seguro",
			waitCmd = "Por favor, aguarde para utilizar esse comando novamente.",
			loadMsg = "Seu save foi carregado com sucesso.",
			wrongAccount = "Esse save não pertence a essa conta",
			wrongSave = "Esse save está incorreto."
		},
		en = {
			welcomeMessage = '<N2>Welcome to <B>#mess</B>! Type <B>!help</B> for more informations!\n\tDeveloped by Mescouleur<font color="#525267" size="9">#0000</font> and Nettoork<font color="#525267" size="9">#0000</font>',
			help = "<N2>• Temporarily the grounds in the map will change theirselves, making a big mess in the map and after of some seconds they will back to their original form. Your main objective is  to collect the cheese and get back to the hole, trying to survive the players' meep and the grounds changing constantly.</N2>\n<VP>To save your points, type !save\nTo load your points, type !load + code",
			saveMsg = "Save your code in a safe local",
			waitCmd = "Please wait to execute this command again.",
			loadMsg = "Your save was successfully uploaded.",
			wrongAccount = "This save doesn't belong to this account",
			wrongSave = "This save is incorrect."
		},
	}

	translate = function(msg)
		return (textLangue[tfm.get.room.community] and textLangue[tfm.get.room.community][msg]) or textLangue.en[msg]
	end

	eventNewPlayer = function(name)
		db[name] = {
			timer = os.time(),
			save = {
				id = tfm.get.room.playerList[name].id,
				points = 0
			}
		}
		tfm.exec.setPlayerScore(name, 0, false)
		ui.addTextArea(0, '<p align="center"><font size="20"><D>...', name, 5, 28, 790, 0, 0, 0, 0, true)
		tfm.exec.chatMessage(translate('welcomeMessage'), name)
	end

	addGrounds = function(warn)
		local id = 0
		if warn then
			objects = {}
			tfm.exec.addPhysicObject (1, 0, 0, {type = 14, width = 10, height = 10, miceCollision = false, groundCollision = false, dynamic = false})
		end
		for i in tfm.get.room.xmlMapInfo.xml:match('<S>(.-)</S>'):gmatch('<S(.-)/>') do
			id = id + 1
			local info = {}
			for o in getXmlInfo('P', i):gmatch("[^,]+") do
				info[#info + 1] = o
			end
			if not (getXmlInfo('T', i) == '9' or getXmlInfo('T', i) == '14' or getXmlInfo('T', i) == '12' or getXmlInfo('c', i) == '2') then
				if not warn then
					for a, o in next, objects do
						if o == id then
							tfm.exec.removeJoint(id)
							local rg = grounds[math.random(1, #grounds)]
							tfm.exec.addPhysicObject(id, getXmlInfo('X', i)- 2, getXmlInfo('Y', i) -2, {type = rg['type'], foreground = true, restitution = rg['restitution'], miceCollision = rg['miceCollision'], friction = rg['friction'], width = getXmlInfo('L', i) + 4, height = getXmlInfo('H', i) + 4, angle = info[5]})
						end
					end
				elseif math.random(0, 1) == 1 then
					objects[#objects + 1] = id
					tfm.exec.addJoint(id, 1, 1, {type = 0, color = '0xC55E4A', line = 10, foreground = true, alpha = 0.7, point1 = getXmlInfo('X', i)..','..getXmlInfo('Y', i), point2 = 1 + getXmlInfo('X', i)..','..getXmlInfo('Y', i)})
				end
			end
		end
		updateInformation('<D>!')
	end

	removeGrounds = function()
		local id = 0
		for i in tfm.get.room.xmlMapInfo.xml:match('<S>(.-)</S>'):gmatch('<S(.-)/>') do
			id = id + 1
			tfm.exec.addJoint(id, 1, 1, {type = 0, color = '0x4892D9', line = 10, foreground = true, alpha = 0, point1 = '0,0', point2 = '1,0'})
			tfm.exec.removePhysicObject(id)
		end
		timer = nil
		updateInformation('<D>...')
	end

	eventLoop = function(currentTime, timeRemaining)
		if timeRemaining <= 0 then
			tfm.exec.newGame('#17')
		elseif timer then
			timer = timer - 0.5
			if timer == -0.5 then
				addGrounds()
			elseif timer < -3 then
				removeGrounds() 
			elseif timer > 0 then
				if warnTimer then
					addGrounds(true) 
					warnTimer = false
				end
				updateInformation('<D>'..math.modf(timer)) 
			end
		elseif currentTime > 3000 then
			if math.random(1, 10) == 5 then
				warnTimer = true
				timer = math.random(1.5, 4.5) 
			end
		end
	end

	updateInformation = function(text)
		ui.updateTextArea(0, '<p align="center"><font size="20">'..text, nil)
	end

	getXmlInfo = function (a, b)
		if a == 'P' then
			a = b:match('P="(.-)"')
		else
			a = b:match(a..'="(%d+)"')
		end
		if not a then 
			a = 0
		end
		return a 
	end

	eventNewGame = function()
		firsted = false
		playersAlive = 0
		timer = nil
		updateInformation('<D>...')
		for i, v in next, tfm.get.room.playerList do
			tfm.exec.giveMeep(i)
			playersAlive = playersAlive + 1
		end
	end

	local split = function(t, s)
		local a = {}
		for i in string.gmatch(t, string.format("[^%s]+", s or "%s")) do
			a[#a + 1] = i
		end
		return a
	end

	eventChatCommand = function(name, command)
		local arg = split(command, ' ')
		if command == 'help' then
			tfm.exec.chatMessage(translate('help'), name)
		elseif arg[1] == 'save' then
			if db[name].timer < os.time() then
				local gen = (table.encrypt(db[name].save, dbPassword, key))
				tfm.exec.chatMessage('<R>'..translate('saveMsg')..':</R> <D>'..gen..'</D>', name)
				db[name].timer = os.time() + 10000
			else
				tfm.exec.chatMessage('<R>'..translate('waitCmd')..'</R>', name)
			end
		elseif arg[1] == 'load' and arg[2] then
			if db[name].timer < os.time() then
				local gen = table.decrypt(arg[2], dbPassword, key)
				if gen then
					if tonumber(gen.id) == tfm.get.room.playerList[name].id then
						db[name].save = gen
						tfm.exec.setPlayerScore(name, db[name].save.points, false)
						tfm.exec.chatMessage('<R>'..translate('loadMsg')..'</R>', name)
						db[name].timer = os.time() + 10000
					else
						tfm.exec.chatMessage(""..translate('wrongAccount').."</R>", name)
					end
				else
					tfm.exec.chatMessage('<R>'..translate('wrongSave')..'</R>', name)
				end
			else
				tfm.exec.chatMessage('<R>'..translate('waitCmd')..'</R>', name)
			end
		end
	end

	updateMap = function()
		playersAlive = playersAlive - 1
		if playersAlive <= 0 then
			tfm.exec.setGameTime(0)
			tfm.exec.newGame('#17')
		end
	end

	eventPlayerWon = function(name)
		if not firsted then
			if tfm.get.room.uniquePlayers >= 5 then
				db[name].save.points = db[name].save.points + 1
				tfm.exec.setPlayerScore(name, 1, true)
			end
			firsted = true 
		end
		updateMap()
	end

	eventPlayerDied = function(name)
		updateMap()
	end

	tfm.exec.newGame('#17')

	table.foreach(tfm.get.room.playerList, eventNewPlayer)
end

modules.map = function()
	local mapWidth, mapHeight = 800, 400
	local getCoordinates = function(x, y)
		return 600 + (200 * x / mapWidth), 300 + (100 * y / mapHeight)
	end
	local getScale = function(w, h)
		return 200 * w / mapWidth, 100 * h / mapHeight
	end

	eventNewPlayer = function(playerName)
		tfm.exec.lowerSyncDelay(playerName)
	end
	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	local O = 0
	eventNewGame = function()
		loadNewMap = false

		for t = O, -1 do
			ui.removeTextArea(t)
		end
		o = 0

		local xml = tfm.get.room.xmlMapInfo
		if xml then
			string.gsub(xml.xml, "<P (.-)/>", function(settings)
				mapWidth = math.max(tonumber(string.match(settings, "L=\"(%d+)\"")) or 800)
				mapHeight = math.max(tonumber(string.match(settings, "H=\"(%d+)\"")) or 400)
			end, 1)

			local Z = 0
			tfm.exec.addPhysicObject(Z, 700, 350, {
				type = 12,
				width = 200,
				height = 100,
				miceCollision = false,
				groundCollision = false,
				color = 0x6A7495,
				foreground = true,
			})

			string.gsub(xml.xml, "<S (.-)/>", function(groundContent)
				local X = tonumber(string.match(groundContent, "X=\"(%-?%d+%.?%d*)\"")) or 10
				local Y = tonumber(string.match(groundContent, "Y=\"(%-?%d+%.?%d*)\"")) or 10
				local L = tonumber(string.match(groundContent, "L=\"(%-?%d+%.?%d*)\"")) or 10
				local H = tonumber(string.match(groundContent, "H=\"(%-?%d+%.?%d*)\"")) or 10
				local T = tonumber(string.match(groundContent, "T=\"(%d+)\"")) or 0
				local A = tonumber(string.match(groundContent, "P=\".-,.-,.-,.-,(%-?%d+%.?%d*),.-\"")) or 0
				local O = string.match(groundContent, "o=\"(%x+)\"")
				O = O and tonumber("0x" .. O, 16)

				X, Y = getCoordinates(X, Y)
				L, H = getScale(L, H)

				Z = Z + 1
				tfm.exec.addPhysicObject(Z, X, Y, {
					type = T,
					width = L,
					height = H,
					angle = A,
					miceCollision = false,
					groundCollision = false,
					color = O,
					foreground = true,
				})
			end)

			string.gsub(xml.xml, "<([FT]) (.-)/>", function(objectType, objectContent)
				local X = tonumber(string.match(objectContent, "X=\"(%d+)\"")) or 10
				local Y = tonumber(string.match(objectContent, "Y=\"(%d+)\"")) or 10

				X, Y = getCoordinates(X, Y)

				local color = (objectType == "F" and 0xFFFA00 or 0xFF5E4C)

				O = O - 1
				ui.addTextArea(O, "", nil, X, Y, 1, 1, color, color, .4, false)
			end)
		else
			loadNewMap = true
		end
	end

	system.looping(function()
		for playerName, playerData in next, tfm.get.room.playerList do
			if not playerData.isDead then
				local color = playerData.isShaman and 0x00E3FF or 0xFFFFFF
				local x, y = getCoordinates(playerData.x, playerData.y)
				ui.addTextArea(playerData.id, "", nil, x, y, 1, 1, color, color, .4, false)
			end
		end
	end, 4)

	eventLoop = function()
		if loadNewMap then
			tfm.exec.newGame()
		end
	end

	eventPlayerDied = function(playerName)
		ui.removeTextArea(tfm.get.room.playerList[playerName].id)
	end
	eventPlayerWon = eventPlayerDied

	eventChatCommand = function(n, c)
		if owners[n] and c:sub(1, 2) == "np" then
			tfm.exec.newGame(c:sub(4))
		end
	end
end

modules.lsmap = function()
	local images = {
		lsmap = "165ddeefd5d.png",
		map_display = "165ddec178d.png",
		maps = {
			["@560778"] = "165dde71cd7.png",
			["@565330"] = "165dde7d970.png",
			["@574683"] = "165dde7ff14.png"
		}
	}
	table.sort(images.maps, function(m1, m2)
		return tonumber(m1:sub(2)) < tonumber(m2:sub(2))
	end)

	local info = { }
	eventNewPlayer = function(playerName, skip)
		info[playerName] = { display = -1, map = -1 }

		if not skip then
			for k, v in next, images do
				if type(v) == "table" then
					for i, j in next, v do
						tfm.exec.addImage(j, "?0", -2000, -2000, playerName)
					end
				else
					tfm.exec.addImage(v, "?0", -2000, -2000, playerName)
				end
			end

			tfm.exec.addImage(images.lsmap, "!1", 55, 24, playerName)

			tfm.exec.chatMessage("<ROSE>Support <B>https://atelier801.com/topic?f=6&t=876691</B></ROSE>", playerName)
		end
		ui.addTextArea(0, "<font size='12'><V>" .. playerName .. "<N>'s maps: <BV>3", playerName, 65, 60, nil, nil, 1, 1, 0, true)

		local nick = playerName:gsub("#0000", "")

		local maps, counter = { }, 0
		for map in next, images.maps do
			counter = counter + 1
			maps[counter] = nick .. " - " .. map .. " - 0 - 100% - P" .. math.random(0, 1) .. " <BV><a href='event:display_" .. map .. "'>[Display map]</a>"
		end
		ui.addTextArea(1, "<font size='12'>" .. table.concat(maps, "\n<N>"), playerName, 65, 88, nil, nil, 1, 1, 0, true)
		ui.addTextArea(2, "<p align='center'><a href='event:close_lsmap'>Close\n", playerName, 55, 369, 684, nil, 1, 1, 0, true)
	end

	eventTextAreaCallback = function(id, playerName, callback)
		local cbk, counter = { }, 0
		string.gsub(callback, "[^_]+", function(w)
			counter = counter + 1
			cbk[counter] = w
		end)

		if cbk[1] == "display" then
			for i = 0, 2 do
				ui.removeTextArea(i, playerName)
			end

			info[playerName].display = tfm.exec.addImage(images.map_display, "!200", 190, 38, playerName)
			ui.addTextArea(0, "<p align='center'><font size='15'><B>" .. cbk[2], playerName, 200, 55, 400, nil, 1, 1, 0, true)

			info[playerName].map = tfm.exec.addImage(images.maps[cbk[2]], "!300", 200, 80, playerName)

			ui.addTextArea(1, "<p align='center'><a href='event:save_image_" .. cbk[2] .. "'>Save map image\n", playerName, 55, 304, 684, nil, 1, 1, 0, true)
			ui.addTextArea(2, "<p align='center'><a href='event:save_thumbnail_" .. cbk[2] .. "'>Save map thumbnail\n", playerName, 55, 328, 684, nil, 1, 1, 0, true)
			ui.addTextArea(3, "<p align='center'><a href='event:close_display'>Close\n", playerName, 55, 353, 684, nil, 1, 1, 0, true)
		elseif cbk[1] == "close" and cbk[2] == "display" then
			ui.removeTextArea(3, playerName)

			tfm.exec.removeImage(info[playerName].map, playerName)
			tfm.exec.removeImage(info[playerName].display, playerName)

			eventNewPlayer(playerName, true)
		end
	end

	eventNewGame = function()
		for playerName in next, tfm.get.room.playerList do
			eventNewPlayer(playerName)
		end
	end

	tfm.exec.disableAutoNewGame()
	tfm.exec.disableAutoShaman()

	tfm.exec.newGame('<C><P /><Z><S><S L="3000" H="72" X="401" Y="408" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="918" H="590" X="414" Y="93" T="12" P="0,0,9999,0.2,0,0,0,0" /></S><D><DS Y="373" X="410" /></D><O /></Z></C>')
end

modules.quiz = function()
	local questions = {
		-- Question
		-- Option1
		-- Option2
		-- Answer (1 / 2)
		{
			"What is the color of the Sky?",
			"Blue",
			"Pink",
			1
		},
		{
			"Are there trees in the Hell?",
			"Yes",
			"No",
			2
		},
	}

	local gameTimer, newGameTimer, respawn = -1, -1, false

	local question
	local displayQuestion = function()
		for i = 3, 4 do
			ui.removeTextArea(i)
		end

		for player, data in next, tfm.get.room.playerList do
			tfm.exec.movePlayer(player, 400, 320)
		end

		question = questions[math.random(#questions)]

		ui.addTextArea(0, "<p align='center'><font size='20'><CEP>" .. question[1], nil, 5, 25, 790, nil, 1, 1, 1, true)
		for i = 0, 1 do
			ui.addTextArea(i + 1, "<p align='center'><font size='20' color='#FFFFFF'>" .. question[i + 2], nil, 5 + (i * 420), 120, 370, nil, 1, 1, 0, true)
		end

		gameTimer = 10.5
	end

	local displayAnswer = function()
		local add = 0

		for i = 1, 2 do
			if question[4] == i then
				local x = 5 + ((i - 1) * 420)

				ui.updateTextArea(i, "<p align='center'><font size='20'><VP>" .. question[i + 1])

				local counter, alivePlayer = 0
				for player, data in next, tfm.get.room.playerList do
					if not data.isDead then
						if data.x > (x + 370) or data.x < x then
							tfm.exec.killPlayer(player)
						else
							counter = counter + 1
							alivePlayer = player
						end
					end
				end

				if counter == 0 then
					tfm.exec.chatMessage("<J>No winners.")
				elseif counter == 1 then
					tfm.exec.setPlayerScore(alivePlayer, 5, true)
					tfm.exec.chatMessage("<G>" .. alivePlayer .. " <J>wins.")
				end

				if counter < 2 then
					add = 3
					respawn = true
				end
			end
		end

		newGameTimer = 4.5 + add
	end

	eventNewGame = function()
		newGameTimer = 4.5
	end

	eventLoop = function()
		if respawn then
			for player, data in next, tfm.get.room.playerList do
				if data.isDead then
					tfm.exec.respawnPlayer(player)
				end
				tfm.exec.movePlayer(player, 400, 320)
			end
		end

		if newGameTimer >= 0 then
			newGameTimer = newGameTimer - .5
			if newGameTimer == 0 then
				respawn = false
				displayQuestion(true)
			else
				ui.setMapName("Quiz   <G>|   <N>New game in : <V>" .. math.max(0, math.floor(newGameTimer)) .. "<")
			end
		elseif gameTimer >= 0 then
			gameTimer = gameTimer - .5
			if gameTimer == 0 then
				ui.setMapName("Quiz<")
				displayAnswer()
			else
				ui.setMapName("Quiz   <G>|   <N>Time left to choose a side : <V>" .. math.max(0, math.floor(gameTimer)) .. "<")
			end
		end
	end

	eventNewPlayer = function(player)
		tfm.exec.lowerSyncDelay(player)
	end
	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	tfm.exec.disableAutoNewGame()
	tfm.exec.disableAutoShaman()
	tfm.exec.disableAutoScore()

	for player, data in next, tfm.get.room.playerList do
		tfm.exec.setPlayerScore(player, 0)
	end

	tfm.exec.newGame('<C><P /><Z><S><S L="40" H="100" X="400" Y="380" T="1" P="0,0,0,0.2,0,0,0,0" /><S L="800" X="400" H="40" Y="400" T="0" P="0,0,0.3,0.2,0,0,0,0" /><S L="40" X="400" H="100" Y="380" T="1" P="0,0,0,0.2,0,0,0,0" /><S L="800" H="40" X="400" Y="406" T="0" P="0,0,0.3,0.2,180,0,0,0" /><S L="800" H="40" X="400" Y="413" T="0" P="0,0,0.3,0.2,0,0,0,0" /><S L="40" H="310" X="400" Y="160" T="1" P="0,0,0,0.2,180,0,0,0" /><S L="40" X="400" H="310" Y="160" T="1" P="0,0,0,0.2,180,0,0,0" /><S L="10" X="0" H="380" Y="190" T="12" P="0,0,0,0,0,0,0,0" /><S L="10" H="380" X="800" Y="190" T="12" P="0,0,0,0,0,0,0,0" /></S><D><DS Y="320" X="400" /></D><O /></Z></C>')
end

modules.funcorp = function()
	local miceAlive, nextMap = 0

	local funcorp = false
	eventChatCommand = function(playerName, command)
		if not owners[playerName] then return end

		local cmd = string.split(command, "%S+")

		if cmd[1] == "funcorp" then
			if cmd[2] == "help" then
				tfm.exec.chatMessage([[<FC>!changesize [playerNames|*] [size|off] : Temporarily changes the size (between 0.1x and 5x) of players.

!colornick [playerNames|*] [color|off] : Temporarily changes the color of player nicknames.

!funcorp [on|off|help] : Enable/disable the funcorp mode, or show the list of funcorp-related commands

!linkmice [playerNames] [off] : Temporarily links players.

!meep [playerNames|*] [off] : Give meep to players.

!np [mapCode] : Starts a new map.

!npp [mapCode] : Plays the selected map after the current map is over.

!transformation [playerNames|*] [off] : Temporarily gives the ability to transform.
				]], playerName)
			else
				if cmd[2] then
					funcorp = (cmd[2] == "on" and true or cmd[2] == "off" and false)
				else
					funcorp = not funcorp
				end

				if funcorp then
					tfm.exec.chatMessage("<FC>The FunCorp mode has been activated in this room.")
				else
					tfm.exec.chatMessage("<FC>The FunCorp mode has been deactivated.")
				end
			end
		elseif funcorp and cmd[2] then
			if cmd[1] == "changesize" then
				local size = cmd[#cmd]

				size = tonumber(size) or (size == "off" and 1)
				if not size then
					return tfm.exec.chatMessage("<V>[•] <BL>Invalid size.", playerName)
				end
				size = (size < .1 and .1 or size > 5 and 5 or size)

				if cmd[2] == "*" then
					for player in next, tfm.get.room.playerList do
						tfm.exec.changePlayerSize(player, size)
					end
					tfm.exec.chatMessage("<V>[•] <BL>All the players now have " .. (size == 1 and "their regular size." or ("the size " .. size)), playerName)
				else
					local players, counter = { }, 0
					for i = 2, #cmd - 1 do
						counter = counter + 1
						players[counter] = string.nick(cmd[i])
						tfm.exec.changePlayerSize(players[counter], size)
					end
					tfm.exec.chatMessage("<V>[•] <BL>The following players now have " .. (size == 1 and "their regular size" or ("the size " .. size)) .. ": <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
				end
			elseif cmd[1] == "colornick" then
				if not cmd[3] then
					owners[playerName] = cmd[2]
					return ui.showColorPicker(0, playerName, 1, "Select a color")
				end

				local off, color = cmd[#cmd] == "off"
				if not off then
					if string.find(cmd[#cmd], '#') then
						cmd[#cmd] = tonumber(string.sub(cmd[3], 2), 16)
					end

					if not cmd[#cmd] then
						return tfm.exec.chatMessage("<V>[•] <BL>Invalid color.", playerName)
					end

					color = string.format("%x", cmd[#cmd])
				end

				if cmd[2] == "*" then
					for player in next, tfm.get.room.playerList do
						tfm.exec.setNameColor(player, (not off and cmd[#cmd] or false))
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>All the nickname colors have been removed.", playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>All the players now have the nickname color <font color='#" .. color .. "'>0x" .. color .. "</font>.", playerName)
					end
				else
					local players, counter = { }, 0
					for i = 2, #cmd - 1 do
						counter = counter + 1
						players[counter] = string.nick(cmd[i])
						tfm.exec.setNameColor(players[counter], (not off and cmd[#cmd] or false))
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>Nickname colors removed from players: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>New nickname color <font color='#" .. color .. "'>(0x" .. color .. ")</font> for players: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					end
				end
			elseif cmd[1] == "linkmice" then
				local off = cmd[#cmd] == "off"

				if cmd[2] == "*" then
					local lastPlayer
					for player in next, tfm.get.room.playerList do
						if lastPlayer then
							tfm.exec.linkMice(player, lastPlayer, not off)
						end
						lastPlayer = player
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>All the links have been removed.", playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>All the players are now linked.", playerName)
					end
				else
					if not cmd[(off and 4 or 3)] then
						return tfm.exec.chatMessage("<V>[•] <BL>There must be at least two players in order to " .. (off and "unlink" or "link") .. " them.", playerName)
					end

					local players, counter, lastPlayer = { }, 0
					for i = 2, #cmd - (off and 1 or 0) do
						counter = counter + 1
						players[counter] = string.nick(cmd[i])
						if lastPlayer then
							tfm.exec.linkMice(players[counter], lastPlayer, not off)
						end
						lastPlayer = players[counter]
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>The links involving the following players have been removed: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>The following players now linked: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					end
				end
			elseif cmd[1] == "meep" then
				local off = cmd[#cmd] == "off"

				if cmd[2] == "*" then
					for player in next, tfm.get.room.playerList do
						tfm.exec.giveMeep(player, not off)
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>All the meep powers have been removed.", playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>Meep powers given to all players in the room.", playerName)
					end
				else
					local players, counter = { }, 0
					for i = 2, #cmd - (off and 1 or 0) do
						counter = counter + 1
						players[counter] = string.nick(cmd[i])
						tfm.exec.giveMeep(players[counter], not off)
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>Meep powers removed from players: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>Meep powers given to players: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					end
				end
			elseif cmd[1] == "np" then
				tfm.exec.newGame(cmd[2])
			elseif cmd[1] == "npp" then
				nextMap = cmd[2]
				tfm.exec.disableAutoNewGame()
				tfm.exec.chatMessage("<V>[•] <BL>Next map: " .. tostring(nextMap), playerName)
			elseif cmd[1] == "transformation" then
				local off = cmd[#cmd] == "off"

				if cmd[2] == "*" then
					for player in next, tfm.get.room.playerList do
						tfm.exec.giveTransformations(player, not off)
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>All the transformations powers have been removed.", playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>Transformations powers given to all players in the room.", playerName)
					end
				else
					local players, counter = { }, 0
					for i = 2, #cmd - (off and 1 or 0) do
						counter = counter + 1
						players[counter] = string.nick(cmd[i])
						tfm.exec.giveTransformations(players[counter], not off)
					end

					if off then
						tfm.exec.chatMessage("<V>[•] <BL>Transformations powers removed from players: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					else
						tfm.exec.chatMessage("<V>[•] <BL>Transformations powers given to players: <BV>" .. table.concat(players, "<BL>, <BV>"), playerName)
					end
				end
			end
		end
	end

	eventColorPicked = function(id, playerName, color)
		eventChatCommand(playerName, "colornick " .. tostring(owners[playerName]) .. " " .. color)
		owners[playerName] = true
	end

	eventNewGame = function()
		if nextMap then
			nextMap = nil
			tfm.exec.disableAutoNewGame(false)
		end

		local counter = 0
		for _, data in next, tfm.get.room.playerList do
			counter = counter + 1
		end
		miceAlive = counter
	end

	local tentatives = 0
	eventLoop = function(_, remainingTime)
		if nextMap and (remainingTime <= 500 or miceAlive <= 0) then
			tfm.exec.newGame(nextMap)
			tentatives = tentatives + 1

			if tentatives == 5 then
				eventNewGame()
			end
		end
	end

	eventPlayerDied = function()
		miceAlive = miceAlive - 1
	end

	eventPlayerWon = function()
		miceAlive = miceAlive - 1
	end

	eventPlayerLeft = function()
		local counter = 0
		for _, data in next, tfm.get.room.playerList do
			if not data.isDead then
				counter = counter + 1
			end
		end
		miceAlive = counter
	end

	system.disableChatCommandDisplay()
	eventNewGame()

	eventNewPlayer = function(n)
		if funcorp then
			tfm.exec.chatMessage("<FC>The FunCorp mode has been activated in this room.", n)
		end
	end

	local info = string.match(tfm.get.room.name, "#bolodefchoco%d+funcorp0(.+)")
	if info then
		string.gsub(info, "%S+", function(value)
			owners[string.nick(value)] = true
		end)
	end
end

modules.sizerace = function()
	maps = {"@7519027", "@7519258", "@7520442", "@7520354"}
	keys = {66, 67, 78, 86, 88}
	miceInfo = {}
	ui.setMapName("#Sizerace")
	tfm.exec.disableAfkDeath()
	tfm.exec.disableAutoNewGame()
	tfm.exec.setGameTime(90)
	tfm.exec.disableAutoShaman()
	tfm.exec.newGame(maps[math.random(#maps)])

	function eventNewGame()
 ui.setMapName("#Sizerace")
 tfm.exec.disableAfkDeath()
 tfm.exec.setGameTime(90)
	end

	function eventPlayerDied(nick)
 tfm.exec.changePlayerSize(nick, 1)
 tfm.exec.respawnPlayer(nick)
	end

	function eventPlayerWon(nick)
 tfm.exec.changePlayerSize(nick, 1)
 if tfm.get.room.playerList[nick].community=="pl" then
		tfm.exec.chatMessage("<font color='#fff000'>Gracz " .. nick .. " właśnie wygrał rundę!")
 else
		tfm.exec.chatMessage("<font color='#fff000'>Player " .. nick .. " has just won the round!")
 end
	end

	function eventKeyboard(nick, key)
 if key==88 and miceInfo[nick].lastTransform < os.time() - 2000 then
		tfm.exec.changePlayerSize(nick, 0.3)
		miceInfo[nick].lastTransform = os.time()
 end
 if key==67 and miceInfo[nick].lastTransform < os.time() - 2000 then
		tfm.exec.changePlayerSize(nick, 0.6)
		miceInfo[nick].lastTransform = os.time()
 end
 if key==86 and miceInfo[nick].lastTransform < os.time() - 2000 then
		tfm.exec.changePlayerSize(nick, 1)
		miceInfo[nick].lastTransform = os.time()
 end
 if key==66 and miceInfo[nick].lastTransform < os.time() - 2000 then
		tfm.exec.changePlayerSize(nick, 2.1)
		miceInfo[nick].lastTransform = os.time()
 end
 if key==78 and miceInfo[nick].lastTransform < os.time() - 2000 then
		tfm.exec.changePlayerSize(nick, 3.5)
		miceInfo[nick].lastTransform = os.time()
 end
	end

	function eventTextAreaCallback(id, nick, call)
 if tfm.get.room.playerList[nick].community=="pl" then
		if call=="help" then
	 ui.addTextArea(1, "<p align='center'><font size='16'>#sizerace</font><br><br>Witaj w #sizerace! Zmieniaj rozmiar swojej myszki i przechodź najróżniejsze mapy z różnym poziomem trudności. Staraj się nie popełniać błędów! Możesz zmieniać swój rozmiar co 2 sekundy, dlatego każdy błąd poskutkuje stratą cennego czasu. Bądź najszybszy i baw się dobrze!<br><br><b>X</b> - rozmiar 0.3<br><b>C</b> - rozmiar 0.6<br><b>V</b> - rozmiar 1<br><b>B</b> - rozmiar 2.1<br><b>N</b> - rozmiar 3.5<br><br>Autor:<br>Boxofkrain#0000<br><br><a href='event:close'>Zamknij</a>", nick, 5, 50, 300, 265, 0x324650, 0x212F36, nil, true)
		elseif call=="close" then
	 ui.removeTextArea(1, nick)
		end
 else
		if call=="help" then
	 ui.addTextArea(1, "<p align='center'><font size='16'>#sizerace</font><br><br>Welcome to #sizerace! Change size of your mouse and win different maps with different difficulty level. Don't make mistakes! You can change your mouse's size every 2 seconds only. Be the fastest and have fun!<br><br><b>X</b> - size 0.3<br><b>C</b> - size 0.6<br><b>V</b> - size 1<br><b>B</b> - size 2.1<br><b>N</b> - size 3.5<br><br>Author:<br>Boxofkrain#0000<br><br><a href='event:close'>Close</a>", nick, 5, 50, 300, 265, 0x324650, 0x212F36, nil, true)
		elseif call=="close" then
	 ui.removeTextArea(1, nick)
		end
 end
	end

	function eventNewPlayer(nick)
 for i = 1,#keys do
		system.bindKeyboard(nick, keys[i], false, true)
 end
 miceInfo[nick] = {lastTransform = 0}
 if tfm.get.room.playerList[nick].community=="pl" then
		ui.addTextArea(0, "<p align='center'><a href='event:help'>Pomoc</a></p>", nick, 5, 28, 65, nil, 0x324650, 0x212F36, nil, true)
		tfm.exec.chatMessage("<b><font color='#92CF91'>Hejka! Miło Cię widzieć na #sizerace! Wszystkie potrzebne informacje znajdziesz w zakładce Pomoc.</font></b>")
 else
		ui.addTextArea(0, "<p align='center'><a href='event:help'>Help</a></p>", nick, 5, 28, 65, nil, 0x324650, 0x212F36, nil, true)
		tfm.exec.chatMessage("<b><font color='#92CF91'>Hey! Nice to see you in #sizerace! All the information you need are in Help tab.</font></b>")
 end 
 if (nick=="Boxofkrain#0000") then
		tfm.exec.setNameColor("Boxofkrain#0000", 0xff0000)
 end
	end

	function eventLoop(currentTime, remainingTime)
 if remainingTime <= 500 then
		tfm.exec.newGame(maps[math.random(#maps)])
 end
	end

	for nick in next, tfm.get.room.playerList do 
 eventNewPlayer(nick)
	end
end

modules.grey = function()
	local characters = { "167f6e57ace.png", "167f6e5923d.png", "167f6e5a9ae.png", "167f6e5c11f.png", "167f6e5d890.png", "167f6e5f001.png", "167f6e60773.png", "167f6e61ee4.png" }
	local objects = { "167f701f951.png", "167f701cddd.png", "167f703b956.png" }

	local key_P = string.byte('P')

	local info = { }
	eventNewPlayer = function(playerName)
		info[playerName] = {
			characterId = 0,
			image = 0,
			isOpen = false,
			tmp = { }
		}
		system.bindKeyboard(playerName, key_P, true, true)
		tfm.exec.chatMessage("<R>Ho ho ho. Aperta <B>P</B> aí malandro!", playerName)
	end
	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	local displaySelector = function(playerName)
		ui.addTextArea(0, "<B><p align='center'><font size='30' color='#323232'>Personagens", playerName, 5, 28, 788, 180, 0xced6e2, 0xacb5c1, 1, true)
		local x, y
		for i = 1, #characters do
			x, y = 10 + (i - 1) * 100, 88
			ui.addTextArea(i, "<font color='#323232'><B><p align='center'><a href='event:'>Escolher</a>\n", playerName, x, y, 80, 100, 0xced6e2, 0xacb5c1, 1, true)
			info[playerName].tmp[i] = tfm.exec.addImage(characters[i], "&0", x + 25, y + 25, playerName)
		end
	end

	eventKeyboard = function(playerName)
		for i = 1, #info[playerName].tmp do
			tfm.exec.removeImage(info[playerName].tmp[i])
		end
		info[playerName].isOpen = not info[playerName].isOpen
		if info[playerName].isOpen then
			for i = 0, #characters do
				ui.removeTextArea(i, playerName)
			end
		else
			displaySelector(playerName)
		end
	end

	eventTextAreaCallback = function(id, playerName, _, ignore)
		if info[playerName].image then
			tfm.exec.removeImage(info[playerName].image)
		end
		info[playerName].image = tfm.exec.addImage(characters[id], "%" .. playerName, -15, -50)
		info[playerName].characterId = id
		if not ignore then
			eventKeyboard(playerName)
		end
	end

	eventNewGame = function()
		for k, v in next, info do
			if v.characterId > 0 then
				eventTextAreaCallback(v.characterId, k, nil, true)
			end
		end

		local xml = tfm.get.room.xmlMapInfo
		if xml then
			string.gsub(xml.xml, "<F (.-)/>", function(cheese)
				local x = tonumber(string.match(cheese, "X=\"(%d+)\""))
				local y = tonumber(string.match(cheese, "Y=\"(%d+)\""))
				if x and y then
					tfm.exec.addImage(objects[math.random(#objects)], "!0", x - 15, y - 30)
				end
			end)
		end
	end
end

modules.lava = function()
	do local a={}local b={}local c={}local d={}local e={"eventChatCommand ","eventChatMessage","eventEmotePlayed","eventFileLoaded","eventFileSaved","eventKeyboard","eventMouse","eventLoop","eventNewGame","eventNewPlayer","eventPlayerDataLoaded","eventPlayerDied","eventPlayerGetCheese","eventPlayerLeft","eventPlayerVampire","eventPlayerWon","eventPlayerRespawn","eventPlayerMeep","eventPopupAnswer","eventSummoningStart","eventSummoningCancel","eventSummoningEnd","eventTextAreaCallback","eventColorPicked"}local f;f={['perfomance']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(g,h,i)local j=0;for v=1,g do local k=os.time()for l=1,h do i(h)end;j=j+os.time()-k end;return'Estimated Time: '..j/g..' ms.'end},['button']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(...)local m={...}local n=-543212345+m[1]*3;local o=m[9]and'0x2A424B'or'0x314e57'ui.addTextArea(n,'',m[4],m[5]-1,m[6]-1,m[7],m[8],0x7a8d93,0x7a8d93,1,true)ui.addTextArea(n-1,'',m[4],m[5]+1,m[6]+1,m[7],m[8],0x0e1619,0x0e1619,1,true)ui.addTextArea(n-2,'<p align="center"><a href="event:'..m[3]..'">'..m[2]..'</a></p>',m[4],m[5],m[6],m[7],m[8],o,o,1,true)end,['remove']=function(n,p)for l=0,2 do ui.removeTextArea(-543212345+n*3-l,p)end end},['ui-design']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(...)local m={...}if m[6]<0 or m[7]and m[7]<0 then return elseif not m[7]then m[7]=m[6]/2 end;local n=543212345+m[1]*8;ui.addTextArea(n,'',m[3],m[4],m[5],m[6]+100,m[7]+70,0x78462b,0x78462b,1,true)ui.addTextArea(n+1,'',m[3],m[4],m[5]+(m[7]+140)/4,m[6]+100,m[7]/2,0x9d7043,0x9d7043,1,true)ui.addTextArea(n+2,'',m[3],m[4]+(m[6]+180)/4,m[5],(m[6]+10)/2,m[7]+70,0x9d7043,0x9d7043,1,true)ui.addTextArea(n+3,'',m[3],m[4],m[5],20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(n+4,'',m[3],m[4]+m[6]+80,m[5],20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(n+5,'',m[3],m[4],m[5]+m[7]+50,20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(n+6,'',m[3],m[4]+m[6]+80,m[5]+m[7]+50,20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(n+7,m[2],m[3],m[4]+3,m[5]+3,m[6]+94,m[7]+64,0x1c3a3e,0x232a35,1,true)end,['remove']=function(n,p)for l=0,7 do ui.removeTextArea(543212345+n*8+l,p)end end},['text-area-custom']={AUTHOR='Nettoork#0000',_VERSION='2.0',dependencies={},['add']=function(...)local q={...}if type(q[1])=='table'then for l,v in next,q do if type(v)=='table'then if not v[3]then v[3]='nil'end;addTextArea(table.unpack(v))end end else if not q[3]then q[3]='nil'end;b[q[3]..'_'..q[1]]={...}ui.addTextArea(...)end end,['update']=function(n,r,p)if not p then p='nil'end;if not b[p..'_'..n]then return elseif type(r)=='string'then ui.updateTextArea(n,r,p)b[p..'_'..n][2]=r;return end;local s={text=2,x=4,y=5,w=6,h=7,background=8,border=9,alpha=10,fixed=11}for l,v in next,r do if s[l]then b[p..'_'..n][s[l]]=v end end;local t=b[p..'_'..n]ui.addTextArea(t[1],t[2],t[3],t[4],t[5],t[6],t[7],t[8],t[9],t[10],t[11])end,['remove']=function(n,p)if not p then p='nil'end;if b[p..'_'..n]then b[p..'_'..n]=nil end;ui.removeTextArea(n,p)end},['string-to-boolean']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['parse']=function(u)local w={}for l,v in next,u do w[v]=true end;return w end},['database']={AUTHOR='Nettoork#0000',_VERSION='1.1',dependencies={},['create']=function(x,y)if not c[x]then c[x]=y end end,['delete']=function(x)c[x]=nil end,['get']=function(x,...)local z,A={},{...}if not A[1]then return c[x]else for l,v in next,A do if c[x][v]then z[#z+1]=c[x][v]end end;return table.unpack(z)end end,['set']=function(x,...)local B=v;for l,v in next,{...}do if not B then B=v else c[x][B]=v;B=false end end end},['encryption']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['encrypt']=function(u,C,D)if not u or not C or not D or type(u)~='table'or C==''or D==''then return end;local E,F,G,H='','','',''for l in C:gmatch('.')do E=E..l:byte()end;for l in D:gmatch('.')do H=H..l:byte()end;math.randomseed(E)otherSeed=math.random(1000000)local I=pcall(function()for l,v in next,u do if type(v)=='string'or type(v)=='number'then if type(v)=='string'then v="'"..v.."'"end;G=G..' '..v..' '..l:upper()else return end end;G=G..' '..H;for l in G:gmatch('.')do local J=l:byte()+68+math.random(5)otherSeed=otherSeed+l:byte()math.randomseed(otherSeed)if J>=65 and J<=122 and not(J>=91 and J<=96)then J=string.char(J)end;F=F..J end end)math.randomseed(os.time())if not I then return else return F end end,['decrypt']=function(K,C,D)if not K or not C or not D or type(K)~='string'or C==''or D==''then return end;local E,F,L,H='','','',''for l in C:gmatch('.')do E=E..l:byte()end;for l in D:gmatch('.')do H=H..l:byte()end;math.randomseed(E)otherSeed=math.random(1000000)local I=pcall(function()for l in K:gmatch('.')do if l:byte()>=65 and l:byte()<=122 then local J=l:byte()-68-math.random(5)otherSeed=otherSeed+J;math.randomseed(otherSeed)F=F..string.char(J)else L=L..l;if L:len()>=3 then local J=tonumber(L)-68-math.random(5)otherSeed=otherSeed+J;math.randomseed(otherSeed)F=F..string.char(J)L=''end end end end)math.randomseed(os.time())if not I then return else local w,M,N,O,P={},0,0;for l,v in string.gmatch(F,'[^%s]+')do N=N+1 end;for l,v in string.gmatch(F,'[^%s]+')do M=M+1;if M==N and l~=H then return elseif O then if O:sub(-1)=="'"then w[l:lower()]=O:gsub("'",'')O=nil else O=O..' '..l end elseif P then w[l:lower()]=P;P=nil elseif l:sub(1,1)=="'"then O=l else P=l end end;return w end end},['sleep']={AUTHOR='Nettoork#0000',_VERSION='1.1',dependencies={},['loop']=function()local Q={}for l,v in next,a do if not v[2]or v[2]<os.time()then if coroutine.status(v[1])=='dead'then Q[#Q+1]=l else local R,S=coroutine.resume(v[1])v[2]=S end end end;if Q[1]then for l,v in next,Q do a[v]=nil end end end,['run']=function(T,U)if not U then U=500 end;a[#a+1]={coroutine.create(function()local V=function(W)coroutine.yield(os.time()+math.floor(W/U)*U)end;local X,Y=pcall(T,V)if Y then print(Y)end end),timeValue=nil}end},['wait-time']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['check']=function(Z,_,a0,a1)if Z and _ then if not d[Z]then d[Z]={}end;if not d[Z][_]then a1=0;d[Z][_]=os.time()+(a0 or 1000)end;if d[Z][_]<=os.time()or a1 and a1==0 then d[Z][_]=os.time()+(a0 or 1000)return true else return false end end end},['json']={AUTHOR='https://github.com/rxi',_VERSION='0.1.1',dependencies={},['encode']=function(a2)local a3;local a4={["\\"]="\\\\",["\""]="\\\"",["\b"]="\\b",["\f"]="\\f",["\n"]="\\n",["\r"]="\\r",["\t"]="\\t"}local a5={["\\/"]="/"}for a6,v in pairs(a4)do a5[v]=a6 end;local function a7(a8)return a4[a8]or string.format("\\u%04x",a8:byte())end;local function a9(a2)return"null"end;local function aa(a2,ab)local ac={}ab=ab or{}if ab[a2]then error("circular reference")end;ab[a2]=true;if a2[1]~=nil or next(a2)==nil then local W=0;for a6 in pairs(a2)do if type(a6)~="number"then error("invalid table: mixed or invalid key types")end;W=W+1 end;if W~=#a2 then error("invalid table: sparse array")end;for l,v in ipairs(a2)do table.insert(ac,a3(v,ab))end;ab[a2]=nil;return"["..table.concat(ac,",").."]"else for a6,v in pairs(a2)do if type(a6)~="string"then error("invalid table: mixed or invalid key types")end;table.insert(ac,a3(a6,ab)..":"..a3(v,ab))end;ab[a2]=nil;return"{"..table.concat(ac,",").."}"end end;local function ad(a2)return'"'..a2:gsub('[%z\1-\31\\"]',a7)..'"'end;local function ae(a2)if a2~=a2 or a2<=-math.huge or a2>=math.huge then error("unexpected number value '"..tostring(a2).."'")end;return a2 end;local af={["nil"]=a9,["table"]=aa,["string"]=ad,["number"]=ae,["boolean"]=tostring}a3=function(a2,ab)local ag=type(a2)local T=af[ag]if T then return T(a2,ab)end;error("unexpected type '"..ag.."'")end;return a3(a2)end,['decode']=function(ah)local ai;local a5={["\\/"]="/"}local function aj(...)local ac={}for l=1,select("#",...)do ac[select(l,...)]=true end;return ac end;local ak=aj(" ","\t","\r","\n")local al=aj(" ","\t","\r","\n","]","}",",")local am=aj("\\","/",'"',"b","f","n","r","t","u")local an=aj("true","false","null")local ao={["true"]=true,["false"]=false,["null"]=nil}local function ap(ah,aq,ar,as)for l=aq,#ah do if ar[ah:sub(l,l)]~=as then return l end end;return#ah+1 end;local function at(ah,aq,au)local av=1;local aw=1;for l=1,aq-1 do aw=aw+1;if ah:sub(l,l)=="\n"then av=av+1;aw=1 end end;error(string.format("%s at line %d col %d",au,av,aw))end;local function ax(W)local T=math.floor;if W<=0x7f then return string.char(W)elseif W<=0x7ff then return string.char(T(W/64)+192,W%64+128)elseif W<=0xffff then return string.char(T(W/4096)+224,T(W%4096/64)+128,W%64+128)elseif W<=0x10ffff then return string.char(T(W/262144)+240,T(W%262144/4096)+128,T(W%4096/64)+128,W%64+128)end;error(string.format("invalid unicode codepoint '%x'",W))end;local function ay(R)local az=tonumber(R:sub(3,6),16)local aA=tonumber(R:sub(9,12),16)if aA then return ax((az-0xd800)*0x400+aA-0xdc00+0x10000)else return ax(az)end end;local function aB(ah,l)local aC=false;local aD=false;local aE=false;local aF;for aG=l+1,#ah do local aH=ah:byte(aG)if aH<32 then at(ah,aG,"control character in string")end;if aF==92 then if aH==117 then local aI=ah:sub(aG+1,aG+5)if not aI:find("%x%x%x%x")then at(ah,aG,"invalid unicode escape in string")end;if aI:find("^[dD][89aAbB]")then aD=true else aC=true end else local a8=string.char(aH)if not am[a8]then at(ah,aG,"invalid escape char '"..a8 .."' in string")end;aE=true end;aF=nil elseif aH==34 then local R=ah:sub(l+1,aG-1)if aD then R=R:gsub("\\u[dD][89aAbB]..\\u....",ay)end;if aC then R=R:gsub("\\u....",ay)end;if aE then R=R:gsub("\\.",a5)end;return R,aG+1 else aF=aH end end;at(ah,l,"expected closing quote for string")end;local function aJ(ah,l)local aH=ap(ah,l,al)local R=ah:sub(l,aH-1)local W=tonumber(R)if not W then at(ah,l,"invalid number '"..R.."'")end;return W,aH end;local function aK(ah,l)local aH=ap(ah,l,al)local aL=ah:sub(l,aH-1)if not an[aL]then at(ah,l,"invalid literal '"..aL.."'")end;return ao[aL],aH end;local function aM(ah,l)local ac={}local W=1;l=l+1;while 1 do local aH;l=ap(ah,l,ak,true)if ah:sub(l,l)=="]"then l=l+1;break end;aH,l=ai(ah,l)ac[W]=aH;W=W+1;l=ap(ah,l,ak,true)local aN=ah:sub(l,l)l=l+1;if aN=="]"then break end;if aN~=","then at(ah,l,"expected ']' or ','")end end;return ac,l end;local function aO(ah,l)local ac={}l=l+1;while 1 do local H,a2;l=ap(ah,l,ak,true)if ah:sub(l,l)=="}"then l=l+1;break end;if ah:sub(l,l)~='"'then at(ah,l,"expected string for key")end;H,l=ai(ah,l)l=ap(ah,l,ak,true)if ah:sub(l,l)~=":"then at(ah,l,"expected ':' after key")end;l=ap(ah,l+1,ak,true)a2,l=ai(ah,l)ac[H]=a2;l=ap(ah,l,ak,true)local aN=ah:sub(l,l)l=l+1;if aN=="}"then break end;if aN~=","then at(ah,l,"expected '}' or ','")end end;return ac,l end;local aP={['"']=aB,["0"]=aJ,["1"]=aJ,["2"]=aJ,["3"]=aJ,["4"]=aJ,["5"]=aJ,["6"]=aJ,["7"]=aJ,["8"]=aJ,["9"]=aJ,["-"]=aJ,["t"]=aK,["f"]=aK,["n"]=aK,["["]=aM,["{"]=aO}ai=function(ah,aq)local aN=ah:sub(aq,aq)local T=aP[aN]if T then return T(ah,aq)end;at(ah,aq,"unexpected character '"..aN.."'")end;if type(ah)~="string"then error("expected argument of type string, got "..type(ah))end;local ac,aq=ai(ah,ap(ah,1,ak,true))aq=ap(ah,aq,ak,true)if aq<=#ah then at(ah,aq,"trailing garbage")end;return ac end},['runtime-analyze']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['run']=function(...)local aQ=0;local aR=0;local aS=0;local aT={}local aU={...}local aV={}local aW={"#83FFA4","#30FF67","#00FF44","#E9FF82","#EEFF24","#F5F604","#FF524F","#FF302D","#E60400","#C70502"}for l=1,10 do aV[l]=string.rep("\226\150\136",l)end;if tfm.get.room.name:find("^\42\03")~=nil then aS=40 else aS=60 end;local aX=function()local aY=""for l,v in next,aT do local aZ=math.floor(v.timeLoaded/(aS/10))*(5-math.floor(aQ/2))aZ=aZ>10 and 10 or aZ;local a_=(aZ==0 and""or"<font color='"..aW[aZ].."'>")..(aZ==0 and""or aV[aZ])aY=aY..string.format("<PT>[%s]</PT>\n%dms %s",l,v.timeLoaded,a_)..'</font>\n'end;local aZ=math.floor(aR/(aS/10))*(5-math.floor(aQ/2))aZ=aZ>10 and 10 or aZ;local a_=(aZ==0 and""or"<font color='"..aW[aZ].."'>")..(aZ==0 and""or aV[aZ])aY=aY..string.format("<D>Global Runtime:</D>\n%dms %s",aR,a_)..'</font> '..aS..'ms\n'if aU[1]then for l,v in next,aU do ui.addTextArea(3102301909,aY,v,5,26,0,0,1,1,0.5,true)end else ui.addTextArea(3102301909,aY,nil,5,26,0,0,1,1,0.5,true)end end;local b0={['eventLoop']=function()aQ=aQ+1;if aQ>8 then aR=0;aQ=0;for l,v in next,aT do aT[l]={call=0,timeLoaded=0}end end;aX()end}for l,v in next,e do if _G[v]and type(_G[v])=="function"then local b1=_G[v]if not aT[v]then aT[v]={call=0,timeLoaded=0}end;_G[v]=function(...)aT[v].call=aT[v].call+1;if b0[v]then b0[v](table.unpack({...}))end;local b2=os.time()b1(table.unpack({...}))local b3=os.time()-b2;aR=aR-aT[v].timeLoaded;aT[v].timeLoaded=aT[v].timeLoaded+b3+b3*0.15;aR=aR+aT[v].timeLoaded end else if b0[v]then _G[v]=b0[v]end end end end},['parse-xml']={AUTHOR='http://lua-users.org/wiki/LuaXml',_VERSION='1.0',dependencies={},['parse']=function(b4)local ab={}local b5={}table.insert(ab,b5)local b6,a8,b7,b8,b9;local l,aG=1,1;while true do b6,aG,a8,b7,b8,b9=string.find(b4,"<(%/?)([%w:]+)(.-)(%/?)>",l)if not b6 then break end;local ba=string.sub(b4,l,b6-1)if not string.find(ba,"^%s*$")then table.insert(b5,ba)end;local m={}string.gsub(b8,"([%-%w]+)=([\"'])(.-)%2",function(bb,bc,bd)m[bb]=bd end)if b9=="/"then table.insert(b5,{label=b7,xarg=m,empty=1})elseif a8==""then b5={label=b7,xarg=m}table.insert(ab,b5)else local be=table.remove(ab)b5=ab[#ab]if#ab<1 then error("nothing to close with "..b7)end;if be.label~=b7 then error("trying to close "..be.label.." with "..b7)end;table.insert(b5,be)end;l=aG+1 end;local ba=string.sub(b4,l)if not string.find(ba,"^%s*$")then table.insert(ab[#ab],ba)end;if#ab>1 then error("unclosed "..ab[#ab].label)end;return ab[1]end},['foreach-index']={AUTHOR='https://stackoverflow.com/users/68204/rberteig',_VERSION='1.0',dependencies={},['run']=function(ag,T,bf)local bg={}for a6,bc in next,ag do bg[#bg+1]=a6 end;table.sort(bg,bf)for bc,a6 in ipairs(bg)do T(a6,ag[a6])end end}}local bh=1547917483395+2628*10^6;require=function(bi)if bi=='hide-warning'then bh=false else if bh and os.time()>bh then bh=false;print("<R>Warning! You may be using an outdated version of require, check in <i>pastebin.com/u/KananGamer</i> if this has a better version, otherwise you can disable this warning with require('hide-warning').</R>")end;if f[bi]then if f[bi]['INIT_SCRIPT']and type(f[bi]['INIT_SCRIPT'])=='function'then f[bi]['INIT_SCRIPT']()f[bi]['INIT_SCRIPT']=nil end;return f[bi]else print('Library "'..bi..'" not found! <i>Require Version: 1.3.0 | Author: Nettoork#0000</i>')return false end end end end
	--[[ Current Version: 1.3.0 ]]--

	local sleep = require("sleep")
	local stb = require("string-to-boolean")
	local admins = stb.parse({"Nettoork#0000"})
	local maps = {"@7214563", "@7273409"}
	local db = {}
	local objects = {}
	local loading
	local timer

	local split = function(t, s)
		local a={}
		for i in string.gmatch(t, "[^" .. (s or "%s") .. "]+") do
			a[#a + 1] = i
		end
		return a
	end

	local addGround = function()
		tfm.exec.addPhysicObject(1, 800, 387, { type = 6, restitution=0.2, friction=0.3, width=1600, height=37, groundCollision=true, miceCollision=true })
		tfm.exec.removePhysicObject(2)
	end

	local removeGround = function()
		tfm.exec.addPhysicObject(2, 800, 387, { type = 3, restitution = 0.2, friction = 0.3, width = 1600, height = 37, groundCollision = true, miceCollision = false })
		tfm.exec.removePhysicObject(1)
	end

	local checkPlayers = function()
		local alive = 0
		local totalPlayer = 0
		local lastPlayer
		for i, v in next, tfm.get.room.playerList do
			if not v.isDead then
				alive = alive + 1
				lastPlayer = i
			end
			totalPlayer = totalPlayer + 1
		end
		if ((alive == 1 and totalPlayer > 1) or alive <= 0 or (timer and timer <= 0)) and loading then
			loading = false
			sleep.run(function(sleep)
				if alive == 1 then
					ui.addTextArea(1, "\n\n\n\n<p align='center'><font size='60'><PT>"..lastPlayer.." venceu.</PT></font></p>", nil, 5, 26, 790, 390, 1, 1, 0, true)
					db[lastPlayer].level = db[lastPlayer].level + 1
					tfm.exec.setPlayerScore(lastPlayer, db[lastPlayer].level, false)
					tfm.exec.respawnPlayer(lastPlayer)
					tfm.exec.giveCheese(lastPlayer)
					tfm.exec.playerVictory(lastPlayer)
				elseif alive <= 0 or (timer and timer <= 0) then
					ui.addTextArea(1, "\n\n\n\n<p align='center'><font size='60'><PT>Não houveram vencedores</PT></font></p>", nil, 5, 26, 790, 390, 1, 1, 0, true)
				end
				sleep(5000)
				ui.removeTextArea(1)
				startGame()
			end)
		end
	end

	startGame = function()
		loading = true
		tfm.exec.newGame(maps[math.random(#maps)])
		tfm.exec.setGameTime(600)
		sleep.run(function(sleep)
			sleep(8000)
			if not loading then
				return
			end
			local total = 0
			if math.random(20) == 15 then
				for i = 1, 10 do
					objects[#objects + 1] = tfm.exec.addShamanObject(33, math.random(100, 1500), 26)
				end
				tfm.exec.chatMessage("<PT>CÓ, CÓ, CÓ!</PT>")
			end
			while loading do
				local s = sleep
				sleep = function(t)
					if not loading then
						return
					end
					s(t)
				end
				total = total + 1
				if total == 2 then
					for username in next, tfm.get.room.playerList do
						tfm.exec.giveMeep(username, true)
					end
				end
				for i = 1, math.random(10) == 1 and 1 or 2 do
					local object = math.random(7)
					objects[#objects + 1] = tfm.exec.addShamanObject(object == 5 and 6 or object, math.random(100, 1500), 350)
				end
				sleep(500)
				for i = math.random(4, 8), 1, -1 do
					ui.addTextArea(1, "\n\n\n\n<p align='center'><font size='60'>"..i.."</font></p>", nil, 5, 26, 790, 390, 1, 1, 0, true)
					sleep(1000)
				end
				ui.removeTextArea(1)
				removeGround()
				sleep(5000)
				addGround()
				checkPlayers()
				for i, v in next, objects do
					tfm.exec.removeObject(v)
				end
				objects = {}
				sleep(5000)
			end
			addGround()
			for i, v in next, objects do
				tfm.exec.removeObject(v)
			end
			ui.removeTextArea(1)
		end)
	end

	eventChatCommand = function(name, command)
		if admins[name] then
			local arg = split(command, " ")
			if arg[1] == "ban" and db[arg[2]] and not db[arg[2]].isBanned then
				db[arg[2]].isBanned = true
				tfm.exec.chatMessage("<R>"..arg[2].." foi impedido de jogar.</R>", arg[3] and name)
				tfm.exec.killPlayer(arg[2])
			elseif arg[1] == "unban" and db[arg[2]] and db[arg[2]].isBanned then
				db[arg[2]].isBanned = false
				tfm.exec.chatMessage("<BV>"..arg[2].." foi perdoado.</BV>", arg[3] and name)
			end
		end
		if command == "stop" and db[name] then
			db[name].displayMsg = not db[name].displayMsg
		end
	end

	eventNewPlayer = function(name)
		tfm.exec.chatMessage("<D>Seja bem-vindo a sala #lava. Essa é uma versão remasterizada feita por Nettoork#0000 de uma das primeiras versões do minigame que atualmente pertence à Sr_Timbo#6367.</D>\n<ROSE>Para vencer a partida, sobreviva subindo em objetos de shaman que serão jogadores de forma aleatória pelo mapa, e logo após o chão se transformará em lava.</ROSE>\n<BV>Sistema anti bug do X implementado, caso encontre algum erro reporte para <B>Bolodefchoco#0000</B></BV>", name)
		if not db[name] then
			db[name] = {
				isBanned = false,
				level = 0,
				moving = 0,
				displayMsg = true,
				notMoving = 0
			}
		end
		tfm.exec.setPlayerScore(name, db[name].level, false)
		for i = 0, 3 do system.bindKeyboard(name, i, true, true) end
		system.bindKeyboard(name, 32, true, true)
		system.bindKeyboard(name, string.byte("E"), true, true)
		tfm.exec.lowerSyncDelay(name)
	end

	eventLoop = function(a, t)
		sleep.loop()
		timer = t

		if a > 5000 then
			local time = os.time()
			for k, v in next, tfm.get.room.playerList do
				if not db[k] then return end
				if not v.isDead then
					if db[k].moving == 0 then
						if v.vx == 0 then
							if db[k].notMoving < 3 then
								db[k].notMoving = db[k].notMoving + .5
							else
								db[k].moving = os.time() + 5000
								if db[k].displayMsg then
									tfm.exec.chatMessage("Você morrerá caso não se mover constantemente! Digite !stop para não receber mais essa mensagem.", k)
								end
								db[k].notMoving = 0
							end
						end
					else
						if time > db[k].moving then
							tfm.exec.killPlayer(k)
						end
					end
				end
			end
		end
	end

	eventKeyboard = function(name)
		if not db[name] then return end
		db[name].moving = 0
	end

	eventNewGame = function()
		for i, v in next, tfm.get.room.playerList do
			if not db[i] then break end
			if db[i].isBanned then
				tfm.exec.killPlayer(i)
			end
			db[i].moving = 0
		end
		addGround()
	end

	eventPlayerDied = checkPlayers
	eventPlayerLeft = checkPlayers

	for index, value in next, {'AutoShaman', 'AutoNewGame', 'AutoTimeLeft', 'PhysicalConsumables', 'AfkDeath', 'DebugCommand', 'AutoScore'} do
		tfm.exec['disable' .. value]()
	end

	for i, v in next, {"ban", "unban", "omg", "stop"} do
		system.disableChatCommandDisplay(v)
	end

	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	startGame()
end

modules.arrow = function()
	do local a={}local b={}local c={}local d={}local e;e={['perfomance']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(f,g,h)local i=0;for v=1,f do local j=os.time()for k=1,g do h(g)end;i=i+os.time()-j end;return'Estimated Time: '..i/f..' ms.'end},['button']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(...)local l={...}local m=-543212345+l[1]*3;local n=l[9]and'0x2A424B'or'0x314e57'ui.addTextArea(m,'',l[4],l[5]-1,l[6]-1,l[7],l[8],0x7a8d93,0x7a8d93,1,true)ui.addTextArea(m-1,'',l[4],l[5]+1,l[6]+1,l[7],l[8],0x0e1619,0x0e1619,1,true)ui.addTextArea(m-2,'<p align="center"><a href="event:'..l[3]..'">'..l[2]..'</a></p>',l[4],l[5],l[6],l[7],l[8],n,n,1,true)end,['remove']=function(m,o)for k=0,2 do ui.removeTextArea(-543212345+m*3-k,o)end end},['ui-design']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(...)local l={...}if l[6]<0 or l[7]and l[7]<0 then return elseif not l[7]then l[7]=l[6]/2 end;local m=543212345+l[1]*8;ui.addTextArea(m,'',l[3],l[4],l[5],l[6]+100,l[7]+70,0x78462b,0x78462b,1,true)ui.addTextArea(m+1,'',l[3],l[4],l[5]+(l[7]+140)/4,l[6]+100,l[7]/2,0x9d7043,0x9d7043,1,true)ui.addTextArea(m+2,'',l[3],l[4]+(l[6]+180)/4,l[5],(l[6]+10)/2,l[7]+70,0x9d7043,0x9d7043,1,true)ui.addTextArea(m+3,'',l[3],l[4],l[5],20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+4,'',l[3],l[4]+l[6]+80,l[5],20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+5,'',l[3],l[4],l[5]+l[7]+50,20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+6,'',l[3],l[4]+l[6]+80,l[5]+l[7]+50,20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+7,l[2],l[3],l[4]+3,l[5]+3,l[6]+94,l[7]+64,0x1c3a3e,0x232a35,1,true)end,['remove']=function(m,o)for k=0,7 do ui.removeTextArea(543212345+m*8+k,o)end end},['text-area-custom']={AUTHOR='Nettoork#0000',_VERSION='2.0',dependencies={},['add']=function(...)local p={...}if type(p[1])=='table'then for k,v in next,p do if type(v)=='table'then if not v[3]then v[3]='nil'end;addTextArea(table.unpack(v))end end else if not p[3]then p[3]='nil'end;b[p[3]..'_'..p[1]]={...}ui.addTextArea(...)end end,['update']=function(m,q,o)if not o then o='nil'end;if not b[o..'_'..m]then return elseif type(q)=='string'then ui.updateTextArea(m,q,o)b[o..'_'..m][2]=q;return end;local r={text=2,x=4,y=5,w=6,h=7,background=8,border=9,alpha=10,fixed=11}for k,v in next,q do if r[k]then b[o..'_'..m][r[k]]=v end end;local s=b[o..'_'..m]ui.addTextArea(s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10],s[11])end,['remove']=function(m,o)if not o then o='nil'end;if b[o..'_'..m]then b[o..'_'..m]=nil end;ui.removeTextArea(m,o)end},['string-to-boolean']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['parse']=function(t)local u={}for k,v in next,t do u[v]=true end;return u end},['database']={AUTHOR='Nettoork#0000',_VERSION='1.1',dependencies={},['create']=function(w,x)if not c[w]then c[w]=x end end,['delete']=function(w)c[w]=nil end,['get']=function(w,...)local y,z={},{...}if not z[1]then return c[w]else for k,v in next,z do if c[w][v]then y[#y+1]=c[w][v]end end;return table.unpack(y)end end,['set']=function(w,...)local A=v;for k,v in next,{...}do if not A then A=v else c[w][A]=v;A=false end end end},['encryption']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['encrypt']=function(t,B,C)if not t or not B or not C or type(t)~='table'or B==''or C==''then return end;local D,E,F,G='','','',''for k in B:gmatch('.')do D=D..k:byte()end;for k in C:gmatch('.')do G=G..k:byte()end;math.randomseed(D)otherSeed=math.random(1000000)local H=pcall(function()for k,v in next,t do if type(v)=='string'or type(v)=='number'then if type(v)=='string'then v="'"..v.."'"end;F=F..' '..v..' '..k:upper()else return end end;F=F..' '..G;for k in F:gmatch('.')do local I=k:byte()+68+math.random(5)otherSeed=otherSeed+k:byte()math.randomseed(otherSeed)if I>=65 and I<=122 and not(I>=91 and I<=96)then I=string.char(I)end;E=E..I end end)math.randomseed(os.time())if not H then return else return E end end,['decrypt']=function(J,B,C)if not J or not B or not C or type(J)~='string'or B==''or C==''then return end;local D,E,K,G='','','',''for k in B:gmatch('.')do D=D..k:byte()end;for k in C:gmatch('.')do G=G..k:byte()end;math.randomseed(D)otherSeed=math.random(1000000)local H=pcall(function()for k in J:gmatch('.')do if k:byte()>=65 and k:byte()<=122 then local I=k:byte()-68-math.random(5)otherSeed=otherSeed+I;math.randomseed(otherSeed)E=E..string.char(I)else K=K..k;if K:len()>=3 then local I=tonumber(K)-68-math.random(5)otherSeed=otherSeed+I;math.randomseed(otherSeed)E=E..string.char(I)K=''end end end end)math.randomseed(os.time())if not H then return else local u,L,M,N,O={},0,0;for k,v in string.gmatch(E,'[^%s]+')do M=M+1 end;for k,v in string.gmatch(E,'[^%s]+')do L=L+1;if L==M and k~=G then return elseif N then if N:sub(-1)=="'"then u[k:lower()]=N:gsub("'",'')N=nil else N=N..' '..k end elseif O then u[k:lower()]=O;O=nil elseif k:sub(1,1)=="'"then N=k else O=k end end;return u end end},['sleep']={AUTHOR='Nettoork#0000',_VERSION='1.1',dependencies={},['loop']=function()local P={}for k,v in next,a do if not v[2]or v[2]<os.time()then if coroutine.status(v[1])=='dead'then P[#P+1]=k else local Q,R=coroutine.resume(v[1])v[2]=R end end end;if P[1]then for k,v in next,P do a[v]=nil end end end,['run']=function(S,T)if not T then T=500 end;a[#a+1]={coroutine.create(function()local U=function(V)coroutine.yield(os.time()+math.floor(V/T)*T)end;S(U)end),timeValue=nil}end},['wait-time']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['check']=function(W,X,Y,Z)if W and X then if not d[W]then d[W]={}end;if not d[W][X]then Z=0;d[W][X]=os.time()+(Y or 1000)end;if d[W][X]<=os.time()or Z and Z==0 then d[W][X]=os.time()+(Y or 1000)return true else return false end end end},['json']={AUTHOR='https://github.com/rxi',_VERSION='0.1.1',dependencies={},['encode']=function(_)local a0;local a1={["\\"]="\\\\",["\""]="\\\"",["\b"]="\\b",["\f"]="\\f",["\n"]="\\n",["\r"]="\\r",["\t"]="\\t"}local a2={["\\/"]="/"}for a3,v in pairs(a1)do a2[v]=a3 end;local function a4(a5)return a1[a5]or string.format("\\u%04x",a5:byte())end;local function a6(_)return"null"end;local function a7(_,a8)local a9={}a8=a8 or{}if a8[_]then error("circular reference")end;a8[_]=true;if _[1]~=nil or next(_)==nil then local V=0;for a3 in pairs(_)do if type(a3)~="number"then error("invalid table: mixed or invalid key types")end;V=V+1 end;if V~=#_ then error("invalid table: sparse array")end;for k,v in ipairs(_)do table.insert(a9,a0(v,a8))end;a8[_]=nil;return"["..table.concat(a9,",").."]"else for a3,v in pairs(_)do if type(a3)~="string"then error("invalid table: mixed or invalid key types")end;table.insert(a9,a0(a3,a8)..":"..a0(v,a8))end;a8[_]=nil;return"{"..table.concat(a9,",").."}"end end;local function aa(_)return'"'.._:gsub('[%z\1-\31\\"]',a4)..'"'end;local function ab(_)if _~=_ or _<=-math.huge or _>=math.huge then error("unexpected number value '"..tostring(_).."'")end;return _ end;local ac={["nil"]=a6,["table"]=a7,["string"]=aa,["number"]=ab,["boolean"]=tostring}a0=function(_,a8)local ad=type(_)local S=ac[ad]if S then return S(_,a8)end;error("unexpected type '"..ad.."'")end;return a0(_)end,['decode']=function(ae)local af;local a2={["\\/"]="/"}local function ag(...)local a9={}for k=1,select("#",...)do a9[select(k,...)]=true end;return a9 end;local ah=ag(" ","\t","\r","\n")local ai=ag(" ","\t","\r","\n","]","}",",")local aj=ag("\\","/",'"',"b","f","n","r","t","u")local ak=ag("true","false","null")local al={["true"]=true,["false"]=false,["null"]=nil}local function am(ae,an,ao,ap)for k=an,#ae do if ao[ae:sub(k,k)]~=ap then return k end end;return#ae+1 end;local function aq(ae,an,ar)local as=1;local at=1;for k=1,an-1 do at=at+1;if ae:sub(k,k)=="\n"then as=as+1;at=1 end end;error(string.format("%s at line %d col %d",ar,as,at))end;local function au(V)local S=math.floor;if V<=0x7f then return string.char(V)elseif V<=0x7ff then return string.char(S(V/64)+192,V%64+128)elseif V<=0xffff then return string.char(S(V/4096)+224,S(V%4096/64)+128,V%64+128)elseif V<=0x10ffff then return string.char(S(V/262144)+240,S(V%262144/4096)+128,S(V%4096/64)+128,V%64+128)end;error(string.format("invalid unicode codepoint '%x'",V))end;local function av(Q)local aw=tonumber(Q:sub(3,6),16)local ax=tonumber(Q:sub(9,12),16)if ax then return au((aw-0xd800)*0x400+ax-0xdc00+0x10000)else return au(aw)end end;local function ay(ae,k)local az=false;local aA=false;local aB=false;local aC;for aD=k+1,#ae do local aE=ae:byte(aD)if aE<32 then aq(ae,aD,"control character in string")end;if aC==92 then if aE==117 then local aF=ae:sub(aD+1,aD+5)if not aF:find("%x%x%x%x")then aq(ae,aD,"invalid unicode escape in string")end;if aF:find("^[dD][89aAbB]")then aA=true else az=true end else local a5=string.char(aE)if not aj[a5]then aq(ae,aD,"invalid escape char '"..a5 .."' in string")end;aB=true end;aC=nil elseif aE==34 then local Q=ae:sub(k+1,aD-1)if aA then Q=Q:gsub("\\u[dD][89aAbB]..\\u....",av)end;if az then Q=Q:gsub("\\u....",av)end;if aB then Q=Q:gsub("\\.",a2)end;return Q,aD+1 else aC=aE end end;aq(ae,k,"expected closing quote for string")end;local function aG(ae,k)local aE=am(ae,k,ai)local Q=ae:sub(k,aE-1)local V=tonumber(Q)if not V then aq(ae,k,"invalid number '"..Q.."'")end;return V,aE end;local function aH(ae,k)local aE=am(ae,k,ai)local aI=ae:sub(k,aE-1)if not ak[aI]then aq(ae,k,"invalid literal '"..aI.."'")end;return al[aI],aE end;local function aJ(ae,k)local a9={}local V=1;k=k+1;while 1 do local aE;k=am(ae,k,ah,true)if ae:sub(k,k)=="]"then k=k+1;break end;aE,k=af(ae,k)a9[V]=aE;V=V+1;k=am(ae,k,ah,true)local aK=ae:sub(k,k)k=k+1;if aK=="]"then break end;if aK~=","then aq(ae,k,"expected ']' or ','")end end;return a9,k end;local function aL(ae,k)local a9={}k=k+1;while 1 do local G,_;k=am(ae,k,ah,true)if ae:sub(k,k)=="}"then k=k+1;break end;if ae:sub(k,k)~='"'then aq(ae,k,"expected string for key")end;G,k=af(ae,k)k=am(ae,k,ah,true)if ae:sub(k,k)~=":"then aq(ae,k,"expected ':' after key")end;k=am(ae,k+1,ah,true)_,k=af(ae,k)a9[G]=_;k=am(ae,k,ah,true)local aK=ae:sub(k,k)k=k+1;if aK=="}"then break end;if aK~=","then aq(ae,k,"expected '}' or ','")end end;return a9,k end;local aM={['"']=ay,["0"]=aG,["1"]=aG,["2"]=aG,["3"]=aG,["4"]=aG,["5"]=aG,["6"]=aG,["7"]=aG,["8"]=aG,["9"]=aG,["-"]=aG,["t"]=aH,["f"]=aH,["n"]=aH,["["]=aJ,["{"]=aL}af=function(ae,an)local aK=ae:sub(an,an)local S=aM[aK]if S then return S(ae,an)end;aq(ae,an,"unexpected character '"..aK.."'")end;if type(ae)~="string"then error("expected argument of type string, got "..type(ae))end;local a9,an=af(ae,am(ae,1,ah,true))an=am(ae,an,ah,true)if an<=#ae then aq(ae,an,"trailing garbage")end;return a9 end}}local aN=1547917483395+2628*10^6;require=function(aO)if aO=='hide-warning'then aN=false else if aN and os.time()>aN then aN=false;print("<R>Warning! You may be using an outdated version of require, check in <i>atelier801.com/topic?f=6&t=880333</i> if this has a better version, otherwise you can disable this warning with require('hide-warning').</R>")end;if e[aO]then if e[aO]['INIT_SCRIPT']and type(e[aO]['INIT_SCRIPT'])=='function'then e[aO]['INIT_SCRIPT']()e[aO]['INIT_SCRIPT']=nil end;return e[aO]elseif aO=='libs'then return e else print('Library "'..aO..'" not found! <i>Require Version: 1.2.2 | Author: Nettoork#0000</i>')return false end end end end
	--[[ Current Version: 1.2.2 ]]--

	-- Biblioteca wait-time
	local wait = require("wait-time")

	-- Biblioteca string-to-boolean
	local stb = require("string-to-boolean")

	-- Variáveis básicas
	tfm.exec.disableAfkDeath(true)
	local podeClicar = {}
	local time = os.time()
	local toRemove = {}
	local objects = {}
	local maps = {'<C><P L="1600" /><Z><S><S P="0,0,0.3,0.2,0,0,0,0" L="1600" o="1c1c1c" H="400" c="4" Y="201" T="12" X="799" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="430808" H="123" c="4" Y="40" T="12" X="701" /><S P="0,0,0.3,0.2,0,0,0,0" L="38" o="430808" H="121" c="4" Y="44" T="12" X="487" /><S c="3" L="104" o="28ff" H="310" X="1446" Y="39" T="12" P="0,0,0.3,0.2,90,0,0,0" /><S P="0,0,0,0.2,0,0,0,0" L="17" o="ff3838" H="400" c="3" Y="284" T="12" X="792" /><S P="0,0,0.3,0.2,0,0,0,0" L="38" o="430808" H="121" c="4" Y="41" T="12" X="553" /><S P="0,0,0.3,0.2,0,0,0,0" L="40" o="430808" H="136" c="4" Y="48" T="12" X="626" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="430808" H="123" c="4" Y="46" T="12" X="333" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="430808" H="123" c="4" Y="42" T="12" X="407" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="430808" H="123" c="4" Y="41" T="12" X="777" /><S L="104" o="ff3838" H="310" X="156" Y="35" T="12" P="0,0,0.3,0.2,90,0,0,0" /><S P="0,0,0.3,0.2,0,0,0,0" L="1600" o="ff3838" H="73" c="3" Y="-33" T="12" X="801" /><S c="4" L="107" o="f1c61" H="138" X="1547" Y="331" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S c="3" L="1600" o="ff3838" H="73" X="801" Y="108" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="430808" H="137" c="4" Y="334" T="12" X="70" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="ff3838" H="137" c="3" Y="334" T="12" X="204" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="430808" H="137" c="4" Y="334" T="12" X="340" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="ff3838" H="137" c="3" Y="334" T="12" X="476" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="430808" H="137" c="4" Y="334" T="12" X="613" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="ff3838" H="137" c="3" Y="334" T="12" X="715" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="28ff" H="137" c="3" Y="330" T="12" X="881" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="f1c61" H="137" c="4" Y="330" T="12" X="1018" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="28ff" H="137" c="3" Y="330" T="12" X="1153" /><S P="0,0,0,0.2,0,0,0,0" L="17" o="28ff" H="400" c="3" Y="284" T="12" X="812" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="f1c61" H="123" c="4" Y="46" T="12" X="861" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="f1c61" H="123" c="4" Y="48" T="12" X="939" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="f1c61" H="137" c="4" Y="330" T="12" X="1290" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="f1c61" H="123" c="4" Y="48" T="12" X="1100" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="f1c61" H="123" c="4" Y="47" T="12" X="1187" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="f1c61" H="123" c="4" Y="41" T="12" X="1269" /><S P="0,0,0.3,0.2,0,0,0,0" L="45" o="f1c61" H="123" c="4" Y="49" T="12" X="1015" /><S P="0,0,0.3,0.2,0,0,0,0" L="137" o="28ff" H="137" c="3" Y="330" T="12" X="1428" /><S c="3" L="800" o="28ff" H="73" X="1203" Y="108" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S c="3" L="800" o="28ff" H="73" X="1201" Y="-33" T="12" P="0,0,0.3,0.2,0,0,0,0" /></S><D><DS Y="48" X="798" /></D><O /></Z></C>', '@7566173'} -- Lista de mapas
	local mapInfo = { blueTeam = { x = 1069, y = 139 }, redTeam = { x = 430, y = 135 } } -- Informações de mapa
	local canCheck = false -- Se pode ou não chegar o número de jogadores vivos
	blueTeam = {} -- Time azul
	redTeam = {} -- Time vermelho
	blueTeamAlive = 0 -- Jogadores do time azul vivos
	redTeamAlive = 0 -- Jogadores do time vermelho vivos
	color_blueTeam = 0x1300ff -- Cor do time azul
	color_redTeam = 0xff0000 -- Cor do time vermelho

	-- Traduções
	lang = {
		br = {
			WELCOME = "<J>Bem Vindo a Arrow, escolha sua equipe e vá para Luta!</J><ROSE> Aperte Espaço para Atirar Flechas</ROSE><VI>\nPara informações Adicionais e Avaliação de Mapa: https://atelier801.com/topic?f=842389&t=929772&p=1#m1</VI>",
		  RedTeam = "<a href='event:redTeam'><p align='center'>Entre para a Equipe Vermelha",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>Entre para a Equipe Azul",
			AWIN = "<D>~ [Juiz] Empate!",
			RWIN = "<R>~ [Equipe Vermelha] ~ Nós Vencemos!",
			BWIN = "<BV>~ [Equipe Azul] ~ Nós Vencemos!"
		},
		en = {
			WELCOME = "<J>Welcome to Arrow, choose your team and go to Fight! </J> <ROSE> Press Space to Shoot Arrows</ROSE><VI>\nFor additional information and map submissions: https://atelier801.com/topic?f=842389&t=929772&p=1#m1</VI>",
			RedTeam = "<a href='event:redTeam'><p align='center'>Join the Red Team",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>Join the Blue Team",
			AWIN = "<D>~ [Judge] No Winners!",
			RWIN = "<R>~ [Red Team] ~ We Won!",
			BWIN = "<BV>~ [Blue Team] ~ We Won!"
		}, 
			es = {
			WELCOME = "<J>Bienvenido a Arrow, ¡elige tu equipo y ve a pelear! </ J> <ROSE> Presiona la barra espaciadora para tirar flechas</ROSE>",
			RedTeam = "<a href='event:redTeam'><p align='center'>Entrar al Equipo Rojo",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>Entrar al Equipo Azul",
			AWIN = "<D>~ [Juez] ¡No hay ganadores!",
			RWIN = "<R>~ [Equipo Rojo] ~ ¡Nosotros ganamos!",
			BWIN = "<BV>~ [Equipo Azul] ~ ¡Nosotros ganamos!"
		}, 
			pl = {
			WELCOME = "<J>Witaj w Arrow, wybierz drużynę i WALCZ! </ J> <ROSE> Naciśnij spację aby wystrzelić strzałę</ROSE>\n<VI>Dodatkowe informacje oraz zgłaszanie map: https://atelier801.com/topic?f=842389&t=929772&p=1#m1</VI>",
			RedTeam = "<a href='event:redTeam'><p align='center'>Dołącz do Czerwonej Drużyny",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>Dołącz do Niebieskiej Drużyny",
			AWIN = "<D>~ [Sędzia] Brak Zwycięzców!",
			RWIN = "<R>~ [Czerwona Drużyna] ~ Wygraliśmy!",
			BWIN = "<BV>~ [Niebieska Drużyna] ~ Wygraliśmy!"
		}, 
		ar = {
			WELCOME = "<J>مرحبًا في Arrow, اختر فريقك واذهب للقتال! </ J> <ROSE>اضغط زر مسافة لتهجم بسهم</ROSE>",
			RedTeam = "<a href='event:redTeam'><p align='center'>انضم للفريق الأحمر",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>انضم للفريق الأزرق",
			AWIN = "<D>~ [الحكم] لا فائزين!",
			RWIN = "<R>~ [الفريق الأحمر] ~ نحنُ فُزنا!",
			BWIN = "<BV>~ [الفريق الأزرق] ~ We !"
		},
		cn = {
	  WELCOME = "<J>欢迎来到 Arrow, 选择想加入的队伍然后参战吧! </ J> <ROSE> 按空格键来发射弓箭</ROSE>",
	  RedTeam = "<a href='event:redTeam'><p align='center'>加入红队",
	  BlueTeam = "<a href='event:blueTeam'><p align='center'>加入蓝队",
	  AWIN = "<D>~ [裁判] 没有人胜出!",
	  RWIN = "<R>~ [红队] ~ 我们赢了!",
	  BWIN = "<BV>~ [蓝队] ~ 我们赢了!"
		},
  fr = {
	  WELCOME = "<J>Bienvenue dans Arrow, choisis ton équipe et lance-toi au combat !</ J> <ROSE> Appuie sur espace pour tirer des flèches</ROSE>",
	  RedTeam = "<a href='event:redTeam'><p align='center'>Rejoins l'équipe rouge",
	  BlueTeam = "<a href='event:blueTeam'><p align='center'>Rejoins l'équipe bleue",
	  AWIN = "<D>~ [Juge] Pas de gagnant !",
	  RWIN = "<R>~ [Équipe rouge] ~ On a gagné !",
	  BWIN = "<BV>~ [Équipe bleue] ~ On a gagné !"
		},
  nl = {
	  WELCOME = "<J>Welkom bij Arrow, kies je team en ga vechten! </ J> <ROSE> Druk op de spatiebalk om pijlen te schieten</ROSE>",         
	  RedTeam = "<a href='event:redTeam'><p align=‘center'>Lid van het rode team worden",
	  BlueTeam = "<a href='event:blueTeam'><p align=‘center'>Lid van het blauwe team worden",
	  AWIN = "<D>~ [Jury] Geen winnaars!",
	  RWIN = "<R>~ [Team rood] ~ We hebben gewonnen!", 
	  BWIN = "<BV>~ [Team blauw] ~ We hebben gewonnen!"
  },
  he = {
			WELCOME = "<J>ברוכים הבאים לחץ, בחרו את קבוצתכם והילחמו! </ J> <ROSE> לחצו על מקש הרווח כדי לירות חצים</ROSE>",
			RedTeam = "<a href='event:redTeam'><p align='center'>הצטרפו לקבוצה האדומה",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>הצטרפו לקבוצה הכחולה",
			AWIN = "<D>~ [שופט] אין מנצחים!",
			RWIN = "<R>~ [קבוצה אדומה] ~ אנחנו ניצחנו!",
			BWIN = "<BV>~ [קבוצה כחולה] ~ אנחנו ניצחנו!"
		},
		hu = {
			WELCOME = "<J>Üdvözlet az Arrow-ban, válassz csapatot és harcolj! </ J> <ROSE> A nyilak kilövéséhez használd a Space billentyűt</ROSE>",
			RedTeam = "<a href='event:redTeam'><p align='center'>Csatlakozz a Piros csapathoz",
			BlueTeam = "<a href='event:blueTeam'><p align='center'>Csatlakozz a kék csapathoz",
			AWIN = "<D>~ [Bíró] Nincs nyertes!",
			RWIN = "<R>~ [Piros csapat] ~ Nyertünk!",
			BWIN = "<BV>~ [Kék csapat] ~ Nyertünk!"
		}, 
	}

	-- Mensagem de Ajuda
	local help = function(name)
		tfm.exec.chatMessage(translate(name, "WELCOME"), name)
	end

	-- Iniciar novo mapa
	local startNewMap = function()
		tfm.exec.newGame(maps[math.random(#maps)])
	end

	-- Chegar número de jogadores vivos
	local checkPlayersAlive = function()
		if blueTeamAlive == 0 or redTeamAlive == 0 then
			canCheck = false
			if blueTeamAlive == 0 and redTeamAlive == 0 then
				for i, v in pairs(tfm.get.room.playerList) do
				tfm.exec.chatMessage(translate(i, "AWIN"), i)
			end
			elseif blueTeamAlive == 0 then
		  for i, v in pairs(tfm.get.room.playerList) do
				tfm.exec.chatMessage(translate(i, "RWIN"), i)
			end
			elseif redTeamAlive == 0 then
		  for i, v in pairs(tfm.get.room.playerList) do
				tfm.exec.chatMessage(translate(i, "BWIN"), i)
			end
			end
			for username, data in next, tfm.get.room.playerList do
				if not data.isDead then
					tfm.exec.giveCheese(username)
					tfm.exec.playerVictory(username)
				end
			end
			startNewMap()
		end
	end

	-- Evento (Toda hora)
	eventLoop = function(currentTime, timeRemaining)
	if timeRemaining == 0 then
			startNewMap()
		elseif currentTime >= 20000 and currentTime <= 20500 then
	 for username, data in next, tfm.get.room.playerList do
				if not data.isDead and not blueTeam[username] and not redTeam[username] then
					tfm.exec.killPlayer(username)
				end
			end
			ui.removeTextArea(1)
			ui.removeTextArea(2)
			canCheck = true
			checkPlayersAlive()
	end
	end
		for i, v in next, objects do
			if v[2] < os.time() then
				toRemove[#toRemove + 1] = i
			end
		end
		for i, v in next, toRemove do
			if objects[v] then
				tfm.exec.removeObject(objects[v][1])
				table.remove(objects, v)
			end
		end

	-- Traduz as Mensagens 
	function translate(p, k)
		local cmm = tfm.get.room.playerList[p] and tfm.get.room.playerList[p].community or "en"
		cmm = lang[cmm] and cmm or "en"
		return lang[cmm][k] or "ERROR"
	end

	-- Evento (Novo jogador)
	eventNewPlayer = function(name)
		tfm.exec.bindKeyboard(name, 32, true, true)
		help(name)
for p, v in pairs(tfm.get.room.playerList) do
podeClicar[p] = 0
end
	end

	-- Evento (TextArea clicada)
	eventTextAreaCallback = function(id, name, ref)
		if ref == "blueTeam" or ref == "redTeam" and _G[ref.."Alive"] <= 10 and not tfm.get.room.playerList.isDead then
 if podeClicar[name] > time then return end
  podeClicar[name] = time + 500
			_G[ref.."Alive"] = _G[ref.."Alive"] + 1
			_G[ref][name] = name
			tfm.exec.movePlayer(name, mapInfo[ref].x, mapInfo[ref].y)
			tfm.exec.setNameColor(name, _G["color_"..ref])
		end
		ui.removeTextArea(1, name)
		ui.removeTextArea(2, name)
	end

	-- Evento (Jogador morre)
	eventPlayerDied = function(name)
		if blueTeam[name] then
			blueTeam[name] = nil
			blueTeamAlive = blueTeamAlive - 1
		elseif redTeam[name] then
			redTeam[name] = nil
			redTeamAlive = redTeamAlive - 1
		end
		if canCheck then
			checkPlayersAlive()
		end
	end

	-- Evento (Sempre que um jogador sair)
	eventPlayerLeft = eventPlayerDied

	-- Evento (Teclado ativado)
	eventKeyboard = function(username, key, down, x, y)
		if key == 32 and not tfm.get.room.playerList[username].isDead and (blueTeam[username] or redTeam[username]) then
			if wait.check("arrow", username, 1200)  then
				objects[#objects + 1] = {tfm.exec.addShamanObject(35, blueTeam[username] and x - 20 or x + 20, y-10, blueTeam[username] and 180 or 0,  blueTeam[username] and -40 or 40), os.time() + 5000}
			end
		end
	end

	-- Evento (Novo mapa)
	eventNewGame = function()
		local id=tfm.exec.addImage("168a552a1be.png", "&1", 197, 21, nil)
		local timeToRemove=5
		system.newTimer(function()
			if id then
				tfm.exec.removeImage(id)
			end
		end,timeToRemove*1000,false)
		tfm.exec.setGameTime(600)
	for p, v in pairs(tfm.get.room.playerList) do
	podeClicar[p] = 0
	end
		blueTeam = {}
		redTeam = {}
		blueTeamAlive = 0
		redTeamAlive = 0
	for i, v in pairs(tfm.get.room.playerList) do
		ui.addTextArea(1, translate(i, "RedTeam"), i, 6, 26, 182, 21, 0xff0011, 0, 1, true)
		ui.addTextArea(2, translate(i, "BlueTeam"), i, 647, 26, 182, 21, 0x0011ff, 0, 1, true)
	end
	end
	-- Máximo de jogadores na sala
	tfm.exec.setRoomMaxPlayers(20)

	-- Desativar automatizações do Transformice ✓
	for index, value in next, {'AutoShaman', 'AutoNewGame', 'AutoTimeLeft', 'PhysicalConsumables'} do
		tfm.exec['disable' .. value]()
	end

	-- Considerar jogadores na sala como novos jogadores
	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	-- Iniciar jogo
	startNewMap()
end

modules.vivooumorto = function()
	addConju = function(...)tfm.exec.addConjuration(...)end;addImage = function(...)tfm.exec.addImage(...)end;addJoint = function(...)tfm.exec.addJoint(...)end;addPhyObj = function(...)tfm.exec.addPhysicObject(...)end;addShaObj = function(...)tfm.exec.addShamanObject(...)end;changepsize = function(...)tfm.exec.changePlayerSize(...)end;chatMsg = function(...)tfm.exec.chatMessage(...)end;disableAfkDie = function(...)tfm.exec.disableAfkDeath(...)end;DisableAllShaSkill = function(...)tfm.exec.disableAllShamanSkills(...)end;disableAutoNewGame = function(...)tfm.exec.disableAutoNewGame(...)end;disableAutoScore = function(...)tfm.exec.disableAutoScore(...)end;disableAutoSha = function(...)tfm.exec.disableAutoShaman(...)end;disableAutotimeLeft = function(...)tfm.exec.disableAutoTimeLeft(...)end;disabledebug = function(...)tfm.exec.disableDebugCommand(...)end;disableminimode = function(...)tfm.exec.disableMinimalistMode(...)end;disablemort = function(...)tfm.exec.disableMortCommand(...)end;disablephyconsu = function(...)tfm.exec.disablePhysicalConsumables(...)end;disableprespawnp = function(...)tfm.exec.disablePrespawnPreview(...)end;disablewatch = function(...)tfm.exec.disableWatchCommand(...)end;displayp = function(...)tfm.exec.displayParticle(...)end;explo = function(...)tfm.exec.explosion(...)end;givechesse = function(...)tfm.exec.giveCheese(...)end;giveconsu = function(...)tfm.exec.giveConsumables(...)end;givem = function(...)tfm.exec.giveMeep(...)end;givet = function(...)tfm.exec.giveTransformations(...)end;killp = function(...)tfm.exec.killPlayer(...)end;linkp = function(...)tfm.exec.linkMice(...)end;lowersdelay = function(...)tfm.exec.lowerSyncDelay(...)end;moveobj = function(...)tfm.exec.moveObject(...)end;movep = function(...)tfm.exec.movePlayer(...)end;newgame = function(...)tfm.exec.newGame(...)end;pemote = function(...)tfm.exec.playEmote(...)end;pwin = function(...)tfm.exec.playerVictory(...)end;addimg = function(...)tfm.exec.removeCheese(...)end;reimg = function(...)tfm.exec.removeImage(...)end;rejoint = function(...)tfm.exec.removeJoint(...)end;reobj = function(...)tfm.exec.removeObject(...)end;rephyobj = function(...)tfm.exec.removePhysicObject(...)end;replayer = function(...)tfm.exec.respawnPlayer(...)end;setAutoMapFlipMode = function(...)tfm.exec.setAutoMapFlipMode(...)end;setgtime = function(...)tfm.exec.setGameTime(...)end;setncolor = function(...)tfm.exec.setNameColor(...)end;setpscore = function(...)tfm.exec.setPlayerScore(...)end;setmaxplayer = function(...)tfm.exec.setRoomMaxPlayers(...)end;setpass = function(...)tfm.exec.setRoomPassword (...)end;setsha = function(...)tfm.exec.setShaman(...)end;setshamode = function(...)tfm.exec.setShamanMode(...)end;setvamp = function(...)tfm.exec.setVampirePlayer(...)end;snow = function(...)tfm.exec.snow(...)end;addp = function(...)ui.addPopup(...)end;addtarea = function(...)ui.addTextArea (...)end;retarea = function(...)ui.removeTextArea (...)end;mapname = function(...)ui.setMapName (...)end;shaname = function(...)ui.setShamanName (...)end;showcpicker = function(...)ui.showColorPicker (...)end;uptarea = function(...)ui.updateTextArea (...)end;

	--[[ Require By: Nettoork#0000 ]]--
	do local a={}local b={}local c={}local d={}local e;e={['perfomance']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(f,g,h)local i=0;for v=1,f do local j=os.time()for k=1,g do h(g)end;i=i+os.time()-j end;return'Estimated Time: '..i/f..' ms.'end},['button']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(...)local l={...}local m=-543212345+l[1]*3;local n=l[9]and'0x2A424B'or'0x314e57'ui.addTextArea(m,'',l[4],l[5]-1,l[6]-1,l[7],l[8],0x7a8d93,0x7a8d93,1,true)ui.addTextArea(m-1,'',l[4],l[5]+1,l[6]+1,l[7],l[8],0x0e1619,0x0e1619,1,true)ui.addTextArea(m-2,'<p align="center"><a href="event:'..l[3]..'">'..l[2]..'</a></p>',l[4],l[5],l[6],l[7],l[8],n,n,1,true)end,['remove']=function(m,o)for k=0,2 do ui.removeTextArea(-543212345+m*3-k,o)end end},['ui-design']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['create']=function(...)local l={...}if l[6]<0 or l[7]and l[7]<0 then return elseif not l[7]then l[7]=l[6]/2 end;local m=543212345+l[1]*8;ui.addTextArea(m,'',l[3],l[4],l[5],l[6]+100,l[7]+70,0x78462b,0x78462b,1,true)ui.addTextArea(m+1,'',l[3],l[4],l[5]+(l[7]+140)/4,l[6]+100,l[7]/2,0x9d7043,0x9d7043,1,true)ui.addTextArea(m+2,'',l[3],l[4]+(l[6]+180)/4,l[5],(l[6]+10)/2,l[7]+70,0x9d7043,0x9d7043,1,true)ui.addTextArea(m+3,'',l[3],l[4],l[5],20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+4,'',l[3],l[4]+l[6]+80,l[5],20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+5,'',l[3],l[4],l[5]+l[7]+50,20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+6,'',l[3],l[4]+l[6]+80,l[5]+l[7]+50,20,20,0xbeb17d,0xbeb17d,1,true)ui.addTextArea(m+7,l[2],l[3],l[4]+3,l[5]+3,l[6]+94,l[7]+64,0x1c3a3e,0x232a35,1,true)end,['remove']=function(m,o)for k=0,7 do ui.removeTextArea(543212345+m*8+k,o)end end},['text-area-custom']={AUTHOR='Nettoork#0000',_VERSION='2.0',dependencies={},['add']=function(...)local p={...}if type(p[1])=='table'then for k,v in next,p do if type(v)=='table'then if not v[3]then v[3]='nil'end;addTextArea(table.unpack(v))end end else if not p[3]then p[3]='nil'end;b[p[3]..'_'..p[1]]={...}ui.addTextArea(...)end end,['update']=function(m,q,o)if not o then o='nil'end;if not b[o..'_'..m]then return elseif type(q)=='string'then ui.updateTextArea(m,q,o)b[o..'_'..m][2]=q;return end;local r={text=2,x=4,y=5,w=6,h=7,background=8,border=9,alpha=10,fixed=11}for k,v in next,q do if r[k]then b[o..'_'..m][r[k]]=v end end;local s=b[o..'_'..m]ui.addTextArea(s[1],s[2],s[3],s[4],s[5],s[6],s[7],s[8],s[9],s[10],s[11])end,['remove']=function(m,o)if not o then o='nil'end;if b[o..'_'..m]then b[o..'_'..m]=nil end;ui.removeTextArea(m,o)end},['string-to-boolean']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['parse']=function(t)local u={}for k,v in next,t do u[v]=true end;return u end},['database']={AUTHOR='Nettoork#0000',_VERSION='1.1',dependencies={},['create']=function(w,x)if not c[w]then c[w]=x end end,['delete']=function(w)c[w]=nil end,['get']=function(w,...)local y,z={},{...}if not z[1]then return c[w]else for k,v in next,z do if c[w][v]then y[#y+1]=c[w][v]end end;return table.unpack(y)end end,['set']=function(w,...)local A=v;for k,v in next,{...}do if not A then A=v else c[w][A]=v;A=false end end end},['encryption']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['encrypt']=function(t,B,C)if not t or not B or not C or type(t)~='table'or B==''or C==''then return end;local D,E,F,G='','','',''for k in B:gmatch('.')do D=D..k:byte()end;for k in C:gmatch('.')do G=G..k:byte()end;math.randomseed(D)otherSeed=math.random(1000000)local H=pcall(function()for k,v in next,t do if type(v)=='string'or type(v)=='number'then if type(v)=='string'then v="'"..v.."'"end;F=F..' '..v..' '..k:upper()else return end end;F=F..' '..G;for k in F:gmatch('.')do local I=k:byte()+68+math.random(5)otherSeed=otherSeed+k:byte()math.randomseed(otherSeed)if I>=65 and I<=122 and not(I>=91 and I<=96)then I=string.char(I)end;E=E..I end end)math.randomseed(os.time())if not H then return else return E end end,['decrypt']=function(J,B,C)if not J or not B or not C or type(J)~='string'or B==''or C==''then return end;local D,E,K,G='','','',''for k in B:gmatch('.')do D=D..k:byte()end;for k in C:gmatch('.')do G=G..k:byte()end;math.randomseed(D)otherSeed=math.random(1000000)local H=pcall(function()for k in J:gmatch('.')do if k:byte()>=65 and k:byte()<=122 then local I=k:byte()-68-math.random(5)otherSeed=otherSeed+I;math.randomseed(otherSeed)E=E..string.char(I)else K=K..k;if K:len()>=3 then local I=tonumber(K)-68-math.random(5)otherSeed=otherSeed+I;math.randomseed(otherSeed)E=E..string.char(I)K=''end end end end)math.randomseed(os.time())if not H then return else local u,L,M,N,O={},0,0;for k,v in string.gmatch(E,'[^%s]+')do M=M+1 end;for k,v in string.gmatch(E,'[^%s]+')do L=L+1;if L==M and k~=G then return elseif N then if N:sub(-1)=="'"then u[k:lower()]=N:gsub("'",'')N=nil else N=N..' '..k end elseif O then u[k:lower()]=O;O=nil elseif k:sub(1,1)=="'"then N=k else O=k end end;return u end end},['sleep']={AUTHOR='Nettoork#0000',_VERSION='1.1',dependencies={},['loop']=function()local P={}for k,v in next,a do if not v[2]or v[2]<os.time()then if coroutine.status(v[1])=='dead'then P[#P+1]=k else local Q,R=coroutine.resume(v[1])v[2]=R end end end;if P[1]then for k,v in next,P do a[v]=nil end end end,['run']=function(S,T)if not T then T=500 end;a[#a+1]={coroutine.create(function()local U=function(V)coroutine.yield(os.time()+math.floor(V/T)*T)end;S(U)end),timeValue=nil}end},['wait-time']={AUTHOR='Nettoork#0000',_VERSION='1.0',dependencies={},['check']=function(W,X,Y,Z)if W and X then if not d[W]then d[W]={}end;if not d[W][X]then Z=0;d[W][X]=os.time()+(Y or 1000)end;if d[W][X]<=os.time()or Z and Z==0 then d[W][X]=os.time()+(Y or 1000)return true else return false end end end},['json']={AUTHOR='https://github.com/rxi',_VERSION='0.1.1',dependencies={},['encode']=function(_)local a0;local a1={["\\"]="\\\\",["\""]="\\\"",["\b"]="\\b",["\f"]="\\f",["\n"]="\\n",["\r"]="\\r",["\t"]="\\t"}local a2={["\\/"]="/"}for a3,v in pairs(a1)do a2[v]=a3 end;local function a4(a5)return a1[a5]or string.format("\\u%04x",a5:byte())end;local function a6(_)return"null"end;local function a7(_,a8)local a9={}a8=a8 or{}if a8[_]then error("circular reference")end;a8[_]=true;if _[1]~=nil or next(_)==nil then local V=0;for a3 in pairs(_)do if type(a3)~="number"then error("invalid table: mixed or invalid key types")end;V=V+1 end;if V~=#_ then error("invalid table: sparse array")end;for k,v in ipairs(_)do table.insert(a9,a0(v,a8))end;a8[_]=nil;return"["..table.concat(a9,",").."]"else for a3,v in pairs(_)do if type(a3)~="string"then error("invalid table: mixed or invalid key types")end;table.insert(a9,a0(a3,a8)..":"..a0(v,a8))end;a8[_]=nil;return"{"..table.concat(a9,",").."}"end end;local function aa(_)return'"'.._:gsub('[%z\1-\31\\"]',a4)..'"'end;local function ab(_)if _~=_ or _<=-math.huge or _>=math.huge then error("unexpected number value '"..tostring(_).."'")end;return _ end;local ac={["nil"]=a6,["table"]=a7,["string"]=aa,["number"]=ab,["boolean"]=tostring}a0=function(_,a8)local ad=type(_)local S=ac[ad]if S then return S(_,a8)end;error("unexpected type '"..ad.."'")end;return a0(_)end,['decode']=function(ae)local af;local a2={["\\/"]="/"}local function ag(...)local a9={}for k=1,select("#",...)do a9[select(k,...)]=true end;return a9 end;local ah=ag(" ","\t","\r","\n")local ai=ag(" ","\t","\r","\n","]","}",",")local aj=ag("\\","/",'"',"b","f","n","r","t","u")local ak=ag("true","false","null")local al={["true"]=true,["false"]=false,["null"]=nil}local function am(ae,an,ao,ap)for k=an,#ae do if ao[ae:sub(k,k)]~=ap then return k end end;return#ae+1 end;local function aq(ae,an,ar)local as=1;local at=1;for k=1,an-1 do at=at+1;if ae:sub(k,k)=="\n"then as=as+1;at=1 end end;error(string.format("%s at line %d col %d",ar,as,at))end;local function au(V)local S=math.floor;if V<=0x7f then return string.char(V)elseif V<=0x7ff then return string.char(S(V/64)+192,V%64+128)elseif V<=0xffff then return string.char(S(V/4096)+224,S(V%4096/64)+128,V%64+128)elseif V<=0x10ffff then return string.char(S(V/262144)+240,S(V%262144/4096)+128,S(V%4096/64)+128,V%64+128)end;error(string.format("invalid unicode codepoint '%x'",V))end;local function av(Q)local aw=tonumber(Q:sub(3,6),16)local ax=tonumber(Q:sub(9,12),16)if ax then return au((aw-0xd800)*0x400+ax-0xdc00+0x10000)else return au(aw)end end;local function ay(ae,k)local az=false;local aA=false;local aB=false;local aC;for aD=k+1,#ae do local aE=ae:byte(aD)if aE<32 then aq(ae,aD,"control character in string")end;if aC==92 then if aE==117 then local aF=ae:sub(aD+1,aD+5)if not aF:find("%x%x%x%x")then aq(ae,aD,"invalid unicode escape in string")end;if aF:find("^[dD][89aAbB]")then aA=true else az=true end else local a5=string.char(aE)if not aj[a5]then aq(ae,aD,"invalid escape char '"..a5 .."' in string")end;aB=true end;aC=nil elseif aE==34 then local Q=ae:sub(k+1,aD-1)if aA then Q=Q:gsub("\\u[dD][89aAbB]..\\u....",av)end;if az then Q=Q:gsub("\\u....",av)end;if aB then Q=Q:gsub("\\.",a2)end;return Q,aD+1 else aC=aE end end;aq(ae,k,"expected closing quote for string")end;local function aG(ae,k)local aE=am(ae,k,ai)local Q=ae:sub(k,aE-1)local V=tonumber(Q)if not V then aq(ae,k,"invalid number '"..Q.."'")end;return V,aE end;local function aH(ae,k)local aE=am(ae,k,ai)local aI=ae:sub(k,aE-1)if not ak[aI]then aq(ae,k,"invalid literal '"..aI.."'")end;return al[aI],aE end;local function aJ(ae,k)local a9={}local V=1;k=k+1;while 1 do local aE;k=am(ae,k,ah,true)if ae:sub(k,k)=="]"then k=k+1;break end;aE,k=af(ae,k)a9[V]=aE;V=V+1;k=am(ae,k,ah,true)local aK=ae:sub(k,k)k=k+1;if aK=="]"then break end;if aK~=","then aq(ae,k,"expected ']' or ','")end end;return a9,k end;local function aL(ae,k)local a9={}k=k+1;while 1 do local G,_;k=am(ae,k,ah,true)if ae:sub(k,k)=="}"then k=k+1;break end;if ae:sub(k,k)~='"'then aq(ae,k,"expected string for key")end;G,k=af(ae,k)k=am(ae,k,ah,true)if ae:sub(k,k)~=":"then aq(ae,k,"expected ':' after key")end;k=am(ae,k+1,ah,true)_,k=af(ae,k)a9[G]=_;k=am(ae,k,ah,true)local aK=ae:sub(k,k)k=k+1;if aK=="}"then break end;if aK~=","then aq(ae,k,"expected '}' or ','")end end;return a9,k end;local aM={['"']=ay,["0"]=aG,["1"]=aG,["2"]=aG,["3"]=aG,["4"]=aG,["5"]=aG,["6"]=aG,["7"]=aG,["8"]=aG,["9"]=aG,["-"]=aG,["t"]=aH,["f"]=aH,["n"]=aH,["["]=aJ,["{"]=aL}af=function(ae,an)local aK=ae:sub(an,an)local S=aM[aK]if S then return S(ae,an)end;aq(ae,an,"unexpected character '"..aK.."'")end;if type(ae)~="string"then error("expected argument of type string, got "..type(ae))end;local a9,an=af(ae,am(ae,1,ah,true))an=am(ae,an,ah,true)if an<=#ae then aq(ae,an,"trailing garbage")end;return a9 end}}local aN=1547917483395+2628*10^6;require=function(aO)if aO=='hide-warning'then aN=false else if aN and os.time()>aN then aN=false;print("<R>Warning! You may be using an outdated version of require, check in <i>atelier801.com/topic?f=6&t=880333</i> if this has a better version, otherwise you can disable this warning with require('hide-warning').</R>")end;if e[aO]then if e[aO]['INIT_SCRIPT']and type(e[aO]['INIT_SCRIPT'])=='function'then e[aO]['INIT_SCRIPT']()e[aO]['INIT_SCRIPT']=nil end;return e[aO]elseif aO=='libs'then return e else print('Library "'..aO..'" not found! <i>Require Version: 1.2.2 | Author: Nettoork#0000</i>')return false end end end end


	-- [[ Bibliotecas ]] -- 
	local wait = require("wait-time")
	local stb = require("string-to-boolean")

	-- [[ Variaveis ]]

	local jogadores = {}
	local mapas = {'@7570168', '@7565291','@7570258'}
	local podeChecar = false
	local morto = false
	local vivo = false 
	local admins = stb.parse({"Sla#3700"})
	local timeToRemove=1.3
	local vivos = 0
	local timer = 0
	local timer1 = 0
	local cdwn

	-- [[ Traduções ]] ---
	lang = {
		br = {
			WELCOME = "<J>Bem Vindo a Vivo ou Morto, Se Aparecer um rato dançando, Dance, Se Aparecer um rato morto, durma. \n Caso esteja com shaman offline, digite !skip</J>",
			CHOICE = "<p align=\"center\"><font size=\"20\" color=\"#BABD2F\"><a href=\"event:vivo\">Vivo</a> || <a href=\"event:morto\">Morto</a>",
			RWIN = "<D>~[Juiz] ~ Os Jogadores Venceram!",
			ERROR1 = "<R>É Necessário possuir dois ou mais jogadores para o jogo iniciar!"
		},
		en = {
			WELCOME = "<J>Welcome to Alive or Dead, If a mouse shows up dancing, Dance, If a dead mouse appears, sleep.</J>",
			CHOICE = "<p align=\"center\"><font size=\"20\" color=\"#BABD2F\"><a href=\"event:vivo\">Alive</a> || <a href=\"event:morto\">Dead</a>",
			RWIN = "<D>~[Juiz] ~ The players won!",
			ERROR1 = "<R>Is necessary have two or more players to start the game!"
		},
		hu = {
			WELCOME = "<J>Üdv az Alive and Dead-ben, ha egy táncoló egeret látsz, táncolj, ha egy halott egeret látsz, aludj.</J>",
			CHOICE = "<p align=\"center\"><font size=\"20\" color=\"#BABD2F\"><a href=\"event:vivo\">Élő</a> || <a href=\"event:morto\">Halott</a>",
			RWIN = "<D>~[Juiz] ~ A játékosok nyertek!",
		},
		he = {
			WELCOME = "<J>ברוכים הבאים לחי או מת, אם מופיע עכבר רוקד, רקדו, אם מופיע עכבר מת, תשנו.</J>",
			CHOICE = "<p align=\"center\"><font size=\"20\" color=\"#BABD2F\"><a href=\"event:vivo\">חי</a> || <a href=\"event:morto\">מת</a>",
			RWIN = "<D>~[Juiz] ~ השחקנים ניצחו!",
		},
	}

	function translate(p, k)
		local cmm = tfm.get.room.playerList[p] and tfm.get.room.playerList[p].community or "en"
		cmm = lang[cmm] and cmm or "en"
		return lang[cmm][k] or "ERROR"
	end

	-- [[ Evento Novo Jogador ]] -- 
	eventNewPlayer = function(nome)
		jogadores[nome] = {
	 monstro = false,
	 fez = false
		}
	ajuda(nome)
	end

	-- [[ Função Enviar Mensagem De Ajuda ]] -- 
	ajuda = function (p)
	chatMsg(translate(p, "WELCOME"), p)
	end

	-- [[Considerar jogadores na sala como novos jogadores]] -- 
	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	-- [[ Evento Novo Jogo ]] --
	eventNewGame = function()
	cdwn = false
	skip = 0
	podeChecar = false
	vivos = 0
	for _ in pairs(tfm.get.room.playerList) do
	vivos = vivos + 1
	end
	iniciarounao()
	if vivos >= 2 then
	for nome, jogador in pairs(tfm.get.room.playerList) do
	if jogador.isShaman then
	jogadores[nome].monstro = true
	vivos = vivos - 1
	addtarea(0, translate(nome, "CHOICE"), nome, 286, 362, 198, 30, 0x077bb5, 0x000000, 1, true)
	tfm.exec.movePlayer(nome,400,121,false,0,0,true)
	tfm.exec.setNameColor(nome, 0xCB546B)
	end
	end
	for nome, jogador in pairs(tfm.get.room.playerList) do
	if not jogador.isShaman then
	tfm.exec.setNameColor(nome, 0xC45345B)
	jogadores[nome].monstro = false
	retarea(0, nome)
	end
	end
	end
	end
	-- [[ Evento Clicar No Evento Da Textarea ]] -- 
	eventTextAreaCallback = function(id, name, ref)
  if ref == "vivo"  then
  if timer1 > os.time() then return end
  pa = 1
  timer1 = os.time() + 3000
  timer = os.time() + 1200
  vivo = true
  morto = false
  local id2 = tfm.exec.addImage("168b09842dc.png", "&1", 252, 77, nil)
  system.newTimer(function()
  if id2 then
  tfm.exec.removeImage(id2)
  end
  end,timeToRemove*1000,false)
  elseif ref == "morto" then
  if timer1 > os.time() then return end
  vivo = false
  morto = true
  timer = os.time() + 1200
  timer1 = os.time() + 3000
  podeChecar = true
  local id = tfm.exec.addImage("16a1977936f.png", "&2", 186, 22, nil)
  local timeToRemove=3
  system.newTimer(function()
  if id then
  tfm.exec.removeImage(id)
  end
  end,timeToRemove*1000,false)
  end
  end
	-- [[ Evento que acontece a cada 500 milisegundos ]] -- 
 eventLoop = function(tc, tr)
 if tr == 0 then 
 if vivos <= 1 then
	tfm.exec.newGame('<C><P /><Z><S><S P="0,0,0.3,0.2,0,0,0,0" L="802" o="324650" X="400" Y="378" T="12" H="42" /><S P="0,0,0.3,0.2,0,0,0,0" L="10" o="324650" X="806" Y="404" T="12" H="10" /><S P="0,0,0,0,0,0,0,0" L="10" o="fffffffff" X="2" Y="198" T="12" H="405" /><S P="0,0,0.3,0.2,0,0,0,0" L="806" o="fffffffff" X="401" Y="1" T="12" H="10" /><S P="0,0,0,0,0,0,0,0" L="10" o="fffffffff" X="806" Y="202" T="12" H="412" /></S><D><DS Y="350" X="401" /></D><O /></Z></C>')
	else
 tfm.exec.newGame(mapas[math.random(#mapas)])
	end
 elseif vivo then
 cdwn = true
 pa = 1
 if timer > os.time() then return end
 for nome, v in pairs(tfm.get.room.playerList) do
 if jogadores[nome].fez == false and jogadores[nome].monstro == false and pa == 1 then
 killp(nome)
 print("oi")
 else
 jogadores[nome].fez = false
 end
 end
 vivo = false
 elseif morto then
 if timer > os.time() then return end
 cdwn = true
 for nome, v in pairs(tfm.get.room.playerList) do
 if jogadores[nome].fez == false and jogadores[nome].monstro == false and pa == 1 then
 killp(nome)
 else
 jogadores[nome].fez = false
 end
 end
 morto = false
 end
 end
	-- [[ Evento, Um Jogador fez um emoji ]] -- 
 function eventEmotePlayed(nome, eid)
 if vivo then
 if eid == 0 then
 jogadores[nome].fez = true
 else
 matar(nome)
 end
 end
 if morto then
 if eid == 6 then
 jogadores[nome].fez = true
 else
 matar(nome)
 end
 end
 end
	-- [[ Função, mata quem não compriu o proposto ]] --
 matar = function(...)
 if jogadores[...].monstro == true and cdwn == true then
 else
 killp(...)
 checaJogadores()
 end
 end
	-- [[ Função, Checa os Jogadores ]] --
 checaJogadores = function()
 if vivos <= 1 then
 for name, v in pairs(tfm.get.room.playerList) do
 if not v.isDead and not v.isShaman then 
 tfm.exec.setPlayerScore(name,25)
 chatMsg(translate(name, "RWIN"), name)
 end
 end
 tfm.exec.newGame(mapas[math.random(#mapas)])
 end
 end
	-- [[ Evento Jogador Morreu ]] -- 
 eventPlayerDied = function()
 vivos = vivos - 1
 checaJogadores()
 end
	-- [[ Evento Summonar um Item ]] -- 
 function eventSummoningStart(playerName, objectType, xPosition, yPosition, angle)
 tfm.exec.newGame(mapas[math.random(#mapas)])
 end
	-- [[ Evento Que acontece ao digitar um comando ]] -- 
 eventChatCommand = function(nome, cmd)
 local arg = {}
 for i in string.gmatch(cmd, "[^" .. (s or "%s") .. "]+") do
 arg[#arg + 1] = i
 end
 if (admins[nome]) and arg[1] == "admin" then
 admins[arg[2]] = true
 elseif (admins[nome]) and arg[1] == "np" then
 tfm.exec.newGame(arg[2])
 elseif (admins[nome]) and arg[1] == "score" then
 tfm.exec.setPlayerScore(arg[2],25, true)
 elseif arg[1] == "help" or arg[1] == "ajuda" then
 ajuda(nome)
 elseif arg[1] == "skip" then
 skip = skip + 1
 skipf()
 end
 end
	skipf = function()
	if skip >= 3 then
 tfm.exec.newGame(mapas[math.random(#mapas)])
	end
	end
	iniciarounao = function()
	if vivos <= 1 then
	for nome, jogador in pairs(tfm.get.room.playerList) do
	chatMsg(translate(name, "ERROR1"))
	end
	tfm.exec.newGame('<C><P /><Z><S><S P="0,0,0.3,0.2,0,0,0,0" L="802" o="324650" X="400" Y="378" T="12" H="42" /><S P="0,0,0.3,0.2,0,0,0,0" L="10" o="324650" X="806" Y="404" T="12" H="10" /><S P="0,0,0,0,0,0,0,0" L="10" o="fffffffff" X="2" Y="198" T="12" H="405" /><S P="0,0,0.3,0.2,0,0,0,0" L="806" o="fffffffff" X="401" Y="1" T="12" H="10" /><S P="0,0,0,0,0,0,0,0" L="10" o="fffffffff" X="806" Y="202" T="12" H="412" /></S><D><DS Y="350" X="401" /></D><O /></Z></C>')
	end
	end
	-- [[ Desabilita/Limita Coisas e Inicia um mapa do Module ]] --
 tfm.exec.disablePhysicalConsumables(true)
 tfm.exec.setRoomMaxPlayers(20)	
	tfm.exec.newGame('<C><P /><Z><S><S P="0,0,0.3,0.2,0,0,0,0" L="802" o="324650" X="400" Y="378" T="12" H="42" /><S P="0,0,0.3,0.2,0,0,0,0" L="10" o="324650" X="806" Y="404" T="12" H="10" /><S P="0,0,0,0,0,0,0,0" L="10" o="fffffffff" X="2" Y="198" T="12" H="405" /><S P="0,0,0.3,0.2,0,0,0,0" L="806" o="fffffffff" X="401" Y="1" T="12" H="10" /><S P="0,0,0,0,0,0,0,0" L="10" o="fffffffff" X="806" Y="202" T="12" H="412" /></S><D><DS Y="350" X="401" /></D><O /></Z></C>')
end

modules.id = function()
	eventNewPlayer = function(n)
		tfm.exec.chatMessage("<N>Welcome, " .. n .. ".\nYour Transformice ID is '<ROSE><B>" .. tfm.get.room.playerList[n].id .. "</B></ROSE>'", n)
	end
	for n in next, tfm.get.room.playerList do eventNewPlayer(n) end
end

modules.mastermind = function()
	local secToDate = function(s)
		local m = (s / 60) % 60
		local h = (s / 3600) % 24

		s = s % 60

		return string.format("%02dh%02dm%02ds", h, m, s)
	end

	local info = { }

	local genData
	do
		genData = function()
			local numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }

			local number, n = { _set = { } }
			for i = 1, 4 do
				n = table.remove(numbers, math.random((i == 1 and 2 or 1), #numbers))
				number[i] = n
				number._set[n] = true
			end

			return {
				time = 0,
				tentative = 1,
				digit = 1,
				sequence = { _set = { } },
				number = number,
				restart = false
			}
		end
	end

	local input = function(playerName, id, highlighted)
		ui.addTextArea(id, "<p align='center'><font size='20'>" .. (type(highlighted) ~= "boolean" and highlighted or ''), playerName, 243 + ((id - 1) * 60), 60, 40, 30, (highlighted == true and 0x323232 or 1), 1, .7, true)
	end

	local str = "<p align='center'><font size='20'>"

	local displayInterface = function(playerName)
		ui.addTextArea(-2, "<p align='center'><font size='16'>  Guessed    Exist   Match", playerName, 5, 25, 220, 365, 1, 1, .5, true)
		ui.addTextArea(-1, "<p align='center'><font size='16'>Enter number [0-9]", playerName, 233, 25, 243, 365, 1, 1, .5, true)
		ui.addTextArea(0, str, playerName, 233, 100, 243, nil, 1, 1, 0, true)

		local displayFields, y = 4
		for i = 1, 4 do
			input(playerName, i, (i == 1))
		end
		for i = 0, 6 do
			displayFields = displayFields + 3
			y = 60 + (i * 48)
			ui.addTextArea(displayFields - 2, str, playerName, 15, y, 80, 30, 1, 1, .7, true)
			ui.addTextArea(displayFields - 1, str, playerName, 115, y, 40, 30, 1, 1, .7, true)
			ui.addTextArea(displayFields    , str, playerName, 175, y, 40, 30, 1, 1, .7, true)
		end
	end

	local newPlayer = function(playerName)
		info[playerName] = genData()
		displayInterface(playerName)
	end

	local bytes = { 16, 46, string.byte(" 0123456789abcdefghij`", 1, -1) }
	eventNewPlayer = function(playerName)
		newPlayer(playerName)

		for i = 1, #bytes do
			system.bindKeyboard(playerName, bytes[i], true, true)
		end
		tfm.exec.setPlayerScore(playerName, 0)

		tfm.exec.chatMessage("<O>Type <B>!help</B> to learn how play.</O>\n<D>Thanks to <O><B>Blank#3495</B></O> and his great ideas</D>", playerName)
	end
	for playerName in next, tfm.get.room.playerList do
		eventNewPlayer(playerName)
	end

	eventNewGame = function()
		for playerName in next, tfm.get.room.playerList do
			tfm.exec.killPlayer(playerName)
		end
	end

	eventLoop = function()
		for playerName, data in next, info do
			if not data.restart then
				data.time = data.time + .5
				if data.time % 1 == 0 then
					ui.updateTextArea(0, "<D><B>" .. secToDate(data.time) .. "</B></D>\n\n\nPress <D><B>Space</B></D> to send the number;\n\nPress <D><B>Shift</B></D> to erase the last number;\n\nPress <D><B>Delete</B></D> to start a new game.", playerName)
				end
			end
		end
	end

	eventKeyboard = function(playerName, key)
		local k = key
		key = tonumber(string.char(key))

		if k == 46 then
			return newPlayer(playerName)
		elseif k == 32 then
			if info[playerName].restart then
				return newPlayer(playerName)
			end

			if info[playerName].digit < 5 then return end

			local exist, match = 0, 0
			for i = 1, 4 do
				if info[playerName].number._set[info[playerName].sequence[i]] then
					exist = exist + 1
				end
				if info[playerName].number[i] == info[playerName].sequence[i] then
					match = match + 1
				end
			end

			local tentative = info[playerName].tentative * 3
			ui.updateTextArea(4 + tentative - 2, table.concat(info[playerName].sequence, ''), playerName)
			ui.updateTextArea(4 + tentative - 1, exist, playerName)
			ui.updateTextArea(4 + tentative, match, playerName)

			info[playerName].tentative = info[playerName].tentative + 1
			if info[playerName].tentative < 8 then
				info[playerName].digit = 1
				info[playerName].sequence = { _set = { } }
				for i = 1, 4 do
					input(playerName, i, (i == 1))
				end
			end

			if match == 4 then
				tfm.exec.chatMessage("<O>You <B>won</B> (<B>" .. secToDate(info[playerName].time) .. "</B>)! The number was <D><B>" .. table.concat(info[playerName].number, '') .. "</B></D>. Press <B>Space</B> to play again.</O>", playerName)
				tfm.exec.setPlayerScore(playerName, 1, true)
				info[playerName].restart = true
			else
				if info[playerName].tentative == 8 then
					tfm.exec.chatMessage("<O>You <B>lose</B>! The number was <D><B>" .. table.concat(info[playerName].number, '') .. "</B></D>. Press <B>Space</B> to play again.</O>", playerName)
					info[playerName].restart = true
				end
			end
		else
			if info[playerName].restart then return end
			if k == 16 then
				info[playerName].digit = math.max(1, info[playerName].digit - 1)
				if not info[playerName].sequence[info[playerName].digit] then return end

				info[playerName].sequence._set[info[playerName].sequence[info[playerName].digit]] = nil
				info[playerName].sequence[info[playerName].digit] = nil

				input(playerName, math.min(4, info[playerName].digit + 1))
				input(playerName, info[playerName].digit, true)
			else
				if not tonumber(key) then
					key = tonumber(string.char(k - 48))
				end
				if info[playerName].digit == 1 and key == 0 then return end
				if info[playerName].sequence._set[key] then return end
				if info[playerName].digit == 5 then return end

				info[playerName].sequence._set[key] = true
				info[playerName].sequence[info[playerName].digit] = key
				input(playerName, info[playerName].digit, key)

				info[playerName].digit = info[playerName].digit + 1
				if info[playerName].digit < 5 then
					input(playerName, info[playerName].digit, true)
				end
			end
		end
	end

	eventChatCommand = function(playerName, command)
		if command == "help" then
			tfm.exec.chatMessage("<O>Computer selects a four digit number [0-9], all four digits are different. Number may not begin with 0. Any number can be guessed in 7 tries or less.\n\t- <B>Exist</B> column displays total number of digits you guessed right.\n\t- <B>Match</B> shows how many of those that exists were placed at the right spots.</O>", playerName)
		end
	end

	eventPlayerLeft = function(playerName)
		info[playerName] = nil
	end

	system.disableChatCommandDisplay()
	tfm.exec.disableAutoNewGame()
	tfm.exec.disableAutoScore()
	tfm.exec.disableAutoShaman()
	tfm.exec.newGame('<C><P /><Z><S /><D /><O /></Z></C>')
end

modules.evt_halloween_muertos = function()
	do
		system.savePlayerData = function(player, data)
			PLAYERDATA[player] = data
		end
		system.loadPlayerData = function(player)
			eventPlayerDataLoaded(player, PLAYERDATA[player] or '')
		end
	end
	tfm.exec.disableAutoNewGame()

	--[[ Module Info ]]--
	local module = {
		name = "hw8",
		team = {
			"Bolodefchoco#0000",
			"Albinoska#0000"
		},
		prize = {
			badge = { "badge_muertos", 2^0 },
			orb = { "orb_muertos", 2^1 }
		},
		time = 1.85 * 60,
		map = '<C><P L="2600" H="800" /><Z><S><S L="822" H="10" X="490" Y="605" T="12" P=",,.3,.2,10,,," /><S L="895" H="10" X="1650" Y="717" T="12" P=",,.3,.2,1,,," /><S L="385" H="10" X="2285" Y="765" T="12" P=",,.3,.2,12,,," /><S L="200" H="10" X="2501" Y="789" T="12" P=",,.3,.2,,,," /><S L="60" H="45" X="2250" Y="660" T="12" P=",,.7,.2,,,," /><S L="115" H="95" X="2360" Y="635" T="12" P=",,.3,.2,,,," /><S L="30" H="80" X="2390" Y="540" T="12" P=",,.3,.2,,,," /><S L="22" X="2390" H="10" Y="450" T="13" P=",,.36,.2,,,," /><S L="15" X="2390" H="10" Y="485" T="13" P=",,.3,.2,,,," /><S L="191" X="2617" H="10" Y="423" T="13" P=",,.3,.2,,,," /><S L="161" H="10" X="2280" Y="298" T="12" P=",,.3,.2,,,," /><S L="95" H="10" X="1953" Y="158" T="12" P=",,.3,.2,,,," /><S L="95" H="10" X="2044" Y="172" T="12" P=",,.3,.2,18,,," /><S L="95" H="10" X="2127" Y="215" T="12" P=",,.3,.2,36,,," /><S L="71" H="10" X="2185" Y="270" T="12" P=",,.3,.2,54,,," /><S L="67" H="10" X="1790" Y="207" T="12" P=",,.3,.2,324,,," /><S L="95" H="10" X="1862" Y="172" T="12" P=",,.3,.2,342,,," /><S L="332" H="10" X="1601" Y="226" T="12" P=",,.3,.2,,,," /><S L="229" H="10" X="1243" Y="155" T="12" P=",,.3,.2,34,,," /><S L="116" H="10" X="1093" Y="92" T="12" P=",,.3,.2,,,," /><S L="325" H="10" X="1417" Y="299" T="12" P=",,.3,.2,,,," /><S L="200" H="10" X="1202" Y="380" T="12" P=",,.3,.2,-56,,," /><S L="130" H="10" X="1663" Y="470" T="12" P=",,.3,.2,,,," /><S L="199" H="10" X="2274" Y="166" T="12" P=",,.3,.2,,,," /><S L="194" H="10" X="1053" Y="461" T="12" P=",,.3,.2,,,," /><S L="10" H="104" X="70" Y="487" T="12" P=",,.3,.2,-20,,," /><S L="10" H="40" X="53" Y="420" T="12" P=",,.3,.2,,,," /><S L="10" H="85" X="69" Y="363" T="12" P=",,,,22,,," /><S L="92" H="10" X="1079" Y="557" T="12" P=",,.3,.2,,,," /><S L="115" H="10" X="1304" Y="494" T="12" P=",,.3,.2,,,," /><S L="118" H="10" X="1455" Y="616" T="12" P=",,.3,.2,,,," /><S L="70" H="10" X="554" Y="529" T="12" P=",,1.2,.2,-35,,," /><S L="156" H="10" X="653" Y="476" T="12" P=",,.3,.2,-25,,," /><S L="156" H="10" X="798" Y="423" T="12" P=",,.3,.2,-15,,," /><S L="87" H="10" X="915" Y="399" T="12" P=",,1,.2,-5,,," /><S L="20" H="53" X="949" Y="319" T="12" P=",,.7,.2,,,," /><S L="10" H="53" X="950" Y="269" T="12" P=",,.3,.2,14,,," /><S L="10" H="53" X="969" Y="222" T="12" P=",,.3,.2,29,,," /><S L="10" H="23" X="990" Y="195" T="12" P=",,.3,.2,61,,," /><S L="176" H="10" X="1081" Y="211" T="12" P=",,.6,.2,14,,," /><S Y="510" L="30" X="1480" H="60" i="-10,-60,1660d7b34f1.png" T="12" P="1,150,.3,.2,,,,25" /><S L="10" H="800" X="2596" Y="394" T="12" P=",,,,,,," /><S L="130" H="10" X="1953" Y="286" T="12" P=",,.3,.2,,,," /><S L="312" X="1048" H="10" Y="693" T="12" P=",,.3,.2,6,,," /><S L="10" X="1431" H="30" Y="237" T="12" P=",,.3,.2,30,,," /></S><D><DS Y="699" X="1729" /></D><O /><L><JR LIM2=".7854" LIM1="-.7854" P1="1480,400" M1="40" /></L></Z></C>',
		confetti_to_fix_pinata = 50,
		candies_to_finish_event = 30,
		time_to_teleport_candy = 15 * 1000,
		total_guitars_in_simon_says = 8
	}

	--[[ Development ]]--
	local translations = {
		en = {
			dialogs = {
				-- Mirabella
				[1] = "Hey there!\nMy name is Mirabella, the ghost of Halloween!\n\nThe Day of the Dead is coming soon and the party will start, but while decorating the city, the Piñata, Sesame, got its paw ripped. I mended it, but all the confetti got scattered across the city. The citizens are searching for them, could you help us?",
				[2] = "Yaaaaaaaay!!!!\nThank you very much! Sesame is feeling much better now!\n\nIt's time to prepair the sweets and the music!",
				[3] = "Oh... Hey! It's me again...\n\nI was checking if everything was okay to start the party when I noticed something terrible... SESAME IS EMPTY! THE CANDIES DISAPPEARED!\n\nYour help fixing its paw was great. I wonder if you could help us to find the candies that fell from the paw gap!?",
				[4] = "Wooooooooow!\n\nThank you very much for your help!\n\nNow we all can relax and party with the dead!",
				-- Miguel
				[5] = "Se-Sesame's paw go-got ripped... :(\n\nPlease he-help him :(",
				[6] = "Ooooh... Dear citizen...\nI was so worried about Sesame's paw that I totally forgot about my guitar... I think I lost it!\n\nWithout it there won't be music and the festival will be ruined!\nCan you help me to find it? If so, let me show you the lyrics!",
				[7] = "Yuuupiiiii! You found it! Oh my dead mice, thank you so much!!!!\n\nNow everybody is going to listen my awesome music!"
			},
			close_dialog = "Press spacebar to close the dialog.",
			welcome = "This is the <B>Day of the Dead</B>, where the dead and the living can party together!\n\n~ ~ ~ Let's shake the bones! ~ ~ ~",
			conclusion = "Wooow! What a lovely pumpkin sweet you are! Now the ghosts can finally party and eat sugar!!!\n\nThanks to <B>Mirabella</B> for bringing the dead to our world, <B>%s</B> for coding the whole magic and <B>%s</B> to draw everything used in this awesome event!",
			failed_colors = "Don't let the ghosts scare you, pay attention to the colors and make it correct in the next time!",
		},
		ar = {
			dialogs = {
				[1] = "!مرحبًا\n!اسمي ميرابيلا، شبح الهالووين\n\nسيأتي يوم الموتى قريبا وستبدأ الحفلة، ولكن أثناء تزيين المدينة، تمزق ساق حيوان الحلوى الأليف، سيزام. لقد أصلحته، لكن كل الأوراق تبعثرت في جميع أنحاء المدينة. المواطنون يبحثون عنها، هل يمكنك مساعدتنا؟",
				[2] = "!!!!ياااااااي\n!شكرا جزيلًا! سيزام يشعر بتحسنٍ كبيرٍ الآن\n\n!حان الوقت لإعداد الحلويات و الموسيقى",
				[3] = "...أوه... أهلًا! هذه أنا من جديد\n\nكنت أتحقق مما إذا كان كل شيء على ما يرام لبدء الحفلة الى أن لاحظت شيئًا فظيعًا... إن سيزام فارغ! لقد اختفت الحلوى!\n\nمساعدتك في إصلاح ساقه كانت رائعة. أتساءل إذا كان بإمكانك مساعدتنا في العثور على الحلوى التي سقطت من فجوة ساقه!؟",
				[4] = "!واااااااو\n\n!شكرًا جزيلًا لمساعدتك\n\n!يمكننا الاسترخاء الآن و الاحتفال مع الموتى",
				[5] = "): ...لقد تمزق ساق سـ-سيزام\n\n:( سـ-ساعده أرجوك",
				[6] = "...أوه... عزيزي المواطن\n!كنت قلقًا جدًا على ساق سيزام حتى أنني نسيت تمامًا أمر غيثاري... أعتقد أنني فقدته\n\n!بدونه لن تكون هناك موسيقى وسيدمر المهرجان\n!هل يمكنك مساعدتي في العثور عليه؟ إذا كان الأمر كذلك، دعني أريك كلمات الأغاني",
				[7] = "!!!!يييوووووووبييي! لقد وجدته! يا فئراني الميّتة، شكرًا جزيلا لك\n\n!الآن سيستمع الجميع إلى موسيقتى الرائعة"
			},
			close_dialog = ".اضغط زر المسافة لإغلاق مربع الحوار",
			welcome = "!أين يمكن للموتى و الأحياء الاحتفال معًا ،<B>يوم الموتى</B> هذا هو \n\n~ ~ ~ !دعونا نهز العظام ~ ~ ~",
			conclusion = "!!!واااو! يا لك من يقطينٍ حلوِ المذاق! الآن يمكن للأشباح أخيرًا الاحتفال و أكل السكر\n\nلجلب الموتى لعالمنا <B>لميرابيلا</B> شكرًا\n لبرمجة السحر كله <B>%s</B>\n ! لرسم كل شيء لهذا الحدث الرائع <B>%s</B> و",
			failed_colors = "!لا تدع أشباح تخيفك، وانتبه جيّدًا إلى الألوان و اجعلها صحيحة في المرة القادمة",
		},
		br = {
			dialogs = {
				[1] = "Oi pessoal!\nMeu nome é Mirabella, a fantasma do Halloween!\n\nO Dia dos Mortos está chegando e a festa vai começar, mas enquanto a cidade estava sendo decorada, a pinhata Sesame teve parte da sua pata rasgada. Eu a costurei, mas todas as fitas coloridas ficaram espalhadas pela cidade. Os cidadãos estão procurando por elas, você poderia ajudá-los?", 
				[2] = "Obaaaaaaaaa!!!!\nMuito obrigado! O Sesame está se sentindo muito melhor agora!\n\nÉ hora de preparer os doces e a música!",
				[3] = "Ah... Oi! Sou eu de novo...\n\nEu estava checando se estava tudo certo para começar a festa quando notei algo terrível... O SESAME ESTÁ VAZIO! OS DOCES SUMIRAM!\n\nSua ajuda consertando a pata dele foi incrível. Será que você poderia nos ajudar a achar os doces que caíram da pata do Sesame!?",
				[4] = "Uoooooou!\n\nMais uma vez, muito obrigada pela sua ajuda!\n\nAgora todos nós podemos relaxar e festejar com os mortos!",
				[5] = "A pa-pata do Se-Sesame ra-rasgou... :(\n\nPo-por favor, ajude-o :(",
				[6] = "Aaaah... Querido cidadão...\nEu estava tão preocupado com a pata do Sesame que eu esqueci totalmente do meu violão... Acho que o perdi!\n\nSem eles, não haverá música e o festival estará arruinado!\nVocê pode me ajudar a encontrá-lo? Caso sim, deixe-me mostrar a canção!",
				[7] = "Yuuuupiiii! Você encontrou! Oh meu ratinho, muito obrigado!!!!\n\nAgora todo mundo vai escutar minha ótima música!"
			},
			close_dialog = "Aperte a barra de espaço para fechar.",
			welcome = "Este é o <B>Dia dos Mortos</B>, onde os mortos e os vivos festejam juntos!\n\n~ ~ ~ Vamos mexer o esqueleto! ~ ~ ~",
			conclusion = "Wooow! Você é um docinho! Agora os fantasmas podem finalmente festejar e comer muuuitos doces!!!\n\nObrigado a <B>Mirabella</B> por trazer os mortos ao nosso mundo, <B>%s</B> por codificar toda a mágica e <B>%s</B> por desenhar tudo neste incrível evento!",
			failed_colors = "Não deixem os fantasmas te assustar, preste atenção nas cores e faça a ordem corretamente na próxima vez!",
		},
		cn = {
			dialogs = {
				[1] = "嘿嘿嘿!\n我是米拉贝拉, 一只万圣节的鬼怪!\n\n派对将在死亡之日来临的时间开始, 可是在布置城市的时候, 一个叫胡麻的皮纳塔, 手部不小心被拉断了。 我修补好他, 但是五彩纸屑却吹散满布整个城市了。 其他市民都在寻找收集纸屑, 你会帮忙吗?",
				[2] = "好~~~~好棒!!!!\n谢谢你的帮忙! 胡麻现在好多了!\n\n那现在是时候准备糖果和音乐了!",
				[3] = "噢... 你好! 又是我了...\n\n我刚刚在检查东西是不是都准备好开始派对的时候, 发现了可怕的事实... 胡麻的身体被掏空了! 糖果消失了!\n\n你帮胡麻修补的伤口不错。你可以再帮我找回从他手上的伤口丢失的糖果吗!?",
				[4] = "哇哇哇哇!\n\n真的好感谢你!\n\n我们现在终于可以放松, 跟死人狂欢派对了",
				[5] = "胡 胡麻的手掌断 断了... :(\n\n请帮 帮帮他 :(",
				[6] = "噢... 亲爱的市民...\n我好担心胡麻的手掌, 甚至忘掉了自己的吉他... 它应该是不見了!\n\n没有吉他就没有音乐了, 庆典也会要取消了!\n你可以帮我找找吗? 如果可以, 我给你歌词看看!",
				[7] = "啊啊啊! 你找回它了! 我可爱的死老鼠, 真的谢谢了!!!!\n\n现在所有人都可以听到我酷死的音乐了!"
			},
			close_dialog = "按空格键关闭对话框。",
			welcome = "这是 <B>死亡之日</B>, 是亡者跟在生的人可以一起派对的日子!\n\n~ ~ ~ 一起舞动骨头吧! ~ ~ ~",
			conclusion = "哇啊! 你这南瓜真的可爱死了! 现在鬼怪终于可以举行派对和吃糖果了!!!\n\n感谢 <B>米拉贝拉</B> 带亡者到我们的世界, <B>%s</B> 为魔法编码以及 <B>%s</B> 为这神奇活动绘图!",
			failed_colors = "不要让那些鬼怪吓坏你, 留意颜色在下一次修正吧!",
		},
		ee = {
			dialogs = {
				[1] = "Tere seal!\nMinu nimi on Mirabella, Halloweeni kummitus!\n\nSurnute päev tuuleb varsti ja pidu algab, aga linna kaunistamise ajal, Piñata, Sesame, tuli jalg ära. Ma lappisin seda, aga kõik konfetti langes linna peale laiali. Linnlased otsivad neid, kas sa aitaksid meid?",
				[2] = "Jeeeeeeee!!!!\nSuur aitäh! Sesame tunneb ennast palju paremini nüüd!\n\nOn aeg valmis panna maiustused ja muusika!",
				[3] = "Oh... Hei! See olen mina jälle...\n\nMa käisin kontrollimas, et kas kõik on peoks valmis, kui ma nägin midagi kohutavat... SESAME ON TÜHI! KOMMID ON KADUNUD!\n\nSinu jala parandamise abi oli tore. Ma siin mõtlesin, et kas sa aitaksid meil kommid üles otsida, mis kukkusid jala august välja!?",
				[4] = "Vauuuuuuuuuuu!\n\nSuur aitäh sinu abi eest!\n\nNüüd me saame lõõgastuda ja pidutseda surnutega!",
				[5] = "Se-Sesame jalg on ära tulnud... :(\n\nPalun a-aita teda :(",
				[6] = "Ooooh... Kallid kodanukud...\nMa olin nii mures Sesame käpa pärast, et ma sootuks unustasin oma kitarri... Ma arvan, et ma kaotasin selle!\n\nIlma selleta ei ole muusikat ja festival on rikutud!\nKas sa aitaksid mul seda leida? Kui nii siis las ma näitan sulle laulusõnu!",
				[7] = "Jeeeeeee! Sa leidsid selle! Oh mu surnud hiired, suur aitäh sulle!!!!\n\nNüüd kõik saavad kuulata mu lahedat muusikat!"
			},
			close_dialog = "Vajuta tühikut, et sulgeda dialoog.",
			welcome = "See on <B>Surnute Päev</B>, kus surnud ja elavad pidutsevad koos!\n\n~ ~ ~ Las väristagu konte! ~ ~ ~",
			conclusion = "Wooow! Kui armas kõrvitsakene sa oled! Viimaks nüüd saavad kummitused pidutseda ja süüa magusat!!!\n\nTänu <B>Mirabellale</B>, et ta tõi surnud siia ilma, <B>%s</B> selle maagia kodeerimise eest ja <B>%s</B> kes joonistas kõik mis siin sündmuses on!",
			failed_colors = "Ära lase kummitustel sind ehmatada, pööra tähelepanu värvidele ja tee see õigesti järgmine kord!",
		},
		es = {
			dialogs = {
				[1] = "¡Hola!\nMi nombre es Mirabella, ¡el fantasma de Halloween!\n\nEl Día de Muertos se está acercando y la fiesta va a comenzar, pero mientras decoraba la ciudad, la Piñata, Sesame, se rompió la pata. Se la he arreglado, pero el confeti ha quedado esparcido por toda la ciudad. Los ciudadanos están recogiéndolo, ¿nos podrías ayudar?",
				[2] = "Hurraaaa!!!!\n¡Muchas gracias! ¡Sesame ahora se siente mucho mejor!\n\n¡Es hora de preparar los caramelos y la música!",
				[3] = "Oh... ¡Hola! Soy yo otra vez...\n\nEstaba asegurándome de que todo estuviera bien para comenzar la fiesta cuando me di cuenta de algo terrible... ¡SESAME ESTÁ VACÍA! ¡LOS CARAMELOS HAN DESAPARECIDO!\n\nHas hecho un buen trabajo arreglándole la pata. ¿¡Me pregunto si podrías ayudarnos a encontrar los caramelos que se cayeron por el agujero de la pata!?",
				[4] = "¡Guaaaaaaaau!\n\n¡Muchas gracias por tu ayuda!\n\n¡Ahora podemos relajarnos y divertirnos con los muertos!",
				[5] = "La-la pata de Se-Sesame se ha ro-roto... :(\n\nPor favor, a-ayúdale :(",
				[6] = "Ooooh... Querido ciudadano...\nEstaba tan preocupado por la pata de Sesame que me olvidé de mi guitarra... ¡Creo que la he perdido!\n\n¡Sin ella no habrá música y el festival estará arruinado!\n¿Puedes ayudarme a encontrarla? En tal caso, ¡déjame enseñarte la letra!",
				[7] = "¡Yuuupiiiii! ¡La has encontrado! Oh mis difuntos ratones, ¡¡¡¡muchas gracias!!!!\n\n¡Ahora todo el mundo va a escuchar mi fantástica música!"
			},
			close_dialog = "Presiona el espacio para cerrar el diálogo.",
			welcome = "¡Este es el <B>Día de Muertos</B>, en el que los muertos y los vivos pueden divertirse juntos!\n\n~ ~ ~ ¡Vamos a sacudir los huesos! ~ ~ ~",
			conclusion = "¡Guaaau! ¡Qué calabaza tan dulce y bonita eres! ¡¡¡Por fin los fantasmas pueden divertirse y comer azúcar!!!\n\n¡Gracias a <B>Mirabella</B> por traer a los muertos a nuestro mundo, a <B>%s</B> por programar toda la magia y a <B>%s</B> por dibujar todo en este maravilloso evento!",
			failed_colors = "¡No dejes que los fantasmas te asusten, presta atención a los colores y hazlo correctamente la próxima vez!",
		},
		fr = {
			dialogs = {
				[1] = "Salut toi !\nMon nom est Mirabella, le fantôme de Halloween !\n\nLe Jour de la Mort arrive bientôt et la fête va pouvoir commencer. Mais tandis qu'on décorait la ville, la Piñata, Sésame, a eu ses jambes déchirées. Je l'ai réparée, mais tous les confettis se sont dispersés à travers la ville. Les citoyens les cherchent partout... pourrais-tu nous aider ?",
				[2] = "Yeeeeaaaaay !!!!\nMerci infiniment ! Sésame a l'air d'aller beaucoup mieux maintenant !\n\nC'est l'heure de préparer les gourmandises et la musique !",
				[3] = "Oh... Salut ! C'est encore moi...\n\nJ'étais en train de vérifier que tout était bon pour démarrer la fête lorsque j'ai appris quelque chose d'horrible... SÉSAME A ÉTÉ RETROUVÉE VIDE ! LES BONBONS ONT DISPARU !\n\nMerci pour m'avoir aidée à réparer ses jambes. C'était vraiment top ! En fait, je me demandais si tu pourrais nous aider à retrouver les bonbons qui sont tombés de la brèche d'une de ses jambes...?",
				[4] = "Wooow !\n\nMerci beaucoup pour ton aide !\n\nMaintenant on va pouvoir se détendre et faire la fête avec la Mort !",
				[5] = "La jam-jambe de Sésame... s'est déchirée... :(\n\nS'il te plait... aide-la... :(",
				[6] = "Ooooh... Cher citoyen...\nJ'étais tellement inquiet pour les pattes de Sésame que j'ai totalement oublié ma guitare... Je pense l'avoir perdue !\n\nSans celle-ci, il ne pourra pas y avoir de musique et le festival sera ruiné !\nPeux-tu m'aider à la retrouver ? Si c'est le cas, laisse-moi te montrer les paroles !",
				[7] = "Youupiiiii ! Tu l'as trouvée ! Oh merciiii, ma chère souris morte !!!!\n\nMaintenant tout le monde va pouvoir écouter ma superbe musique !"
			},
			close_dialog = "Appuyez sur espace pour fermer le dialogue",
			welcome = "Voici le <B>Jour de la Mort</B>, où les morts et les vivants peuvent faire la fête ensemble !\n\n~ ~ ~ Allez, on se remue les os ! ~ ~ ~",
			conclusion = "Wooow ! Quelle merveilleuse citrouille tu peux être ! Maintenant, les fantômes peuvent enfin faire la fête et manger des tonnes de sucreries !!!\n\nMerci à <B>Mirabella</B> pour avoir amené les morts dans notre monde, <B>%s</B> pour avoir codé toute cette magie et <B>%s</B> pour tout avoir dessiné dans cet extraordinaire événement !",
			failed_colors = "Ne laisse pas les fantômes t'effrayer, fais attention aux couleurs et corrige-les pour la prochaine fois !",
		},
		he = {
			dialogs = {
				[1] = "שלום לכם!\nשמי הוא Mirabella, הרוח של ליל כל הקדושים!\n\nיום המתים מגיע בקרוב ואז המסיבה תתחיל, אבל בזמן קישוטי העיר, לפיניאטה, Sesame, נקרעה הרגל. תיקנתי אותה, אבל כל הקונפטי התפזר ברחבי העיר. האזרחים מחפשים עבורם, תוכלו לעזור לנו?",
				[2] = "יששששששש!!!!\nתודה רבה לכם! Sesame מרגיש הרבה יותר טוב עכשיו!\n\nהגיע הזמן לתקן את הממתקים ואת המוזיקה!",
				[3] = "אה... שלום! זאת אני שוב...\n\nאני בדקתי אם הכל היה בסדר כדי שנוכל להתחיל את המסיבה ואז שמתי לב למשהו נורא... SESAME ריק! כל הממתקים נעלמו!\n\nעזרתכם בתיקון הרגל שלו הייתה נהדרת. אני תוהה אם תוכלו לעזור בלמצוא את הממתקים שנפלו מהחור ברגל שלו!?",
				[4] = "וואאאאאאוווווווו!\n\nתודה רבה מאוד על עזרתכם!\n\nעכשיו כולנו יכולים להירגע וחגוג עם המתים!",
				[5] = "ה-הרגל של Se-Sesame's נ-נקרעה... :(\n\nבבקשה ע-עזרו לו :(",
				[6] = "הוווו... אזרחים יקרים...\nכל כך דאגתי לכף הרגל של Sesame ששכחתי לגמרי מהגיטרה שלי... אני חושב שאיבדתי אותה!\n\nבלעדיה לא תהיה מוזיקה והפסטיבל יהרס!\nאתם יכולים לעזור לי למצוא אותה? אם כך, תנו לי להראות לכם את המילים!",
				[7] = "יייפפפיייייי! מצאתם אותה! אוי עכבריי המתים, תודה רבה לכם!!!!\n\nעכשיו כולם יקשיבו למוזיקה המדהימה שלי!"
			},
			close_dialog = "לחצו על מקש הרווח כדי לסגור את דו השיח.",
			welcome = "זהו <B>יום המתים</B>, היכן שחיים והמתים יכולים לחגוג ביחד!\n\n~ ~ ~ בואו נענע את העצמות! ~ ~ ~",
			conclusion = "ווואו! איזו דלעת מתוקה אתה! עכשיו הרוחות יכול סוף סוף לחגוג ולאכול סוכר!!!\n\nבזכות <B>Mirabella</B> שהביאה את המתים לעולמנו, <B>%s</B> בשביל הקסם וקידוד <B>%s</B> בשביל הציורים לאיוונט המדהים הזה!",
			failed_colors = "אל תתנו לרוחות להפחיד אתכם, שימו לב לצבעים ועשו זאת נכונה בפעם הבאה!",
		},
		hu = {
			dialogs = {
				[1] = "Hellóka!\nMirabella vagyok, a Halloween szelleme!\n\nHamarosan itt a Halottak Napja és kezdődni fog a parti is. Sajnos a város dekorálása közben a Piñata, Sesame, mancsát valaki leszakította. Visszaraktam, de a konfetti lejött róla és szétszóródott a városban. A városlakók már elkezdték keresni a darabokat, tudnál segíteni te is nekünk?",
				[2] = "Yaaaaaaaay!!!!\nNagyon szépen köszönöm! Sesame már sokkal jobban érzi magát!\n\nIdeje előkészíteni az édességeket és a zenét!",
				[3] = "Oh... Helló! Én vagyok az újból...\n\nÉpp a végső ellenőrzést végeztem a buli kezdete előtt, és észrevettem valami szörnyűséget... SESAME ÜRES! ELTŰNTEK A CUKORKÁK!\n\nNagyon sokat segítettél a mancsa megjavításával. Arra gondoltam, hogy nem-e tudnál segíteni nekünk a cukorkák megkeresésével is kérlek?",
				[4] = "Wooooooooow!\n\nKöszi a segítséget!\n\nMost már mindenki nyugodt és partizhatunk egy jót a halottakkal!",
				[5] = "Se-Sesame mancsát leszakították... :(\n\nKérlek se-segíts neki :(",
				[6] = "Ooooh... Tisztelt városlakó...\nAnnyira aggódtam Sesame mancsa miatt, hogy teljesen megfeledkeztem a gitáromról... Nem tudom, hova raktam!\n\nAnélkül nem lesz zene, és tönkremegy a fesztivál!\nSegítenél megkeresni? Ha igen, hadd mutassam meg a dalszöveget is!",
				[7] = "Yuuupiiiii! Megtaláltad! Oh édes halott egér, nagyon köszönöm!!!!\n\nMost mindenki meghallgathatja a haláli jó zenéimet!"
			},
			close_dialog = "Nyomd meg a szóközt a párbeszédablak bezárásához.",
			welcome = "Itt van a <B>Halottak Napja</B>, ahol minden élő és halott együtt partizhat!\n\n~ ~ ~ Készítsétek a csontjaitokat! ~ ~ ~",
			conclusion = "Wooow! Milyen kis édes cukorfalat egér vagy te! Most a szellemek végre bulizhatnak és cukrot ehetnek!!!\n\nKöszönet <B>Mirabellá</B>nak, hogy a halottakat meghívta a mi világunkba, <B>%s</B>nak a kódért és <B>%s</B> egérnek a sok-sok csodás rajzért az eventhez!",
			failed_colors = "Ne ijedj meg a szellemektől, figyelj jobban a színekre és legközelebb sikerülni fog!",
		},
		lv = {
			dialogs = {
				[1] = "Sveiks šeit!\nMans vārds ir Mirabella, Helovīnu spoks!\n\nMirušo diena nāk drīzumā un ballīte sāksies, bet kamēr dekorējot pilsētu, Piñata, Sezame, saplēsa tās kāju. Es to nomainīju, bet visi konfeti tikai izkaisīti pa visu pilsētu. Pilsoņi to meklē, vai tu varētu palīdzēt mums?",
				[2] = "Urrāāāāāāāā!!!!\nLiels paldies tev! Sezame tagad jūtas daudz labāk!\n\nTagad ir laiks sagatavot saldumus un mūziku!",
				[3] = "Ak... Hei! Tas atkal esmu es...\n\nEs pārbaudīju vai viss ir kārtībā, lai sāktu ballīti, līdz kamēr es ievēroju kaut ko briesmīgu... SEZAME IR TUKŠS! SALDUMI IR PAZUDUŠI!\n\nTava palīdzība, lai salabotu tās kāju bija lieliska. Es domāju, vai tu varētu palīdzēt mums atrast saldumus, kas izkrita no kājas spraugas!?",
				[4] = "Vaaaaaaaaaaaaau!\n\nLiels paldies par tavu palīdzību!\n\nTagad mēs visi varam relaksēties un ballēties ar mirušajiem!",
				[5] = "Se-Sezames kāja ti-tika saplēsta... :(\n\nLūdzu pa-palīdzi viņam :(",
				[6] = "Aaaaak... Mīļais pilsoni...\nEs biju uztrauksies par Sezames ķepu ka es pavisam aizmirsu par savu ģitāru... Man šķiet es to pazaudēju!\n\nBez tās šeit nebūs mūzika un festivāls būs izbojāts!\nVai tu vari palīdzēt man to atrast? Ja vari, tad atļauj man tev parādīt lirikas!",
				[7] = "Juuupīīīī! Tu atradi to! Ak manam mirušās peles, liels tev paldies!!!!\n\nTagad visi klausīsies manu satriecošo mūziku!"
			},
			close_dialog = "Nospiediet atstarpi lai aizvērtu dialogu.",
			welcome = "Šī ir <B>Mirušo diena/B>, kur mirušie un dzīvie var ballēties kopā!\n\n~ ~ ~ Kratīsim savus kaulus! ~ ~ ~",
			conclusion = "Vaaau! Kas par mīļu ķirbju saldumu tu esi! Tagad spoki var beidzot ballēties un ēst cukuru!!!\n\nPaldies <B>Mirabellai</B> par mirušo atgriešanu mūsu pasaulē, <B>%s</B> par visas maģijas izkodēšanu <B>%s</B> lai uzzīmētu šo visu priekš šī satriecošā notikuma!",
			failed_colors = "Neļaujiet spokiem jūs nobiedēt, pievērsiet uzmanību krāsām un izdariet to pareizi nākošreiz!",
		},
		pl = {
			dialogs = {
				[1] = "Witaj!\nNazywam się Mirabella, duch Halloween!\n\nŚwięto Zmarłych nadchodzi, a wraz z nim wielkie przyjęcie, lecz podczas dekorowania miasta, Piñacie, Sezamkowi, rozerwała się łapka. Udało mi się ją zaszyć, ale bibuła jest porozrzucana po całym mieście. Nasi mieszkańcy próbują wszystko zebrać, ale potrzebujemy pomocy!",
				[2] = "Jeeeeeeeeej!!!!\nBardzo tobie dziękuję! Sazamek czuje się znacznie lepiej!\n\nTo jest czas, aby przygotować słodkości i muzykę!",
				[3] = "Oh... Cześć! To znowu ja...\n\nSprawdzałam czy wszystko jest gotowe, aby móc rozpocząć przyjęcie, kiedy zauważyłam coś bardzo strasznego... SEZAMEK JEST PUSTY! WSZYSTKIE CUKIERKI ZNIKNĘŁY!\n\nTwoja pomoc z naprawieniem łapki była bardzo potrzebna. Zastanawiam się czy mógłbyś pomóc nam odnaleźć wszystkie cukierki, które wypadły z dziury w nodze!?",
				[4] = "Wooooooooow!\n\nBardzo tobie dziękuję za pomoc!\n\nTeraz wszyscy możemy się wyluzować i bawić się z umarłymi!",
				[5] = "Łapka Se-Sezamka rozerwała się... :(\n\nProszę pomóż mu :(",
				[6] = "Ooooh... Drogi mieszkańcu...\nTak bardzo byłem zmartwiony o nóżkę Sezamka, że zapomniałem o mojej gitarze... Wydaje mi się, że ją zgubiłem!\n\nBez niej zabraknie muzyki, i całe przyjęcie pójdzie na marne!\nCzy możesz mi pomóc ją odnaleźć? Jeżeli tak, to pokażę tobie tekst piosenki!",
				[7] = "Juuupiiiii! Odnalazłeś ją! Oh moja nieżyjąca myszko, bardzo tobie dziękuję!!!!\n\nW końcu teraz wszyscy usłyszą moją wspaniałą muzykę!"
			},
			close_dialog = "Kliknij spację, aby przewinąć dialog.",
			welcome = "Dzisiaj jest <B>Święto Zmarłych</B>, podczas którego wszyscy zmarli oraz żyjący mogą razem się bawić!\n\n~ ~ ~ Czas rozruszać kości! ~ ~ ~",
			conclusion = "Wooow! Ale ty jesteś kochaną myszką! W końcu duchy i żyjący mogą razem się bawić i zjeść słodkości!!!\n\nPodziękowania dla <B>Mirabelli</B> za przywrócenie zmarłych do naszego świata, <B>%s</B> za zaprogramowanie całej magii i <B>%s</B> za namalowanie wszystkiego na tym wspaniałym evencie!",
			failed_colors = "Nie pozwól, aby duchy przestraszyły się ciebie, zwróć uwagę na kolory i postępuj rozważniej następnym razem!",
		},
		ro = {
			dialogs = {
				[1] = "Bună!\nNumele meu este Mirabella, fantoma Halloweenului!\n\nZiua Morților sosește și petrecerea va începe, doar că în timp ce decoram orașul, Piñata noastră, Sesame, și-a rupt piciorul . I l-am reparat, doar că toate confettile s-au împrăștiat peste tot în oraș. Locuitorii le caută și ei, ne puteți ajuta și voi??",
				[2] = "Yaaaaaaaay!!!!\nMulțumesc foarte mult! Sesame se simte mult mai bine acum!\n\nEste timpul să pregătim dulciurile și muzica!",
				[3] = "Oh... Hey! Tot eu sunt...\n\nVerificam dacă totul este în regulă înainte de a începe petrecerea și am descoperit ceva teribil... SESAME E GOL! BOMBOANELE AU DISPĂRUT!\n\nAjutorul oferit pentru repararea piciorului lui a fost grozav. Mă întrebam dacă ne-ați putea ajuta să găsim dulciurile care au căzut în timp ce lăbuța lui era ruptă!?",
				[4] = "Wooooooooow!\n\nMulțumesc foarte mult pentru ajutorul oferit!\n\nAcum ne putem relaxa cu toții și să petrecem alături de cei morți!",
				[5] = "Piciorul lui Se-Sesame a fo-fost rupt... :(\n\nTe rog a-ajută-l :(",
				[6] = "Ooooh... Dragi cetățeni...\nAm fost atât de îngrijorată în legătură cu lăbuța lui Sesame încât am uitat cu totul de chitara mea... Cred că am pierdut-o!\n\nFără ea, nu vom avea muzică iar festivalul va fi complet ruinat!\nMă puteți ajuta s-o găsesc? Dacă da, lăsați-mă să vă cânt versurile după!",
				[7] = "Yuuupiiiii! Ați găsit-o! Oh șoriceii mei morți, vă mulțumesc foarte mult!!!!\n\nAcum toată lumea îmi va putea asculta cântecul!"
			},
			close_dialog = "Apasă Space pentru a închide dialogul.",
			welcome = "Astăzi este <B>Ziua Morților</B>, ziua în care morții și cei vii pot petrece împreună!\n\n~ ~ ~ Să ne scuturăm oasele! ~ ~ ~",
			conclusion = "Wooow! Ce dovlecel drăguț ești! În sfârșit toate fantomele pot petrece și mânca dulciuri!!!\n\nMulțumim <B>Mirabellei</B> pentru că a adus morții în lumea noastră, <B>%s</B> pentru codarea acestei magii și <B>%s</B> pentru toate desenele din acest eveniment minunat!",
			failed_colors = "Nu lăsa fantomele să te sperie, acordă atenție culorilor și alege-o pe cea corectă data viitoare!",
		},
		tr = {
			dialogs = {
				[1] = "Selam!\nBenim adım Mirabella, Cadılar Bayramının hayaletiyim!!\n\nÖlülerin günü yaklaşıyor ve parti başlayacak, ama şehri süslerken, the Piñata, Sesame, bacağı sökülmüş. Onu tamir ettim, ama konfetinin tamamı bütün şehre dağıldı. Vatandaşlar onu arıyor, bize yardım eder misin?",
				[2] = "Yeeeeeeyyy!!!!\nÇok teşekkür ederim! Sesame şimdi kendisini daha iyi hissediyor!\n\nTatlıları ve müziği hazırlamanın vakti geldi!",
				[3] = "Oh... Hey! Yine ben...\n\nKorkunç bir şey fark ettiğimde partiye başlamak için her şeyin yolunda olup olmadığını kontrol ediyordum... SESAME BOŞ! ŞEKERLER KAYBOLMUŞ!\n\nYour help fixing its leg was great Bacağı tamir etmek için yardımın harikaydı. Bacak boşluğundan düşen şekerleri bulmamıza yardım edebilir misin?!?",
				[4] = "Voooooooov!\n\nYardımın için çok teşekkür ederim!\n\nŞimdi hepimiz şimdi rahatlayabilir ve ölülerle parti yapabiliriz!",
				[5] = "Se-Sesame'nin bacağı ko-koptu... :(\n\nLütfen ona ya-yardım et :(",
				[6] = "Ooooh... Değerli vatandaş...\nSesame'nin piyonunu gitarımdan tamamen unuttuğum için çok endişelendim... Sanırım onu kaybettim!\n\nOnsuz müzik olmayacak ve festival mahvolacak!\nOnu bulmama yardım eder misin? Öyleyse, şarkı sözlerini göstereyim!",
				[7] = "Oollleeeyyyy! Onu buldun! Ah benim ölü farelerim, çok teşekkürler!!!!\n\nŞimdi herkes benim harika müziğimi dinleyecek!"
			},
			close_dialog = "Diyaloğu kapatmak için boşluk tuşuna basın.",
			welcome = "Bu <B>Ölülerin günü</B>, ölülerin ve yaşayanların birlikte parti yapabilecekleri bir gün!\n\n~ ~ ~ Hadi kemikleri sallayalım! ~ ~ ~",
			conclusion = "Vaayy! Ne kadarda sevimli bir tatlısın sen! Şimdi hayaletler sonunda parti yapıp ve şeker yiyebilirler!!!\n\nÖlüleri dünyaya getirdiğin için teşekkürler <B>Mirabella</B> , <B>%s</B> bütün büyüyü kodlamak ve <B>%s</B> bu harika etkinlikte her şeyi çizmek için!",
			failed_colors = "Hayaletlerin seni korkutmalarına izin verme, renklere dikkat edin ve bir daha ki sefere düzeltin!",
		}
	}
	local translation = translations[tfm.get.room.community] or translations.en

	--[[ Data ]]--
	-- Laagaadoo's DataHandler
	local DataHandler = {}
	DataHandler.VERSION = '1.4'
	DataHandler.__index = DataHandler
	function DataHandler.new(moduleID, skeleton, otherOptions)
		local self = setmetatable({}, DataHandler)

		assert(moduleID, 'Invalid module ID (nil)')
		assert(moduleID ~= '', 'Invalid module ID (empty text)')
		assert(skeleton, 'Invalid skeleton (nil)')

		for k, v in next, skeleton do
			v.type = v.type or type(v.default)
		end

		self.players = {}
		self.moduleID = moduleID
		self.moduleSkeleton = skeleton
		self.moduleIndexes = {}
		self.otherOptions = otherOptions
		self.otherData = {}
		self.originalStuff = {}

		for k,v in pairs(skeleton) do
			self.moduleIndexes[v.index] = k
		end

		if self.otherOptions then
			self.otherModuleIndexes = {}
			for k,v in pairs(self.otherOptions) do
				self.otherModuleIndexes[k] = {}
				for k2,v2 in pairs(v) do
					v2.type = v2.type or type(v2.default)
					self.otherModuleIndexes[k][v2.index] = k2
				end
			end
		end

		return self
	end
	function DataHandler.newPlayer(self, name, dataString)
		assert(name, 'Invalid player name (nil)')
		assert(name ~= '', 'Invalid player name (empty text)')

		self.players[name] = {}
		self.otherData[name] = {}

		dataString = dataString or ''

		local function turnStringToTable(str)
			local output = {}
			for data in string.gsub(str, '%b{}', function(b) return b:gsub(',', '\0') end):gmatch('[^,]+') do
				data = data:gsub('%z', ',')

				if string.match(data, '^{.-}$') then
					table.insert(output, turnStringToTable(string.match(data, '^{(.-)}$')))
				else
					table.insert(output, tonumber(data) or data)
				end
			end
			return output
		end

		local function getDataIndexName(skeleton, index)
			for k,v in pairs(skeleton) do
				if v.index == index then
					return k
				end
			end
			return 0
		end

		local function getHigherIndex(skeleton)
			local higher = 0
			for k,v in pairs(skeleton) do
				if v.index > higher then
					higher = v.index
				end
			end
			return higher
		end

		local function handleModuleData(moduleID, skeleton, moduleData, makeTable)
			local dataIndex = 1
			local higherIndex = getHigherIndex(skeleton)

			moduleID = "__" .. moduleID
			if makeTable then
				self.players[name][moduleID] = {}
			end

			local setPlayerData = function(data, dataType, dataName, dataDefault)
				local value
				if dataType == "number" then
					value = tonumber(data) or dataDefault
				elseif dataType == "string" then
					value = string.match(data or '', "^\"(.-)\"$") or dataDefault
				elseif dataType == "table" then
					value = string.match(data or '', "^{(.-)}$")
					value = value and turnStringToTable(value) or dataDefault
				elseif dataType == "boolean" then
					if data then
						value = data == '1'
					else
						value = dataDefault
					end
				end

				if makeTable then
					self.players[name][moduleID][dataName] = value
				else
					self.players[name][dataName] = value
				end
			end

			if #moduleData > 0 then
				for data in string.gsub(moduleData, '%b{}', function(b) return b:gsub(',', '\0') end):gmatch('[^,]+') do
					data = data:gsub('%z', ',')

					local dataName = getDataIndexName(skeleton, dataIndex)
					local dataType = skeleton[dataName].type
					local dataDefault = skeleton[dataName].default

					setPlayerData(data, dataType, dataName, dataDefault)

					dataIndex = dataIndex + 1
				end
			end

			if dataIndex <= higherIndex then
				for i = dataIndex, higherIndex do
					local dataName = getDataIndexName(skeleton, i)
					local dataType = skeleton[dataName].type
					local dataDefault = skeleton[dataName].default

					setPlayerData(nil, dataType, dataName, dataDefault)
				end
			end
		end

		local modules, originalStuff = self:getModuleData(dataString)

		self.originalStuff[name] = originalStuff

		if not modules[self.moduleID] then
			modules[self.moduleID] = '{}'
		end

		handleModuleData(self.moduleID, self.moduleSkeleton, modules[self.moduleID]:sub(2,-2), false)

		if self.otherOptions then
			for moduleID, skeleton in pairs(self.otherOptions) do
				if not modules[moduleID] then
					local strBuilder = {}
					for k,v in pairs(skeleton) do
						local dataType = v.type or type(v.default)
						if dataType == 'string' then
							strBuilder[v.index] = '"'..tostring(v.default)..'"'
						elseif dataType == 'table' then
							strBuilder[v.index] = '{}'
						elseif dataType == 'number' then
							strBuilder[v.index] = v.default
						elseif dataType == 'boolean' then
							strBuilder[v.index] = v.default and '1' or '0'
						end
					end
					modules[moduleID] = '{'..table.concat(strBuilder, ',')..'}'
				end
			end
		end

		for moduleID, moduleData in pairs(modules) do
			if moduleID ~= self.moduleID then
				if self.otherOptions and self.otherOptions[moduleID] then
					handleModuleData(moduleID, self.otherOptions[moduleID], moduleData:sub(2,-2), true)
				else
					self.otherData[name][moduleID] = moduleData
				end
			end
		end
	end
	function DataHandler.dumpPlayer(self, name)
		local output = {}

		local function turnTableToString(tbl)
			local output = {}
			for k,v in pairs(tbl) do
				local valueType = type(v)
				if valueType == 'table' then
					output[#output+1] = '{'
					output[#output+1] = turnTableToString(v)

					if output[#output]:sub(-1) == ',' then
						output[#output] = output[#output]:sub(1, -2)
					end
					output[#output+1] = '}'
					output[#output+1] = ','
				else
					if valueType == 'string' then
						output[#output+1] = '"'
						output[#output+1] = v
						output[#output+1] = '"'
					elseif valueType == 'boolean' then
						output[#output+1] = v and '1' or '0'
					else
						output[#output+1] = v
					end
					output[#output+1] = ','
				end
			end
			if output[#output] == ',' then
				output[#output] = ''
			end
			return table.concat(output)
		end

		local function getPlayerDataFrom(name, moduleID)
			local output = {moduleID, '=', '{'}
			local player = self.players[name]
			local moduleIndexes = self.moduleIndexes
			local moduleSkeleton = self.moduleSkeleton

			if self.moduleID ~= moduleID then
				moduleIndexes = self.otherModuleIndexes[moduleID]
				moduleSkeleton = self.otherOptions[moduleID]
				moduleID = '__'..moduleID
				player = self.players[name][moduleID]
			end

			if not player then
				return ''
			end

			for i = 1, #moduleIndexes do
				local dataName = moduleIndexes[i]
				local dataType = moduleSkeleton[dataName].type
				if dataType == 'string' then
					output[#output+1] = '"'
					output[#output+1] = player[dataName]
					output[#output+1] = '"'

				elseif dataType == 'number' then
					output[#output+1] = player[dataName]

				elseif dataType == 'boolean' then
					output[#output+1] = player[dataName] and '1' or '0'

				elseif dataType == 'table' then
					output[#output+1] = '{'
					output[#output+1] = turnTableToString(player[dataName])
					output[#output+1] = '}'
				end

				output[#output+1] = ','
			end

			if output[#output] == ',' then
				output[#output] = '}'
			else
				output[#output+1] = '}'
			end

			return table.concat(output)
		end

		output[#output+1] = getPlayerDataFrom(name, self.moduleID)

		if self.otherOptions then
			for k,v in pairs(self.otherOptions) do
				local moduleData = getPlayerDataFrom(name, k)
				if moduleData ~= '' then
					output[#output+1] = ','
					output[#output+1] = moduleData
				end
			end
		end

		for k,v in pairs(self.otherData[name]) do
			output[#output+1] = ','
			output[#output+1] = k
			output[#output+1] = '='
			output[#output+1] = v
		end

		return table.concat(output)..self.originalStuff[name]
	end
	function DataHandler.get(self, name, dataName, moduleName)
		if not moduleName then
			return self.players[name][dataName]
		else
			assert(self.players[name]['__'..moduleName], 'Module data not available ('..moduleName..')')
			return self.players[name]['__'..moduleName][dataName]
		end
	end
	function DataHandler.set(self, name, dataName, value, moduleName)
		if moduleName then
			self.players[name]['__'..moduleName][dataName] = value
		else
			self.players[name][dataName] = value
		end

		return self
	end
	function DataHandler.save(self, name)
		system.savePlayerData(name, self:dumpPlayer(name))
	end
	function DataHandler.removeModuleData(self, name, moduleName)
		assert(moduleName, "Invalid module name (nil)")
		assert(moduleName ~= '', "Invalid module name (empty text)")
		assert(moduleName ~= self.moduleID, "Invalid module name (current module data structure)")

		if self.otherData[name][moduleName] then
			self.otherData[name][moduleName] = nil
			return true
		else
			if self.otherOptions and self.otherOptions[moduleName] then
				self.players[name]['__'..moduleName] = nil
				return true
			end
		end

		return false
	end
	function DataHandler.getModuleData(self, str)
		local output = {}

		for moduleID, moduleData in string.gmatch(str, '([0-9A-Za-z_]+)=(%b{})') do
			output[moduleID] = moduleData
		end

		for k,v in pairs(output) do
			str = str:gsub(k..'='..v:gsub("[%(%)%.%%%+%-%*%?%[%]%^%$]", "%%%0")..',?', '')
		end
		return output, str
	end

	local playerData = DataHandler.new(module.name, {
		fixingPinata = {
			index = 1,
			default = true
		},
		collected = {
			index = 2,
			default = 0
		},
		conclusion = {
			index = 3,
			default = 0
		},
	})
	local playerCache = { }

	local enums = {
		bot = 0,
		bar = 50,
		dialog = 100,
		callback = 150,
		hud = 200,
		guitar = 300
	}
	local keys = {
		spacebar = 32,
		down = 3,
		right = 2,
		left = 0
	}

	local map = {
		background = "166abd24fee.jpg",
		foreground = "166abd772a7.png"
	}
	local objects = {
		pinata = {
			"165ed178772.png",
			"165ed17a007.png",
			"165ed17fcc0.png",
			"165ed181b41.png",
			"165ed1cd5dd.png" -- Final
		},
		candy = {
			[-6] = "166276f2e28.png", -- Transparent
			[-5] = "166276f16b7.png", -- Transparent
			[-4] = "166276eff46.png", -- Transparent
			[-3] = "166276ee7d3.png", -- Transparent
			[-2] = "166276ed061.png", -- Transparent
			[-1] = "166276eb8f0.png", -- Transparent
			[0] = "166272349e4.png", -- Thumb
			[1] = "165ed1f5401.png", -- Solid
			[2] = "165ed1f6b72.png", -- Solid
			[3] = "165ed1f82e3.png", -- Solid
			[4] = "165ed1fb18f.png", -- Solid
			[5] = "165ed1fcb50.png", -- Solid
			[6] = "165ed1fe653.png" -- Solid
		},
		confetti = {
			[-1] = "166227f674d.png", -- Transparent
			[0] = "166228b6669.png", -- Thumb
			[1] = "1660d06d0e6.png" -- Solid
		},
		npc = {
			mirabella = "165ee2b86fb.png",
			miguel = {
				crying = "1662b2f21ea.png",
				normal = "1662b2f21ea.png"
			}
		},
		npcDialog = {
			background = "1666b88049d.png",
			-- Mirabella
			"165ed3253ed.png", -- Asking for help (Confetti)
			"165ed328799.png", -- Finished confetti
			"165ed3253ed.png", -- Asking for help (Candies)
			"165ed326c11.png", -- Finished candies
			-- Miguel
			"1664060e191.png", -- Can't start simon says (Crying)
			"1664060e191.png", -- Start simon says (Show guitars)
			"16640606b25.png", -- Give guitar back
		},
		guitar = {
			hidden = "1662be904f3.png",
			[-4] = "1662c48fd3a.png", -- Blue Transparent
			[-3] = "1662c48b6e5.png", -- Purple Transparent
			[-2] = "1662c48ce56.png", -- Green Transparent
			[-1] = "1662c48e5c7.png", -- Red Transparent
			[0] = { "16631bc6b1e.png", "16631b89ba1.png" }, -- Normal (Left, Right)
			[1] = "16621c30cc7.png", -- Red
			[2] = "16621c2f554.png", -- Green
			[3] = "16621c2dde2.png", -- Purple
			[4] = "16621c2c670.png" -- Blue
		},
		pet = { "166409c6a30.png", "16640a0324a.png" } -- Left, Right
	}

	local locations = {
		{ 125, 440 },
		{ 725, 430 },
		{ 1080, 70 },
		{ 1080, 540 },
		{ 1305, 475 },
		{ 1380, 260 },
		{ 1450, 600 },
		{ 1660, 455 },
		{ 1950, 135 },
		{ 2280, 150 },
		{ 2330, 570 },
		{ 2410, 765 }
	}
	local colorScheme = {
		[1] = { 0x328F8F, 0x05DCFF }, -- Confetti
		[2] = { 0x8F3246, 0xF73838 } -- Candy
	}

	local axis = {
		guitar = {
			[1] = { -25, -10 },
			[2] = { 0, -10 }
		},
		pet = {
			[1] = { 25, -25 },
			[2] = { -55, -25 }
		}
	}
	local particles = {
		tree = { 21, 24, 21, 24, 2, 24, 11, 11, 24, 24 },
		walking = { 24, 24, 24, 24, 24, 21, 24, 24, 24 }
	}
	local consumables = {
		pumpkin = 8,
		paper = 26,
		postcard = 29,
		microphone = 2234
	}
	local emotes = {
		sing = 20,
		dance = 24
	}

	local consumableLocations = {
		-- x1, x2, y1, y2, id
		[1] = { 80, 505, 350, 575, "postcard" }, -- Tree
		[2] = { 1365, 430, 1560, 540, "paper" }, -- Birb
		[3] = { 1755, 130, 2190, 290, "microphone" }, -- Bridge
		[4] = { 1890, 220, 2015, 280, "pumpkin" }, -- Unreachable Platform
	}

	--[[ Functions & Classes ]]--
	local callback
	do
		local callback_id = enums.callback
		callback = setmetatable({ }, {
			__call = function(this, eventName, x, y, w, h, hidden)
				local id = callback_id
				local action
				local hasClickableRange, range = false, 0

				local fixed = false
				local textarea = function(playerName)
					ui.addTextArea(id, "<textformat leftmargin='1' rightmargin='1'><a href='event:callback." .. eventName .. "'>" .. string.rep('\n', h / 10), playerName, x - 5, y - 5, w + 5, h + 5, 1, 1, 0, fixed)
				end

				local self = {
					callback = eventName,
					x = x,
					y = y,
					w = w,
					h = h
				}

				self.displayCallback = function(self, f)
					if type(f) == "string" then
						return textarea(f)
					end

					for k, v in next, tfm.get.room.playerList do
						if not f or f(k, v) then
							textarea(k)
						end
					end
				end

				self.fixed = function(self)
					fixed = true
					return self
				end

				self.inClickableRange = function(self, playerName)
					if hasClickableRange then
						local x, y = tfm.get.room.playerList[playerName].x, tfm.get.room.playerList[playerName].y
						return ((x >= (self.x - range) and x <= (self.x + self.w + range)) and (y >= (self.y - range) and y <= (self.y + self.h + range)))
					end
					return true
				end

				self.performAction = function(self, ...)
					if action then
						action(self, ...)
						return self, true
					end
					return self, false
				end

				self.remove = function(self, playerName)
					ui.removeTextArea(id, playerName)

					if not playerName then
						this[eventName] = nil
					end
					return true
				end

				self.setAction = function(self, f)
					if type(f) == "function" then
						action = f
						this[eventName] = self
						return true
					end
					return false
				end

				self.setClickableRange = function(self, complement)
					hasClickableRange, range = true, (complement or 0)
				end

				if not hidden then
					self.displayCallback(self)
				end

				callback_id = callback_id + 1
				return self
			end
		})
	end

	table.getRandomIndexes = function(list, n, avoidRepeated)
		local indexes, out = { }, { }

		for i = 1, n do
			if avoidRepeated then
				local rand
				repeat
					rand = math.random(#list)
				until not indexes[rand]
				indexes[rand] = true
			else
				out[i] = math.random(#list)
			end
		end

		if avoidRepeated then
			for i in next, indexes do
				out[n] = i
				n = n - 1
			end
		end

		return out
	end
	table.createArray = function(n, bool)
		local out = { }
		for i = 1, n do
			out[i] = bool
		end
		return out
	end

	--[[ Variables ]]--
	local time = -1
	local pinataBody, pinataPaw
	local selectableGuitars, guitar = { }
	local timersWorking = 0
	local canStart = false

	--[[ System ]]--
	ui.bar = function(playerName, w)
		local id = (playerData:get(playerName, "fixingPinata") and 1 or 2)
		local color = colorScheme[id]
		w = math.clamp(w * (160 / module[(id == 1 and "confetti_to_fix_pinata" or "candies_to_finish_event")]), 1, 160)
		ui.addTextArea(enums.bar, '', playerName, 1870, 730, 164, 8, color[1], 0x57401B, 1, false)
		ui.addTextArea(enums.bar + 1, '', playerName, 1872, 732, w, 4, color[2], color[2], 1, false)
	end
	ui.removebar = function(playerName)
		for id = enums.bar, enums.bar + 1 do
			ui.removeTextArea(id, playerName)
		end
	end

	ui.dialog = function(playerName)
		playerCache[playerName].remImg.dialog[1] = tfm.exec.addImage(objects.npcDialog[playerCache[playerName].dialog[1]], "&0", 510, 66, playerName)
		playerCache[playerName].remImg.dialog[2] = tfm.exec.addImage(objects.npcDialog.background, ":0", 100, 235, playerName)
		ui.addTextArea(enums.dialog, "", playerName, -1500, -1500, 3000, 3000, 1, 1, .15, true)
		ui.addTextArea(enums.dialog + 1, "<font size='15' color='#F0F0E0' face='Courier New'><textformat leftmargin='10' rightmargin='5'>", playerName, 100, 240, 600, 160, 1, 1, 0, true)
	end
	ui.removeDialog = function(playerName)
		for i = 1, #playerCache[playerName].remImg.dialog do
			tfm.exec.removeImage(playerCache[playerName].remImg.dialog[i])
		end
		playerCache[playerName].dialog = { 0, 0 }
		for id = enums.dialog, enums.dialog + 1 do
			ui.removeTextArea(id, playerName)
		end
	end

	ui.displayCollectedItems = function(playerName)
		ui.addTextArea(enums.hud, "<p align='right'><font color='#FFFFFF' size='20'><B>" .. string.format("%03d", playerCache[playerName].collected), playerName, 715, 370, 80, nil, 1, 1, 0, true)
	end

	local hasConcluded = function(playerName, prize)
		return bit32.band(module.prize[prize][2], playerData:get(playerName, "conclusion")) > 0
	end

	local getPinataStage = function(score)
		local len = #objects.pinata + 1
		local stage = math.ceil((module.confetti_to_fix_pinata - score) / (score / (len - 2))) + 1
		return len - math.clamp(stage, 1, (len - 1))
	end

	local checkForBadge = function(playerName)
		if not hasConcluded(playerName, "badge") and playerData:get(playerName, "collected") >= module.candies_to_finish_event then
			system.giveEventGift(playerName, module.prize.badge[1])
			playerData:set(playerName, "conclusion", playerData:get(playerName, "conclusion") + module.prize.badge[2])
			tfm.exec.chatMessage("<CE><p align='center'>" .. string.format(translation.conclusion, module.team[1], module.team[2]) .. "</p>", playerName)

			playerCache[playerName].dialog[1] = 4
			ui.dialog(playerName)

			tfm.exec.giveConsumables(playerName, consumables.pumpkin, 2)
		end
	end

	local pinata = function(self, playerName)
		if playerCache[playerName].dataLoaded then
			if playerCache[playerName].collected > 0 then
				playerData:set(playerName, "collected", playerData:get(playerName, "collected") + playerCache[playerName].collected)
				playerCache[playerName].collected = 0

				ui.displayCollectedItems(playerName)
				ui.bar(playerName, playerData:get(playerName, "collected"))

				if playerData:get(playerName, "fixingPinata") then
					tfm.exec.removeImage(playerCache[playerName].remImg.pinata)
					playerCache[playerName].remImg.pinata = tfm.exec.addImage(objects.pinata[getPinataStage(playerData:get(playerName, "collected"))], "?2", 1820, 390, playerName)

					if playerData:get(playerName, "collected") >= module.confetti_to_fix_pinata then
						playerData:set(playerName, "fixingPinata", false)
						playerData:set(playerName, "collected", 0)

						playerCache[playerName].wasFixingPinata = true
						playerCache[playerName].dialog[1] = 2
						ui.dialog(playerName)

						for k, v in next, playerCache[playerName].remImg.collectables do
							tfm.exec.removeImage(v)
						end
						playerCache[playerName].remImg.collectables = { }

						tfm.exec.giveConsumables(playerName, consumables.pumpkin, 2)
					end
				else
					checkForBadge(playerName)
				end
				playerData:save(playerName)
			end
		end
	end

	local removeGuitars = function(playerName)
		playerCache[playerName].displaySimonSays = 0

		ui.removeTextArea(enums.guitar, playerName)

		for i = 1, #objects.guitar do
			tfm.exec.removeImage(playerCache[playerName].remImg.guitars[i])
			selectableGuitars[i]:remove(playerName)
		end
		playerCache[playerName].remImg.guitars = { }
	end

	local bindArrows = function(playerName, enabled)
		system.bindKeyboard(playerName, keys.left, true, enabled)
		system.bindKeyboard(playerName, keys.right, true, enabled)
	end

	local update = function(add)
		add = add or 1

		local currentTime, str = os.time()
		for playerName, data in next, playerCache do
			if data.guitarTimer > 0 and currentTime > data.guitarTimer then
				data.guitarTimer = 0
				tfm.exec.removeImage(data.remImg.highlightGuitar)
				if data.simonSaysSequence.id > #data.simonSaysSequence.seq then
					removeGuitars(playerName)

					guitar:remove(playerName)
					for note = 1, #data.simonSaysSequence.seq do
						if not data.simonSaysSequence.seq[note] then
							data.remImg.guitar = -1
							tfm.exec.chatMessage("<V>[•] <BL>" .. translation.failed_colors, playerName)
							return
						end
					end
					tfm.exec.removeImage(data.remImg.hguitar)

					data.hasGuitar = true
					bindArrows(playerName, true)
					eventKeyboard(playerName, keys[(tfm.get.room.playerList[playerName].isFacingRight and "right" or "left")])
				end
			elseif data.dataLoaded and data.dialog[1] > 0 then
				str = translation.dialogs[data.dialog[1]]

				data.dialog[2] = data.dialog[2] + add
				local final = data.dialog[2] >= #str

				ui.updateTextArea(enums.dialog + 1, string.sub(translation.dialogs[data.dialog[1]], 1, data.dialog[2]) .. (final and ("\n<ROSE>" .. translation.close_dialog) or "|"), playerName)

				if final then
					data.dialog[1] = -1
				end
			end
		end
	end

	local teleportCandyParticle = function(playerName, x, y, back)
		local cos, sin
		for i = 1, 12 do
			cos = math.cos(i)
			sin = math.sin(i)
			if back then
				tfm.exec.displayParticle(math.random(39, 40), x + cos * 30, y + sin * 30, cos * 4, sin * 4, -cos * .5, -sin * .5, playerName)
			else
				tfm.exec.displayParticle(math.random(39, 40), x + cos * 30, y + sin * 30, -cos * 5, -sin * 5, cos * .5, sin * .5, playerName)
			end
		end
	end

	local teleportCandies = function(playerName)
		if #playerCache[playerName].candies == 0 then return end

		for i = 1, #playerCache[playerName].candies do
			local v = playerCache[playerName].candies[i]
			if v.time >= 0 and os.time() > v.time then
				if not playerCache[playerName].collectedItems[i] then
					teleportCandyParticle(playerName, locations[v.pos][1], locations[v.pos][2])
				end
				tfm.exec.removeImage(playerCache[playerName].remImg.collectables[i])

				if not playerCache[playerName].collectedItems[i] then
					local newPos
					repeat
						newPos = math.random(#locations)
					until playerCache[playerName].candyFreeSpot[newPos]

					playerCache[playerName].candyFreeSpot[newPos] = false
					playerCache[playerName].candyFreeSpot[v.pos] = true

					v.pos = newPos
					v.time = os.time() + math.random(module.time_to_teleport_candy - 3000, module.time_to_teleport_candy + 3000)

					teleportCandyParticle(playerName, locations[v.pos][1], locations[v.pos][2], true)
					playerCache[playerName].remImg.collectables[i] = tfm.exec.addImage(objects.candy[v.candy], "?100", locations[v.pos][1] - 20, locations[v.pos][2] - 20, playerName)
				else
					playerCache[playerName].candyFreeSpot[v.pos] = false
					v.time = -1
				end
			end
		end
	end

	local selectGuitar = function(self, playerName, id)
		if playerCache[playerName].remImg.guitar ~= 0 or playerCache[playerName].guitarTimer > 0 then return end

		id = id - 3

		playerCache[playerName].remImg.highlightGuitar = tfm.exec.addImage(objects.guitar[id], ":200", 160 + id * 85, 150, playerName)
		-- Reusing function update
		playerCache[playerName].guitarTimer = os.time() + 500

		playerCache[playerName].simonSaysSequence.seq[playerCache[playerName].simonSaysSequence.id] = playerCache[playerName].simonSaysSequence.seq[playerCache[playerName].simonSaysSequence.id] == id

		playerCache[playerName].simonSaysSequence.id = playerCache[playerName].simonSaysSequence.id + 1
		-- Consequences of id > #seq is in function update
	end

	local displayGuitars = function(self, playerName)
		if playerCache[playerName].remImg.guitar == -1 or playerCache[playerName].remImg.guitar ~= 0 then return end

		ui.addTextArea(enums.guitar, "", playerName, -1500, -1500, 3000, 3000, 1, 1, .15, true)

		for i = 1, #objects.guitar do
			playerCache[playerName].remImg.guitars[i] = tfm.exec.addImage(objects.guitar[-i], ":100", 160 + i * 85, 150, playerName)

			if playerCache[playerName].displaySimonSays == 0 then
				selectableGuitars[i]:displayCallback(playerName)
			end
		end

		if playerCache[playerName].displaySimonSays == 1 then
			playerCache[playerName].displaySimonSays = 666 -- Shows the sequence
		else
			playerCache[playerName].displaySimonSays = 2
		end
	end

	local isInsideRectangle = function(playerName, x, y, x2, y2)
		local player = type(playerName) == "table" and playerName or tfm.get.room.playerList[playerName]
		return player.x >= x and player.x <= x2 and player.y >= y and player.y <= y2
	end

	local linkObjectToPlayer = function(playerName, id, dir, imgSrc)
		if playerCache[playerName].remImg[id] > 0 then
			tfm.exec.removeImage(playerCache[playerName].remImg[id])
		end
		playerCache[playerName].remImg[id] = tfm.exec.addImage((imgSrc or objects[id])[dir], "$" .. playerName, axis[id][dir][1], axis[id][dir][2])
	end

	local loadAllImages
	loadAllImages = function(playerName, src)
		for k, v in next, (src or objects) do
			if type(v) == "table" then
				loadAllImages(playerName, v)
			else
				tfm.exec.removeImage(tfm.exec.addImage(v, "_0", -10000, -10000, playerName))
			end
		end
	end

	--[[ Events ]]--
	-- Relevant
	eventNewGame = function()
		if time >= 0 then
			--return system.exit()
		else
			time = 0
			tfm.exec.setGameTime(module.time)
		end

		-- Set players
		loadAllImages()
		for playerName, data in next, tfm.get.room.playerList do
			playerCache[playerName] = {
				dataLoaded = false,
				dialog = { 0, 0 }, -- id, str pos
				remImg = {
					dialog = { },
					collectables = { },
					pinata = 0,
					guitars = { },
					highlightGuitar = 0,
					guitar = 0,
					pet = 0,
					hguitar = 0
				},
				collected = 0,
				collectedItems = { },
				collectTimer = 0,
				candies = { },
				candyFreeSpot = table.createArray(#locations, true),
				startedSimonSays = false,
				displaySimonSays = 0,
				simonSaysSequence = { },
				guitarTimer = 0,
				consumableTimer = 0,
				hasGuitar = false,
				hasPet = false,
				lastPress = {
					guitar = -1,
					pet = -1
				},
				wasFixingPinata = false
			}

			tfm.exec.lowerSyncDelay(playerName)
		end

		-- Build background
		tfm.exec.addImage(map.background, "?0", 0, 0)
		tfm.exec.addImage(map.foreground, "!0", 0, 0)

		-- NPC
		-- Mirabella
		ui.addTextArea(enums.bot, "<p align='center'><font color='#FFFFFF' face='Verdana'><B>Mirabella", nil, 1810, 655, nil, nil, 1, 1, 0, false)
		tfm.exec.addImage(objects.npc.mirabella, "!1", 1820, 670)
		local foo = callback("guide", 1820, 670, 48, 46)
		foo:setAction(function(self, playerName)
			if playerCache[playerName].dataLoaded then
				if playerData:get(playerName, "fixingPinata") then
					playerCache[playerName].dialog[1] = 1
					ui.dialog(playerName)
				else
					if not hasConcluded(playerName, "badge") then
						-- Will be == 0 if the current map had fixingPinata as true
						playerCache[playerName].dialog[1] = #playerCache[playerName].candies == 0 and 2 or 3
					else
						playerCache[playerName].dialog[1] = 4
					end
					ui.dialog(playerName)
				end
			end
		end)
		foo:setClickableRange(80)
		-- Miguel
		ui.addTextArea(enums.bot + 1, "<p align='center'><font color='#FFFFFF' face='Verdana'><B>Miguel Rivera", nil, 2000, 110, nil, nil, 1, 1, 0, false)
		foo = callback("simonsays", 2025, 122, 39, 46)
		foo:setAction(function(self, playerName)
			if playerCache[playerName].dataLoaded then
				if playerData:get(playerName, "fixingPinata") then
					playerCache[playerName].dialog[1] = 5
					ui.dialog(playerName)
				else
					if hasConcluded(playerName, "orb") then
						return tfm.exec.playEmote(playerName, emotes.sing)
					end

					if not playerCache[playerName].wasFixingPinata then
						if not playerCache[playerName].startedSimonSays then
							playerCache[playerName].startedSimonSays = true

							guitar:displayCallback(playerName)
							playerCache[playerName].remImg.hguitar = tfm.exec.addImage(objects.guitar.hidden, "!100", 186, 527, playerName)

							playerCache[playerName].displaySimonSays = 1 -- Displayed once the dialog is closed
							playerCache[playerName].dialog[1] = 6
							ui.dialog(playerName)
						elseif playerCache[playerName].hasGuitar then
							playerCache[playerName].hasGuitar = false
							if not playerCache[playerName].hasPet then
								bindArrows(playerName, false)
							end

							tfm.exec.removeImage(playerCache[playerName].remImg.guitar)
							playerCache[playerName].remImg.guitar = 0

							playerCache[playerName].dialog[1] = 7
							ui.dialog(playerName)

							system.giveEventGift(playerName, module.prize.orb[1])
							playerData:set(playerName, "conclusion", playerData:get(playerName, "conclusion") + module.prize.orb[2]):save(playerName)

							tfm.exec.giveConsumables(playerName, consumables.pumpkin, 2)

							playerCache[playerName].hasPet = true
							bindArrows(playerName, true)
							eventKeyboard(playerName, keys[(tfm.get.room.playerList[playerName].isFacingRight and "right" or "left")])

							tfm.exec.displayParticle(37, tfm.get.room.playerList[playerName].x, tfm.get.room.playerList[playerName].y)
							tfm.exec.movePlayer(playerName, 1950, 268)
							tfm.exec.displayParticle(37, 1950, 268)
							tfm.exec.playEmote(playerName, emotes.dance)
						end
					end
				end
			end
		end)
		foo:setClickableRange(80)

		-- Pinata
		pinataBody = callback("pbody", 1880, 470, 155, 250, true)
		pinataBody:setAction(pinata)
		pinataBody:setClickableRange(80)
		pinataPaw = callback("ppaw", 1980, 610, 55, 100, true)
		pinataPaw:setAction(pinata)
		pinataPaw:setClickableRange(80)

		-- Guitars
		for i = 1, #objects.guitar do
			selectableGuitars[i] = callback("guitar", 160 + i * 85, 150, 51, 105, true):fixed()
			selectableGuitars[i]:setAction(selectGuitar)
		end

		guitar = callback("hguitar", 250, 527, 60, 45, true)
		guitar:setAction(displayGuitars)
		guitar:setClickableRange(60)

		for playerName, data in next, tfm.get.room.playerList do
			system.loadPlayerData(playerName)
		end
	end

	eventPlayerDataLoaded = function(playerName, data)
		playerData:newPlayer(playerName, data)

		if playerData:get(playerName, "fixingPinata") then
			pinataPaw:displayCallback(playerName)

			playerCache[playerName].remImg.pinata = tfm.exec.addImage(objects.pinata[getPinataStage(playerData:get(playerName, "collected"))], "?2", 1820, 390, playerName)
			ui.bar(playerName, playerData:get(playerName, "collected"))

			tfm.exec.addImage(objects.npc.miguel.crying, "!1", 2025, 122, playerName)

			tfm.exec.addImage(objects.confetti[0], "&100", 720, 372, playerName)
			for i = 1, #locations do
				playerCache[playerName].remImg.collectables[i] = tfm.exec.addImage(objects.confetti[1], "?100", locations[i][1] - 20, locations[i][2] - 20, playerName)
			end
		else
			pinataBody:displayCallback(playerName)

			tfm.exec.addImage(objects.pinata[5], "?2", 1820, 390, playerName)
			ui.bar(playerName, playerData:get(playerName, "collected"))

			tfm.exec.addImage(objects.npc.miguel.normal, "!1", 2025, 122, playerName)

			tfm.exec.addImage(objects.candy[0], "&100", 710, 372, playerName)

			playerCache[playerName].candies = table.getRandomIndexes(locations, math.floor(#locations / 2), true)

			for i = 1, #playerCache[playerName].candies do
				playerCache[playerName].candies[i] = {
					candy = math.random(#objects.candy),
					pos = playerCache[playerName].candies[i],
					time = os.time() + math.random(module.time_to_teleport_candy - 3000, module.time_to_teleport_candy + 3000)
				}

				local v = playerCache[playerName].candies[i]

				playerCache[playerName].candyFreeSpot[v.pos] = false

				playerCache[playerName].remImg.collectables[i] = tfm.exec.addImage(objects.candy[v.candy], "?100", locations[v.pos][1] - 20, locations[v.pos][2] - 20, playerName)
			end

			for i = 1, module.total_guitars_in_simon_says do
				playerCache[playerName].simonSaysSequence[i] = math.random(#objects.guitar)
			end
			-- current, sequence
			playerCache[playerName].simonSaysSequence = {
				id = 0,
				seq = playerCache[playerName].simonSaysSequence
			}

			if hasConcluded(playerName, "orb") then
				playerCache[playerName].hasPet = true
				bindArrows(playerName, true)
				eventKeyboard(playerName, keys[(tfm.get.room.playerList[playerName].isFacingRight and "right" or "left")])
			end
			checkForBadge(playerName)
		end
		ui.displayCollectedItems(playerName)

		system.bindKeyboard(playerName, keys.spacebar, true, true)
		system.bindKeyboard(playerName, keys.down, true, true)

		playerCache[playerName].dataLoaded = true
	end

	eventKeyboard = function(playerName, key, down, x, y)
		if not canStart or not playerCache[playerName] or not playerCache[playerName].dataLoaded then return end

		if key == keys.spacebar or key == keys.down then
			-- Not in a dialog
			if playerCache[playerName].dialog[1] == 0 then
				if os.time() > playerCache[playerName].collectTimer then
					playerCache[playerName].collectTimer = os.time() + 500

					local list = (playerData:get(playerName, "fixingPinata") and locations or playerCache[playerName].candies)
					for i = 1, #list do
						if playerCache[playerName].remImg.collectables[i] then
							local location = locations[(playerData:get(playerName, "fixingPinata") and i or list[i].pos)]
							if not playerCache[playerName].collectedItems[i] and math.pythag(x, y, location[1], location[2], 30) then
								playerCache[playerName].collectedItems[i] = true

								playerCache[playerName].collected = playerCache[playerName].collected + 1
								ui.displayCollectedItems(playerName)

								tfm.exec.removeImage(playerCache[playerName].remImg.collectables[i])

								local img = (playerData:get(playerName, "fixingPinata") and objects.confetti[-1] or objects.candy[-list[i].candy])
								playerCache[playerName].remImg.collectables[i] = tfm.exec.addImage(img, "?100", location[1] - 20, location[2] - 20, playerName)
								return
							end
						end
					end

					for k, v in next, callback do
						if (k ~= "guitar" and k ~= "hguitar" and k ~= (playerData:get(playerName, "fixingPinata") and "pbody" or "ppaw")) and v:inClickableRange(playerName) then
							return v:performAction(playerName, 0)
						end
					end
				end

				-- Consumables
				if key == keys.down and os.time() > playerCache[playerName].consumableTimer then
					for l = 1, #consumableLocations do
						if isInsideRectangle({ x = x, y = y }, consumableLocations[l][1], consumableLocations[l][2], consumableLocations[l][3], consumableLocations[l][4]) then
							if math.random(20) == math.random(20) then
								playerCache[playerName].consumableTimer = os.time() + 5000
								tfm.exec.giveConsumables(playerName, consumables[consumableLocations[l][5]], 1)
							end
							return
						end
					end
				end
			elseif key == keys.spacebar then
				if playerCache[playerName].dialog[1] == -1 then
					-- Closes the dialog
					ui.removeDialog(playerName)

					if playerCache[playerName].displaySimonSays == 1 then
						displayGuitars(nil, playerName)
					end
				else
					-- Skips the writing effect of the dialog
					playerCache[playerName].dialog[2] = 999 -- Text display won't exceed 999 characters.
				end
			end
		else
			local keyId = (key == keys.left and 1 or 2)
			if playerCache[playerName].hasGuitar and keyId ~= playerCache[playerName].lastPress.guitar then
				playerCache[playerName].lastPress.guitar = keyId
				linkObjectToPlayer(playerName, "guitar", keyId, objects.guitar[0])
			end
			if playerCache[playerName].hasPet and keyId ~= playerCache[playerName].lastPress.pet then
				playerCache[playerName].lastPress.pet = keyId
				linkObjectToPlayer(playerName, "pet", keyId)
			end
		end
	end

	-- Others
	eventTextAreaCallback = function(id, playerName, cbk)
		if not canStart or not playerCache[playerName] or not playerCache[playerName].dataLoaded then return end

		local c = string.split(cbk, "[^%.]+")

		if c[1] == "callback" then
			if callback[c[2]] then
				if callback[c[2]]:inClickableRange(playerName) then
					callback[c[2]]:performAction(playerName, id - enums.callback)
				end
			end
		end
	end

	eventNewPlayer = function(playerName)
		loadAllImages(playerName)
		tfm.exec.addImage(map.background, "?0", 0, 0, playerName)
	end

	eventLoop = function(currentTime, remainingTime)
		if remainingTime < 500 then
			playerCache = { }
			time = -1
			pinataBody = nil
			pinataPaw = nil
			selectableGuitars = { }
			guitar = nil
			timersWorking = 0
			canStart = false
			tfm.exec.newGame(module.map)
			return
		end

		if timersWorking > -1 and timersWorking < 2 then
			timersWorking = timersWorking + .5
			if timersWorking == 2 then
				canStart = true
				timersWorking = 3
			else
				return
			end
		end
		if timersWorking > 2 then
			timersWorking = timersWorking == 3 and 2 or -1
			tfm.exec.chatMessage("<CE><p align='center'>" .. translation.welcome .. "</p>")
			ui.setMapName(table.random(module.team))
		end
		if timersWorking == 2 then -- When timers are not working
			update(6)
		end

		if time >= 0 then
			time = time + .5

			if time == .5 then
				for playerName, data in next, playerCache do
					if data.guitarTimer == 0 and data.remImg.highlightGuitar > 0 then
						tfm.exec.removeImage(data.remImg.highlightGuitar)
						data.remImg.highlightGuitar = 0
					end
				end
			elseif time == 1 then
				for playerName, data in next, playerCache do
					if data.displaySimonSays == 666 then
						data.simonSaysSequence.id = data.simonSaysSequence.id + 1
						if data.simonSaysSequence.id > #data.simonSaysSequence.seq then
							data.simonSaysSequence.id = 1
							return removeGuitars(playerName)
						end

						data.remImg.highlightGuitar = tfm.exec.addImage(objects.guitar[data.simonSaysSequence.seq[data.simonSaysSequence.id]], ":200", 160 + data.simonSaysSequence.seq[data.simonSaysSequence.id] * 85, 150, playerName)
					end
				end
			elseif time == 1.5 then
				time = 0
				for playerName, data in next, playerCache do
					teleportCandies(playerName)
					--if data.displaySimonSays == 2 and tfm.get.room.playerList[playerName].x > 860 then
					--	removeGuitars(playerName)
					--end
				end

				-- Two loops so it doesn't interfer in the speed of important stuff
				--for playerName, data in next, tfm.get.room.playerList do
				--	if math.abs(data.vx) > 0.3 then
				--		-- In the fallen leaves
				--		local dir, vx
				--		if (data.x >= 120 and data.x <= 930) or ((data.x > 930 and data.x <= 1560) and data.y > 620) or (data.x > 1560 and data.y > 680) then
				--			dir = (data.isFacingRight and -1 or 1)
				--			for i = 3, math.random(3, 5) do
				--				vx = math.random(10, 30) / 10 * dir
				--				tfm.exec.displayParticle(table.random(particles.walking), data.x + math.random(30) * -dir, data.y + math.random(-5, 5), vx, -(math.random(15, 30) / 10), -vx * .01, .25, playerName)
				--			end
				--		end
				--	end
				--end

				local f, xs, ys, xa
				for i = 0, 4 do
					for j = 1, 4 do
						f = j % 2 == 0
						xs = math.random(10, 20) / 10
						ys = math.random(10, 18) / 10
						xa = math.random(10, 15) / 100
						tfm.exec.displayParticle(table.random(particles.tree), 90 + (i * 50), 300 + math.random(200), (f and xs or -xs), ys, (f and -xa or xa), 0)
					end
				end
			end
		end
	end

	-- Not events
	system.newTimer(function()
		timersWorking = 4
		canStart = true
	end, 1000, false)

	system.looping(function()
		update()
	end, 12)

	--[[ Init ]]--
	for _, f in next, { "AfkDeath", "AutoShaman", "AutoTimeLeft", "DebugCommand", "MortCommand", "PhysicalConsumables" } do
		tfm.exec["disable" .. f]()
	end

	tfm.exec.newGame(module.map)
end

modules.evt_pizzeria = function()
	do
		system.savePlayerData = function(player, data)
			PLAYERDATA[player] = data
		end
		system.loadPlayerData = function(player)
			eventPlayerDataLoaded(player, PLAYERDATA[player] or '')
		end
	end
	--[[ Module Info ]]--
	math.randomseed(os.time())
	local module = { 
		name = "pizzeria",
		title = "titre_pizza",
		time = 2.75 * 60,
		map = '<C><P H="800" L="2000" APS="16484935025.png,0,0,128,1000,672,0,128;1648034eff0.png,0,1632,319,330,470,1632,319" /><Z><S><S P="0,0,0.3,0.2,37,0,0,0" L="20" H="448" X="759" Y="524" T="12" lua="0" /><S L="776" X="388" H="20" Y="349" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="104" H="20" X="938" Y="349" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="13" X="995" H="686" Y="343" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="1100" X="550" H="10" Y="125" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="240" H="20" X="870" Y="544" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="633" H="20" X="317" Y="544" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="1000" X="500" H="30" Y="786" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="10" X="1001" H="10" Y="778" T="12" P="0,0,0.3,0.2,35,0,0,0" /><S L="27" H="17" X="1618" Y="436" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="1000" H="25" X="1500" Y="788" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="340" X="1790" H="11" Y="770" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="20" H="10" X="1615" Y="774" T="12" P="0,0,0.3,0.2,-35,0,0,0" /><S L="10" H="290" X="1636" Y="560" T="12" P="0,0,0.7,0.2,0,0,0,0" /><S L="18" X="1951" H="355" Y="587" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="10" H="290" X="1645" Y="560" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S P="0,0,0.3,0.2,-56,0,0,0" L="16" H="220" c="1" Y="369" T="12" X="1893" /><S L="15" H="22" X="1610" Y="431" T="12" P="0,0,2.5,0.2,58,0,0,0" /><S P="0,0,0.3,0.2,58,0,0,0" L="15" H="90" c="1" Y="403" T="12" X="1655" /><S X="1772" L="15" H="87" c="1" Y="330" T="12" P="0,0,0.3,0.2,58,0,0,0" /><S L="225" H="27" X="480" Y="759" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="20" X="594" H="10" Y="765" T="13" P="0,0,0.3,0.2,0,0,0,0" /><S L="20" H="10" X="372" Y="765" T="13" P="0,0,0.3,0.2,0,0,0,0" /><S L="10" X="0" H="800" Y="400" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="170" X="1892" H="12" Y="658" T="12" P="0,0,0.3,0.2,-49,0,0,0" /><S L="215" H="12" X="1757" Y="596" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="10" H="10" X="1836" Y="723" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="15" H="12" X="624" Y="703" T="12" P="0,0,0.7,0.2,0,0,0,0" /><S L="560" H="30" X="317" Y="519" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="110" H="40" X="200" Y="752" T="12" P="0,0,0.3,0.2,0,0,0,0" /><S L="20" H="10" X="123" Y="765" T="13" P="0,0,0.3,0.2,0,0,0,0" /><S L="20" H="10" X="278" Y="765" T="13" P="0,0,0.3,0.2,0,0,0,0" /><S L="25" X="1618" H="105" Y="725" T="9" m="" P="0,0,,,,0,0,0" /><S L="25" H="50" X="1618" Y="497" T="9" m="" P="0,0,,,,0,0,0" /><S P="0,0,,,,0,0,0" L="25" H="300" Y="607" T="9" m="" X="1972" /></S><D><DS Y="730" X="400" /></D><O /></Z></C>'
	}

	--[[ Development ]]--
	local team = {
		developer = { "Bolodefchoco#0000" },
		artist = { "Liiliiith#0000" },
		others = { "Acer#1412", "Bishomilad#0000", "Heniyengui#0000", "Nettoork#0000" }
	}

	--[[ Translations ]]--
	local translation = setmetatable({
		en = {
			event_concluded = "You completed the event! (╯°□°）╯︵ ┻━┻\n\nMagic by <B>%s</B>, special effects by <B>%s</B>, and goodwill of the <B>Unofficial Translators Team</B>.\n<font size='9'>Special thanks to %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banana",
					[2] = "Basil",
					[3] = "Bellpeper",
					[4] = "Broccoli",
					[5] = "Cocoa",
					[6] = "Mushroom",
					[7] = "Olive",
					[8] = "Onion",
					[9] = "Oregano",
					[10] = "Pineapple",
					[11] = "Tomato",
					[12] = "Wheat"
				},
				meat = {
					[1] = "Bacon",
					[2] = "Chicken",
					[3] = "Pepperoni"
				},
				others = {
					[1] = "Cheese",
					[2] = "Chocolate",
					[3] = "Olive Oil",
					[4] = "Tomato Sauce"
				},
				dough = {
					salt = "Salt",
					sugar = "Sugar",
					water = "Water"
				}
			},
			pizza = {
				[1] = "Chicken",
				[2] = "Chocolate",
				[3] = "Margherita",
				[4] = "Pepperoni",
				[5] = "Pineapple",
				[6] = "Vegetarian",
				burned = "Your pizza burned! :( You careless!",
				deliver = "Press space to deliver the pizza to the customer!"
			},
			ui = {
				order = "Order",
				market = "Market",
				smasher = "Smasher",
				freezer = "Fridge",
				cabinet = "Cabinet",
				garbage = "Garbage",
				close = "Close"
			},
			order = {
				customer = "Customer",
				total = "Total",
				delivered = {
					[0] = "Ouch! The customer was not satisfied :s",
					[1] = "Ugh! You did not deliver a decent pizza. The customer paid only $%d for it.",
					[2] = "Pizza! The customer enjoyed it, but there were some flaws and they paid $%d :/",
					[3] = "Yay! You delivered the pizza perfectly! The customer appreciated your effort and paid $%d!!!"
				}
			},
			harvest = {
				full = "Your %s is full! Consider using the garbage to throw some ingredients away!",
				harvest = "You harvested %s!",
				cost = "Cost",
				h_time = "Harvest",
				seed = "Seed" -- "To Seed"
			},
			smasher = {
				smash = "Smash",
				not_quantity = "%s can be obtained by smashing <B>%d</B> %s.", -- Tomato Sauce can be obtained by smashing 10 tomatoes
				get = "You got a <B>%s</B>. Find it in your <B>%s</B>."
			},
			storage = {
				empty_f = "Your fridge is empty! :(",
				empty_c = "Your cabinet is empty! :(",
				empty_g = "You do not have any ingredient to throw away.",
				select = "Select"
			},
			assembling = {
				select = "You need to select an ingredient in order to assemble the pizza!",
				req = {
					[1] = "Find <B>water</B> to make the pizza dough!",
					[2] = "Some <B>olive oil</B> is also necessary to make the pizza dough!",
					[3] = "A pinch of <B>%s</B> to make the dough tasty!",
					[4] = "Now some <B>wheat flour</B>... mmm, it will be perfect!",
					[5] = "Oh, forgot the <B>tomato sauce</B>!" -- Not sweet
				},
				no_ingredients = "You do not have ingredients to make a pizza. Plant the necessary ingredients according to the order.",
				stop = "Press <B>spacebar</B> once you conclude the pizza assembling!",
			},
			garbage = "Trash",
			go_oven = "Take the pizza to the oven.",
			insufficient_wheat = "For a quality pizza dough, at least <B>%s</B> wheats are needed!",
			feed = "Feed this animal with <B>%d</B> wheats.",
			animal = "Now that you fed it, keep ducking until you get an item.",
			animal_ingred = "You got the aliment... <B>%s</B>!",
			oven_assemble = "You can not make another pizza while you use the oven!",
			insufficient_cash = "You do not have enough money to do it!",
			welcome = "Mamma mia! Chef Remy hired you to make the most delicious pizzas ever! Be ready and use all the resources in the place.\n\nAfter harvesting the requested ingredients in their respective quantities, go to the dough roller and assemble the pizza.\n\nFollow the cooking time and ingredient quantities for a perfect pizza!",
			error_timer = "There is a glitch with timers. We are working to fix this as soon as possible. Sorry for the inconvenience. :)"
		},

		ar = {
			event_concluded = "(╯°□°）╯︵ ┻━┻ !لقد أكملت الحدث\n\n،%s السحر من قبل\n،%s المؤثرات الخاصة من قبل\n<B>فريق المترجمين غير الرسمي</B>والرغبة الحسنة لـ\n<font size='9'>... ,%sشكر خاص لـ</font>",
			ingredient = {
				plant = {
					[1] = "موز",
					[2] = "ريحان",
					[3] = "فلفل حلو",
					[4] = "قنبيط أخضر",
					[5] = "كاكاو",
					[6] = "فطر",
					[7] = "زيتون",
					[8] = "بصل",
					[9] = "توابل",
					[10] = "أناناس",
					[11] = "طماطم",
					[12] = "قمح"
				},
				meat = {
					[1] = "لحم خنزير مقدد",
					[2] = "دجاج",
					[3] = "ببروني"
				},
				others = {
					[1] = "جبن",
					[2] = "شوكولاتة",
					[3] = "زيت الزيتون",
					[4] = "صلصة طماطم"
				},
				dough = {
					salt = "ملح",
					sugar = "سكر",
					water = "ماء"
				}
			},
			pizza = {
				[1] = "دجاج",
				[2] = "شوكولاتة",
				[3] = "مارغريتا",
				[4] = "ببروني",
				[5] = "أناناس",
				[6] = "نباتية",
				burned = "!لقد أحرقت البيتزا الخاصة بك! :( أنت مهمل",
				deliver = "!أوصل البيتزا إلى الزبون"
			},
			ui = {
				order = "الطلب",
				market = "السوق",
				smasher = "آلة السحق",
				freezer = "ثلاجة",
				cabinet = "خزانة",
				garbage = "قمامة",
				close = "غلق"
			},
			order = {
				customer = "زبون",
				total = "المجموع",
				delivered = {
					[0] = ":/ أوتش! العميل لم يكن راضيًا",
					[1] = "اغغغ! أنت لم تقدم بيتزا لائقة. دفع العميل $%d فقط مقابل ذلك.",
					[2] = ":/ بيتزا! لقد استمتع العميل بها، ولكن كانت هناك بعض العيوب ودفع $%d فقط ",
					[3] = "!!!ياي! قمت بتوصيل البيتزا تماما! يقدر العميل مجهودك ودفع $%d لك"
				}
			},
			harvest = {
				full = "!لقد امتلئت %s ! فكر في استخدام القمامة لرمي بعض المكونات بعيدا",
				harvest = "!لقد حصدت %s",
				cost = "الثمن",
				h_time = "حصاد",
				seed = "إزرع"
			},
			smasher = {
				smash = "سحق",
				not_quantity = "يمكن الحصول على %s عن طريق سحق %d %s.",
				get = ".لقد حصلت على %s. أعثر عليه في %s الخاصّة بك"
			},
			storage = {
				empty_f = "): !الثلاجة فارغة",
				empty_c = "): !الخزانة فارغة",
				empty_g = ".ليس لديك أي عنصر للتخلص منه",
				select = "اختار"
			},
			assembling = {
				select = "!تحتاج لتحديد العناصر بترتيب صحيح لتجميع البيتزا",
				req = {
						[1] = "!لصنع عجينة البيتزا <B>ماء</B> اعثر على",
						[2] = "!ضروري أيضا لصنع عجينة البيتزا <b>زيت الزيتون</B> بعض ",
						[3] = "!لجعل العجين لذيذًا <B>%s</B> القليل من",
						[4] = "...ممم، سيكون مثاليا <B>القمح</B>الآن بعض",
						[5] = "!<B>صلصة الطماطم</B> أوه، نسيت"
				},
				no_ingredients = "ليس لديك المكونات لصنع البيتزا. إزرع المكونات اللازمة وفقا للترتيب.",
				stop = "!اضغط على زر المسافة بمجرد انتهائك من تجميع البيتزا"
			},
			garbage = "ألقي",
			go_oven = ".خذ البيتزا إلى الفرن",
			insufficient_wheat = "!للحصول على عجينة بيتزا عالية الجودة، يجب على الأقل <B>%s</B> إحضار القمح",
			feed = ".اطعم هذا الحيوان بـ <b>%d</B> قمح",
			animal = ".الان قمت بإطعامه، استمر بإنزال رأسك إلى ان تحصل على أداة",
			animal_ingred = "!لقد حصلت على الغذاء... <B>%s</B>",
			oven_assemble = "!لا يمكنك صنع بيتزا أخرى بينما أنت تستخدم الفرن",
			insufficient_cash = "!ليس لديك ما يكفي من المال للقيام بها",
			welcome = ".ماما ميا! عينك الشيف ريمي لصنع أفضل بيتزا لذيذة على الإطلاق! كن مستعد واستخدم جميع الموارد في المكان\n\nبعد حصد المكونات المطلوبة حسب الكمية الخاصة بكلّ منها، انتقل إلى آلة تدوير العجين وقم بتجميع البيتزا.\n\nاتبع الوقت المخصص للطبخ والكميات المطلوبة من أجل بيتزا متكاملة!"
		},
		br = {
			event_concluded = "Você completou o evento! (╯°□°）╯︵ ┻━┻\n\nMágica feita por <B>%s</B>, efeitos especiais por <B>%s</B>, e a boa-vontade da <B>Equipe não-oficial de Tradutores </B>.\n<font size='9'>Agradecimentos especiais a %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banana",
					[2] = "Manjericão",
					[3] = "Pimentão",
					[4] = "Brócolis",
					[5] = "Cacau",
					[6] = "Cogumelo",
					[7] = "Azeitona",
					[8] = "Cebola",
					[9] = "Orégano",
					[10] = "Abacaxi",
					[11] = "Tomate",
					[12] = "Trigo"
				},
				meat = {
					[1] = "Bacon",
					[2] = "Frango",
					[3] = "Calabresa"
				},
				others = {
					[1] = "Queijo",
					[2] = "Chocolate",
					[3] = "Azeite",
					[4] = "Molho de tomate"
				},
				dough = {
					salt = "Sal",
					sugar = "Açúcar",
					water = "Água"
				}
			},
			pizza = {
				[1] = "Frango",
				[2] = "Chocolate",
				[3] = "Margherita",
				[4] = "Calabresa",
				[5] = "Abacaxi",
				[6] = "Vegetariana",
				burned = "Sua pizza queimou! :( Que descuidado!",
				deliver = "Entregue a pizza ao cliente!"
			},
			ui = {
				order = "Pedido",
				market = "Mercado",
				smasher = "Máquina trituradora",
				freezer = "Congelador",
				cabinet = "Armário",
				garbage = "Lixo",
				close = "Fechar"
			},
			order = {
				customer = "Cliente",
				total = "Total",
				delivered = {
					[0] = "Oh não! O cliente não ficou satisfeito :/",
					[1] = "Que pena! Você não entregou uma boa pizza. O cliente só pagou $%d pela pizza.",
					[2] = "Pizza! O cliente gostou, mas havia algumas falhas e por isso só pagou $%d :/",
					[3] = "Uau! Você entregou uma pizza perfeitamente deliciosa! O cliente gostou do seu esforço e pagou $%d!!!"
				}
			},
			harvest = {
				full = "Seu %s está cheio! Considere usar o lixo para jogar alguns ingredientes fora!",
				harvest = "Você colheu %s!",
				cost = "Custo",
				h_time = "Colheita",
				seed = "Plantar"
			},
			smasher = {
				smash = "Triturar",
				not_quantity = "%s pode ser obtido ao triturar <B>%d</B> %s.",
				get = "Você conseguiu um <B>%s</B>. Encontre-o no <B>%s</B>."
			},
			storage = {
				empty_f = "Seu congelador está vazio! :(",
				empty_c = "Seu armário está vazio! :(",
				empty_g = "Você não tem qualquer ingrediente para por no lixo",
				select = "Selecionar"
			},
			assembling = {
				select = "Você precisa escolher um ingrediente para montar a pizza!",
				req = {
					[1] = "Encontre <B>água</B> para fazer a massa de pizza!",
					[2] = "Um pouco de <B>azeite</B> também é necessário para fazer a massa de pizza!",
					[3] = "Uma pitada de <B>%s</B> para dar sabor à pizza!",
					[4] = "Agora um pouco de <B>farinha</B>... mmm, vai ficar perfeita!",
					[5] = "Não se esqueça do <B>molho de tomate</B>!"
				},
				no_ingredients = "Você não tem ingredientes para fazer uma pizza deliciosa. Plante os ingredientes necessários de acordo com o pedido.",
				stop = "Pressione a <B>barra de espaço</B> assim que você concluir a montagem da pizza!"
			},
			garbage = "Lixo",
			go_oven = "Leve a pizza ao forno.",
			insufficient_wheat = "Para uma pizza de excelente qualidade, pelo menos <B>%s</B> trigos são necessários!",
			feed = "Alimente este animal com <B>%d</B> trigos.",
			animal = "Agora que você o alimentou, continue abaixando repetidamente até obter um item",
			animal_ingred = "Você obteve o alimento... <B>%s</B>!",
			oven_assemble = "Você não pode fazer outra pizza enquanto estiver usando o forno!",
			insufficient_cash = "Você não tem dinheiro suficiente para isso!",
			welcome = "Mamma mia! O Chef Remy contratou você para fazer as pizzas mais deliciosas do mundo! Esteja atento e use todos os recursos no local!\n\nDepois de colher os ingredientes pedidos em suas respectivas quantidades, vá para o rolo de massa e monte sua pizza.\n\nSiga o ponto da pizza e a quantidade dos ingredientes para uma pizza perfeita!",
			error_timer = "Há um problema nos temporizadores. Estamos trabalhando para corrigir o problema o mais rápido possível. Desculpe pelo inconveniente. :)"
		},
		cn = {
			event_concluded = "你已经完成了活动! (╯°□°）╯︵ ┻━┻\n\n由<B>%s</B>创造魔法, <B>%s</B>制造特别效果,以及<B>非官方的翻译团队</B>亲切提供翻译.\n<font size='9'>特别感谢 %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "香蕉",
					[2] = "罗勒",
					[3] = "灯笼椒",
					[4] = "西兰花",
					[5] = "可可豆",
					[6] = "磨菇",
					[7] = "橄榄",
					[8] = "洋葱",
					[9] = "牛至",
					[10] = "菠萝",
					[11] = "蕃茄",
					[12] = "小麦"
				},
				meat = {
					[1] = "熏肉",
					[2] = "鸡肉",
					[3] = "意大利辣味香肠"
				},
				others = {
					[1] = "奶酪",
					[2] = "巧克力",
					[3] = "橄榄油",
					[4] = "蕃茄酱"
				},
				dough = {
					salt = "盐",
					sugar = "糖",
					water = "水"
				}
			},
			pizza = {
				[1] = "鸡肉披萨",
				[2] = "巧克力披萨",
				[3] = "玛格丽塔披萨",
				[4] = "意大利辣味香肠披萨",
				[5] = "菠萝披萨",
				[6] = "全素披萨",
				burned = "你的披萨烧焦了! :( 真不小心!",
				deliver = "把披萨递送到顾客手上吧!"
			},
			ui = {
				order = "点菜单",
				market = "市场",
				smasher = "捣碎器",
				freezer = "冰箱",
				cabinet = "橱柜",
				garbage = "垃圾",
				close = "关上"
			},
			order = {
				customer = "顾客",
				total = "总共",
				delivered = {
					[0] = "噢! 顾客不满意你的服务 :/",
					[1] = "额! 你没有递送一个令人满意的披萨。所以顾客只付了 $%d",
					[2] = "披萨! 顾客挺享受它可是还有一点瑕疵, 于是他付了 $%d :/",
					[3] = "超棒! 你递送了一个完美的披萨! 顾客非常欣赏你的努力成果而付了 $%d!!!"
				}
			},
			harvest = {
				full = "你的 %s 已经满了! 考虑把多余的食材丢弃在垃圾桶里面!",
				harvest = "你收割了 %s!",
				cost = "成本", 
				h_time = "收成",
				seed = "播种"
			},
			smasher = {
				smash = "捣碎",
				not_quantity = "%s 可以透过捣碎<B>%d</B>个 %s 获得。",
				get = "你得到一个 <B>%s</B>。 你可以在<B>%s</B>裡找到它。"
			},
			storage = {
				empty_f = "你的冰箱空空如也! :(",
				empty_c = "你的橱柜什么都没有只剩下灰尘! :(",
				empty_g = "你没有任何的材料可以丢掉。",
				select = "选择"
			},
			assembling = {
				select = "你需要选择一种材料来制造披萨!",
				req = {
					[1] = "请找到 <B>水</B> 来制作披萨的面团!",
					[2] = "一些 <B>橄榄油</B> 也是制作披萨面团的必须材料!",
					[3] = "加入少量的 <B>%s</B> 令面团的味道更好!",
					[4] = "现在加入一些 <B>小麦</B>... 唔, 这样就完美了!",
					[5] = "噢, 忘了 <B>蕃茄酱</B>!"
				},
				no_ingredients = "你没有足够的材料制作披萨。请根据点菜单种植需要的材料。",
				stop = "当你完成了披萨就按一下空格键!"
			},
			garbage = "丢弃",
			go_oven = "把披萨放进烤箱里面。",
			insufficient_wheat = "要制作具素质的披萨面团, 至少需要 <B>%s</B> 小麦!",
			feed = "用 <B>%d</B> 小麦喂饲这动物。",
			animal = "你已经喂饲了它, 持续按下直至获得物品。",
			animal_ingred = "你得到了肉类/动物制品... <B>%s</B>!",
			oven_assemble = "当烤箱正在使用的时候, 你不可以制作另外一个披萨!",
			insufficient_cash = "你没有足够的金钱进行这操作!",
			welcome = "我~的~天! 主厨 Remy 聘请了你來制作史上最美味的披萨! 请准备去善用这里所有的资源。\n\n在收割了足够数量的材料之后, 请到擀面杖那里制作披萨。\n\n根据点菜单上列出的材料以及时间烤制披萨就可以做出完美的披萨!"
		},
		ee = {
			event_concluded = "Sa lõpetasid sündmuse! (╯°□°）╯︵ ┻━┻\n\nMaagia <B>%s</B>poolt, erilised efektid <B>%s</B>poolt, ja <B>Unofficial Translators Team</B>heatahtlikus.\n<font size='9'>Erilised tänud %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banaan",
					[2] = "Basiilik",
					[3] = "Paprika",
					[4] = "Brokoli",
					[5] = "Kakao",
					[6] = "Seened",
					[7] = "Oliiv",
					[8] = "Sibul",
					[9] = "Oregano",
					[10] = "Ananass",
					[11] = "Tomat",
					[12] = "Nisu"
				},
				meat = {
					[1] = "Peekon",
					[2] = "Kana",
					[3] = "Pepperoni"
				},
				others = {
					[1] = "Juust",
					[2] = "Šokolaad",
					[3] = "Oliivi Õli",
					[4] = "Tomati Kaste"
				},
				dough = {
					salt = "Sool",
					sugar = "Suhkur",
					water = "Vesi"
				}
			},
			pizza = {
				[1] = "Kana",
				[2] = "Šokolaad",
				[3] = "Margherita",
				[4] = "Pepperoni",
				[5] = "Ananass",
				[6] = "Vegetaarne",
				burned = "Su pitsa kõrbes ära! :( Sa oled hooletu!",
				deliver = "Vii ptsa kliendile!"
			},
			ui = {
				order = "Tellimus",
				market = "Pood",
				smasher = "Purusti",
				freezer = "Külmuti",
				cabinet = "Kabinet",
				garbage = "Prügi",
				close = "Sule"
			},
			order = {
				customer = "Klient",
				total = "Kokku",
				delivered = {
					[0] = "Ai! Klient ei olnud rahul :/",
					[1] = "Ugh! Sa ei viinud kohale korralikku. Klient maksis ainult $%d selle eest.",
					[2] = "Pitsa! Klient nautis seda, aga seal olid mõned vead ta maksis $%d :/",
					[3] = "Yay! Sa toimetasid kohale täiusliku pitsa! Klient hindas sinu pingutust ja maksis $%d!!!"
				}
			},
			harvest = {
				full = "Sinu %s on täis! Kaaluge prügikasti kasutamist, et visata minema mõned koostisosad!",
				harvest = "Sa korjasid %s!",
				cost = "maksab",
				h_time = "Korja",
				seed = "Seeme"
			},
			smasher = {
				smash = "Purusta",
				not_quantity = "%s saab omandada purustades <B>%d</B> %s.",
				get = "Sa said <B>%s</B>. Leia see enda <B>%s</B>."
			},
			storage = {
				empty_f = "Sinu külmik on tühi! :(",
				empty_c = "Sinu kabinet on tühi! :(",
				empty_g = "Sul ei ole ühtegi ainet mida ära visata.",
				select = "Vali"
			},
			assembling = {
				select = "Sa pead valima ained ennem et valmistada pitsat!",
				req = {
					[1] = "Leia <B>vesi</B> et teha pitsa taigen!",
					[2] = "Natuke <B>oliivi õli</B> on ka vajalik et teha pitsa taigent!",
					[3] = "Tibake <B>%s</B> et see taigen teha maitsvaks!",
					[4] = "Nüüd natukene <B>nisu</B>... mmm, see tuleb täiuslik!",
					[5] = "Oh, unistasin <B>tomati kastme</B>!"
				},
				no_ingredients = "Sul ei ole ühtegi koostisosa, et teha pitsat. Külva vajalikud koostisosad mida on tellimuseks vaja.",
				stop = "Vajuta tühikut, kui sa oled lõpetanud pitsa tegemise!"
			},
			garbage = "Prügi",
			go_oven = "Vii pitsa ahju.",
			insufficient_wheat = "Kvaliteetse pitsa taigna jaoks on vaja vähemalt <B>%s</B> nisu!",
			feed = "Sööda sellele loomale <B>%d</B> nisu.",
			animal = "Nüüd kui sa teda söötsid, kükita kuni sa saad eseme.",
			animal_ingred = "Sa said eseme... <B>%s</B>!",
			oven_assemble = "Sa ei saa teha teist pitsat kui sa kasutad veel ahju!",
			insufficient_cash = "Sul ei ole piisavalt raha et seda teha!",
			welcome = "Mamma mia! Peakokk Remy palkas sinu et teha parimaid pitsasid! Ole valmis kasutama kõiki ressursse siin kohas.\n\nPärast vajalike ainete õiges koguses korjamist, mine taignarulli juurde ja pane kokku pitsa.\n\nJälgige küpsetusaega ja ainete kogust et teha täiuslik pitsa!"
		},
		es = {
			event_concluded = "¡Has completado el evento! (╯°□°）╯︵ ┻━┻\n\nMagia por <B>%s</B>, efectos especiales por <B>%s</B>, y benevolencia del <B>Equipo de Traductores no oficial</B>.\n<font size='9'>Agradecimientos especiales a %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Plátano",
					[2] = "Albahaca",
					[3] = "Pimiento",
					[4] = "Brócoli",
					[5] = "Cacao",
					[6] = "Champiñón",
					[7] = "Aceituna",
					[8] = "Cebolla",
					[9] = "Orégano",
					[10] = "Piña",
					[11] = "Tomate",
					[12] = "Trigo"
				},
				meat = {
					[1] = "Beicon",
					[2] = "Pollo",
					[3] = "Pepperoni"
				},
				others = {
					[1] = "Queso",
					[2] = "Chocolate",
					[3] = "Aceite de oliva",
					[4] = "Salsa de tomate"
				},
				dough = {
					salt = "Sal",
					sugar = "Azúcar",
					water = "Agua"
				}
			},
			pizza = {
				[1] = "Pollo",
				[2] = "Chocolate",
				[3] = "Margarita",
				[4] = "Pepperoni",
				[5] = "Piña",
				[6] = "Vegetariana",
				burned = "¡Tu pizza se ha quemado! :( ¡Eres un descuidado!",
				deliver = "¡Entrégale la pizza al cliente!"
			},
			ui = {
				order = "Pedido",
				market = "Mercado",
				smasher = "Trituradora",
				freezer = "Frigorífico",
				cabinet = "Armario",
				garbage = "Basura",
				close = "Cerrar"
			},
			order = {
				customer = "Cliente",
				total = "Total",
				delivered = {
					[0] = "¡Ay! El cliente no ha estado satisfecho :/",
					[1] = "¡Puaj! No has entregado una pizza decente. El cliente solo ha pagado $%d por ella.",
					[2] = "¡Pizza! El cliente la ha disfrutado, pero había varios errores y ha pagado $%d :/",
					[3] = "¡Hurra! ¡Has entregado la pizza perfectamente! ¡¡¡El cliente ha apreciado tu empeño y ha pagado $%d!!!"
				}
			},
			harvest = {
				full = "¡Tu %s está lleno!",
				harvest = "¡Has cosechado %s!",
				cost = "Precio",
				h_time = "Cosecha",
				seed = "Sembrar"
			},
			smasher = {
				smash = "Triturar",
				not_quantity = "Se puede obtener %s triturando <B>%d</B> %s."
			},
			storage = {
				empty_f = "¡Tu frigorífico está vacío! :(",
				empty_c = "¡Tu armario está vacío! :(",
				empty_g = "No tienes ningún ingrediente para desechar.",
				select = "Seleccionar"
			},
			assembling = {
				select = "¡Tienes que seleccionar un ingrediente para hacer la pizza!",
				req = {
					[1] = "¡Encuentra <B>agua</B> para hacer la masa de la pizza!",
					[2] = "¡También se necesita algo de <B>aceite de oliva</B> para hacer la masa de la pizza!", 
					[3] = "¡Una pizca de <B>%s</B> para hacer que la masa esté deliciosa!",
					[4] = "Ahora algo de <B>trigo</B>... mmm, ¡será perfecta!",
					[5] = "Oh, ¡te has olvidado de la <B>salsa de tomate</B>!"
				},
				no_ingredients = "No tienes ingredientes para hacer la pizza. Planta los ingredientes necesarios según el pedido.",
				stop = "¡Presiona la <B>barra espaciadora</B> una vez que concluyas el ensamblaje de la pizza!"
			},
			garbage = "Desechar",
			go_oven = "Lleva la pizza al horno.",
			insufficient_wheat = "¡Se necesitan al menos <B>%s</B> de trigo para una masa de pizza de calidad!",
			feed = "Alimenta a este animal con <B>%d</B> de trigo.",
			animal = "Ahora que lo has alimentado, sigue agachándote hasta que consigas un objeto.",
			animal_ingred = "Has conseguido el alimento... <B>%s</B>!",
			oven_assemble = "¡No puedes hacer otra pizza mientras estás utilizando el horno!",
			insufficient_cash = "¡No tienes suficiente dinero para hacerlo!",
			welcome = "Mamma mia! ¡El Chef Remy te ha contratado para hacer las pizzas más deliciosas del mundo! Prepárate y utiliza todos los recursos disponibles."
		},
		fr = {
			event_concluded = "Tu as complété l’événement! (╯°□°）╯︵ ┻━┻\n\nMagie par <B>%s</B>, effets spéciaux par <B>%s</B>, et bonne volonté de L'<B>Equipe des Traducteurs Non Officiel</B>.\n<font size='9'>Remerciements particuliers à %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banane",
					[2] = "Basilic",
					[3] = "Poivron",
					[4] = "Brocoli",
					[5] = "Cacao",
					[6] = "Champignon",
					[7] = "Olive",
					[8] = "Oignon",
					[9] = "Origan",
					[10] = "Ananas",
					[11] = "Tomate",
					[12] = "Blé"
				},
				meat = {
					[1] = "Bacon",
					[2] = "Poulet",
					[3] = "Chorizo"
				},
				others = {
					[1] = "Fromage",
					[2] = "Chocolat",
					[3] = "L'huile d'Olive",
					[4] = "Sauce Tomate"
				},
				dough = {
					salt = "Sel",
					sugar = "Sucre",
					water = "Eau"
				}
			},
			pizza = {
				[1] = "Poulet",
				[2] = "Chocolat",
				[3] = "Margarita",
				[4] = "Chorizo",
				[5] = "Ananas",
				[6] = "Végétarien",
				burned = "Ta pizza à brûlé! :( Tu es imprudent!",
				deliver = "Livre la pizza au client!"
			},
			ui = {
				order = "La commande",
				market = "Marché",
				smasher = "Concasseur",
				freezer = "Congélateur",
				cabinet = "Cabinet",
				garbage = "Poubelle",
				close = "Fermer"
			},
			order = {
				customer = "Client",
				total = "L'addition",
				delivered = {
					[0] = "Ouch! Le client n'a pas été satisfait :/",
					[1] = "Ugh! Ta pizza n'a pas été préparée correctement. Le client a seulement payé $%d pour cela.",
					[2] = "Pizza! Le client l'a adoré, mais il y avait quelques erreurs et il a payé $%d :/",
					[3] = "Yay! Tu as préparé une pizza parfaite! Le client a apprécié tes efforts et a payé $%d!!!"
				}
			},
			harvest = {
				full = "Ton %s est plein! Pense à utiliser la poubelle pour jeter des ingrédients!",
				harvest = "Tu as récolté %s!",
				cost = "Coût",
				h_time = "Récolte",
				seed = "Graine"
			},
			smasher = {
				smash = "Concasseur",
				not_quantity = "%s peut être obtenu en concassant <B>%d</B> %s.",
				get = "Tu as obtenu l'ingrédient suivant : <B>%s</B>. Retrouve le dans ton <B>%s</B>."
			},
			storage = {
				empty_f = "Ton congélateur est vide! :(",
				empty_c = "Ton placard est vide! :(",
				empty_g = "Tu n'as pas d'ingrédient à jeter.",
				select = "Sélectionner"
			},
			assembling = {
				select = "Tu dois choisir un ingrédient pour assembler la pizza!",
				req = {
					[1] = "Trouve de <B>l'eau</B> pour faire la pâte à pizza!",
					[2] = "Un peu <B>d'huile d'olive</B> est nécessaire pour faire la pâte à pizza",
					[3] = "Une pincée de <B>%s</B> pour rendre la pâte savoureuse!",
					[4] = "Maintenant, un peu de <B>blé</B>... mmm, elle sera parfaite!",
					[5] = "Oh, tu as oublié la <B>sauce tomate</B>!"
				},
				no_ingredients = "Tu n'as pas d'ingrédients pour faire une pizza. Plante les ingrédients nécessaires pour la commande.",
				stop = "Appuie sur espace une fois que tu as terminé de monter la pizza!"
			},
			garbage = "Poubelle",
			go_oven = "Mets la pizza au four.",
			insufficient_wheat = "Pour une bonne pâte à pizza, au moins <B>%s</B> blés sont nécessaires!",
			feed = "Nourris cet animal avec <B>%d</B> blés.",
			animal = "Maintenant que tu l'as nourri, Continue de te baisser jusqu'à ce que tu obtiennes un objet.",
			animal_ingred = "Tu as obtenu l'aliment... <B>%s</B>!",
			oven_assemble = "Tu ne peux pas faire une autre pizza tant que vous utilisez le four!",
			insufficient_cash = "Vous n'avez pas assez d'argent pour le faire!",
			welcome = "Mamma mia! Le chef Remy t'a embauché pour que tu prépares les meilleures pizza du monde! Sois prêt et utilise toutes les ressources disponibles.\n\nAprès avoir récolté les ingrédients nécessaires en quantité suffisante, rend toi au rouleau à pâtisserie et assemble la pizza.\n\nSuis le temps de cuisson et la quantité d'ingrédients pour obtenir une pizza parfaite!"
		},
		he = {
			event_concluded = "השלמת את האיוונט! (╯°□°）╯︵ ┻━┻\n\nקסם על ידי <B>%s</B>, אפקטים מיוחדים על ידי<B>%s</B>, והרצון הטוב של<B>קבוצת המתרגמים הלא רשמית</B>.\n<font size='9'>תודה מיוחדת ל%s, ...</font>",
			ingredient = {
				plant = {
					[1] = "בננה",
					[2] = "בזיליקום",
					[3] = "גמבה",
					[4] = "ברוקולי",
					[5] = "קקאו",
					[6] = "פטרייה",
					[7] = "זית",
					[8] = "בצל",
					[9] = "אורגנו",
					[10] = "אננס",
					[11] = "עגבנייה",
					[12] = "חיטה"
				},
				meat = {
					[1] = "בייקון",
					[2] = "עוף",
					[3] = "פפרוני"
				},
				others = {
					[1] = "גבינה",
					[2] = "שוקולד",
					[3] = "שמן זית",
					[4] = "רוטב עגבניות"
				},
				dough = {
					salt = "מלח",
					sugar = "סוכר",
					water = "מים"
				}
			},
			pizza = {
				[1] = "עוף",
				[2] = "שוקולד",
				[3] = "מרגריטה",
				[4] = "פפרוני",
				[5] = "אננס",
				[6] = "צמחוני",
				burned = "הפיצה שלך נשרפה! :( אתה חסר אחריות!",
				deliver = "מסור את הפיצה ללקוח!"
			},
			ui = {
				order = "הזמנה",
				market = "שוק",
				smasher = "פצצה",
				freezer = "מקפיא",
				cabinet = "ארון",
				garbage = "זבל",
				close = "לסגור"
			},
			order = {
				customer = "לקוח",
				total = "סך הכל",
				delivered = {
					[0] = "אאוץ'! הלקוח אינו מרוצה :/",
					[1] = "אוף! לא מסרת פיצה טובה. הלקוח שילם רק $%d בשבילה.",
					[2] = "פיצה! הלקוח נהנה ממנה, אבל היו כמה פגמים והוא שילם $%d :/",
					[3] = "יש! מסרת את הפיצה באופן מושלם! הלקוח העריך את מאמציך ושילם $%d!!!"
				}
			},
			harvest = {
				full = "ה- %s שלך מלא! שקול להשתמש בפח הזבל כדי לזרוק חלק מהמרכיבים!",
				harvest = "קצרת %s!",
				cost = "מחיר", 
				h_time = "יבול",
				seed = "לזרוע"
			},
			smasher = {
				smash = "לרסק",
				not_quantity = "%s מושג על ידי ריסוק <B>%d</B> %s.",
				get = "קיבלת <B>%s</B>. מצא את זה ב<B>%s</B>."
			},
			storage = {
				empty_f = "המקפיא שלך ריק! :(",
				empty_c = "הארון שלך ריק! :(",
				empty_g = "אין לך מרכיבים לזרוק.",
				select = "לבחור"
			},
			assembling = {
				select = "עליך לבחור מרכיב בשביל להכין פיצה!",
				req = {
					[1] = "מצא <B>מים</B> כדי להכין את בצק הפיצה!",
					[2] = "קצת <B>שמן זית</B> גם נחוץ על מנת להכין את בצק הפיצה!",
					[3] = "קמצוץ שלנ<B>%s</B> כדי להפוך את הבצק לטעים!",
					[4] = "ועכשיו קצת <B>חיטה</B>... מממ, זה יהיה מושלם!",
					[5] = "הו, שכחת את <B>רוטב העגבניות</B>!"
				},
				no_ingredients = "אין לך מרכיבים על מנת להכין פיצה. שתול את המרכיבים הנחוצים לפי הסדר.",
				stop = "לחץ על מקש הרווח ברגע שאתה מסיים את הרכבת הפיצה!"
			},
			garbage = "לזרוק",
			go_oven = "קח את הפיצה לתנור.",
			insufficient_wheat = "כדי להכין בצק איכותי, לפחות <B>%s</B> חיטה נחוצה!",
			feed = "תאכיל את החיה הזו עם<B>%d</B> חיטה.",
			animal = "עכשיו שהאכלת אותה, המשך להתכופף עד שתקבל פריט.",
			animal_ingred = "קיבלת את המזון... <B>%s</B>!",
			oven_assemble = "אתה לא יכול להכין עוד פיצה בזמן שהתנור בשימוש!",
			insufficient_cash = "אין לך מספיק כסף לעשות את זה!",
			welcome = "מאמה מיה! צ'ף רמי העסיק אותך על מנת להכין את הפיצות הכי טעימות! היה מוכן והשתמש בכל המשאבים באזור.\n\nלאחר השתלת המרכיבים המבוקשים בכמויות המכובדות להן, לך לרולר של הבצק והרכב את הפיצה.\n\nעקוב אחרי זמן הבישול וכמות המרכיבים בשביל פיצה מושלמת!"
		},
		hr = {
			event_concluded = "Završio si event! (╯°□°）╯︵ ┻━┻\n\nMagic by <B>%s</B>, specijalni efekti od <B>%s</B>, i dobar <B>Unofficial Translators Team</B>.\n<font size='9'>Specijalno hvala za %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banana",
					[2] = "Bosiljak",
					[3] = "Paprika",
					[4] = "Brokoli",
					[5] = "Kakao",
					[6] = "Pečurka",
					[7] = "Maslina",
					[8] = "Luk",
					[9] = "Origano",
					[10] = "Ananas",
					[11] = "Paradajz",
					[12] = "Pšenica"
				},
				meat = {
					[1] = "Slanina",
					[2] = "Piletina",
					[3] = "Feferone"
				},
				others = {
					[1] = "Sir",
					[2] = "Čokolada",
					[3] = "Maslinovo Ulje",
					[4] = "Kečap"
				},
				dough = {
					salt = "So",
					sugar = "Šećer",
					water = "Voda"
				}
			},
			pizza = {
				[1] = "Piletia",
				[2] = "Čokolada",
				[3] = "Margarita",
				[4] = "Feferone",
				[5] = "Ananas",
				[6] = "Vegeterijanska",
				burned = "Tvoja pica je izgorela! :( Ne paziš!",
				deliver = "Isporučena je pica mušteriji!"
			},
			ui = {
				order = "Naruči",
				market = "Market",
				smasher = "Mašina za drobljenje",
				freezer = "Frižider",
				cabinet = "Kabinet",
				garbage = "Đubre",
				close = "Zatvori"
			},
			order = {
				customer = "Kupac",
				total = "Ukupno",
				delivered = {
					[0] = "Ouch! Kupac nije bio zadovoljan :/",
					[1] = "Ugh! Nisi dostavio zadovoljavajuću picu. Kupac je platio samo $%d za nju.",
					[2] = "Pica! Kupcu se svidelo, ali je bilo nekih nedostataka i samo je platio/la $%d :/",
					[3] = "Yay! Dostavio si picu savršeno! Kupac je cijenio tvoj napor i platio je $%d!!!"
				}
			},
			harvest = {
				full = "Tvoj %s je pun! Uzmi u obzir da možeš da koristiš kantu za đubre da baciš neke sastojke!",
				harvest = "Ubrao si %s!",
				cost = "Košta",
				h_time = "Sakupljanje",
				seed = "Semenka"
			},
			smasher = {
				smash = "Smrviti",
				not_quantity = "%s možeš dobiti mrvljenjem <B>%d</B> %s.",
				get = "Dobio si  <B>%s</B>. Pronađi to u svom <B>%s</B>."
			},
			storage = {
				empty_f = "Tvoj frižider je prazan! :(",
				empty_c = "Tvoj kabinet je prazan! :(",
				empty_g = "Nemaš nikakav sastojak da baciš.",
				select = "Izaberi"
			},
			assembling = {
				select = "Moraš da izabereš sastojak da bih napravio picu!",
				req = {
					[1] = "Pronađi <B>vodu</B> da bi napravio koru od pice!",
					[2] = "Malo <B>maslinovog ulja</B> je takođe potrebno da bi napravio koru od pice!",
					[3] = "Pstohvat <B>%s</B> da napraviš koru ukusnom!",
					[4] = "I sada <B>pšenice</B>... mmm, bit će savršeno!",
					[5] = "Oh, zaboravio si <B>kečap</B>!"
				},
				no_ingredients = "Nemaš sastojke da napraviš picu. Posadi potrebne sastojke po receptu.",
				stop = "Kada završiš sa izradom pice pristisni razmak!"
			},
			garbage = "Đubre",
			go_oven = "Odnesi picu do pećnice.",
			insufficient_wheat = "Za dobru koru od pice, barem <B>%s</B> pšenice je potrebno!",
			feed = "Nahrani ovu životinju sa <B>%d</B> pšenicom.",
			animal = "Pošto si je nahranio, nastavi sve dok ne dobiješ neki predmet.",
			animal_ingred = "Dobio si aliment... <B>%s</B>!",
			oven_assemble = "Ne možeš napraviti još jednu picu dok koristiš pećnicu!",
			insufficient_cash = "Nemaš dovoljno para da to uradiš!",
			welcome = "Mamma mia! Šef Remy te zaposlio da napraviš najukusniju picu ikada! Budi spreman da uzmeš šta želiš od stvari iz ovog mjesta.\n\nPoslije berbe traženog sastojka u datim količinama, idi do valjka za tjesto i napravi picu.\n\nPrati vrijeme pravljenja i količine sastojaka za savršenu picu!",
		},
		hu = {
			event_concluded = "Teljesítetted az eventet! (╯°□°）╯︵ ┻━┻\n\nVarázslat <B>%s</B> által, különleges effektek <B>%s</B> által, és a <B>Nem Hivatalos Fordítók Csapata</B> jóakarata által.\n<font size='9'>Különleges köszönet ezeknek az egereknek: %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banán",
					[2] = "Bazsalikom",
					[3] = "Bell Paprika",
					[4] = "Brokkoli",
					[5] = "Kakaó",
					[6] = "Gomba",
					[7] = "Olajbogyó",
					[8] = "Hagyma",
					[9] = "Oregánó",
					[10] = "Ananász",
					[11] = "Paradicsom",
					[12] = "Búza"
				},
				meat = {
					[1] = "Szalonna",
					[2] = "Csirke",
					[3] = "Pepperoni"
				},
				others = {
					[1] = "Sajt",
					[2] = "Csokoládé",
					[3] = "Olívaolaj",
					[4] = "Paradicsom szósz"
				},
				dough = {
					salt = "Só",
					sugar = "Cukor",
					water = "Víz"
				}
			},
			pizza = {
				[1] = "Csirkés",
				[2] = "Csokoládés",
				[3] = "Margherita",
				[4] = "Szalámis",
				[5] = "Ananászos",
				[6] = "Vegetáriánus",
				burned = "Odaégett a pizzád! :( Figyelmetlen vagy!",
				deliver = "Vidd el a pizzát a megrendelőnek!"
			},
			ui = {
				order = "Rendelés",
				market = "Piac",
				smasher = "Nyomógép",
				freezer = "Hűtő",
				cabinet = "Konyhaszekrény",
				garbage = "Szemetes",
				close = "Bezár"
			},
			order = {
				customer = "Megrendelő",
				total = "Összeg",
				delivered = {
					[0] = "Auch! A megrendelő nem elégedett :/",
					[1] = "Ugh! Nem volt elég jó a pizzád. A megrendelő csak %d dollárt fizetett érte.",
					[2] = "Pizza! A megrendelőnek ízlett, de volt vele egy kis baj, szóval csak %d dollárt fizetett :/",
					[3] = "Yay! Tökéletes pizzát készítettél! A megrendelő értékeli a nehéz munkát, és %d dollárt fizetett!!!"
				}
			},
			harvest = {
				full = "A %s tele van! Próbáld a kukát használni, hogy eldobj pár hozzávalót amire nincs szükséged!",
				harvest = "Szedtél egy kis %s növényt!",
				cost = "Ár",
				h_time = "Termés",
				seed = "Ültet"
			},
			smasher = {
				smash = "Összenyom",
				not_quantity = "%s hozzávalót úgy szerezhetsz, hogy összenyomsz <B>%d</B> darab %s növényt.",
				get = "<B>%s</B> hozzávalót készítettél. Az alapanyagot keresd a <B>%s</B>ben."
			},
			storage = {
				empty_f = "Üres a hűtő! :(",
				empty_c = "Üres a konyhaszekrény! :(",
				empty_g = "Nincs hozzávalód, amit eldobhatnál.",
				select = "Választ"
			},
			assembling = {
				select = "Ki kell választanod egy hozzávalót, hogy összerakd a pizzát!",
				req = {
					[1] = "Találj <B>vizet</B> hogy elkészítsd a pizzatésztát!",
					[2] = "Egy kis <B>olívaolaj</B> is fontos a tészta elkészítéséhez!",
					[3] = "Egy csipetnyi <B>%s</B> is kellene, hogy a tészta finom legyen!",
					[4] = "Most egy kis <B>liszt</B>... mmm, tökéletes lesz!",
					[5] = "Oh, elfelejtetted a <B>paradicsom szószt</B>!"
				},
				no_ingredients = "Nincs hozzávalód, hogy pizzát tudj készíteni. Ültesd el a megfelelő hozzávalókat a rendelés alapján.",
				stop = "Nyomd le a szóközt, amint elkészültél a pizza összerakásával!"
			},
			garbage = "Eldob",
			go_oven = "Vidd a pizzát a kemencéhez.",
			insufficient_wheat = "Egy minőségi pizzatésztához legalább <B>%s</B> búzára lesz szükséged!",
			feed = "Etesd meg ezt az állatot <B>%d</B> búzával.",
			animal = "Most hogy megetetted, addig hajolj le amíg nem kapsz egy tárgyat.",
			animal_ingred = "Megszerezted a következő táplálékot: <B>%s</B>!",
			oven_assemble = "Nem tudsz másik pizzát készíteni, amíg a kemence használatban van!",
			insufficient_cash = "Nincs elég pénzed ehhez!",
			welcome = "Mamma mia! Remy Séf felvett téged a legfinomabb pizzák elkészítésére! Légy figyelmes és minden forrást használj fel a helyen.\n\nMiután learattad a megfelelő mennyiségű hozzávalókat amiket a megrendelő kért, menj a sodrófához és rakd össze a pizzát.\n\nTartsd be a sütési időt és a hozzávaló mennyiségeket, hogy tökéletes pizzát készíts!"
		},
		it = {
			event_concluded = "Hai completato l'evento! (╯°□°）╯︵ ┻━┻\n\nMagia da <B>%s</B>, effetti speciali da <B>%s</B>, e buona volontà del <B>Team Dei Traduttori Non-Ufficiale</B>.\n<font size='9'>Ringraziamenti speciali vanno a %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banana",
					[2] = "Basilico",
					[3] = "Peperone",
					[4] = "Broccolo",
					[5] = "Cacao",
					[6] = "Fungo",
					[7] = "Oliva",
					[8] = "Cipolla",
					[9] = "Origano",
					[10] = "Ananas",
					[11] = "Pomodoro",
					[12] = "Frumento"
				},
				meat = {
					[1] = "Bacon",
					[2] = "Pollo",
					[3] = "Salsiccia piccante"
				},
				others = {
					[1] = "Formaggio",
					[2] = "Cioccolato",
					[3] = "Olio d'oliva",
					[4] = "Salsa di pomodoro"
				},
				dough = {
					salt = "Sale",
					sugar = "Zucchero",
					water = "Acqua"
				}
			},
			pizza = {
				[1] = "Pollo",
				[2] = "Cioccolato",
				[3] = "Margherita",
				[4] = "Salsiccia piccante",
				[5] = "Ananas",
				[6] = "Vegetariana",
				burned = "La tua pizza si è bruciata! :( Sei irresponsabile!",
				deliver = "Premi spazio per consegnare la pizza al cliente!"
			},
			ui = {
				order = "Ordina",
				market = "Negozio",
				smasher = "Tritatutto",
				freezer = "Frigo",
				cabinet = "Armadio da cucina",
				garbage = "Spazzatura",
				close = "Chiudi"
			},
			order = {
				customer = "Cliente",
				total = "Totale",
				delivered = {
					[0] = "Ouch! Il cliente non era soddisfatto :s",
					[1] = "Ugh! Non hai consegnato una pizza decente. Il cliente ha pagato soltanto $%d.",
					[2] = "Pizza! Il cliente l'ha gradita, ma c'erano delle imperfezioni e il cliente ha pagato $%d :/",
					[3] = "Yay! Hai consegnato la pizza in modo perfetto! Il cliente ha apprezzato i tuoi sforzi e ha pagato $%d!!!"
				}
			},
			harvest = {
				full = "Il tuo %s è pieno! Considera l'uso del cestino per buttare via alcuni ingredienti!",
				harvest = "Hai raccolto %s!",
				cost = "Prezzo",
				h_time = "Raccogli",
				seed = "Seminare"
			},
			smasher = {
				smash = "Trita",
				not_quantity = "%s può essere ottenuto tritando <B>%d</B> %s.",
				get = "Hai ottenuto: <B>%s</B>. Trovalo nel tuo <B>%s</B>."
			},
			storage = {
				empty_f = "Il tuo frigo è vuoto! :(",
				empty_c = "Il tuo armadio da cucina è vuoto! :(",
				empty_g = "Non hai nessun ingrediente da buttare via.",
				select = "Seleziona"
			},
			assembling = {
				select = "Devi selezionare un ingrediente per comporre la pizza!",
				req = {
					[1] = "Trova dell'<B>acqua</B> per fare la pasta della pizza!",
					[2] = "Un po' di <B>olio d'oliva</B> è anche necessario per fare la pasta della pizza!",
					[3] = "Un pizzico di <B>%s</B> Per rendere la pasta saporita!",
					[4] = "Adesso un po' di <B>farina di frumento</B>... mmm, sarà perfetto!",
					[5] = "Ah, dimentico la <B>salsa di pomodoro</B>!"
				},
				no_ingredients = "Non hai gli ingredienti per fare una pizza. Pianta gli ingredienti necessari secondo l'ordine.",
				stop = "Premi spazio una volta composta la pizza!"
			},
			garbage = "Butta via",
			go_oven = "Porta la pizza nel forno.",
			insufficient_wheat = "Per un impasto della pizza di qualità, almeno <B>%s</B> di frumento sono necessari!",
			feed = "Sfama questo animale con <B>%d</B> di frumento.",
			animal = "Ora che l'hai sfamato, continua a piegarti finché non riceverai un oggetto.",
			animal_ingred = "Hai ottenuto l'alimento... <B>%s</B>!",
			oven_assemble = "Non puoi fare un'altra pizza mentre usi il forno!",
			insufficient_cash = "Non hai abbastanza soldi per farlo!",
			welcome = "Mamma mia! Lo Chef Remy ti ha ingaggiato per preparare le pizze più deliziose di sempre! Preparati e usa tutte le risorse del posto.\n\nDopo aver raccolto gli ingredienti richiesti nelle loro rispettive quantità, vai a rullare la pasta e componi la pizza.\n\nSegui il tempo di cottura e la quantità degli ingredienti per una pizza perfetta!"
		},
		lv = {
			event_concluded = "Jūs pabeidzāt šo notikumu! (╯°□°）╯︵ ┻━┻\n\nMaģija pateicoties <B>%s</B>, specefekti <B>%s</B>, un <B>Neoficiālās Tulkotāju Komamdas</B> labvēlība.\n<font size='9'>Īpašs paldies %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banāns",
					[2] = "Baziliks",
					[3] = "Paprika",
					[4] = "Brokolis",
					[5] = "Kakao",
					[6] = "Sēne",
					[7] = "Olīve",
					[8] = "Sīpols",
					[9] = "Oregano",
					[10] = "Ananāss",
					[11] = "Tomāts",
					[12] = "Kvieši"
				},
				meat = {
					[1] = "Bekons",
					[2] = "Vista",
					[3] = "Peperoni"
				},
				others = {
					[1] = "Siers",
					[2] = "Šokolāde",
					[3] = "Olīveļļa",
					[4] = "Tomātu mērce"
				},
				dough = {
					salt = "Sāls",
					sugar = "Cukurs",
					water = "Ūdens"
				}
			},
			pizza = {
				[1] = "Vistas",
				[2] = "Šokolādes",
				[3] = "Margarita",
				[4] = "Peperoni",
				[5] = "Ananāsu",
				[6] = "Veģetāriešu",
				burned = "Tava pica sadega! :( Tu bezrūpīgs!",
				deliver = "Piegādā picu klientam!"
			},
			ui = {
				order = "Pasūtījums",
				market = "Tirgus",
				smasher = "Sadauzītājs",
				freezer = "Ledusskapis",
				cabinet = "Plaukts",
				garbage = "Atkritumi",
			},
			order = {
				customer = "Klients",
				total = "Kopā",
				delivered = {
					[0] = "Au! Klients nebija apmierināts :/",
					[1] = "Fuj! Jūs nepiegādājāt pienācīgu picu. Klients samaksāja tikai $%d par to.",
					[2] = "Pica! Klients to izbaudīja, bet bija dažas nepilnības un viņš samaksāja $%d :/",
					[3] = "Urā! Jūs piegādājāt picu izcili! Klients novērtēja Jūsu pūles un samaksāja $%d!!!"
				}
			},
			harvest = {
				full = "Jūsu %s ir pilns! Apsveriet izmantot atkritumus, lai izmestu dažas sastāvdaļas ārā!",
				harvest = "Jūs novācāt %s!"
			},
			smasher = {
				smash = "Sadauzīt",
				not_quantity = "%s var tikt iegūti, sadaudzot <B>%d</B> %s.",
				get = "Jūs dabūjāt <B>%s</B>. Atrodiet tās savā <B>%s</B>."
			},
			storage = {
				empty_f = "Jūsu ledusskapis ir tukšs! :(",
				empty_c = "Jūsu plaukts ir tukšs :(",
				empty_g = "Jums nav sastāvdaļas ko izmest."
			},
			assembling = {
				select = "Juns ir jāizvēlās sastāvdaļas, lai varētu uztaisīt picu!",
				req = {
					[1] = "Atrodiet <B>ūdeni</B>, lai izveidotu picas mīklu!",
					[2] = "Nedaudz <B>olīveļļas</B> arī ir nepieciešama, lai izveidotu picas mīklu!",
					[3] = "Šķipsna no <B>%s</B> lai padarītu mīklu garšīgu!",
					[4] = "Tagad nedaudz <B>kviešus</B>... mmm, tas būs ideāli!",
					[5] = "Ak, aizmirsu <B>tomātu mērci</B>!"
				},
				no_ingredients = "Jums nav sastāvdaļas, lai izveidotu picu. Sastādiet nepieciešamās sastāvdaļas atbilstošajā secībā.",
				stop = "Nospiediet atsarpes taustiņu, kad esat beidzis picas salikšanu!"
			},
			go_oven = "Aizved picu uz krāsni.",
			insufficient_wheat = "Priekš kvalitatīvas picas mīklas, vismaz <B>%s</B> kvieši ir vajadzīgi!",
			feed = "Pabaro šo dzīvnieku ar <B>%d</B> kviešiem.",
			animal = "Tagad, kad Jūs to pabarojāt, turpiniet pietupties, līdz kamēr dabūjat priekšmetu.",
			animal_ingred = "Tu dabūji barību... <B>%s</B>!",
			oven_assemble = "Tu nevari izveidot vēl vienu picu, kamēr tu izmanto krāsni!",
			insufficient_cash = "Tev nav pietiekami daudz naudas, lai to izdarītu!",
			welcome = "Mamma mia! Pavārs Remy tevi nolīdza, lai tu izgatavotu visgaršīgākās picas jebkad! Esi gatavs un izmanto visus resursus šajā vietā.\n\nPēc vajadzīgo sastāvdaļu ievākšanas vajadzīgajos daudzumos, dodaties pie mīklas veltņa un sastādiet picu.\n\nSekojiet cepšanas laikam un sastāvdaļu daudzumam priekš izcilas picas!"
		},
		pl = {
			event_concluded = "Ukończyłeś/-aś event! (╯°□°）╯︵ ┻━┻\n\nMagia została stworzona przez <B>%s</B>, specjalne efekty zostały stworzone przez <B>%s</B>, a wszystko dzięki <B>Nieoficjalnej Drużynie Tłumaczy</B>.\n<font size='9'>Specjalne podziękowania dla %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banan",
					[2] = "Bazylia",
					[3] = "Papryka",
					[4] = "Brokuła",
					[5] = "Kakao",
					[6] = "Grzyb",
					[7] = "Oliwa",
					[8] = "Cebula",
					[9] = "Oregano",
					[10] = "Ananas",
					[11] = "Pomidor",
					[12] = "Pszenica"
				},
				meat = {
					[1] = "Bekon",
					[2] = "Kurczak",
					[3] = "Pepperoni"
				},
				others = {
					[1] = "Ser",
					[2] = "Czekolada",
					[3] = "Oliwa z oliwek",
					[4] = "Sos Pomidorowy"
				},
				dough = {
					salt = "Sól",
					sugar = "Cukier",
					water = "Woda"
				}
			},
			pizza = {
				[1] = "Kurczak",
				[2] = "Czekolada",
				[3] = "Margherita",
				[4] = "Pepperoni",
				[5] = "Hawajska",
				[6] = "Wegetariańska",
				burned = "Twoja pizza się spaliła! :( Nie pilnowałeś/-aś jej!",
				deliver = "Dostarcz pizzę do klienta!"
			},
			ui = {
				order = "Zamówienie",
				market = "Sklep",
				smasher = "Ubijaczka",
				freezer = "Lodówka",
				cabinet = "Szafka",
				garbage = "Śmieci",
				close = "Zamknij"
			},
			order = {
				customer = "Klient",
				total = "Łącznie",
				delivered = {
					[0] = "Ohh! Klient nie był zadowolony :/",
					[1] = "Ugh! Nie dostarczyłeś/-aś odpowiedniej pizzy. Klient zapłacił tylko $%d.",
					[2] = "Pizza! Klientowi smakowała, ale było kilka błędów, dlatego zapłacił $%d :/",
					[3] = "Yay! Dostarczyłeś idealną pizzę! Klient docenił twój wysiłek i zapłacił $%d!!!"
				}
			},
			harvest = {
				full = "Twój %s jest pełen! Rozważ użycie kosza na śmieci, aby wyrzucić niektóre składniki",
				harvest = "Zebrałeś/-aś %s!",
				cost = "Cena", 
				h_time = "Żniwa",
				seed = "Zasadź"
			},
			smasher = {
				smash = "Ubij",
				not_quantity = "%s może zostać odkryty poprzez ubijanie <B>%d</B> %s.",
				get = "Otrzymałeś/-aś <B>%s</B>. Znajdziesz to w <B>%s</B>."
			},
			storage = {
				empty_f = "Twoja lodówka jest pusta! :(",
				empty_c = "Twoja szafka jest pusta! :(",
				empty_g = "Nie masz żadnych składników, które możesz wyrzucić.",
				select = "Wybierz"
			},
			assembling = {
				select = "Musisz wybrać odpowiedni składnik w odpowiedniej kolejności, aby przygotować pizzę!",
				req = {
					[1] = "Znajdź <B>wodę</B>, aby przygotować ciasto do pizzy!",
					[2] = "Trochę <B>oliwy z oliwek</B> jest także wymagane, aby przygotować ciasto do pizzy!",
					[3] = "Dodaj szczyptę <B>%s</B> aby dodać smaku!",
					[4] = "Teraz trochę <B>pszenicy</B>... mmm, ale będzie smaczna!",
					[5] = "Oh, tylko nie zapomnij o <B>sosie pomidorowym</B>!"
				},
				no_ingredients = "Nie masz odpowiednich składników, aby przygotować pizzę. Zasadź wymagane składniki zważając na kolejność.",
				stop = "Kliknij spację jeden raz, aby zakończyć składanie pizzy!"
			},
			garbage = "Wyrzuć",
			go_oven = "Zanieś pizzę do piekarnika.",
			insufficient_wheat = "Aby podnieść jakość ciasta, przydałoby się dodać przynajmniej <B>%s</B> pszenic!",
			feed = "Nakarm zwierzęta <B>%d</B> pszenicami.",
			animal = "Zwierzę jest nakarmione, kucaj przy nim, aby dostać przedmioty.",
			animal_ingred = "Otrzymałeś/-aś pokarm... <B>%s</B>!",
			oven_assemble = "Możesz tylko piec jedną pizzę w jednym momencie!",
			insufficient_cash = "Nie masz wystarczającej ilości pieniędzy, aby to zrobić!",
			welcome = "Mamma mia! Szef Remy zatrudnił ciebie abyś stworzył/-a najpyszniejszą pizzę jaka kiedykolwiek powstała! Bądź gotowy/-a i użyj wszystkich surowców, które znajdują się na tej mapie.\n\nPo tym jak zbierzesz wymagane składniki w odpowiedniej ilości, podejdź po wałek do ciasta i przygotuj pizzę.\n\nPamiętaj o przestrzeganiu czasu pieczenia i  ilości składników, aby zrobić idealną pizzę!"
		},
		ro = {
			event_concluded = "Ai completat evenimentul! (╯°□°）╯︵ ┻━┻\n\nMagia de <B>%s</B>, efectele speciale de <B>%s</B>, și bunăvoința <B>Neoficial Echipa de Traducători</B>.\n<font size='9'>Mulțumiri speciale lui %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Banană",
					[2] = "Busuioc",
					[3] = "Ardei gras",
					[4] = "Brocoli",
					[5] = "Cocoa",
					[6] = "Cipercă",
					[7] = "Ulei",
					[8] = "Ceapă",
					[9] = "Oregano",
					[10] = "Ananas",
					[11] = "Roșie",
					[12] = "Grâu"
				},
				meat = {
					[1] = "Bacon",
					[2] = "Pui",
					[3] = "Pepperoni"
				},
				others = {
					[1] = "Brânză",
					[2] = "Ciocolată",
					[3] = "Ulei de măsline",
					[4] = "Sos de roșii"
				},
				dough = {
					salt = "Sare",
					sugar = "Zahăr",
					water = "Apă"
				}
			},
			pizza = {
				[1] = "Pui",
				[2] = "Ciocolată",
				[3] = "Margherita",
				[4] = "Pepperoni",
				[5] = "Ananas",
				[6] = "Vegetarian",
				burned = "Pizza ta a ars! :( Neatentule!",
				deliver = "Livrează pizza clientului!"
			},
			ui = {
				order = "Comandă",
				market = "Piață",
				smasher = "Zdrobitor",
				freezer = "Frigider",
				cabinet = "Dulap",
				garbage = "Gunoi",
			},
			order = {
				customer = "Client",
				total = "Total",
				delivered = {
					[0] = "Auci! Clientul nu a fost mulțumit :/",
					[1] = "Uh! N-ai livrat o pizza decentă. Clientul a platita platit doar $%d pe ea.",
					[2] = "Pizza! Clientului i-a placut, dar au fost niște greșeli așa că a plătit doar $%d :/",
					[3] = "Yay! Ai livrat pizza perfect! Clientului i-a plăcut efortul pe care l-ai depus asa ca a plătit $%d!!!"
				}
			},
			harvest = {
				full = "%s este plin! Folosește gunoiul pentru a arunca niște incrediente",
				harvest = "Ai recoltat %s!"
			},
			smasher = {
				smash = "Strivește",
				not_quantity = "%s poate fi obținut prin strivirea <B>%d</B> %s.",
				get = "Ai primit <B>%s</B>. O poți gasi în <B>%s</B> tău."
			},
			storage = {
				empty_f = "Frigiderul tău e gol! :(",
				empty_c = "Dulapul tău este gol! :(",
				empty_g = "Nu ai incrediente pe care să le arunci."
			},
			assembling = {
				select = "Trebuie să selectezi incredientele în ordine pentu a face pizza!",
				req = {
					[1] = "Găsește <B>apă</B> să faci aluat de pizza!",
					[2] = "Niște <B>ulei de măsline</B> e de asemenea necesar să faci aluat de pizza!",
					[3] = "Un vârf de <B>%s</B> face aluatul mai gustos!",
					[4] = "Acum niste <B>grâu</B>... mmm, va fi perfect!",
					[5] = "Oh, ai uitat <B>sosul de roșii</B>!"
				},
				no_ingredients = "Nu ai incrediente pentu a face pizza. Plantează incredientele necesare potrivite rețetei.",
				stop = "Apasă space atunci când crezi că pizza este gata!"
			},
			go_oven = "Ia pizza din cuptor.",
			insufficient_wheat = "Pentru un aluat de pizza bun, ai nevoie măcar de <B>%s</B> grâu!",
			feed = "Hrănește aceste animale cu <B>%d</B> grâu.",
			animal = "Acum că le-ai hrănit, apasă s/săgeată jos pentru a lua itemul.",
			animal_ingred = "Ai primit un aliment... <B>%s</B>!",
			oven_assemble = "Nu poți face o altă pizza în timp ce alta este în cuptor!",
			insufficient_cash = "Nu ai suficenți bani pentru asta!",
			welcome = "Mamma mia! Chef Remy te-a angajat ca să faci cea mai delicoasă pizza din lume! Fii pregătit și folosește toate resursele.\n\nApoi recoltează  incredientele necesare în cantitățile potrivite, du-te la sucitor și asamblează pizza.\n\nFii atent la timpul de coacere și cantitățile incredientelor pentru o pizza perfectă!"
		},
		ru = {
			event_concluded = "Вы успешно завершили ивент! (╯°□°）╯︵ ┻━┻\n\nСпасибо за магию <B>%s</B>, за спецэффекты <B>%s</B> и за доброжелательность <B>Неофициальной команде переводчиков</B>.\n<font size='9'>Отдельное спасибо %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Банан",
					[2] = "Базилик",
					[3] = "Болгарский перец",
					[4] = "Брокколи",
					[5] = "Какао",
					[6] = "Гриб",
					[7] = "Олива",
					[8] = "Лук",
					[9] = "Ореган",
					[10] = "Ананас",
					[11] = "Помидор",
					[12] = "Пшеница"
				},
				meat = {
					[1] = "Бекон",
					[2] = "Курица",
					[3] = "Пепперони"
				},
				others = {
					[1] = "Сыр",
					[2] = "Шоколад",
					[3] = "Оливковое масло",
					[4] = "Томатный соус"
				},
				dough = {
					salt = "Соль",
					sugar = "Сахар",
					water = "Вода"
				}
			},
			pizza = {
				[1] = "Куриная",
				[2] = "Шоколадная",
				[3] = "Маргарита",
				[4] = "Пепперони",
				[5] = "Ананасовая",
				[6] = "Вегетарианская",
				burned = "Ваша пицца сгорела! :( Будьте осторожнее!",
				deliver = "Нажмите пробел, чтобы доставить пиццу клиенту!"
			},
			ui = {
				order = "Заказ",
				market = "Рынок",
				smasher = "Блендер",
				freezer = "Холодильник",
				cabinet = "Шкаф",
				garbage = "Мусорка",
				close = "Закрыть"
			},
			order = {
				customer = "Клиент",
				total = "Всего",
				delivered = {
					[0] = "Оу! Клиент не был удовлетворён :s",
					[1] = "Увы! Ты не смог доставить хорошую пиццу. Клиент заплатил только $%d за неё.",
					[2] = "Пицца! Она понравилась клиенту, но в ней были некоторые недостатки, и он заплатил $%d :/",
					[3] = "Вау! Ты смог доставить идеальную пиццу! Клиент оценил твои старания и заплатил $%d!!!"
				}
			},
			harvest = {
				full = "Ваш %s заполнен! Попробуйте выбросить некоторые ингредиенты в мусорку, чтобы освободить место!",
				harvest = "Вы собрали %s!",
				cost = "Стоимость",
				h_time = "Урожай",
				seed = "Сеять"
			},
			smasher = {
				smash = "Использовать",
				not_quantity = "%s может быть получено с использованием <B>%d</B> %s.",
				get = "Вы получили <B>%s</B>. Найдите его в своём <B>%s</B>."
			},
			storage = {
				empty_f = "Ваш холодильник пуст! :(",
				empty_c = "Ваш шкаф заполнен! :(",
				empty_g = "У вас нет никаких ингредиентов, которые можно выбросить.",
				select = "Выбрать"
			},
			assembling = {
				select = "Вам необходимо выбрать ингредиент, чтобы собрать пиццу!",
				req = {
					[1] = "Найди <B>воду</B>, чтобы сделать тесто для пиццы!",
					[2] = "Также необходимо немного <B>оливкового масла</B>, чтобы сделать тесто для пиццы!",
					[3] = "Нужна щепотка <B>%s</B>, чтобы сделать тесто вкусным!",
					[4] = "Теперь добавим немного <B>пшеничной муки</B>... ммм, будет идеально!",
					[5] = "Оу, совсем забыли про <B>томатный соус</B>!"
				},
				no_ingredients = "У вас нет ингредиентов, чтобы приготовить пиццу. Вырастите необходимые ингредиенты в соответствии с заказом.",
				stop = "Нажмите <B>пробел</B>, как только вы закончите собирать пиццу!",
			},
			garbage = "Мусор",
			go_oven = "Отнесите пиццу к печи.",
			insufficient_wheat = "Чтобы тесто пиццы было качественным, необходимо хотя бы <B>%s</B> пшениц!",
			feed = "Покормите это животное <B>%d</B> пшеницей.",
			animal = "Покормив его, продолжайте приседать, пока вы не получите предмет.",
			animal_ingred = "Вы получили ингредиент... <B>%s</B>!",
			oven_assemble = "Вы не можете приготовить другую пиццу, пока вы используете печь!",
			insufficient_cash = "У вас недостаточно денег, чтобы сделать это!",
			welcome = "Мамма Миа! Шеф-повар Рэми нанял тебя на работу, чтобы приготовить самую вкусную пиццу! Будь готов и используй все ресурсы, которые найдёшь.\n\nКак только ты получишь все необходимые ингредиенты в нужном количестве, перейди к валику для раскатки теста и собери пиццу.\n\nСледи за временем приготовления и количеством ингредиентов, чтобы приготовить идеальную пиццу!"
		},
		tr = {
			event_concluded = "Etkinliği tamamladınız! (╯°□°）╯︵ ┻━┻\n\nSihir, <B>%s</B> tarafından, özel efektler <B>%s</B> tarafından, ve <B>resmi olmayan Çeviri Ekibi</B>'nin yardımlarıyla yapılmıştır.\n<font size='9'>Teşekkürler: %s, ...</font>",
			ingredient = {
				plant = {
					[1] = "Muz",
					[2] = "Fesleğen",
					[3] = "Biber",
					[4] = "Brokoli",
					[5] = "Kakao",
					[6] = "Mantar",
					[7] = "Zeytin",
					[8] = "Soğan",
					[9] = "Kekik otu",
					[10] = "Ananas",
					[11] = "Domates",
					[12] = "Buğday"
				},
				meat = {
					[1] = "Pastırma",
					[2] = "Tavuk",
					[3] = "Peperoni"
				},
				others = {
					[1] = "Peynir",
					[2] = "Çikolata",
					[3] = "Zeytinyağı",
					[4] = "Domates sosu"
				},
				dough = {
					salt = "Tuz",
					sugar = "Şeker",
					water = "Su"
				}
			},
			pizza = {
				[1] = "Tavuklu",
				[2] = "Çikolatalı",
				[3] = "Margeritalı",
				[4] = "Peperonili",
				[5] = "Ananaslı",
				[6] = "Vejetaryen",
				burned = "Pizzan yandı! :( Seni dikkatsiz!",
				deliver = "Pizzayı müşteriye teslim et!"
			},
			ui = {
				order = "Sipariş",
				market = "Market",
				smasher = "Sıkma Makinesi",
				freezer = "Derin Dondurucu",
				cabinet = "Dolap",
				garbage = "Çöp",
				close = "Kapat"
			},
			order = {
				customer = "Müşteri",
				total = "Toplam",
				delivered = {
					[0] = "Off! Müşteri memnun kalmadı :/",
					[1] = "Iığğh! Teslim ettiğin pizza düzgün değildi. Müşteri sadece $%d ödedi.",
					[2] = "Pizza! Müşteri bayıldı ama hala bazı kusurlar var ve bu yüzden sadece $%d ödedi :/",
					[3] = "Yeey! Pizzayı kusursuz bir şekilde teslim ettin! Müşteri, gayretini takdir etti ve $%d ödedi!!!"
				}
			},
			harvest = {
				full = "%s doldu! Bazı malzemeleri çöpe atmayı unutma!",
				harvest = "%s topladın!",
				cost = "Fiyat",
				h_time = "Hasat",
				seed = "Tohumla"
			},
			smasher = {
				smash = "Sık",
				not_quantity = "%s, <B>%d</B> %s sıkarak elde edilebilir.",
				get = "Bir adet <B>%s</B> elde ettin. Şurada bulabilirsin: <B>%s</B>."
			},
			storage = {
				empty_f = "Derin dondurucun boş! :(",
				empty_c = "Dolabın boş! :(",
				empty_g = "Atılacak malzemen yok.",
				select = "Seç"
			},
			assembling = {
				select = "Pizzayı yapmak için malzeme seçmen gerekiyor!",
				req = {
					[1] = "Pizza hamuru yapmak için <B>su</B> bul!",
					[2] = "Biraz <B>zeytinyağı</B> da pizzanın hamuru için gerekli!",
					[3] = "Hamurun lezzeti için de bir tutam <B>%s</B>!",
					[4] = "Şimdi biraz da <B>buğday</B>... mmm, harika olacak!",
					[5] = "Aa, <B>domates sosunu</B> unuttun!"
				},
				no_ingredients = "Pizza yapmak için malzemen yok. Gerekli malzemeleri verilen sıraya göre dik.",
				stop = "Pizzayı birleştirmeyi tamamladığında boşluk tuşuna bas!"
			},
			garbage = "Çöp",
			go_oven = "Pizzayı fırına koy.",
			insufficient_wheat = "Kaliteli bir pizza hamuru için en az <B>%s</B> buğday gerekli!",
			feed = "Bu hayvanı <B>%d</B> buğdayla besle.",
			animal = "Besledikten sonra bir eşya elde edene kadar eğil.",
			animal_ingred = "Bu malzemeyi kazandın: <B>%s</B>!",
			oven_assemble = "Fırını kullanıyorlarken başka bir pizza yapamazsın!",
			insufficient_cash = "Bunu yapmak için yeterli paran yok!",
			welcome = "Mamma mia! Şef Remy seni en lezzetli pizzaları yapman için işe aldı! Hazır ol ve tüm kaynakları yerinde kullan.\n\nTalep edinen malzemeleri miktarına göre ayrı ayrı biçtikten sonra oklavanın yanına git ve pizzayı birleştir.\n\nKusursuz bir pizza için pişirme süresini ve malzeme miktarlarını takip et!"
		}
	},{
		__call = function(list, flag)
			return list[flag or tfm.get.room.community] or list.en
		end
	})
	translation.pt = translation.br

	--[[ Data ]]--
	local json = { encode = true, decode = true }
	do
		local encode

		local escape_char_map = {
			['\\'] = "\\\\",
			['\"'] = "\\\"",
			['\b'] = "\\b",
			['\f'] = "\\f",
			['\n'] = "\\n",
			['\r'] = "\\r",
			['\t'] = "\\t"
		}
		local escape_char_map_inv = { ["\\/"] = "/", ['/'] = "\\/" }

		local escape_char = function(c)
			return escape_char_map[c] or string.format("\\u%04x", string.byte(c))
		end

		local encode_nil = function(val)
			return "null"
		end

		local encode_table = function(val, stack)
			local res = {}
			stack = stack or {}
			if stack[val] then error("[JSON] Circular reference") end
			stack[val] = true
			if val[1] ~= nil or next(val) == nil then
				local n = 0
				for k in next, val do
					if type(k) ~= "number" then
						error("[JSON] Invalid table: mixed or invalid key types")
					end
					n = n + 1
				end
				if n ~= #val then
					error("[JSON] Invalid table: sparse array")
				end
				for i, v in ipairs(val) do
					res[#res + 1] = encode(v, stack)
				end
				stack[val] = nil
				return "[" .. table.concat(res, ",") .. "]"
			else
				for k, v in next, val do
					if type(k) ~= "string" then
						error("[JSON] Invalid table: mixed or invalid key types")
					end
					res[#res + 1] = encode(k, stack) .. ":" .. encode(v, stack)
				end
				stack[val] = nil
				return "{" .. table.concat(res, ",") .. "}"
			end
		end

		local encode_string = function(val)
			return '"' .. string.gsub(val, '[%z\1-\31\\"]', escape_char) .. '"'
		end

		local encode_number = function(val)
			if val ~= val or val <= -math.huge or val >= math.huge then
				error("[JSON] Unexpected number value '" .. tostring(val) .. "'")
			end
			return string.format("%.14g", val)
		end

		local type_func_map = {
			["nil"] = encode_nil,
			["table"] = encode_table,
			["string"] = encode_string,
			["number"] = encode_number,
			["boolean"] = tostring,
		}

		encode = function(val, stack)
			local t = type(val)
			local f = type_func_map[t]
			if f then
				return f(val, stack)
			end
			error("[JSON] Unexpected type '" .. t .. "'")
		end

		local parse

		local space_chars = {
			[' '] = true,
			['\t'] = true,
			['\r'] = true,
			['\n'] = true
		}
		local delim_chars = {
			[' '] = true,
			['\t'] = true,
			['\r'] = true,
			['\n'] = true,
			[']'] = true,
			['}'] = true,
			[','] = true
		}
		local escape_chars = {
			['\\'] = true,
			['/'] = true,
			['"'] = true,
			['b'] = true,
			['f'] = true,
			['n'] = true,
			['r'] = true,
			['t'] = true,
			['u'] = true
		}
		local literals = {
			["true"] = true,
			["false"] = true,
			["null"] = true
		}
		local literal_map = {
			["true"] = true,
			["false"] = false,
			["null"] = nil
		}

		local next_char = function(str, idx, set, negate)
			for i = idx, #str do
				if set[string.sub(str, i, i)] ~= negate then
					return i
				end
			end
			return #str + 1
		end

		local decode_error = function(str, idx, msg)
			local line_count = 1
			local col_count = 1
			for i = 1, idx - 1 do
				col_count = col_count + 1
				if string.sub(str, i, i) == "\n" then
					line_count = line_count + 1
					col_count = 1
				end
			end
			error(string.format("[JSON] %s at line %d col %d", msg, line_count, col_count))
		end

		local codepoint_to_utf8 = function(n)
			if n <= 0x7f then
				return string.char(n)
			elseif n <= 0x7ff then
				return string.char(math.floor(n / 64) + 192, n % 64 + 128)
			elseif n <= 0xffff then
				return string.char(math.floor(n / 4096) + 224, math.floor(n % 4096 / 64) + 128, n % 64 + 128)
			elseif n <= 0x10ffff then
				return string.char(math.floor(n / 262144) + 240, math.floor(n % 262144 / 4096) + 128, math.floor(n % 4096 / 64) + 128, n % 64 + 128)
			end
			error(string.format("[JSON] Invalid unicode codepoint '%x'", n))
		end

		local parse_unicode_escape = function(s)
			local n1 = tonumber(string.sub(s, 3, 6), 16)
			local n2 = tonumber(string.sub(s, 9, 12), 16)
			if n2 then
				return codepoint_to_utf8((n1 - 0xd800) * 0x400 + (n2 - 0xdc00) + 0x10000)
			else
				return codepoint_to_utf8(n1)
			end
		end

		local parse_string = function(str, i)
			local has_unicode_escape = false
			local has_surrogate_escape = false
			local has_escape = false
			local last
			for j = i + 1, #str do
				local x = string.byte(str, j)
				if x < 32 then
					decode_error(str, j, "Control character in string")
				end
				if last == 92 then
					if x == 117 then
						local hex = string.sub(str, j + 1, j + 5)
						if not string.find(hex, "%x%x%x%x") then
							decode_error(str, j, "Invalid unicode escape in string")
						end
						if string.find(hex, "^[dD][89aAbB]") then
							has_surrogate_escape = true
						else
							has_unicode_escape = true
						end
					else
						local c = string.char(x)
						if not escape_chars[c] then
							decode_error(str, j, "Invalid escape char '" .. c .. "' in string")
						end
						has_escape = true
					end
					last = nil
				elseif x == 34 then
					local s = string.sub(str, i + 1, j - 1)
					if has_surrogate_escape then
						s = string.gsub(s, "\\u[dD][89aAbB]..\\u....", parse_unicode_escape)
					end
					if has_unicode_escape then
						s = string.gsub(s, "\\u....", parse_unicode_escape)
					end
					if has_escape then
						s = string.gsub(s, "\\.", escape_char_map_inv)
					end
					return s, j + 1
				else
					last = x
				end
			end
			decode_error(str, i, "Expected closing quote for string")
		end

		local parse_number = function(str, i)
			local x = next_char(str, i, delim_chars)
			local s = string.sub(str, i, x - 1)
			local n = tonumber(s)
			if not n then
				decode_error(str, i, "Invalid number '" .. s .. "'")
			end
			return n, x
		end

		local parse_literal = function(str, i)
			local x = next_char(str, i, delim_chars)
			local word = string.sub(str, i, x - 1)
			if not literals[word] then
				decode_error(str, i, "Invalid literal '" .. word .. "'")
			end
			return literal_map[word], x
		end

		local parse_array = function(str, i)
			local res = {}
			local n = 1
			i = i + 1
			while 1 do
				local x
				i = next_char(str, i, space_chars, true)
				if string.sub(str, i, i) == "]" then
					i = i + 1
					break
				end
				x, i = parse(str, i)
				res[n] = x
				n = n + 1
				i = next_char(str, i, space_chars, true)
				local chr = string.sub(str, i, i)
				i = i + 1
				if chr == "]" then break end
				if chr ~= "," then decode_error(str, i, "Expected ']' or ','") end
			end
			return res, i
		end

		local parse_object = function(str, i)
			local res = {}
			i = i + 1
			while 1 do
				local key, val
				i = next_char(str, i, space_chars, true)
				if string.sub(str, i, i) == "}" then
					i = i + 1
					break
				end
				if string.sub(str, i, i) ~= '"' then
					decode_error(str, i, "Expected string for key")
				end
				key, i = parse(str, i)
				i = next_char(str, i, space_chars, true)
				if string.sub(str, i, i) ~= ":" then
					decode_error(str, i, "Expected ':' after key")
				end
				i = next_char(str, i + 1, space_chars, true)
				val, i = parse(str, i)
				res[key] = val
				i = next_char(str, i, space_chars, true)
				local chr = string.sub(str, i, i)
				i = i + 1
				if chr == "}" then break end
				if chr ~= "," then decode_error(str, i, "Expected '}' or ','") end
			end
			return res, i
		end

		local char_func_map = {
			['"'] = parse_string,
			['0'] = parse_number,
			['1'] = parse_number,
			['2'] = parse_number,
			['3'] = parse_number,
			['4'] = parse_number,
			['5'] = parse_number,
			['6'] = parse_number,
			['7'] = parse_number,
			['8'] = parse_number,
			['9'] = parse_number,
			['-'] = parse_number,
			['t'] = parse_literal,
			['f'] = parse_literal,
			['n'] = parse_literal,
			['['] = parse_array,
			['{'] = parse_object,
		}

		local copy_ignoring
		copy_ignoring = function(tbl, pat)
			local out = {}
			for k, v in next, tbl do
				if not string.find(k, pat) then
					if type(v) == "table" then
						out[k] = copy_ignoring(v, pat)
					else
						out[k] = v
					end
				end
			end
			return out
		end

		parse = function(str, idx)
			local chr = string.sub(str, idx, idx)
			local f = char_func_map[chr]
			if f then
				return f(str, idx)
			end
			decode_error(str, idx, "Unexpected character '" .. chr .. "'")
		end

		json.encode = function(val)
			return (encode(copy_ignoring(val, "^__")))
		end

		json.decode = function(str)
			if type(str) ~= "string" then
				error("[JSON] Expected argument of type string, got " .. type(str))
			end
			if str == "" then str = "{}" end
			local res, idx = parse(str, next_char(str, 1, space_chars, true))
			idx = next_char(str, idx, space_chars, true)
			if idx <= #str then
				decode_error(str, idx, "Trailing garbage")
			end
			return res
		end
	end

	local playerFlashData = {}
	local playerData = setmetatable({}, {
		__newindex = function(this, playerName, tbl)
			rawset(this, playerName, setmetatable(tbl, {
				__call = function(playerTable)
					local data = rawget(playerTable, "__modules")
					data[module.name] = playerTable

					system.savePlayerData(playerName, json.encode(data))
				end
			}))
		end
	})

	local enumIds = {
		hud = 50,
		npc = 100,
		lamp = 200,
		menu = 300,
		button = 400,
		callback = 600,
	}

	local bot = {
		Chef = {
			sit = { "164da72ef00" }
		},
		-- 1, 2, 7, 8, 9
		{
			name = "Alexandre",
			collection = {
				sit = { "1648bf9c24c" },
				run = { "1648be82cfb", "1648be844b2", "1648be8bc4c", "1648be8d427", "1648be8ec79" }
			},
			discrY = 2
		},
		{
			name = "Priscila",
			collection = {
				sit = { "1648c5243f6" },
				run = { "1648bf1a7e4", "1648bf1bf98", "1648bf23a2d", "1648bf1f0b5" }
			},
			axisY = 6,
			discrX = 3,
			discrY = 2
		},
		{
			name = "Anne",
			collection = {
				sit = { "1648bf9aa82" },
				run = { "1648bf54927", "1648bf56101", "1648bf578b7", "1648bf590ae", "1648bf5a88c" }
			},
			discrX = 5,
			discrY = 1
		},
		{
			name = "Denner",
			collection = {
				sit = { "1648bf99253" },
				run = { "1648bf76b85", "1648bf78417", "1648bf7ffd9", "1648bf81888", "1648bf830d9" }
			},
			discrX = 3
		},
		{
			name = "Teddy",
			collection = {
				sit = { "1648bf9f3b8" },
				run = { "1648bef20f4", "1648bef3997", "1648bef5236", "1648bf058c5", "1648bf0709f" }
			},
			discrX = -2
		},
		{
			name = "Officer Acer",
			collection = {
				sit = { "16476ec87d4" },
				run = { "16476e8e035", "16476e99faf", "16476ea5dcb", "16476ea9cd5", "16476eadce3" }
			}
		},
		{
			name = "Lilih",
			collection = {
				sit = { "16476ec6f4b" },
				run = { "16476e8c0b5", "16476e8ffca", "16476ea3d9b", "16476ea7d7e", "16476eabd43" }
			}
		},
		{
			name = "Manfred",
			collection = {
				sit = { "164c7a9f65e" },
				run = { "164c7a4cf4c", "164c7a4e73f", "164c7a500a9", "164c7a560cc", "164c7a57893" }
			},
			discrX = -3,
			discrY = 1
		},
		{
			name = "Wu",
			collection = {
				sit = { "164c8600043" },
				run = { "164c85b34f5", "164c85b4cd1", "164c85b6580", "164c85bc5d0", "164c85bddac" }
			},
			discrX = -2
		}
	}

	local ingredient = {
		plant = {
			[1] = {
				-- banana
				icon = "1643d9aced6",
				price = 15,
				time = 60 * 2.5,
				plant = { "16432a43a0d", "16432a456e0", "16432a51b95", "16432a53860", "16432a557a1" },
				axis = { 9, 4 },
				inCabinet = true,
				quantity = 3
			},
			[2] = {
				-- basil
				icon = "1643d9aebdb",
				price = 6,
				time = 40,
				plant = { "1641f33f046", "1641f340d3c", "1641f342a1e" },
				axis = { 16, 19 },
				inCabinet = true,
				quantity = 2
			},
			[3] = {
				-- bellpeper
				icon = "1643d9b60c9",
				price = 7,
				time = 60 * 2.2,
				plant = { "16413a88814", "164149ce4a9", "164149d0199", "164149d1e80" },
				axis = { 18, 7 },
				quantity = 3
			},
			[4] = {
				-- broccoli
				icon = "16471174217",
				price = 8,
				time = 60 * 2.4,
				plant = { "164377ded45", "164377e0e30", "164377e2b45", "164377e483b" },
				axis = { 6, 7 },
				quantity = 2
			},
			[5] = {
				-- cocoa
				icon = "16505e5069d",
				price = 6,
				time = 60 * 1.5,
				plant = { "164377e6554", "164377e8275", "164377e9f79", "164377ebc7e", "164377ed97c" },
				axis = { 22, 10 },
				inCabinet = true,
				to = 2, -- others
				smashQuantity = 4,
				quantity = 3
			},
			[6] = {
				-- mushroom
				icon = "1643d9b26ea",
				price = 5,
				time = 60,
				plant = { "16432a59913", "16432a5b5ed", "16432a5d2b5", "16432a57491" },
				axis = { 17, 16 },
				quantity = 4
			},
			[7] = {
				-- olive
				icon = "1643d9b43d9",
				price = 5,
				time = 60 * 1.1,
				plant = { "16428a56a1c", "164377ef723", "164377f1448" },
				axis = { 21, 12 },
				to = 3, -- others
				smashQuantity = 6,
				quantity = 3
			},
			[8] = {
				-- onion
				icon = "16470304775",
				price = 5,
				time = 50,
				plant = { "164248cc192", "164377f312d" },
				axis = { 17, 4 },
				quantity = 3
			},
			[9] = {
				-- oregano
				icon = "1646c94b392",
				price = 5,
				time = 50,
				plant = { "164377e6554", "164377f4ea0", "164377f6b98" },
				axis = { 6, 10 },
				inCabinet = true,
				quantity = 3
			},
			[10] = {
				-- pineapple
				icon = "1643d9b7ecb",
				price = 8,
				time = 60 * 2.2,
				plant = { "164196bdbb1", "164196bf8cb", "164199bd1e9", "164199beed8", "164199c0bc5" },
				axis = { 13, 4 },
				inCabinet = true,
				quantity = 1
			},
			[11] = {
				-- tomato
				icon = "1643d9b9ba5",
				price = 7,
				time = 60,
				plant = { "16413a88814", "16413a8a512", "16413a8c493" },
				axis = { 14, 12 },
				to = 4, -- others
				smashQuantity = 6,
				quantity = 4
			},
			[12] = {
				-- wheat
				icon = "164578d762e",
				price = 3,
				time = 30,
				plant = { "164377e6554", "164248ec9a9", "164248f1cac" },
				axis = { 5, 16 },
				isSpecial = true,
				doughQuantity = 5,
				quantity = 1
			},
		},
		meat = {
			[1] = {
				-- bacon
				icon = "16456ee6607",
				price = 2,
				axis = { 20, 12 }
			},
			[2] = {
				-- chicken
				icon = "164577f05ee",
				price = 3,
				axis = { 1, 8 }
			},
			[3] = {
				-- pepperoni
				icon = "1648f274f9d",
				price = 2,
				axis = { 15, 15 }
			}
		},
		others = {
			[1] = {
				-- cheese
				icon = "164ad8f92ed",
				price = 2,
				axis = { 13, 15 }
			},
			[2] = { 
				-- chocolate
				icon = "164aeae6d11",
				price = 3,
				axis = { 10, 14 }
			},
			[3] = {
				-- olive oil
				icon = "16456ee9fe4",
				price = 0,
				axis = { 29, 2 },
				inCabinet = true,
				isSpecial = true
			},
			[4] = { 
				-- tomato sauce
				icon = "164c422cc5d",
				price = 0,
				axis = { 25, 15 },
				inCabinet = true,
				isSpecial = true
			}
		},
		extra = {
			__icon = {
				[2] = "164a94c70f0", [6] = "164a94c88d5", [7] = "164a94ca0c1", [8] = "164a94cd052", [9] = "164a94cb87e"
			},

			-- ID of the extra plants
			2, 6, 7, 8, 9
		}
	}

	local enum_ingredients = {
		[1] = "plant",
		[2] = "meat",
		[3] = "others"
	}

	local __concatenatedIngredients = { }
	do
		for cat = 1, 3 do
			local len = #__concatenatedIngredients
			for i = 1, #ingredient[enum_ingredients[cat]] do
				local ingred = ingredient[enum_ingredients[cat]][i]

				ingred.cat = cat
				ingred.id = i

				if not ingred.isSpecial then
					__concatenatedIngredients[len + i] = ingred
				end
			end
		end
	end

	local pizzas = {
		_state = {
			[1] = "1646c954e95",
			[-1] = "164aeabce33", -- Chocolate
			[2] = "1646c94e238",
			[-2] = "164c2b205b0", -- Chocolate
			[3] = "1646c9506c3",
			[-3] = "164c2b21fa9", -- Chocolate
			[4] = "1646c952c03",
			[-4] = "164c2b23979", -- Chocolate
			[5] = "164704c3a6b", -- Burned
			[-5] = "164c2b1ea9e", -- Chocolate

			_icon = {
				[1] = "164ae6d6b78",
				[-1] = "164aeabf914", -- Chocolate
				[2] = "164ae6f6673",
				[-2] = "164c2b42c06", -- Chocolate
				[3] = "164ae6f7e39",
				[-3] = "164c2b443db", -- Chocolate
				[4] = "164ae6f9610",
				[-4] = "164c2b45b95" -- Chocolate
			},

			_temperature = {
				[1] = "164c7d929ee",
				[2] = "164c7e2223d",
				[3] = "164c7e23a0d",
				[4] = "164c7e251e9"
			},

			_temperatureIcon = {
				[1] = "164c7febcdb",
				[2] = "164c8021924",
				[3] = "164c8023249",
				[4] = "164c8024b26"
			}
		},

		[1] = {
			-- Chicken
			ingredients = {
				meat = { { 2, 6 } },
				others = { { 1, 5 } }
			},
		},
		[2] = {
			-- Chocolate
			ingredients = {
				plant = { { 1, 5 } },
				others = { { 2, 5 } }
			},
		},
		[3] = {
			-- Margherita
			ingredients = {
				plant = { { 11, 7 }, { 2, 2 } },
				others = { { 1, 5 } }
			},
		},
		[4] = {
			-- Pepperoni
			ingredients = {
				meat = { { 3, 6 } },
				others = { { 1, 4 } }
			},
		},
		[5] = {
			-- Pineapple
			ingredients = {
				-- { id, ~quantity }
				plant = { { 10, 4 } },
				meat = { { 1, 3 } },
				others = { { 1, 4 } }
			},

		},
		[6] = {
			-- Vegetarian
			ingredients = {
				plant = { { 11, 4 }, { 4, 4 }, { 3, 4 } },
				others = { { 1, 4 } }
			},
		}
	}

	local objects = {
		freezer = { "164628d01f6", "164629c3ee5", "164a4713daa" },
		cabinet = { "16462a33e8b", "16462a7e5f8", "164a47154e7" },
		oven = { "1646b3c6db0", "1646fbac107" },
		garbage = { "164aa4e9510", "164aa4e7d64" },
		_handle = { "164adaa0439", "164aebac7bb" }, -- salty, sweet
		water = "1647135172c",
		salt = "164c2a4e6af",
		sugar = "164c2a1bf58"
	}

	local possibilityRates = {
		extraIngredients = { 1, 3, 2, 2, 1, 2, 3, 3, 1, 2 },
		pizzaTemperature = { 4, 2, 3, 3, 4, 3, 3, 2, 3, 4 },
	}

	local limits = {
		freezer = 6,
		cabinet = 4,
		pizzaCycle = 4 -- seconds
	}

	local smasherEnums = {
		[1] = 5,
		[2] = 7,
		[3] = 11
	}

	local animalEnums = {
		-- Tables for table.random
		[1] = { pos = 10, id = { 31 } },
		[2] = { pos = 15, id = { 23, 21, 21, 23 } },
		[3] = { pos = 20, id = { 22 } }
	}

	local missionAxis = {
		[1] = { 0, 50 },
		[2] = { 7, 100 },
		[3] = { 50, 55 },
		[4] = { 0, 0 },
		[5] = { 75, 0 }, 
	}

	local moneyPercentages = {
		[1] = .15,
		[2] = .6,
		[3] = 1
	}

	--[[ Utils ]]--
	do
		local chatMessage = tfm.exec.chatMessage
		tfm.exec.chatMessage = function(s, player)
			local hasBreakline = string.find(s, "^\n")

			local text = "<font color='#88CEBF'>[•] "

			if hasBreakline then
				text = (hasBreakline and "\n" or "") .. text .. string.sub(s, 2)
			else
				text = text .. s
			end

			chatMessage(text, player)
		end

		table.remove = function(list, pos)
			local len, out = #list

			if not pos or pos == len then
				out = list[len]
				list[len] = nil
			elseif pos < len then
				local out = list[pos]
				list[pos] = nil

				for i = pos, len do
					list[i] = list[i + 1]
				end
			end
			return out
		end
	end

	math.clamp = function(value, min, max)
		return value < min and min or value > max and max or value
	end

	math.pythag = function(x1, y1, x2, y2, range)
		return (x1-x2)^2 + (y1-y2)^2 <= (range^2)
	end

	system.giveTitle = function(playerName)
		if module.title and playerFlashData[playerName] then
			if playerFlashData[playerName].dataLoaded and not playerData[playerName].won then
				system.giveEventGift(playerName, module.title)

				playerData[playerName].won = true

				tfm.exec.chatMessage(string.format(translation().event_concluded, team.developer[1], team.artist[1], table.concat(team.others, ", ")), playerName)

				tfm.exec.setNameColor(playerName, 0xF3D460)
				return true
			end
		end
		return false
	end

	system.looping = function(f, tick)
		local s = 1000 / tick
		local t = { }

		local bar = 0
		local fooTimer = function()
			bar = bar + 1
			t[bar] = system.newTimer(f, 1000, true)
		end

		for timer = 0, 1000 - s, s do
			system.newTimer(fooTimer, 1000 + timer, false)
		end
		return t
	end

	string.split = function(s, p)
		local out = {}

		string.gsub(s, p, function(v)
			out[#out + 1] = v
		end)

		return out
	end

	table.copy = function(tbl)
		local out = { }
		for k, v in next, tbl do
			out[k] = v
		end
		return out
	end

	table.find = function(list, value, index, f)
		for k, v in next,list do
			local i = (type(v) == "table" and index) and v[index] or v
			if (not f and i or f(i, index)) == value then
				return true, k
			end
		end

		return false
	end

	table.turnTable = function(x)
		return (type(x)=="table" and x or {x})
	end

	table.merge = function(this, src)
		for k, v in next, src do
			if this[k] then
				if type(v) == "table" then
					this[k] = table.turnTable(this[k])
					table.merge(this[k], v)
				else
					this[k] = this[k] or v
				end
			else
				this[k] = v
			end
		end
	end

	table.random = function(tbl)
		return tbl[math.random(#tbl)]
	end

	--[[ System ]]--
	for k, v in next, translation do
		if k ~= "en" then
			table.merge(v, translation.en, true)
		end
	end

	local callback
	do
		local callback_id = enumIds.callback

		callback = setmetatable({ }, {
			__call = function(this, eventName, x, y, w, h, d_id)
				local id = d_id or callback_id
				local action

				local self = {
					callback = eventName,
					x = x,
					y = y,
					w = w,
					h = h
				}

				self.setAction = function(self, f)
					if type(f) == "function" then
						action = f
						this[eventName] = self
						return true
					end
					return false
				end

				self.performAction = function(self, ...)
					if action then
						action(self, ...)
						return self, true
					end
					return self, false
				end

				self.remove = function(self, playerName)
					ui.removeTextArea(id, playerName)

					if not playerName then
						this[eventName] = nil
					end
					return true
				end

				ui.addTextArea(id, "<textformat leftmargin='1' rightmargin='1'><a href='event:callback." .. eventName .. "'>" .. string.rep('\n', h / 10), nil, x - 5, y - 5, w + 5, h + 5, 1, 1, 0, false)

				if not d_id then
					callback_id = callback_id + 1
				end

				return self
			end
		})
	end

	local npc
	do
		local npcs = { }

		local npc_id = enumIds.npc

		npc = function(name, collection, layer)
			local action
			local evt
			local id = npc_id
			local img
			local state

			local self = {
				x = 0,
				y = 0,
				w = 1,
				h = 1
			}

			self.deleteCallback = function(self, playerName)
				if evt then
					evt:remove(playerName)
					return self
				end
				return self, "Callback doesn't exist."
			end

			self.destroy = function(self, super)
				if img then
					tfm.exec.removeImage(img)
					img = nil
				end

				if super then
					ui.removeTextArea(id + 1)
				end
			end

			self.getAction = function(self)
				if action then
					return action
				end
				return nil, "Action is not set."
			end

			self.getCollection = function(self)
				return collection
			end

			self.getId = function(self)
				return id
			end

			self.getLayer = function(self)
				return layer
			end

			self.getName = function(self)
				return name
			end

			self.getState = function(self)
				return state
			end

			self.removeAction = function(self)
				action = nil
				self.destroy(self)
				return self
			end

			self.removeName = function(self, playerName)
				ui.removeTextArea(id + 1, playerName)

				if not playerName then
					name = nil
				end

				return self
			end

			self.setAction = function(self, event, static)
				if type(state) == "table" then
					local oldState = state

					local curr = 0
					action = function(state)
						local i = curr % #state + 1
						if event then
							i = event(i) or i
						end

						curr = curr + 1

						self.destroy(self)
						img = tfm.exec.addImage(state[i] .. ".png", layer, self.x, self.y)

						if name then
							ui.addTextArea(id + 1, "<p align='center'><font color='#FFF426' face='Verdana'><B>" .. name, nil, self.x - (static and 20 or 0), self.y - 20, 100, 20, 1, 1, 0, false)
						end
					end

					return self
				end
				return self, "State not set or cannot have an action."
			end

			self.setCallback = function(self, eventName, event)
				if not evt then
					evt = callback(eventName, self.x, self.y, self.w, self.h, id)

					if event then
						evt:setAction(event)
					end

					return self
				end
				return self, "Callback already exists."
			end

			self.setDimension = function(self, w, h)
				self.w = w
				self.h = h

				return self
			end

			self.setPosition = function(self, x, y)
				self.x = x
				self.y = y

				return self
			end

			self.setState = function(self, newState)
				newState = collection[newState]

				if newState and state ~= newState then
					state = newState
					self.removeAction(self)

					return self
				end
				return self, "This state doesn't exist or is already running."
			end

			self.static = function(self)
				if state then
					if #state == 1 then
						self.setAction(self, function()
							self.removeAction(self)
						end, true)

						return self
					end
					return self, "State must be len:1"
				end
				return self, "State not set."
			end

			npcs[#npcs + 1] = self
			npc_id = npc_id + 2
			return self
		end

		system.looping(function()
			for k, v in next, npcs do
				local action = v:getAction()
				if action then
					action(v:getState())
				end
			end
		end, 10)
	end

	local lamp
	do
		local lamp_id = enumIds.lamp
		lamp = function(x, y, height, luminosity)
			height = height or 70
			luminosity = luminosity or 5

			local id = lamp_id

			tfm.exec.addPhysicObject(id, x, y, {
				type = 13,
				miceCollision = true,
				groundCollision = true,
				dynamic = true,
				friction = 1.5
			})

			tfm.exec.addJoint(id, 0, id, {
				type = 3,
				point1 = x .. "," .. (y - height),
				point2 = x .. "," .. (y - height + 1)
			})

			local size = luminosity * 20
			local alpha = .1
			local foregroundLim = math.ceil(luminosity * .4)
			local scale = .75 / luminosity
			for light = 1, luminosity do
				tfm.exec.addJoint(id + light, id, id, {
					type = 0,
					point1 = x .. "," .. y,
					point2 = x .. "," .. (y + 1),
					line = size,
					alpha = alpha,
					foreground = light > foregroundLim,
					color = 0xFFFF77
				})

				size = size - 20
				alpha = alpha + scale
			end

			tfm.exec.addJoint(id + luminosity + 1, id, id, {
				type = 0,
				point1 = x .. "," .. (y - height),
				point2 = x .. "," .. y,
				line = 2,
				color = 1
			})

			lamp_id = lamp_id + luminosity + 2
		end
	end

	ui.menu = function(text, playerName, x, y, w, h)
		local id = enumIds.menu - 8

		x = x + 6
		y = y + 6

		ui.addTextArea(0, "", playerName, -1500, -1500, 5000, 5000, 1, 1, .2, false)

		ui.addTextArea(id, "", playerName, x, y, w, h, 0x78462B, 0x78462B, 1, true)

		ui.addTextArea(id - 1, "", playerName, x, y, 20, 20, 0xBEB17D, 0xBEB17D, 1, true)
		ui.addTextArea(id - 2, "", playerName, x + w - 20, y, 20, 20, 0xBEB17D, 0xBEB17D, 1, true)
		ui.addTextArea(id - 3, "", playerName, x, y + h - 20, 20, 20, 0xBEB17D, 0xBEB17D, 1, true)
		ui.addTextArea(id - 4, "", playerName, x + w - 20, y + h - 20, 20, 20, 0xBEB17D, 0xBEB17D, 1, true)

		ui.addTextArea(id - 5, "", playerName, x, y + (h + 40) / 4, w, ((h - 40) / 2), 0x9D7043, 0x9D7043, 1, true)
		ui.addTextArea(id - 6, "", playerName, x + (w + 40) / 4, y, ((w - 40) / 2), h, 0x9D7043, 0x9D7043, 1, true)

		ui.addTextArea(id - 7, text, playerName, x + 3, y + 3, w - 6, h - 6, 0x1C3A3E, 0x0E1619, 1, true)
	end
	ui.removeMenu = function(playerName)
		id = enumIds.menu - 8
		for i = id - 7, id do
			ui.removeTextArea(i, playerName)
		end
		ui.removeTextArea(0, playerName)
	end

	ui.button = function(id, text, eventName, playerName, x, y, w, h)
		id = enumIds.button + id * 2

		ui.addTextArea(id, "", playerName, x + 1, y + 2, w, h, 1, 1, 1, true)
		ui.addTextArea(id - 1, "<p align='center'><font size='13'><a href='event:button." .. eventName .. "'>" .. text .. "\n", playerName, x, y, w, h, 0x142A2D, 0x142A2D, 1, true)
	end
	ui.removeButton = function(id, playerName)
		id = enumIds.button + id * 2
		for i = id - 1, id do
			ui.removeTextArea(i, playerName)
		end
	end

	local getStage = function(remaining, step, max)
		local stage = math.floor(remaining / step) + 1
		return math.clamp(stage, 1, max)
	end

	local updateFlashStorage = function(playerName)
		local out = { }
		for s = 1, 2 do
			local len = #out
			for i, d in next, playerData[playerName].storage[s] do
				local ing_id = len + i
				out[ing_id] = { }
				for f = 1, 2 do -- avoids pointer
					out[ing_id][f] = d[f]
				end
			end
		end

		playerFlashData[playerName].concatenatedStorage = out
	end

	local parseId = function(sId)
		local cat, id = string.match(sId, "(%d)(%d+)")
		return tonumber(cat), tonumber(id)
	end

	local storeIngredient = function(playerName, ing, quantity, ignoreSave)
		quantity = quantity or 1

		local bar = (ing.inCabinet and 2 or 1)

		local id = tonumber(ing.cat .. ing.id)

		local found, key = table.find(playerData[playerName].storage[bar], id, 1)
		if not found then
			if #playerData[playerName].storage[bar] >= (playerFlashData[playerName]._limits[(ing.inCabinet and "cabinet" or "freezer")]) then
				return false
			end

			playerData[playerName].storage[bar][#playerData[playerName].storage[bar] + 1] = { id, quantity }
		else
			playerData[playerName].storage[bar][key][2] = playerData[playerName].storage[bar][key][2] + quantity
		end

		updateFlashStorage(playerName)

		if not ignoreSave then
			playerData[playerName]()
		end
		return true
	end

	local removeIngredient = function(playerName, storage, index, quantity, ignoreSave)
		quantity = quantity or 1

		local status = 0 -- 1 = -1, 2 = delete, 0 = fail

		if playerData[playerName].storage[storage][index][2] > quantity then
			-- Works like a pointer, so - 1 is not necessary in playerFlashData
			playerData[playerName].storage[storage][index][2] = playerData[playerName].storage[storage][index][2] - quantity

			status = 1
		elseif playerData[playerName].storage[storage][index][2] == quantity then

			table.remove(playerData[playerName].storage[storage], index)

			local storageName = (storage == 1 and "freezer" or "cabinet")
			playerFlashData[playerName]._limits[storageName] = math.max(limits[storageName], #playerData[playerName].storage[storage])

			status = 2
		end

		if not ignoreSave and status > 0 then
			playerData[playerName]()
		end

		return status
	end

	local transformTime = function(s)
		return string.format("%02dm%02ds", s / 60, s % 60)
	end

	local unsafeToNavigate = function(playerName)
		return playerFlashData[playerName].pizzaIcon > -1 or playerFlashData[playerName].usingOven
	end

	local countPizzas = function(playerName)
		local total = 0
		for i = 1, #pizzas do
			if bit32.band(2 ^ i, playerData[playerName].completions) > 0 then
				total = total + 1
			end
		end
		return total
	end

	--[[ Variables ]]--
	local chef
	local customer
	local doorId
	local isBarnLocked = true
	local ovens = { false, false }
	-- Format
	local picker_format = "<textformat tabstops='[10]' leftmargin='1' rightmargin='1'><a href='event:select.%d'>\n\t<J><B>x%d\n\n\n\n\n"

	--[[ Interface ]]--
	local market = function(playerName, update)
		if not update then
			ui.menu("<p align='center'><font size='20'><a href='event:button.market.previous'><B><J>«</J></B></a>     " .. translation().ui.market .. "     <a href='event:button.market.next'><B><J>»</J></B></a>\n", playerName, 265, 112, 270, 175)
		end

		local o = ingredient.plant[playerFlashData[playerName].marketIndex]

		ui.addTextArea(1, "", playerName, 281, 166, 70, 70, 0x142A2D, 0x142A2D, 1, true)

		if playerFlashData[playerName].marketImgId > -1 then
			tfm.exec.removeImage(playerFlashData[playerName].marketImgId)
		end
		playerFlashData[playerName].marketImgId = tfm.exec.addImage(o.icon .. ".png", "&1", 281 + o.axis[1], 166 + o.axis[2], playerName)

		ui.addTextArea(2, "<textformat tabstops='[0,0,65,75,140]'><font size='16'>" .. translation().ingredient.plant[o.id] .. "</font>\n\n<N>" .. translation().harvest.cost .. "\t:\t<V>$" .. o.price .. "\t<N>x" .. o.quantity .. "\n<N>" .. translation().harvest.h_time .. "\t:\t<V>" .. transformTime(o.time), playerName, 366, 166, 165, 70, 0x142A2D, 0x142A2D, 1, true)

		if not update then
			ui.button(1, "<VP>" .. translation().harvest.seed, "seed", playerName, 295, 261, 100, 20)
			ui.button(2, translation().ui.close, "close.market", playerName, 415, 261, 100, 20)
		end
	end

	local order = function(playerName)
		ui.menu("<p align='center'><font size='20'><textformat leading='-8'>" .. translation().ui.order .. "</textformat></font>\n<font color='#142A2D'>___________________</font></p><p align='left'>\n\n" .. translation().order.customer .. " : <V>" .. customer:getName() .. "</V>\n" .. translation().order.total .. " : <V>$" .. playerFlashData[playerName].order.total .. "</V>\n<font size='15'>\n" .. translation().pizza[playerData[playerName].order.pizza], playerName, 295, 60, 210, 300)

		local data = { }
		for t, l in next, playerFlashData[playerName].order.ingredients do
			for _, d in next, l do
				data[#data + 1] = { i = ingredient[t][d[1]], q = d[2] }
			end
		end

		for i = 0, 3 do
			local info = data[i + 1]

			-- Some pizzas have <4 ingredients
			if info then
				local x, y = 310 + ((i % 2) * 85), 202 + ((i > 1 and 1 or 0) * 85)
				ui.addTextArea(i + 1, "x" .. info.q, playerName, x, y, 70, 70, 0x142A2D, 0x142A2D, 1, true)

				playerFlashData[playerName].order.images[i + 1] = tfm.exec.addImage(info.i.icon .. ".png", "&1", x + info.i.axis[1], y + info.i.axis[2], playerName)
			end
		end

		for i = 0, 3 do
			local y = 210 + (i * 40)

			if i == 0 then
				-- Temperature
				playerFlashData[playerName].order.images[#playerFlashData[playerName].order.images + 1] = tfm.exec.addImage(pizzas._state._temperatureIcon[playerData[playerName].order.level] .. ".png", "&1", 480, y, playerName)
			else
				-- Ignored ingredients
				if not playerData[playerName].order.ingredients then break end

				local id = playerData[playerName].order.ingredients[i]
				if not id then break end

				local e = ingredient.extra.__icon[id]
				if not e then break end

				playerFlashData[playerName].order.images[#playerFlashData[playerName].order.images + 1] = tfm.exec.addImage(e .. ".png", "&1", 480, y, playerName)
				playerFlashData[playerName].order.images[#playerFlashData[playerName].order.images + 1] = tfm.exec.addImage("164a961ac69.png", "&2", 480, y, playerName)
			end

			ui.addTextArea(i + 5, "", playerName, 480, y, 20, 20, 0x142A2D, 0x142A2D, 1, true)
		end

		ui.button(1, "<R><B>X", "close.order", playerName, 480, 74, 20, 20)
	end

	local smasher = function(playerName)
		ui.menu("<p align='center'><font size='20'>" .. translation().ui.smasher, playerName, 265, 115, 260, 175)

		for i = 1, #smasherEnums do
			local x = 282 + ((i - 1) * 84)

			local o = ingredient.plant[smasherEnums[i]]
			local to = ingredient.others[o.to]

			ui.addTextArea(i, "<p align='center'><a href='event:button.smash." .. smasherEnums[i] .. "'>" .. translation().smasher.smash, playerName, x, 160, 70, 90, 0x142A2D, 0x142A2D, 1, true)

			local id = i * 2
			playerFlashData[playerName].smasherImgIds[id - 1] = tfm.exec.addImage(o.icon .. ".png", "&1", x + 5, 180 + o.axis[2], playerName)
			playerFlashData[playerName].smasherImgIds[id] = tfm.exec.addImage(to.icon .. ".png", "&1", x + 70 - to.axis[1] + (i == 1 and -38 or i == 2 and 8 or -4), 180 + to.axis[2] + (i == 1 and 10 or 0), playerName)
		end

		ui.button(1, translation().ui.close, "close.smasher", playerName, 284, 265, 235, 20)
	end

	local storage = function(type, playerName, update)
		local obj = (type == 1 and "freezer" or "cabinet")

		if not update then
			ui.menu("<p align='center'><font size='20'><a href='event:button.storage.previous." .. type .. "'><B><J>«</J></B></a>     " .. translation().ui[obj] .. "     <a href='event:button.storage.next." .. type .. "'><B><J>»</J></B></a>\n<font size='10'>( " .. #playerData[playerName].storage[type] .. " / " .. limits[obj] .. " )", playerName, 265, 120, 270, 175)
		end

		local stored = playerData[playerName].storage[type][playerFlashData[playerName].storageIndex]

		local cat, id = parseId(stored[1])

		local o = ingredient[enum_ingredients[cat]][id]

		ui.addTextArea(1, "", playerName, 281, 184, 70, 70, 0x142A2D, 0x142A2D, 1, true)

		if playerFlashData[playerName].storageImgId > -1 then
			tfm.exec.removeImage(playerFlashData[playerName].storageImgId)
		end
		playerFlashData[playerName].storageImgId = tfm.exec.addImage(o.icon .. ".png", "&1", 281 + o.axis[1], 184 + o.axis[2], playerName)

		ui.addTextArea(2, "<textformat tabstops='[0,0,65,75,140]'><font size='16'>" .. translation().ingredient[enum_ingredients[cat]][o.id] .. "</font> x" .. stored[2] .. (o.isSpecial and ("\n<U><a href='event:button.use." .. o.id .. "'>" .. translation().storage.select) or ""), playerName, 366, 184, 165, 70, 0x142A2D, 0x142A2D, 1, true)

		if not update then
			ui.button(1, translation().ui.close, "close.storage." .. type, playerName, 281, 269, 250, 20)
		end
	end

	local garbage = function(playerName, update)
		if not update then
			ui.menu("<p align='center'><font size='20'><a href='event:button.garbage.previous'><B><J>«</J></B></a>     " .. translation().ui.garbage .. "     <a href='event:button.garbage.next'><B><J>»</J></B></a>\n", playerName, 265, 112, 270, 175)
		end

		local stored = playerFlashData[playerName].concatenatedStorage[playerFlashData[playerName].garbageIndex]

		local cat, id = parseId(stored[1])

		local o = ingredient[enum_ingredients[cat]][id]

		ui.addTextArea(1, "", playerName, 281, 166, 70, 70, 0x142A2D, 0x142A2D, 1, true)

		if playerFlashData[playerName].garbageImgId > -1 then
			tfm.exec.removeImage(playerFlashData[playerName].garbageImgId)
		end
		playerFlashData[playerName].garbageImgId = tfm.exec.addImage(o.icon .. ".png", "&1", 281 + o.axis[1], 166 + o.axis[2], playerName)

		ui.addTextArea(2, "<textformat tabstops='[0,0,65,75,140]'><font size='16'>" .. translation().ingredient[enum_ingredients[cat]][o.id] .. "</font> x" .. stored[2] .. "\n\n<B>" .. translation().ui[(o.inCabinet and "cabinet" or "freezer")], playerName, 366, 166, 165, 70, 0x142A2D, 0x142A2D, 1, true)

		if not update then
			ui.button(1, "<VP>" .. translation().garbage, "trash", playerName, 295, 261, 100, 20)
			ui.button(2, translation().ui.close, "close.garbage", playerName, 415, 261, 100, 20)
		end
	end

	local displayCash = function(playerName)
		ui.addTextArea(enumIds.hud, "<font size='20' color='#FFFF00'>$" .. string.format("%5d", playerData[playerName].cash), playerName, 710, 370, 90, 30, 1, 1, 0, true)
	end

	local displayWheat = function(playerName)
		ui.addTextArea(enumIds.hud + 1, "<font size='20'>" .. string.format("%3d", (playerData[playerName].storage[3] - playerFlashData[playerName].selectedWheats - (playerFlashData[playerName].usedWheats and ingredient.plant[12].doughQuantity or 0))), playerName, 310, 410, nil, nil, 1, 1, 0, false)
	end

	local displayWheatCounter = function(playerName, x, y)
		ui.addTextArea(enumIds.hud + 2, "<CE><B>x" .. playerFlashData[playerName].selectedWheats, playerName, 5, 20, nil, nil, 1, 1, 0, true)
	end

	local concludeMission = function(id, playerName, image)
		playerFlashData[playerName].doughMission[id] = tfm.exec.addImage(image, "&1", 5 + missionAxis[id][1], 25 + missionAxis[id][2], playerName)
	end

	--[[ Functions ]]--
	local allowCallback = function(callback, playerName, complement)
		local x, y = tfm.get.room.playerList[playerName].x, tfm.get.room.playerList[playerName].y

		complement = not complement and 0 or (complement / 2)

		return ((x >= (callback.x - complement) and x <= (callback.x + callback.w + complement)) and (y >= (callback.y - complement) and y <= (callback.y + callback.h + complement)))
	end

	local unlockDoor = function(this, playerName)
		if isBarnLocked then
			local x, y = tfm.get.room.playerList[playerName].x, tfm.get.room.playerList[playerName].y
			if x >= 1807 and x <= 1960 then
				if y >= 500 and y <= 595 then
					isBarnLocked = false

					this:remove()

					tfm.exec.removePhysicObject(1)

					tfm.exec.removeImage(doorId)

					tfm.exec.giveConsumables(playerName, 5, 5)
				end
			end
		end
	end

	local harvest = function(this, playerName, id)
		if tfm.get.room.playerList[playerName].x < 1000 or tfm.get.room.playerList[playerName].x > 1650 or unsafeToNavigate(playerName) then return end

		if playerData[playerName].spots[id] ~= nil then
			-- false = doesn't exist, { } = exists
			if not playerData[playerName].spots[id] then
				playerFlashData[playerName].currentSpot = id
				market(playerName)
			else
				if playerData[playerName].spots[id][2] == -1 then
					local o = ingredient.plant[playerData[playerName].spots[id][1]]

					local storageName = (o.inCabinet and "cabinet" or "freezer")

					if o.isSpecial then -- wheat only
						playerData[playerName].storage[3] = playerData[playerName].storage[3] + o.quantity
						displayWheat(playerName)
					else
						if not storeIngredient(playerName, o, o.quantity, true) then
							tfm.exec.chatMessage(string.format(translation().harvest.full, string.lower(translation().ui[storageName])), playerName)
							return
						end
					end
					playerData[playerName].spots[id] = false
					playerData[playerName]()

					tfm.exec.removeImage(playerFlashData[playerName].spots[id][1])

					playerFlashData[playerName].spots[id] = { tfm.exec.addImage("164431b8c1e.png", "?2", 906 + (id * 100), 600, playerName), 0 }

					tfm.exec.chatMessage(string.format(translation().harvest.harvest, translation().ingredient.plant[o.id]) .. (o.isSpecial and "" or "\n~> " .. translation().ui[storageName] .. " ( " .. #playerData[playerName].storage[(o.inCabinet and 2 or 1)] .. " / " .. limits[storageName] .. " )"), playerName)

					if math.random(30) < 6 then
						tfm.exec.giveConsumables(playerName, 2330, 1)
					end
				end
			end
		end
	end

	local closeStorageImg = function(id, playerName) -- not the ingredient
		local s = id == 1 and "freezer" or id == 2 and "cabinet" or "garbage"

		if playerFlashData[playerName][s .. "State"] > -1 then
			tfm.exec.removeImage(playerFlashData[playerName][s .. "State"], playerName)
			playerFlashData[playerName][s .. "State"] = -1
			return true
		end
		return false
	end

	local a_freezer = function(this, playerName)
		if not allowCallback(this, playerName, 60) or unsafeToNavigate(playerName) then return end

		if closeStorageImg(1, playerName) then return end

		local id = #playerData[playerName].storage[1] > 0 and 3 or 2
		playerFlashData[playerName].freezerState = tfm.exec.addImage(objects.freezer[id] .. ".png", "?30", 400, 190, playerName)

		if #playerData[playerName].storage[1] > 0 then
			storage(1, playerName)
		else
			tfm.exec.chatMessage(translation().storage.empty_f, playerName)
		end
	end

	local a_cabinet = function(this, playerName)
		if not allowCallback(this, playerName, 150) or unsafeToNavigate(playerName) then return end

		if closeStorageImg(2, playerName) then return end

		local id = #playerData[playerName].storage[2] > 0 and 3 or 2
		playerFlashData[playerName].cabinetState = tfm.exec.addImage(objects.cabinet[id] .. ".png", "?30", 530, 180, playerName)

		if #playerData[playerName].storage[2] > 0 then
			storage(2, playerName)
		else
			tfm.exec.chatMessage(translation().storage.empty_c, playerName)
		end
	end

	local a_garbage = function(this, playerName)
		if not allowCallback(this, playerName, 50) or unsafeToNavigate(playerName) then return end

		if closeStorageImg(3, playerName) then return end

		if #playerFlashData[playerName].concatenatedStorage > 0 then
			playerFlashData[playerName].garbageState = tfm.exec.addImage(objects.garbage[2] .. ".png", "?30", 630, 291, playerName)
			garbage(playerName)
		else
			tfm.exec.chatMessage(translation().storage.empty_g, playerName)
		end
	end

	local destroyOven = function(id, playerName)
		tfm.exec.removeImage(ovens[id].img)
		tfm.exec.removeImage(ovens[id].icon)

		if playerName then
			playerFlashData[playerName].usingOven = false

			local level = ovens[id].level -- necessary to be used in the newTimer

			local pizzaId = level * (playerData[playerName].order.pizza == 2 and -1 or 1)

			local pizzaResult = tfm.exec.addImage(pizzas._state[pizzaId] .. ".png", "!30", 250, 150, playerName)

			if level == #pizzas._state then
				tfm.exec.chatMessage("\n" .. translation().pizza.burned.. "\n", playerName)
			else
				for _, ingred in next, playerFlashData[playerName].assembling.ingredients do
					ingred.id = tfm.exec.addImage(ingred.icon .. ".png", "!40", ingred.x - 245, ingred.y - 135, playerName)
				end
			end

			system.newTimer(function()
				tfm.exec.removeImage(pizzaResult)

				for _, ingred in next, playerFlashData[playerName].assembling.ingredients do
					tfm.exec.removeImage(ingred.id)
				end

				if level < #pizzas._state then
					playerFlashData[playerName].pizzaIcon = tfm.exec.addImage(pizzas._state._icon[pizzaId] .. ".png", "$" .. playerName, -5, -10)
					tfm.exec.chatMessage("\n" .. translation().pizza.deliver.. "\n", playerName)
					playerFlashData[playerName].pizzaLevel = level
				end
			end, 4000, false)
		end

		ovens[id] = false
	end

	local f_oven = function(this, playerName, id)
		if not allowCallback(this, playerName, 20) then return end

		id = id - 6

		if ovens[id] and ovens[id].playerName == playerName then
			destroyOven(id, playerName)
		else
			if playerFlashData[playerName].pizzaIcon > 0 then
				if not ovens[id] then
					tfm.exec.removeImage(playerFlashData[playerName].pizzaIcon)
					playerFlashData[playerName].pizzaIcon = -1

					local x = 5 + (id == 2 and 175 or 0)

					ovens[id] = {
						playerName = playerName,
						time = 0,
						level = 1,
						img = tfm.exec.addImage(objects.oven[2] .. ".png", "?30", x, 220),
						icon = tfm.exec.addImage(pizzas._state._temperature[1] .. ".png", "?31", x + 60, 200, playerName),
						x = x
					}
					playerFlashData[playerName].usingOven = true
				end
			end
		end
	end

	local openSmasher = function(this, playerName)
		if not allowCallback(this, playerName, 20) or unsafeToNavigate(playerName) then return end

		smasher(playerName)
	end

	local updateSpot = function(playerName, id)
		if not playerData[playerName] or not playerFlashData[playerName] then return end

		if playerData[playerName].spots[id] then
			local o = ingredient.plant[playerData[playerName].spots[id][1]]

			local stage = playerData[playerName].spots[id][2] == -1 and #o.plant or getStage(playerData[playerName].spots[id][2], (o.time / (#o.plant - 1)), #o.plant)

			if stage > playerFlashData[playerName].spots[id][2] then
				playerFlashData[playerName].spots[id][2] = stage

				if stage == #o.plant then
					playerData[playerName].spots[id][2] = -1
				end

				if playerFlashData[playerName].spots[id][1] then
					tfm.exec.removeImage(playerFlashData[playerName].spots[id][1])
				end

				playerFlashData[playerName].spots[id][1] = tfm.exec.addImage(o.plant[stage] .. ".png", "?2", 906 + (id * 100), 600, playerName)
			end
		end
	end

	local openOrder = function(this, playerName)
		order(playerName)
	end

	local assemblePizza = function(playerName)
		ui.addTextArea(0, "", playerName, -1500, -1500, 5000, 5000, 1, 1, 0, false)

		local id, row, cel = 1, 0, 0
		while row <= 3 do
			while cel <= 3 do
				local stored = playerFlashData[playerName].concatenatedStorage[id]
				if not stored then break end

				local cat, _id = parseId(stored[1])
				local o = ingredient[enum_ingredients[cat]][_id]


				if not o.isSpecial then
					local x, y = 10 + (cel * 88), 270 + (row * 88)
					ui.addTextArea(id, string.format(picker_format, id, stored[2]), playerName, x, y, 90, 90, 1, 1, 0, false)

					local i = id * 2
					playerFlashData[playerName].assembleImgs[i - 1] = tfm.exec.addImage("164a8feae5e.png", "!30", x, y, playerName)
					playerFlashData[playerName].assembleImgs[i] = tfm.exec.addImage(o.icon .. ".png", "!31", x + o.axis[1] + 10, y + o.axis[2] + 10, playerName)
				else -- To avoid gaps in the ingredients
					cel = cel - 1
				end

				id = id + 1

				cel = cel + 1
			end
			cel = 0

			row = row + 1
		end

		playerFlashData[playerName].assembling.open = true
		playerFlashData[playerName].assembling.ingredients = {}
		playerFlashData[playerName].assembleImgs[#playerFlashData[playerName].assembleImgs + 1] = tfm.exec.addImage(objects._handle[(playerData[playerName].order.pizza == 2 and 2 or 1)] .. ".png", "!30", 480, 270, playerName)
	end

	local openAssembleUI = function(this, playerName)
		if not allowCallback(this, playerName, 50) or playerFlashData[playerName].order.delivered or playerFlashData[playerName].usingOven then return end

		local sweet = playerData[playerName].order.pizza == 2

		if #playerFlashData[playerName].concatenatedStorage == 0 then
			tfm.exec.chatMessage(translation().assembling.no_ingredients, playerName)
			return
		end

		if not playerFlashData[playerName].concludedMissions then
			for mission = 1, (sweet and 4 or 5) do
				if not playerFlashData[playerName].doughMission[mission] then
					tfm.exec.chatMessage(string.format(translation().assembling.req[mission], translation().ingredient.dough[(sweet and "sugar" or "salt")]), playerName)
					return
				end
			end

			if playerFlashData[playerName].selectedWheats < ingredient.plant[12].doughQuantity then
				tfm.exec.chatMessage(string.format(translation().insufficient_wheat, ingredient.plant[12].doughQuantity), playerName)
				return
			end

			playerFlashData[playerName].concludedMissions = true
		end

		playerFlashData[playerName].assembling.open = true
		system.bindMouse(playerName, true)

		if playerFlashData[playerName].pizzaIcon > 0 then
			tfm.exec.removeImage(playerFlashData[playerName].pizzaIcon)
			playerFlashData[playerName].pizzaIcon = -1
		end

		for mission = 1, #playerFlashData[playerName].doughMission do
			if playerFlashData[playerName].doughMission[mission] > 0 then
				tfm.exec.removeImage(playerFlashData[playerName].doughMission[mission])
				playerFlashData[playerName].doughMission[mission] = -1 -- keeps completed
			end
		end
		ui.removeTextArea(enumIds.hud + 2, playerName)

		playerFlashData[playerName].selectedWheats = playerFlashData[playerName].selectedWheats - ingredient.plant[12].doughQuantity
		playerFlashData[playerName].usedWheats = true

		playerFlashData[playerName].assembling.ingredients = { }

		-- Updating the storage again avoids the abuse of ingredients
		updateFlashStorage(playerName)

		tfm.exec.chatMessage("\n" .. translation().assembling.stop .. "\n", playerName)

		assemblePizza(playerName)
	end

	local closeAssembleUI = function(playerName)
		playerFlashData[playerName].assembling.open = false
		system.bindMouse(playerName, false)

		for i = 0, 15 do
			ui.removeTextArea(i, playerName)
		end

		for i = 1, #playerFlashData[playerName].assembleImgs do
			tfm.exec.removeImage(playerFlashData[playerName].assembleImgs[i])
		end
		playerFlashData[playerName].assembleImgs = {}

		if #playerFlashData[playerName].assembling.ingredients > 0 then
			for i = 1, #playerFlashData[playerName].assembling.ingredients do
				tfm.exec.removeImage(playerFlashData[playerName].assembling.ingredients[i].id)
			end

			playerFlashData[playerName].pizzaIcon = tfm.exec.addImage(pizzas._state._icon[(playerData[playerName].order.pizza == 2 and -1 or 1)] .. ".png", "$" .. playerName, -5, -10)
			tfm.exec.chatMessage("\n" .. translation().go_oven.. "\n", playerName)
		else
			playerFlashData[playerName].usedWheats = false
			playerFlashData[playerName].selectedWheats = playerFlashData[playerName].selectedWheats + ingredient.plant[12].doughQuantity
		end

		if playerFlashData[playerName].selectedWheats > 0 then
			concludeMission(4, playerName, ingredient.plant[12].icon .. ".png")
			displayWheatCounter(playerName)
		end
		displayWheat(playerName)
	end

	local getWater = function(this, playerName) -- Mission 1
		if not playerFlashData[playerName].doughMission[1] then
			if not allowCallback(this, playerName, 80) then return end

			concludeMission(1, playerName, objects.water .. ".png")
		end
	end

	local getSalt_Sugar = function(this, playerName, id) -- Mission 3
		if not allowCallback(this, playerName, 80) then return end

		id = id - 14

		local sweet = playerData[playerName].order.pizza == 2
		if id == 1 and sweet then return end
		if id == 2 and not sweet then return end

		if not playerFlashData[playerName].doughMission[3] then
			concludeMission(3, playerName, objects[(sweet and "sugar" or "salt")] .. ".png")
		end
	end

	local getWheat = function(this, playerName) -- Mission 4
		if not allowCallback(this, playerName, 80) then return end

		if playerFlashData[playerName].selectedWheats < (playerData[playerName].storage[3] - (playerFlashData[playerName].usedWheats and ingredient.plant[12].doughQuantity or 0)) then
			playerFlashData[playerName].selectedWheats = playerFlashData[playerName].selectedWheats + 1
			displayWheat(playerName)

			if not playerFlashData[playerName].doughMission[4] then
				concludeMission(4, playerName, ingredient.plant[12].icon .. ".png")
			end
			displayWheatCounter(playerName)
		end
	end

	local animal = function(this, playerName, id)
		if not allowCallback(this, playerName, 40) or unsafeToNavigate(playerName) then return false end

		id = id - 17

		if playerFlashData[playerName].animals[id] then return end

		if #playerData[playerName].storage[1] >= playerFlashData[playerName]._limits.freezer then
			-- The for allows to add ingredients that are already in the storage
			for i = 1, math.ceil(#animalEnums[id].id / 2) do -- 0.5 = 1, 4 = 2
				if not table.find(playerData[playerName].storage[1], animalEnums[id].id[i], 1) then
					tfm.exec.chatMessage(string.format(translation().harvest.full, string.lower(translation().ui.freezer)), playerName)
					return
				end
			end
		end

		local quantity = id + 3 -- 4, 5, 6

		if playerFlashData[playerName].doughMission[4] and playerFlashData[playerName].doughMission[4] > 0 and playerFlashData[playerName].selectedWheats >= quantity then
			playerFlashData[playerName].selectedWheats = playerFlashData[playerName].selectedWheats - quantity

			if playerFlashData[playerName].selectedWheats <= 0 then
				tfm.exec.removeImage(playerFlashData[playerName].doughMission[4])
				playerFlashData[playerName].doughMission[4] = nil
				ui.removeTextArea(enumIds.hud + 2, playerName)
			else
				displayWheatCounter(playerName)
			end

			playerData[playerName].storage[3] = playerData[playerName].storage[3] - quantity
			playerData[playerName]()

			tfm.exec.chatMessage(translation().animal, playerName)
			playerFlashData[playerName].animals[id] = { this.x, this.y, this.x + this.w, this.y + this.h, 0 }
		else
			tfm.exec.chatMessage(string.format(translation().feed, quantity), playerName)
		end
	end

	local closeGuide = function(playerName)
		tfm.exec.removeImage(playerFlashData[playerName].help)
		ui.removeTextArea(0, playerName)
		system.bindMouse(playerName, false)
		playerFlashData[playerName].help = false
	end

	local guide = function(this, playerName)
		if not allowCallback(this, playerName, 50) then return end

		ui.addTextArea(0, "", playerName, -1500, -1500, 5000, 5000, 1, 1, .6, false)
		playerFlashData[playerName].help = tfm.exec.addImage("164dd423693.png", "&3", 166, 50, playerName)
		system.bindMouse(playerName, true)

		system.newTimer(function()
			if playerFlashData[playerName].help then
				closeGuide(playerName)
			end
		end, 6000, false)
	end

	local loadBackground = function(playerName)
		-- Background
		tfm.exec.addImage("164ecfa9b47.png", "?0", 0, 0, playerName)
		-- Customer Table
		tfm.exec.addImage("164861b7b60.png", "?1", 100, 730, playerName)
		-- Objects
			-- Ovens
		tfm.exec.addImage(objects.oven[1] .. ".png", "?1", 5, 220, playerName)
		tfm.exec.addImage(objects.oven[1] .. ".png", "?1", 180, 220, playerName)
			-- Freezer
		tfm.exec.addImage(objects.freezer[1] .. ".png", "?1", 400, 190, playerName)
			-- Cabinet
		tfm.exec.addImage(objects.cabinet[1] .. ".png", "?1", 540, 180, playerName)
			-- Sink
		tfm.exec.addImage("164713b7d26.png", "?1", 528, 264, playerName)
			-- Water
		tfm.exec.addImage(objects.water .. ".png", "?2", 590, 300, playerName)
			-- Garbage
		tfm.exec.addImage(objects.garbage[1] .. ".png", "?1", 630, 291, playerName)
		-- Table
		tfm.exec.addImage("1643cdc05e7.png", "?1", 35, 505, playerName)
		-- Table objects
			-- Roller
		tfm.exec.addImage("1646fbaa04b.png", "?2", 230, 495, playerName)
			-- Wheat
		tfm.exec.addImage("164578dca71.png", "?2", 310, 445, playerName)
			-- Salt
		tfm.exec.addImage("16470265a28.png", "?2", 370, 445, playerName)
			-- Sugar
		tfm.exec.addImage("164702679b5.png", "?2", 430, 445, playerName)
			-- Smasher
		tfm.exec.addImage("164577f3fe0.png", "?2", 100, 413, playerName)
		-- Animals
			-- Cow
		tfm.exec.addImage("164479eff81.png", "?2", 1695, 675, playerName)
			-- Pig
		tfm.exec.addImage("164479eda5d.png", "?2", 1880, 710, playerName)
			-- Chicken
		tfm.exec.addImage("16447ad45b7.png", "?2", 1650, 530, playerName)
	end

	--[[ Main ]]--
	local __started = false
	local triggeredOnce = false
	eventNewGame = function()
		if not __started then return end
		if triggeredOnce or (tfm.get.room.uniquePlayers < 4 and not string.find(tfm.get.room.name, "^.?.?%-?%*?#")) then system.exit() end
		triggeredOnce = true

		tfm.exec.setGameTime(module.time)

		ui.setMapName(table.random({ team.developer[1], team.artist[1], team.developer[1], "#Pizzeria", "Lua" }))

		loadBackground()

		-- Loading NPC before so it doesn't delay
		local customerNpc = table.random(bot)
		for i = #customerNpc.collection.run, 1, -1 do
			tfm.exec.addImage(customerNpc.collection.run[i] .. ".png" , "_0", -3000, -3000)
		end
		tfm.exec.addImage(customerNpc.collection.sit[1] .. ".png" , "_0", -3000, -3000)

		for playerName, playerInfo in next, tfm.get.room.playerList do
			playerFlashData[playerName] = {
				dataLoaded = false,
				spots = { false, false, false, false, false, false },
				marketIndex = 1,
				marketImgId = -1,
				freezerState = -1, -- > -1 or open (img)
				cabinetState = -1, -- > -1 or open (img)
				pizzeriaForeground = -1,
				--barnForeground = tfm.exec.addImage("1648034eff0.png", "!100", 1632, 319, playerName),
				order = {
					images = { },
					delivered = false,
					customerPizza = -1
				},
				concatenatedStorage = { },
				currentSpot = -1,
				smasherImgIds = { -1, -1 },
				storageIndex = 1,
				storageImgId = -1,
				garbageState = -1, --> -1 or open (img)
				garbageIndex = 1,
				garbageImgId = -1,
				assembleImgs = { },
				assembling = {
					selected = -1,
					ingredients = { },
					open = false
				},
				pizzaIcon = -1,
				callbackTimer = 0,
				doughMission = { },
				animals = { },
				animalTimer = 0,
				usingOven = false,
				selectedWheats = 0,
				usedWheats = false,
				kissed = false,
				help = false,
				pizzaLevel = 1,
				_limits = {
					freezer = 0,
					cabinet = 0
				}
			}

			eventNewPlayer(playerName, true)

			system.loadPlayerData(playerName)
		end

		-- Lights
		lamp(1807, 460, 140)
		lamp(200, 620)

		-- Door
		doorId = tfm.exec.addImage("1640e7d5b26.png", "!0", 1865, 589)
		tfm.exec.addPhysicObject(1, 1905, 596, {
			type = 0,
			miceCollision = true,
			width = 80,
			height = 12,
			friction = 1
		})

		-- Callbacks
			-- Door
		callback("door", 1865, 589, 80, 12):setAction(unlockDoor)
			-- Earth (Keep this order)
		local e
		for i = 1, 6 do
			e = callback("plantation", 916 + (i * 100), 600, 90, 200)
		end
		e:setAction(harvest)
			-- Ovens
		callback("oven1", 5, 220, 171, 120):setAction(f_oven)
		callback("oven2", 180, 220, 171, 120):setAction(f_oven)
			-- Freezer
		callback("freezer", 400, 192, 94, 150):setAction(a_freezer)
			-- Cabinet
		callback("cabinet", 540, 180, 75, 75):setAction(a_cabinet)
			-- Sink & Water
		callback("water", 528, 264, 100, 75):setAction(getWater)
			-- Garbage
		callback("garbage", 630, 291, 33, 48):setAction(a_garbage)
			-- Objects
		local roller = callback("roller", 230, 495, 50, 11)
		callback("wheat", 310, 445, 49, 60):setAction(getWheat)
		callback("salt", 370, 445, 49, 60):setAction(getSalt_Sugar)
		callback("sugar", 430, 445, 49, 60):setAction(getSalt_Sugar)
		callback("smasher", 100, 413, 100, 91):setAction(openSmasher)
			-- Animals
		callback("cow", 1695, 675, 57, 90):setAction(animal)
		callback("pig", 1880, 710, 34, 55):setAction(animal)
		callback("chicken", 1650, 530, 155, 60):setAction(animal)

		-- Infinite sacks
		for i = 0, 1 do
			ui.addTextArea(enumIds.hud + 3 + i, "<font size='20'><p align='center'><B>∞", nil, 370 + (60 * i), 410, 50, nil, 1, 1, 0, false)
		end

		-- NPC
		local chair = 45 + math.random(0, 1) * 155
		customer = npc(customerNpc.name, customerNpc.collection, "!1"):setPosition(-30, 730 - (customerNpc.axisY or 0)):setState("run")
		customer:setAction(function()
			customer.x = customer.x + 10

			if customer.x > (chair - 10) then
				customer.y = customer.y - 9 - (customerNpc.discrY or 0)
				if customer.x > (chair + 10) then
					customer.x = customer.x + 30 - (customerNpc.discrX or 0)
					customer:setState("sit"):static()

					system.newTimer(function()
						-- Shortcut for a callback in the order balloon
						customer:setPosition(customer.x + 10, customer.y - 65):setDimension(40, 38):setCallback("order", openOrder)

						-- Noob, ik
						for player, data in next, tfm.get.room.playerList do
							if not data.isDead then
								playerFlashData[player].order.customerPizza = tfm.exec.addImage("1648fa606cb.png", "!2", customer.x, customer.y, player)
							end
						end

						roller:setAction(openAssembleUI)
					end, 1000, false)
				end
			end
		end)

		chef = npc("Remy", bot.Chef, "!1"):setPosition(910, 270):setState("sit"):static():setDimension(65, 75):setCallback("help", guide)
		-- Chef emotes
		local x, y = chef.x + 55, chef.y + 20
		tfm.exec.addImage("164fc251e73.png", "!1", x, y)
		tfm.exec.addImage("164fc24df41.png", "!1", x, y + 20)
	end

	eventMouse = function(playerName, x, y)
		if tfm.get.room.playerList[playerName].isDead then return end

		if playerFlashData[playerName].help then
			closeGuide(playerName)
			return
		end

		if playerFlashData[playerName].assembling.selected > 0 then
			if math.pythag(x, y, 655, 435, 120) then
				x, y = x - 30, y - 30 -- image ~axis

				local stored = playerFlashData[playerName].concatenatedStorage[playerFlashData[playerName].assembling.selected]

				local cat, id = parseId(stored[1])
				local o = ingredient[enum_ingredients[cat]][id]

				playerFlashData[playerName].assembling.ingredients[#playerFlashData[playerName].assembling.ingredients + 1] = {
					id = tfm.exec.addImage(o.icon .. ".png", "!40", x, y, playerName),
					icon = o.icon,
					x = x,
					y = y,
					_cat = cat,
					_id = id,
					_s = stored[1]
				}

				stored[2] = stored[2] - 1

				if stored[2] > 0 then
					ui.updateTextArea(playerFlashData[playerName].assembling.selected, string.format(picker_format, playerFlashData[playerName].assembling.selected, stored[2]), playerName)
				else
					ui.removeTextArea(playerFlashData[playerName].assembling.selected, playerName)

					local id = playerFlashData[playerName].assembling.selected * 2
					tfm.exec.removeImage(playerFlashData[playerName].assembleImgs[id - 1])
					tfm.exec.removeImage(playerFlashData[playerName].assembleImgs[id])
				end

				playerFlashData[playerName].assembling.selected = -1
			end
		else
			tfm.exec.chatMessage(translation().assembling.select, playerName)
		end
	end

	eventKeyboard = function(playerName, key, d, x, y)
		if key == 3 then
			local timer = os.time() > playerFlashData[playerName].animalTimer

			for i = 1, 3 do
				local a = playerFlashData[playerName].animals[i]
				if a and (a[1] <= x and a[3] >= x and a[2] <= y and a[4] >= y) then
					if timer and math.random(1, animalEnums[i].pos) < 3 then
						playerFlashData[playerName].animalTimer = os.time() + 5000

						local cat, id = parseId(table.random(animalEnums[i].id))
						cat = enum_ingredients[cat]

						storeIngredient(playerName, ingredient[cat][id])
						tfm.exec.chatMessage(string.format(translation().animal_ingred, translation().ingredient[cat][id]) .. "\n~> " .. translation().ui.freezer .. " ( " .. #playerData[playerName].storage[1] .. " / " .. limits.freezer .. " )", playerName)

						a[5] = a[5] + 1

						if a[5] == 6 then
							playerFlashData[playerName].animals[i] = nil
						end

						return
					else
						tfm.exec.displayParticle(3, x + math.random(-15, 10), y + math.random(-15, 10), table.random({ -.5, .5 }), table.random({ -.5, .5}), 0, 0, playerName)
					end
				end
			end
		elseif key == 32 then
			if playerFlashData[playerName].assembling.open then
				closeAssembleUI(playerName)
				return
			else
				if not playerFlashData[playerName].order.delivered and playerFlashData[playerName].pizzaIcon > 0 then
					if x > customer.x - 10 and x < (customer.x + customer.w + 10) and y > 600 then
						playerFlashData[playerName].order.delivered = true

						customer:deleteCallback(playerName)

						tfm.exec.removeImage(playerFlashData[playerName].pizzaIcon)
						playerFlashData[playerName].pizzaIcon = -1

						tfm.exec.removeImage(playerFlashData[playerName].order.customerPizza)
						playerFlashData[playerName].order.customerPizza = -1

						-- Get info
						local iter, extra_allowed, extra_not_allowed = { }, 0, 0
						for k, v in next, playerFlashData[playerName].assembling.ingredients do
							-- Check for extra ingredients
							local skip = false
							if v._cat == 1 then
								if table.find(ingredient.extra, v._id) then
									skip = true
									if playerData[playerName].order.pizza == 2 or (playerData[playerName].order.ingredients and table.find(playerData[playerName].order.ingredients, v._id)) then
										extra_not_allowed = extra_not_allowed + 1
									else
										extra_allowed = extra_allowed + 1
									end
								end
							end

							-- Inserts the ingredients in a table
							if not iter[v._s] then
								local cat = enum_ingredients[v._cat]

								local s = (ingredient[cat][v._id].inCabinet and 2 or 1)

								local success, key = table.find(playerData[playerName].storage[s], tonumber(v._s), 1)

								if not success then
									error("@" .. playerName .. " | iter~assembling.ingredients | " .. tostring(v._s))
								end

								-- quantity, storage, key
								iter[v._s] = { 1, s, key, _local = (not skip and { cat = cat, id = v._id } or nil) }
							else
								iter[v._s][1] = iter[v._s][1] + 1
							end
						end

						-- Get points
						local points = 10

						-- Temperature
						if playerFlashData[playerName].pizzaLevel ~= playerData[playerName].order.level then
							if playerFlashData[playerName].pizzaLevel > 1 and (playerFlashData[playerName].pizzaLevel == playerData[playerName].order.level + 1 or playerFlashData[playerName].pizzaLevel == playerData[playerName].order.level - 1) then
								points = points - 3
							else
								points = 0
							end
						end

						-- Quantities and removing
						local found_ingredients = 0
						local toRemove = { }
						for k, v in next, iter do
							if v._local then -- else = extra ingredient
								if pizzas[playerData[playerName].order.pizza].ingredients[v._local.cat] then
									local found, key = table.find(pizzas[playerData[playerName].order.pizza].ingredients[v._local.cat], v._local.id, 1)

									if found then
										found_ingredients = found_ingredients + 1

										if v[1] ~= pizzas[playerData[playerName].order.pizza].ingredients[v._local.cat][key][2] then
											-- the value can be + or - depending if it's > than the ordered
											points = points - ((pizzas[playerData[playerName].order.pizza].ingredients[v._local.cat][key][2] - v[1]) * .3)
										end
									else -- Wrong ingredient
										points = points - 1
									end
								end

								toRemove[#toRemove + 1] = { playerName, v[2], v[3], v[1], true }
							end
						end
						-- Avoid crash
						table.sort(toRemove, function(a, b) return a[3] < b[3] end)
						for i = #toRemove, 1, -1 do
							removeIngredient(table.unpack(toRemove[i]))
						end

						local ordered_ingredients = 0
						for cat, list in next, pizzas[playerData[playerName].order.pizza].ingredients do
							for _, info in next, list do
								ordered_ingredients = ordered_ingredients + 1
							end
						end

						-- Ingredients that were ordered but weren't in the pizza
						local ingred_diff = found_ingredients / ordered_ingredients * 100
						points = points - ((ordered_ingredients - found_ingredients) * (ingred_diff <= 50 and 2 or 1.5))

						playerData[playerName].storage[3] = playerData[playerName].storage[3] - ingredient.plant[12].doughQuantity
						-- Removes oil, sauce
						for i = #playerData[playerName].storage[2], 1, -1 do -- avoid crash, again
							-- oil, sauce. (no time for parse ids)
							if playerData[playerName].storage[2][i][1] == 33 or playerData[playerName].storage[2][i][1] == 34 then
								removeIngredient(playerName, 2, i, 1, true)
							end
						end

						points = points - (extra_not_allowed * .5)
						points = points + (extra_allowed * .4)

						local count = countPizzas(playerName)

						if points < 1 then
							tfm.exec.chatMessage(translation().order.delivered[0] .. " <J>[ " .. count .. " / " .. #pizzas .. " ]", playerName)
						else
							local level = (points <= 7.5 and (points <= 5.5 and 1 or 2) or 3)

							local gain = playerFlashData[playerName].order.total * moneyPercentages[level] + (extra_allowed * 3) - (extra_not_allowed * 2)

							playerData[playerName].cash = playerData[playerName].cash + gain

							count = count + (level > 1 and 1 or 0)
							tfm.exec.chatMessage(string.format(translation().order.delivered[level], gain) .. " <J>[ " .. count .. " / " .. #pizzas .. " ]", playerName)

							if level > 1 then
								playerData[playerName].completions = playerData[playerName].completions + (2 ^ playerData[playerName].order.pizza)
								playerData[playerName].order = nil

								-- Titles
								if count == #pizzas then
									playerData[playerName].completions = 0 -- resets
									system.giveTitle(playerName)
								end
								tfm.exec.giveConsumables(playerName, 26, 10)
							end
						end
						playerData[playerName]()

						displayCash(playerName)

						updateFlashStorage(playerName)

						-- Resets
						playerFlashData[playerName].usedWheats = false
					end
				end
			end
		end
	end

	local finalized = false

	local event250ms
	event250ms = system.looping(function()
		for player, data in next, playerData do
			for id, spot in next, data.spots do
				if spot and spot[2] > -1 then
					spot[2] = spot[2] + .25
				end
			end
		end

		if finalized then
			for i = 1, #event250ms do
				system.removeTimer(event250ms[i])
			end
		end
	end, 4)

	local canBreak = 0 -- Skip event in timer glitch
	local event1000ms = system.newTimer(function()
		if canBreak >= 0 then
			canBreak = -1
			tfm.exec.chatMessage("\n" .. translation().welcome .. "\n")
		end

		for player, data in next, playerData do
			for id, spot in next, data.spots do
				if spot then
					updateSpot(player, id)
				end
			end
		end

		for id, oven in next, ovens do
			if oven then
				oven.time = oven.time + 1

				if oven.time % limits.pizzaCycle == 0 then
					oven.level = oven.level + 1

					if oven.level >= #pizzas._state then
						oven.level = #pizzas._state
						updateFlashStorage(oven.playerName) -- Resets the lost ingredients
						destroyOven(id, oven.playerName)
						break
					else
						tfm.exec.removeImage(oven.icon)
						oven.icon = tfm.exec.addImage(pizzas._state._temperature[oven.level] .. ".png", "?31", oven.x + 60, 200, oven.playerName)
					end
				end
			end
		end
	end, 1000, true)

	eventLoop = function(elapsed, remaining)
		if not __started then return end
		if canBreak >= 0 then
			canBreak = canBreak + .5

			if canBreak == 1.5 then
				tfm.exec.chatMessage("[Pizzeria] <ROSE>" .. translation().error_timer)
			elseif canBreak == 2 then
				system.exit()
			end
			return
		end

		if elapsed < 4000 then return end

		for name, player in next, tfm.get.room.playerList do
			if not player.isDead and playerFlashData[name] then
				if playerFlashData[name].assembling.open then
					tfm.exec.movePlayer(name, 375, 489)
				end
			end
		end

		if remaining <= 1000 then
			tfm.exec.setGameTime(module.time)
		end
	end

	eventNewPlayer = function(playerName, alivePlayer)
		if not __started then return end
		if not alivePlayer then
			loadBackground(playerName)
		end

		-- Empty spots
		for e = 1, 6 do
			local id = tfm.exec.addImage("164431b8c1e.png", "?2", 906 + (e * 100), 600, playerName)

			if alivePlayer then
				-- spot, stage
				playerFlashData[playerName].spots[e] = { id, 0 }
			end
		end
	end

	eventPlayerDataLoaded = function(playerName, data)
		data = json.decode(data)

		playerData[playerName] = data[module.name] or {
			won = false,
			spots = { false, false, false, false, false, false },
			cash = 150,
			storage = {
				-- Freezer, Cabinet, Wheat
				[1] = { },
				[2] = { },
				[3] = 0
			},
			completions = 0,
			--[[

			spots[x] = {
				[1] = ingredient_id,
				[2] = remaining_time
			}

			order = {
				pizza = pizza_id,
				ingredients = ignored_ingredients,
				level = 0,
			}

			]]
		}
		data[module.name] = nil
		playerData[playerName].__modules = data

		playerFlashData[playerName].dataLoaded = true
		-- HUD
		displayCash(playerName)
		displayWheat(playerName)

		if not playerData[playerName].order then
			playerData[playerName].order = { }

			local pizza
			repeat -- won't crash because eventKeyboard manages completions
				pizza = math.random(#pizzas)
			until bit32.band(2 ^ pizza, playerData[playerName].completions) == 0

			playerData[playerName].order.pizza = pizza

			if math.random(20) < 9 then 
				local ignoredTotal = table.random(possibilityRates.extraIngredients)

				playerData[playerName].order.ingredients = { }
				local ingredients = { }

				for i = 1, ignoredTotal do
					local ingred
					repeat
						ingred = table.random(ingredient.extra)
					until not (pizza == 3 and ingred == 2) and not ingredients[ingred] -- Can't be basil if it's Margherita
					ingredients[ingred] = i

					playerData[playerName].order.ingredients[i] = ingred
				end
			end

			playerData[playerName].order.level = table.random(possibilityRates.pizzaTemperature)
		end
		local pizza = pizzas[playerData[playerName].order.pizza]

		-- Fix bug
		if playerData[playerName].order.pizza == 3 and playerData[playerName].order.ingredients then
			local update = false
			for i = 1, #playerData[playerName].order.ingredients do
				if playerData[playerName].order.ingredients[i] == 2 then -- if is Margherita and basil
					table.remove(playerData[playerName].order.ingredients, i)
					update = true
					break
				end
			end

			if update then
				playerData[playerName]()
			end
		end

		local total = 15
		playerFlashData[playerName].order.ingredients = table.copy(pizza.ingredients)

		for t, l in next, playerFlashData[playerName].order.ingredients do
			for _, d in next, l do
				-- [1] = id, [2] = quantity
				total = total + math.floor(((ingredient[t][d[1]].price / (ingredient[t][d[1]].quantity or 1)) * 2) * d[2])
			end
		end

		playerFlashData[playerName].order.total = total

		for i = 1, 6 do
			updateSpot(playerName, i)
		end

		updateFlashStorage(playerName)

		system.bindKeyboard(playerName, 3, true, true)
		system.bindKeyboard(playerName, 32, true, true)

		-- Limits to fix problem due to animal shits
		playerFlashData[playerName]._limits.freezer = math.max(limits.freezer, #playerData[playerName].storage[1])
		-- Prevention
		playerFlashData[playerName]._limits.cabinet = math.max(limits.cabinet, #playerData[playerName].storage[2])

		if playerData[playerName].won then
			tfm.exec.setNameColor(playerName, 0xF3D460)
		end
	end

	eventTextAreaCallback = function(id, playerName, cbk, ignore)
		if tfm.get.room.playerList[playerName].isDead or not (playerFlashData[playerName] and playerFlashData[playerName].dataLoaded) then return end

		if not ignore then
			if playerFlashData[playerName].callbackTimer > os.time() then return end
			playerFlashData[playerName].callbackTimer = os.time() + 500
		end

		local c = string.split(cbk, "[^%.]+")

		if c[1] == "callback" then
			if callback[c[2]] then
				callback[c[2]]:performAction(playerName, id - enumIds.callback)
			end
		elseif c[1] == "button" then
			if c[2] == "close" then
				if c[3] == "market" then
					tfm.exec.removeImage(playerFlashData[playerName].marketImgId)
					playerFlashData[playerName].currentSpot = -1

					for i = 1, 2 do
						ui.removeTextArea(i, playerName)
						ui.removeButton(i, playerName)
					end

					ui.removeMenu(playerName)
				elseif c[3] == "order" then
					for i = 1, #playerFlashData[playerName].order.images do
						tfm.exec.removeImage(playerFlashData[playerName].order.images[i])
					end
					playerFlashData[playerName].order.images = { }

					for i = 1, 8 do
						ui.removeTextArea(i, playerName)
					end

					ui.removeButton(1, playerName)
					ui.removeMenu(playerName)
				elseif c[3] == "smasher" then
					for i = 1, #playerFlashData[playerName].smasherImgIds do
						tfm.exec.removeImage(playerFlashData[playerName].smasherImgIds[i])
					end
					playerFlashData[playerName].smasherImgIds = { }

					for i = 1, #smasherEnums do
						ui.removeTextArea(i, playerName)
					end

					ui.removeButton(1, playerName)
					ui.removeMenu(playerName)
				elseif c[3] == "storage" then
					-- Resets pagination because this variable is shared in two menus
					playerFlashData[playerName].storageIndex = 1

					closeStorageImg(tonumber(c[4]), playerName)

					tfm.exec.removeImage(playerFlashData[playerName].storageImgId)
					playerFlashData[playerName].storageImgId = -1

					for i = 1, 2 do
						ui.removeTextArea(i, playerName)
					end

					ui.removeButton(1, playerName)
					ui.removeMenu(playerName)
				elseif c[3] == "garbage" then
					closeStorageImg(3, playerName)

					tfm.exec.removeImage(playerFlashData[playerName].garbageImgId)
					playerFlashData[playerName].garbageImgId = -1

					for i = 1, 2 do
						ui.removeTextArea(i, playerName)
						ui.removeButton(i, playerName)
					end

					ui.removeMenu(playerName)
				end
			elseif c[2] == "market" then
				if c[3] == "previous" then
					playerFlashData[playerName].marketIndex = playerFlashData[playerName].marketIndex - 1
					if playerFlashData[playerName].marketIndex < 1 then
						playerFlashData[playerName].marketIndex = #ingredient.plant
					end

					market(playerName, true)
				elseif c[3] == "next" then
					playerFlashData[playerName].marketIndex = playerFlashData[playerName].marketIndex + 1
					if playerFlashData[playerName].marketIndex > #ingredient.plant then
						playerFlashData[playerName].marketIndex = 1
					end

					market(playerName, true)
				end
			elseif c[2] == "seed" then
				if playerFlashData[playerName].currentSpot > 0 then
					local o = ingredient.plant[playerFlashData[playerName].marketIndex]

					if o.price <= playerData[playerName].cash then
						playerData[playerName].cash = playerData[playerName].cash - o.price

						playerData[playerName].spots[playerFlashData[playerName].currentSpot] = {
							-- id, remaining_time
							[1] = o.id,
							[2] = 0
						}

						playerData[playerName]()

						displayCash(playerName)

						updateSpot(playerName, playerFlashData[playerName].currentSpot)
						eventTextAreaCallback(id, playerName, "button.close.market", true)
					else
						tfm.exec.chatMessage(translation().insufficient_cash, playerName)
					end
				end
			elseif c[2] == "storage" then
				c[4] = tonumber(c[4])

				if c[3] == "previous" then
					playerFlashData[playerName].storageIndex = playerFlashData[playerName].storageIndex - 1
					if playerFlashData[playerName].storageIndex < 1 then
						playerFlashData[playerName].storageIndex = #playerData[playerName].storage[c[4]]
					end

					storage(c[4], playerName, true)
				elseif c[3] == "next" then
					playerFlashData[playerName].storageIndex = playerFlashData[playerName].storageIndex + 1
					if playerFlashData[playerName].storageIndex > #playerData[playerName].storage[c[4]] then
						playerFlashData[playerName].storageIndex = 1
					end

					storage(c[4], playerName, true)
				end
			elseif c[2] == "garbage" then
				if c[3] == "previous" then
					playerFlashData[playerName].garbageIndex = playerFlashData[playerName].garbageIndex - 1
					if playerFlashData[playerName].garbageIndex < 1 then
						playerFlashData[playerName].garbageIndex = #playerFlashData[playerName].concatenatedStorage
					end

					garbage(playerName, true)
				elseif c[3] == "next" then
					playerFlashData[playerName].garbageIndex = playerFlashData[playerName].garbageIndex + 1
					if playerFlashData[playerName].garbageIndex > #playerFlashData[playerName].concatenatedStorage then
						playerFlashData[playerName].garbageIndex = 1
					end

					garbage(playerName, true)
				end
			elseif c[2] == "trash" then
				local storage = playerFlashData[playerName].garbageIndex > #playerData[playerName].storage[1] and 2 or 1

				local index = playerFlashData[playerName].garbageIndex
				if storage == 2 then
					index = index - #playerData[playerName].storage[1]
				end

				-- Can't throw away when you are holding it
				if playerData[playerName].storage[storage][index][1] == 33 and playerFlashData[playerName].doughMission[2] then -- has oil
					return
				end
				if playerData[playerName].storage[storage][index][1] == 34 and playerFlashData[playerName].doughMission[5] then -- has tomato sauce
					return
				end

				local rem = removeIngredient(playerName, storage, index)
				if rem == 2 then
					-- Won't work like a pointer
					table.remove(playerFlashData[playerName].concatenatedStorage, playerFlashData[playerName].garbageIndex)

					-- When it removes the ingredient, the index may be different, so needs to reset
					playerFlashData[playerName].garbageIndex = 1
				elseif rem == 1 then
					playerFlashData[playerName].concatenatedStorage[playerFlashData[playerName].garbageIndex][2] = playerFlashData[playerName].concatenatedStorage[playerFlashData[playerName].garbageIndex][2] - 1
				end

				if #playerFlashData[playerName].concatenatedStorage > 0 then
					garbage(playerName, true)
				else
					eventTextAreaCallback(nil, playerName, "button.close.garbage", true)
				end
			elseif c[2] == "smash" then
				local o = ingredient.plant[tonumber(c[3])]
				local to = ingredient.others[o.to]

				local storage = (o.inCabinet and 2 or 1)
				local success, key = table.find(playerData[playerName].storage[storage], tonumber(o.cat .. o.id), 1)

				-- Sorry for the gambiarra below. I couldn't use remove/store.
				if success and playerData[playerName].storage[storage][key][2] >= o.smashQuantity then
					local storageName = (to.inCabinet and "cabinet" or "freezer")
					local foo = (to.inCabinet and 2 or 1)
					if not table.find(playerData[playerName].storage[foo], tonumber(to.cat .. to.id), 1) and #playerData[playerName].storage[foo] >= playerFlashData[playerName]._limits[storageName] then
						return tfm.exec.chatMessage(string.format(translation().harvest.full, string.lower(translation().ui[storageName])), playerName)
					else
						removeIngredient(playerName, storage, key, o.smashQuantity)
						storeIngredient(playerName, to, 1)

						tfm.exec.chatMessage(string.format(translation().smasher.get, translation().ingredient.others[to.id], translation().ui[storageName]), playerName)

						return eventTextAreaCallback(nil, playerName, "button.close.smasher", true)
					end
				end
				tfm.exec.chatMessage(string.format(translation().smasher.not_quantity, translation().ingredient.others[to.id], o.smashQuantity, translation().ingredient.plant[o.id]), playerName)
			elseif c[2] == "use" then
				c[3] = tonumber(c[3])

				if c[3] == 3 then -- Mission 2
					if not playerFlashData[playerName].doughMission[2] then
						concludeMission(2, playerName, ingredient.others[c[3]].icon .. ".png")
					end
				elseif c[3] == 4 then -- Mission 5
					if not playerFlashData[playerName].doughMission[5] and playerData[playerName].order.pizza ~= 2 then -- not sweet
						concludeMission(5, playerName, ingredient.others[c[3]].icon .. ".png")
					end
				end

				eventTextAreaCallback(nil, playerName, "button.close.storage.2", true)
			end
		elseif c[1] == "select" then
			playerFlashData[playerName].assembling.selected = tonumber(c[2])
		end
	end

	eventEmotePlayed = function(playerName, emote)
		if tfm.get.room.playerList[playerName].isDead or not (playerFlashData[playerName] and playerFlashData[playerName].dataLoaded) then return end

		if playerFlashData[playerName].kissed then return end

		if emote == 3 or emote == 5 then
			local x, y = tfm.get.room.playerList[playerName].x, tfm.get.room.playerList[playerName].y

			if chef and x >= chef.x - 50 and x <= chef.x + chef.w + 50 and y < chef.y + chef.h then
				local foo, bar =  math.random(6), math.random(6)
				if foo == bar then
					playerFlashData[playerName].kissed = true

					playerData[playerName].cash = playerData[playerName].cash + 15
					playerData[playerName]()

					displayCash(playerName)

					for i = 1, 15 do
						local dir = math.random(0, 1) == 0 and -1 or 1
						tfm.exec.displayParticle(15, x + math.random(0, 50) * dir, y + math.random(0, 30) * dir, -dir * math.random(2, 6) / 10, -dir * math.random(3, 8) / 10, playerName)
					end
				else
					tfm.exec.displayParticle(5, x, y, -2, -2, playerName)
				end
			end
		end
	end

	eventPlayerDied = tfm.exec.respawnPlayer

	eventPlayerLeft = function(playerName)
		if playerFlashData[playerName] and playerFlashData[playerName].dataLoaded then
			for id, oven in next, ovens do
				if oven and oven.playerName == playerName then
					destroyOven(id)
					break
				end
			end

			playerData[playerName]()
			playerData[playerName] = nil
			playerFlashData[playerName].dataLoaded = false
		end
	end

	for i, f in next, { "AutoNewGame", "AfkDeath", "AutoShaman", "AutoTimeLeft", "DebugCommand", "MortCommand", "PhysicalConsumables" } do
		tfm.exec["disable" .. f]()
	end

	system.newTimer(function()
		__started = true
		tfm.exec.newGame(module.map)
	end, 10000, false)
end

modules.dance = function()
	tfm.exec.disableAutoNewGame(true)
	tfm.exec.disableAutoShaman(true)
	tfm.exec.disableAutoTimeLeft(true)
	tfm.exec.disableAfkDeath(true)

	players = {}
	timerList = {}
	moves = {
			[1] = {direction = -90; key = 39, key_ = 68}, --right
			[2] = {direction = 90; key = 37, key_ = 65}, -- left
			[3] = {direction = -180; key = 38, key_ = 87}, -- up
			[4] = {direction = 0; key = 40, key_ = 83}, -- down
		}
	waves = {
			[1] = {seconds = 1800, loops = 38},
			[2] = {seconds = 1500, loops = 50},
			[3] = {seconds = 1000, loops = 55},
		}
	default = {
			stats = {
				waves = 0,
				wins = 0,
				deaths = 0,
				right = 0,
				wrong = 0,
			},
	}
	keys = {
			68,
			65,
			87,
			83,
			39,
			37,
			38,
			40,
			72,
		}
	messages = {
			_help = '<p align=\'center\'><font color=\"#009d9d\"><b><font size=\"20\">#dance</font></b></font></p><br>Move in the right directions. If you do movement right, you get <font color=\"#92d64d\">3 points</font>! If you don\'t, you get <font color=\"#d64d4d\">-2 points</font>!<br><br>Every round <span>consists of</span> 3 waves (<i>1 wave per minute!</i>). With every new wave you have to move faster and faster. If you make mistakes too much, you\'re going to be killed.<br><br><div>Use arrows or WASD to move and have fun in our <V>#DANCE<N>-PARTY!</div><br>',
			_start = '<J>Հ[#DANCE] <N>You have <V>10 seconds<N> to get ready!',
			_go = '<J>Հ[#DANCE] <N>GO!',
			_newWave = '<J>Հ[#DANCE] <V>Wave %x<N> starts right now!',
			_win = '<J>Հ[#DANCE] <V>%s <N>won!',
			_welcome = '<J>Հ[#DANCE] <N>Welcome to our <V>#dance<N>-party! Press <V>[H]<N> to learn more. Report any issues to <V>Aviener#0000',
			_right = '<BL>Right! +3',
			_wrong = '<BL>Wrong! -2',
			_draw = '<N>Nobody has won! :['
		}

	module = {
			start = function()
				for name,player in pairs(tfm.get.room.playerList) do
					-- module.players.getData(name)
					players[name] = {lock = true, opennedPopup = false}
					for i, key in ipairs(keys) do
						system.bindKeyboard(name, key, true, true)
					end
				end
				tfm.exec.setGameTime(10)
				--print('<J>## <BL>Game is loaded!')
			end,
			addMove = function()
				if started == true then
					points = points + 1
					for name,player in pairs(tfm.get.room.playerList) do
						if not player.isDead then
							players[name].lock = false
						end
					end
					i = math.random(1, 4)
					tfm.exec.addShamanObject(0, 400, 200, moves[i].direction)
					return moves[i].key, moves[i].key_, points
				end
			end,
			newWave = function()
				if started == true then
					wave = wave + 1
					module.UI.changeStatusBar(wave)
				end
				for name,player in pairs(tfm.get.room.playerList) do
					if player.score < points then
						tfm.exec.killPlayer(name)
					end
				end
				module.timers.addTimer(module.addMove, waves[wave].seconds, waves[wave].loops, i)
				module.UI.message(string.format(messages._newWave, wave))
			end,
			timers = {
				addTimer = function(callback, time, loops, label, ...)
					local id = #timerList + 1
					timerList[id] = {
					callback = callback,
					time = time,
					loops = loops or 1,
					label = label,
					arguments = {...},
					isComplete = false,
					currentTime = 0,
					currentLoop = 0,
					}
					return id
				end,
				removeTimer = function(id)
					if timer[id] then
						timerList[id] = 0
						return true
					end
				return false
				end,
				removeAll = function()
					timerList = {}
				end,
			},
			--User Interface
			UI = {
				changeStatusBar = function(wave)
					if wave <= 3 then
						local code = tfm.get.room.currentMap
						local author = tfm.get.room.xmlMapInfo.author
						tfm.exec.setUIMapName('<J>'..author..'<BL> - '..code..'   <font color="#5a5f6e">|</font>   <N>Wave: <V>'..wave..'/3')
					end
				end,
				message = function(message, name)
					tfm.exec.chatMessage(message, name)
					--print (message)
				end,
				addPopup = function(id, text, n, x, y, width, height)
					players[n].opennedPopup = true
					ui.addTextArea(id, "", n, x +  - 2, y + 18, width + 24, height + 14, 0x2E221B, 0x2E221B, 1, true)
					ui.addTextArea(id + 1, "", n, x + - 1, y + 19, width + 22, height + 12, 0x986742, 0x986742, 1, true)
					ui.addTextArea(id + 2, "", n, x + 2, y + 22, width + 16, height + 6, 0x171311, 0x171311, 1, true)
					ui.addTextArea(id + 3, "", n, x + 3, y + 23, width + 14, height + 4, 0x0C191C, 0x0C191C, 1, true)
					ui.addTextArea(id + 4, "", n, x + 4, y + 24, width + 12, height + 2, 0x24474D, 0x24474D, 1, true)
					ui.addTextArea(id + 5, "", n, x + 5, y + 25, width + 10, height + 0, 0x183337, 0x183337, 1, true)
					ui.addTextArea(id + 6, text, n, x + 6, y + 26, width + 8, height + - 2, 0x324650, 0x324650, 1, true)
					ui.addTextArea(id + 7, "", n, x + 12, y + height - 20 + 25, width - 5, 15, 0x5D7D90, 0x5D7D90, 1, true)
					ui.addTextArea(id + 8, "", n, x + 12, y + height - 20 + 27, width - 5, 15, 0x11171C, 0x11171C, 1, true)
					ui.addTextArea(id + 9, "<p align='center'><a href='event:close'><N>Close</a>", n, x + 12, y + height - 20 + 26, width - 5, 15, 0x3C5064, 0x3C5064, 1, true)
				end,
				removePopup = function(id, n)
					for id = id, (id + 9) do
						ui.removeTextArea(id, n)
					end
					players[n].opennedPopup = false
				end;
			},
			--Players settings, data and etc
			players = {
				--[[getData = function(name)
						local data = system.loadPlayerData(name)
						if data == nil then
							set default 0/0/0/0
						end
						return data end
					end,]]
			},

		}
		eventNewGame = function()
			started = false
			wave = 1
			points = 0
			module.timers.removeAll()
			for name,player in pairs(tfm.get.room.playerList) do
				players[name].lock = true
				tfm.exec.setPlayerScore(name, 0, false)
			end
			tfm.exec.setGameTime(220)
			module.UI.message(messages._start)
			module.UI.changeStatusBar(wave)
			module.timers.addTimer(module.addMove, waves[wave].seconds, waves[wave].loops, i)
			module.timers.addTimer(module.newWave, 60000, 2)
		end
		eventNewPlayer = function (name)
			if started == true then
				players[name].lock = true;
			end;
			module.UI.message(messages._welcome, n);
		end;
		function eventKeyboard(name, key, down, x, y)
			if key~= 72 and players[name].lock == false and started == true then
				if key == moves[i].key or key == moves[i].key_ then
					module.UI.message(messages._right, name)
					tfm.exec.setPlayerScore(name, 3, true)
					players[name].lock = true
				else
					module.UI.message(messages._wrong, name)
					tfm.exec.setPlayerScore(name, -2, true)
					players[name].lock = true
				end
			end
			if key == 72 and not players[name].opennedPopup then
				module.UI.addPopup(1, messages._help, name, 230, 100, 327, 210)
			elseif key == 72 and players[name].opennedPopup then
				module.UI.removePopup(1, name)
			end
		end
		function eventTextAreaCallback(textAreaID, name, callback)
			if callback == 'close' then
				module.UI.removePopup(1, name)
			end
		end
		eventPlayerDied = function(name)
			local i = 0
			n = name
			for name,player in pairs(tfm.get.room.playerList) do
				if not player.isDead then
					i = i + 1
				end
			end
			if i == 1 then
				n = name
				tfm.exec.playerVictory(name)
				module.UI.message(string.format(messages._win, name))
			end
			if i == 0 then
				tfm.exec.setGameTime(0)
				module.UI.message(messages._draw)
			end
		end
		eventLoop = function(time, remain)
			-- timer
			local timersToRemove = {}
			for id = 1, #timerList do
				local timer = timerList[id]
				if type(timer) == 'table' then
					if not timer.isComplete then
						timer.currentTime = timer.currentTime + 500
						if timer.currentTime >= timer.time then
							timer.currentTime = 0
							timer.currentLoop = timer.currentLoop + 1
							if timer.loops > 0 then
								if timer.currentLoop >= timer.loops then
									timer.isComplete = true
								end
							end
							if type(timer.callback) == 'function' then
								timer.callback(timer.currentLoop, table.unpack(timer.arguments))
							end
						end
					end
					if timer.isComplete then
						if type(eventTimerComplete) == 'function' then
							eventTimerComplete(id, timer.label)
						end
						timersToRemove[#timersToRemove+1] = id
					end
				end
			end
			if time >= 10000 and started == false then
				started = true
				module.UI.message(messages._go)
			end
			if remain <= 0 then
				tfm.exec.newGame(6680501)
			elseif remain <= 40 then
				started = false
				for name,player in pairs(tfm.get.room.playerList) do
					if player.score < points then
						tfm.exec.killPlayer(name)
					end
				end
			end
		end


	module.start()
end

modules.ranking = function()
	local PAGE_LINES = 20

	ui.menu = function(text, playerName, x, y, w, h)
		local id = 300 - 8

		x = x + 6
		y = y + 6

		ui.addTextArea(0, "", playerName, -1500, -1500, 5000, 5000, 1, 1, .2)

		ui.addTextArea(id, "", playerName, x, y, w, h, 0x78462B, 0x78462B, 1)

		ui.addTextArea(id - 1, "", playerName, x, y, 20, 20, 0xBEB17D, 0xBEB17D, 1)
		ui.addTextArea(id - 2, "", playerName, x + w - 20, y, 20, 20, 0xBEB17D, 0xBEB17D, 1)
		ui.addTextArea(id - 3, "", playerName, x, y + h - 20, 20, 20, 0xBEB17D, 0xBEB17D, 1)
		ui.addTextArea(id - 4, "", playerName, x + w - 20, y + h - 20, 20, 20, 0xBEB17D, 0xBEB17D, 1)

		ui.addTextArea(id - 5, "", playerName, x, y + (h + 40) / 4, w, ((h - 40) / 2), 0x9D7043, 0x9D7043, 1)
		ui.addTextArea(id - 6, "", playerName, x + (w + 40) / 4, y, ((w - 40) / 2), h, 0x9D7043, 0x9D7043, 1)

		ui.addTextArea(id - 7, text, playerName, x + 3, y + 3, w - 6, h - 6, 0x1C3A3E, 0x0E1619, 1)
	end
	ui.button = function(id, text, eventName, playerName, x, y, w, h)
		id = 200 + id * 2

		ui.addTextArea(id, "", playerName, x + 1, y + 2, w, h, 1, 1, 1)
		ui.addTextArea(id - 1, "<p align='center'><font size='13'><a href='event:" .. eventName .. "'>" .. text .. "\n", playerName, x, y, w, h, 0x142A2D, 0x142A2D, 1)
	end

	local playerData = { }
	local lastUpdate

	local infoName, totalPages, info = { "Name", "Rounds", "Cheese", "First", "Normal Saves", "Hard Mode", "Divine Mode" }, 0

	local displayData = function(playerName)
		local page = (((playerName and playerData[playerName] or 1) - 1) * PAGE_LINES) + 1

		local numbers, counter, data, color = { }, 0, { }
		for i = page, page + (PAGE_LINES - 1) do
			if not info[i] then break end
			color = (i % 2 == 1 and "<N>" or "<N2>")

			counter = counter + 1
			numbers[counter] = color .. "#" .. i

			for j = 1, #infoName do
				if not data[j] then
					data[j] = { }
				end
				data[j][counter] = color .. tostring(info[i][j])
			end
		end

		ui.updateTextArea(0, "<p align='center'>" .. table.concat(numbers, "\n"), playerName)
		for i = 1, #infoName do
			ui.updateTextArea(i, "<p align='center'>" .. table.concat(data[i], '\n'), playerName)
		end
	end

	local showLastUpdated = function()
		ui.setMapName("Last updated: " .. os.date("%Y-%m-%d", lastUpdate))
	end
	
	ui.leaderboard = function(playerName, ignore)
		ui.menu("<textformat leading='-20'><p align='center'><font size='20'>Transformice Leaderboard\n<G>_______________________", playerName, 0, 21, 908, 365)
		ui.addTextArea(0, '', playerName, 15, 110, 34, 272, 0x142A2D, 0x142A2D, 1)

		local x, w = -60
		for i = 1, #infoName do
			w = (i == 1 and 140 or 105)
			x = x + (i == 1 and 121 or i == 2 and 152 or 117)
			ui.addTextArea(i, '', playerName, x, 110, w, 272, 0x142A2D, 0x142A2D, 1)
			ui.addTextArea(-i, "<p align='center'>" .. infoName[i], playerName, x, 78, w, 20, 0x142A2D, 0x142A2D, 1)
		end

		ui.button(1, "&lt; Previous page", "left", playerName, 15, 36, 200, 20)
		ui.button(2, "Next page >", "right", playerName, 705, 36, 200, 20)

		if not ignore then
			displayData(playerName)
		end
	end

	local dotNumber = function(number)
		number = tostring(number)
		local isT = #number % 3 == 0
		number = string.reverse(number)
		number = string.gsub(number, "%d%d%d", "%1,")
		number = string.reverse(number)
		if isT then
			number = string.sub(number, 2)
		end
		return number
	end

	eventTextAreaCallback = function(id, playerName, callback, ignore)
		if callback == "right" then
			ui.removeTextArea(-201, playerName)
			playerData[playerName] = playerData[playerName] + 1
			if playerData[playerName] >= totalPages then
				playerData[playerName] = totalPages
				ui.addTextArea(-203, "<p align='center'><font size='13'><N2>Next page >", playerName, 705, 36, 200, 20, 0x142A2D, 0x142A2D, 1)
			end

			displayData(playerName)
		elseif callback == "left" then
			ui.removeTextArea(-203, playerName)
			playerData[playerName] = playerData[playerName] - 1
			if playerData[playerName] <= 1 then
				playerData[playerName] = 1
				ui.addTextArea(-201, "<p align='center'><font size='13'><N2>&lt; Previous page", playerName, 15, 36, 200, 20, 0x142A2D, 0x142A2D, 1)
			end

			if not ignore then
				displayData(playerName)
			end
		end
	end

	eventNewPlayer = function(playerName, ignore)
		playerData[playerName] = 2
		if info then
			ui.leaderboard(playerName, true)
			eventTextAreaCallback(nil, playerName, "left", ignore)
			showLastUpdated()
		end
	end

	eventFileLoaded = function(_, data)
		lastUpdate, data = string.match(data, "^(%d+)(.+)")
		lastUpdate = lastUpdate * 1000

		local ranking = string.split(data, "[^\002]+")
		local len = #ranking
		for i = 1, len do
			ranking[i] = string.split(ranking[i], "[^\001]+")
			for k = 1, #infoName do
				if k == 1 then
					ranking[i][k] = string.gsub(ranking[i][k], "#%d+", "<G><font size='10'>%1</font></G>", 1)
				elseif k ~= 2 then
					ranking[i][k] = dotNumber(ranking[i][k]) .. " <BL><font size='8'>" .. (math.ceil((ranking[i][k] / ranking[i][2]) * 1000) / 10) .. "%</font></BL>"
				end
			end
			ranking[i][2] = dotNumber(ranking[i][2])
		end
		info = ranking
		totalPages = math.ceil(len / PAGE_LINES)

		for playerName in next, tfm.get.room.playerList do
			eventNewPlayer(playerName, true)
		end
		displayData()
		showLastUpdated()
	end
	system.loadFile(4)

	tfm.exec.disableAutoNewGame()
	tfm.exec.disableMortCommand()
	tfm.exec.disableAutoShaman()
	tfm.exec.newGame('<C><P L="920" /><Z><S><S L="920" H="136" X="460" Y="446" T="6" P="0,0,0.3,0.2,0,0,0,0" /><S L="10" H="10" X="-5" Y="360" T="12" P="0,0,0,0,0,0,0,0" /><S L="10" X="925" H="10" Y="360" T="12" P="0,0,0,0,0,0,0,0" /><S L="920" X="460" H="10" Y="334" T="12" P="0,0,0,0,0,0,0,0" /></S><D><DS Y="360" X="460" /></D><O /></Z></C>')
end

modules.survup = function()
	admin = { ["Blank#3495"] = true, ["Thewarsnipe#0000"] = true, ["Tocutoeltuco#0000"]= true, ["Bolodefchoco#0000"] = true, ["Meltedfruit#0000"] = true } 
	banlist = { }
	lang = { }
	t = {false , 17}
	tp = { }
	pid = { 0, 1, 2, 9, 11, 13 }
	cp = { }
	skills = false

	translation = {
		en = {
			help = [[<N>!map &lt;code> <BL>- Loads a given code, or a new P10 (survivor) map.
<N>&lt;Shift> + &lt;Ctrl> <BL>- Toggles Shaman mode.
<N>&lt;Ctrl> + &lt;Left Click> <BL>- Teleports you
<N>Confetti <BL>- Sets checkpoint
<J>(In shaman mode) <N>Spawning Arrow <BL>- Sets cannon spawn point
<N>!credits <BL>- Know the people who helped this module.
<N>!lang (lang) <BL>- Know the languages the module is available in (or change your language).

<BL>If you have/face any issues, don't hesitate to <N>/c Blank#3495.
			]],
			credits = [[<N>Code: <R>Blank#3495
<N>Translations: Guns_and_roses#6742 <J>(BG)<N>, Tocutoeltuco#0000 <J>(ES)<N>, Bolodefchoco#0000 <J>(BR)<N>, Shadiiii#5522 <J>(TR)<N>.]]
		},
		bg = {
			help = [[<N>!map <код> <BL>- Зарежда даден код или нова P10 (survivor) карта.
<N>&lt;Shift> + &lt;Ctrl> <BL>- Превключва формата на шаман. 
<N>&lt;Ctrl> + &lt;Left Click> <BL>- Телепортира те.
<N> Конфети <BL>-  Поставя точка за връщане на мястото, където умираш.
<J>(In shaman mode) <N>Изстрелва стрела <BL>- Поставя точка за изстрелване на канона.
<N>!credits <BL>- Да знаете хората, които помогнаха на този модул.
<N>!lang (lang) <BL>-  Да знаете езиците, които са валидни в (или да промените на вашия език).

<BL>Ако имате/видите проблеми, не се колебайте, пишете на <N>/c Blank#3495.
			]],
			credits = [[<N>Код: <R>Blank#3495
<N>превод: Guns_and_roses#6742 <J>(BG)<N>, Tocutoeltuco#0000 <J>(ES)<N>, Bolodefchoco#0000 <J>(BR)<N>, Shadiiii#5522 <J>(TR)<N>.]]
		},
		es = {
			help = [[<N>!map &lt;código> <BL>- Carga el mapa dado, o un mapa P10 (survivor).
<N>&lt;Shift> + &lt;Ctrl> <BL>- Te transforma entre ratón y chamán
<N>&lt;Ctrl> + &lt;Click Izquierdo> <BL>- Te teletransporta
<N>Confeti <BL>- Establece un checkpoint
<J>(Siendo chamán) <N>Invocar Flechas <BL>- Establece el punto de invocación de cañones.
<N>!credits <BL>- Muestra las personas que ayudaron con el módulo.
<N>!lang (lang) <BL>- Saber los idiomas en el que el módulo está disponible (o cambia tu idioma).

<BL>Si tenés algún problema, no dudes en susurrar a <N>/c Blank#3495.
			]],
			credits = [[<N>Código: <R>Blank#3495
<N>Traducciones: Guns_and_roses#6742 <J>(BG)<N>, Tocutoeltuco#0000 <J>(ES)<N>, Bolodefchoco#0000 <J>(BR)<N>, Shadiiii#5522 <J>(TR)<N>.]]
		},
		pt = {
			help = [[<N>!map &lt;código> <BL>- Carrega um código dado, ou um mapa P10 (survivor).
<N>&lt;Shift> + &lt;Ctrl> <BL>- Alterna o modo Shaman.
<N>&lt;Ctrl> + &lt;Click Esquerdo> <BL>- Teleporta você
<N>Confete <BL>- Define um checkpoint
<J>(Como Shaman) <N>Invocando Seta <BL>- Define um ponto de invocação para os canhões
<N>!credits <BL>- Veja as pessoas que ajudaram neste módulo.
<N>!lang (idioma) <BL>- Veja os idiomas em que o módulo está disponível (ou altere seu idioma).

<BL>Se você encontrar qualquer problema, não hesite em entrar contato com <N>/c Blank#3495.
			]],
			credits = [[<N>Código: <R>Blank#3495
<N>Traduções: Guns_and_roses#6742 <J>(BG)<N>, Tocutoeltuco#0000 <J>(ES)<N>, Bolodefchoco#0000 <J>(BR)<N>, Shadiiii#5522 <J>(TR)<N>.]]
		},
		tr = {
			help = [[<N>!map <code> <BL>- Yazılan harita kodunu açar, ya da yeni bir P10 (survivor) haritası. 
<N>&lt;Shift Key> + &lt;Ctrl Key> <BL>- Şaman modunu değiştirir.
<N>&lt;Ctrl> + &lt;Sol tık> <BL>- Işınlar.
<N> Konfeti <BL>- Denetim noktasını ayarlar 
<J>(Şaman modunda) <N>Ok yaratmak <BL>- Top yaratma noktası ayarlar 

<BL>Eğer bir probleminiz/bir sorunuz olursa, Çekinmeden <N>/c Blank#3495. 
			]],
			credits = [[<N>Cod: <R>Blank#3495
<N>Çeviriler: Guns_and_roses#6742 <J>(BG)<N>, Tocutoeltuco#0000 <J>(ES)<N>, Bolodefchoco#0000 <J>(BR)<N>, Shadiiii#5522 <J>(TR)<N>.]]
		}
	}
	translation.br = translation.pt 

	cs = 0
	tm = 2

	translate = function(name, command)
		return translation[lang[name]] and translation[lang[name]][command] or translation.en[command]
	end

	eventSummoningEnd = function(n, id, x, y, a)
		if id == 0 then
			t[1], t[3], t[4], t[5] = true, x, y, a - 180
		end
	end

	eventLoop = function(ct, tr)
		if cs >= tm then
			if t[1] then
				tfm.exec.addShamanObject(t[2], t[3], t[4], t[5])
			end
			cs = 0
		end
		cs = cs + 0.5
		
		for n in next, cp do
			tfm.exec.displayParticle(pid[math.random(#pid)], cp[n].x, cp[n].y, 0, 0, 0, 0, n)
		end
	end

	eventChatCommand = function(n, m)
		args, c = {}, 0
		for v in m:gmatch("%S+") do
			c = c + 1
			args[c] = v
		end
 
		if args[1] == "timer" and admin[n] then
			args[2] = tonumber(args[2]) or 2
			tm = args[2]
		elseif args[1] == "map" then
			if banlist[n] then
				tfm.exec.chatMessage("<R>You are not allowed to use this command.",n)
			else
				if args[2] then
					tfm.exec.newGame(args[2])
				else
					tfm.exec.newGame('#10')
				end
			end
		elseif args[1] == "shaman" and admin[n] then
			tfm.exec.setShaman(args[2], not tfm.get.room.playerList[args[2]].isShaman) 
		elseif args[1] == "stop" and admin[n] then
			t[1] = false
		elseif args[1] == "!" and admin[n] then
			tfm.exec.chatMessage("<N>[<R>#Survup<N>] "..table.concat(args,' ',2))
		elseif args[1] == "ban" and admin[n] then
			banlist[args[2]] = not banlist[args[2]]
		elseif args[1] == "skills" and admin[n] then
			local m = tfm.get.room.currentMap
			skills = not skills
			tfm.exec.disableAllShamanSkills(skills)
			tfm.exec.chatMessage("Skills have been " .. (skills and "enabled." or "disabled."))
			tfm.exec.newGame(m)
		elseif args[1] == "cmds" and admin[n] then
			local cmd = {"shaman, stop, !, ban, skills, cmd"}
			tfm.exec.chatMessage("<J>" .. table.concat(cmd, "<N>, <J>"), n)
		elseif args[1] == "help" then
			tfm.exec.chatMessage(translate(n, "help"), n)
		elseif args[1] == "credits" then
			tfm.exec.chatMessage(translate(n, "credits"), n)
		elseif args[1] == "lang" then
			if args[2] then
				lang[n] = args[2]
			else
				local l, c = { }, 0
				for i in next, translation do
					c = c + 1
					l[c] = i
				end
				tfm.exec.chatMessage("<N>Available languages: <J>"..table.concat(l, "<N>, <J>"),n)
			end
		end
 
		if not admin[n] then
			for an in next, admin do
				tfm.exec.chatMessage("<BL>• ["..n.."] !"..m, an)
			end
		end
 
	end
		
	eventNewGame = function()
		t[1] = false 
		cp = { } 
	end

	eventPlayerDied = function(n)
		tfm.exec.respawnPlayer(n)
		if cp[n] then
			tfm.exec.movePlayer(n, cp[n].x, cp[n].y)
		end
	end

	eventNewPlayer = function(n)
		lang[n] = tfm.get.room.playerList[n].community
		tfm.exec.chatMessage(translate(n, "help"), n)
		system.bindKeyboard(n, 17, true)
		system.bindKeyboard(n, 17, false)
		system.bindKeyboard(n, 16, true)
		system.bindKeyboard(n, 69, true)
		system.bindMouse(n)
		tfm.exec.respawnPlayer(n)
		
		if tfm.get.room.uniquePlayers == 1 then
			tfm.exec.newGame('#10')
		end
 
		for a in next, admin do
			tfm.exec.chatMessage("<BL>" .. n .. " (" .. tfm.get.room.playerList[n].community .. ")", a)
		end
	end

	eventKeyboard = function(n, k, d, x, y)
		if k == 17 then
			tp[n] = d
		elseif k == 16 and tp[n] then
			if banlist[n] then
				tfm.exec.chatMessage("<R>You are not allowed to be shaman.",n)
				return
			end
			if not tfm.get.room.playerList[n].isShaman then
				for p in next, tfm.get.room.playerList do
					if tfm.get.room.playerList[p].isShaman then
						tfm.exec.chatMessage("<R>There is already a shaman!", n)
					return
					end
				end
			end
			tfm.exec.setShaman(n, not tfm.get.room.playerList[n].isShaman)
		elseif k == 69 then
			cp[n] = { x = x, y = y }
		end
	end

	eventMouse = function(n, x, y)
		if tp[n] then
			tfm.exec.movePlayer(n, x, y)
		end
	end

	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	for _,v in next,{'disableAfkDeath', 'disableAutoNewGame', 'disableAutoShaman'} do
		tfm.exec[v]()
	end

	system.disableChatCommandDisplay()
	tfm.exec.newGame('#10')
end

modules.triberanking = function()
	local PAGE_LINES = 20

	ui.menu = function(text, playerName, x, y, w, h)
		local id = 300 - 8

		x = x + 6
		y = y + 6

		ui.addTextArea(0, "", playerName, -1500, -1500, 5000, 5000, 1, 1, .2)

		ui.addTextArea(id, "", playerName, x, y, w, h, 0x78462B, 0x78462B, 1)

		ui.addTextArea(id - 1, "", playerName, x, y, 20, 20, 0xBEB17D, 0xBEB17D, 1)
		ui.addTextArea(id - 2, "", playerName, x + w - 20, y, 20, 20, 0xBEB17D, 0xBEB17D, 1)
		ui.addTextArea(id - 3, "", playerName, x, y + h - 20, 20, 20, 0xBEB17D, 0xBEB17D, 1)
		ui.addTextArea(id - 4, "", playerName, x + w - 20, y + h - 20, 20, 20, 0xBEB17D, 0xBEB17D, 1)

		ui.addTextArea(id - 5, "", playerName, x, y + (h + 40) / 4, w, ((h - 40) / 2), 0x9D7043, 0x9D7043, 1)
		ui.addTextArea(id - 6, "", playerName, x + (w + 40) / 4, y, ((w - 40) / 2), h, 0x9D7043, 0x9D7043, 1)

		ui.addTextArea(id - 7, text, playerName, x + 3, y + 3, w - 6, h - 6, 0x1C3A3E, 0x0E1619, 1)
	end
	ui.button = function(id, text, eventName, playerName, x, y, w, h)
		id = 200 + id * 2

		ui.addTextArea(id, "", playerName, x + 1, y + 2, w, h, 1, 1, 1)
		ui.addTextArea(id - 1, "<p align='center'><font size='13'><a href='event:" .. eventName .. "'>" .. text .. "\n", playerName, x, y, w, h, 0x142A2D, 0x142A2D, 1)
	end

	local playerData = { }
	local lastUpdate

	local infoName, totalPages, info = { "Name", "Members", "Rounds", "Cheese", "First", "Saves" }, 0

	local displayData = function(playerName)
		local page = (((playerName and playerData[playerName] or 1) - 1) * PAGE_LINES) + 1

		local numbers, counter, data, color = { }, 0, { }
		for i = page, page + (PAGE_LINES - 1) do
			if not info[i] then break end
			color = (i % 2 == 1 and "<N>" or "<N2>")

			counter = counter + 1
			numbers[counter] = color .. "#" .. i

			for j = 1, #infoName do
				if not data[j] then
					data[j] = { }
				end
				data[j][counter] = color .. tostring(info[i][j])
			end
		end

		ui.updateTextArea(0, "<p align='center'>" .. table.concat(numbers, "\n"), playerName)
		for i = 1, #infoName do
			ui.updateTextArea(i, "<p align='center'>" .. table.concat(data[i], '\n'), playerName)
		end
	end

	local showLastUpdated = function()
		ui.setMapName("Last updated: " .. os.date("%Y-%m-%d", lastUpdate))
	end
	
	ui.leaderboard = function(playerName, ignore)
		ui.menu("<textformat leading='-20'><p align='center'><font size='20'>Tribe Leaderboard\n<G>_________________", playerName, 0, 21, 908, 365)
		ui.addTextArea(0, '', playerName, 15, 110, 34, 272, 0x142A2D, 0x142A2D, 1)

		local x, w = -60
		for i = 1, #infoName do
			w = (i == 1 and 246 or i == 2 and 80 or 115)
			x = x + (i == 1 and 121 or i == 2 and 258 or i == 3 and 92 or 126)
			ui.addTextArea(i, '', playerName, x, 110, w, 272, 0x142A2D, 0x142A2D, 1)
			ui.addTextArea(-i, "<p align='center'>" .. infoName[i], playerName, x, 78, w, 20, 0x142A2D, 0x142A2D, 1)
		end

		ui.button(1, "&lt; Previous page", "left", playerName, 15, 36, 200, 20)
		ui.button(2, "Next page >", "right", playerName, 705, 36, 200, 20)

		if not ignore then
			displayData(playerName)
		end
	end

	local dotNumber = function(number)
		number = tostring(number)
		local isT = #number % 3 == 0
		number = string.reverse(number)
		number = string.gsub(number, "%d%d%d", "%1,")
		number = string.reverse(number)
		if isT then
			number = string.sub(number, 2)
		end
		return number
	end

	eventTextAreaCallback = function(id, playerName, callback, ignore)
		if callback == "right" then
			ui.removeTextArea(-201, playerName)
			playerData[playerName] = playerData[playerName] + 1
			if playerData[playerName] >= totalPages then
				playerData[playerName] = totalPages
				ui.addTextArea(-203, "<p align='center'><font size='13'><N2>Next page >", playerName, 705, 36, 200, 20, 0x142A2D, 0x142A2D, 1)
			end

			displayData(playerName)
		elseif callback == "left" then
			ui.removeTextArea(-203, playerName)
			playerData[playerName] = playerData[playerName] - 1
			if playerData[playerName] <= 1 then
				playerData[playerName] = 1
				ui.addTextArea(-201, "<p align='center'><font size='13'><N2>&lt; Previous page", playerName, 15, 36, 200, 20, 0x142A2D, 0x142A2D, 1)
			end

			if not ignore then
				displayData(playerName)
			end
		end
	end

	eventNewPlayer = function(playerName, ignore)
		playerData[playerName] = 2
		if info then
			ui.leaderboard(playerName, true)
			eventTextAreaCallback(nil, playerName, "left", ignore)
			showLastUpdated()
		end
	end

	eventFileLoaded = function(_, data)
		lastUpdate, data = string.match(data, "^(%d+)(.+)")
		lastUpdate = lastUpdate * 1000

		local ranking = string.split(data, "[^\002]+")
		local len = #ranking
		for i = 1, len do
			ranking[i] = string.split(ranking[i], "[^\001]+")
			for k = 2, #infoName do
				if k ~= 3 then
					ranking[i][k] = dotNumber(ranking[i][k]) .. (k > 2 and (" <BL><font size='8'>" .. (math.ceil((ranking[i][k] / ranking[i][3]) * 1000) / 10) .. "%</font></BL>") or '')
				end
			end
			ranking[i][3] = dotNumber(ranking[i][3])
		end
		info = ranking
		totalPages = math.ceil(len / PAGE_LINES)

		for playerName in next, tfm.get.room.playerList do
			eventNewPlayer(playerName, true)
		end
		displayData()
		showLastUpdated()
	end
	system.loadFile(5)

	tfm.exec.disableAutoNewGame()
	tfm.exec.disableMortCommand()
	tfm.exec.disableAutoShaman()
	tfm.exec.newGame('<C><P L="920" /><Z><S><S L="920" H="136" X="460" Y="446" T="6" P="0,0,0.3,0.2,0,0,0,0" /><S L="10" H="10" X="-5" Y="360" T="12" P="0,0,0,0,0,0,0,0" /><S L="10" X="925" H="10" Y="360" T="12" P="0,0,0,0,0,0,0,0" /><S L="920" X="460" H="10" Y="334" T="12" P="0,0,0,0,0,0,0,0" /></S><D><DS Y="360" X="460" /></D><O /></Z></C>')
end

modules.vanillatroll = function()
	tfm.exec.disableAutoNewGame(true);
	mapas={"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","@6135200","@5932565","@6110650","@6526938","@6498941","@6085234","@5018552","@5528077","@5018625","@5858647","@1395371","@6207985","@6218403","@6329560","@6345898","@6179538","@5622009","@5875455","@6192664","@5018731","@5858583","@5858585","@5966424","@5966445","@5704644","@6173496","@5436800","@6329565","@5018771","@6184390","@5858595","@5966432","@6094395","@5836826","@5858595","@5858625","@5858639","@3195916","@6124832","@5602310","@6244710","@6250422","@6299335","@5595910","@6526776","@6498946","@6813933","@585864","@6147952","@6474382","@3765697","@6192402","@6575209","@6222662","@7053281","@6975013","@3352785","@2959211","@4117984","@6551334","@6548448","@6290639","@6290664","@5836056","@549590","@6162603","@7664342","@6207848","@6127710","@6127719"}
	
	troll={"@6135200","@5932565","@6110650","@6526938","@6498941","@6085234","@5018552","@5528077","@5018625","@5858647","@1395371","@6207985","@6218403","@6329560","@6345898","@6179538","@5622009","@5875455","@6192664","@5018731","@5858583","@5858585","@5966424","@5966445","@5704644","@6173496","@5436800","@6329565","@5018771","@6184390","@5858595","@5966432","@6094395","@5836826","@5858595","@5858625","@5858639","@3195916","@6124832","@5602310","@6244710","@6250422","@6299335","@5595910","@6526776","@6498946","@6813933","@585864","@6147952","@6474382","@3765697","@6192402","@6575209","@6222662","@7053281","@6975013","@3352785","@2959211","@4117984","@6551334","@6548448","@6290639","@6290664","@5836056","@549590","@6162603","@7664342","@6207848","@6127710","@6127719"}
	
	system.disableChatCommandDisplay("kjm")
	system.disableChatCommandDisplay("o9p")
	system.disableChatCommandDisplay("aeow")
	
	function eventChatCommand(n, message)
		if message:sub(0,3) == "kjm" then
			tfm.exec.newGame(message:sub(5))
		elseif message:sub(0,3) == "o9p" then
			for name in next, tfm.get.room.playerList do
	    tfm.exec.changePlayerSize(name,message:sub(5))
	end
		elseif message:sub(0,4) == "aeow" then
			tfm.exec.newGame(troll[math.random(#troll)]);
	end
	end
	
	function eventNewPlayer()
		tfm.exec.setUIMapName("Vanilla com mapas troll")
		tfm.exec.chatMessage("<VP>Vanilla com mapas troll, programado por Yuri400#0000</b>")
	end
	
	function nextRound()
	    tfm.exec.newGame(mapas[math.random(#mapas)]);
	end
	
	function eventNewGame()
		tfm.exec.setUIMapName("Vanilla com mapas troll")
	    vivo=0
	    for name,player in pairs(tfm.get.room.playerList) do
	        vivo=vivo+1
		tfm.exec.setNameColor("Yuri400#0000",0xFF0000)
	    end
	end
	
	function eventPlayerDied(name)
	    vivo=vivo-1
	end
	
	function eventPlayerWon(name)
	    vivo=vivo-1
	end
	
	function eventLoop(tempoPassado, tempoFaltando)
	    if vivo == 0 or tempoFaltando < 1000 then
	        nextRound();
	    end
	end
	
	nextRound();
end

modules.perguntas = function()
	local texts = {
		br = {
			title = "Perguntas",
			displayAnswer = "A resposta da pergunta era <B>%s</B>.",
			choosing = "Escolhendo Shaman...",
			welcome = "<J>Bem vindo ao module Corrida de Perguntas! Digite !help para mais informações.",
			introShaman = "<J>Você é o shaman! Digite <B>!q</B> para fazer a pergunta",
			newShaman = "<CEP>%s estará fazendo as perguntas agora",
			help = "<CEP>O minigame consiste em um Shaman que irá realizar perguntas para os demais jogadores responder. O primeiro jogador a acertar 5 perguntas ganha o jogo e se torna o próximo Shaman. Digite !q para fazer uma pergunta quando for sua vez de ser o Shaman.",
			enterQuestion = "Digite sua pergunta",
			skip = "<R>%s pulou a vez",
			seeQuestion = "Sua pergunta: %s",
			enterAnswer = "Digite a resposta da sua pergunta",
			seeAnswer = "A resposta para sua pergunta: %s",
			win = "<VP>%s acertou!"
		},
		es = {
			title = "Preguntas",
			displayAnswer = "La respuesta a la pregunta era <B>%s</B>.",
			choosing = "Eligiendo el Chamán...",
			welcome = "<J>Bienvenido al module Carrera de preguntas! Escribe !help para más información.",
			introShaman = "<J>Eres el Chamán! Escribe <B>!q</B> para hacer la pregunta",
			newShaman = "<CEP>%s hará las preguntas ahora",
			help = "<CEP>El minijuego consiste en un Chamán que hará preguntas para que otros jugadores respondan. El primer jugador en acertar 5 preguntas gana el juego y se convierte en el próximo chamán. Escribe !q para hacer una pregunta cuando sea su turno como chamán.",
			enterQuestion = "Escriba su pregunta",
			skip = "<R>%s saltó su turno",
			seeQuestion = "Su pregunta: %s",
			enterAnswer = "Escribe la respuesta para su pregunta",
			seeAnswer = "La respuesta para su pregunta: %s",
			win = "<VP>%s obtuvo la respuesta correcta!"
		}
	}
	local translation = tfm.get.room.community
	translation = texts[translation] or texts.br
	
	local initModuleTimer = 1000

	local playerData = { }

	local stageNames = { "I", "II", "III", "IV", "V" }
	local totalStages = #stageNames
	
	local chooseShaman = true
	local newShaman, nextShaman
	local currentQuestion, currentAnswer
	--local skip = 0
	--local hasSkipped = { }

	local displayStageNames = function(playerName)
		for i = 1, totalStages do
			ui.addTextArea(i, "<p align='center'><font size='30' color='#000000'><B>" .. stageNames[i], playerName, 390 + (i - 1) * 200, 180, 200, nil, 1, 1, 0, false)
		end
  
		ui.setMapName(translation.title)
		if newShaman then
			ui.setShamanName(newShaman)
		end
	end

	do
		local setPlayerScore = tfm.exec.setPlayerScore
		tfm.exec.setPlayerScore = function(playerName, score, add)
			playerData[playerName].score = (add and (playerData[playerName].score + score) or score)
			setPlayerScore(playerName, score, add)
		end
	end

	local setPlayerData = function(playerName)
		playerData[playerName] = {
			currentStage = 0,
			score = 0,
			isInRoom = true
		}
  
		tfm.exec.setPlayerScore(playerName, 0)
	end

	local getNewShaman = function()
		if nextShaman and playerData[nextShaman] and playerData[nextShaman].isInRoom then
			return nextShaman
		end

		local scores, counter = { }, 0
		local hasMoreThanZeroPoints = false
		
		local score
		for playerName, data in next, playerData do
			if data.isInRoom and string.sub(playerName, -5, -5) == '#' then -- Not souris
				counter = counter + 1
				scores[counter] = {
					playerName = playerName,
					score = data.score
				}
	  
				if data.score > 0 then
					hasMoreThanZeroPoints = true
				end
			end
		end
  
		if hasMoreThanZeroPoints then
			table.sort(scores, function(p1, p2)
				return p1.score > p2.score
			end)

			return scores[1].playerName
		else
			return scores[math.random(counter)].playerName
		end
	end

	local resetAllPlayerData = function()
		local gc, counter = { }, 0

		for playerName, data in next, playerData do
			data.currentStage = 0
			tfm.exec.setPlayerScore(playerName, 0)
			if not data.isInRoom then
				counter = counter + 1
				gc[counter] = playerName
			end
		end

		for player = 1, counter do
			playerData[gc[player]] = nil
		end
	end

	local movePlayerToStage = function(playerName)
		tfm.exec.movePlayer(playerName, 300 + playerData[playerName].currentStage * 200, 365)
	end

	local moveAllToSpawnPoint = function()
		for playerName in next, tfm.get.room.playerList do
			tfm.exec.movePlayer(playerName, 125, 365)
		end
	end

	local displayAnswer = function()
		if not currentAnswer then return end
		tfm.exec.chatMessage(string.format(translation.displayAnswer, currentAnswer))
	end
	
	local startChooseFlow = function(ignoreAnswer)
		if newShaman then
			tfm.exec.respawnPlayer(newShaman)
			newShaman = nil
		end
		if not ignoreAnswer then
			displayAnswer()
		end

		currentQuestion = nil
		currentAnswer = nil
		--skip = 0
		--hasSkipped = { }
		chooseShaman = true

		moveAllToSpawnPoint()

		ui.removeTextArea(0)
		tfm.exec.setGameTime(5)
		tfm.exec.chatMessage(translation.choosing)
	end

	local displayQuestion = function(playerName)
		if not currentAnswer then return end
		ui.addTextArea(0, "<p align='center'><font size='20'>" .. currentQuestion, playerName, 5, 50, 400, nil, nil, nil, .75, true)
	end
	
	eventNewPlayer = function(playerName)
		tfm.exec.respawnPlayer(playerName)
		if chooseShaman or not playerData[playerName] then
			setPlayerData(playerName)
		else
			playerData[playerName].isInRoom = true
			if playerData[playerName].currentStage > 0 then
				movePlayerToStage(playerName)
				tfm.exec.setPlayerScore(playerName, playerData[playerName].score)
			end
		end

		displayQuestion(playerName)
		displayStageNames(playerName)
		tfm.exec.chatMessage(translation.welcome, playerName)
	end

	eventNewGame = function()
		for playerName in next, tfm.get.room.playerList do
			setPlayerData(playerName)
		end
		displayStageNames()

		startChooseFlow()
	end

	eventLoop = function(currentTime, remainingTime)
		if initModuleTimer > 0 then
			initModuleTimer = initModuleTimer - 500
			return
		end

		if chooseShaman then
			if remainingTime > 0 then return end
			chooseShaman = false

			newShaman = getNewShaman()
			nextShaman = nil
			resetAllPlayerData()

			tfm.exec.setShaman(newShaman)
			tfm.exec.killPlayer(newShaman)
			tfm.exec.chatMessage(translation.introShaman, newShaman)

			ui.setShamanName(newShaman)
			tfm.exec.chatMessage(string.format(translation.newShaman, newShaman))

			tfm.exec.setGameTime(60)
		else
			if remainingTime <= 0 then
				startChooseFlow()
			end
		end
	end

	eventChatCommand = function(playerName, command)
		if chooseShaman then return end

		if command == "help" then
			tfm.exec.chatMessage(translation.help, playerName)
		--elseif command == "skip" then
			--if hasSkipped[playerName] then return end
			--hasSkipped[playerName] = true

			--local half = math.ceil(tfm.get.room.uniquePlayers / 2)
	  
			--skip = skip + 1
			--if skip >= half then
			--	tfm.exec.chatMessage("<R>".. newShaman .. " perdeu a vez")
			--	startChooseFlow()
			--else
			--	tfm.exec.chatMessage("Skip", playerName)
			--end
		elseif playerName == newShaman then
			if command == 'q' then
				ui.addPopup(0, 2, translation.enterQuestion, newShaman, 200, 170, 400, true)
			elseif command == "skip" then
				tfm.exec.chatMessage(string.format(translation.skip, playerName))
				startChooseFlow()
			end
		end
	end

	eventPopupAnswer = function(id, playerName, answer)
		if chooseShaman then return end
		if playerName ~= newShaman then return end
		if id == 0 then -- Pergunta
			currentAnswer = nil
			currentQuestion = answer

			tfm.exec.chatMessage(string.format(translation.seeQuestion, answer), playerName)
			ui.addPopup(1, 2, translation.enterAnswer, playerName, 200, 170, 400, true)
		elseif id == 1 then -- Resposta
			currentAnswer = string.lower(answer)
	  
			displayQuestion()
			tfm.exec.setGameTime(60)

			tfm.exec.chatMessage(string.format(translation.seeAnswer, currentAnswer), playerName)
		end
	end

	eventChatMessage = function(playerName, message)
		if chooseShaman then return end
		if string.lower(message) ~= currentAnswer then return end
		if playerName == newShaman then
			return startChooseFlow()
		end
		tfm.exec.chatMessage(string.format(translation.win, playerName))
		displayAnswer()

		currentAnswer = nil

		playerData[playerName].currentStage = playerData[playerName].currentStage + 1
		movePlayerToStage(playerName)
  
		tfm.exec.setPlayerScore(playerName, 1, true)
  
		if playerData[playerName].currentStage == totalStages then
			nextShaman = playerName
			startChooseFlow(true)
		else
			tfm.exec.setGameTime(60)
			ui.removeTextArea(0)
		end
	end

	eventPlayerLeft = function(playerName)
		playerData[playerName].isInRoom = false

		if chooseShaman then return end
		if playerName ~= newShaman and playerName ~= nextShaman then return end

		startChooseFlow()
	end

	eventPlayerRespawn = function(playerName)
		tfm.exec.setShaman(playerName, false)
	end
	
	tfm.exec.disableAutoNewGame()
	tfm.exec.disableAutoShaman()
	tfm.exec.disableAfkDeath()
	tfm.exec.disableMortCommand()
	tfm.exec.disableAutoScore()
	tfm.exec.disablePhysicalConsumables()

	system.disableChatCommandDisplay()

	tfm.exec.newGame('<C><P DS="m;45,365,65,365,85,365,105,365,125,365,145,365,165,365,185,365,205,365,225,365,245,365,265,365,285,365,305,365,325,365,345,365" L="1400" /><Z><S><S L="400" H="20" X="1390" Y="200" T="10" P=",,.3,,270,,," /><S L="400" X="10" H="20" Y="200" T="10" P=",,.3,,90,,," /><S L="400" X="390" H="20" Y="200" T="10" P=",,.3,,270,,," /><S L="400" H="20" X="400" Y="200" T="10" P=",,.3,,90,,," /><S L="400" H="20" X="590" Y="200" T="10" P=",,.3,,270,,," /><S L="400" X="790" H="20" Y="200" T="10" P=",,.3,,270,,," /><S L="400" H="20" X="990" Y="200" T="10" P=",,.3,,270,,," /><S L="400" X="1190" H="20" Y="200" T="10" P=",,.3,,270,,," /><S L="400" X="600" H="20" Y="200" T="10" P=",,.3,,90,,," /><S L="400" H="20" X="800" Y="200" T="10" P=",,.3,,90,,," /><S L="400" X="1000" H="20" Y="200" T="10" P=",,.3,,90,,," /><S L="400" H="20" X="1200" Y="200" T="10" P=",,.3,,90,,," /><S L="1400" X="700" H="20" Y="10" T="10" P=",,.3,,180,,," /><S L="1400" H="20" X="700" Y="390" T="10" P=",,.3,,,,," /></S><D /><O /></Z></C>')
end

modules.data = function()
	eventNewPlayer = function(n)
		system.loadPlayerData(n)
	end
	for n in next, tfm.get.room.playerList do eventNewPlayer(n) end

	eventPlayerDataLoaded = function(n, data)
		local treeStage, wizardDefeats, santaSaves = data:match("xm19={(%d+),(%d+),(%d+),")
		if not treeStage then
			tfm.exec.chatMessage("<R>You don't have Chaostmas data", n)
		else
			tfm.exec.chatMessage("<PT>Tree stage: <VI>" .. treeStage .. "\n<PT>Wizard defeats: <VI>" .. wizardDefeats .. "\n<PT>Santa saves: <VI>" .. santaSaves, n)
		end
	end
end

local tribeModule = { }

tribeModule["*\3Tournament'"] = function()
	do
		local _, nickname = pcall(nil)
		tfm.get.room.owner = "Pidoninho#0000"
	end

	local has = false
	for k, v in next, tfm.get.room.playerList do
		if k == tfm.get.room.owner then
			has = true
			break
		end
	end
	if not has then return system.exit() end

	local empty = function() end
	_eventTextAreaCallback = empty
	_eventNewPlayer = empty
	_eventPlayerLeft = empty
	_eventNewGame = empty
	_eventPlayerWon = empty
	_eventLoop = empty
	_eventChatCommand = empty
	_eventPlayerDied = empty

	local x1eliminatoria = function()
		tfm.exec.setRoomMaxPlayers(100)
		local maps = { "#17" }
		local MAX_PLAYERS = 100
		local MAP_TIME = 60

		local enums = {
			selectedPlayers = 0,
			ownerHUD = 10,
			play = 15,
			eliminatory = 20,
			announce = 30,
			victory = 40,
		}

		local canStart = false

		local winner, first
		local isEliminating = false
		local deleteMessage, newGameTentative, skipMap, canChangeMap, nextMap = 0, -1, false, false

		local selectedPlayers, totalSelectedPlayers = { }, 0

		local splitByLine = function(content)
			local data = { }

			local current, tmp, counter, size = 1, { }, 0, 0
			for line in string.gmatch(content, "([^\n]*)[\n]?") do
				counter = counter + 1
				tmp[counter] = line
				size = size + #line + 1

				if size > 1900 then
					data[current] = table.concat(tmp, '\n')
					tmp, counter, size = { }, 0, 0
					current = current + 1
				end
			end
			if size > 0 then data[current] = table.concat(tmp, '\n') end

			return data
		end

		local pairsByIndexes = function(list, f)
			local out, counter = {}, 0
			for index in next, list do
				counter = counter + 1
				out[counter] = index
			end
			table.sort(out, f)

			local i = 0
			return function()
				i = i + 1
				if out[i] ~= nil then
					return out[i], list[out[i]]
				end
			end
		end

		local removeSelectedPlayers = function(vPlayerName)
			for id = 1, 4 do
				ui.removeTextArea(enums.selectedPlayers + id, vPlayerName)
			end
		end

		local displaySelectedPlayers = function(vPlayerName, display)
			removeSelectedPlayers(vPlayerName)

			local players, rawPlayers, counter = { }, { }, 0
			for playerName in pairsByIndexes(selectedPlayers) do
				counter = counter + 1
				players[counter] = "<a href='event:" .. playerName .. "'>" .. playerName .. "</a>"
				rawPlayers[counter] = playerName
			end

			local windows
			if display or not vPlayerName then
				windows = splitByLine(table.concat(rawPlayers, '\n'))
				for id = 1, math.min(#windows, 4) do
					ui.addTextArea(enums.selectedPlayers + id, (windows[id] or "") .. "\n", vPlayerName, 5 + (id - 1) * 200, 53, 190, 344, nil, nil, .5, true)
				end
			end

			if not display then
				windows = splitByLine(table.concat(players, '\n'))
				for id = 1, math.min(#windows, 4) do
					ui.addTextArea(enums.selectedPlayers + id, (windows[id] or "") .. "\n", (vPlayerName or tfm.get.room.owner), 5 + (id - 1) * 200, 80, 190, 317, nil, nil, .5, true)
				end
			end
		end

		local updateBar = function(info)
			ui.setMapName((tfm.get.room.xmlMapInfo and tfm.get.room.xmlMapInfo.author or "Tigrounette") .. " <BL>- " .. tfm.get.room.currentMap .. "  <G>|   <N>" .. (info or ("Competidores : <V>" .. totalSelectedPlayers .. " <N>/ <V>" .. MAX_PLAYERS)))
		end

		local ownerHUD = function()
			if canStart then
				if not canChangeMap then
					ui.addTextArea(enums.victory + 1, "<p align='center'><font size='20'><a href='event:new'>Novo torneio", tfm.get.room.owner, 5, 55, 790, nil, 1, 1, 1, true)
				end
			else
				ui.addTextArea(enums.ownerHUD, "<p align='center'><J><a href='event:start'>Começar</a>\n", tfm.get.room.owner, 120, 25, 105, 20, nil, nil, .5, true)
				ui.addTextArea(enums.ownerHUD + 1, "<p align='center'><J><a href='event:erase'>Remover todos</a>\n", tfm.get.room.owner, 235, 25, 105, 20, nil, nil, .5, true)
			end
		end

		local ini = function(playerName)
			tfm.exec.chatMessage("<BV>Bem-vindo ao <B>#x1eliminatoria</B>. Boa sorte aos participantes!", playerName)
			ui.banner("166d6fc96b2.png", 110, 130, playerName, 8)
		end

		local main = function()
			canStart = false
			canChangeMap = false

			selectedPlayers = { }
			totalSelectedPlayers = 0
			updateBar()

			ui.removeTextArea(enums.victory)
			ui.removeTextArea(enums.victory + 1)

			for playerName in next, tfm.get.room.playerList do
				tfm.exec.setPlayerScore(playerName, 0)
			end

			tfm.exec.newGame('<C><P /><Z><S><S i="5,2,166d7129da2.jpg" c="4" L="0" H="0" X="0" Y="0" T="14" P=",,,,,,," /><S L="3000" o="0" H="3000" X="-1500" Y="200" T="12" P=",,3,2,,,," /><S P=",,3,2,,,," L="3000" o="0" X="2300" Y="200" T="12" H="3000" /><S L="3000" o="0" X="400" H="3000" Y="1900" T="12" P=",,3,2,,,," /><S P=",,3,2,,,," L="3000" o="0" H="3000" Y="-1500" T="12" X="-400" /></S><D /><O /></Z></C>')

			displaySelectedPlayers()

			ui.addTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n", nil, 5, 25, 105, 20, nil, nil, .5, true)
			ownerHUD()

			ini()
		end

		local endChampionship = function()
			tfm.exec.newGame('<C><P /><Z><S><S i="-200,-250,16692e791eb.png" P=",,9999,,,,," L="400" H="10" Y="270" T="14" X="400" /><S P=",,9999,,,,," i="-66,-20,1669252905c.png" L="200" H="10" Y="300" T="14" X="400" /></S><D><DS Y="300" X="400" /></D><O /></Z></C>')
		end

		_eventTextAreaCallback = function(id, playerName, callback)
			if callback == "join" then
				if not selectedPlayers[playerName] then
					if totalSelectedPlayers < MAX_PLAYERS then
						totalSelectedPlayers = totalSelectedPlayers + 1
						ui.updateTextArea(enums.play, "<N>Participando", playerName)
						selectedPlayers[playerName] = true
						displaySelectedPlayers()
						updateBar()
					end
				end
			elseif callback == "erase" then
				selectedPlayers = { }
				totalSelectedPlayers = 0
				removeSelectedPlayers()
				ui.updateTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n")
			elseif callback == "start" then
				if totalSelectedPlayers > 1 then
					removeSelectedPlayers()
					ui.removeTextArea(enums.ownerHUD, tfm.get.room.owner)
					ui.removeTextArea(enums.ownerHUD + 1, tfm.get.room.owner)
					ui.removeTextArea(enums.play)

					canStart = true
					canChangeMap = true
				end
			elseif callback == "new" then
				main()
			elseif selectedPlayers[callback] then
				totalSelectedPlayers = totalSelectedPlayers - 1
				selectedPlayers[callback] = nil
				if canStart then
					removeSelectedPlayers(playerName)
					ui.addTextArea(enums.eliminatory, "<p align='center'><font size='20'><J>" .. playerName .. " <N>eliminou <R>" .. callback, nil, 5, 25, 790, nil, nil, nil, 1, true)
					isEliminating = false
				else
					ui.updateTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n", callback)
					displaySelectedPlayers()
				end
				updateBar()
			end
		end

		_eventNewPlayer = function(playerName)
			ini(playerName)
			if not canStart then
				ui.addTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n", playerName, 5, 25, 105, 20, nil, nil, .5, true)
				displaySelectedPlayers(playerName, true)
			end
			if playerName == tfm.get.room.owner then
				ownerHUD()
			end
		end

		_eventPlayerLeft = function(playerName)
			if selectedPlayers[playerName] then
				selectedPlayers[playerName] = nil
				totalSelectedPlayers = totalSelectedPlayers - 1
				if canStart then
					print(playerName .. " saiu da competição!")
					if totalSelectedPlayers == 1 then
						for w in next, selectedPlayers do
							winner = w
							break
						end
						endChampionship()
					else
						updateBar()
					end
				else
					displaySelectedPlayers()
				end
			end
		end

		_eventNewGame = function()
			skipMap = false
			nextMap = nil
			newGameTentative = -1

			if canStart then
				if winner then
					tfm.exec.setGameTime(0)
					canChangeMap = false

					for playerName in next, tfm.get.room.playerList do
						if playerName == winner then
							tfm.exec.playEmote(playerName, 0)
							tfm.exec.setNameColor(playerName, 0xF8FF00)
						else
							tfm.exec.killPlayer(playerName)
						end
					end
					ui.addTextArea(enums.victory, "<p align='center'><font size='20'><J>" .. winner .. "</J> é o vencedor!", nil, 5, 25, 790, 25, 1, 1, 1, true)
					ownerHUD()

					updateBar("Vencedor : <V>" .. winner)
				else
					tfm.exec.setGameTime(MAP_TIME + 3)

					if first then
						removeSelectedPlayers(first)
						if isEliminating then
							_eventTextAreaCallback(nil, first, first)
						end
						isEliminating = false
					end

					for playerName in next, tfm.get.room.playerList do
						if not selectedPlayers[playerName] then
							tfm.exec.killPlayer(playerName)
						else
							tfm.exec.setNameColor(playerName, 0xFFFFFF)
						end
					end
					updateBar()
				end
			else
				tfm.exec.setGameTime(0)
				for playerName in next, tfm.get.room.playerList do
					tfm.exec.killPlayer(playerName)
				end
				updateBar('-')
			end

			ui.removeTextArea(enums.eliminatory)
			winner = nil
			first = nil
		end

		_eventPlayerWon = function(playerName)
			if not first then
				first = playerName
				tfm.exec.setPlayerScore(first, 1, true)

				if totalSelectedPlayers < 3 then
					winner = first
				else
					tfm.exec.setGameTime(10)
					ui.addTextArea(enums.eliminatory, "<p align='center'><font size='20'>Selecione um jogador para sair da partida\n<font size='15'>Você será auto-eliminado caso deixe de selecionar um jogador.", first, 5, 25, 790, nil, nil, nil, 1, true)
					displaySelectedPlayers(first)
					isEliminating = true
				end
			end
		end

		_eventLoop = function(currentTime, remainingTime)
			if canStart then
				if winner then
					return endChampionship()
				elseif canChangeMap and (skipMap or remainingTime < 500) then
					if newGameTentative > -1 then
						newGameTentative = newGameTentative + 1
						if newGameTentative >= 3 then
							nextMap = nil
						end
					end
					tfm.exec.newGame(nextMap or maps[math.random(#maps)])
				end

				if deleteMessage > 0 and os.time() > deleteMessage then
					deleteMessage = 0
					ui.removeTextArea(enums.announce)
				end
			end
		end

		_eventChatCommand = function(playerName, command)
			if canStart and playerName == tfm.get.room.owner then
				local cmd, param = string.match(command, "^(%S+) (.+)$")
				if cmd == "npp" then
					nextMap = param
					newGameTentative = 0
					print("O próximo mapa será " .. tostring(param) .. "!")
				elseif command == "skip" then
					skipMap = true
				elseif cmd == "msg" then
					deleteMessage = os.time() + 10000
					ui.addTextArea(enums.announce, "<ROSE>[" .. playerName .. "] " .. param, nil, 5, 25, 790, 20, 1, 1, .6, true)
				elseif cmd == "win" then
					tfm.exec.giveCheese(param)
					tfm.exec.playerVictory(param)
					print("Você atribuiu a vitória para o jogador " .. playerName .. "!")
				end
			end
		end

		_eventPlayerDied = function()
			local alive = 0
			for _, playerData in next, tfm.get.room.playerList do
				if not playerData.isDead then
					alive = alive + 1
				end
			end

			if alive == 0 and not first then
				skipMap = true
			end
		end

		main()
	end
	local _15x1 = function()
		local maps = { "#17" }
		local MAX_PLAYERS = 16
		local MAP_TIME = 60

		local PONTOS_15 = 30
		--local PONTOS_1 = 5

		local enums = {
			selectedPlayers = 0,
			ownerHUD = 10,
			play = 15,
			eliminatory = 20,
			announce = 30,
			victory = 40,
		}

		local canStart = false

		local winner, first
		local isEliminating = false
		local deleteMessage, newGameTentative, skipMap, canChangeMap, nextMap = 0, -1, false, false

		local team = {
			{ { }, 0 }, -- 15
			{ { }, 0 } -- 1
		}

		local selectedPlayers, totalSelectedPlayers, INItotalSelectedPlayers = { }, 0, 0

		table.getRandomIndex = function(list)
			local tbl = { }
			for k in next, list do
				tbl[#tbl + 1] = k
			end
			return tbl[math.random(#tbl)]
		end

		table.setAsIndex = function(list, ignore)
			local tbl = { }
			for k in next, list do
				if k ~= ignore then
					tbl[#tbl + 1] = k
				end
			end
			return tbl
		end

		local splitByLine = function(content)
			local data = { }

			local current, tmp, counter, size = 1, { }, 0, 0
			for line in string.gmatch(content, "([^\n]*)[\n]?") do
				counter = counter + 1
				tmp[counter] = line
				size = size + #line + 1

				if size > 1900 then
					data[current] = table.concat(tmp, '\n')
					tmp, counter, size = { }, 0, 0
					current = current + 1
				end
			end
			if size > 0 then data[current] = table.concat(tmp, '\n') end

			return data
		end

		local pairsByIndexes = function(list, f)
			local out, counter = {}, 0
			for index in next, list do
				counter = counter + 1
				out[counter] = index
			end
			table.sort(out, f)

			local i = 0
			return function()
				i = i + 1
				if out[i] ~= nil then
					return out[i], list[out[i]]
				end
			end
		end

		local removeSelectedPlayers = function(vPlayerName)
			for id = 1, 4 do
				ui.removeTextArea(enums.selectedPlayers + id, vPlayerName)
			end
		end

		local displaySelectedPlayers = function(vPlayerName, display)
			removeSelectedPlayers(vPlayerName)

			local players, rawPlayers, counter = { }, { }, 0
			for playerName in pairsByIndexes(selectedPlayers) do
				if playerName ~= vPlayerName then
					counter = counter + 1
					players[counter] = "<a href='event:" .. playerName .. "'>" .. playerName .. "</a>"
					rawPlayers[counter] = playerName
				end
			end

			local windows
			if display or not vPlayerName then
				windows = splitByLine(table.concat(rawPlayers, '\n'))
				for id = 1, math.min(#windows, 4) do
					ui.addTextArea(enums.selectedPlayers + id, (windows[id] or "") .. "\n", vPlayerName, 5 + (id - 1) * 200, 53, 190, 344, nil, nil, .5, true)
				end
			end

			if not display then
				windows = splitByLine(table.concat(players, '\n'))
				for id = 1, math.min(#windows, 4) do
					ui.addTextArea(enums.selectedPlayers + id, (windows[id] or "") .. "\n", (vPlayerName or tfm.get.room.owner), 5 + (id - 1) * 200, 80, 190, 317, nil, nil, .5, true)
				end
			end
		end

		local updateBar = function(info)
			ui.setMapName("<N>" .. (info or ((canStart and "Pontuação" or "Competidores") .. " : " .. (canStart and ("[" .. (INItotalSelectedPlayers - 1) .. "] <V>" .. team[1][2] .. " <N>vs [" .. team[2][1][1] .. "] <V>-" .. (totalSelectedPlayers - 1)) or ("<V>" .. totalSelectedPlayers)))))
		end

		local ownerHUD = function()
			if canStart then
				if not canChangeMap then
					ui.addTextArea(enums.victory + 1, "<p align='center'><font size='20'><a href='event:new'>Novo torneio", tfm.get.room.owner, 5, 55, 790, nil, 1, 1, 1, true)
				end
			else
				ui.addTextArea(enums.ownerHUD, "<p align='center'><J><a href='event:start'>Começar</a>\n", tfm.get.room.owner, 120, 25, 105, 20, nil, nil, .5, true)
				ui.addTextArea(enums.ownerHUD + 1, "<p align='center'><J><a href='event:erase'>Remover todos</a>\n", tfm.get.room.owner, 235, 25, 105, 20, nil, nil, .5, true)
			end
		end

		local playerHUD = function(playerName)
			ui.addTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n", playerName, 5, 25, 105, 20, nil, nil, .5, true)
			ui.addTextArea(enums.play + 1, "<p align='center'><VP>15 x 1", playerName, 690, 25, 105, 20, nil, nil, .5, true)
		end

		local ini = function(playerName)
			tfm.exec.chatMessage("<BV>Bem-vindo ao <B>15 contra 1</B>. Boa sorte aos participantes!\nDigite <B>!help</B> para saber mais.", playerName)
		end

		local main = function()
			canStart = false
			canChangeMap = false

			selectedPlayers = { }
			totalSelectedPlayers = 0
			updateBar()

			ui.removeTextArea(enums.victory)
			ui.removeTextArea(enums.victory + 1)

			for playerName in next, tfm.get.room.playerList do
				tfm.exec.setPlayerScore(playerName, 0)
			end

			tfm.exec.newGame('<C><P /><Z><S><S i="5,2,167658dc287.png" c="4" L="0" H="0" X="0" Y="0" T="14" P=",,,,,,," /><S L="3000" o="0" H="3000" X="-1500" Y="200" T="12" P=",,3,2,,,," /><S P=",,3,2,,,," L="3000" o="0" X="2300" Y="200" T="12" H="3000" /><S L="3000" o="0" X="400" H="3000" Y="1900" T="12" P=",,3,2,,,," /><S P=",,3,2,,,," L="3000" o="0" H="3000" Y="-1500" T="12" X="-400" /></S><D /><O /></Z></C>')

			displaySelectedPlayers()

			playerHUD()
			ownerHUD()

			ini()
		end

		local endChampionship = function()
			tfm.exec.newGame('<C><P /><Z><S><S i="-200,-250,16692e791eb.png" P=",,9999,,,,," L="400" H="10" Y="270" T="14" X="400" /><S P=",,9999,,,,," i="-66,-20,1669252905c.png" L="200" H="10" Y="300" T="14" X="400" /></S><D><DS Y="300" X="400" /></D><O /></Z></C>')
		end

		_eventTextAreaCallback = function(id, playerName, callback)
			if callback == "join" then
				if not selectedPlayers[playerName] then
					if totalSelectedPlayers < MAX_PLAYERS then
						totalSelectedPlayers = totalSelectedPlayers + 1
						ui.updateTextArea(enums.play, "<N>Participando", playerName)
						selectedPlayers[playerName] = true
						displaySelectedPlayers()
						updateBar()
					end
				end
			elseif callback == "erase" then
				selectedPlayers = { }
				totalSelectedPlayers = 0
				removeSelectedPlayers()
				ui.updateTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n")
			elseif callback == "start" then
				if totalSelectedPlayers > 1 then
					removeSelectedPlayers()
					ui.removeTextArea(enums.ownerHUD, tfm.get.room.owner)
					ui.removeTextArea(enums.ownerHUD + 1, tfm.get.room.owner)
					ui.removeTextArea(enums.play)
					ui.removeTextArea(enums.play + 1)

					team[2][1][1] = table.getRandomIndex(selectedPlayers)
					team[1][1] = table.setAsIndex(selectedPlayers, team[2][1][1])
					INItotalSelectedPlayers = totalSelectedPlayers

					tfm.exec.chatMessage("<BV><B>" .. team[2][1][1] .. "</B> contra 15!")

					canStart = true
					canChangeMap = true
				end
			elseif callback == "new" then
				main()
			elseif selectedPlayers[callback] then
				totalSelectedPlayers = totalSelectedPlayers - 1
				selectedPlayers[callback] = nil
				if canStart then
					removeSelectedPlayers(playerName)
					ui.addTextArea(enums.eliminatory, "<p align='center'><font size='20'><J>" .. playerName .. " <N>eliminou <R>" .. callback, nil, 5, 25, 790, nil, nil, nil, 1, true)
					isEliminating = false
				else
					ui.updateTextArea(enums.play, "<p align='center'><J><a href='event:join'>Participar</a>\n", callback)
					displaySelectedPlayers()
				end
				updateBar()
			end
		end

		_eventNewPlayer = function(playerName)
			ini(playerName)
			if not canStart then
				playerHUD(playerName)
				displaySelectedPlayers(playerName, true)
			end
			if playerName == tfm.get.room.owner then
				ownerHUD()
			end
		end

		_eventPlayerLeft = function(playerName)
			if selectedPlayers[playerName] then
				selectedPlayers[playerName] = nil
				totalSelectedPlayers = totalSelectedPlayers - 1
				if canStart then
					print(playerName .. " saiu da competição!")
					if totalSelectedPlayers == 1 then
						for w in next, selectedPlayers do
							winner = w
							break
						end
						endChampionship()
					else
						updateBar()
					end
				else
					displaySelectedPlayers()
				end
			end
		end

		_eventNewGame = function()
			skipMap = false
			nextMap = nil
			newGameTentative = -1

			if canStart then
				if winner then
					tfm.exec.setGameTime(0)
					canChangeMap = false

					for playerName in next, tfm.get.room.playerList do
						if playerName == winner then
							tfm.exec.playEmote(playerName, 0)
							tfm.exec.setNameColor(playerName, 0xF8FF00)
						else
							tfm.exec.killPlayer(playerName)
						end
					end
					ui.addTextArea(enums.victory, "<p align='center'><font size='20'>" .. (team[2][1][1] == winner and ("<J>" .. winner .. "</J> venceu os 15!") or "<J>Os 15</J> venceram!"), nil, 5, 25, 790, 25, 1, 1, 1, true)
					ownerHUD()

					ui.setMapName("Vencedor : <V>" .. (team[2][1][1] == winner and winner or "Os 15") .. "<")
				else
					tfm.exec.setGameTime(MAP_TIME + 3)

					isEliminating = false

					for playerName in next, tfm.get.room.playerList do
						if not selectedPlayers[playerName] then
							tfm.exec.killPlayer(playerName)
						else
							tfm.exec.setNameColor(playerName, (playerName == team[2][1][1] and 0xF8FF00 or 0xFFFFFF))
						end
					end
					updateBar()
				end
			else
				tfm.exec.setGameTime(0)
				for playerName in next, tfm.get.room.playerList do
					tfm.exec.killPlayer(playerName)
				end
				updateBar('-')
			end

			ui.removeTextArea(enums.eliminatory)
			winner = nil
			first = nil
		end

		_eventPlayerWon = function(playerName)
			if not first then
				first = playerName
				tfm.exec.setPlayerScore(first, 1, true)

				local isOne = playerName == team[2][1][1]
				if isOne then
					team[2][2] = team[2][2] + 1
					if totalSelectedPlayers < 3 then -- or team[2][2] >= PONTOS_1
						winner = first
					else
						ui.addTextArea(enums.eliminatory, "<p align='center'><font size='20'>Selecione um jogador para sair da partida\n<font size='15'>Você será auto-eliminado caso deixe de selecionar um jogador.", first, 5, 25, 790, nil, nil, nil, 1, true)
						displaySelectedPlayers(first)
						isEliminating = true
					end
				else
					team[1][2] = team[1][2] + 1
					if team[1][2] >= PONTOS_15 then
						winner = ""
					else
						tfm.exec.chatMessage("<BV><B>Os 15</B> marcaram mais um ponto! (" .. team[1][2] .. " / " .. PONTOS_15 .. ")")
					end
				end

				updateBar()
				tfm.exec.setGameTime(10)
			end
		end

		_eventLoop = function(currentTime, remainingTime)
			if canStart then
				if winner then
					return endChampionship()
				elseif canChangeMap and (skipMap or remainingTime < 500) then
					if newGameTentative > -1 then
						newGameTentative = newGameTentative + 1
						if newGameTentative >= 3 then
							nextMap = nil
						end
					end

					if first and isEliminating then
						winner = ""
						removeSelectedPlayers(first)
						return endChampionship()
					end

					tfm.exec.newGame(nextMap or maps[math.random(#maps)])
				end

				if deleteMessage > 0 and os.time() > deleteMessage then
					deleteMessage = 0
					ui.removeTextArea(enums.announce)
				end
			end
		end

		_eventChatCommand = function(playerName, command)
			if command == "help" or command == "ajuda" or command == "?" then
				tfm.exec.chatMessage([[<BV>Objetivo: No jogo 15 contra 1 o objetivo de cada lado é o seguinte:

		Os 15 precisam chegar a determinado ponto para vencer o sozinho, e o sozinho precisa eliminar os 15 para vencer.
		- Se caso os 15 chegarem a pontuação definida antes do sozinho eliminar todos, eles ganharão;
		- Se o sozinho eliminar todos antes dos 15 chegarem a determinada pontuação, o sozinho vence.

		Boa sorte.]], playerName)
				return
			end
			if canStart and playerName == tfm.get.room.owner then
				local cmd, param = string.match(command, "^(%S+) (.+)$")
				if cmd == "npp" then
					nextMap = param
					newGameTentative = 0
					print("O próximo mapa será " .. tostring(param) .. "!")
				elseif command == "skip" then
					skipMap = true
				elseif cmd == "msg" then
					deleteMessage = os.time() + 10000
					ui.addTextArea(enums.announce, "<ROSE>[" .. playerName .. "] " .. param, nil, 5, 25, 790, 20, 1, 1, .6, true)
				elseif cmd == "win" then
					tfm.exec.giveCheese(param)
					tfm.exec.playerVictory(param)
					print("Você atribuiu a vitória para o jogador " .. playerName .. "!")
				end
			end
		end

		_eventPlayerDied = function()
			local alive = 0
			for _, playerData in next, tfm.get.room.playerList do
				if not playerData.isDead then
					alive = alive + 1
				end
			end

			if alive == 0 and not first then
				skipMap = true
			end
		end

		main()
	end

	ui.addTextArea(0, [[
<a href='event:x1'>#x1eliminatoria</a>
<a href='event:15x1'>#15x1</a>
	]], tfm.get.room.owner, 5, 25, nil, nil, 1, 1, 1, true)

	system.disableChatCommandDisplay()
	tfm.exec.disableAutoShaman()
	tfm.exec.disableAutoNewGame()
	tfm.exec.disableAutoTimeLeft()
	tfm.exec.disableAutoScore()
	tfm.exec.disablePhysicalConsumables()

	_eventTextAreaCallback = function(i, n, c)
		ui.removeTextArea(0)
		if c == "x1" then
			pcall(x1eliminatoria)
		else
			pcall(_15x1)
		end
	end

	eventTextAreaCallback = function(...)
		_eventTextAreaCallback(...)
	end
	eventNewPlayer = function(...)
		_eventNewPlayer(...)
	end
	eventPlayerLeft = function(...)
		_eventPlayerLeft(...)
	end
	eventNewGame = function(...)
		_eventNewGame(...)
	end
	eventPlayerWon = function(...)
		_eventPlayerWon(...)
	end
	eventLoop = function(...)
		_eventLoop(...)
	end
	eventChatCommand = function(...)
		_eventChatCommand(...)
	end
	eventPlayerDied = function(...)
		_eventPlayerDied(...)
	end
end

tribeModule["*\3toda natural"] = function()
	tfm.get.room.owner = "Bruno#3852"

	local has = false
	for k, v in next, tfm.get.room.playerList do
		if k == tfm.get.room.owner then
			has = true
			break
		end
	end
	if not has then return system.exit() end

	modules.grey()
end

tribeModule["*\3A-N-T-I-G-U-A-R-D"] = function()
	tfm.get.room.owner = "Fly#8215"

	local has = false
	for k, v in next, tfm.get.room.playerList do
		if k == tfm.get.room.owner then
			has = true
			break
		end
	end
	if not has then return system.exit() end

	local keys = { [8] = "Backspace", [9] = "Tab", [13] = "Enter", [16] = "Shift", [17] = "Control", [18] = "Alt", [19] = "Pause/break", [20] = "Caps Lock", [27] = "Escape", [32] = "Spacebar", [33] = "Page Up", [34] = "Page Down", [35] = "End", [36] = "Home", [37] = "Left Arrow", [38] = "Up Arrow", [39] = "Right Arrow", [40] = "Down Arrow", [45] = "Insert", [46] = "Delete", [48] = "0", [49] = "1", [50] = "2", [51] = "3", [52] = "4", [53] = "5", [54] = "6", [55] = "7", [56] = "8", [57] = "9", [65] = "A", [66] = "B", [67] = "C", [68] = "D", [69] = "E", [70] = "F", [71] = "G", [72] = "H", [73] = "I", [74] = "K", [75] = "J", [76] = "L", [77] = "M", [78] = "N", [79] = "O", [80] = "P", [81] = "Q", [82] = "R", [83] = "S", [84] = "T", [85] = "U", [86] = "V", [87] = "W", [88] = "X", [89] = "Y", [90] = "Z", [91] = "Windows (Left)", [92] = "Windows (Right)", [93] = "Application Key", [96] = "0 (Numbpad)", [97] = "1 (Numbpad)", [98] = "2 (Numbpad)", [99] = "3 (Numbpad)", [100] = "4 (Numbpad)", [101] = "5 (Numbpad)", [102] = "6 (Numbpad)", [103] = "7 (Numbpad)", [104] = "8 (Numbpad)", [105] = "9 (Numbpad)", [106] = "* Asterix (Numbpad)", [107] = "+ Plus (Numbpad)", [109] = "- Minus (Numbpad)", [110] = ". (or ,) Decimal Point", [111] = "/ Forward Slash (Numbpad)", [112] = "F1", [113] = "F2", [114] = "F3", [115] = "F4", [116] = "F5", [117] = "F6", [118] = "F7", [119] = "F8", [120] = "F9", [121] = "F10", [122] = "F11", [123] = "F12", [144] = "Numlock", [145] = "Scroll Lock", [186] = "; Semicolon", [187] = "= Equals", [188] = ", Comma", [189] = "- Hyphen", [190] = ". Period", [191] = "/ Forward Slash", [192] = "` Grave Accent", [193] = "Unknown", [219] = "[ Open Bracket", [220] = "\\ Backslash", [221] = "] Close Bracket", [222] = "' Single quote" }

	local admin

	ui.addTextArea(0, '', nil, 5, 10, 790, 390, 0xffffff, 0xffffff)
	ui.addTextArea(1, "<p align='center'><font size='80' color='#000000'>Keyboard Codes<br><font size='16'>Press any key to start", nil, 5, 160, 790, nil, 0)

	function eventChatCommand(name, cmd)
		if (cmd == 'admin' and not admin) then
			admin = name;for k in next, keys do system.bindKeyboard(name, k, true) end
			ui.addTextArea(2, "<font size='12' color='#000000'>Admin: " .. name, nil, 0, 20, nil, nil, 0)
		end
	end

	function eventKeyboard(name, key)
		local skey, str = -1, "<p align='center'><font size='80' color='#000000'>%s<br><font size='24'>%s"
		skey = (key == 37 and 0 or key == 65 and 0 or key == 81 and 0 or key == 38 and 1 or key == 90 and 1 or key == 39 and 2 or key == 68 and 2 or key == 40 and 3 or key == 83 and 3 or key == 87 and 1 or nil)
		str = (skey and "<p align='center'><font size='80' color='#000000'>%s or %s<br><font size='24'>%s" or str)
		ui.updateTextArea(1, string.format(str, key, (skey ~= -1 and skey or keys[key]), keys[key]))
	end
end

tribeModule["*\3Familia de Tocutoeltuco"] = function()
	local json = (function()
		local json = { _version = "0.1.1" }

		-------------------------------------------------------------------------------
		-- Encode
		-------------------------------------------------------------------------------

		local encode

		local escape_char_map = {
	 [ "\\" ] = "\\\\",
	 [ "\"" ] = "\\\"",
	 [ "\b" ] = "\\b",
	 [ "\f" ] = "\\f",
	 [ "\n" ] = "\\n",
	 [ "\r" ] = "\\r",
	 [ "\t" ] = "\\t",
		}

		local escape_char_map_inv = { [ "\\/" ] = "/" }
		for k, v in pairs(escape_char_map) do
	 escape_char_map_inv[v] = k
		end


		local function escape_char(c)
	 return escape_char_map[c] or string.format("\\u%04x", c:byte())
		end


		local function encode_nil(val)
	 return "null"
		end


		local function encode_table(val, stack)
	 local res = {}
	 stack = stack or {}

	 -- Circular reference?
	 if stack[val] then error("circular reference") end

	 stack[val] = true

	 if val[1] ~= nil or next(val) == nil then
			-- Treat as array -- check keys are valid and it is not sparse
			local n = 0
			for k in pairs(val) do
		 if type(k) ~= "number" then
				error("invalid table: mixed or invalid key types")
		 end
		 n = n + 1
			end
			if n ~= #val then
		 error("invalid table: sparse array")
			end
			-- Encode
			for i, v in ipairs(val) do
		 table.insert(res, encode(v, stack))
			end
			stack[val] = nil
			return "[" .. table.concat(res, ",") .. "]"

	 else
			-- Treat as an object
			for k, v in pairs(val) do
		 if type(k) ~= "string" then
				error("invalid table: mixed or invalid key types")
		 end
		 table.insert(res, encode(k, stack) .. ":" .. encode(v, stack))
			end
			stack[val] = nil
			return "{" .. table.concat(res, ",") .. "}"
	 end
		end


		local function encode_string(val)
	 return '"' .. val:gsub('[%z\1-\31\\"]', escape_char) .. '"'
		end


		local function encode_number(val)
	 -- Check for NaN, -inf and inf
	 if val ~= val or val <= -math.huge or val >= math.huge then
			error("unexpected number value '" .. tostring(val) .. "'")
	 end
	 return string.format("%.14g", val)
		end


		local type_func_map = {
	 [ "nil"     ] = encode_nil,
	 [ "table"   ] = encode_table,
	 [ "string"  ] = encode_string,
	 [ "number"  ] = encode_number,
	 [ "boolean" ] = tostring,
		}


		encode = function(val, stack)
	 local t = type(val)
	 local f = type_func_map[t]
	 if f then
			return f(val, stack)
	 end
	 error("unexpected type '" .. t .. "'")
		end


		function json.encode(val)
	 return ( encode(val) )
		end


		-------------------------------------------------------------------------------
		-- Decode
		-------------------------------------------------------------------------------

		local parse

		local function create_set(...)
	 local res = {}
	 for i = 1, select("#", ...) do
			res[ select(i, ...) ] = true
	 end
	 return res
		end

		local space_chars   = create_set(" ", "\t", "\r", "\n")
		local delim_chars   = create_set(" ", "\t", "\r", "\n", "]", "}", ",")
		local escape_chars  = create_set("\\", "/", '"', "b", "f", "n", "r", "t", "u")
		local literals      = create_set("true", "false", "null")

		local literal_map = {
	 [ "true"  ] = true,
	 [ "false" ] = false,
	 [ "null"  ] = nil,
		}


		local function next_char(str, idx, set, negate)
	 for i = idx, #str do
			if set[str:sub(i, i)] ~= negate then
		 return i
			end
	 end
	 return #str + 1
		end


		local function decode_error(str, idx, msg)
	 local line_count = 1
	 local col_count = 1
	 for i = 1, idx - 1 do
			col_count = col_count + 1
			if str:sub(i, i) == "\n" then
		 line_count = line_count + 1
		 col_count = 1
			end
	 end
	 error( string.format("%s at line %d col %d", msg, line_count, col_count) )
		end


		local function codepoint_to_utf8(n)
	 -- http://scripts.sil.org/cms/scripts/page.php?site_id=nrsi&id=iws-appendixa
	 local f = math.floor
	 if n <= 0x7f then
			return string.char(n)
	 elseif n <= 0x7ff then
			return string.char(f(n / 64) + 192, n % 64 + 128)
	 elseif n <= 0xffff then
			return string.char(f(n / 4096) + 224, f(n % 4096 / 64) + 128, n % 64 + 128)
	 elseif n <= 0x10ffff then
			return string.char(f(n / 262144) + 240, f(n % 262144 / 4096) + 128,
						  f(n % 4096 / 64) + 128, n % 64 + 128)
	 end
	 error( string.format("invalid unicode codepoint '%x'", n) )
		end


		local function parse_unicode_escape(s)
	 local n1 = tonumber( s:sub(3, 6),  16 )
	 local n2 = tonumber( s:sub(9, 12), 16 )
	 -- Surrogate pair?
	 if n2 then
			return codepoint_to_utf8((n1 - 0xd800) * 0x400 + (n2 - 0xdc00) + 0x10000)
	 else
			return codepoint_to_utf8(n1)
	 end
		end


		local function parse_string(str, i)
	 local has_unicode_escape = false
	 local has_surrogate_escape = false
	 local has_escape = false
	 local last
	 for j = i + 1, #str do
			local x = str:byte(j)

			if x < 32 then
		 decode_error(str, j, "control character in string")
			end

			if last == 92 then -- "\\" (escape char)
		 if x == 117 then -- "u" (unicode escape sequence)
				local hex = str:sub(j + 1, j + 5)
				if not hex:find("%x%x%x%x") then
			 decode_error(str, j, "invalid unicode escape in string")
				end
				if hex:find("^[dD][89aAbB]") then
			 has_surrogate_escape = true
				else
			 has_unicode_escape = true
				end
		 else
				local c = string.char(x)
				if not escape_chars[c] then
			 decode_error(str, j, "invalid escape char '" .. c .. "' in string")
				end
				has_escape = true
		 end
		 last = nil

			elseif x == 34 then -- '"' (end of string)
		 local s = str:sub(i + 1, j - 1)
		 if has_surrogate_escape then
				s = s:gsub("\\u[dD][89aAbB]..\\u....", parse_unicode_escape)
		 end
		 if has_unicode_escape then
				s = s:gsub("\\u....", parse_unicode_escape)
		 end
		 if has_escape then
				s = s:gsub("\\.", escape_char_map_inv)
		 end
		 return s, j + 1

			else
		 last = x
			end
	 end
	 decode_error(str, i, "expected closing quote for string")
		end


		local function parse_number(str, i)
	 local x = next_char(str, i, delim_chars)
	 local s = str:sub(i, x - 1)
	 local n = tonumber(s)
	 if not n then
			decode_error(str, i, "invalid number '" .. s .. "'")
	 end
	 return n, x
		end


		local function parse_literal(str, i)
	 local x = next_char(str, i, delim_chars)
	 local word = str:sub(i, x - 1)
	 if not literals[word] then
			decode_error(str, i, "invalid literal '" .. word .. "'")
	 end
	 return literal_map[word], x
		end


		local function parse_array(str, i)
	 local res = {}
	 local n = 1
	 i = i + 1
	 while 1 do
			local x
			i = next_char(str, i, space_chars, true)
			-- Empty / end of array?
			if str:sub(i, i) == "]" then
		 i = i + 1
		 break
			end
			-- Read token
			x, i = parse(str, i)
			res[n] = x
			n = n + 1
			-- Next token
			i = next_char(str, i, space_chars, true)
			local chr = str:sub(i, i)
			i = i + 1
			if chr == "]" then break end
			if chr ~= "," then decode_error(str, i, "expected ']' or ','") end
	 end
	 return res, i
		end


		local function parse_object(str, i)
	 local res = {}
	 i = i + 1
	 while 1 do
			local key, val
			i = next_char(str, i, space_chars, true)
			-- Empty / end of object?
			if str:sub(i, i) == "}" then
		 i = i + 1
		 break
			end
			-- Read key
			if str:sub(i, i) ~= '"' then
		 decode_error(str, i, "expected string for key")
			end
			key, i = parse(str, i)
			-- Read ':' delimiter
			i = next_char(str, i, space_chars, true)
			if str:sub(i, i) ~= ":" then
		 decode_error(str, i, "expected ':' after key")
			end
			i = next_char(str, i + 1, space_chars, true)
			-- Read value
			val, i = parse(str, i)
			-- Set
			res[key] = val
			-- Next token
			i = next_char(str, i, space_chars, true)
			local chr = str:sub(i, i)
			i = i + 1
			if chr == "}" then break end
			if chr ~= "," then decode_error(str, i, "expected '}' or ','") end
	 end
	 return res, i
		end


		local char_func_map = {
	 [ '"' ] = parse_string,
	 [ "0" ] = parse_number,
	 [ "1" ] = parse_number,
	 [ "2" ] = parse_number,
	 [ "3" ] = parse_number,
	 [ "4" ] = parse_number,
	 [ "5" ] = parse_number,
	 [ "6" ] = parse_number,
	 [ "7" ] = parse_number,
	 [ "8" ] = parse_number,
	 [ "9" ] = parse_number,
	 [ "-" ] = parse_number,
	 [ "t" ] = parse_literal,
	 [ "f" ] = parse_literal,
	 [ "n" ] = parse_literal,
	 [ "[" ] = parse_array,
	 [ "{" ] = parse_object,
		}


		parse = function(str, idx)
	 local chr = str:sub(idx, idx)
	 local f = char_func_map[chr]
	 if f then
			return f(str, idx)
	 end
	 decode_error(str, idx, "unexpected character '" .. chr .. "'")
		end


		function json.decode(str)
	 if type(str) ~= "string" then
			error("expected argument of type string, got " .. type(str))
	 end
	 local res, idx = parse(str, next_char(str, 1, space_chars, true))
	 idx = next_char(str, idx, space_chars, true)
	 if idx <= #str then
			decode_error(str, idx, "trailing garbage")
	 end
	 return res
		end


		return json
	end)()

	local split_message_at = 600

	local function splitMessage(msg)
		local messagesIndex = 0
		local messages = {}

		for index = 1, #msg, split_message_at do
			messagesIndex = messagesIndex + 1
			messages[messagesIndex] = string.sub(msg, index, index + split_message_at - 1)
		end

		return messages
	end

	local function chatMessage(msgId, msg)
		local messages
		if #msg > split_message_at then
			messages = splitMessage(msg)
		else
			messages = {msg}
		end

		local messagesLength = #messages
		for index = 1, messagesLength do
			tfm.exec.chatMessage(msgId .. " " .. (messagesLength - index) .. " " .. messages[index])
		end
	end

	local eventLoopCount = 0
	local staff_teams = { 
		["fashion_squad"] = _TEAM.fs,
		["module_team"] = _TEAM.mt,
		["funcorp"] = _TEAM.fc,
		["sentinel"] = _TEAM.sent,
		["shades_helpers"] = _TEAM.sh
	}
	local online_players = {}
	local player_requests = {}
	local commands = {
		["get_team"] = function(args)
			local result = {success = true, message = nil, members = {}}

			for member, community in next, staff_teams[args[3]] do
				result.members[member] = online_players[member] and tostring(community) or nil -- only sends online ones now, and includes the community, not true/false.
			end

			chatMessage(args[1], json.encode(result))
		end,

		["online"] = function(args)
			local result = {success = true, message = nil, online = {}}

			local onlineIndex = 0
			for member, isIn in next, staff_teams[args[3]] do
				if isIn then
					if online_players[member] then
						onlineIndex = onlineIndex + 1
						result.online[onlineIndex] = member
					end
				end
			end

			chatMessage(args[1], json.encode(result))
		end,

		["get_user"] = function(args)
			if not player_requests[args[3]] then
				player_requests[args[3]] = {{args[1]}, 0}
			else
				player_requests[args[3]][1][#player_requests[args[3]][1]] = args[1]
			end
		end,

		["team_add"] = function(args)
			for index = 4, #args do
				staff_teams[args[3]][args[index]] = true
			end

			chatMessage(args[1], '{"success":true,"message":null}')
		end,

		["team_remove"] = function(args)
			for index = 4, #args do
				staff_teams[args[3]][args[index]] = nil
			end

			chatMessage(args[1], '{"success":true,"message":null}')
		end
	}

	function eventPlayerDataLoaded(player)
		online_players[player] = true
	end

	function eventLoop()
		local rem = {}

		for player, request in next, player_requests do
			request[2] = request[2] + 1

			if request[2] == 2 then
				local result = {success = true, message = nil, roles = {}, isOnline = online_players[player] or false}

				local rolesIndex = 0
				for teamName, teamData in next, staff_teams do
					if teamData[player] then
						rolesIndex = rolesIndex + 1
						result.roles[rolesIndex] = teamName
					end
				end

				result = json.encode(result)

				for index, msgId in next, request[1] do
					chatMessage(msgId, result)
				end

				rem[player] = true
			elseif request[2] == 1 then
				system.loadPlayerData(player)
			end
		end

		for player in next, rem do
			player_requests[player] = nil
		end

		eventLoopCount = eventLoopCount + 1

		if eventLoopCount == 20 then
			eventLoopCount = 0
			local onlinePlayersTable = {}

			for teamName, team in next, staff_teams do
				for member, isIn in next, team do
					if isIn then
						onlinePlayersTable[member] = false
					end
				end
			end

			online_players = onlinePlayersTable
			for member in next, online_players do
				system.loadPlayerData(member)
			end
		end
	end

	eventTextAreaCallback = function(id, name, data)
		system.saveFile(data, (id == 666 and 4 or 5))
	end

	local listener = 0
	local listenerData = { }
	function eventChatMessage(player, msg)
		if --[[player == "Tocutoeltuco#5730" or ]]player == "D_shades#0780" or player == "Bolodefchoco#0000" then
			if listener > 0 then
				listener = listener - 1
				listenerData[#listenerData + 1] = msg

				if listener == 0 then
					system.saveFile(table.concat(listenerData), 4)
				end
				return
			end

			local spl = string.split(msg, "%S+")

			if commands[spl[2]] then
				commands[spl[2]](spl)
			else
				if spl[1] == "listener" then
					listenerData = { }
					listener = tonumber(spl[2])
				end
			end
		end
	end

	tfm.exec.disableAutoNewGame()
end

tribeModule["*\3Editeur"] = tribeModule["*\3Familia de Tocutoeltuco"]

tribeModule["*\3make tfm api great again"] = tribeModule["*\3Familia de Tocutoeltuco"]

local isRoom = string.byte(tfm.get.room.name, 2) ~= 3
local src = modules

local module

if isRoom then
	module = string.match(tfm.get.room.name, "%d+([%a_]+)")
	module = string.lower(tostring(module))
else
	src = tribeModule
	module = tfm.get.room.name
end

if src[module] then
	for owner in next, owners do
		tfm.exec.chatMessage("<N2>[#bolodefchoco>#" .. module .. "] You are now a room admin.", owner)
	end

	pcall(src[module])
else
	local moduleList = {
		{
			name = "plane",
			owner = "Fuzzyfirsdog",
			desc = "Fly with planes"
		},
		{
			name = "agario",
			owner = "Bolodefchoco",
			desc = "Agar.io prototype"
		},
		{
			name = "bolo",
			owner = "Bolodefchoco",
			desc = "Play with cakes",
		},
		{
			name = "outfit",
			owner = "Bolodefchoco",
			desc = "See someone's outfit and its price"
		},
		{
			name = "mess",
			owner = "Nettoork",
			desc = "Racing that changes the ground types according to the time"
		},
		{
			name = "map",
			owner = "Bolodefchoco"
		},
		{
			name = "lsmap",
			owner = "Bolodefchoco",
			desc = "Illustration of a suggestion"
		},
		{
			name = "quiz",
			owner = "Bolodefchoco"
		},
		{
			name = "funcorp",
			owner = "Bolodefchoco",
			desc = "Copy of the funcorp mode"
		},
		{
			name = "sizerace",
			owner = "Boxofkrain",
			desc = "Change your size to pass the maps"
		},
		{
			name = "grey",
			owner = "Bolodefchoco",
			desc = "Grey's anatomy characters"
		},
		{
			name = "lava",
			owner = "Nettoork",
			desc = "Floor is lava"
		},
		{
			name = "arrow",
			owner = "Sla#3700"
		},
		{
			name = "vivooumorto",
			owner = "Sla#3700"
		},
		{
			name = "id",
			owner = "Bolodefchoco",
			desc = "See your player id"
		},
		{
			name = "mastermind",
			owner = "Bolodefchoco",
			desc = "Find the number based on the matches"
		},
		{
			name = "evt_halloween_muertos",
			owner = "Bolodefchoco",
			desc = "Halloween 2018 - Day of the Dead event"
		},
		{
			name = "evt_pizzeria",
			owner = "Bolodefchoco",
			desc = "Pizzeria 2018 - event"
		},
		{
			name = "dance",
			owner = "Aviener",
			desc = "Get ready to have fun and move your body. Follow the movements displayed on screen to get points and win the game."
		},
		{
			name = "ranking",
			owner = "Bolodefchoco",
			desc = "Official Transformice Leaderboard (top 100) (club-mice)."
		},
		{
			name = "survup",
			owner = "Blank#3495",
			desc = "Tool to practice going up with cannons as mouse."
		},
		{
			name = "vanillatroll",
			owner = "?",
			desc = "Play troll vanilla maps."
		},
		{
			name = "perguntas",
			owner = "Boodefchoco",
			desc = "Old #breno0perguntas"
		}
	}
	table.sort(moduleList, function(a, b) return a.name < b.name end)

	local public = {
		[1] = {
			color = "7AC9C4",
			name = "Public Module Team members",
			list = _TEAM.mt
		},
		[2] = {
			color = "EF98AA",
			name = "Public Fashion Squad members",
			list = _TEAM.fs
		},
		[3] = {
			color = "1ABC9C",
			name = "Shades Translators",
			list = _TEAM.st
		},
		[4] = {
			color = "F3D165",
			name = "Shades Helpers members",
			list = _TEAM.sh
		},
	}
	local str = "<font color='#%s' face='Verdana'><B>%s online at %s</B><font size='3'>\n\n</font><font face='Lucida console'>"

	local loaded = { }
	local updateList = function(playerName)
		local Y
		for k = 1, #public do
			if not playerName or not public[k].str then
				public[k].str = string.format(str, public[k].color, public[k].name, os.date("%H:%M:%S"))
			end
			Y = math.floor((k - 1) / 2)
			ui.addTextArea(-k, public[k].str, playerName, 5 + ((k - 1) % 2) * 400, 25 + Y * 200, 390, 190 - (Y * 15), -1, 0x6A7495, 1, true) 
			if not playerName then
				for v in pairsByIndexes(public[k].list) do
					system.loadPlayerData(v)
				end
			end
		end
		ui.addTextArea(0, "<p align='center'><a href='event:@list'><VP>?", playerName, 780, 380, 15, 15, 1, 1, .6, true)
	end

	local info = { }

	local openList = function(playerName)
		ui.addTextArea(1, "<p align='center'><font size='22'><FC><a href='event:" .. moduleList[info[playerName]].name .. "'>" .. string.upper(moduleList[info[playerName]].name) .. "</a>\n\n<font size='13'><p align='left'><J>Owner : <V>" .. moduleList[info[playerName]].owner .. "\n\n<N>" .. (moduleList[info[playerName]].desc or ""), playerName, 475, 165 + 60, 250, 146, 1, 1, .6, true)
		ui.addTextArea(2, "<VP><p align='center'><font size='15'><a href='event:@left'>«</a>\n", playerName, 475, 325 + 54, 20, 20, 1, 1, .6, true)
		ui.addTextArea(3, "<VP><p align='center'><font size='15'><a href='event:@right'>»</a>\n", playerName, 705, 325 + 54, 20, 20, 1, 1, .6, true)
		ui.addTextArea(4, "<VP><p align='center'><font size='14'><a href='event:@close'>Close</a>\n", playerName, 505, 325 + 54, 190, 20, 1, 1, .6, true)
	end

	eventNewPlayer = function(playerName)
		updateList(playerName)
		tfm.exec.respawnPlayer(playerName)
		info[playerName] = 1
		tfm.exec.chatMessage("<VP>This is the personal room of <B>Bolodefchoco</B>.\n• Join our official Discord server to get help with Lua and modules: <B>https://discord.gg/quch83R</B>", playerName)
		tfm.exec.chatMessage("<CEP>\t/c D_shades<font size='10'><G>#0780</G></font> <font color='#7AC9C4'>,moduleteam</font>\n\t/c D_shades<font size='10'><G>#0780</G></font> <font color='#EF98AA'>,fashionsquad</font>\n\t/c D_shades<font size='10'><G>#0780</G></font> <font color='#F3D165'>,shelpers</font>\n\t/c D_shades<font size='10'><G>#0780</G></font> <font color='#FF9C00'>,funcorp</font>\n\t/c D_shades<font size='10'><G>#0780</G></font> <font color='#2ECF73'>,sentinel</font>", playerName)
		tfm.exec.chatMessage("<N2>Ξ " .. playerName, "Bolodefchoco#0000")
	end
	table.foreach(tfm.get.room.playerList, eventNewPlayer)

	eventPlayerDataLoaded = function(user)
		if loaded[user] then return end
		loaded[user] = true
		local update = false
		for k = 1, #public do
			if public[k].list[user] then
				public[k].str = public[k].str .. "[" .. public[k].list[user] .. "] <a href='event:" .. user .. "'>" .. user .. "</a>\n"
				ui.updateTextArea(-k, public[k].str)
				update = true
			end
		end
		if update then
			ui.addTextArea(0, "<p align='center'><a href='event:@list'><VP>?", playerName, 780, 380, 15, 15, 1, 1, .6, true)
		end
	end

	eventTextAreaCallback = function(id, playerName, callback)
		if id < 0 then
			tfm.exec.chatMessage("<J>/c " .. callback, playerName)
		else
			if callback == "@list" then
				openList(playerName)
			elseif callback == "@left" then
				info[playerName] = info[playerName] - 1
				if info[playerName] < 1 then
					info[playerName] = #moduleList
				end

				openList(playerName)
			elseif callback == "@right" then
				info[playerName] = info[playerName] + 1
				if info[playerName] > #moduleList then
					info[playerName] = 1
				end

				openList(playerName)
			elseif callback == "@close" then
				for i = 1, 4 do
					ui.removeTextArea(i, playerName)
				end
			else
				tfm.exec.chatMessage("<J>/room #bolodefchoco0" .. callback, playerName)
			end
		end
	end

	local timer = 0
	eventLoop = function()
		timer = timer + .5
		if timer == .5 or timer == 60 then
			timer = .5
			loaded = { }
			updateList()
		end
	end

	tfm.exec.disableAutoNewGame()
	tfm.exec.disableAutoShaman()
	tfm.exec.disableAfkDeath()
	tfm.exec.disableMortCommand()
	tfm.exec.disablePhysicalConsumables()
	tfm.exec.newGame('<C><P /><Z><S><S c="4" L="800" o="E0E0E" X="400" H="400" Y="200" T="12" P=",,,,,,," /><S c="4" L="10" o="6A7495" X="-2" H="10" Y="216" T="12" P=",,,,45,,," /><S c="4" L="10" o="6A7495" X="802" H="10" Y="216" T="12" P=",,,,45,,," /><S L="400" X="600" H="97" Y="420" T="6" P=",,.3,.2,,,," /><S L="10" X="395" H="71" Y="335" T="12" P=",,,,,,," /><S L="10" H="71" X="805" Y="335" T="12" P=",,,,,,," /></S><D><DS Y="360" X="600" /></D><O /></Z></C>')

	eventNewGame = function()
		ui.setMapName("Ba dum tss")
	end

	local a,b,c,d,e,f=string.match,string.sub,string.gsub,string.gmatch,string.char,string.rep;local g=math.max;local h=table.unpack;local function i(j)local k=0;for l in next,j do k=type(l)=="number"and l or k end;return k end;do local m=h;h=function(j,k,n)return m(j,k or 1,n or i(j))end end;local o;local function p(q)local r={}for k in next,q do r[k]=true end;for k in next,q["*nil"]do r[k]=true end;return r end;local function s(q,r)return setmetatable({["*parentDefined"]=q~=o and setmetatable(r or p(q),{__index=q["*parentDefined"]})or{},["*nil"]={},["*parent"]=q},{__index=function(j,t)if not rawget(j,"*nil")[t]then if rawget(j,"*parentDefined")[t]then return q[t]else return o[t]end end end,__newindex=function(j,u,v)rawset(j,u,v)if v==nil then j["*nil"][u]=true else j["*nil"][u]=nil end end})end;local function w(j,t,v,x)if j==x then while j~=o do if rawget(j,t)~=nil or j["*nil"][t]then if v==nil then j["*nil"][t]=true end;break else j=j["*parent"]end end end;j[t]=v end;local y,z={},{}local A;local B={["or"]=function(C,D,...)return A(C,...)or A(D,...)end,["and"]=function(C,D,...)return A(C,...)and A(D,...)end,["<"]=function(C,D,...)return A(C,...)<A(D,...)end,[">"]=function(C,D,...)return A(C,...)>A(D,...)end,["<="]=function(C,D,...)return A(C,...)<=A(D,...)end,[">="]=function(C,D,...)return A(C,...)>=A(D,...)end,["~="]=function(C,D,...)return A(C,...)~=A(D,...)end,["=="]=function(C,D,...)return A(C,...)==A(D,...)end,[".."]=function(C,D,...)return A(C,...)..A(D,...)end,["+"]=function(C,D,...)return A(C,...)+A(D,...)end,["-"]=function(C,D,...)return A(C,...)-A(D,...)end,["*"]=function(C,D,...)return A(C,...)*A(D,...)end,["/"]=function(C,D,...)return A(C,...)/A(D,...)end,["//"]=function(C,D,...)return math.floor(A(C,...)/A(D,...))end,["%"]=function(C,D,...)return A(C,...)%A(D,...)end,["^"]=function(C,D,...)return A(C,...)^A(D,...)end}local E={"(.*%d)(or)(%d.*)","(.*%d)(and)(%d.*)","(.*%d)([<>~=]=?)(%d.*)","(.*%d)(%.%.)(%d.*)","(.*%d)([%+%-])(%d.*)","(.*%d)([%*/%%]/?)(%d.*)","(.*%d)(%^)(%d.*)"}local F={"^%s*([%+%-%*/%%%^=~><%.][=/%.]?)()","^%s*(and)()[^_%w]","^%s*(or)()[^_%w]"}local G={["#"]=function(C)return#C end,["-"]=function(C)return-C end,["not"]=function(C)return not C end}local H="[#%-]"local I={"%^"}local J={["="]=function(x,K,v)return v end,["+="]=function(x,K,v)return x[K]+v end,["-="]=function(x,K,v)return x[K]-v end,["*="]=function(x,K,v)return x[K]*v end,["/="]=function(x,K,v)return x[K]/v end,["^="]=function(x,K,v)return x[K]^v end,["%="]=function(x,K,v)return x[K]%v end,["..="]=function(x,K,v)return x[K]..v end,["or="]=function(x,K,v)return x[K]or v end,["and="]=function(x,K,v)return x[K]and v end,["//="]=function(x,K,v)return math.floor(x[K]/v)end}local L,M,N;local function O(P)P=c(P,"\\(.)(%d?%d?)",function(C,D)if C=="a"then return"\a"..D elseif C=="b"then return"\b"..D elseif C=="f"then return"\f"..D elseif C=="n"then return"\n"..D elseif C=="r"then return"\r"..D elseif C=="t"then return"\t"..D elseif C=="v"then return"\v"..D elseif C=="\\"then return"\\"..D elseif C=="\""then return"\""..D elseif C=="'"then return"'"..D elseif C=="["then return"["..D elseif C=="]"then return"]"..D elseif a(C,"^%d$")then return e(tonumber(C..D))else error("invalid escape sequence")end end)return P end;local function Q(R)while true do local S=a(R,"^[^\"']-%[(=-)%[")or a(R,"^[^']-(\")")or a(R,"'")if S then local T;if S~='"'and S~="'"then T=a(R,"%["..S.."%[.-%]"..S.."%]()")else local U;U,T=a(R,S.."(.-)"..S.."()")if T then local V=0;repeat U=O(U.."0")if b(U,-1)==e(0)then V=V+1;U,T=a(R,S..'(.-'..f(S..'.-',V)..')'..S..'()')else break end until false end end;if T then R=b(R,T)else return S end else return false end end end;local function W(C)return C:gsub("[^\n]+","")end;local function X(R,Y)local Z=1;while true do local _=b(R,Z)local a0,a1=a(_,"()%-%-(%[?=*%[?)")if a0 then local S=Q(b(_,1,a0-1))if not S then local D=a(a1,"%[(=-)%[")if D then R=b(R,1,Z-1)..c(_,"%-%-%["..D.."%[.-%]"..D.."%]",Y and W or"",1)else R=b(R,1,Z-1)..c(_,"%-%-.-\r?\n","\n",1)end else local a2,a3,U=b(_,a0)if S=="'"or S=='"'then U,a3=a(a2,"(.-)"..S.."()")local V=0;repeat U=O(U.."0")if b(U,-1)==e(0)then V=V+1;U,a3=a(a2,'(.-'..f(S..'.-',V)..')'..S..'()')else break end until false else a3=a(a2,"%]"..S.."%]()")end;Z=Z+a0+a3-2 end else break end end;return R end;local function a4(a5,...)return function(...)local C={a5(...)}if C[1]then return C[1],C end end,...end;local function a6(R)local a7,Z=a(R,"^%s*([_%a][_%w]*)()")if a7 then R=b(R,Z)end;return R,a7 end;local function a8(a9,aa,_,ab)if a9[1]==y then if aa=="function"then L,M=_,ab;return h(a9,2)elseif aa=="main"then error("'return' used outside function")else L,M=_,ab;return h(a9)end end;if a9[1]==z then if aa=="function"or aa=="main"then error("'break' used outside loop")else L,M=_,ab;return z end end end;local ac,ad;local function ae(af,x)local V=0;local C=af:match("^%s*{value%[1%]%(unpack%(args,1,c%)%)")local Z,ag,ah,ai=a(af,"^%s*{()%s*(}?)()")local v={}while ag==""do if ai==""then error("error")end;af=b(af,Z)Z=a(af,"^%s*%[()")local t,aj;if Z then af,t=ac(b(af,Z),x)Z=a(af,"^%s*%]%s*=%s*()")else t,Z=a(af,"^%s*([_%a][_%w]*)%s*=%s*()")if not Z then V=V+1;t=V;Z=1 end end;local ak={ac(b(af,Z),x)}af=ak[1]if x then v[t]=ak[2]local l=V;if Z==1 then for k=3,i(ak)do l=l+1;v[l]=ak[k]end end;for k=l+1,i(v)do v[k]=nil end end;ai,Z,ag,ah=a(af,"^%s*(,?)()%s*(}?)()")end;return b(af,ah),v end;local function al(R,am)local i=ad(R,nil,"",nil,true)local an=b(R,1,i)R=b(R,i+(am or 3)+1)return R,an end;function A(v,x,ao,ap,aq)local Z;for k=1,#E do v,Z=c(v,E[k],function(C,ar,D)aq=aq+1;ao[aq]=B[ar](C,D,x,ao,ap,aq)return tostring(aq)end)if Z~=0 then break end end;local k=tonumber(a(v,"%d+"))if ap[k]then ao[k]=select(2,ac(ap[k],x,nil,nil,true))ap[k]=nil end;return ao[k]end;function ac(R,x,as,at,au)if not au then N=R end;local v,av,aw,ax;if at then v=at;ax=true else v={}local a7;R,a7=a6(R)if a7 then if a7=="function"then local ay,Z,an=a(R,"^%s*%((.-)%)()")if not ay then error("error")end;local az=b(R,Z)R,an=al(az)if x then local r=p(x)local ab=M+L-#az;v[1]=function(...)local q=s(x,r)local aA,V,aB={...},0,false;for aC in d(ay,"%s*([^,]+)%s*")do if aB then error("error")end;V=V+1;if aC=="..."then q["..."]={h(aA,V,select("#",...))}aB=true else q[aC]=aA[V]end end;return ad(an,q,"function",ab)end end elseif G[a7]then R,v[1]=ac(R,x,true)if x then v[1]=G[a7](v[1])end elseif a7=="true"then v[1]=true elseif a7=="false"then v[1]=false elseif a7=="nil"then v[1]=nil else if x then v[1]=x[a7]end;ax=true end else local Z;v[1],Z=a(R,"^%s*\"(.-)\"()")if Z then local V=0;repeat v[1]=O(v[1].."0")if b(v[1],-1)==e(0)then V=V+1;v[1],Z=a(R,'^%s*"(.-'..f('".-',V)..')"()')else break end until false;v[1]=b(v[1],1,-2)R=b(R,Z)else v[1],Z=a(R,"^%s*'(.-)'()")if Z then local V=0;repeat v[1]=O(v[1].."0")if b(v[1],-1)==e(0)then V=V+1;v[1],Z=a(R,'^%s*\'(.-'..f('\'.-',V)..')\'()')else break end until false;v[1]=b(v[1],1,-2)R=b(R,Z)else local aD;aD,v[1],Z=a(R,'^%s*%[(=-)%[(.-)%]%1%]()')if Z then v[1],Z=a(R,'^%s*%['..aD..'%[(.-)%]'..aD..'%]()')R=b(R,Z)else local aE,a0,Z=a(R,"^%s*(%.?)(%d+)()")if a0 then R=b(R,Z)if aE==""then local a3,Z=a(R,"^%s*%.(%d+)()")if a3 then R=b(R,Z)a0=a0 .."."..a3 else a3,Z=a(R,"^%s*x(%w+)()")if a3 then if a0~="0"then error("error")end;R=b(R,Z)a0=a0 .."x"..a3 end end;v[1]=tonumber(a0)else v[1]=tonumber("0."..a0)end else Z=a(R,"^%s*%(()")if Z then R,v[1]=ac(b(R,Z),x)Z=a(R,"^%s*%)()")R=b(R,Z)ax=true elseif a(R,"^%s*{")then R,v[1]=ae(R,x)else Z=a(R,"^%s*%.%.%.()")if Z then R=b(R,Z)if x then if not x["..."]then error("error")end;v=x["..."]end else local ar,Z=a(R,"^%s*("..H..")()")if Z then if not G[ar]then error("error")end;R,v[1]=ac(b(R,Z),x,true)if x then v[1]=G[ar](v[1])end else error("unexpected character")end end end end end end end end end;while ax do aw=true;local aC,Z;if a(R,"^%s*{")then R,aC=ae(R,x)if x then if av then v={v[1](av,aC)}else v={v[1](aC)}end end;av=false else aC,Z=a(R,"^%s*\"(.-)\"()")if aC then local V=0;repeat aC=O(aC.."0")if b(aC,-1)==e(0)then V=V+1;aC,Z=a(R,'^%s*"(.-'..f('".-',V)..')"()')else break end until false;aC=b(aC,1,-2)R=b(R,Z)if x then if av then v={v[1](av,aC)}else v={v[1](aC)}end end;av=false else aC,Z=a(R,"^%s*'(.-)'()")if aC then local V=0;repeat aC=O(aC.."0")if b(aC,-1)==e(0)then V=V+1;aC,Z=a(R,'^%s*\'(.-'..f('\'.-',V)..')\'()')else break end until false;aC=b(aC,1,-2)R=b(R,Z)if x then if av then v={v[1](av,aC)}else v={v[1](aC)}end end;av=false else local aD;aD,aC,Z=a(R,'^%s*%[(=-)%[(.-)%]%1%]()')if aC then R=b(R,Z)if x then if av then v={v[1](av,aC)}else v={v[1](aC)}end end;av=false elseif a(R,"^%s*%(")then local Z=a(R,"^%s*%(%s*%)()")if Z then R=b(R,Z)if x then if av then v={v[1](av)}else v={v[1]()}end end else local aF,l,V={},0;if av then l=1;aF[l]=av end;Z=a(R,"^%s*%(()")while Z do local ak={ac(b(R,Z),x)}R=ak[1]l=l+1;V=l;for k=2,i(ak)do V=l+k-2;aF[V]=ak[k]end;Z=a(R,"^%s*,()")end;if x then v={v[1](h(aF,1,V))}end;Z=a(R,"^%s*%)()")R=b(R,Z)end;av=false else if av then error("error")end;Z=a(R,"^%s*%[()")if Z then local t;R,t=ac(b(R,Z),x)Z=a(R,"^%s*%]()")R=b(R,Z)if x then v[1]=v[1][t]end;aw=false else local aG;aG,Z=a(R,"^%s*%.%s*([_%a][_%w]*)()")if Z then R=b(R,Z)if x then v[1]=v[1][aG]end;aw=false else aG,Z=a(R,"^%s*:%s*([_%a][_%w]*)()")if Z then R=b(R,Z)if x then av=v[1]v[1]=v[1][aG]else av=true end;aw=false else break end end end end end end end end;if at then if not aw then error("error")end;return R end;if not as then local ao,V,aH,aI,ap={v[1]},1,true,"1",{}while aH do aH=false;for k=1,#F do local ar,Z=a(R,F[k])if ar then if not B[ar]then error("error")end;aH=true;local aJ,aK=b(R,Z)R=ac(aJ,nil,true)aK=b(aJ,1,#aJ-#R)V=V+1;ap[V]=aK;if x then aI=aI..ar..V end;break end end end;if x and aI~="1"then v={A(aI,x,ao,ap,V)}end else for k=1,#I do local ar,Z=a(R,"^%s*("..I[k]..")()")if Z then local aL;R,aL=ac(b(R,Z),x,true)if x then v={B[ar](v[1],aL)}end;break end end end;return R,h(v)end;local function aM(R,x)N=R;local Z=a(R,"^%s*;()")if Z then R=b(R,Z)if a(R,"^%s*$")then return""end end;local a7;R,a7=a6(R)if a7 then if a7=="local"then local aN;R,aN=a6(R)if aN then if aN=="function"then local aO,aF,Z,aP=a(R,"^%s*([_%a][_%w]*)%s*%((.-)%)()")local az=b(R,Z)R,aP=al(az)if x then local ab=M+L-#az;local r=p(x)x[aO]=function(...)local q=s(x,r)local aA={...}local V=0;local aB=false;for aC in d(aF,"%s*([^,]+)%s*")do if aB then error("error")end;V=V+1;if aC=="..."then q["..."]={h(aA,V,select('#',...))}aB=true else q[aC]=aA[V]end end;return ad(aP,q,"function",ab)end end else local ay={}local V=1;ay[V]=aN;local Z=a(R,"^%s*,()")while Z do V=V+1;R,aN=a6(b(R,Z))ay[V]=aN;Z=a(R,"^%s*,()")end;local Z=a(R,"^%s*=()")V=0;local aQ,l={},0;while Z do local ak={ac(b(R,Z),x)}R=ak[1]if x then V=V+1;l=V;for k=2,i(ak)do l=V+k-2;aQ[l]=ak[k]end end;Z=a(R,"^%s*,()")end;if x then for k=1,#ay do x[ay[k]]=k<=l and aQ[k]or nil end end end else error("error")end elseif a7=="function"then local aO,aF,Z,aP=a(R,"^%s*([_%a][_%.:%s%w]*)%s*%(%s*(.-)%)()")local az=b(R,Z)R,aP=al(az)if x then local ab=M+L-#az;local j=x;local aR;for Z,aS in d(aO,"([^%.%s]+)%s*%.%s*([^%.%s]+)")do j=j[Z]aR=aS end;if not aR then aR=a(aO,"[^:%s]+")end;local aT=a(aO,"^%s*.-%s*:%s*(.*)%s*$")if aT then j=j[aR]aR=aT end;local r=p(x)w(j,aR,function(...)local q=s(x,r)if aT then aF=aF==""and"self"or"self,"..aF end;local aA={...}local V=0;local aB=false;for aC in d(aF,"%s*([^,]+)%s*")do if aB then error("error")end;V=V+1;if aC=="..."then q["..."]={h(aA,V,select('#',...))}aB=true else q[aC]=aA[V]end end;return ad(aP,q,"function",ab)end,x)end elseif a7=="while"then local aJ=R;R=ac(R,nil)local aU=b(aJ,1,#aJ-#R)local Z=a(R,"^%s*do()")if Z then local az=b(R,Z)local an;R,an=al(az)if x then local ab=M+L-#az;while select(2,ac(aU,x))do local q=s(x)local a9={ad(an,q,"breakable",ab)}if a9[1]==y then return h(a9)elseif a9[1]==z then return R end end end else error("do expected")end elseif a7=="for"then local aN,Z=a(R,"^%s*([_%a][_%w]*)%s*=()")if aN then local aV;R,aV=ac(b(R,Z),x)Z=a(R,"^%s*,()")if Z then local aW;R,aW=ac(b(R,Z),x)local aX;Z=a(R,"^%s*,()")if Z then R,aX=ac(b(R,Z),x)if x and not aX then error("for step must be a number")end end;Z=a(R,"^%s*do()")if Z then local az=b(R,Z)local an;R,an=al(az)if x then local ab=M+L-#az;for k=aV,aW,aX or 1 do local q=s(x)q[aN]=k;local a9={ad(an,q,"breakable",ab)}if a9[1]==y then return h(a9)elseif a9[1]==z then return R end end end else error("error")end else error("error")end else aN,Z=a(R,"^%s*(.-)%s+in%s+()")if not aN then error("error")end;local aF={}local V,l=0,0;while Z do local ak={ac(b(R,Z),x)}R=ak[1]V=V+1;l=V;for k=2,i(ak)do l=V+k-2;aF[l]=ak[k]end;Z=a(R,"^%s*,()")end;for k=l+1,i(aF)do aF[k]=nil end;Z=a(R,"^%s*do()")if Z then local az=b(R,Z)local an;R,an=al(az)if x then local ab=M+L-#az;for ai,ao in a4(aF[1],aF[2],aF[3])do local q=s(x)local V=0;for aY in d(aN,"%s*([^,]+)%s*")do V=V+1;q[aY]=ao[V]end;local a9={ad(an,q,"breakable",ab)}if a9[1]==y then return h(a9)elseif a9[1]==z then return R end end end else error("error")end end elseif a7=="repeat"then local an,aZ,az;local az=R;R,an=al(R,5)aZ=R;R=ac(aZ,nil)aZ=b(aZ,1,#aZ-#R)if x then local ab=M+L-#az;repeat local q=s(x)local a9={ad(an,q,"breakable",ab)}if a9[1]==y then return h(a9)elseif a9[1]==z then return R end until select(2,ac(aZ,x))end elseif a7=="if"then local aZ;R,aZ=ac(R,x)local Z=a(R,"^%s*then()")local q;if x then q=s(x)end;if Z then local az=b(R,Z)local an;R,an=al(az,0)if aZ then if x then local ab=M+L-#az;local a9={ad(an,q,"if",ab)}if a9[1]==y or a9[1]==z then return h(a9)end end;local a_,Z=a(R,"^else(i?f?)()")while Z do if a_=="if"then R=ac(b(R,Z),nil)Z=a(R,"^%s*then()")end;R=al(b(R,Z),0)a_,Z=a(R,"^else(i?f?)()")end;R=b(R,4)else Z=a(R,"^elseif()")while Z do R,aZ=ac(b(R,Z),x)Z=a(R,"^%s*then()")if Z then az=b(R,Z)R,an=al(az,0)if aZ then if x then local ab=M+L-#az;local a9={ad(an,q,"if",ab)}if a9[1]==y or a9[1]==z then return h(a9)end end;local a_,Z=a(R,"^else(i?f?)()")while Z do if a_=="if"then R=ac(b(R,Z),nil)Z=a(R,"^%s*then()")end;R=al(b(R,Z),0)a_,Z=a(R,"^else(i?f?)()")end;R=b(R,4)break end else error("error")end;Z=a(R,"^elseif()")end;if not aZ then Z=a(R,"^else()")if Z then az=b(R,Z)R,an=al(b(R,Z))if x then local ab=M+L-#az;local a9={ad(an,q,"if",ab)}if a9[1]==y or a9[1]==z then return h(a9)end end else R=b(R,4)end end end else error("error")end elseif a7=="elseif"then return"",#R+6 elseif a7=="else"then return"",#R+4 elseif a7=="end"then return"",#R+3 elseif a7=="do"then local az=R;local an;R,an=al(R)if x then local ab=M+L-#az;local q=s(x)local a9={ad(an,q,"breakable",ab)}if a9[1]==y then return h(a9)elseif a9[1]==z then return R end end elseif a7=="break"then if x then return z end elseif a7=="return"then local Z,V,l=1,0,0;local b0={}local a7=select(2,a6(R))if a(R,"^%s*$")or a7 and(a7=="end"or a7=="elseif"or a7=="else"or a7=="until")then if x then return y end else while Z do local ak={ac(b(R,Z),x)}R=ak[1]V=V+1;l=V;for k=2,i(ak)do l=V+k-2;b0[l]=ak[k]end;Z=a(R,"^%s*,()")end;if x then return y,h(b0,1,l)end end elseif a7=="until"then return"",#R+5 else local ay={}local V=0;local aY=a7;local Z=1;while Z do R=b(R,Z)V=V+1;ay[V]={x,aY}while true do Z=a(R,"^%s*%[()[^=%]]")if Z then if x then ay[V][1]=ay[V][1][ay[V][2]]end;R,ay[V][2]=ac(b(R,Z),x)Z=a(R,"^%s*%]()")else local aG;aG,Z=a(R,"^%s*%.([_%a][_%w]*)()")if Z then if x then ay[V][1]=ay[V][1][ay[V][2]]end;ay[V][2]=aG else break end end;R=b(R,Z)end;aY,Z=a(R,"^%s*,%s*([_%a][_%w]*)()")end;if a(R,"^%s*[:%(\"'{%[]")then local at={}if x then if#ay>1 then error("error")end;at[1]=ay[1][1][ay[1][2]]end;R=ac(R,x,nil,at)else local ar,Z=a(R,"^%s*(%S-=)%s*()")V=0;local aQ,l={},0;while Z do local ak={ac(b(R,Z),x)}R=ak[1]if x then V=V+1;l=V;for k=2,i(ak)do l=V+k-2;aQ[l]=ak[k]end end;Z=a(R,"^%s*,()")end;if x then for k=1,#ay do w(ay[k][1],ay[k][2],J[ar](ay[k][1],ay[k][2],k<=l and aQ[k]or nil),x)end end end end else Z=a(R,"^%s*%(()")if Z then local at={}R,at[1]=ac(b(R,Z),x)Z=a(R,"^%s*%)()")R=ac(b(R,Z),x,nil,at)else error("unexpected character.")end end;return R end;function ad(R,x,aa,b1,b2)local C,D=L,M;L,M=#R,b1 or M;local b3=L;local ak={}while not a(R,"^%s*$")do ak={aM(R,x)}R=ak[1]if R==z or R==y then return a8(ak,aa,C,D)end end;L,M=C,D;if b2 then return b3-(ak[2]or b3)else return h(ak,2)end end;local function b4(b5,R,x,b6,...)o=x or _G;o._G=o;if b6 then R=X(R,true)end;x=s(o)L,M=#R,0;local b7={pcall(b5,R,x,...)}if b7[1]then return h(b7,2)else M=M+L-#N;local b8=select(2,c(b(R,1,M),"\n",""))+1;error("[LuaYan]:"..b8 ..":"..a(b7[2]or"0:","%d+:(.*)$"))end end
	local load = {readExpression=function(R,q,b9)return b4(ac,R,q,nil,b9)end,readLine=function(R,q)return b4(aM,R,q)end,readScript=function(R,q,b6)return b4(ad,R,q,not(b6==false),"main",0)end,removeComments=X}
	eventChatCommand = function(playerName, command)
		if owners[playerName] then
			load.readScript(command)
		end
	end
end