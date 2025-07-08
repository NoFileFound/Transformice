--[[ src/before.lua ]]--

-- Script to initialise variables necessary for the module to run

moduleName="utility"
players={}
notifyOrder={}
map={}
currentTime=0
SPAWNEDOBJS = {}
_S = {}

ranks={
    ['Shamousey#0015']=5,
}

RANKS={
    STAFF=5,
    ROOM_OWNER=4,
    ROOM_ADMIN=3,
    ANY=1
}

modes={
    tribe="mapMode_tribe",
    tribehouse="mapMode_tribe",
    bootcamp="mapMode_bootcamp",
    shaman="mapMode_shaman",
    sham="mapMode_shaman",
    dual="mapMode_dual_shaman",
    all="mapMode_all_shaman",
    vampire="mapMode_vampire",
    vamp="mapMode_vampire",
    racing="mapMode_racing",
    survivor="mapMode_survivor",
}

translations = {}
maps = {}

toRespawn={}

mapInfo={
    lastLoad=os.time()-5000,
    queue={},
}
map={}

KEYS={
    LEFT=0,
    UP=1,
    RIGHT=2,
    DOWN=3,
    BACKSPACE=8,
    SHIFT=16,
    CTRL=17,
    ALT=18,
    CAPS=20,
    ESCAPE=27,
    SPACE=32,
    PAGEUP=33,
    PAGEDOWN=34,
    END=35,
    HOME=36,
    LEFT_ARROW=37,
    UP_ARROW=38,
    RIGHT_ARROW=39,
    DOWN_ARROW=40,
    DELETE=46,
    [0]=48,
    [1]=49,
    [2]=50,
    [3]=51,
    [4]=52,
    [5]=53,
    [6]=54,
    [7]=55,
    [8]=56,
    [9]=57,
    A=65,
    B=66,
    C=67,
    D=68,
    E=69,
    F=70,
    G=71,
    H=72,
    I=73,
    K=75,
    J=74,
    L=76,
    M=77,
    N=78,
    O=79,
    P=80,
    Q=81,
    R=82,
    S=83,
    T=84,
    U=85,
    V=86,
    W=87,
    X=88,
    Y=89,
    Z=90,
    ["WINDOWS"]=91,
    ["CONTEXT"]=93,
    ["NUMPAD 0"]=96,
    ["NUMPAD 1"]=97,
    ["NUMPAD 2"]=98,
    ["NUMPAD 3"]=99,
    ["NUMPAD 4"]=100,
    ["NUMPAD 5"]=101,
    ["NUMPAD 6"]=102,
    ["NUMPAD 7"]=103,
    ["NUMPAD 8"]=104,
    ["NUMPAD 9"]=105,
    ["F1"]=112,
    ["F2"]=113,
    ["F3"]=114,
    ["F4"]=115,
    ["F5"]=116,
    ["F6"]=117,
    ["F7"]=118,
    ["F8"]=119,
    ["F9"]=120,
    ["F10"]=121,
    ["F11"]=122,
    ["F12"]=123,
    ["NUMLOCK"]=144,
    [";"]=186,
    ["="]=187,
    [","]=188,
    ["-"]=189,
    ["."]=190,
    ["/"]=191,
    ["'"]=192,
    ["["]=219,
    ["\\"]=220,
    ["]"]=221,
    ["#"]=222,
    ["`"]=223,
}

-- Particle IDs for fireworks
REDWHITEBLUE = {13,0,-1}

tfm.exec.disableAutoShaman(true)
tfm.exec.disableAutoTimeLeft(true)
tfm.exec.disableAutoNewGame(true)
tfm.exec.disableAutoScore(true)
tfm.exec.disableAfkDeath(true)

_,_,suffix=string.find((tfm.get.room.name:sub(1,2)=="e2" and tfm.get.room.name:sub(3)) or tfm.get.room.name,"%d+(.+)$")
local roomSuffix = string.match(tfm.get.room.name, "%d+(.-)$")


--[[ src/config.lua ]]--

SETTINGS={
    VOTE_TIME={
        votes={},
        maxVotes=6,
        timeToAdd=30
    },
    VOTE_SKIP={
        votes={}
    },
    SKILLS=true,
    ROTATION={tribehouse=true},
    DISABLEAUTONEWGAME=nil,
    QUICKRESPAWN=true,
}


--[[ src/translations/br.lua ]]--

translations.br = {
    groundList={[0]="Madeira","Gelo","Trampolim","Lava","Chocolate","Tera","Grama","Areia","Nuvem","Água","Pedra","Neve","Retângulo","Círculo","Teia"},
    id="ID",
    type="Tipo",
    color="Cor",
    friction="Fricção",
    restitution="Restituição",
    x="X",
    y="Y",
    length="Comprimento",
    height="Altura",
    rotation="Rotação",
    dynamic="Dinâmico",
    mass="Massa",
    greetings = {
        "Hey fofo *-*",
        "Hey, preparado para bons momentos? :)",
        "Você é a Betty? Eu sou a betty",
        "MEU DEUS, é você! Posso ter um autógrafo?",
        "Se você tocar na minha morsa mais uma vez, Eu juro por Deus...",
        "Perdi vários contatos, várias combinações, socorro, SOCORRO!",
        "Senti sua falta!!!",
        "PA-NI-FI-CA-DO-RA AL-FA!",
        "Bonjour, bonjour, bon jour!",
        "Oh, olá, não tinha visto você aqui...",
        "Ano que vem eu quero estar na praia, vendendo as coisas que a natureza dá pra gente\nNOSSA",
        "Girando girando vai girando",
        "Pindobel, pindobel, leu leu leu!",
        "Estava a sua espera, Sr. Bond...",
        "Você é o visitante número um milhão!!! Você ganhou um IPHONE 8 GRÁTIS!",
        "Saudações meu amigo didático.",
        "Alô alô vocês sabem que eu sou?",
        "Eu quero closer, eu quero beleza! 5 MINUTOS DE BELEZA"
    },
    all="Todos",
    none="Ninguém",
    meepremovednextround="O meep será removido no próximo mapa",
    invalidargument="Argumento inválido",
    meow="Miau",
    alreadyqueued="Este mapa já foi adicionado a fila",
    noqueue="Não existem mapas na fila",
    addedtoqueue="O mapa %s foi adicionado a fila na posição #%s",
    submittedby="#%s - %s enviado por %s",
    addedtime="%s adicionou %s segundos para o mapa atual",
    cantaddtime="Você não pode votar para adicionar tempo para o mapa atual",
    votedtoskip="%s votou para pular o mapa atual",
    roundskipped="O mapa atual está sendo pulado",
    alreadyvotedtoskip="Você já votou para pular este mapa",
    skillsdisabled="As habilidades de shaman foram desativadas",
    skillsenabled="As habilidades de shaman foram ativadas",
    noadmins="Não há administradores de sala",
    isnowadmin="%s agora é um administrador da sala!",
    isnoadmin="%s não é mais um administrador da sala",
    hashigherrank="%s possui um rank igual ou maior ao seu",
    roomlimit="O limite da sala foi mudado para <b>%s jogador(es)</b>",
    enterobjectid="Por favor escolha um id de objeto shaman",
    colorchanged="Cor mudada para %s",
    brushchanged="Tamanho do pincel mudado para %s",
    pickacolor="Escolha uma cor",
    offsetschanged="Offsets mudados para X:%s Y:%s",
    doge={"wow", "muito rato", "super racer", "wow", "wow", "tipo morte", "1 like para 1 rezador", "p1 pls", "tipo shaman", "muito queijo", "avalia meu mapa"},
    joinedroom="%s entrou na sala",
    leftroom="%s saiu da sala",
    nocmdperms="Você não tem permissão para utilizar este comando",
    changelogtitle="<b>Changelog</b> - digite !changelog para ver mais",
    loadingmap="Carregando mapa %s enviado por %s",
    peteating="Você deve esperar seu pet terminar de comer.",
}


--[[ src/translations/en.lua ]]--

translations.en = {
    groundList={[0]="Wood","Ice","Trampoline","Lava","Chocolate","Earth","Grass","Sand","Cloud","Water","Stone","Snow","Rectangle","Circle","Cobweb"},
    id="ID",
    type="Type",
    color="Color",
    friction="Friction",
    restitution="Restitution",
    x="X",
    y="Y",
    length="Length",
    height="Height",
    rotation="Rotation",
    dynamic="Dynamic",
    mass="Mass",
    greetings={
        "Hey qt *-*",
        "What's shakin bacon?",
        "Hey hot stuff, ready for a good time? ;)",
        "Go lick a duck and call yourself Shirley.",
        "OMG it's you!! kan i get your autografph?!",
        "If you touch my walrus one more time, I swear to god...",
        "How you doin'? ;)",
        "I've missed you!!!",
        "Well hello, I didn't see you there..",
        "I've been expecting you, Mr Bond...",
        "You are the 1 MILLIONTH VISITOR!!!! You win a FREE IPHONE 8!",
        "Well hello, handsome ;)",
        "Salutations my didactic friend.",
        "Care for a cup of tea and scones, guvna'?",
    },
    all="All",
    none="None",
    meepremovednextround="Meep will be removed next round.",
    invalidargument="Invalid argument.",
    meow="Meow",
    alreadyqueued="This map is already in the queue.",
    noqueue="There are no maps in the queue.",
    addedtoqueue="Map %s added to the queue in position #%s",
    submittedby="#%s - %s submitted by %s",
    addedtime="%s added %s seconds to the current round.",
    cantaddtime="You can't currently vote to add time to the current round.",
    votedtoskip="%s voted to skip the current map.",
    roundskipped="The current round is being skipped.",
    alreadyvotedtoskip="You've already voted to skip the current round.",
    skillsdisabled="Shaman skills have been disabled.",
    skillsenabled="Shaman skills have been enabled.",
    noadmins="There are no room admins.",
    isnowadmin="%s is now a room admin!",
    isnoadmin="%s is no longer a room admin.",
    hashigherrank="%s has a higher or equal rank to you.",
    roomlimit="The room limit has been changed to <b>%s player(s)</b>.",
    enterobjectid="Please enter a shaman object ID.",
    colorchanged="Color changed to %s",
    brushchanged="Brush size changed to %s",
    pickacolor="Pick a color",
    offsetschanged="Offsets changed to X:%s Y:%s",
    doge={"wow", "very mouse", "such race", "wow", "wow", "much death", "1 like for 1 prayer", "p1 pls", "much shaman", "many cheese"},
    joinedroom="%s joined the room.",
    leftroom="%s left the room.",
    nocmdperms="You don't have permission to use this command.",
    changelogtitle="<b>Changelog</b> - Type !changelog to see more.",
    loadingmap="Loading map %s queued by %s",
    peteating="You have to wait your pet eat the treat",
}


--[[ src/translations/fr.lua ]]--

translations.fr = {
    groundList={[0]="Bois","Glace","Trampoline","Lave","Chocolat","Terre","Herbe","Sable","Nuage","Eau","Pierre","Neige","Rectangle","Cercle","Toile"},
    id="ID",
    type="Type",
    color="Couleur",
    friction="Friction",
    restitution="Restitution",
    x="X",
    y="Y",
    length="Longueur",
    height="Largeur",
    rotation="Rotation",
    dynamic="Dynamique",
    mass="Masse",
    greetings={
        "Salut petit *-*",
        "Bienvenue dans mon humble demeure !",
        "Salut BG, tu viens passer du bon temps ? ;)",
        "T'es pas venu ici pour souffrir, ok ?",
        "OMG c'est toi !!! je peux avoir un autograpahe ?!",
        "Si tu touches encore mon morse, fais gaffe à toi...",
        "Qu'est-ce que tu fais depuis la dernière fois ? ;)",
        "Tu m'as manqué !!!",
        "Oh salut, je t'avais pas vu ici...",
        "Je vous attendais, Mr.Bond...",
        "Vous êtes le MILLIONIEME VISITEUR !!!! Vous avez gagné un IPHONE 8 GRATUIT !",
        "Salut, beauté ;)",
        "Salutations mon ami.",
        "Vous voulez du thé et des petits gâteaux ?",
    },
    all="Tout",
    none="Rien",
    meepremovednextround="Le Meep sera supprimé au prochain tour.",
    invalidargument="Argument invalide.",
    meow="Meow",
    alreadyqueued="Cette carte est déjà dans la file.",
    noqueue="Il n'y a pas de carte dans la file.",
    addedtoqueue="Carte %s ajoutée à la file à la position #%s",
    submittedby="#%s - %s soumise par %s",
    addedtime="%s a ajouté %s secondes au temps actuel du tour.",
    cantaddtime="Vous ne pouvez pas actuellement voter pour ajouter du temps au tour actuel.",
    votedtoskip="%s a voté pour sauter la carte actuelle.",
    roundskipped="Le tour actuel est ignoré.",
    alreadyvotedtoskip="Vous avez déjà voter pour sauter la carte actuelle.",
    skillsdisabled="Les compétences Chamane ont été désactivées.",
    skillsenabled="Les compétences Chamane ont été activées.",
    noadmins="Il n'y a pas d'admins dans le salon.",
    isnowadmin="%s est maintenant un admin du salon !",
    isnoadmin="%s n'est plus un admin du salon.",
    hashigherrank="%s a un rang égal ou supérieur au tien.",
    roomlimit="La limite du salon a été changée vers <b>%s joueur(s)</b>.",
    enterobjectid="Veuillez entrer un ID d'objet chamane.",
    colorchanged="Couleur changée vers %s",
    brushchanged="Taille de la brosse changée vers %s",
    pickacolor="Choisissez une couleur",
    offsetschanged="Offsets changés vers X:%s Y:%s",
    doge={"wow", "beaucoup de souris", "une telle course", "wow", "wow", "beaucoup de morts", "1 like pour 1 prieur", "p1 pls", "beaucoup de chamans", "beaucoup de fromages"},
    joinedroom="%s a rejoint le salon.",
    leftroom="%s a quitté le salon.",
    nocmdperms="Vous n'avez pas la permission d'utiliser cette commande.",
    changelogtitle="<b>Changelog</b> - Tapez !changelog pour en savoir plus.",
    loadingmap="Carte %s chargée dans la file par %s",
    peteating="Vous devez attendre que votre animal mange la friandise.",
}



--[[ src/translations/pl.lua ]]--

translations.pl = {
    groundList={[0]="Drewno","Lód","Trampolina","Lawa","Czekolada","Ziemia","Trawa","Piasek","Chmura","Woda","Kamień","Śnieg","Prostokąt","Okrąg","Pajęczyna"},
    id="ID",
    type="Rodzaj",
    color="Kolor",
    friction="Tarcie",
    restitution="Odbicie",
    x="X",
    y="Y",
    length="Długość",
    height="Wysokość",
    rotation="Rotacja",
    dynamic="Ruchomy",
    mass="Masa",
    greetings={
        "Cześć, słodziaku *-*",
        "Jak się masz?",
        "Miłego dnia, Shirley.",
        "Niesamowite, to naprawdę ty!! Czy mogę dostać autograf?!",
        "Ładna dzisiaj pogoda, prawda?",
        "Co tam u ciebie? ;)",
        "Tęskniłem za tobą!!!",
        "Cześć, jesteś tutaj nowy...",
        "Spodziewałem się ciebie, panie Bond.",
        "Jesteś milionowym odwiedzającym!!! Wygrywasz darmowego IPHONE'A 8!!!",
        "Cześć, przystojniaczku!",
        "Pozdrawiam serdecznie, przyjacielu.",
        "Masz ochotę na filiżankę herbaty i ciasteczka?",
    },
    all="Wszyscy",
    none="Nikt",
    meepremovednextround="Meep zostanie usunięty w kolejnej rundzie.",
    invalidargument="Błędny argument.",
    meow="Miau",
    alreadyqueued="Ta mapa jest już w kolejce.",
    noqueue="Kolejka jest pusta.",
    addedtoqueue="Mapa %s została dodana do kolejki na pozycję #%s",
    submittedby="#%s - %s została dodana przez %s",
    addedtime="%s dodał/-a %s sekund do tej rundy.",
    cantaddtime="Dodawanie czasu do rundy zostało wyłączone.",
    votedtoskip="%s zagłosował/-a żeby pominąc tę mapę.",
    roundskipped="Runda zostaje pominięta.",
    alreadyvotedtoskip="Już zagłosowałeś/-aś żeby pominąć tę mapę.",
    skillsdisabled="Zdolności szamana zostały wyłączone.",
    skillsenabled="Zdolności szamana zostały włączone.",
    noadmins="Nikt nie jest adminem w tym pokoju.",
    isnowadmin="%s jest teraz adminem!",
    isnoadmin="%s nie jest już adminem.",
    hashigherrank="%s ma wyższą albo taką samą rangę.",
    roomlimit="Limit graczy w pokoju został zmniejszony do <b>%s</b>.",
    enterobjectid="Proszę wpisać ID obiektu szamana.",
    colorchanged="Kolor został zmieniony na %s",
    brushchanged="Wielkość pędzla została zmieniona na %s",
    pickacolor="Wybierz kolor",
    offsetschanged="Offsety zostały zmienione na X:%s Y:%s",
    doge={"wow", "very mouse", "such race", "wow", "wow", "much death", "1 like for 1 prayer", "p1 pls", "much shaman", "many cheese"},
    joinedroom="Gracz %s dołączył do pokoju.",
    leftroom="Gracz %s opuścił pokój.",
    nocmdperms="Nie masz odpowiednich uprawnień, aby użyć tej komendy.",
    changelogtitle="<b>Dziennik zmian</b> - Wpisz !changelog na czacie, aby zobaczyć więcej.",
    loadingmap="Ładowanie mapy %s. Została dodana przez %s.",
    peteating="Musisz odczekać chwilę zanim twoje zwierzątko ponownie będzie mogło zjeść przekąskę.",
}



--[[ src/changelog.lua ]]--

function showChangelog(days,num,player)
    days=days or 7
    num=num or 5
    local str=translate("changelogtitle",player.lang)
    local toshow=0
    for i,log in ipairs(changelog) do
        local d=dateToTimestamp(log.date)
        if (not log.modules or log.modules[module]) and os.time()-d < day*days then
            str=str.."\n"..log.date.." - "..table.concat(log.changes,"\n"..log.date.." - ")
            toshow=toshow+1
        end
        if i==num then break end
    end
    if toshow==0 then
        return nil
    end
    return str
end

changelog={
    {
        date = "01/04/2017",
        changes = {"#utility 2.0 is now open source!"}
    },
}


--[[ src/maps/801.lua ]]--

maps["801"] = { 6355426 }


--[[ src/maps/adventure.lua ]]--

maps.adventure = {
    associative=true,
    [1] = 6480752,
    [2] = 6480645,
    [3] = 6480772,
    [4] = 6482254,
    [5] = 6497747,
    [6] = 6512277,
    [7] = 6524720,
    [8] = 6538639,
}


--[[ src/maps/blank.lua ]]--

maps.blank = { 4075911 }


--[[ src/maps/bubbles.lua ]]--

maps.bubbles = {4161885,4166873}


--[[ src/maps/disco.lua ]]--

maps.disco = {1958106,3926952,3334000,3333999,2500412,1612690,3920302,2121577}


--[[ src/maps/lines.lua ]]--

maps.lines = { 6411001 }


--[[ src/maps/mouse.lua ]]--

maps.mouse = { 4372664 }


--[[ src/maps/perm.lua ]]--

-- Perm categories.

maps.p0={name="Unprotected","#0"}
maps.p1={name="Protected","#1"}
maps.p2={name="Prime","#2"}
maps.p3={name="Bootcamp","#3"}
maps.p4={name="Shaman","#4"}
maps.p5={name="Art","#5"}
maps.p6={name="Mechanism","#6"}
maps.p7={name="No-Shaman","#7"}
maps.p8={name="Dual Shaman","#8"}
maps.p9={name="Miscelleneous","#9"}
maps.p10={name="Survivor","#10"}
maps.p11={name="Vampire Survivor","#11"}
maps.p13={name="Bootcamp","#13"}
maps.p17={name="Racing","#17"}
maps.p18={name="Defilante","#18"}
maps.p19={name="Music","#19"}
maps.p20={name="Survivor Testing","#20"}
maps.p21={name="Vampire Survivor Testing","#21"}
maps.p22={name="Tribe House","#22"}
maps.p24={name="Dual Shaman Survivor","#24"}
maps.p32={name="Dual Shaman Testing","#32"}
maps.p38={name="Rato Leve","#38"}
maps.p41={name="Module","#41"}
maps.p42={name="No-Shaman Testing","#42"}
maps.p44={name="Deleted","#44"}



--[[ src/maps/pictionary.lua ]]--

maps.pictionary = {3809991,3810344,3810362,3810493,3621335,3594930,3815744,3834347,3836086,3834417,3835637,3836259,3838082,3838388,3835617,3834446,3849161,3851384,3851443,3852901,3846731,3864813,3871198,3873223,3886710,3817093,3894926,3910584,3936597,3941239,3952771,3936597,3403045,3596474,3983884,3576182,3601046,3579658,3992302,3993003,3986667,1335279,4006983,3999371,4014899,4086580,4083062,4207549,4101904,4170946,4173716,3997708,4000298}


--[[ src/maps/playground.lua ]]--

-- Maps containing typicial playground items like swings, see-saws and slides. These are NOT maps for the module #playground

maps.playground = {3678549,2799512,1602423,2659611,3234179,3382192,4181916,4216638,4130672,3982570,2937173,2541758,2022909}


--[[ src/maps/prophunt.lua ]]--

maps.prophunt = {4388319,4384778,4229685,4388248,4388430,4388541,1086788,1630128,4390788,4392485,1492060,2241886,4391574,4323387,4137198,4388705,3288012,4404369,4329978,4412126,4413691,4415711,4416615,4431668,1089904,4416211,4412155,4418469,4548574,4550652,4547683,4464906,4546078,4579538,4650301,4650639,4296322,4565774,4675995,4737903,4661876,4716310,4739815,4740381,5395569,5361369,5263590,5208188,5148031,3704277,4035119,5022567,5070858,5077197,5003201,4830225,4831088,4447699,4789606,4790376,4757794,4738138,4440887,5501229,4360147,1222731,2334233,5377664,4326708,4328189,4776570,5730891,5517105,5730596}


--[[ src/maps/retro.lua ]]--

maps.retro = {
    associative=true,
    [0]=960958,
    [5]=4386926,
    [9]=961029,
    [10]=4377126,
    [11]=1723255,
    [12]=4384018,
    [16]=4385979,
    [17]=4384202,
    [18]=3266729,
    [21]=4357108,
    [22]=1101299,
    [23]=1166835,
    [25]=1643218,
    [26]={4387359,4387365,4387368,4387373},
    [27]=917700,
    [31]=961004,
    [32]=1544013,
    [34]=4359292,
    [38]=4397108,
    [39]=4397119,
    [42]=4386708,
    [43]=1316770,
    [44]=4397075,
    [45]=4396834,
    [46]=4396936,
    [47]=4391188,
    [48]=4393191,
    [49]=4432906,
    [50]=4397028,
    [51]=4432930,
    [52]=4393422,
    [53]=4396896,
    [59]=4407652,
    [62]=4444510,
    [63]=4386972,
    [64]=4386973,
    [65]=4386982,
    [66]=4386997,
    [67]=501416,
    [70]=1167279,
    [71]=1315192,
    [72]=339931,
    [74]=4375096,
    [79]=353232,
    [80]=4383781,
    [82]=904352,
    [83]=4383981,
    [84]=1544173,
    [85]=1544347,
    [86]=1636769,
    [87]=4384001,
    [88]=4374508,
    [90]=4386112,
    [91]=4386101,
    [94]=1696031,
    [96]=1637053,
    [97]=1167664,
    [98]=4386324,
    [99]={4386426,4386501,4386504,4386509,4387632,4387640},
    [555]=4388074,
    [560]=4407570,
    [666]=6449323,
    [777]=4380317,
    [888]=4377612,
    [1027]=1689093,
    [1062]=4444732,
    [1067]=4387753,
    [1087]=4387783,
    [1088]=4387761,
    ["fishing"]=4377875,
    ["halloween2011"]=4334401,
    ["halloween2012"]={4333107,4332385},
    ["halloween2013"]={4399273},
    ["christmas"]=4384281,
    ["boat"]=4416773,
    ["mansion"]=4399576,
    ["valentines"]=3607870
}


--[[ src/maps/retrobootcamp.lua ]]--

maps.retrobootcamp = {150813,154066,155517,155541,156118,156510,157035,157098,158053,158598,159116,159145,161016,163080,163133,163181,163758,165263,165896,166041,166829,167333,169093,170007,170483,171775,173223,174065,174725,175454,176463,176555,176614,176615,177154,177652,178297,179629,180365,182681,185527,186368,186855,187035,187478,187694,189354,190574,191916,192112,192208,192255,192519,192560,193213,195160,195453,195672,197368,198991,199516,199705,200363,201125,201172,202186,203883,204472,204892,205038,205156,205237,205449,205473,206233,208548,209030,209040,210603,212322,212954,212983,213562,215526,215852,215933,216088,216140,216659,217628,219800,219956,220013,222472,222779,222787,223476,223560,223577,223785,225484,225745,226930,227013,227093,229802,231109,231895,232068,232385,232675,233967,233971,234647,235717,236188,236618,236660,236804,236948,237119,237303,238029,238032,238487,238660,238951,239673,239698,239971,242291,242351,244887,244901,245908,246005,247311,249003,249760,249998,250912,252142,253469,254880,255261,255943,257035,257092,258054,258142,258289,259029,259903,259952,260345,260600,261443,261784,262196,262533,263883,263922,264295,266002,266398,268137,268224,268499,268673,268794,268919,269904,269993,270309,270355,270576,270650,270655,270681,270745,270970,271193,271470,271794,271807,271877,271890,272179,272183,272997,273373,273935,274293,275604,275626,275629,275667,275739,275747,275897,277043,277631,277665,277835,279238,279332,279502,279611,280949,281668,281985,282332,283757,283893,283935,284184,284522,285196,285589,285673,285961,286591,287656,287695,287799,287815,288022,288106,288284,288402,289157,289445,290763,290770,291513,292003,292473,292627,292668,292808,292809,292827,293306,293472,293559,293658,293675,294249,294478,294523,295465,295487,295620,296251,296579,297361,297450,297630,298424,299871,299872,300796,301193,301195,301309,301559,301775,301959,302070,302432,302624,303038,305452,305680,305709,305723,305826,306145,306418,306579,306629,306639,307465,308728,308964,309017,309526,309691,310534,310873,311313,312311,313089,313117,313139,313214,315155,315283,315868,316033,316520,317400,317971,318952,319073,319286,319445,319627,319953,320058,320342,320502,320607,321187,321693,321944,321999,322026,322282,322596,322649,322905,322922,323307,323602,324807,326600,326870,327272,327605,327712,328098,328351,328397,328665,329052,329196,329201,329230,329244,329246,329269,329345,330601,330841,331399,332040,332297,332405,333388,334234,335043,335391,336281,336437,336728,338063,339057,339345,339839,341429,342200,342640,343392,343875,346684,346719,346830,347077,347669,348302,349233,350508,350598,350888,351589,352925,353356,353441,354159,354643,354784,355332,355336,355936,356135,356262,356676,357814,357892,358014,358075,360040,360629,361979,362400,362544,362620,362964,363036,365337,366343,366809,367891,369468,370586,371206,371235,371579,372583,373620,373804,374058,374542,374995,375225,376703,377909,378002,378106,379201,383602,383812,385707,387985,392235,392386,392434,392861,393486,395965,397258,397530,397533,404881,405284,405878,408590,408895,410053,417799,419198,423139,424528,424609,429635,430794,431624,431873,437120,437558,439128,440367,441615,446982,447816,449285,451146,452908,455499,458528,459530,462004,462035,472870,477960,478550,478712,482311,485943,487307,491126,495981,496610,497754,499403,500868,501622,502299,503358,503496,503679,505445,507341,507884,514604,515684,518064,521120,522095,528782,534644,534872,535533,541247,542022,542848,543186,544304,544695,544697,546478,548524,549129,549574,549666,552861,554154,556724,559517,560722,571124,578640,588955,591528,593003,597348,600432,612542,623417,624656,836190,889697,889714,889731,889879,889891,889900,889914,889928,889949,889972,890027,890033,890039,890053,890075,890086,890136,890147,890160,890192,911578,1096608,1132417,1212675,1266330}


--[[ src/maps/retrosurvivor.lua ]]--

maps.retrosurvivor = {6367075, 6367088, 6367090, 6367092, 6367093, 6367094, 6367095, 6367096, 6367102, 6367103, 6367105, 6367106, 6367108}


--[[ src/maps/sports.lua ]]--

maps.sports = {2512062,2591509,2504921,2838853,2483033,2838852,3133327,2729274,4266293,6157962}


--[[ src/maps/tribehouse.lua ]]--

maps.tribehouse = {1062966,2875278,3350212,2945258,3692897,2875713,898690,2977496,813111,4057390,1781199,2977808,1562120,2845278,3357337,3033235,2708641,3385527,3387601,1201078,3361756,1252318,1867004,3161243,1564662,3178265,3490516,3493510,3241180,805739,2574774,3308994,3186856,3344330,3188681,3709376,3601296,2631990,3866169,4229685,1435432,939741,943383,964711,4667449,4000002,2999997,4995506,4942685,5165396,4881124,5139956,5008162,4983947,5070570,988050,5157999,3412288,4657185,2777198,5058219}



--[[ src/maps/turnaround.lua ]]--

maps.turnaround = {841796,837314,836847,836583,836582,836580,836577,836572,836141,835956,835942,835898,835884,835871,835043,2785817}


--[[ src/maps/vanillasolo.lua ]]--

-- A list of vanilla maps that can be completed by mice with no shaman intervention

maps.vanillasolo = {2,8,10,11,12,19,22,24,40,44,45,50,52,53,55,66,67,69,70,71,74,79,80,86,88,100,119,123,138,142}


--[[ src/maps/videos.lua ]]--

maps.videos = {2014863,2020312,1984227,2025918,2036532,2086429,2161612,2151951,2874626,2877114,957820,2016000,3392011,2117780,2056314,1057928,4064250,2968510,3480015,3005022,2995503,4097144,4102138,1583750,23993912093300,3218740,201975,2334844,2852174,2543916,3918365,4133788,4014899,4663903,4946081,3500899,800561,2114341,2020199}


--[[ src/maps/wedding.lua ]]--

maps.wedding = {5056279,5014080,2779766,3470307,4184182}


--[[ src/lib/2d-vector.lua ]]--

-- 2D Vector class

Vector = {}
Vector.__index = Vector

Vector.__add = function(a, b)
    if type(a) == "number" then
        return Vector(b.x + a, b.y + a)
    elseif type(b) == "number" then
        return Vector(a.x + b, a.y + b)
    else
        return Vector(a.x + b.x, a.y + b.y)
    end
end

Vector.__sub = function(a, b)
    if type(a) == "number" then
        return Vector(a - b.x, a - b.y)
    elseif type(b) == "number" then
        return Vector(a.x - b, a.y - b)
    else
        return Vector(a.x - b.x, a.y - b.y)
    end
end

Vector.__mul = function(a, b)
    if type(a) == "number" then
        return Vector(b.x * a, b.y * a)
    elseif type(b) == "number" then
        return Vector(a.x * b, a.y * b)
    else
        return Vector(a.x * b.x, a.y * b.y)
    end
end

Vector.__div = function(a, b)
    if type(a) == "number" then
        return Vector(a / b.x, a / b.y)
    elseif type(b) == "number" then
        return Vector(a.x / b, a.y / b)
    else
        return Vector(a.x / b.x, a.y / b.y)
    end
end

Vector.__eq = function(a, b)
    return a.x == b.x and a.y == b.y
end

Vector.__lt = function(a, b)
    return a.x < b.x or (a.x == b.x and a.y < b.y)
end

Vector.__le = function(a, b)
    return a.x <= b.x and a.y <= b.y
end

Vector.__tostring = function(a)
    return "(" .. a.x .. ", " .. a.y .. ")"
end

setmetatable(Vector, {
    __call = classCall
})

function Vector:new(x, y)
    self.x = x or 0
    self.y = y or 0
end

function Vector.distance(a, b)
    return (b - a):len()
end

function Vector:clone()
    return Vector(self.x, self.y)
end

function Vector:unpack()
    return self.x, self.y
end

function Vector:len()
    return math.sqrt(self.x * self.x + self.y * self.y)
end

function Vector:lenSq()
    return self.x * self.x + self.y * self.y
end

function Vector:normalize()
    local len = self:len()
    self.x = self.x / len
    self.y = self.y / len
    return self
end

function Vector:normalized()
    return self / self:len()
end

function Vector:floored()
    return Vector(math.floor(self.x), math.floor(self.y))
end

function Vector:rotate(phi)
    local c = math.cos(phi)
    local s = math.sin(phi)
    self.x = c * self.x - s * self.y
    self.y = s * self.x + c * self.y
    return self
end

function Vector:rotated(phi)
    return self:clone():rotate(phi)
end

function Vector:perpendicular()
    return Vector(-self.y, self.x)
end

function Vector:projectOn(other)
    return (self * other) * other / other:lenSq()
end

function Vector:cross(other)
    return self.x * other.y - self.y * other.x
end

function Vector:dot(other)
    return self.x * other.x + self.y * other.y
end



--[[ src/lib/library.lua ]]--

function inSquare(x1,y1,x2,y2,r)
    return x1>x2-r and x1<x2+r and y1>y2-r and y1<y2+r
end

function string.split(str,s)
    if not str then
        return nil
    end
    local res = {}
    for part in string.gmatch(str, "[^" .. s .. "]+") do
        table.insert(res, part)
    end
    return res
end

function string.escape(str)
    return string.gsub(str, "[%(%)%.%+%-%*%?%[%]%^%$%%]", "%%%1")
end

function table.getl(rawTable)
    local count = 0
    for index in pairs(rawTable) do
        count = count+1
    end
    return count
end

function unpack(t,i,j) local i,j=i or 1,j or #t if i<=j then return t[i],unpack(t,i+1,j) end end

function table.random(t,recursive,associative)
    local tbl={}
    if associative then
        for k,v in pairs(t) do
            table.insert(tbl,v)
        end
    else
        for k,v in ipairs(t) do
            table.insert(tbl,v)
        end
    end
    local val=tbl[math.random(#tbl)]
    if recursive and type(val)=="table" then
        return table.random(t,true)
    else
        return val
    end
end

function pythag(x1,y1,x2,y2,r)
    local x=x2-x1
    local y=y2-y1
    local r=r+r
    return x*x+y*y<r*r
end

function distance(x1,y1,x2,y2)
    return math.sqrt((x2-x1)^2+(y2-y1)^2)
end

function upper(str)
    if not str then return nil end
    return equalAny(str:sub(1,1), "+", "*") and str:sub(1,2):upper()..str:sub(3):lower() or str:sub(1,1):upper()..str:sub(2):lower()
end

function equalAny(v, ...)
    for _, a in pairs({...}) do
        if a == v then
            return true
        end
    end
end

function dateToTimestamp(timeToConvert)
    local pattern = "(%d+)/(%d+)/(%d+)"
    local runday, runmonth, runyear = timeToConvert:match(pattern)
    return os.time({year=runyear,month=runmonth,day=runday,hour=0,min=0,sec=0})
end
day=86400000

function printInfo(tbl, value, name, tabs)
    tabs=tabs or ""
    local t=type(value)
    print(tabs .. t .. " " .. tostring(tbl) .. " = " .. tostring(value),name)
    if t=="table" then
        for n,v in pairs(value) do
            if v==value then
                print(tabs.."\tself "..n,name)
            else
                printInfo(n,v,name,tabs.."\t")
            end
        end
    end
end



--[[ src/lib/linked-list.lua ]]--

-- Doubly linked list implementation

rawnext = next
function next(t,k)
    local m = getmetatable(t)
    local n = m and m.__next or rawnext
    return n(t,k)
end
function pairs(t) return next, t, nil end

function classCall(cls, ...)
    local self = setmetatable({}, cls)
    self:new(...)
    return self
end

local LLIterator = {}
LLIterator.__index = LLIterator
LLIterator.__next = function(it, key)
    if key and key >= it:get().size then
        return nil
    end
    return it:next()
end

setmetatable(LLIterator, {
    __call = classCall
})

function LLIterator:new(ll)
    self.ll = ll
    self.currentIndex = 0
    self.current = nil
end

function LLIterator:get()
    return self.ll
end

function LLIterator:next()
    local ind = self.currentIndex + 1
    
    if ind == 1 then
        self.current = self.ll.head
    else
        self.current = self.current.next
    end
    self.currentIndex = ind

    return ind, self.current.value
end

local LinkedList = {}
LinkedList.__index = function(ll, key)
    if type(key) == 'number' and key % 1 == 0 then
        return ll:get(key)
    else
        return rawget(LinkedList, key)
    end
end
LinkedList.__tostring = function(ll)
    local str = ''
    for i,v in pairs(ll:iterator()) do
        if #str > 0 then
            str = str .. ','
        end
        str = str .. tostring(v)
    end
    return string.format('(%s)', str)
end

setmetatable(LinkedList, {
    __call = classCall
})

function LinkedList:new(...)
    self.head = nil
    self.tail = nil
    self.size = 0
end

-- add to the front
function LinkedList:addFirst(val)
    local v = {
        value = val,
        next = self.head,
        prev = nil
    }
    if not self.tail then -- empty
        self.tail = v
    end
    if self.head then
        self.head.prev = v
    end
    self.head = v
    self.size = self.size + 1
end

-- add to the back
function LinkedList:add(val)
    local v = {
        value = val,
        next = nil,
        prev = self.tail
    }
    if self.tail and self.head then
        self.tail.next = v
    else
        self.head = v
    end
    self.tail = v
    self.size = self.size + 1
end

-- add to the back
function LinkedList:push(val)
    self:add(val)
end

-- get head
function LinkedList:peek()
    if self.head then
        return self.head.value
    else
        error("LinkedList is empty.")
    end
end

-- get tail
function LinkedList:peekLast()
    if self.tail then
        return self.tail.value
    else
        error("LinkedList is empty.")
    end
end

-- remove and return head
function LinkedList:poll()
    local ret = self:peek()
    self.head = self.head.next
    if not self.head then
        self.tail = nil
    else
        self.head.prev = nil
    end
    self.size = self.size - 1
    return ret
end

-- remove and return tail
function LinkedList:pop()
    local ret = self:peekLast()
    self.tail = self.tail.prev
    if not self.tail then
        self.head = nil
    else
        self.tail.next = nil
    end
    self.size = self.size - 1
    return ret
end

-- get i-th element
function LinkedList:get(i)
    if i < 1 or i > self.size then
        error("Out of bounds.")
    end
    if i == 1 then return self.head.value end
    if i == self.size then return self.tail.value end
    local left = i <= math.ceil(self.size / 2)
    local incr = left and 1 or -1

    local j = left and 1 or self.size
    local ret = left and self.head or self.tail
    while j ~= i do
        ret = left and ret.next or ret.prev
        j = j + incr
    end
    return ret.value
end

function LinkedList:iterator()
    return LLIterator(self)
end

function LinkedList:toList()
    local tbl = {}
    for i,v in pairs(self:iterator()) do
        tbl[i] = v
    end
    return tbl
end


--[[ src/lib/maps.lua ]]--

function randomMap(tbl)
    if tbl.associative then
        local t={}
        for k,v in pairs(tbl) do
            if k~="associative" then
                table.insert(t,v)
            end
        end
        local m=t[math.random(#t)]
        if type(m)=="table" then
            return m[math.random(#m)]
        else
            return m
        end
    else
        return tbl[math.random(#tbl)]
    end
end

function selectMap(map,category)
    if map then
        if category and maps[category] and (maps[category][tonumber(map)] or maps[category][map]) then
            local m=maps[category][tonumber(map)] or maps[category][map]
            if type(m)=="table" then m=table.random(m,false,true) end
            playMap(m)
        elseif category and maps[category] then
            playMap(randomMap(maps[category]))
        elseif category and modes[tostring(category):lower()] then
            tempMode=category:lower()
            playMap(map)
        elseif maps[map] then
            playMap(randomMap(maps[map]))
        else
            playMap(map)
        end
    else
        if mapInfo.queue[1] then
            playMap(mapInfo.queue[1].map)
            tfm.exec.chatMessagePublic("loadingmap",players,mapInfo.queue[1].map,mapInfo.queue[1].name)
            table.remove(mapInfo.queue,1)
        else
            local tbl={} for k,v in pairs(SETTINGS.ROTATION) do table.insert(tbl,k) end
            local category=tbl[math.random(#tbl)]
            playMap(randomMap(maps[category]) or 0)
        end
    end
end

function playMap(map)
    local timeSinceLastLoad=os.time()-mapInfo.lastLoad
    if timeSinceLastLoad<=3000 then
        --tfm.exec.chatMessage("Error. Map trying to reload: "..map)
        local timeUntilNextLoad=3000-timeSinceLastLoad
        if timeUntilNextLoad<1000 then timeUntilNextLoad=1000 end
        if mapInfo.timer then system.removeTimer(mapInfo.timer) mapInfo.timer=nil end
        mapInfo.timer=system.newTimer(function(...)
            local arg={...}
            --tfm.exec.chatMessage("Reloading "..tostring(arg[2]))
            playMap(arg[2])
        end,timeUntilNextLoad,false,map,map)
    else
        --tfm.exec.chatMessage("Map "..map.." loaded!")
        tfm.exec.newGame(map,false)
        mapInfo.lastLoad=os.time()
    end
end

function parseMapXML()
    local m={
        loaded=false,
        segments={},
        grounds={},
        decorations={},
        spawns={},
        shamspawns={},
        holes={},
        cheese={},
        length=800,
        height=400,
        code=tonumber(string.match(tfm.get.room.currentMap,"%d+")) or 0,
        wind=0,
        gravity=10,
    }
    if #tostring(m.code)<=3 and tfm.get.room.currentMap~="@0" then
        m.mode="vanilla"
    else
        m.xml=tfm.get.room.xmlMapInfo.xml
        local g=getValueFromXML
        local P=m.xml:match('<C><P (.-) /><Z>') or ""
        m.perm=tfm.get.room.xmlMapInfo.permCode
        m.author=g(P,"author") or tfm.get.room.xmlMapInfo.author or "Tigrounette"
        m.title=g(P,"title")
        m.id=g(P,"id")
        m.length=g(P,"L") or 800
        m.height=g(P,"H") or 400
        m.reload=g(P,"reload") and true or false
        local bg=g(P,"bg")
        if bg then m.bg=getBackgrounds(bg,".jpg") end
        local bg=g(P,"bg")
        if fg then m.bg=getBackgrounds(fg,".png") end
        local wg=g(P,"G")
        if wg and #wg>2 then
            wg=string.split(wg,",")
            m.wind=tonumber(wg[1]) or 0
            m.gravity=tonumber(wg[2]) or 10
        end
        local segmentstr=g(P,"segments")
        if segmentstr then
            for k,v in pairs(string.split(segmentstr,",")) do
                m.segments[v]=true
            end
        end
        m.collision=g(P,"C") and true
        m.soulmate=g(P,"A") and true
        m.nightmode=g(P,"N") and true
        m.aie=g(P,"aie") and true
        m.portals=g(P,"P") and true
        m.mgoc=g(P,"mgoc")
        for ground in m.xml:gmatch("<S [^/]+/>") do
            local P=string.split(g(ground,"P"),",")
            table.insert(m.grounds,{
                id=#m.grounds+1,
                x=g(ground,"X"),
                y=g(ground,"Y"),
                height=g(ground,"H"),
                length=g(ground,"L"),
                type=g(ground,"T"),
                color=g(ground,"o"),
                dynamic=tonumber(P[1]),
                mass=tonumber(P[2]),
                friction=tonumber(P[3]),
                restitution=tonumber(P[4]),
                rotation=tonumber(P[5]),
            })
        end
        local openingP=true
        for decoration in m.xml:gmatch("<P[^/]+/>") do
            if not openingP then
                local P=string.split(g(decoration,"P"),",")
                table.insert(m.decorations,{
                    id=g(decoration,"T"),
                    x=g(decoration,"X"),
                    y=g(decoration,"Y"),
                    color=C,
                    flip=P[2]=="1" and true or nil
                })
            end
            openingP=nil
        end
        for spawn in m.xml:gmatch("<DS [^/]+/>") do
            table.insert(m.spawns,{
                x=g(spawn,"X"),
                y=g(spawn,"Y"),
            })
        end
        --[[
        local multispawns=g(P,"DS")
        if multispawns then
            multispawns=string.split(multispawns,",")
            for i=1,#multispawns,2 do
                if tonumber(multispawns[i]) and tonumber(multispawns[i+1]) then
                    tableinsert(m.spawns,{
                        x=tonumber(multispawns[i]),
                        y=tonumber(multispawns[i+1])
                    })
                end
            end
        end
        ]]
        for spawn in m.xml:gmatch("<DC [^/]+/>") do
            table.insert(m.shamspawns,{
                x=g(spawn,"X"),
                y=g(spawn,"Y"),
            })
        end
        for spawn in m.xml:gmatch("<DC2 [^/]+/>") do
            table.insert(m.shamspawns,{
                x=g(spawn,"X"),
                y=g(spawn,"Y"),
            })
        end
        for hole in m.xml:gmatch("<T [^/]+/>") do
            table.insert(m.holes,{
                x=g(hole,"X"),
                y=g(hole,"Y"),
            })
        end
    end
    return m
end

function getValueFromXML(str,attribute)
    return tonumber(str:match(('%s="([^"]+)"'):format(attribute))) or str:match(('%s="([^"]+)"'):format(attribute)) or str:match(('%s=""'):format(attribute))
end

function getBackgrounds(str,extension)
    local imgs={}
    for _,bg in ipairs(string.split(str,";")) do
        local t={img="",x=0,y=0}
        for i,s in ipairs(string.split(bg,",")) do
            if i==1 then t.img=s
            elseif i==2 and tonumber(s) then t.x=tonumber(s)
            elseif i==3 and tonumber(s) then t.y=tonumber(s) end
        end
        if not t.img:find("%.") then
            t.img=t.img..extension
        end
        --if #t.img>11 then
            table.insert(imgs,t)
        --end
    end
    return #imgs>0 and imgs or nil
end

function setMapName()
    if map.id then
        ui.setMapName("<J>"..map.id)
    elseif map.title and map.author then
        ui.setMapName("<J>"..map.title.." <BL>- "..map.author)
    elseif map.title then
        ui.setMapName("<J>"..map.title)
    elseif map.author and map.author~=tfm.get.room.xmlMapInfo.author then
        ui.setMapName("<J>"..map.author.." <BL>- "..map.code)
    end
end


--[[ src/lib/misc.lua ]]--


function hexColorEntering(letter)
    local fn=function(player,down,x,y)
        if ranks[player.name]>=RANKS.ROOM_ADMIN and down and player.draw.enteringColor then
            _S.draw.addHexCharToColor(player,letter)
        end
    end
    return fn
end

function highscore()
    local hiscore={0}
    for name,player in pairs(tfm.get.room.playerList) do
        if player.score>=hiscore[1] then
            hiscore={player.score,name}
        end
    end
    return hiscore[2]
end

function hearts(player)
    local width=16
    for i=1,#hearts do
        tfm.exec.removeImage(hearts[i])
    end
    player.hearts={}
    if player.hearts then
        local s=#player.hearts.count*(width+3)
        for i=1,#player.hearts do
            table.insert(player.hearts,tfm.exec.addImage("ndStXBw.png","$"..player.name,-(s/2)+(i*(width+3))-((width/2)*i),-50))
        end
    end
end

function translate(str,lang)
    lang=lang or "en"
    return translations[lang] and translations[lang][str] or translations["en"][str] or str or "Error"
end

function tfm.exec.chatMessagePublic(str,players,...)
    local arg={...}
    if arg and arg[1] then
        for n,p in pairs(players) do
            tfm.exec.chatMessage(translate(str,p.lang):format(...),p.name)
        end
    else
        for n,p in pairs(players) do
            tfm.exec.chatMessage(translate(str,p.lang),p.name)
        end
    end
end

function getColor(color)
    if color and color:sub(1,1)=="#" then color=color:sub(2) end
    if color and tonumber(color,16) then
        color=tonumber(color,16)
        if color==0 then color=1 end
        return color
    elseif color and _S.draw.colors[color:lower()] then
        return _S.draw.colors[color:lower()].color
    end
end

function sortScores()
    local tbl={}
    for k,v in pairs(tfm.get.room.playerList) do
        table.insert(tbl,{name=v.playerName,score=v.score})
    end
    table.sort(tbl,function(i,v) return i.score>v.score end)
    return tbl
end

function playersAlive()
    local i=0
    for n,p in pairs(tfm.get.room.playerList) do
        if not p.isDead then
            i=i+1
        end
    end
    return i
end

function shouldBeAdmin(player)
    local hashTag = getHashTag(player.name)
    local isStaff = hashTag == '0001' or hashTag == '0010' or hashTag == '0015' or hashTag == '0020'
    local isTribeRoom = string.byte(tfm.get.room.name, 2) == 3
    local roomName = getInternalRoomName()

    if isStaff then
        return true
    end

    if isTribeRoom and player.tribeName and roomName:lower() == player.tribeName:lower() then
        return true
    end

    if player.tribeName and roomName:lower() == player.tribeName:lower() then
        return true
    end

    if roomName:lower() == player.name:lower() then
        return true
    end

    if getHashTag(player.name) == '0000' and roomName:lower() == getNameWithoutHashTag(player.name):lower() then
        return true
    end

    return false
end

function getInternalRoomName()
    local isTribeRoom = string.byte(tfm.get.room.name, 2) == 3
    local roomName = tfm.get.room.name

    if isTribeRoom then
        return tfm.get.room.name:sub(2)
    end

    if tfm.get.room.name:sub(1,2)=="e2" then
        roomName = tfm.get.room.name:sub(3)
    end

    roomName = roomName:match("[%d% ]+(.+)$")

    if roomName == nil then
        return ""
    end

    return roomName
end

function getHashTag(name)
    local hashTag = name:match("#(.*)")

    if (hashTag == nil) then
        return '0000'
    end

    return hashTag
end

function getNameWithoutHashTag(name)
    local nameWithoutHashTag = name:match("(.*)#")

    if (nameWithoutHashTag == nil) then
        return name
    end

    return nameWithoutHashTag
end


--[[ src/lib/notify-listeners.lua ]]--

-- Notify each active segment that an event has occured

function initNotifyOrder(event)
    local segmentNames={}
    for sn,s in pairs(_S) do
        if s.callbacks[event] then
            local niceness=s.callbacks[event].pr or 20
            table.insert(segmentNames, {sn, niceness})
        end
    end
    table.sort(segmentNames, function(a,b)
        return a[2] < b[2]
    end)
    notifyOrder[event]=segmentNames
end

function notifyListeners(f,prioritized)
    if prioritized then
        for _,no in ipairs(notifyOrder[prioritized]) do
            local sn=no[1]
            local s=_S[sn]
            if f(sn,s) then break end
        end
    else
        local stop=f("global",_S.global)
        for sn,s in pairs(_S) do
            if stop then break end
            if sn~="global" then
                stop=f(sn,s)
            end
        end
    end
end

function notifyNameListeners(name,f,prioritized)
    local player=players[name]
    if prioritized then
        for _,no in ipairs(notifyOrder[prioritized]) do
            local sn=no[1]
            local s=_S[sn]
            if player and (player.activeSegments[sn] or (map.segments and map.segments[sn])) then
                if f(player,sn,s) then break end
            end
        end
    else
        local stop=f(player,"global",_S.global)
        for sn,s in pairs(_S) do
            if stop then break end
            if sn~="global" then
                if player and (player.activeSegments[sn] or (map and map.segments and map.segments[sn])) then
                    stop=f(player,sn,s)
                end
            end
        end
    end
end


--[[ src/lib/segments.lua ]]--

function toggleSegment(name,segment,active)
    if active then
        activateSegment(name,segment)
    else
        deactivateSegment(name,segment)
    end
end

function activateSegment(name,segment)
    local s=_S[segment]
    if s.callbacks.keyboard then
        for key in pairs(s.callbacks.keyboard) do
            system.bindKeyboard(name,key,true,true)
            system.bindKeyboard(name,key,false,true)
        end
    end
    if s.onEnable then
        s.onEnable(players[name])
    end
    players[name].activeSegments[segment]=true
    _S.global.showMenu(name)
end

function deactivateSegment(name,segment)
    local s=_S[segment]
    local mouse
    local keys={}
    if s.callbacks.keyboard then
        for key in pairs(s.callbacks.keyboard) do
            keys[key]=true
        end
        
        -- See if anything else needs to use it, if so it won't unbind.
        for seg in pairs(players[name].activeSegments) do
            if _S[seg] and _S[seg].callbacks then
                if _S[seg].callbacks.keyboard then
                    for key in pairs(_S[seg].callbacks.keyboard) do
                        if keys[key] then
                            keys[key]=nil
                        end
                    end
                end
                if _S[seg].callbacks.mouse then
                    mouse=true
                end
            end
        end
        for key in pairs(keys) do
            system.bindKeyboard(name,key,true,false)
            system.bindKeyboard(name,key,false,false)
        end
    end
    if s.onDisable then
        s.onDisable(players[name])
    end
    players[name].activeSegments[segment]=nil
    _S.global.showMenu(name)
end

function bindChatCommands()
    for _,segment in pairs(_S) do
        if segment.callbacks and segment.callbacks.chatCommand then
            for cmd in pairs(segment.callbacks.chatCommand) do
                system.disableChatCommandDisplay(cmd,true)
            end
        end
    end
end

function defaultToggleSegmentChatCallback(segment)
    local fn=function(player,...)
        local arg={...}
        if arg[1] and (arg[1] == "on" or arg[1] == "off") then
            table.insert(arg, 1, "all") -- insert "all" at 1, moving "on" or "off" to index 2
            executeCommand(player, function(a, enable)
                toggleSegment(a,segment,enable == "on")
            end, arg)
        else
            executeCommand(player, function(a)
                toggleSegment(a,segment,not players[a].activeSegments[segment])
            end, arg)
        end
    end
    return fn
end

function executeCommand(player,f,arg)
    local getTargets = function()
        local ret = {}
        local addMe = function(args)
            ret[player.name] = args
        end
        local str = arg[1]
        if str then
            if str == "all" or str == "*" then
                table.remove(arg, 1)
                for n in pairs(players) do
                    ret[n] = arg
                end
            elseif str == "me" then
                table.remove(arg, 1)
                addMe(arg)
            else
                local i = 0
                for j,a in ipairs(arg) do
                    local n = tonumber(a) or upper(a)
                    if players[n] then
                        ret[n] = true
                    else
                        break
                    end
                    i = j
                end
                if i == 0 then
                    addMe(arg)
                else
                    local tmp = {}
                    for k = 1, #arg - i do
                        tmp[k] = arg[i + k]
                    end
                    for n in pairs(ret) do
                        ret[n] = tmp
                    end
                end
            end
        else
            addMe(arg)
        end
        return ret
    end
    local targets = getTargets() -- of the shape {name1={rest of the arguments}, name2={...}, name3={...}, ...}
    -- if there are no more non-name arguments it will be {name1=true, name2=true, name3=true} mesa thinks
    for t,args in pairs(targets) do
        f(t, unpack(args)) -- t is targetName, followed by rest of arguments
    end
    return targets
end


--[[ src/events/eventChatCommand.lua ]]--

function eventChatCommand(name,message)
    local args = string.split(message, "%s")
    local cmd = table.remove(args, 1)

    notifyNameListeners(name, function(player,sn,s)
        if s.callbacks.chatCommand then
            local cb=s.callbacks.chatCommand[cmd]
            if cb then
                local privLevel=cb.rank or 1
                if ranks[name]>=privLevel then
                    if not cb.hide  then
                        for pn,r in pairs(ranks) do
                            if players[pn] and r>=RANKS.ROOM_ADMIN then
                                tfm.exec.chatMessage("<font color='#AAAAAA'>&#926; ["..name.."] !"..message.."</font>",pn)
                            end
                        end
                    end
                    cb.fnc(player, unpack(args))
                else
                    tfm.exec.chatMessage(translate("nocmdperms",player.lang),name)
                end
            end
        end
    end)
end


--[[ src/events/eventChatMessage.lua ]]--

function eventChatMessage(name,message)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.chatMessage
        if cb then
            cb(player)
        end
    end)

    -- Dad jokes galore!
    if math.random(1,200)==1 then
        local lowermessage=message:lower()
        for _,im in ipairs({"i'm ","im ","i'm","im"}) do
            local found=lowermessage:find(im)
            if found and #message>#im then
                tfm.exec.chatMessage("<V>[Dad] <N>Hi "..message:sub(found+#im)..", I'm dad!</b>",name)
                break
            end
        end
    end
end


--[[ src/events/eventColorPicked.lua ]]--

function eventColorPicked(id, name, color)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        if s.callbacks.colorPicked then
            local cb=s.callbacks.colorPicked
            if cb then
                cb(player,id,color)
            end
        end
    end)
end


--[[ src/events/eventEmotePlayed.lua ]]--

function eventEmotePlayed(name,emote,param)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.emotePlayed
        if cb then
            cb(player,emote,param)
        end
    end)
end


--[[ src/events/eventKeyboard.lua ]]--

function eventKeyboard(name,key,down,x,y)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        if s.callbacks.keyboard then
            local cb=s.callbacks.keyboard[key]
            if cb then
                cb(player,down,x,y)
            end
        end
    end)
end


--[[ src/events/eventLoop.lua ]]--

function eventLoop(time,remaining)
    currentTime=remaining
    -- Notify listeners
    notifyListeners(function(sn,s)
        if not s.disabled or (map.segments and map.segments[sn]) then
            local cb=s.callbacks.eventLoop
            if cb then
                cb(time,remaining)
            end
        end
    end)
    if not SETTINGS.DISABLEAUTONEWGAME and remaining<=0 and not map.mode == "tribehouse" then
        selectMap()
    end
    if SETTINGS.QUICKRESPAWN and not (map.reload and tfm.get.room.currentMap~=0) and time>=3000 then
        local tbl={}
        for n,t in pairs(toRespawn) do
            if t<=os.time()-1000 then
                tfm.exec.respawnPlayer(n)
            else
                tbl[n]=t
            end
        end
        toRespawn=tbl
    end
    for key,segment in pairs(_S) do
        if segment.toDespawn then
            for i = #segment.toDespawn, 1, -1 do
                local object = segment.toDespawn[i]
                if object.despawn<=os.time() then
                    tfm.exec.removeObject(object.id)
                    table.remove(segment.toDespawn,i)
                end
            end
        end
    end
end


--[[ src/events/eventMouse.lua ]]--

function eventMouse(name,x,y)
    -- Initialize notify order
    if not notifyOrder.mouse then
        initNotifyOrder("mouse")
    end
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.mouse
        if cb then
            if cb.fnc(player,x,y) then return true end
        end
        return false
    end, "mouse")
end


--[[ src/events/eventNewGame.lua ]]--

function eventNewGame()
    if timerID then
        system.removeTimer(timerID)
    end
    map=parseMapXML()
    if tempMode then map.mode=tempMode end
    if map.reload and (map.code and map.code~=0) then
        system.newTimer(function() selectMap(tfm.get.room.xmlMapInfo.xml) end,3000,false)
    else
        if map.mode and modes[map.mode] then
            map.segments[modes[map.mode]]=true
        end
    end
    if not map.mode then map.mode="tribehouse" end
    tempMode=nil
    for name,player in pairs(players) do
        player.facingRight=true
        player.lastSpawn=os.time()
    end
    
    if SETTINGS.VOTE_TIME then
        SETTINGS.VOTE_TIME.votes={}
    end
    
    if SETTINGS.VOTE_SKIP then
        SETTINGS.VOTE_SKIP.votes={}
        SETTINGS.VOTE_SKIP.skipped=nil
    end
    
    -- Notify listeners
    notifyListeners(function(sn,s)
        if not s.disabled or (map.segments and map.segments[sn]) then
            local cb=s.callbacks.newGame
            if cb then
                cb()
            end
        end
    end)
    for key,segment in pairs(_S) do
        if segment.toDespawn then
            segment.toDespawn={}
        end
    end
    setMapName()
    
    if map.bg then for i,image in pairs(map.bg) do tfm.exec.addImage(image.img,"?"..(1-i),image.x,image.y) end end
    if map.fg then for i,image in pairs(map.fg) do tfm.exec.addImage(image.img,"!"..(50+i),image.x,image.y) end end
end


--[[ src/events/eventNewPlayer.lua ]]--

function eventNewPlayer(name)
    local player={
        activeSegments={},
        name=name,
        lang=tfm.get.room.playerList[name].community or "en"
    }
    if tfm.get.room.playerList[name].tribeName then
        player.tribeName = tfm.get.room.playerList[name].tribeName
    end
    if not ranks[name] then
        ranks[name]=1
    end
    -- Combine defaultPlayer with player
    for _,s in pairs(_S) do
        if s.defaultPlayer then
            s.defaultPlayer(player)
        end
    end
    players[name]=player
    tfm.exec.lowerSyncDelay(name)

    -- Activates segments
    for sn,s in pairs(_S) do
        if player.activeSegments[sn] then
            activateSegment(name,sn)
        end
    end

    -- Show recent changelog
    if showChangelog(7,3,player) then
        tfm.exec.chatMessage(showChangelog(7,3,player),name)
    end

    -- Show random greeting message
    local greets=translate("greetings",player.lang)
    tfm.exec.chatMessage("<J>"..greets[math.random(#greets)],name)

    if (shouldBeAdmin(player)) then
        ranks[name] = 4
    end

    if not ranks[name] then
        tfm.exec.chatMessage("ok")
        ranks[name]=1
    end

    if ranks[name]>=RANKS.ROOM_ADMIN then
        for n,r in pairs(ranks) do
            if r>=RANKS.ROOM_ADMIN and players[n]then
                tfm.exec.chatMessage("<font color='#AAAAAA'>&#926; ["..upper(moduleName).."] "..(translate("joinedroom",players[n].lang):format(name)).."</font>",n)
           end
       end
    end

    system.bindMouse(name,true)

    -- Notify listeners
    notifyListeners(function(sn,s)
        if not s.disabled or (map.segments and map.segments[sn]) then
            local cb=s.callbacks.newPlayer
            if cb then
                cb(player)
            end
        end
    end)
    if SETTINGS.QUICKRESPAWN then
        toRespawn[name]=os.time()
    end
    if _S.global.tempMapName then
        ui.setMapName("<J>".._S.global.tempMapName)
    else
        setMapName()
    end
    if map.bg then for i,image in pairs(map.bg) do tfm.exec.addImage(image.img,"?"..(1-i),image.x,image.y) end end
    if map.fg then for i,image in pairs(map.fg) do tfm.exec.addImage(image.img,"!"..(50+i),image.x,image.y) end end
end


--[[ src/events/eventPlayerDied.lua ]]--

function eventPlayerDied(name)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.playerDied
        if cb then
            cb(player)
        end
    end)
    if SETTINGS.QUICKRESPAWN then
        toRespawn[name]=os.time()
    end
end


--[[ src/events/eventPlayerGetCheese.lua ]]--

function eventPlayerGetCheese(name)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.playerGetCheese
        if cb then
            cb(player)
        end
    end)
end


--[[ src/events/eventPlayerLeft.lua ]]--

function eventPlayerLeft(name)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.playerLeft
        if cb then
            cb(player)
        end
    end)
    if ranks[name]>=RANKS.ROOM_ADMIN then
        for n,r in pairs(ranks) do
            if r>=RANKS.ROOM_ADMIN and players[n] then
                tfm.exec.chatMessage("<font color='#AAAAAA'>&#926; ["..upper(moduleName).."] "..(translate("leftroom",players[n].lang):format(name)).."</font>",n)
           end
       end
    end
    players[name]=nil
end


--[[ src/events/eventPlayerRespawn.lua ]]--

function eventPlayerRespawn(name)
    players[name].facingRight=true
    players[name].lastSpawn=os.time()
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.playerRespawn
        if cb then
            cb(player)
        end
    end)
end


--[[ src/events/eventPlayerVampire.lua ]]--

function eventPlayerVampire(name)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.playerVampire
        if cb then
            cb(player)
        end
    end)
end


--[[ src/events/eventPlayerWon.lua ]]--

function eventPlayerWon(name)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.playerWon
        if cb then
            cb(player)
        end
    end)
    if SETTINGS.QUICKRESPAWN then
        toRespawn[name]=os.time()
    end
end


--[[ src/events/eventRoundEnd.lua ]]--

-- Execute the eventRoundEnd() function to act as a pseudo-event whenever tfm.exec.newGame is executed

_newGame=tfm.exec.newGame
function tfm.exec.newGame(map,flip) eventRoundEnd() _newGame(map,flip) end

function eventRoundEnd()
    notifyListeners(function(sn,s)
        if not s.disabled or (map.segments and map.segments[sn]) then
            local cb=s.callbacks.roundEnd
            if cb then
                cb()
            end
        end
    end)
end


--[[ src/events/eventSummoningEnd.lua ]]--

function eventSummoningEnd(name, type, x, y, ang, other)
    -- Notify listeners
    SPAWNEDOBJS[other.id] = true
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.summoningEnd
        if cb then
            cb(player,type,x,y,ang,other)
        end
    end)
end


--[[ src/events/eventSummoningStart.lua ]]--

function eventSummoningStart(name, type, x, y, ang)
    -- Notify listeners
    notifyNameListeners(name, function(player,sn,s)
        local cb=s.callbacks.summoningStart
        if cb then
            cb(player,type,x,y,ang)
        end
    end)
end


--[[ src/events/eventTextAreaCallback.lua ]]--

function eventTextAreaCallback(id,name,callback)
    local arg = string.split(callback, "%s")
    if arg[1] and _S[arg[1]] then
        local s=_S[arg[1]]
        local cb=s.callbacks.textArea
        if cb and cb[arg[2]] then
            cb[arg[2]](id,name,arg)
        end
    end
end


--[[ src/segments/_global.lua ]]--

-- Global segment that's always active and contains default functionality

_S.global = {
    defaultPlayer=function(player)
        player.activeSegments.global=true
        player.ctrl=false
        player.shift=false
        player.facingRight=true
        player.selected={}
    end,
    menu=function(player)
        return {
            --title="Help"
            {callback="global help",icon="?",width=40},
            {callback="global players",icon="Players",width=60},
        }
    end,
    menuCondition=function(player) return true end,
    shamObjects={},
    tempMapName=nil,
    selectPlayer=function(player,selection,x,y)
        if selection then
            if player.selected[selection] then
                player.selected[selection]=nil
            else
                player.selected[selection]=true
            end
        end
        local str=("<a href='event:global select all %s %s'>[%s]</a> <a href='event:global select none %s %s'>[%s]\n</a>"):format(x,y,translate("all",player.lang),x,y,translate("none",player.lang))
        local total=0
        local selected=0    
        for n,p in pairs(players) do
            str=str.."\n<font color='#"..(player.selected[n] and "2ECF73" or "C2C2DA").."'><a href='event:global select "..n.." "..x.." "..y.."'>"..(ranks[n]>=RANKS.ROOM_ADMIN and "★ " or "")..n.."</a>"
            total=total+1
            if player.selected[n] then
                selected=selected+1
            end
        end
        ui.addTextArea(0,str,player.name,x,y,nil,nil,nil,nil,0.5,true)
    end,
    callbacks={
        newGame=function()
            _S.global.tempMapName=nil
        end,
        newPlayer=function(player)
            _S.global.showMenu(player.name)
        end,
        playerLeft=function(player)
            for n,p in pairs(players) do
                if p.selected[player.name] then
                    p.selected[player.name]=nil
                end
            end
        end,
        textArea={
            players=function(id,name,arg)
                if players[name].dropdown and players[name].dropdown=="players" then
                    players[name].dropdown=nil
                    ui.removeTextArea(0,name)
                else
                    local ta
                    for k,v in pairs(players[name].menu) do
                        if v.id==id then ta=v end
                    end
                    _S.global.selectPlayer(players[name],nil,ta.x,ta.y+30)
                    players[name].dropdown="players"
                end
            end,
            select=function(id,name,arg)
                local x,y=tonumber(arg[4]),tonumber(arg[5])
                if arg[3]=="all" then
                    for n,p in pairs(tfm.get.room.playerList) do
                        players[name].selected[n]=true
                    end
                    _S.global.selectPlayer(players[name],nil,x,y)
                elseif arg[3]=="none" then
                    for n,p in pairs(tfm.get.room.playerList) do
                        players[name].selected[n]=nil
                    end
                    _S.global.selectPlayer(players[name],nil,x,y)
                elseif tfm.get.room.playerList[arg[3]] then
                    _S.global.selectPlayer(players[name],arg[3],x,y)
                end
            end
        },
        keyboard={
            [KEYS.SHIFT]=function(player,down,x,y)
                player.shift=down
            end,
            [KEYS.CTRL]=function(player,down,x,y)
                player.ctrl=down
            end,
            [KEYS.LEFT]=function(player,down,x,y)
                if down then player.facingRight=false end
            end,
            [KEYS.RIGHT]=function(player,down,x,y)
                if down then player.facingRight=true end
            end,
            [KEYS.UP]=function(player,down,x,y) end,
            [KEYS.DOWN]=function(player,down,x,y) end,
            [KEYS.DELETE]=function(player,down,x,y)
                tfm.exec.killPlayer(player.name)
            end,
        },
        mouse={
            pr=-20,
            fnc=function(player,x,y)
                if _S.omo and _S.omo.welcomed[player.name] then
                    local id=_S.omo.startID
                    for i=1,10 do
                        ui.removeTextArea(id+i,player.name)
                    end
                    _S.omo.welcomed[player.name]=nil
                elseif _S.splashScreen and _S.splashScreen.welcomed[player.name] then
                    tfm.exec.removeImage(_S.splashScreen.welcomed[player.name].img)
                    _S.splashScreen.welcomed[player.name]=nil
                elseif player.shift and player.ctrl then
                    tfm.exec.chatMessage("X:"..x.."   Y:"..y,player.name)
                    return true
                elseif player.shift then
                    -- Inspect ground.
                    return false
                elseif player.ctrl then
                    if ranks[player.name]>=RANKS.ROOM_ADMIN then
                        tfm.exec.movePlayer(player.name,x,y)
                    end
                    return true
                end
            end
        },
        chatCommand={
            win={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.giveCheese(a)
                        tfm.exec.playerVictory(a)
                    end, arg)
                end
            },
            cheese={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.giveCheese(a)
                    end, arg)
                end
            },
            victory={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.playerVictory(a)
                    end, arg)
                end
            },
            kill={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.killPlayer(a)
                    end, arg)
                end
            },
            respawn={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.respawnPlayer(a)
                    end, arg)
                end
            },
            r={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.respawn.fnc(player,...) end},
            vampire={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.setVampirePlayer(a)
                    end, arg)
                end
            },
            vamp={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.vampire.fnc(player,...) end},
            meep={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a, power)
                        tfm.exec.giveMeep(a)
                        players[a].meepPower=tonumber(power) or false
                        players[a].meepTimer=os.time()
                        toggleSegment(a, "meep", not tonumber(arg[1]) and not players[a].activeSegments.meep or tonumber(arg[1]))
                        if not players[a].activeSegments.meep then tfm.exec.chatMessage(translate("meepremovednextround",player.lang), a) end
                    end, arg)
                end
            },
            shaman={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(a)
                        tfm.exec.setShaman(a)
                    end, arg)
                end
            },
            sham={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.shaman.fnc(player,...) end},
            s={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.shaman.fnc(player,...) end},
            mort={
                rank=RANKS.ANY,
                hide=true,
                fnc=function(player)
                    tfm.exec.killPlayer(player.name)
                end
            },
            die={rank=RANKS.ANY,hide=true,fnc=function(player,...) _S.global.callbacks.chatCommand.mort.fnc(player,...) end},
            me={
                rank=RANKS.ANY,
                hide=true,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        tfm.exec.chatMessage("<V>*"..player.name.." <N>"..(table.concat(arg," ")))
                    end
                end
            },
            mod={
                rank=RANKS.STAFF,
                hide=true,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        tfm.exec.chatMessage("<ROSE><b>["..player.name.."] "..(table.concat(arg," ")).."</b>")
                    end
                end
            },
            mapname={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        _S.global.tempMapName=table.concat(arg," ")
                        ui.setMapName("<J>".._S.global.tempMapName)
                    end
                end
            },
            c={
                rank=RANKS.ROOM_ADMIN,
                hide=true,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        for n,p in pairs(tfm.get.room.playerList) do
                            if ranks[n]>=RANKS.ROOM_ADMIN then
                                tfm.exec.chatMessage("<font color='#00FFFF'>&#926; ["..player.name.."] "..(table.concat(arg," ")).."</font>",n)
                            end
                        end
                    end
                end
            },
            t={rank=RANKS.ROOM_ADMIN,hide=true,fnc=function(player,...) _S.global.callbacks.chatCommand.c.fnc(player,...) end},
            draw={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("draw")
            },
            drawonme={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("drawOnMe")
            },
            ffa={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("ffa")
            },
            lightning={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("lightning")
            },
            fly={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("fly")
            },
            dash={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("dash")
            },
            projection={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("projection")
            },
            inspect={
                rank=RANKS.ALL,
                fnc=defaultToggleSegmentChatCallback("inspect")
            },
            prophunt={
                rank=RANKS.ALL,
                fnc=defaultToggleSegmentChatCallback("prophunt")
            },
            clear={
                rank=RANKS.ROOM_ADMIN,
                fnc = function()
                    for objectId in pairs(SPAWNEDOBJS) do
                        tfm.exec.removeObject(objectId) 
                    end
                    SPAWNEDOBJS = {}
                end
            },
            name={
                rank=RANKS.ANY,
                fnc=function(player,color,...)
                    local arg={...}
                    if color then
                        if tonumber(color)==0 then
                            color=0
                        else
                            local c=getColor(color)
                            if c then
                                color=c
                            else
                                tfm.exec.chatMessage(translate("invalidargument",player.lang),player.name)
                            end
                        end
                    else
                        color=0
                    end
                    if color then
                        executeCommand(player,function(target)
                            tfm.exec.setNameColor(target,color)
                        end,arg)
                    end
                end
            },
            speed={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,power,...)
                    local arg={...}
                    power=tonumber(power) or 100
                    executeCommand(player, function(targetName)                         
                        local target = targetName and players[targetName] or player
                        target.speedPower=tonumber(power) or 100 
                        toggleSegment(target.name, "speed", not tonumber(arg[1]) and not target.activeSegments.speed or tonumber(arg[1]) and true)
                    end, arg)
                end
            },
            conj={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player,function(target,time)
                        players[target].conjTime=time or 10
                        toggleSegment(target, "conj", not arg[1] and not players[target].activeSegments.conj or arg[1] and true)
                    end,arg)
                end
            },
            emote={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player,function(target,emote)
                        if emote and tfm.enum.emote[emote:lower()] then
                            tfm.exec.playEmote(target,tfm.enum.emote[emote:lower()])
                        elseif tonumber(emote) then
                            tfm.exec.playEmote(target,tonumber(emote))
                        end
                    end,arg)
                end
            },
            flag={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player,function(target,flag)
                        tfm.exec.playEmote(target,tfm.enum.emote.flag,flag)
                    end,arg)
                end
            },
            f={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.flag.fnc(player,...) end},
            insta={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("insta")
            },
            ratapult={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("ratapult")
            },
            checkpoints={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("checkpoints")
            },
            checkpoint={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.checkpoints.fnc(player,...) end},
            cp={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.checkpoints.fnc(player,...) end},
            rainbow={
                rank=RANKS.ROOM_ADMIN,
                fnc=defaultToggleSegmentChatCallback("rainbow")
            },
            meow={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player)
                    local tbl={6356881,6780029}
                    selectMap(tbl[math.random(#tbl)])
                    for n,p in pairs(players) do
                        _S.images.selectImage(p,"pusheen")
                    end
                    tfm.exec.setGameTime(9999999)
                        _S.global.tempMapName=("%s  <BL>|  %s %s %s"):format(translate("meow"),translate("meow"),translate("meow"),translate("meow"))
                        ui.setMapName("<J>".._S.global.tempMapName)
                end
            },
            pw={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,password)
                    if password then
                        tfm.exec.setRoomPassword(password)
                        pw=password
                        tfm.exec.chatMessage("Room password changed to: "..password,player.name)
                    elseif pw then
                        tfm.exec.setRoomPassword("")
                        pw=nil
                        tfm.exec.chatMessage("Room password reset.",player.name)
                    elseif not pw then
                        tfm.exec.chatMessage("The room currently has no password.",player.name)
                    end
                end
            },
            np={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,mapid,category)
                    selectMap(mapid,category)
                end
            },
            map={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.np.fnc(player,...) end},
            maps={
                rank=RANKS.ANY,
                fnc=function(player,id)
                    local tbl={}
                    if id and maps[id] then
                        for k,v in pairs(maps[id]) do
                            if type(v)=="number" or type(v)=="string" then
                                table.insert(tbl,tostring(v))
                            elseif type(v)=="table" then
                                for kk,vv in pairs(v) do
                                    table.insert(tbl,tostring(vv))
                                end
                            end
                        end
                    else
                        for k,v in pairs(maps) do
                            table.insert(tbl,k)
                        end
                    end
                    tfm.exec.chatMessage(table.concat(tbl,","),player.name)
                end
            },
            maplist={rank=RANKS.ANY,fnc=function(player,...) _S.global.callbacks.chatCommand.maps.fnc(player,...) end},
            npp={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,mapid)
                    if mapid then
                        local alreadyQueued
                        for k,v in ipairs(mapInfo.queue) do
                            if v.map==mapid then
                                alreadyQueued=true
                                break
                            end
                        end
                        if alreadyQueued then
                            tfm.exec.chatMessage(translate("alreadyqueued",player.lang),player.name)
                        else
                            table.insert(mapInfo.queue,{map=mapid,name=player.name})
                            tfm.exec.chatMessage(translate("addedtoqueue",player.lang):format(mapid,#mapInfo.queue),player.name)
                        end
                    else
                        local tbl={}
                        for k,v in pairs(mapInfo.queue) do
                            table.insert(tbl,translate("submittedby",player.lang):format(k,v.map,v.name))
                        end
                        if tbl[1] then
                            tfm.exec.chatMessage(table.concat(tbl,"\n"),name)
                        else
                            tfm.exec.chatMessage(translate("noqueue",player.lang),player.name)
                        end
                    end
                end
            },
            queue={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.npp.fnc(player,...) end},
            q={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.npp.fnc(player,...) end},
            doll={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        player.dolls={}
                        --[[
                        for k,v in pairs(arg) do
                            if players[upper(v)] then
                                table.insert(player.dolls,upper(v))
                            end
                        end
                        ]]
                        executeCommand(player, function(target)
                            table.insert(player.dolls,upper(target))
                        end, arg)
                        activateSegment(player.name,"doll")
                    else
                        if player.activeSegments.doll then
                            deactivateSegment(player.name,"doll")
                            player.dolls=nil
                        end
                    end
                end
            },
            time={
                rank=RANKS.ANY,
                fnc=function(player,time)
                    if ranks[player.name]>=RANKS.ROOM_ADMIN then
                        if tonumber(time) then
                            tfm.exec.setGameTime((currentTime/1000)+time)
                            tfm.exec.chatMessagePublic("addedtime",players,player.name,time)
                        end
                        
                    elseif SETTINGS.VOTE_TIME then
                        local totalVotes=0
                        for _ in pairs(SETTINGS.VOTE_TIME.votes) do
                            totalVotes=totalVotes+1
                        end
                        if totalVotes<=SETTINGS.VOTE_TIME.maxVotes and not SETTINGS.VOTE_TIME.votes[player.name] then
                            tfm.exec.setGameTime((currentTime/1000)+SETTINGS.VOTE_TIME.timeToAdd,true)
                            tfm.exec.chatMessagePublic("addedtime",players,player.name,SETTINGS.VOTE_TIME.timeToAdd)
                            SETTINGS.VOTE_TIME.votes[player.name]=true
                        else
                            tfm.exec.chatMessage(translate("cantaddtime",player.lang),player.name)
                        end
                    end
                end
            },
            skip={
                rank=RANKS.ANY,
                fnc=function(player)
                    if SETTINGS.VOTE_SKIP and not SETTINGS.VOTE_SKIP.skipped then
                        if not SETTINGS.VOTE_SKIP.votes[player.name] then
                            SETTINGS.VOTE_SKIP.votes[player.name]=true
                            local total=0
                            local totalVotes=0
                            for n in pairs(players) do
                                total=total+1
                                if SETTINGS.VOTE_SKIP.votes[n] then
                                    totalVotes=totalVotes+1
                                end
                            end
                            local votesRequired=math.floor((total/1.25))
                            tfm.exec.chatMessagePublic("votedtoskip",players,player.name)
                            if totalVotes>=votesRequired then
                                SETTINGS.VOTE_SKIP.skipped=true
                                tfm.exec.chatMessagePublic("roundskipped",players)
                                tfm.exec.setGameTime(5)
                            end
                        else
                            tfm.exec.chatMessage(translate("alreadyvotedtoskip",player.lang),player.name)
                        end
                    end
                end
            },
            print={
                rank=RANKS.STAFF,
                fnc=function(player,...)
                    local arg={...}
                    local tbl=_G
                    local tmp={}
                    if arg[1] and arg[1]:find(".") then
                        tmp=string.split(arg[1],".")
                    end
                    for k,v in pairs(tmp) do
                        if tbl[tonumber(v)] or tbl[v] then
                            tbl=tbl[tonumber(v)] or tbl[v]
                        else
                            tfm.exec.chatMessage("Table doesn't exist.",player.name)
                            return
                        end
                    end
                    if type(tbl)=="string" or type(tbl)=="number" then
                        tfm.exec.chatMessage(tostring(tbl),player.name)
                    else
                        printInfo(arg[1],tbl,player.name)
                    end
                end
            },
            list={
                rank=RANKS.STAFF,
                fnc=function(player,...)
                    local arg={...}
                    local tbl=_G
                    local tmp={}
                    if arg[1] and arg[1]:find(".") then
                        tmp=string.split(arg[1],".")
                    end
                    for k,v in pairs(tmp) do
                        if tbl[tonumber(v)] or tbl[v] then
                            tbl=tbl[tonumber(v)] or tbl[v]
                        else
                            tfm.exec.chatMessage("Table doesn't exist.",player.name)
                            return
                        end
                    end
                    if arg[1] and type(tbl)=="table" then
                        local t={}
                        for k,v in pairs(tbl) do
                            table.insert(t,k)
                        end
                        tfm.exec.chatMessage(table.concat(t,", "),player.name)
                    else
                        tfm.exec.chatMessage("Not a table.",player.name)
                    end
                end
            },
            set={
                rank=RANKS.STAFF,
                fnc=function(player,...)
                    local arg={...}
                    local tbl=_G
                    local tmp={}
                    if arg[1] and arg[1]:find(".") then
                        tmp=string.split(arg[1],".")
                    end
                    for k,v in ipairs(tmp) do
                        local key=tbl[tonumber(v)] and tonumber(v) or v
                        if tmp[k+1] then
                            if tbl[key] then
                                tbl=tbl[key]
                            else
                                tfm.exec.chatMessage("Table doesn't exist.",player.name)
                                break
                            end
                        else
                            if arg[2] then
                                local newval=tonumber(arg[2]) or (arg[2]=="nil" and nil) or (arg[2]=="{}" and {}) or (arg[2]=="true" and true) or (arg[2]=="false" and false) or arg[2]
                                tbl[key]=newval
--                                  tbl=tonumber(arg[2]) or (arg[2]=="nil" and nil) or (arg[2]=="true" and true) or (arg[2]=="false" and false) or arg[2]
                                tfm.exec.chatMessage("Variable set: "..arg[2],player.name)
                            else
                                tfm.exec.chatMessage("No variable set.",player.name)
                            end
                        end
                    end
                end
            },
            skills={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,toggle)
                    local skillsDisabled=SETTINGS.SKILLS
                    if not toggle then
                        if SETTINGS.SKILLS then SETTINGS.SKILLS=false else SETTINGS.SKILLS=true end
                    elseif toggle=="on" then SETTINGS.SKILLS=true
                    elseif toggle=="off" then SETTINGS.SKILLS=false
                    end
                    
                    tfm.exec.disableAllShamanSkills(SETTINGS.SKILLS)
                    tfm.exec.chatMessagePublic(SETTINGS.SKILLS and "enabled" or "disabled",players)
                end
            },
            snow={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,time)
                    tfm.exec.snow(time or 60,10)
                end
            },
            changelog={
                rank=RANKS.ANY,
                fnc=function(player,days,num)
                    if showChangelog(days,num,player) then
                        tfm.exec.chatMessage(showChangelog(days,num,player),player.name)
                    else
                        tfm.exec.chatMessage("There are no recent things in the changelog.",player.name)
                    end
                end
            },
            xml={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        selectMap(table.concat(arg," "):gsub("&lt;","<"))
                    elseif ranks[player.name]>=RANKS.STAFF then
                        local splitnum=800
                        for i=1,#map.xml,splitnum do
                            tfm.exec.chatMessage("<font size='8'>"..(string.sub(map.xml,i,i+splitnum-1)):gsub("<","&lt;").."</font>",player.name)
                        end
                    end
                end
            },
            admins={
                rank=RANKS.ANY,
                fnc=function(player)
                    local t={}
                    for n in pairs(players) do
                        if ranks[n] and ranks[n]>=RANKS.ROOM_ADMIN then
                            table.insert(t,n)
                        end
                    end
                    if #t>0 then
                        tfm.exec.chatMessage(table.concat(t,", "),player.name)
                    else
                        tfm.exec.chatMessage(translate("noadmins",player.lang),player.name)
                    end
                end
            },
            admin={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        executeCommand(player, function(a)
                            if players[a] and ranks[a] then
                                if ranks[a]<RANKS.ROOM_ADMIN then
                                    ranks[a]=RANKS.ROOM_ADMIN
                                    tfm.exec.chatMessagePublic("isnowadmin",players,a)
                                else
                                    tfm.exec.chatMessage(translate("hashigherrank",player.lang):format(a),player.name)
                                end
                            end
                        end, arg)
                    end
                end
            },
            unadmin={
                rank=RANKS.ROOM_OWNER,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1] then
                        executeCommand(player, function(a)
                            if players[a] and ranks[a] then
                                if ranks[a]<ranks[player.name] and ranks[a]==RANKS.ROOM_ADMIN then
                                    ranks[a]=RANKS.ANY
                                    tfm.exec.chatMessagePublic("isnoadmin",players,a)
                                else
                                    tfm.exec.chatMessage(translate("hashigherrank",player.lang):format(a),player.name)
                                end
                            end
                        end, arg)
                    end
                end
            },
            deadmin={rank=RANKS.ROOM_OWNER,fnc=function(player,...) _S.global.callbacks.chatCommand.lock.fnc(player,...) end},
            lock={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,num)
                    num=tonumber(num) or (num and false)  or 1
                    if tonumber(num) and (num>=1 and num<=50) then
                        tfm.exec.setRoomMaxPlayers(num)
                        tfm.exec.chatMessagePublic("roomlimit",players,num)
                    else
                        tfm.exec.chatMessage(translate("invalidargument",player.lang),player.name)
                    end
                end
            },
            unlock={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.lock.fnc(player,50) end},
            score={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(target, score)
                        tfm.exec.setPlayerScore(target, score)
                    end, arg)
                end
            },
            tp={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    local x,y
                    for k,v in pairs(arg) do
                        if tonumber(v) then
                            if not x then x=v elseif not y then y=v end
                        end
                    end
                    if not x and not y then
                        player.activeSegments.tp=true
                        player.tp={}
                        executeCommand(player, function(target)
                            table.insert(player.tp,target)
                        end, arg)
                    else
                        executeCommand(player, function(target,x,y)
                            tfm.exec.movePlayer(target, x, y)
                        end, arg)
                    end
                end
            },
            spawn={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,id,x,y,num,angle,vx,vy)
                    if id then
                        local o={}
                        id=tfm.enum.shamanObject[id] or id
                        num=tonumber(num) or 1
                        if num>15 then num=15 elseif num<0 then num=0 end
                        for i=1,num do
                            table.insert(o,tfm.exec.addShamanObject(id,tonumber(x) or tfm.get.room.playerList[player.name].x, tonumber(y) or tfm.get.room.playerList[player.name].y,tonumber(angle) or 0,tonumber(vx) or 0,tonumber(vy) or 0))
                        end
                        return o
                    else
                        tfm.exec.chatMessage(translate("enterobjectid",player.lang),player.name)
                    end
                end
            },
            addspawn={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,id,x,y,num,angle,vx,vy,interval,despawn)
                    if id then
                        id=tfm.enum.shamanObject[id] or id
                        num=tonumber(num) or 1
                        if num>10 then num=10 elseif num<0 then num=0 end
                        for i=1,num do
                            table.insert(_S.addspawn.toSpawn, {name=player.name, type=tonumber(id), x=tonumber(x) or 0, y=tonumber(y) or 0, ang=tonumber(ang) or 0,vx=tonumber(vx) or 0, vy=tonumber(vy) or 0, interval=tonumber(interval) or 6, tick=0, despawn=tonumber(despawn) or 120})
                        end
                    else
                        toggleSegment(player.name, "addspawn", not players[player.name].activeSegments.addspawn)
                    end
                end
            },
            clearspawns={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player)
                    _S.addspawn.toSpawn={}
                end
            },
            removespawns={rank=RANKS.ROOM_ADMIN,fnc=function(player,...) _S.global.callbacks.chatCommand.clearspawns.fnc(player,...) end},
            disco={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player)
                    _S.disco.disabled = not _S.disco.disabled
                    if _S.disco.disabled then
                        for n,p in pairs(tfm.get.room.playerList) do
                            tfm.exec.setNameColor(n,0)
                        end
                    end
                end
            },
            doge={
                rank=RANKS.ROOM_ADMIN,
                fnc=function()
                    _S.doge.disabled = not _S.doge.disabled 
                    if _S.doge.disabled then
                        ui.removeTextArea(-50)
                    end
                end
            },
            treelights={
                rank=RANKS.ROOM_ADMIN,
                fnc=function()
                    _S.treelights.disabled = not _S.treelights.disabled
                    if not _S.treelights.disabled then _S.treelights.callbacks.newGame() end
                end
            },
            ballExplosion={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player, ...)
                    args = {...}
                    if #args == 0 then
                        _S.ballExplosion.disabled = not _S.ballExplosion.disabled
                    end
                end
            },
            flames={
                rank=RANKS.ROOM_ADMIN,
                fnc=function()
                    _S.flames.disabled = not _S.flames.disabled
                    if not _S.flames.disabled then _S.flames.callbacks.newGame() end
                end
            },
            bubbles={
                rank=RANKS.ROOM_ADMIN,
                fnc=function()
                    _S.bubbles.disabled = not _S.bubbles.disabled
                    if not _S.bubbles.disabled then 
                        _S.bubbles.callbacks.newGame() 
                        _S.bubbles.shamanObjects = {}
                    else
                        for _, id in pairs(_S.bubbles.shamanObjects) do
                            tfm.exec.removeObject(id)
                        end
                    end
                end
            },
            rain={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,id)
                    _S.rain.ID=tonumber(id) or 40
                    _S.rain.disabled = not _S.rain.disabled
                end
            },
            fireworks={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    if #arg==0 then
                        _S.fireworks.disabled = not _S.fireworks.disabled
                        --if not _S.fireworks.disabled then _S.fireworks.setPositions() end
                    else
                        executeCommand(player, function(target)
                            toggleSegment(target,"fireworks",not players[target].activeSegments.fireworks)
                        end, arg)
                    end
                        
                end
            },
            explosion={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    executeCommand(player, function(target)
                        players[target].activeSegments.explosion=not players[target].activeSegments.explosion
                    end, arg)
                end
            },
            debug={
                rank=RANKS.ANY,
                fnc=function(player,...)
                    player.activeSegments.debug=not player.activeSegments.debug
                    if player.activeSegments.debug then
                        _S.debug.showMapInfo(player.name)
                    else
                        ui.removeTextArea(-18,player.name)
                    end
                end
            },
            mapinfo={rank=RANKS.ANY,fnc=function(player,...) _S.global.callbacks.chatCommand.debug.fnc(player) end},
        }
    },
    showMenu=function(name,hidden)
        local player=players[name]
        local menu={}

        if _S.global.menuCondition and _S.global.menuCondition(player) then
            table.insert(menu, _S.global.menu(player))
        end
        for k,s in pairs(_S) do
            if k~="global" and s.menuCondition and s.menuCondition(player) then
                table.insert(menu, s.menu(player))
            end
        end
        player.menu=menu

        local x=0
        local y=25
        local ta=1
        for _,section in ipairs(menu) do
            local titlex=x
            for i,item in ipairs(section) do
                local width=item.width or 20
                ui.addTextArea(ta,item.custom or "<p align='center'><a href='event:"..item.callback.."'>"..item.icon.."\n</a></p>",name,5+x,y,width,20,item.color,nil,0.5,true)
                table.insert(player.menu,{id=ta,callback=item.callback,icon=item.icon,color=item.color,x=x+5,y=y})
                x=x+width+10
                ta=ta+1
            end
            if section.title then
                ui.addTextArea(ta,"<p align='center'>"..section.title.."</p>",name,titlex,50,x-titlex,20,nil,nil,0,true)
                ta=ta+1
            end
            x=x+20
        end
        for i=ta,50 do
            ui.removeTextArea(i,name)
        end
    end
}


--[[ src/segments/addspawn.lua ]]--

_S.addspawn = {
    toSpawn={},
    toDespawn={},
    callbacks={
        newGame=function()
            _S.addspawn.toSpawn={}
        end,
        summoningStart=function(player,type,x,y,ang)
            table.insert(_S.addspawn.toSpawn, {name=player.name, type=type, x=x,y=y, ang=ang, vx=0, vy=0, interval=6, tick=0, despawn=120})
        end,
        eventLoop=function(time,remaining)
            for k,v in pairs(_S.addspawn.toSpawn) do
                if v.tick>=v.interval then
                    table.insert(_S.addspawn.toDespawn,{id=tfm.exec.addShamanObject(v.type, v.x, v.y, v.ang, v.vx or 0, v.vy or 0),despawn=os.time()+(v.despawn*500)})
                    v.tick=0
                end
                v.tick=v.tick+1
            end
        end
    }
}


--[[ src/segments/adventure.lua ]]--

_S.adventure = {
    disabled=true,
    id=0,
    players={},
    callbacks={
        newGame=function()
            local adventureId = getValueFromXML(tfm.get.room.xmlMapInfo.xml, "advId") or 1
            ui.setMapName(string.format("<J>Transformice<BL> - @200%s", (adventureId-1 or 1)))
            _S.adventure.id = adventureId
            if adventureId == 2 then
                local fromagnus = tfm.exec.addShamanObject(6300, 1780, 300)
                tfm.exec.addImage("15265a4df9d.png", "#"..fromagnus, -15, -50, nil)
            elseif adventureId == 3 then
                for player, data in pairs(tfm.get.room.playerList) do
                    _S.adventure.players[player] = {up = false}
                end
            elseif adventureId == 4 then
                local colors = {0xFB4047, 0xFE9926, 0xE7E433, 0xB9E52A, 0x89EAF9, 0x2E8BD8, 0x8E3F97}
                local id = math.random(#colors)
                local color1 = colors[id]
                table.remove(colors, id)
                id = math.random(#colors)
                local color2 = colors[id]
                local playerLen = table.getl(tfm.get.room.playerList)
                local count = 0
                for player, data in pairs(tfm.get.room.playerList) do
                    count = count+1
                    if count < playerLen then
                        tfm.exec.setNameColor(player, color1)
                    else
                        tfm.exec.setNameColor(player, color2)
                        tfm.exec.movePlayer(player, 5340, 0, false, 0, 0, false)
                    end
                end
            end
        end,
        eventLoop = function()
            if _S.adventure.id == 3 then
                for player, data in pairs(tfm.get.room.playerList) do
                    if _S.adventure.players[player] then
                        if _S.adventure.players[player].up then
                            tfm.exec.movePlayer(player, 0, 0, false, 0, -5, false)
                        else
                            tfm.exec.movePlayer(player, 0, 0, false, 0, 5, false)
                        end
                        _S.adventure.players[player].up = not _S.adventure.players[player].up
                    end
                end
            end
        end
    },
}


--[[ src/segments/ballexplosion.lua ]]--

_S.ballExplosion = {
    disabled=true,
    defaultPlayer=function(player)
        player.activeSegments.ballExplosion=true
    end,
    power=70,
    objects={},
    time=3000,
    range=70,
    callbacks={
        summoningEnd=function(player,objectType,x,y,ang,other)
            if objectType > 100 then objectType=objectType/100 end
            if objectType >= 6 and objectType < 7 then -- all balls
                table.insert(_S.ballExplosion.objects, {id=other.id,spawn=os.time(),range=_S.ballExplosion.range,explode=_S.ballExplosion.time,power=_S.ballExplosion.power})
            end
        end,
        eventLoop=function()
            local toRemove={}
            for index, data in pairs(_S.ballExplosion.objects) do
                if data.spawn < os.time()-data.explode then
                    coord = tfm.get.room.objectList[data.id] or {x=-5000,y=-5000}
                    tfm.exec.removeObject(data.id)
                    _S.ballExplosion.explodeLocal(data.power,data.range,coord.x,coord.y)
                    table.insert(toRemove, index)
                end
            end
            for _,i in ipairs(toRemove) do
                _S.ballExplosion.objects[i]=nil
            end
        end,
    },
    explodeLocal=function(power,range,x,y)
        tfm.exec.explosion(x,y,power,range,false)
        for i=0,20 do
            local angle=math.random(-180, 180)
            local velX=math.cos(angle)
            local velY=math.sin(angle)
            tfm.exec.displayParticle(math.random(4), x+math.random(-velX, velX), y+math.random(-velY, velY), velX, velY, math.random(-0.11, 0.11), math.random(-0.11, 0.11), nil)
        end
    end
}


--[[ src/segments/bubbles.lua ]]--

_S.bubbles = {
    disabled=true,
    ticks=0,
    callbacks={
        newGame=function()
            _S.bubbles.grounds = {}
            _S.bubbles.shamanObjects = {}
            for _, ground in pairs(map.grounds) do
                if ground.type == 9 then
                    table.insert(_S.bubbles.grounds, {ground.x, ground.y, ground.length, ground.height})
                end
            end
        end,
        eventLoop=function(time,remaining)
            _S.bubbles.ticks=_S.bubbles.ticks+1
            if _S.bubbles.grounds then
                x = math.random()*map.length
                y = math.random()*map.height
                for _, ground in pairs(_S.bubbles.grounds) do
                    for i = 10, 0, -1 do
                        x = math.random(ground[1]-(ground[3]/2), ground[1]+(ground[3]/2))
                        y = math.random(ground[2]-(ground[4]/2), ground[2]+(ground[4]/2))
                        tfm.exec.displayParticle(14, x, y, 0, math.random(-2, -1), 0, 0)
                    end
                    if _S.bubbles.ticks%3==0 then
                        table.insert(_S.bubbles.shamanObjects, tfm.exec.addShamanObject(59, x, y))
                    end
                end
            end
        end
    }
}


--[[ src/segments/checkpoints.lua ]]--

_S.checkpoints = {
    callbacks={
        playerRespawn=function(player)
            if player.checkpoint then
                tfm.exec.movePlayer(player.name,player.checkpoint.x,player.checkpoint.y)
                if player.checkpoint.id then system.removeTimer(player.checkpoint.id) end
                if player.checkpoint.cheese then
                    player.checkpoint.id=system.newTimer(function()
                        if player and player.checkpoint then 
                            tfm.exec.giveCheese(player.name)
                            player.checkpoint.id=nil
                        end
                    end,1000,false)
                end
            end
        end,
        playerDied=function(player)
            if player.checkpoint then
                if player.checkpoint.id then system.removeTimer(player.checkpoint.id) end
            end
        end,
        playerWon=function(player)
            if player.checkpoint then
                player.checkpoint=nil
                ui.removeTextArea(-1,player.name)
            end
        end,
        newGame=function()
            for n,p in pairs(players) do
                if p.checkpoint then
                    p.checkpoint=nil
                    ui.removeTextArea(-1,p.name)
                end
            end
        end,
        keyboard={
            [KEYS.E]=function(player,down,x,y)
                if player.lastSpawn and player.lastSpawn+3000<=os.time() and (not player.checkpoint or player.checkpoint.timestamp+3000<=os.time()) then
                    player.checkpoint={
                        timestamp=os.time(),
                        x=x,
                        y=y,
                        cheese=tfm.get.room.playerList[player.name].hasCheese
                    }
                    ui.addTextArea(-1,"",player.name,x-2,y-2,4,4,0x44cc44,0xffffff,0.5)
                end
            end,
            [KEYS.DELETE]=function(player,down,x,y)
                if player.checkpoint then
                    player.checkpoint=nil
                    ui.removeTextArea(-1,player.name)
                end
            end
        }
    }
}


--[[ src/segments/conj.lua ]]--

_S.conj = {
    callbacks={
        mouse={
            pr=3,
            fnc=function(player,x,y)
                tfm.exec.addConjuration(x/10,y/10,player.conjTime*1000)
            end
        }
    }
}


--[[ src/segments/dash.lua ]]--

_S.dash = {
    callbacks={
        keyboard={
            [KEYS.LEFT]=function(player,down,x,y) 
                if down then
                    if player.dash and player.dash.direction=="left" and player.dash.time>os.time()-250 and (player.lastDash and player.lastDash<os.time()-5000 or not player.lastDash) then
                        tfm.exec.movePlayer(player.name, 0, 0, false, -100, 0, false)
                        player.lastDash=os.time()
                    end
                    player.dash={time=os.time(),direction="left"}
                end
            end,
            [KEYS.RIGHT]=function(player,down,x,y) 
                if down then
                    if player.dash and player.dash.direction=="right" and player.dash.time>os.time()-250 and (player.lastDash and player.lastDash<os.time()-5000 or not player.lastDash) then
                        tfm.exec.movePlayer(player.name, 0, 0, false, 100, 0, false)
                        player.lastDash=os.time()
                    end
                    player.dash={time=os.time(),direction="right"}
                end
            end,
        }
    }
}


--[[ src/segments/debug.lua ]]--

_S.debug = {
    mapinfo="  ",
        callbacks={
        newGame=function()
            _S.debug.mapinfo="  "
            for k,v in ipairs({{"collision","C"},{"soulmate","S"},{"night","N"},{"portal","P"},{"aie","AIE"},{"mgoc","MGOC"},{"wind","W%s"},{"gravity","G%s"}}) do
                if map[v[1] ] then
                    if tonumber(map[v[1] ]) then
                        if not (v[1]=="wind" and map[v[1] ]==0) and not (v[1]=="gravity" and map[v[1] ]==10) then
                            _S.debug.mapinfo=_S.debug.mapinfo..(v[2]:format(tonumber(map[v[1] ]))).." "
                        end
                    else
                        _S.debug.mapinfo=_S.debug.mapinfo..v[2].." "
                    end
                end
            end
            for name,player in pairs(players) do
                if player.activeSegments.debug then
                    _S.debug.showMapInfo(player.name)
                end
            end
        end
    },
    showMapInfo=function(name)
        if #_S.debug.mapinfo>2 then
            ui.addTextArea(-18,_S.debug.mapinfo,name,5,380,nil,16,nil,nil,0.5,true)
        else
            ui.removeTextArea(-18,name)
        end
    end
}


--[[ src/segments/disco.lua ]]--

_S.disco = {
    disabled=true,
    callbacks={
        eventLoop=function(time,remaining)
            for n,p in pairs(tfm.get.room.playerList) do
                tfm.exec.setNameColor(n,math.random(0,0xFFFFFF))
            end
        end
    }
}


--[[ src/segments/doge.lua ]]--

_S.doge = {
    disabled=true,
    callbacks={
        eventLoop=function(time,remaining)
            for n,p in pairs(players) do
                local phrases=translate("doge",p.lang)
                ui.addTextArea(-50, "<font face='Comic sans MS' color='#"..string.format("%X",math.random(0,0xFFFFFF)).."' size='17'>"..phrases[math.random(#phrases)].."</font>", nil, math.random(100,map.length-100), math.random(40,map.height-40), nil, nil, 0)
            end
        end 
    }
}


--[[ src/segments/doll.lua ]]--

_S.doll = {
    move=function(player,direction)
        if player.dolls then
            for k,v in pairs(player.dolls) do
                tfm.exec.movePlayer(v,0,0,true,
                    direction=="left" and -40 or direction=="right" and 40 or 0,
                    direction=="up" and -50 or direction=="down" and 40 or 0,
                    false)
            end
        end
    end,
    callbacks={
        keyboard={
            [KEYS.U]=function(player,down,x,y)
                if down then _S.doll.move(player,"up") end
            end,
            [KEYS.J]=function(player,down,x,y)
                if down then _S.doll.move(player,"down") end
            end,
            [KEYS.H]=function(player,down,x,y)
                if down then _S.doll.move(player,"left") end
            end,
            [KEYS.K]=function(player,down,x,y)
                if down then _S.doll.move(player,"right") end
            end
        },
    }
}



--[[ src/segments/draw.lua ]]--

-- Drawing segment

_S.draw = {
    redraw=true,
    _={
        minBrushSize=1,
        maxBrushSize=25
    },
    defaultPlayer=function(player)
        player.activeSegments.draw=false
        player.draw={
            tool="line",
            size=10,
            color=0xFFFFFF,
            pickerId=0,
            alpha=100,
            lastClick=nil,
            nextDraw=nil,
            history={},
            bezierPath=nil,
            foreground=true,
            enteringColor=nil
        }
    end,
    menu=function(player)
        return {
            --title="Drawing",
            {callback="draw tool line",icon="|",color=player.draw.tool=="line" and 0xCCCCCC or nil},
            --{callback="draw tool curve",icon="~",color=player.draw.tool=="curve" and 0xCCCCCC or nil},
            {callback="draw tool circle",icon="o",color=player.draw.tool=="circle" and 0xCCCCCC or nil},
            {callback="draw tool brush",icon="b",color=player.draw.tool=="brush" and 0xCCCCCC or nil},
            {callback="draw picker",icon=" ",color=player.draw.color or 0xFFFFFF},
            {callback="draw tool eraser",icon="e",color=player.draw.tool=="eraser" and 0xCCCCCC or nil},
            {callback="draw clear",icon="c"},
            {callback="draw undo",icon="u"},
            {custom="<p align='center'><a href='event:draw size -3'>-</a> "..player.draw.size.."px <a href='event:draw size 3'>+</a></p>",width=65},
            {custom="<p align='center'><a href='event:draw alpha -10'>-</a> "..player.draw.alpha.."% <a href='event:draw alpha 10'>+</a></p>",width=65},
            {custom="<p align='center'><a href='event:draw foreground'>"..(player.draw.foreground and "fg" or "bg").."</a></p>",width=30},
        }
    end,
    menuCondition=function(player) return player.activeSegments.draw end,
    colors={
        red={title="Red",color=0xFF0000},
        green={title="Green",color=0x00FF00},
        blue={title="Blue",color=0x0000FF},
        orange={title="Orange",color=0xFF6600},
        background={title="Background Blue",color=0x6A7495},
        brown={title="Brown",color=0x78583A},
        skin={title="Skin Yellow",color=0xE3C07E},
        white={title="White",color=0xFFFFFF},
        silver={title="Silver",color=0xC0C0C0},
        gray={title="Gray",color=0x808080},
        black={title="Black",color=0x000001},
        maroon={title="Maroon",color=0x800000},
        yellow={title="Yellow",color=0xFFFF00},
        olive={title="Olive",color=0x808000},
        lime={title="Lime",color=0x00FF00},
        green={title="Green",color=0x008000},
        aqua={title="Aqua",color=0x00FFFF},
        teal={title="Teal",color=0x008080},
        navy={title="Navy",color=0x000080},
        fuchsia={title="Fuchsia",color=0xFF00FF},
        purple={title="Purple",color=0x800080},
        pink={title="Pink",color=0xFF69B4}
    },
    onEnable=function(player,manual)
        if manual==nil then manual=false end
        local keys={KEYS.A, KEYS.B, KEYS.D, KEYS.E, KEYS.F}
        for i = 0, 9 do
            table.insert(keys, KEYS["NUMPAD "..i])
        end
        for _,key in ipairs(keys) do
            system.bindKeyboard(player.name,key,true,manual)
            system.bindKeyboard(player.name,key,false,manual)
        end
    end,
    addHexCharToColor=function(player,letter)
        table.insert(player.draw.enteringColor, letter)
        if #player.draw.enteringColor==6 then
            local c=table.concat(player.draw.enteringColor, '')
            c=getColor(c)
            if c then
                player.draw.color=c
--                  c=string.format("%06X",c)
--                  tfm.exec.chatMessage(translate("colorchanged",player.lang):format("[<font color='#"..c.."'>#"..(c:upper()).."</font>]"),player.name)
            end
            player.draw.enteringColor=nil
            _S.draw.onEnable(player,false)
        end
    end,
    callbacks={
        newGame=function()
            _S.draw.ids={}
            _S.draw.physicObject()
        end,
        newPlayer=function(player)
            _S.draw.physicObject()
            if _S.draw.redraw then
                --local tbl=_S.draw.ids
                --_S.draw.ids={}
                for _,joint in ipairs(_S.draw.ids) do
                    if not joint.removed then
                        _S.draw.addJoint(joint.id,joint.coords1,joint.coords2,joint.name,joint.line,joint.color,joint.alpha,joint.foreground)
                    end
                end
            end
        end,
        keyboard={
            [KEYS.Z]=function(player,down,x,y)
                if down and player.ctrl then
                    _S.draw.undo(player.name)
                end
            end,
            [KEYS.W]=function(player,down,x,y)
                _S.draw.callbacks.keyboard[KEYS.Z](player,down,x,y)
            end,
            [KEYS.C]=function(player,down,x,y)
                if ranks[player.name]>=RANKS.ROOM_ADMIN and down then
                    if player.shift then
                        player.draw.enteringColor={}
                        _S.draw.onEnable(player,true)
                    elseif player.draw.enteringColor then
                        _S.draw.addHexCharToColor(player,'C')
                    end
                end
            end,
            [KEYS.A]=hexColorEntering('A'),
            [KEYS.B]=hexColorEntering('B'),
            [KEYS.D]=hexColorEntering('D'),
            [KEYS.E]=hexColorEntering('E'),
            [KEYS.F]=hexColorEntering('F'),
            [KEYS['NUMPAD 0']]=hexColorEntering('0'),
            [KEYS['NUMPAD 1']]=hexColorEntering('1'),
            [KEYS['NUMPAD 2']]=hexColorEntering('2'),
            [KEYS['NUMPAD 3']]=hexColorEntering('3'),
            [KEYS['NUMPAD 4']]=hexColorEntering('4'),
            [KEYS['NUMPAD 5']]=hexColorEntering('5'),
            [KEYS['NUMPAD 6']]=hexColorEntering('6'),
            [KEYS['NUMPAD 7']]=hexColorEntering('7'),
            [KEYS['NUMPAD 8']]=hexColorEntering('8'),
            [KEYS['NUMPAD 9']]=hexColorEntering('9')
        },
        mouse={
            pr=3,
            fnc=function(player,x,y)
                if player.activeSegments.draw and not player.omo then
                    if player.draw.nextDraw or (player.shift and player.draw.lastClick) then
                        _S.draw.tool[player.draw.tool](player.draw.lastClick,{x=x,y=y},player.name)
                    else
                        player.draw.nextDraw=true
                    end
                    player.draw.lastClick={x=x,y=y}
                end
            end
        },
        chatCommand={
            color={
                rank=RANKS.ANY,
                fnc=function(player,color,target)
                    target=target and upper(target) or player.name
                    if color then
                        local c=getColor(color)
                        if c then
                            players[target].draw.color=c
                            c=string.format("%06X",c)
                            tfm.exec.chatMessage(translate("colorchanged",player.lang):format("[<font color='#"..c.."'>#"..(c:upper()).."</font>]"),player.name)
                        else
                            tfm.exec.chatMessage(translate("invalidargument",player.lang),player.name)
                        end
                    else
                        _S.draw.callbacks.textArea.picker(1,player.name)
                    end
                end
            },
            brush={
                rank=RANKS.ANY,
                fnc=function(player,size,target)
                    target=target and upper(target) or player.name
                    size=tonumber(size) or 10
                    players[target].draw.size=math.max(_S.draw._.minBrushSize,math.min(_S.draw._.maxBrushSize,size))
                    _S.global.showMenu(target)
                    tfm.exec.chatMessage(translate("brushchanged",player.lang):format(size),player.name)
                end
            },
            clear={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,target)
                    target=target and upper(target) or player.name
                    if target=="All" or target=="*" then target=nil end
                    _S.draw.clear(target)
                end
            },
            jointxml={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player)
                    local str=""
                    for _,joint in pairs(_S.draw.ids) do
                        if not joint.removed then
                            local j=[[&lt;JD P1="%s"P2="%s"c="%s,%s,1,0"/&gt;]]
                            str=str..(j:format(
                                joint.coords1.x..","..joint.coords1.y,
                                joint.coords2.x..","..joint.coords2.y,
                                string.format("%X",joint.color),
                                joint.line)
                            )
                        end
                    end
                    local splitnum=800
                    for i=1,#str,splitnum do
                        tfm.exec.chatMessage("<font size='8'>"..string.sub(str,i,i+splitnum-1).."</font>",player.name)
                    end
                end
            },
        },
        textArea={
            tool=function(id,name,arg)
                local t = arg[3]
                if t and _S.draw.tool[t] then
                    local player=players[name]
                    player.draw.tool=t
                    _S.global.showMenu(name)
                    player.draw.lastClick=nil
                    player.draw.nextDraw=nil
                    if t=="brush" or t=="eraser" then
                        player.draw.nextDraw=true
                    end
                end
            end,
            clear=function(id,name,arg)
                _S.draw.clear(name)
            end,
            undo=function(id,name,arg)
                _S.draw.undo(name)
            end,
            size=function(id,name,arg)
                local player=players[name]
                local size=player.draw.size
                size=math.max(_S.draw._.minBrushSize,math.min(_S.draw._.maxBrushSize,size+(arg[3] or 1)))
                player.draw.size=size
                _S.global.showMenu(name)
            end,
            alpha=function(id,name,arg)
                local player=players[name]
                local alpha=player.draw.alpha
                alpha=math.min(100,math.max(10,alpha+(arg[3] or 0)))
                player.draw.alpha=tonumber(alpha)
                _S.global.showMenu(name)
            end,
            foreground=function(id,name,arg)
                local player=players[name]
                player.draw.foreground=not player.draw.foreground
                _S.global.showMenu(name)
            end,
            color=function(id,name,arg)
                local player=players[name]
                local color = tonumber(arg[3]) or 1
                player.draw.color=color
                _S.global.showMenu(name)
            end,
            picker=function(id,name,arg)
                --[[
                local i=0
                for k,c in pairs(_S.draw.colors) do
                    ui.addTextArea(600+i,"<a href='event:draw colorpicker "..c.color.."'>"..c.title or c.color.."</a>",name,150,60+(i*24),100,18,c.color,0x212F36,1,true)
                    i=i+1
                end
                ]]
                local player=players[name]
                local id=player.draw.pickerId+1
                player.draw.pickerId=id
                ui.showColorPicker(id, name, player.draw.color, translate("pickacolor",players[name].lang))
            end,
            colorpicker=function(id,name,arg)
                ui.showColorPicker(id, name, arg[3], translate("pickacolor",players[name].lang))
            end
        },
        colorPicked=function(player,id,color)
            if color==0 then color=1 end
            if color==-1 then
                --[[
                for i=600,620 do
                    ui.removeTextArea(i,player.name)
                end
                ]]
            elseif id==player.draw.pickerId then
                player.draw.color=color
                _S.global.showMenu(player.name)
            end
        end
    },
    ids={},
    physicObject=function()
        tfm.exec.addPhysicObject(1,400,-600,{type=13,width=10,height=10,foreground=true,friction=0.3,restitution=0,dynamic=false,miceCollision=false,groundCollision=false})
    end,
    addJoint=function(id,coords1,coords2,name,size,color,alpha,foreground)
        id=id or #_S.draw.ids+1
        local tbl={
            id=id,
            coords={x=(coords1.x+coords2.x)/2,y=(coords1.y+coords2.y)/2},
            coords1=coords1,
            coords2=coords2,
            point1=coords1.x..","..coords1.y,
            point2=coords2.x..","..coords2.y,
            name=name,
            line=size or 10,
            color=color or 0xFFFFFF,
            alpha=alpha or 1,
            frequency=10,
            type=0,
            damping=0.2,
            foreground=foreground
        }
        tfm.exec.addJoint(id,1,1,tbl)
        _S.draw.ids[id]=tbl
        return id
    end,
    readdJoint=function(index,update)
        local joint=_S.draw.ids[index]
        if joint then
            joint.removed=false
            tfm.exec.addJoint(joint.id,1,1,joint)
            if update then _S.draw.blankJoint() end
        end
    end,
    removeJointByIndex=function(index,update)
        local joint=_S.draw.ids[index]
        if joint then
            tfm.exec.removeJoint(joint.id)
            joint.removed=true
            if update then _S.draw.blankJoint() end
        end
    end,
    removeJoint=function(id)
        for _,joint in ipairs(_S.draw.ids) do
            if joint.id==id then
                tfm.exec.removeJoint(id)
                joint.removed=true
            end
        end
        _S.draw.blankJoint()
    end,
    blankJoint=function()
        tfm.exec.addJoint(0,1,1,{
            type=0,
            point1="0,0",
            point2="0,1",
            frequency=10,
            damping=0.2,
            line=1,
            color=0xFFFFFF,
            alpha=0,
            foreground=false
        })
    end,
    clear=function(name)
        local action={}
        for _,joint in ipairs(_S.draw.ids) do
            if (name and joint.name==name or not name) and not joint.removed then
                table.insert(action,joint.id)
                tfm.exec.removeJoint(joint.id)
                joint.removed=true
            end
        end
        if not name then -- !clear all : not undoable (this is to clear memory mainly if the room is getting laggy)
            _S.draw.ids={}
            for _,p in pairs(players) do
                p.draw.history={}
            end
        elseif players[name] then
            _S.draw.addHistoryAction(name,false,action)
        end
        _S.draw.blankJoint()
    end,
    undo=function(name)
        local player=players[name]
        local hist=player.draw.history
        local i=#hist

        if i>0 then
            local action=table.remove(hist,i)
            local additive=action.additive
            local lastJointIndex=nil

            for _,v in ipairs(action) do
                if type(v)=='number' then
                    if additive then -- the action added new joints, so we will remove them
                        _S.draw.removeJointByIndex(v)
                    else -- the action removed joints, so we will re-add them
                        _S.draw.readdJoint(v)
                        lastJointIndex=v
                    end
                else
                    for _,w in ipairs(v) do
                        if additive then -- the action added new joints, so we will remove them
                            _S.draw.removeJointByIndex(w)
                        else -- the action removed joints, so we will re-add them
                            _S.draw.readdJoint(w)
                            lastJointIndex=w
                        end
                    end
                end
            end
            if additive and #hist>0 then
                -- Get coords from last join of action before this one
                action=hist[#hist]
                lastJointIndex=action[#action]
                if type(lastJointIndex)=='table' then
                    lastJointIndex=lastJointIndex[#lastJointIndex]
                end
            end
            if lastJointIndex then
                -- Set lastClick based on coords of lastJointIndex
                local joint=_S.draw.ids[lastJointIndex]
                if joint then
                    player.draw.lastClick=joint.coords2
                end
            end
            _S.draw.blankJoint()
        end
    end,
    addHistoryAction=function(name,additive,...)
        local player=players[name]
        local arg={...}
        arg.additive=additive
        table.insert(player.draw.history,arg)
    end,
    tool={
        line=function(coords1,coords2,name)
            local player=players[name]
            local action=_S.draw.addJoint(nil,coords1,coords2,name,player.draw.size,player.draw.color,player.draw.alpha/100,player.draw.foreground)
            _S.draw.addHistoryAction(name,true,action)
            if name then
                player.draw.nextDraw=nil
            end
        end,
        brush=function(coords1,coords2,name)
            local player=players[name]
            local action=_S.draw.addJoint(nil,coords2,{x=coords2.x,y=coords2.y+1},name,player.draw.size,player.draw.color,player.draw.alpha/100,player.draw.foreground)
            _S.draw.addHistoryAction(name,true,action)
        end,
        circle=function(coords1,coords2,name)
            local player=players[name]
            local action=_S.draw.addJoint(nil,coords1,{x=coords1.x,y=coords1.y+1},name,distance(coords1.x,coords1.y,coords2.x,coords2.y)*2,player.draw.color,player.draw.alpha/100,player.draw.foreground)
            _S.draw.addHistoryAction(name,true,action)
            if name then
                player.draw.nextDraw=nil
            end
        end,
        eraser=function(coords1,coords2,name)
            local dist=10
            local action={}
            for i,joint in pairs(_S.draw.ids) do
                if name and joint.name==name or not name then
                    -- TODO: separate distance function for each shape (line, dot, circle, curve)
                    if pythag(coords2.x,coords2.y,joint.coords.x,joint.coords.y,dist) then
                        table.insert(action,i)
                        tfm.exec.removeJoint(joint.id)
                        joint.removed=true
                    end
                end
            end
--              _S.draw.addHistoryAction(name,false,action)
            _S.draw.blankJoint()
        end,
        curve=function(coords1,coords2,name)
            -- TODO: should convert all {x, y} tables in this file to Vector(x, y)
--[[                local player=players[name]
            local bp = player.draw.bezierPath
            if coords1 then coords1 = Vector(coords1.x, coords1.y) end
            if coords2 then coords2 = Vector(coords2.x, coords2.y) end
            if not bp then
                bp = BezierPath()
                bp:add(coords1)
                bp:add(coords2)
                player.draw.bezierPath = bp
            elseif bp:size() >= 2 then
                bp:add(coords2)
                local action = {}
                local tesPoints = bp:draw()
                for i = 2, #tesPoints - 1 do
                    local p1, p2 = tesPoints[i - 1]:floored(), tesPoints[i]:floored()
                    table.insert(action, _S.draw.addJoint(nil,p1,p2,name,player.draw.size,player.draw.color,player.draw.alpha/100,player.draw.foreground))
                end
                _S.draw.addHistoryAction(name,true,action)
            end]]
        end
    }
}


--[[ src/segments/drawonme.lua ]]--

_S.drawOnMe = {
    defaultPlayer=function(player)
        player.activeSegments.drawOnMe=false
        player.drawOnMe={
            imgs={},
            lastFacing="right"
        }
    end,
    redraw=function(player,direction)
        for _,dot in ipairs(player.drawOnMe.imgs) do
            tfm.exec.removeImage(dot.img)
            dot.img=tfm.exec.addImage("151469722d4.png","$"..player.name,direction=="right" and dot.x*-1 or dot.x,dot.y)
        end
        player.drawOnMe.lastFacing=direction
    end,
    callbacks={
        mouse={
            pr=2,
            fnc=function(player,x,y)
                local p=tfm.get.room.playerList[player.name]
                if pythag(p.x,p.y,x,y,25) then
                    local xoff=x-p.x
                    local yoff=y-p.y
                    table.insert(player.drawOnMe.imgs,{
                        img=tfm.exec.addImage("151469722d4.png","$"..player.name,xoff,yoff),
                        x=player.facingRight and xoff*-1 or xoff,
                        y=yoff
                    })
                end
            end
        },
        keyboard={
            [KEYS.LEFT]=function(player,down,x,y)
                if down then
                    if player.drawOnMe.imgs and player.drawOnMe.lastFacing=="right" then
                        _S.drawOnMe.redraw(player,"left")
                    end
                end
            end,
            [KEYS.RIGHT]=function(player,down,x,y)
                if down then
                    if player.drawOnMe.imgs and player.drawOnMe.lastFacing=="left" then
                        _S.drawOnMe.redraw(player,"right")
                    end
                end
            end,
        },
        newGame=function()
            for name in pairs(tfm.get.room.playerList) do
                local player=players[name]
                _S.drawOnMe.redraw(player,"right")
            end
        end,
        playerRespawn=function(player)
            _S.drawOnMe.redraw(player,"right")
        end,
        chatCommand={
            clear={
                rank=RANKS.ANY,
                fnc=function(player)
                    for _,dot in ipairs(player.drawOnMe.imgs) do
                        tfm.exec.removeImage(dot.img)
                    end
                    player.drawOnMe.imgs={}
                end
            }
        }
    }
}


--[[ src/segments/explosion.lua ]]--

_S.explosion = {
    callbacks={
        mouse={
            pr=1,
            fnc=function(player,x,y)
                tfm.exec.explosion(x,y,50,100)
            end
        }
    }
}


--[[ src/segments/ffa.lua ]]--

_S.ffa = {
    toDespawn={},
    defaultPlayer=function(player)
        player.activeSegments.ffa=false
        player.ffa={
            object=17,
            power=10,
            timestamp=0,
            cooldown=1000,
            spawnLength=2000,
            offset={
                x=2,
                y=10,
            },
        }
    end,
    callbacks={
        keyboard={
            [KEYS.DOWN]=function(player,down,x,y)
                if down and not tfm.get.room.playerList[player.name].isDead then
                    if player.ffa.timestamp<=os.time()-player.ffa.cooldown then
                        local angle=(player.facingRight and 90 or 270)+(player.ffa.object==17 and 0 or -90)
                        table.insert(_S.ffa.toDespawn,{
                            id=tfm.exec.addShamanObject(player.ffa.object,player.facingRight and x+player.ffa.offset.x or x-player.ffa.offset.x,y+player.ffa.offset.y,angle,player.ffa.power*math.cos(math.rad(angle)),player.ffa.power*math.sin(math.rad(angle))),
                            despawn=os.time()+player.ffa.spawnLength
                        })
                        player.ffa.timestamp=os.time()
                    end
                end
            end,
        },
        chatCommand={
            off={
                rank=RANKS.ANY,
                fnc=function(player,x,y)
                    local newx,newy
                    if tonumber(x) and tonumber(y) then
                        newx=x
                        newy=y
                    elseif x=="dom" then
                        newx=-10
                        newy=15
                    elseif x=="def" then
                        newx=2
                        newy=10
                    else
                        tfm.exec.chatMessage(translate("invalidargument",player.lang),player.name)
                    end
                    if newx and newy then
                        player.ffa.offset={x=tonumber(newx),y=tonumber(newy)}
                        tfm.exec.chatMessage(translate("offsetschanged",player.lang):format(newx,newy),player.name)
                    end
                end
            }
        }
    },
}


--[[ src/segments/fireworks.lua ]]--

_S.fireworks = {
    disabled=true,
    counter=0,
    timedEvents={},
    players={},
    defaultPlayer=function(player)
        _S.fireworks.players[player] = os.time()
    end,
    spawnPoints = {
        -- {1=x, 2=y, 3=chance, 4=speed, 5=progress, 6=imageX, 7=imageY, 8=imageId, 9=activateDist, 10=baseVx, 11=maxOffsetVx}
        {20, 340, 0.3, 1.5, 1, 130, 333, -1, 60, 3.5, 1},
        {295, 340, 0.4, 2.5, 1, 348, 336, -1, 80, -2.5, 1},
        {480, 340, 0.4, 2.5, 1, 604, 232, -1, 80, 2.5, 1},
        {777, 340, 0.3, 1.5, 1, 604, 232, -1, 80, -3.5, 1}
    },
    explosionData = {
        --[[ FORMAT START
        {
            function(trailParticleId,extraParticles,centerX,centerY)
                -- trailParticleId is the particle id from the trail
                -- extraParticles gets passed on from this table to this function
                -- centerX is the center X coordinate from the explosion
                -- centerY ............. Y .............................
                -- do stuff here
            end,
            extraParticles,
            timeBeforeExplosion
        }
        FORMAT END ]]--
        {function(id,p,x,y) -- Star shaped, same color as trail
            _S.fireworks.drawParam2({x, y, 7.0, 0.4, 10, {id}})
        end, {}, 1000},
        {function(id,p,x,y) -- Default firework, same color as trail
            _S.fireworks.defaultEffect(id,p,x,y,true)
        end, {0,2}, 1000},
        {function(id,p,x,y)
            _S.fireworks.defaultEffect(id,p,x,y,true)
        end, REDWHITEBLUE, 1000},
        {function(id,p,x,y)
            _S.fireworks.defaultEffect(id,p,x,y,false)
        end, REDWHITEBLUE, 1000}
    },
    fireworkSets={
        --[[ FORMAT START
        {
            trailId or {trailId1, trailId2}, -- if it's a number, it will pick that, if it's a table, it will pick a random one
            explosionIndex, -- index in explosionData
            probability
        }
        FORMAT END]]--
        {REDWHITEBLUE, 4, 50}, -- red white blue default firework
        {REDWHITEBLUE, 3, 25}, -- red white blue default firework
        {{0, 2}, 2, 15}, -- white and gold default firework
        {REDWHITEBLUE, 1, 3}, -- Star shaped, same color as trail
    },
    setPositions=function()
        _S.fireworks.spawnPoints={}
        for i=100,(map.length or 800),200 do
            tfm.exec.chatMessage(i)
            table.insert(_S.fireworks.spawnPoints,{i, (map.height or 400)-20, 0.1, math.random(15,25)/10, 1, math.random(350,650), math.random(350,650), -1, 80, math.random(-35,35)/10, 1})
        end
    end,
    callbacks={
        --newGame=function()
        --  _S.fireworks.setPositions()
        --end,
        eventLoop=function(time,remaining)
            if _S.fireworks.counter % 3 == 0 then
                local b = 1000
                local e = b + 3000
                for i,sp in pairs(_S.fireworks.spawnPoints) do
                    for j = b, e, 1000 / (sp[4] + (sp[5] - 0.5) * 3) do
                        _S.fireworks.timedEvent(j, false, function(i)
                            _S.fireworks.fireIt(i)
                        end, i)
                    end
                end
            end
            local tR = {}
            for i,t in pairs(_S.fireworks.timedEvents) do
                if t[1] < os.time() then
                    t[3](unpack(t[4]))
                    if t[2] == true then
                        t[1] = t[1] + t[5]
                    else
                        tR[i] = true
                    end
                end
            end
            for i in pairs(tR) do
                _S.fireworks.timedEvents[i] = nil
            end
            _S.fireworks.counter = _S.fireworks.counter + 0.5
        end,
        keyboard={
            [KEYS.SPACE]=function(player,down,x,y)
                if down then
                    if _S.fireworks.players[player] < os.time()-500 then
                        _S.fireworks.players[player] = os.time()
                        _S.fireworks.selectAndFire(x,y)
                    end
                end
            end,
        },
    },
    timedEvent=function(ms, r, f, ...)
        _S.fireworks.timedEvents[#_S.fireworks.timedEvents + 1] = {os.time()+ms,r,f,arg,ms}
    end,
    fireIt=function(spI)
        local sp = _S.fireworks.spawnPoints[spI]
        if math.random() < sp[3] * sp[5] then
            local vx = sp[10] + math.random(-sp[11], sp[11])
            _S.fireworks.selectAndFire(sp[1], sp[2], vx)
        end
    end,
    selectAndFire=function(x, y, vx, setI)
        vx = vx == nil and math.random(-3, 3) or vx
        if setI == nil then
            -- calculate a random set based on probabilities
            local total = 0
            for _,s in ipairs(_S.fireworks.fireworkSets) do
                total = total + s[3]
            end
            local r = math.random(0, total - 1)
            total = 0
            for i,s in ipairs(_S.fireworks.fireworkSets) do
                total = total + s[3]
                if total > r then
                    setI = i
                    break
                end
            end
        end
        local fireworkSet = _S.fireworks.fireworkSets[setI]
        if fireworkSet ~= nil then
            local id = fireworkSet[1]
            if type(id) == 'table' then
                id = id[math.random(#id)]   
            end
            _S.fireworks.firework(id, x, y, vx, -20, 0, math.random(8, 9) / 10, 5, 30, _S.fireworks.explosionData[fireworkSet[2]])
        end
    end,
    firework=function(id, initX, initY, vx, vy, ax, ay, magnitude, length, explosion)
        local params = nil
        local xMultiplier = 3
        if explosion == nil then
            xMultiplier = 5
        end
        -- Launch firework
        for i = magnitude, 1, -1 do
            local timeT = xMultiplier * (-i / magnitude)
            local velX = timeT * ax + vx
            local velY = timeT * ay + vy
            local x = initX + (velX + vx) / 2 * timeT
            local y = initY + (velY + vy) / 2 * timeT
            if params == nil then
                params = {x, y, velX, velY, ax, ay, id} -- we use these to calculate our explosion position
            end
            for j = 1, magnitude - i do
                if id == -1 then
                    tfm.exec.displayParticle(9, x, y, velX, velY, ax, ay, nil)
                    tfm.exec.displayParticle(1, x, y, velX, velY, ax, ay, nil)
                else
                    tfm.exec.displayParticle(id, x, y, velX, velY, ax, ay, nil)
                end
            end
        end
        if explosion ~= nil then
            system.newTimer(function(timerId, expl, params)
                -- local tx = expl[3] / (math.pi * 10) -- guesstimation
                local t = explosion[3] / (math.pi * 10)
                local dx = params[3] * t + 0.5 * params[5] * t^2 -- change in x = vxi*changeintime+0.5*ax*t^2
                local x = params[1] + dx
                local dy = params[4] * t + 0.5 * params[6] * t^2 -- change in y = vyi*changeintime+0.5*ay*t^2
                local y = params[2] + dy
    
                local f = explosion[1]
                local particles = explosion[2]
                f(params[7], particles, x, y)
                system.removeTimer(timerId)
            end, math.max(explosion[3], 1000), false, explosion, params)
        end
    end,
    defaultEffect=function(id,p,x,y,rand)
        local minDist = 1
        local outerBorder = 20
        local maxDist = 30
        local totalParticles = rand and 40 or (id == -1 and 35 or 75)
        for i = 1, totalParticles do
            if rand then
                id = p[math.random(#p)]
            end
            local dist = math.min(math.random(minDist, maxDist), outerBorder)
            local angle = math.random(0, 360)
            local r = math.rad(angle)
            local dx = math.cos(r)
            local dy = math.sin(r)
            local vx = dist * dx / 10
            local vy = dist * dy / 10
            local ax = -vx / dist / 15
            local ay = (-vy / dist / 15) + 0.05
            if id == -1 then
                tfm.exec.displayParticle(9, x + dx, y + dy, vx, vy, ax, ay, nil)
                tfm.exec.displayParticle(1, x + dx, y + dy, vx, vy, ax, ay, nil)
            else
                tfm.exec.displayParticle(id, x + dx, y + dy, vx, vy, ax, ay, nil)
            end
        end
    end,
    drawParam2=function(arg)
        local x,y,k,a,m,particles=arg[1],arg[2],arg[3],arg[4],arg[5],arg[6]
        local b=a/k
        local dx,dy=0,0
        for t=0,math.pi*2,math.pi/36 do -- step math.pi/18 is every 10 degrees
            dx=x+((a-b)*math.cos(t)+b*math.cos(t*((a/b)-1)))*m
            dy=y+((a-b)*math.sin(t)-b*math.sin(t*((a/b)-1)))*m
            _S.fireworks.velocityEffect(x,y,dx,dy,particles)
        end
    end,
    velocityEffect=function(x,y,dx,dy,particles)
        local dist = distance(x, y, dx, dy)
        local angle = math.atan2(dy - y, dx - x)
        local vx = dist * math.cos(angle)
        local vy = dist * math.sin(angle)
        local ax = -vx / dist / 15
        local ay = (-vy / dist / 15) + 0.05 -- +0.05 for gravity
        for _,p in ipairs(particles) do
            if p == -1 then
                tfm.exec.displayParticle(9, dx, dy, vx, vy, ax, ay, nil)
                tfm.exec.displayParticle(1, dx, dy, vx, vy, ax, ay, nil)
            else
                tfm.exec.displayParticle(p, dx, dy, vx, vy, ax, ay, nil)
            end
        end
    end
}


--[[ src/segments/flames.lua ]]--

_S.flames = {
    disabled=true,
    defaultPlayer=function(player)
        player.activeSegments.flames=true
    end,
    decorations={},
    onFire={},
    ids={2, 13},
    lamps={
        [44]={x=0,y=-70},
        [46]={x=0,y=-65},
        [55]={x=0,y=-10},
        [71]={x=0,y=-25},
        [96]={x=0,y=0},
        [97]={x=0,y=-60},
        [102]={x=0,y=30},
    },
    callbacks={
        newGame=function()
            _S.flames.onFire={}
            _S.flames.decorations={}
            for _,deco in pairs(map.decorations) do
                if _S.flames.lamps[deco.id] then
                    table.insert(_S.flames.decorations,deco)
                end
            end
        end,
        eventLoop=function(time,remaining)
            for i,deco in pairs(_S.flames.decorations) do
                _S.flames.fireParticles(deco.x+_S.flames.lamps[deco.id].x,deco.y+_S.flames.lamps[deco.id].y)
                if i==10 then break end
            end
            local toremove={}
            for name,time in pairs(_S.flames.onFire) do
                _S.flames.fireParticles(tfm.get.room.playerList[name].x,tfm.get.room.playerList[name].y)
                if time<os.time()-15000 then
                    toremove[name]=true
                end
            end
            for k in pairs(toremove) do _S.flames.onFire[k]=nil end
        end,
        emotePlayed=function(player,emote,param)
            if emote==tfm.enum.emote.confetti or (emote==tfm.enum.emote.flag and param=="us") then
                local x=tfm.get.room.playerList[player.name].x
                local y=tfm.get.room.playerList[player.name].y
                for k,v in pairs(_S.flames.decorations) do
                    local x2=v.x+_S.flames.lamps[v.id].x
                    local y2=v.y+_S.flames.lamps[v.id].y
                    if inSquare(x+(player.facingRight and 50 or -50),y,x2,y2,40) then
                        if param=="us" then tfm.exec.chatMessage("Please stop burning the US flag :(",player.name) end
                        _S.flames.onFire[player.name]=os.time()
                        break
                    end
                end
            end
        end,
    },
    fireParticles=function(x,y)
        for i = 1, 5 do
            local x = x + math.random(-15, 15)
            local y = y - math.random(0, 10) - 5
            local vx = math.random(-1, 1) / 10
            local vy = -(math.random(50, 100) / 100)
            for j = 1, 2 do
                tfm.exec.displayParticle(_S.flames.ids[math.random(#_S.flames.ids)], x, y, vx, vy, 0, 0, nil)
            end
        end
    end
}


--[[ src/segments/fly.lua ]]--

_S.fly = {
    callbacks={
        keyboard={
            [KEYS.SPACE]=function(player,down,x,y)
                if down then
                    tfm.exec.movePlayer(player.name,0,0,true,0,-50,true)
                end
            end,
        },
    },
}


--[[ src/segments/hide.lua ]]--

_S.hide = {
    hidden={},
    movePlayer=function(player)
        if not player then return end --temp
        local p=tfm.get.room.playerList[player.name]
        local x=0
        if p.x<5 then x=400 elseif p.x>map.length-5 then x=map.length-400 end
        tfm.exec.movePlayer(player.name,x,-400,false,0,-5,false)
    end,
    callbacks={
        newGame=function()
            _S.hide.hidden={}
        end,
        playerLeft=function(player)
            _S.hide.hidden[player.name]=nil
        end,
        eventLoop=function(time,remaining)
            for n,p in pairs(_S.hide.hidden) do
                _S.hide.movePlayer(players[n])
            end
        end
    }
}


--[[ src/segments/images.lua ]]--

_S.images = {
    defaultPlayer=function(player)
        player.activeSegments.images=true
    end,
    sprites={
        main={
            troll={img="1507b048307",x=-50,y=-65},
            monkey={left={img="1507b04923a",x=-24,y=-46},right={img="1507b04b231",x=-32,y=-46},forecheese={"main","banana"}},
            pony={left={img="1507b04d0ae",x=-25,y=-40},right={img="1507b04dfdd",x=-25,y=-40}},
            silverpony={left={img="1507b04ef3b",x=-35,y=-55},right={img="1507b04fe9d",x=-35,y=-55}},
            mj={left={img="1507b050e24",x=-11,y=-37},right={img="1507b051d5e",x=-11,y=-37}},
            br={left={img="1507b052ccc",x=-25,y=-25},right={img="1507b053c28",x=-25,y=-25}},
            tr={left={img="1507b054b9c",x=-25,y=-35},right={img="1507b055ae6",x=-25,y=-35}},
            banana={left={img="1507b04a18d",x=-25,y=-40},right={img="1507b04a18d",x=-15,y=-40},forecheese={"main","monkey"}},
            pusheen={left={img="1511d5ae9f6",x=-40,y=-32},right={img="1511d5b3ee0",x=-40,y=-32},
                action=function(player) _S.images.selectImage(player,"pusheensitting","misc") end,forecheese={"misc","pusheenfish"},
                petaction=function(pet) tfm.exec.removeImage(pet.sprite.img) pet.sprite.category="misc" pet.sprite.id="pusheensitting" _S.pet.showImage(pet,pet.direction) end,
            },
            pusheentoast={left={img="1514f3c049f",x=-40,y=-32},right={img="1514f3cb928",x=-40,y=-32}},
            ace={left={img="151368b0460",x=-45,y=-42},right={img="151368ae6b2",x=-45,y=-42},forecheese={"misc","acebone"},
                action=function(player) _S.images.selectImage(player,"acesitting","misc") end,
                petaction=function(pet) tfm.exec.removeImage(pet.sprite.img) pet.sprite.category="misc" pet.sprite.id="acesitting" _S.pet.showImage(pet,pet.direction) end,
            },
            miniace={left={img="1515465248d",x=-25,y=-10},right={img="15154661304",x=-25,y=-10},forecheese={"misc","miniacebone"},
                action=function(player) _S.images.selectImage(player,"miniacesitting","misc") end,
                petaction=function(pet) tfm.exec.removeImage(pet.sprite.img) pet.sprite.category="misc" pet.sprite.id="miniacesitting" _S.pet.showImage(pet,pet.direction) end,
            },
            spurdo={left={img="1511d5ba0f1",x=-25,y=-25},right={img="1511d5bf92c",x=-25,y=-25}},
            penguin={left={img="1511d5c48e1",x=-32,y=-48},right={img="1511d5c9df3",x=-32,y=-48}},
        },
        misc={
            circle80={img="150c9c27dfa",x=-41,y=-41},
            circle100={img="150c9c2b2e9",x=-51,y=-51},
            circle160={img="150c9c2d90d",x=-81,y=-81},
            circle240={img="150c9bb6632",x=-121,y=-121},
            pusheensitting={left={img="15136acc31e",x=-40,y=-32},right={img="15136ace601",x=-30,y=-32},forecheese={"misc","pusheenfish"},
                action=function(player) _S.images.selectImage(player,"pusheen","main") end,
                petaction=function(pet) tfm.exec.removeImage(pet.sprite.img) pet.sprite.category="main" pet.sprite.id="pusheen" _S.pet.showImage(pet,pet.direction) end,
            },
            acesitting={left={img="151368a96cb",x=-45,y=-37},right={img="151368ac201",x=-45,y=-37},forecheese={"misc","acesittingbone"},
                action=function(player) _S.images.selectImage(player,"ace","main") end,
                petaction=function(pet) tfm.exec.removeImage(pet.sprite.img) pet.sprite.category="main" pet.sprite.id="ace" _S.pet.showImage(pet,pet.direction) end,
            },
            miniacesitting={left={img="151549c842b",x=-25,y=-7},right={img="151549cdaa8",x=-25,y=-7},forecheese={"misc","miniacesittingbone"},
                action=function(player) _S.images.selectImage(player,"miniace","main") end,
                petaction=function(pet) tfm.exec.removeImage(pet.sprite.img) pet.sprite.category="main" pet.sprite.id="miniace" _S.pet.showImage(pet,pet.direction) end,
            },
            acebone={left={img="15136c25de5",x=-45,y=-42},right={img="15136c2d6ec",x=-45,y=-42}},
            miniacebone={left={img="15154679858",x=-25,y=-10},right={img="1515468146c",x=-25,y=-10}},
            acesittingbone={left={img="15136c25de5",x=-45,y=-37},right={img="15136c2d6ec",x=-45,y=-37}},
            miniacesittingbone={left={img="151549eff5e",x=-25,y=-7},right={img="151549f3aad",x=-25,y=-7}},
            pusheenfish={left={img="15136c3cb06",x=-40,y=-32},right={img="15136c40261",x=-40,y=-32}},
        },
        transformice={
            meli={img="1507b11647d",x=-40,y=-40},
            meli2={img="1507b1175bb",x=-40,y=-40},
            meli3={img="1507b11865a",x=-40,y=-40},
            meli4={img="1507b1196d0",x=-40,y=-40},
            meli5={img="1507b11a716",x=-40,y=-40},
            tig={left={img="1516f2005ab",x=-46,y=-60},right={img="1516f1f53d2",x=-46,y=-60}},
            retrohole={img="15141167cf4",x=-21,y=-17},
            cheese={left={img="1507b11b7aa",x=-23,y=-9},right={img="1507b11c813",x=-23,y=-9}},
            hole={left={img="1507b11d89b",x=-20,y=-15},right={img="1507b11e927",x=-20,y=-15}},
            shaman={left={img="1507b11f9df",x=-25,y=-47},right={img="1507b121ae7",x=-25,y=-47,},cheese={"transformice","cheese"}},
            mouse={left={img="1507b123bd4",x=-21,y=-30},right={img="1507b125cf9",x=-21,y=-30},cheese={"transformice","cheese"}},
            boat={left={img="1507b127e61",x=-144,y=-242},right={img="1507b129045",x=-144,y=-242}},
            transformice={img="1507b12a1f0",x=-109,y=-35},
            bouboum={img="1507b12b366",x=-70,y=-60},
            ghost={left={img="149c0689433",x=-21,y=-28},right={img="149c068e42f",x=-21,y=-28}},
            shamousey={img="1507b18e69d",x=-40,y=-52},
            evilsantah={img="15121b35ce9",x=-36,y=-51},
            silverbea={img="1507b18f7fb",x=-37,y=-57},
            hybinkunduz={img="1511d5daf3c",x=-48,y=-77},
            emote1={img="150ab0c288e",x=-15,y=-10},
            emote2={img="150ab0c4bd0",x=-15,y=-10},
            emote3={img="150ab0c6e66",x=-15,y=-10},
            emote4={img="150ab0d3ac2",x=-15,y=-10},
            emote5={img="150ab0cd43a",x=-15,y=-10},
            emote6={img="150ab0d18c5",x=-15,y=-10},
            emote7={img="150ab0cb1d1",x=-15,y=-10},
            emote8={img="150ab0c9044",x=-15,y=-10},
            emote9={img="150ab0cf763",x=-15,y=-10},
            emote0={img="150ab0d5da4",x=-15,y=-10},
            airballoon={left={img="15246f784c0",x=-50,y=-80,l="$"},right={img="15246f7b3f6",x=-50,y=-80,l="$"}},
            broom={left={img="151c067545f",x=-38,y=-42},right={img="151c067ad92",x=-38,y=-42},cheese={"transformice","cheese"}},
            cupid={left={img="151c07d7df0",x=-32,y=-38},right={img="151c07cf5ab",x=-32,y=-38}},
        },
        memes={
            doge={left={img="1507b1a432a",x=-30,y=-43},right={img="1507b1a54a9",x=-30,y=-43}},
            pffftch={img="1507b1a6609",x=-25,y=-25},
            pokerface={img="1507b1a76d7",x=-25,y=-25},
            pokerface2={img="1507b1a8772",x=-25,y=-25},
            rage={img="1507b1a98c7",x=-25,y=-25},
            sadface={img="1507b1aa996",x=-25,y=-25},
            suspicious={img="1507b1aba24",x=-25,y=-25},
            sweetjesus={img="1507b1acab8",x=-25,y=-25},
            troll2={img="1507b1adc13",x=-25,y=-25},
            truestory={img="1507b1aee88",x=-25,y=-25},
            unimpressed={img="1507b1aff31",x=-25,y=-25},
            badass={img="1507b1b0ffb",x=-25,y=-25},
            what={img="1507b1b20c3",x=-25,y=-25},
            youdontsay={img="1507b1b314f",x=-25,y=-25},
            yuno={img="1507b1b4200",x=-25,y=-25},
            actually={img="1507b1b52a7",x=-25,y=-25},
            areyoufuckingkiddingme={img="1507b1b6340",x=-25,y=-25},
            areyouserious={img="1507b1b73d8",x=-25,y=-25},
            awwyeah={img="1507b1b8475",x=-25,y=-25},
            bitchplease={img="1507b1b94f9",x=-25,y=-25},
            cereal={img="1507b1ba583",x=-25,y=-25},
            challengeaccepted={img="1507b1bb693",x=-25,y=-25},
            confident={img="1507b1bc76c",x=-25,y=-25},
            derp={img="1507b1bd80d",x=-25,y=-25},
            epic={img="1507b1be8c3",x=-25,y=-25},
            epicrage={img="1507b1bfa13",x=-25,y=-25},
            betterthanexpected={img="1507b1c0a9d",x=-25,y=-25},
            foreveralone={img="1507b1c1b6e",x=-25,y=-25},
            happyderp={img="1507b1c2c6a",x=-25,y=-25},
            happytroll={img="1507b1c3d31",x=-25,y=-25},
            herp={img="1507b1c4dcb",x=-25,y=-25},
            ilied={img="1507b1c5e8e",x=-25,y=-25},
            itsnotok={img="1507b1c5e8e",x=-25,y=-25},
            likeasir={img="1507b1c803d",x=-25,y=-25},
            listening={img="1507b1c90c8",x=-25,y=-25},
            lol={img="1507b1ca194",x=-25,y=-25},
            megusta={img="1507b1cb245",x=-25,y=-25},
            motherofgod={img="1507b1cc438",x=-25,y=-25},
            notbad={img="1507b1cd4f2",x=-25,y=-25},
            nothingtodohere={img="1507b1ce598",x=-25,y=-25},
            notsureifmegusta={img="1507b1cf647",x=-25,y=-25},
            ohgodwhy={img="1507b1d0768",x=-25,y=-25},
            ohno={img="1507b1d17ef",x=-25,y=-25},
            okay={img="1507b1d289c",x=-25,y=-25},
        },
        props={
            [0]={name="Bush",left={img="1507c123d24"},right={img="1507c125b72"},x=-30,y=-11},
            {name="Tree 1",left={img="1507c1279f1"},right={img="1507c1298ca"},x=-71,y=-190},
            {name="Fern",left={img="1507c12b63d"},right={img="1507c12d45c"},x=-19,y=-17},
            {name="Blue Flower",left={img="1507c12f284"},right={img="1507c130f4a"},x=-7,y=-3},
            {name="Sign",left={img="1507c132d38"},right={img="1507c134aad"},x=-24,y=-40},
            {name="Grass",left={img="1507c1367c8"},right={img="1507c1386ff"},x=-33,y=-28},
            {name="Palm Tree",left={img="1507c13a4dc",x=-83},right={img="1507c13c643",x=-23},y=-142},
            {name="Umbrella",left={img="1507c13e427"},right={img="1507c140363"},x=-51,y=-55},
            {name="Sand Castle",left={img="1507c1427a1"},right={img="1507c1445c8"},x=-25,y=-12},
            {name="Shovel",left={img="1507c146315"},right={img="1507c1480ec"},x=-15,y=3},
            {name="Sand Bucket",left={img="1507c2fa50e"},right={img="1507c14a0f0"},x=-10,y=-5},
            {name="Red Flower",left={img="1507c14bcc9"},right={img="1507c14da5c"},x=-7,y=-9},
            {name="Thorns",left={img="1507c14f8ad"},right={img="1507c151e4d"},x=-31,y=-4},
            {name="Fence",left={img="1507c153f8f"},right={img="1507c155f52"},x=-24,y=-35},
            {name="Window"},
            {name="2-seater Sofa",left={img="1507c157eb1"},right={img="1507c159ee1"},x=-87,y=-47},
            {name="Chair",left={img="1507c15be7f"},right={img="1507c15dde5"},x=-20,y=-49},
            {name="Table 1",left={img="1507c15fcf4"},right={img="1507c161d35"},x=-60,y=-22},
            {name="Vase of Flowers 1",left={img="1507c163dc6"},right={img="1507c165e4c"},x=-20,y=-33},
            {name="Sofa with 1 Place",left={img="1507c167eea"},right={img="1507c169e6e"},x=-34,y=-44},
            {name="Vase of Flowers 2",left={img="1507c16be63"},right={img="1507c16e01e"},x=-22,y=-53},
            {name="Roast Chicken",left={img="1507c170081"},right={img="1507c172145"},x=-25,y=-34},
            {name="Bookcase 1",left={img="1507c174292"},right={img="1507c17632b"},x=-36,y=-121},
            {name="Poster",left={img="1507c1786c7"},right={img="1507c17a7f8"},x=-70,y=-95},
            {name="Bed",left={img="1507c17d006"},right={img="1507c17f54d"},x=-67,y=-55},
            {name="Radio",left={img="1507c2fc489"},right={img="1507c2fe292"},x=-22,y=-30},
            {name="Teddy",left={img="1507c3005f6"},right={img="150c9cc7c5e"},x=-34,y=-47},
            {name="Lamp",left={img="1507c1859b9"},right={img="1507c187c74"},x=-29,y=-148},
            {name="Refrigerator",left={img="1507c18a288"},right={img="1507c18c97e"},x=-34,y=-99},
            {name="Wardrobes",left={img="1507c18f145"},right={img="1507c19183e"},x=-52,y=-111},
            {name="TV with Stand",left={img="1507c193ea7"},right={img="1507c196527"},x=-45,y=-70},
            {name="Soda",left={img="1507c198b62"},right={img="1507c19afbf"},x=-6,y=-16},
            {name="Vase of Flowers 3",left={img="1507c19d5cd"},right={img="1507c19fb5a"},x=-17,y=-23},
            {name="Nightstand",left={img="1507c1a482e"},right={img="1507c1a225e"},x=-17,y=-27},
            {name="Background"},
            {name="Range of Halloween 1",left={img="1507c3028bb"},right={img="1507c30501b"},x=-127,y=-51},
            {name="Range of Halloween 2",left={img="1507c1a7f36"},right={img="1507c1aa5b2"},x=-137,y=-54},
            {name="Broom",left={img="1507c1aca48"},right={img="1507c1aed89"},x=-33,y=-75},
            {name="Skeleton",left={img="1507c1b13a7"},right={img="1507c1b3901"},x=-54,y=-145},
            {name="Halloween Poster",left={img="1507c1b5ee8"},right={img="1507c1b8898"},x=-72,y=-102},
            {name="Balloons Halloween",left={img="1507c1bd1ac"},right={img="1507c1bae44"},x=-39,y=-129},
            {name="Web with Spider",left={img="1507c3078f5"},right={img="1507c309c45"},x=-29,y=-105},
            {name="Autumn Tree",left={img="1507c1c0644"},right={img="1507c1c2bb7"},x=-72,y=-190},
            {name="Bats",left={img="1507c1c51cc"},right={img="1507c1c7834"},x=-74,y=-45},
            {name="Torch",left={img="1507c1c9e1b"},right={img="1507c1cc31d"},x=-35,y=-114},
            {name="Cemetery Background"},
            {name="Torch 2",left={img="1507c1ceba0"},right={img="1507c1d1245"},x=-33,y=-107},
            {name="Fencing",left={img="150c9e4e11a"},right={img="1507c1d388e"},x=-77,y=-54},
            {name="Pumpkin 1",left={img="1507c30c10d"},right={img="1507c30e7e0"},x=-34,y=-38},
            {name="Pumpkin 2",left={img="1507c310c65"},right={img="1507c312f44"},x=-93,y=-115},
            {name="Snowmouse",left={img="1507c3151f4"},right={img="1507c1da0e8"},x=-38,y=-59},
            {name="Snowy Tree",left={img="1507c1dc550"},right={img="1507c1deaaf"},x=-72,y=-194},
            {name="Cookies with Milk",left={img="1507c3174c8"},right={img="1507c31992b"},x=-36,y=-5},
            {name="Garland",left={img="1507c31bd94"},right={img="1507c31e28c"},x=-42,y=-71},
            {name="Stocking",left={img="1507c3206f2"},right={img="1507c322bf8"},x=-13,y=-38},
            {name="Candle 1",left={img="1507c324fe9"},right={img="1507c32759a"},x=-12,y=-22},
            {name="Band Christmas",left={img="1507c329e3f"},right={img="1507c32c440"},x=-125,y=-55},
            {name="Christmas Tree",left={img="1507c32ec28"},right={img="1507c33126c"},x=-83,y=-190},
            {name="Ice Stalactites",left={img="1507c3335cb"},right={img="1507c335bc1"},x=-40,y=-21},
            {name="Mistletoe",left={img="1507c33802d"},right={img="1507c33a3fa"},x=-46,y=-67},
            {name="Ballball",left={img="1507c33c8de"},right={img="1507c33ec72"},x=-13,y=-22},
            {name="Fairy Lights",left={img="1507c3411a5"},right={img="1507c3430f3"},x=-58,y=-32},
            {name="Present",left={img="1507c345573"},right={img="1507c347926"},x=-18,y=-21},
            {name="Gifts",left={img="1507c349d56"},right={img="1507c34c178"},x=-63,y=-49},
            {name="Santah's Sleigh",left={img="1507c350a97"},right={img="1507c34e568"},x=-67,y=-50},
            {name="Ribbon",left={img="1507c3b3600"},right={img="1507c3b5b08"},x=-27,y=-34},
            {name="Umbrella Valentine's Day",left={img="1507c3b81c3"},right={img="1507c3ba7d4"},x=-63,y=-129},
            {name="Chair Valentine's Day",left={img="1507c354d75"},right={img="1507c357241"},x=-22,y=-50},
            {name="Table Valentine's Day",left={img="1507c3595e6"},right={img="1507c35da96"},x=-30,y=-25},
            {name="Valentine's Day Menu",left={img="1507c35ff1d"},right={img="150c9ec0917"},x=-34,y=-60},
            {name="Valentine's Day Gift",left={img="1507c3bcd67"},right={img="1507c3bf520"},x=-18,y=-5},
            {name="Candle 2",left={img="1507c3c15a3"},right={img="1507c3c3a91"},x=-6,y=-36},
            {name="Vase of Flowers 4",left={img="1507c3c5de1"},right={img="1507c3c844b"},x=-25,y=-34},
            {name="Flower in Vase",left={img="1507c3ca981"},right={img="1507c3cce28"},x=-9,y=-25},
            {name="Ribbons with Hearts",left={img="1507c3cf44e"},right={img="1507c3d18a5"},x=-33,y=-95},
            {name="Balloon Heart",left={img="1507c3d3ac8"},right={img="1507c3d5e56"},x=-22,y=-70},
            {name="Window Valentine's Day",left={img="1507c3d83c3"},right={img="1507c3da899"},x=-59,y=-68},
            {name="Heart Pendant",left={img="150c9ee0c4f"},right={img="1507c427f52"},x=-19,y=-42},
            {name="Stones with Algae",left={img="1507c42a0ef"},right={img="1507c42c3f7"},x=-32,y=-19},
            {name="Algae 1",left={img="1507c20e9d5"},right={img="1507c21124b"},x=-24,y=-65},
            {name="Chest",left={img="1507c42e7ab"},right={img="1507c430927"},x=-32,y=-31},
            {name="Starfish",left={img="1507c432aec"},right={img="1507c434afd"},x=-16,y=8},
            {name="Shell",left={img="1507c436b00"},right={img="1507c438f88"},x=-18,y=-2},
            {name="Stones",left={img="1507c43b38d"},right={img="1507c43d4e5"},x=-50,y=-2},
            {name="Stones with Algae 2",left={img="1507c43f6f6"},right={img="1507c4418e7"},x=-90,y=-42},
            {name="Coral 1",left={img="1507c4438df"},right={img="1507c445508"},x=-26,y=-19},
            {name="Coral 2",left={img="1507c44740d"},right={img="1507c20bada"},x=-48,y=-61},
            {name="Algae 2",left={img="1507c20e9d5"},right={img="1507c21124b"},x=-25,y=-66},
            {name="Broken Vase",left={img="150c9f0916c"},right={img="1507c4492b0"},x=-27,y=-28},
            {name="Big Screen"},
            {name="Small Screen"},
            {name="Alchemy Pot",left={img="1507c44b0fb"},right={img="1507c218f50"},x=-10,y=-28},
            {name="Objects of Alchemy 1",left={img="1507c21dc38"},right={img="1507c21b5f6"},x=-55,y=-70},
            {name="Objects of Alchemy 2",left={img="1507c22220b"},right={img="1507c21fcef"},x=-40,y=-47},
            {name="Bookshelf 2",left={img="1507c2248af"},right={img="1507c22720b"},x=-53,y=-148},
            {name="Piano",left={img="1507c22927c"},right={img="1507c22b197"},x=-68,y=-78},
            {name="Fireplace",left={img="1507c22d068"},right={img="1507c22ef09"},x=-62,y=-73},
            {name="Candelebra",left={img="150c9f2fa3c"},right={img="1507c230ba2"},x=-22,y=-62},
            {name="Coffin",left={img="1507c44ce09"},right={img="1507c44ec3d"},x=-31,y=-110},
            {name="Altar",left={img="1507c450b25"},right={img="1507c452a6c"},x=-30,y=-75},
            {name="Bottle with Substance 1",left={img="1507c454922"},right={img="1507c4567e0"},x=-13,y=-23},
            {name="Bottle with Substance 2",left={img="1507c458796"},right={img="1507c45a662"},x=-7,y=-6},
            {name="Chandelier",left={img="1507c45e425"},right={img="1507c45c4c4"},x=-44,y=-102},
            {name="Barrel",left={img="1507cbc6847"},right={img="1507cbc8e33"},x=-26,y=-47},
            {name="Table 2",left={img="150c9f48c07"},right={img="1507cbcb54e"},x=-88,y=-25},
            {name="Chair 2",left={img="1507c463ad0"},right={img="1507c465932"},x=-22,y=-58},
            {name="Skull",left={img="1507c469398"},right={img="1507c4677ef"},x=-15,y=-8},
            {name="Cobweb 1",left={img="1507c46b204"},right={img="1507c46d0e8"},x=-26,y=-43},
            {name="Cobweb 2",left={img="1507c46ef14"},right={img="1507c470d94"},x=-76,y=-37},
            {name="Cobweb 3",left={img="1507c472bb5"},right={img="1507c4749e2"},x=-70,y=-65},
            {name="Cobweb 4",left={img="1507c47680b"},right={img="1507c478661"},x=-30,y=-68},
            {name="Cobweb 5",left={img="1507c47a4b5"},right={img="1507c47c30f"},x=-20,y=-58},
            {name="Vampire Portrait",left={img="1507c47e1d9"},right={img="1507c4800f6"},x=-44,y=-92},
            {name="Fruit Bowl",left={img="1507c482093"},right={img="1507c483f84"},x=-16,y=-23},
            {name="Mirror",left={img="150c9f6f48a"},right={img="1507c485ec8"},x=-43,y=-121},
            {name="Tombstone of Elise",left={img="1507c487f03"},right={img="1507c489dea"},x=-21,y=-43},
            {name="Crucifix",left={img="1507c24de51"},right={img="1507c24fed6"},x=-27,y=-57},
            {name="Background 2"},
            {name="RIP Tombstone",left={img="1507c252411"},right={img="1507c2549ab"},x=-21,y=-42},
            {name="Toilet",left={img="1507c256bcc"},right={img="1507c258fe8"},x=-23,y=-41},
            {name="Bath",left={img="1507c25b770"},right={img="1507c25dc34"},x=-55,y=-90},
            {name="Sink",left={img="1507c2601d3"},right={img="1507c26281d"},x=-23,y=-26},
            {name="Mirror",left={img="1507c48bd17"},right={img="1507c265302"},x=-24,y=-39},
            {name="Pan Rack",left={img="1507c2676f9"},right={img="1507c269dc3"},x=-38,y=-29},
            {name="Oven",left={img="1507c26c534"},right={img="1507c26e9c4"},x=-26,y=-39},
            {name="Rocking Chair",left={img="1507c270f9a"},right={img="1507c273508"},x=-38,y=-61},
            {name="Pan",left={img="1507c48dbdc"},right={img="1507c48fa91"},x=-20,y=-10},
            {name="Stool",left={img="1507c491936"},right={img="1507c2771fc"},x=-15,y=-26},
            {name="Cupboard",left={img="1507c27975f"},right={img="1507c27bcc9"},x=-26,y=-35},
            {name="Drawers",left={img="1507c27e26c"},right={img="1507c280726"},x=-26,y=-35},
            {name="Lava Lamp",left={img="150c9fac35e"},right={img="1507c493939"},x=-11,y=-35},
            {name="Checkpoint"}
        },
        pokemon={
            --[[ POKEMON ]]--
            pokeball={img="1507b1faa2a",x=-8,y=-9},
            egg={img="1507b1fbc31",x=-40,y=-40},
            missingno1={img="1507b1fcd7a",x=-40,y=-40},
            missingno2={img="1507b1fde7a",x=-28,y=-28},
            missingno3={img="1507b1fef38",x=-30,y=-30},
            missingno4={img="1507b2002ac",x=-30,y=-30},
            
            rhydon={left={img="1507b5108b3"},right={img="1507b511ba9"}},
            murkrow={left={img="1507b82d3a3"},right={img="1507b82eaf6"}},
            thundurus={left={img="1507be64f7a"},right={img="1507be66cd5"}},
            sunkern={left={img="1507b818284"},right={img="1507b819889"}},
            venusaur={left={img="1507b2de388"},right={img="1507b2df4a5"}},
            shellos={left={img="1507bc4e99a"},right={img="1507bc5049c"}},
            leafeon={left={img="1507bceb1c9"},right={img="1507bcecd82"}},
            natu={left={img="1507b618aab"},right={img="1507b61a04f"}},
            granbull={left={img="1507b84cba6"},right={img="1507b84e47f"}},
            totodile={left={img="1507b5e45f6"},right={img="1507b5e5c1a"}},
            wartortle={left={img="1507b2e8fbc"},right={img="1507b2ea17b"}},
            lucario={left={img="1507bca2275"},right={img="1507bca3d7b"}},
            igglybuff={left={img="1507b61072c"},right={img="1507b611d28"}},
            girafarig={left={img="1507b987b81"},right={img="1507b83b1a5"}},
            giratina={left={img="1507bd22764"},right={img="1507bd242a3"}},
            swampert={left={img="1507b89b085"},right={img="1507b9f9e9a"}},
            exeggutor={left={img="1507b4fb1d4"},right={img="1507b4fc4da"}},
            bagon={left={img="1507b74f97b"},right={img="1507b751067"}},
            audino={left={img="1507bdb7f3b"},right={img="1507bdb9a8a"}},
            meditite={left={img="1507b92d822"},right={img="1507b92eff8"}},
            meloetta={left={img="1507c06abd6"},right={img="1507c06c9ef"}},
            hippowdon={left={img="1507bca8a84"},right={img="1507bcaa4c8"}},
            toxicroak={left={img="1507bcb5d04"},right={img="1507bcb7860"}},
            dodrio={left={img="1507b4cfd99"},right={img="1507b4d1497"}},
            drilbur={left={img="1507beb8897"},right={img="1507beba5e2"}},
            cradily={left={img="1507b93ea3d"},right={img="1507b9402b2"}},
            sharpedo={left={img="1507b6dc4f7"},right={img="1507b6ddb35"}},
            jumpluff={left={img="1507b811ff8"},right={img="1507b813732"}},
            bouffalant={left={img="1507be2ce03"},right={img="1507be2e985"}},
            goldeen={left={img="1507b51eb7d"},right={img="1507b51ffb4"}},
            bellossom={left={img="1507b6264f6"},right={img="1507b627b0a"}},
            shroomish={left={img="1507b8e4fe8"},right={img="1507b8e695d"}},
            whismur={left={img="1507b8ffaa5"},right={img="1507b90136e"}},
            azurill={left={img="1507b910640"},right={img="1507b911eba"}},
            larvesta={left={img="1507be4ee30"},right={img="1507be50bca"}},
            spoink={left={img="1507b6ee019"},right={img="1507b6efd6d"}},
            zoroark={left={img="1507bf46044"},right={img="1507bf47eca"}},
            rhyhorn={left={img="1507b50edd7"},right={img="1507b58ae69"}},
            rayquaza={left={img="1507ba3e696"},right={img="1507ba803dc"}},
            metapod={left={img="1507b2ef738"},right={img="1507b2f086b"}},
            seviper={left={img="1507b70e326"},right={img="1507b70fb3f"}},
            marshtomp={left={img="1507b8980d4"},right={img="1507b89984e"}},
            tangrowth={left={img="1507bcda7dd"},right={img="1507bcdc2a4"}},
            darumaka={left={img="1507bf0aa6b"},right={img="1507bf0c84d"}},
            sableye={left={img="1507b91e51b"},right={img="1507b91fda5"}},
            xatu={left={img="1507b61b625"},right={img="1507b61cbf6"}},
            starly={left={img="1507bbf8047"},right={img="1507bbf9ce9"}},
            slaking={left={img="1507b8f19eb"},right={img="1507b8f3269"}},
            donphan={left={img="1507b9c16d2"},right={img="1507b9c2f77"}},
            suicune={left={y=-55,img="1507b9ea1b4"},right={y=-55,img="1507b9ebaa3"}},
            torkoal={left={img="1507b6eafd4"},right={img="1507b6ec7af"}},
            gible={left={img="1507bc91931"},right={img="1507bc932b8"}},
            shellgon={left={img="1507b75279e"},right={img="1507b753ea4"}},
            flaffy={left={img="1507b620da8"},right={img="1507b62237c"}},
            jynx={left={img="1507b52e733"},right={img="1507b52fa4f"}},
            raticate={left={img="1507b3034b5"},right={img="1507b3046ba"}},
            sandslash={left={img="1507b316727"},right={img="1507b3178eb"}},
            magneton={left={img="1507b5883b8"},right={img="1507b4c9c6e"}},
            pidove={left={img="1507bd9098c"},right={img="1507bd925ad"}},
            grimer={left={img="1507b4d7248"},right={img="1507b4d84d2"}},
            munchlax={left={img="1507bc9b8bf"},right={img="1507bc9d33e"}},
            zubat={left={img="1507b33389a"},right={img="1507b334a1f"}},
            beautifly={left={img="1507b8af127"},right={img="1507b8b09cd"}},
            tyrogue={left={img="1507b9ce1c2"},right={img="1507b9cfabe"}},
            servine={left={img="1507bd41b04"},right={img="1507bd435d1"}},
            lugia={left={img="1507b87b728"},right={img="1507b87d0f1"}},
            hitmonlee={left={img="1507b5026d2"},right={img="1507b503bca"}},
            gastrodon={left={img="1507bc51f18"},right={img="1507bc53946"}},
            snorlax={left={y=-60,img="1507b55e189"},right={y=-60,img="1507b55f564"}},
            mimejr={left={y=-60,img="1507bc8443c"},right={y=-60,img="1507bc85eeb"}},
            slowking={left={img="1507b830196"},right={img="1507b831942"}},
            cottonee={left={img="1507beed5c3"},right={img="1507beef2ec"}},
            floatzel={left={img="1507bc45fa8"},right={img="1507bc47980"}},
            accelgor={left={img="1507c04a520"},right={img="1507c04c31c"}},
            klang={left={img="1507c01a9c2"},right={img="1507c01c907"}},
            lumineon={left={img="1507bcbfe8e"},right={img="1507bcc1982"}},
            snorunt={left={img="1507ba11258"},right={img="1507ba12aba"}},
            stunky={left={img="1507bc7356d"},right={img="1507bc75062"}},
            dwebble={left={img="1507bff5e6c"},right={img="1507bff7e50"}},
            misdreavus={left={img="1507b83316b"},right={img="1507b834918"}},
            electrike={left={img="1507b933b3b"},right={img="1507b935493"}},
            numel={left={img="1507b6e4c6a"},right={img="1507b6e6203"}},
            ampharos={left={img="1507b62398d"},right={img="1507b624f47"}},
            golurk={left={img="1507c0616cd"},right={img="1507c06336a"}},
            scyther={left={img="1507b52bfb1"},right={img="1507b52d35b"}},
            nidorina={left={img="1507b31ae1f"},right={img="1507b31bf36"}},
            pidgeotto={left={img="1507b2fcfe2"},right={img="1507b2fe0da"}},
            weedle={left={img="1507b2f3d7c"},right={img="1507b2f51da"}},
            armaldo={left={img="1507b944d89"},right={img="1507b94614d"}},
            prinplup={left={img="1507bbf08ea"},right={img="1507bbf26c8"}},
            croagunk={left={img="1507bcb27fc"},right={img="1507bcb4288"}},
            magby={left={img="1507b9da64f"},right={img="1507b9dbfcd"}},
            solrock={left={img="1507b714624"},right={img="1507b715d97"}},
            slakoth={left={img="1507b8eb323"},right={img="1507b8ecca8"}},
            torchic={left={img="1507b9f865e"},right={img="1507b88c384"}},
            gengar={left={img="1507b4e53af"},right={img="1507b4e6631"}},
            zangoose={left={img="1507b70b3f3"},right={img="1507b70cb19"}},
            skitty={left={img="1507b9168e0"},right={img="1507b918165"}},
            meganium={left={img="1507b5d9478"},right={img="1507b5dac92"}},
            hoppip={left={img="1507b80c456"},right={img="1507b80dba6"}},
            ralts={left={img="1507b8d68ab"},right={img="1507b8d81d3"}},
            garbodor={left={img="1507bf3ebb4"},right={img="1507bf4090c"}},
            voltorb={left={y=-55,img="1507b4f3c5f"},right={y=-55,img="1507b4f5104"}},
            pidgey={left={img="1507b2facd2"},right={img="1507b2fbe1e"}},
            grovyle={left={y=-55,img="1507b8862d4"},right={y=-55,img="1507b887a44"}},
            golett={left={img="1507c05dbcb"},right={img="1507c05fa31"}},
            seedot={left={img="1507b8c0bf9"},right={img="1507b8c2496"}},
            clefable={left={img="1507b3284cf"},right={img="1507b32962d"}},
            wigglytuff={left={img="1507b3312e0"},right={img="1507b3326dc"}},
            manaphy={left={img="1507bd2cd60"},right={img="1507bd2e99b"}},
            noctowl={left={img="1507b5f4dec"},right={img="1507b5f63b6"}},
            golem={left={y=-55,img="1507b4baf72"},right={y=-55,img="1507b587027"}},
            lopunny={left={img="1507bc6089c"},right={img="1507bc62345"}},
            cryogonal={left={img="1507bfb0bed"},right={img="1507bfb2b88"}},
            porygon2={left={img="1507b9c4918"},right={img="1507b9c62a5"}},
            arbok={left={y=-55,img="1507b30dba4"},right={y=-55,img="1507b30ed0f"}},
            psyduck={left={img="1507b43d426"},right={img="1507b43e7af"}},
            ekans={left={img="1507b309d85"},right={img="1507b30c9e9"}},
            feraligatr={left={img="1507b5e9dc7"},right={img="1507b5eb384"}},
            gabite={left={img="1507bc94dab"},right={img="1507bc9686c"}},
            mienshao={left={img="1507c056110"},right={img="1507c057fd9"}},
            kingler={left={img="1507b4f158b"},right={img="1507b4f294b"}},
            taillow={left={img="1507b8ca2bd"},right={img="1507b8cbbb4"}},
            glaceon={left={img="1507bcee886"},right={img="1507bcf02bd"}},
            zweilous={left={img="1507be47bfb"},right={img="1507be49847"}},
            torterra={left={img="1507bbdd839"},right={img="1507bbdf699"}},
            darmanitan={left={img="1507bf0e594"},right={img="1507bf10396"}},
            nidoranm={left={img="1507b31f67f"},right={img="1507b320811"}},
            mewtwo={left={y=-60,img="1507b56d539"},right={y=-60,img="1507b5924c2"}},
            kabutops={left={img="1507b55920c"},right={img="1507b55a5a5"}},
            patrat={left={img="1507bd5c9c6"},right={img="1507bd5e507"}},
            whirlipede={left={img="1507bee5fc0"},right={img="1507bee7da7"}},
            kadabra={left={y=-55,img="1507b4541cd"},right={y=-55,img="1507b455315"}},
            ludicolo={left={img="1507b8bdb1d"},right={img="1507b8bf2e4"}},
            empoleon={left={img="1507bbf43bc"},right={img="1507bbf62c9"}},
            kricketune={left={img="1507beab9d2"},right={img="1507bc0eb0c"}},
            fearow={left={img="1507b3079d9"},right={img="1507b308b1d"}},
            musharna={left={img="1507bd8d1c4"},right={img="1507bd8ed44"}},
            nidoqueen={img="1507b31d216"},
            carvahna={left={img="1507b6d8c13"},right={img="1507b6da1a1"}},
            seismitoad={left={img="1507becc898"},right={img="1507bece80a"}},
            skarmory={left={img="1507b9b1e22"},right={img="1507b9b3713"}},
            nuzleaf={left={img="1507b8c3dbb"},right={img="1507b8c560c"}},
            meowth={left={img="1507b4389ea"},right={img="1507b439ac6"}},
            charmander={left={img="1507b2e05b6"},right={img="1507b2e16b2"}},
            exeggcute={left={img="1507b4f8b8f"},right={img="1507b4f9ef4"}},
            alakazam={left={y=-60,img="1507b456476"},right={y=-60,img="1507b457651"}},
            cyndaquil={left={img="1507b5dc239"},right={img="1507b5dd806"}},
            simipour={left={img="1507bd8626a"},right={img="1507bd87e24"}},
            watchog={left={img="1507bd6000f"},right={img="1507bd61b0f"}},
            dratini={left={img="1507b58ebc5"},right={img="1507b58fccb"}},
            sawsbuck={left={img="1507bf7cb8f"},right={img="1507bf7ec39"}},
            glameow={left={img="1507bc6968a"},right={img="1507bc6b077"}},
            happiny={left={img="1507bc879c9"},right={img="1507bc894c9"}},
            electrode={left={img="1507b4f643a"},right={img="1507b4f786a"}},
            sudowoodo={left={img="1507b806688"},right={img="1507b807db1"}},
            smoochum={left={img="1507b9d4654"},right={img="1507b9d5fee"}},
            monferno={left={img="1507bbe518d"},right={img="1507bbe7175"}},
            koffing={left={img="1507b509ea9"},right={img="1507b50b31b"}},
            klink={left={img="1507c0168fb"},right={img="1507c0188e4"}},
            sylveon={left={img="1507c0728cf"},right={img="1507c0745d4"}},
            spheal={left={img="1507ba1755d"},right={img="1507ba18de0"}},
            lickitung={left={img="1507b50783a"},right={img="1507b508b66"}},
            rapidash={left={img="1507b4bf63a"},right={img="1507b4c097a"}},
            silcoon={left={img="1507b8ab799"},right={img="1507b8acff3"}},
            galvantula={left={img="1507c00afb6"},right={img="1507c00cea1"}},
            serperior={left={img="1507bd45127"},right={img="1507bd46c00"}},
            aron={left={y=-45,img="1507b92430f"},right={y=-45,img="1507b925b50"}},
            magmar={left={img="1507b5337ef"},right={img="1507b534b93"}},
            zapdos={left={y=-60,img="1507b5630b1"},right={y=-60,img="1507b564459"}},
            uxie={left={img="1507bd0a8de"},right={img="1507bd0c425"}},
            dewgong={left={img="1507b4d4cc5"},right={img="1507b4d5fb0"}},
            drifloon={left={img="1507beaf764"},right={img="1507beb160d"}},
            slowbro={left={img="1507b4c4208"},right={img="1507b4c55d1"}},
            dugtrio={left={img="1507b43604f"},right={img="1507b43715f"}},
            gorebyss={left={img="1507b746d21"},right={img="1507b7485bb"}},
            quilfish={left={img="1507b84fd60"},right={img="1507b851479"}},
            wobbuffet={left={img="1507b838d67"},right={img="1507b9862eb"}},
            charizard={left={y=-65,img="1507b2e4abb"},right={y=-65,img="1507b2e5c4c"}},
            mesprit={left={img="1507bd0df9e"},right={img="1507bd0fb82"}},
            poliwhirl={left={img="1507b44d942"},right={img="1507b44ea34"}},
            zebstrika={left={img="1507bd9e5d3"},right={img="1507bda0344"}},
            clefairy={left={img="1507b3260e1"},right={img="1507b327362"}},
            togetic={left={img="1507b615ec5"},right={img="1507b6174b8"}},
            blissey={left={img="1507b9e0aad"},right={img="1507b9e23ae"}},
            amoonguss={left={img="1507bf9015d"},right={img="1507bf920db"}},
            wooper={left={img="1507b820b7b"},right={img="1507b8222f6"}},
            cherubi={left={img="1507beadac7"},right={img="1507bc498e8"}},
            tornadus={left={img="1507be61666"},right={img="1507be632e4"}},
            honchkrow={left={img="1507bc6754d"},right={img="1507beb34d9"}},
            porygonZ={left={img="1507bcf88db"},right={img="1507beb52ed"}},
            yveltal={left={img="1507c07638c"},right={img="1507c0780da"}},
            heracross={left={img="1507ba7cf7d"},right={img="1507b98ba2b"}},
            nidoranf={left={img="1507b318a84"},right={img="1507b319c2b"}},
            shedinja={left={img="1507b8fc6cb"},right={img="1507b8fe180"}},
            walrein={left={img="1507ba1df04"},right={img="1507ba1f74d"}},
            ninjask={left={img="1507b8f9361"},right={img="1507b8facca"}},
            manectric={left={img="1507b936cff"},right={img="1507b6c1408"}},
            swanna={left={img="1507bffbb94"},right={img="1507bf6b140"}},
            chinchou={left={img="1507b6056da"},right={img="1507b606cef"}},
            ledyba={left={img="1507b5f79c4"},right={img="1507b5f8fe3"}},
            relicanth={left={img="1507b749d55"},right={img="1507b74b4ca"}},
            corphish={left={img="1507b71d629"},right={img="1507b71eec2"}},
            butterfree={left={y=-55,img="1507b2f1946"},right={y=-55,img="1507b2f2a45"}},
            riolu={left={img="1507bc9ecc9"},right={img="1507bca0746"}},
            cacturne={left={img="1507b7040c3"},right={img="1507b7059ea"}},
            sandile={left={img="1507beff93c"},right={img="1507bf016e9"}},
            shiftry={left={img="1507b8c6e2e"},right={img="1507b8c896b"}},
            simisage={left={img="1507bd7871c"},right={img="1507bd7a25d"}},
            omastar={left={img="1507b554fa5"},right={img="1507b5562f7"}},
            kakuna={left={img="1507b2f640f"},right={img="1507b2f7961"}},
            ivysaur={left={img="1507b2dbec0"},right={img="1507b2dd037"}},
            budew={left={img="1507bc1aaea"},right={img="1507bc1c6fb"}},
            gloom={left={img="1507b33a57f"},right={img="1507b33b818"}},
            tentacruel={left={img="1507b4b36cf"},right={img="1507b4b4a96"}},
            buneary={left={img="1507bc5d20f"},right={img="1507bc5ed6a"}},
            hoothoot={left={img="1507b5f21c6"},right={img="1507b5f381f"}},
            roggenrola={left={img="1507bda1f25"},right={img="1507bda3a74"}},
            phanpy={left={img="1507b9be52c"},right={img="1507b9bfe0d"}},
            bonsly={left={img="1507bc80e68"},right={img="1507bc8292a"}},
            whiscash={left={img="1507b71a5c1"},right={img="1507b71bdad"}},
            krokorok={left={img="1507bf03492"},right={img="1507bf051a8"}},
            muk={left={img="1507b4d978b"},right={img="1507b4daa5e"}},
            ponyta={left={img="1507b4bcff0"},right={img="1507b4be3da"}},
            burmy={left={img="1507bc2f1b3"},right={img="1507bc30b7c"}},
            bellsprout={left={img="1507b4a9a50"},right={img="1507b4aad95"}},
            bronzong={left={img="1507bc7d918"},right={img="1507bc7f3ea"}},
            swalot={left={img="1507b6d5d28"},right={img="1507b6d74a3"}},
            seadra={left={img="1507b51c3ba"},right={img="1507b51d7a4"}},
            virizion={left={img="1507be5db9c"},right={img="1507be5f999"}},
            rattata={left={img="1507b30130c"},right={img="1507b3023f9"}},
            stunfish={left={img="1507c04e123"},right={img="1507c050091"}},
            oddish={left={img="1507b3380a3"},right={img="1507b339370"}},
            ledian={left={img="1507b5fa5ea"},right={img="1507b5fbc5e"}},
            cinccino={left={img="1507bf4d7ab"},right={img="1507bf4f4d6"}},
            houndour={left={img="1507b9b5019"},right={img="1507b9b691e"}},
            horsea={left={img="1507b519ce4"},right={img="1507b51b0cd"}},
            bisharp={left={img="1507be2954e"},right={img="1507be2b245"}},
            kyurem={left={img="1507be73917"},right={img="1507be75613"}},
            woobat={left={img="1507bdac571"},right={img="1507bdae0c8"}},
            vileplume={left={img="1507b33ccde"},right={img="1507b33df69"}},
            staraptor={left={img="1507bbffb5d"},right={img="1507bc01db1"}},
            combee={left={img="1507bc38d51"},right={img="1507bc3a72f"}},
            liepard={left={img="1507bd71201"},right={img="1507bd733ef"}},
            ambipom={left={img="1507bc552e7"},right={img="1507bc56c6c"}},
            machoke={left={img="1507b45ad23"},right={img="1507b4a8662"}},
            barboach={left={img="1507b71754a"},right={img="1507b718c47"}},
            spinda={left={img="1507b6f49fd"},right={img="1507b6f6218"}},
            vanillish={left={img="1507bf70f94"},right={img="1507bf72ebe"}},
            gigalith={left={img="1507bda8d61"},right={img="1507bdaa931"}},
            pignite={left={img="1507bd4bd70"},right={img="1507bd4d8ac"}},
            graveler={left={img="1507b4b825e"},right={img="1507b4b9921"}},
            pansage={left={img="1507bd74f99"},right={img="1507bd76b75"}},
            feebas={left={img="1507b9478ac"},right={img="1507b949059"}},
            poliwrath={left={img="1507b44fb79"},right={img="1507b450d43"}},
            petilil={left={img="1507bef4c2f"},right={img="1507bef6981"}},
            sealeo={left={img="1507ba1a686"},right={img="1507ba1c6bf"}},
            solosis={left={img="1507bf5c8ed"},right={img="1507bf5e7a5"}},
            snover={left={img="1507bcc69a3"},right={img="1507bcc84c0"}},
            electivire={left={img="1507bcddd0b"},right={img="1507bcdf943"}},
            magnemite={left={img="1507b4c69df"},right={img="1507b4c7d42"}},
            reuniclus={left={img="1507bf64254"},right={img="1507bf660db"}},
            camerupt={left={img="1507b6e799b"},right={img="1507b6e9142"}},
            azelf={left={img="1507bd11591"},right={img="1507bd1319a"}},
            darkrai={left={img="1507bd30517"},right={img="1507bd32052"}},
            banette={left={img="1507b956b54"},right={img="1507b9583eb"}},
            heatran={left={img="1507bd1bb21"},right={img="1507bd1d6b4"}},
            machop={left={img="1507b458774"},right={img="1507b4599e7"}},
            mamoswine={left={img="1507bcf5355"},right={img="1507bcf6e30"}},
            haunter={left={img="1507b4e2e49"},right={img="1507b4e40e7"}},
            baltoy={left={img="150c9a44ee9"},right={img="1507b726119"}},
            espeon={left={img="1507b8268e0"},right={img="1507b828066"}},
            bibarel={left={img="1507bc072ce"},right={img="1507bc08d82"}},
            mareep={left={img="1507b61e22f"},right={img="1507b61f784"}},
            aipom={left={img="1507b81504e"},right={y=-55,img="1507b816c34"}},
            lileep={left={img="1507b93ba5d"},right={img="1507b93d210"}},
            registeel={left={y=-60,img="1507ba30445"},right={y=-60,img="1507ba31ca6"}},
            jigglypuff={left={img="1507b32ef94"},right={img="1507b33015d"}},
            persian={left={img="1507b43ad35"},right={img="1507b43c179"}},
            cascoon={left={img="1507b8b21b3"},right={img="1507b8b3981"}},
            nincada={left={img="1507b8f6257"},right={img="1507b8f7ad1"}},
            blitzle={left={img="1507bd9ae65"},right={img="1507bd9c9e0"}},
            jellicent={left={img="1507bfff8d6"},right={img="1507c001726"}},
            marill={left={img="150c9a32436"},right={img="1507b802f49"}},
            golbat={left={img="1507b335c14"},right={img="1507b336e87"}},
            smeargle={left={img="1507b9caf06"},right={img="1507b9cc860"}},
            gallade={left={img="1507beb6a97"},right={img="1507bcfb590"}},
            octillery={left={img="1507b9a8752"},right={img="1507b9aa011"}},
            oshawott={left={img="1507bd52ab4"},right={img="1507bd541f7"}},
            charmeleon={left={img="1507b2e27c9"},right={img="1507b2e3922"}},
            squirtle={left={img="1507b2e6d73"},right={img="1507b2e7ee0"}},
            skiploom={left={img="1507b80f200"},right={img="1507b810999"}},
            yamask={left={img="1507bf2422c"},right={img="1507bf25fd2"}},
            articuno={left={y=-65,img="1507b56090d"},right={y=-65,img="1507b561ca7"}},
            magikarp={left={img="1507b53aded"},right={img="1507b53c440"}},
            groudon={left={img="1507ba3b4d2"},right={img="1507ba3cdfd"}},
            carnivine={left={img="1507bcb93e9"},right={img="1507bcbae5b"}},
            munna={left={img="1507bd899a8"},right={img="1507bd8b546"}},
            electabuzz={left={img="1507b530f63"},right={img="1507b53236f"}},
            nidorino={left={img="1507b321979"},right={img="1507b322b01"}},
            onix={left={y=-65,img="1507b4e7901"},right={y=-65,img="1507b4e8ba4"}},
            breloom={left={img="1507b8e81be"},right={img="1507b8e9b35"}},
            ghastly={left={img="1507b4e083a"},right={img="1507b4e1bb5"}},
            hippopotas={left={img="1507bca57e5"},right={img="1507bca6fa2"}},
            cresselia={left={img="1507bd25eb1"},right={img="1507bd279d1"}},
            croconaw={left={img="1507b5e725b"},right={img="1507b5e87b0"}},
            litwick={left={img="1507c031f05"},right={img="1507c033c11"}},
            spearow={left={y=-53,img="1507b30579d"},right={y=-53,img="1507b3068d2"}},
            crobat={left={img="1507b602b40"},right={img="1507b604120"}},
            typhlosion={left={img="1507b5e1a42"},right={img="1507b5e300f"}},
            abra={left={img="1507b451ed3"},right={img="1507b45307d"}},
            chimecho={left={img="1507ba08166"},right={img="1507ba09987"}},
            bronzor={left={img="1507bc7a1cf"},right={img="1507bc7bcde"}},
            ducklett={left={img="1507bf67fc6"},right={img="1507bff9d0f"}},
            krabby={left={img="1507b4eeb7a"},right={img="1507b4efe8b"}},
            seaking={left={img="1507b52130a"},right={img="1507b522692"}},
            ditto={left={img="1507b54367d"},right={img="1507b544c50"},action=function(player,x,y)
                local closest
                for n,p in pairs(players) do
                    if p.sprite and pythag(x,y,tfm.get.room.playerList[n].x,tfm.get.room.playerList[n].y,20) then
                        local d=distance(tfm.get.room.playerList[n].x,tfm.get.room.playerList[n].y,x,y)
                        if not closest or d<closest.distance then
                            closest={category=p.sprite.category,id=p.sprite.id,distance=d}
                        end
                    end
                end
                if closest then
                    _S.images.selectImage(player,closest.id,closest.category)
                end
            end},
            cubchoo={left={img="1507cbc4269"},right={img="1507c0472d1"}},
            grumpig={left={img="1507b6f1669"},right={img="1507b6f2f59"}},
            dragonite={left={y=-65,img="1507b56adb3"},right={y=-65,img="1507b56c17e"}},
            doduo={left={img="1507b4cd653"},right={img="1507b4ce96d"}},
            tirtouga={left={img="1507bf2b9d1"},right={img="1507bf2d81d"}},
            farfetchd={left={img="1507b4caf7f"},right={img="1507b4cc204"}},
            staravia={left={img="1507bbfbb99"},right={img="1507bbfdc21"}},
            seel={left={img="1507b4d2701"},right={img="1507b4d3990"}},
            celebi={left={img="1507b881637"},right={img="1507b882d35"}},
            vigoroth={left={img="1507b8ee798"},right={img="1507b8efffc"}},
            shuckle={left={img="150cac53a9f"},right={img="1507b98949e"}},
            treecko={left={img="1507b884009"},right={img="1507b9f6dca"}},
            growlithe={left={img="1507b446807"},right={img="1507b447bd5"}},
            genesect={left={img="1507c06e86a"},right={img="1507c0708a3"}},
            chandelure={left={img="1507c03906e"},right={img="1507c03ac39"}},
            landorus={left={img="1507be6ff0c"},right={img="1507be71b77"}},
            tyranitar={left={img="1507b9f3a5b"},right={img="1507b9f544c"}},
            zekrom={left={img="1507be6c4df"},right={img="1507be6e11e"}},
            venomoth={left={img="1507b431ae8"},right={img="1507b432c36"}},
            reshiram={left={img="1507be6898b"},right={img="1507be6a7fc"}},
            piloswine={left={img="1507b99f0a9"},right={img="1507b9a0a5d"}},
            poochyena={left={img="1507ba7ea31"},right={img="1507b9fbfeb"}},
            lanturn={left={img="1507b60834d"},right={img="1507b609960"}},
            togepi={left={img="1507b6132e2"},right={img="1507b6148a6"}},
            cobalion={left={img="1507be566c9"},right={img="1507be5840c"}},
            aerodactyl={left={y=-60,img="1507b55b9ac"},right={y=-60,img="1507b55cdcc"}},
            karrablast={left={img="1507bf84992"},right={img="1507bf8684a"}},
            maractus={left={img="1507bf12097"},right={img="1507bff3eb5"}},
            glalie={left={img="1507ba14321"},right={img="1507ba15d02"}},
            azumarill={left={img="1507b98483d"},right={img="1507b804f4d"}},
            kecleon={left={img="1507b950d58"},right={img="1507b952588"}},
            volcarona={left={img="1507be52859"},right={img="1507be54923"}},
            ferrothorn={left={img="1507c012b53"},right={img="1507c014a9c"}},
            hydreigon={left={img="1507be4b53c"},right={img="1507be4d176"}},
            deino={left={img="1507be455f6"},right={img="1507c066d2b"}},
            diglett={left={y=-55,img="1507b433d39"},right={y=-55,img="1507b434f1f"}},
            ursaring={left={img="1507b993529"},right={img="1507b994dbd"}},
            archen={left={img="1507bf33321"},right={img="1507bf350fc"}},
            durant={left={img="1507be41e49"},right={img="1507be43afe"}},
            pineco={left={img="1507b83c9bf"},right={img="1507b83e236"}},
            heatmor={left={img="1507be3e610"},right={img="1507be4028d"}},
            mandibuzz={left={img="1507be3aabb"},right={img="1507be3c74a"}},
            cherrim={left={img="1507bc4b3be"},right={img="1507bc4cdac"}},
            moltres={left={y=-65,img="1507b565845"},right={y=-65,img="1507b566a1f"}},
            snubbull={left={img="1507b849d06"},right={img="1507b84b43a"}},
            vullaby={left={img="1507be373b9"},right={img="1507be38f06"}},
            braviary={left={img="1507be33ba6"},right={img="1507be35765"}},
            spinarak={left={img="1507b5fd229"},right={img="1507b5fe80c"}},
            spiritomb={left={img="1507bc8e427"},right={img="1507bc8fe6e"}},
            sawk={left={img="1507bed3f37"},right={img="1507bed5c0e"}},
            druddigon={left={img="1507c059ee0"},right={img="1507c05bb57"}},
            loudred={left={img="1507b902c99"},right={img="1507b904652"}},
            mienfoo={left={img="1507c052166"},right={img="1507c054102"}},
            hypno={left={img="1507b4ec591"},right={img="1507b4ed873"}},
            shaymin={left={img="1507bd33c0d"},right={img="1507bd35821"}},
            arceus={left={y=-60,img="1507bd3749f"},right={y=-60,img="1507bd3911b"}},
            beartic={left={img="1507c0489be"},right={img="1507bfae561"}},
            unown={left={img="1507b835fb5"},right={img="1507b83762a"}},
            pidgeot={left={img="1507b2ff1b9"},right={img="1507b30026f"}},
            haxorus={left={img="1507c043596"},right={img="1507c045126"}},
            excadrill={left={img="1507bdb4927"},right={img="1507bdb649a"}},
            beheeyem={left={img="1507c02e301"},right={img="1507c030114"}},
            fraxure={left={img="1507c03fdcf"},right={img="1507c0419d0"}},
            axew={left={img="1507c03c7f0"},right={img="1507c03e413"}},
            blastoise={left={img="1507b2eb27f"},right={img="1507b2ec390"}},
            medicham={left={img="1507b9309df"},right={img="1507b9322a6"}},
            yanmega={left={img="1507bce7cdf"},right={img="1507bce96f9"}},
            vaporeon={left={img="1507b5488de"},right={img="1507b549c81"}},
            magnezone={left={img="1507bcd0be8"},right={img="1507bcd2568"}},
            keldeo={left={img="150c9a64ae5"},right={img="1507c068d30"}},
            teddiursa={left={img="1507b9903c7"},right={img="1507b991c2a"}},
            jolteon={left={img="1507b54b0e0"},right={img="1507b54c481"}},
            lapment={left={img="1507c0358a1"},right={img="1507c03748b"}},
            krookodile={left={img="1507bf06f40"},right={img="1507bf08c0f"}},
            elgyem={left={img="1507c02bce1"},right={img="1507cbc1bf5"}},
            pupitar={left={img="1507b9f0798"},right={img="1507b9f20fc"}},
            pawniard={left={img="1507c06504d"},right={img="1507be24f7b"}},
            eelektrik={left={img="1507cbbc94c"},right={img="1507cbbf466"}},
            tynamo={left={img="1507c022240"},right={img="1507c0241f2"}},
            klinklang={left={img="1507c01e3d5"},right={img="1507c02048b"}},
            raichu={left={img="1507b31213d"},right={img="1507b313303"}},
            ferroseed={left={img="1507c00ed4c"},right={img="1507c010bf9"}},
            mismagius={left={img="1507bc63d04"},right={img="1507bc658cd"}},
            joltik={left={img="1507c0072ab"},right={img="1507c0091f3"}},
            bulbasaur={left={y=-45,img="1507b2d9a2d"},right={y=-45,img="1507b2dabdb"}},
            alomomola={left={img="1507c0035d4"},right={img="1507c00548c"}},
            frillish={left={img="150c9a7013c"},right={img="1507bffdaaa"}},
            foongus={left={img="1507bf8c310"},right={img="1507bf8e234"}},
            shuppet={left={img="1507b953e0d"},right={img="1507b9556f3"}},
            emolga={left={img="1507bf80be3"},right={img="1507bf82ae9"}},
            wingull={left={img="1507b8d062b"},right={img="1507b8d1e56"}},
            sandshrew={left={img="1507b314456"},right={img="1507b3155a3"}},
            nidoking={left={img="1507b323de0"},right={img="1507b324f78"}},
            geodude={left={img="1507b4b5d2d"},right={img="1507b4b6fe4"}},
            deerling={left={img="1507bf78e80"},right={img="1507bf7ad69"}},
            vanilluxe={left={img="1507bf74f0d"},right={img="1507bf76ec9"}},
            vanillite={left={img="1507bf6d07c"},right={img="1507bf6efdd"}},
            mawile={left={img="1507b921391"},right={img="1507b922bfc"}},
            duosion={left={img="1507bf605e9"},right={img="1507bf62424"}},
            mightyena={left={img="1507b89dcb4"},right={img="1507b89f4b0"}},
            gothitelle={left={img="1507bf58a77"},right={img="1507bf5aa42"}},
            gothorita={left={img="1507bf54e66"},right={img="1507bf56c79"}},
            starmie={left={img="1507b526118"},right={img="1507b527555"}},
            samurott={left={img="1507bd593ec"},right={img="1507bd5aea2"}},
            gothita={left={img="1507bf5129a"},right={img="1507bf5305b"}},
            mantine={left={img="1507b9aeb4b"},right={img="1507b9b047d"}},
            scraggy={left={img="1507bf18e68"},right={img="1507bf1acef"}},
            steelix={left={y=-55,img="1507b846ea8"},right={y=-55,img="1507b848650"}},
            minccino={left={img="1507bf49c7a"},right={img="1507bf4ba4c"}},
            boldore={left={img="1507bda561d"},right={img="1507bda721c"}},
            luxray={left={img="1507bc1748a"},right={img="1507bc19009"}},
            gurdurr={left={img="1507bebe089"},right={img="1507bebfe10"}},
            trubbish={left={img="1507bf3b131"},right={img="1507bf3cecd"}},
            victini={left={img="1507bd3acf8"},right={img="1507bd3c95e"}},
            wurmple={left={img="1507b8a85f1"},right={img="1507b8a9da4"}},
            mudkip={left={img="1507b8952d3"},right={img="1507b896a2a"}},
            bayleef={left={img="1507b5d6a2b"},right={img="1507b5d7fd3"}},
            gulpin={left={img="1507b6d2d2c"},right={img="1507b6d4549"}},
            dusclops={left={img="1507ba020d2"},right={img="1507ba038da"}},
            golduck={left={img="1507b43f9fc"},right={img="1507b440da0"}},
            huntail={left={img="1507b743e1e"},right={img="1507b74557a"}},
            scrafty={left={img="1507bf1ca98"},right={img="1507bf1e84e"}},
            palkia={left={y=-60,img="1507bd184d2"},right={y=-60,img="1507bd19f67"}},
            basculin={left={img="1507befc09e"},right={img="1507befdbd3"}},
            swadloon={left={img="1507bedb1ce"},right={img="1507bedcb68"}},
            herdier={left={img="1507bd66e2f"},right={img="1507bd68924"}},
            hooh={left={img="1507b87e7f6"},right={img="1507b87ff60"}},
            scolipede={left={img="1507bee9b88"},right={img="1507beeb8cd"}},
            omanyte={left={img="1507b552854"},right={img="1507b553bc4"}},
            venipede={left={img="1507bee25b4"},right={img="1507bee428c"}},
            tropius={left={img="1507ba0511e"},right={img="1507ba06937"}},
            surskit={left={img="1507b8dfdad"},right={img="1507b8e163e"}},
            swellow={left={img="1507b8cd466"},right={img="1507b8cec87"}},
            lilligant={left={img="1507bef862f"},right={img="1507befa39f"}},
            sewaddle={left={img="1507bed78d4"},right={img="1507bed95c6"}},
            eelektross={left={img="1507c027f46"},right={img="1507c029e1b"}},
            foretress={left={img="1507b83f946"},right={img="1507b84125e"}},
            throh={left={img="1507bed0506"},right={img="1507bed223e"}},
            parasect={left={img="1507b341777"},right={img="1507b342a20"}},
            palpitoad={left={img="1507bec9181"},right={img="1507becaf4a"}},
            latias={left={img="1507ba33507"},right={img="1507bb6b94e"}},
            phione={left={img="1507bd295fb"},right={img="1507bd2b175"}},
            conkeldurr={left={img="1507bec1bd2"},right={img="1507bec39b4"}},
            timburr={left={img="1507bdbb5ff"},right={img="1507bdbd1b5"}},
            purrloin={left={img="1507bd6db6a"},right={img="1507bd6f637"}},
            zorua={left={img="1507bf42637"},right={img="1507bf442be"}},
            unfezant={left={img="1507bd97798"},right={img="1507bd99336"}},
            tranquill={left={img="1507bd940fc"},right={img="1507bd95bf5"}},
            panpour={left={img="1507bd82b1b"},right={img="1507bd84743"}},
            shellder={left={img="1507b4dbd03"},right={img="1507b4dcf88"}},
            pikachu={left={img="1507b30fe8f"},right={img="1507b310fa8"},y=-54},
            pansear={left={img="1507bd7bdb1"},right={img="1507bd7d93c"}},
            swoobat={left={img="1507bdafcaa"},right={img="1507bdb18bd"}},
            stoutland={left={img="1507bd6a4b1"},right={img="1507bd6c018"}},
            clampearl={left={img="1507ba20f8a"},right={img="1507b7424c9"}},
            lillipup={left={img="1507bd6367e"},right={img="1507bd651f3"}},
            dewott={left={img="1507bd55cfa"},right={img="1507bd5787a"}},
            emboar={left={img="1507bd4f424"},right={img="1507bd50f5d"}},
            tepig={left={img="1507bd48738"},right={img="1507bd4a247"}},
            sigilyph={left={img="1507bf2063f"},right={img="1507bf22415"}},
            archeops={left={img="1507bf36f4f"},right={img="1507bf39507"}},
            gardevoir={left={img="1507b8dcc9f"},right={img="1507b8de559"}},
            shelmet={left={img="1507bfb4b90"},right={img="150c9a8c93e"}},
            claydol={left={img="1507b9385d4"},right={img="1507b939e70"}},
            politoed={left={img="1507b80954a"},right={img="1507b80acfb"}},
            tympole={left={img="1507bec589e"},right={img="1507bec76a2"}},
            kangaskhan={left={img="1507b58c2f9"},right={img="1507b518912"}},
            makuhita={left={img="1507b90a2d2"},right={img="1507b90bb6e"}},
            eevee={left={img="1507b5460c9"},right={img="1507b54756f"}},
            regigigas={left={img="1507bd1f1cb"},right={img="1507bd20be7"}},
            crustle={left={img="1507bf15363"},right={img="1507bf173ff"}},
            skorupi={left={img="1507bcabda2"},right={img="1507bcad818"}},
            scizor={left={img="1507b852bc7"},right={img="1507b854430"}},
            dunsparce={left={img="1507b8429dc"},right={img="1507b8440e5"}},
            raikou={left={y=-55,img="1507b9e3cb6"},right={y=-55,img="1507b9e55e4"}},
            dustox={left={img="1507b8b50e9"},right={img="1507b8b6823"}},
            caterpie={left={img="1507b2ed4c8"},right={img="1507b2ee5e4"}},
            froslass={left={img="1507bd03c71"},right={img="1507bd05738"}},
            dusknoir={left={img="1507bd00777"},right={img="1507bd02231"}},
            dialga={left={y=-65,img="1507bd14e17"},right={y=-65,img="1507bd16970"}},
            gliscor={left={img="1507bcf1d04"},right={img="1507bcf3859"}},
            quilava={left={img="1507b5dee51"},right={img="1507b5e047b"}},
            togekiss={left={img="1507bce4a73"},right={img="1507bce6230"}},
            trapinch={left={img="1507b6f7ab9"},right={img="1507b6f9306"}},
            volbeat={left={img="1507b6c9af6"},right={img="1507b6cb163"}},
            rhyperior={left={img="1507bcd7277"},right={img="1507bcd8d46"}},
            lombre={left={img="1507b8bb007"},right={img="1507b8bc267"}},
            masquerain={left={img="1507b8e2fca"},right={img="1507b9fd813"}},
            lickilicky={left={img="1507bcd3b5a"},right={img="1507bcd5581"}},
            weavile={left={img="1507bccd69d"},right={img="1507bccf1fb"}},
            slugma={left={img="1507b99607b"},right={img="1507b99732a"}},
            abomasnow={left={img="1507bcca04a"},right={img="1507bccbafe"}},
            mantyke={left={img="1507bcc3447"},right={img="1507bcc4eb1"}},
            shinx={left={img="1507bc105ca"},right={img="1507bc12107"}},
            stantler={left={img="1507b9c7cb7"},right={img="1507b9c95f0"}},
            finneon={left={img="1507bcbc906"},right={img="1507bcbe383"}},
            drapion={left={img="1507bcaf32c"},right={img="1507bcb0da4"}},
            snivy={left={img="1507bd3e516"},right={img="1507bd4003a"}},
            chingling={left={img="1507bc7001f"},right={img="1507bc71a98"}},
            rufflet={left={img="1507be30444"},right={img="1507be31fdf"}},
            chatot={left={img="1507bc8b074"},right={img="1507bc8ca0a"}},
            skuntank={left={img="1507bc76ae2"},right={img="1507bc78540"}},
            purugly={left={img="1507bc6cb47"},right={img="1507bc6e55d"}},
            drifblim={left={img="1507bc59b8b"},right={img="1507bc5b6ec"}},
            buizel={left={img="1507bc42ab2"},right={img="1507bc444aa"}},
            sentret={left={img="1507b5eca06"},right={img="1507b5edfca"}},
            tauros={left={img="1507b538642"},right={img="1507b539a2a"}},
            vespiquen={left={img="1507bc3c1b9"},right={img="1507bc3db47"}},
            rampardos={left={img="1507bc2558b"},right={img="1507bc26f49"}},
            dragonair={left={img="1507b5910e8"},right={img="1507b569985"}},
            wormadam={left={img="1507bc326c2"},right={img="1507bc3401a"}},
            bastiodon={left={img="1507bc2bd9e"},right={img="1507bc2d775"}},
            shieldon={left={img="1507bc289d1"},right={img="1507bc2a386"}},
            mothim={left={img="1507bc35a2e"},right={img="1507bc373b4"}},
            cranidos={left={img="1507bc22054"},right={img="1507bc23a4a"}},
            victreebel={left={img="1507b4ae549"},right={img="1507b4af865"}},
            luxio={left={img="1507bc13d22"},right={img="1507bc15880"}},
            beldum={left={img="150c9a5261a"},right={img="1507ba22776"}},
            magcargo={left={img="1507b998b7d"},right={img="1507b99a3e4"}},
            kricketot={left={img="1507bc0a878"},right={img="1507bc0c357"}},
            staryu={left={img="1507b523993"},right={img="1507b524cb5"}},
            swinub={left={img="1507b99bc73"},right={img="1507b99d860"}},
            bidoof={left={img="1507bc03bf4"},right={img="1507bc056d0"}},
            piplup={left={y=-45,img="1507bbecd03"},right={y=-45,img="1507bbeea65"}},
            drowzee={left={img="1507b4e9f4a"},right={img="1507b4eb2b1"}},
            infernape={left={img="1507bbe8fe3"},right={img="1507bbeae51"}},
            chimchar={left={img="1507bbe13ed"},right={img="1507bbe3522"}},
            cleffa={left={img="1507b60db5d"},right={img="1507b60f1a0"}},
            turtwig={left={img="1507bbd6165"},right={img="1507bbd7ef2"}},
            corsola={left={img="1507b9a2439"},right={img="1507b9a3ce8"}},
            jirachi={left={img="1507ba81f55"},right={img="1507ba83b68"}},
            kyogre={left={img="1507ba38323"},right={img="1507ba39c3d"}},
            lunatone={left={img="1507b7115f4"},right={img="1507b712e1e"}},
            latios={left={img="1507ba34dec"},right={img="1507ba36a52"}},
            ninetales={left={img="1507b32cc3b"},right={img="1507b32ddcd"}},
            pinsir={left={img="1507b535e9a"},right={img="1507b537234"}},
            pichu={left={img="1507b60afa4"},right={img="1507b60c57b"}},
            regice={left={y=-60,img="1507ba2d2ad"},right={y=-60,img="1507ba2ec0d"}},
            tentacool={left={img="1507b4b0b4b"},right={img="1507b4b243b"}},
            metagross={left={img="1507ba27133"},right={img="1507ba28988"}},
            arcanine={left={y=-60,img="1507b448e0e"},right={y=-60,img="1507b44a171"}},
            metang={left={img="1507ba24004"},right={img="1507ba25893"}},
            cacnea={left={img="1507b700deb"},right={img="1507b70274d"}},
            remoraid={left={img="1507b9a567a"},right={img="1507b9a6e8e"}},
            salamence={left={img="1507b7557db"},right={img="1507b756f95"}},
            luvdisc={left={img="1507b74cae6"},right={img="1507b74e24c"}},
            garchomp={left={img="1507bc98358"},right={img="1507bc99e02"}},
            wynaut={left={img="1507ba0e1c8"},right={img="1507ba0f9e0"}},
            absol={left={img="1507ba0b18b"},right={img="1507ba0c997"}},
            leavanny={left={img="1507bede946"},right={img="1507bee087b"}},
            cofagrigus={left={img="1507bf27df0"},right={img="1507bf29bdc"}},
            duskull={left={img="1507b959d0e"},right={img="1507ba008a5"}},
            escavalier={left={img="1507bf888c9"},right={img="1507bf8a244"}},
            castform={left={img="1507b94dace"},right={img="1507b94f4ff"}},
            weezing={left={img="1507b50c691"},right={img="1507b50d982"}},
            gyarados={left={img="1507b53daab"},right={img="1507b53f4b8"}},
            cubone={left={img="1507b4fd7b6"},right={img="1507b4febe5"}},
            sunflora={left={img="1507b81aeed"},right={img="1507b81c72a"}},
            hariyama={left={img="1507b90d41c"},right={img="1507b90edf8"}},
            lapras={left={y=-57,img="1507b540b04"},right={y=-57,img="1507b54212b"}},
            altaria={left={img="1507b7084d4"},right={img="1507b709bfd"}},
            flygon={left={img="1507b6fdc34"},right={img="1507b6ff4d0"}},
            sneasel={left={img="1507b98d28e"},right={img="1507b98eaa2"}},
            furret={left={y=-60,img="1507b5ef5a5"},right={y=-60,img="1507b5f0b63"}},
            vibrava={left={img="1507b6fab19"},right={img="1507b6fc45b"}},
            linoone={left={img="1507b8a564c"},right={img="1507b8a6e7d"}},
            wailord={left={y=-65,img="1507b6e1f9e"},right={y=-65,img="1507b6e35db"}},
            wailmer={left={img="1507b6df1e7"},right={img="1507b6e08c3"}},
            elekid={left={img="1507b9d73ba"},right={img="1507b9d8cf6"}},
            roselia={left={img="1507b6cf990"},right={img="1507b6d1400"}},
            combusken={left={img="1507b88daf3"},right={img="1507b88f203"}},
            illumise={left={img="1507b6cca61"},right={img="1507b6ce134"}},
            magmortar={left={img="1507bce149d"},right={img="1507bce2f42"}},
            houndoom={left={img="1507b9b818f"},right={img="1507b9b99df"}},
            minun={left={img="1507b6c60d0"},right={img="1507b6c78a7"}},
            milktank={left={img="1507b9dd8bf"},right={img="1507b9df1b6"}},
            pachirisu={left={img="1507bc3f5dd"},right={img="1507bc41075"}},
            pelipper={left={img="1507b8d3730"},right={img="1507b8d4fe6"}},
            lairon={left={img="1507b92741e"},right={img="1507b928c65"}},
            vulpix={left={img="1507b32a6ad"},right={img="1507b32b9a3"}},
            delcatty={left={img="1507b919a2f"},right={img="1507b91d134"}},
            nosepass={left={img="1507b91371f"},right={img="1507b914fb3"}},
            anorith={left={img="1507b941abc"},right={img="1507b94334c"}},
            exploud={left={img="1507b905f27"},right={img="1507b9088ec"}},
            kirlia={left={img="1507b8d9b75"},right={img="1507b8db4b6"}},
            lotad={left={img="1507b8b7fed"},right={img="1507b8b97f3"}},
            hitmontop={left={img="1507b9d13a7"},right={img="1507b9d2ced"}},
            terrakion={left={img="1507be5a13c"},right={img="1507be5be90"}},
            carracosta={left={img="1507bf2f6ec"},right={img="1507bf31505"}},
            blaziken={left={img="1507b890909"},right={img="1507b892099"}},
            sceptile={left={y=-58,img="1507b88921e"},right={y=-58,img="1507b88a971"}},
            whimsicott={left={img="1507bef10ab"},right={img="1507bef2eab"}},
            larvitar={left={img="1507b9ed43a"},right={img="1507b9eee59"}},
            entei={left={y=-55,img="1507b9e6f18"},right={y=-55,img="1507b9e8894"}},
            kabuto={left={img="1507b58d6e8"},right={img="1507b557e21"}},
            rotom={left={img="1507bd07289"},right={img="1507bd08de4"}},
            plusle={left={img="1507b6c3396"},right={img="1507b6c4a94"}},
            tangela={left={img="1507b51568d"},right={img="1507b5169a5"}},
            chansey={left={img="1507b512ffc"},right={img="1507b51431e"}},
            slowpoke={left={img="1507b4c1c75"},right={img="1507b4c2f91"}},
            zigzagoon={left={img="1507b8a0c25"},right={img="1507b8a3e39"}},
            kingdra={left={img="1507b9bb220"},right={img="1507b9bcc3f"}},
            delibird={left={img="1507b9ab9b3"},right={img="1507b9ad244"}},
            deoxys={left={img="1507ba41b28"},right={img="1507ba433d2"}},
            flareon={left={img="1507b54d8c8"},right={img="1507b54ecc7"}},
            probopass={left={img="1507bcfd079"},right={img="1507bcfeb71"}},
            umbreon={left={img="1507b82972c"},right={img="1507b82ae83"}},
            cloyster={left={img="1507b4de221"},right={img="1507b4df561"}},
            quagsire={left={img="1507b823ab4"},right={img="1507b8251ad"}},
            yanma={left={img="1507b81de9f"},right={img="1507b81f502"}},
            grotle={left={img="1507bbd9c8a"},right={img="1507bbdba57"}},
            ariados={left={img="1507b5ffec6"},right={img="1507b601531"}},
            weepinbell={left={img="1507b4ac056"},right={img="1507b4ad299"}},
            chikorita={left={img="1507b5d3bd3"},right={img="1507b5d530f"}},
            mew={left={img="1507b593a84"},right={img="1507b56f8e6"}},
            primeape={left={img="1507b444331"},right={img="1507b44562f"}},
            porygon={left={img="1507b5500be"},right={img="1507b5514dd"}},
            crawdaunt={left={img="1507b721cfa"},right={img="1507b72341a"}},
            aggron={left={img="1507b92a559"},right={img="1507b92bdf2"}},
            mrmime={left={img="1507b528af4"},right={img="1507b52ac5c"}},
            hitmonchan={left={img="1507b504f0b"},right={img="1507b50632a"}},
            marowak={left={img="1507b4ffee4"},right={img="1507b501427"}},
            milotic={left={y=-65,img="1507b94a8d9"},right={y=-65,img="1507b94c1bb"}},
            simisear={left={img="1507bd7f4c8"},right={img="1507bd80fbb"}},
            regirock={left={y=-60,img="1507ba2a1e1"},right={y=-60,img="1507ba2ba3d"}},
            beedrill={left={img="1507b2f8b00"},right={img="1507b2f9bde"}},
            roserade={left={img="1507bc1ebdf"},right={img="1507bc205f4"}},
            poliwag={left={img="1507b44b520"},right={img="1507b44c770"}},
            mankey={left={img="1507b441f7f"},right={img="1507b44320b"}},
            venonat={left={img="1507b42f797"},right={img="1507b4308bd"}},
            paras={left={img="1507b33f271"},right={img="1507b340514"}},
        }
    },
    callbacks={
        keyboard={
            [KEYS.LEFT]=function(player,down,x,y)
                if down then
                    if player.sprite and player.sprite.facingRight and _S.images.sprites[player.sprite.category][player.sprite.id].left then
                        _S.images.showImage(player)
                    end
                end
            end,
            [KEYS.RIGHT]=function(player,down,x,y)
                if down then
                    if player.sprite and not player.sprite.facingRight and _S.images.sprites[player.sprite.category][player.sprite.id].right then
                        _S.images.showImage(player)
                    end
                end
            end,
            [KEYS.E]=function(player,down,x,y)
                if down and player.sprite then
                    local fnc=_S.images.sprites[player.sprite.category][player.sprite.id].action
                    if fnc then fnc(player,x,y) end
                end
            end
        },
        chatCommand={
            img={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,sprite,...)
                    sprite=sprite and sprite:lower()
                    local arg={...}
                    executeCommand(player, function(targetName)
                        local target = targetName and players[targetName] or player
                        if sprite and sprite=="remove" then
                            _S.images.removeImage(target)
                        else
                            _S.images.selectImage(target,sprite)
                        end
                    end, arg)
                end
            },
            prop={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,prop,...)
                    if tonumber(prop) and _S.images.sprites.props[tonumber(prop)] then
                        local arg={...}
                        executeCommand(player, function(targetName)
                            local target = targetName and players[targetName] or player
                            _S.images.selectImage(target,tonumber(prop),"props")
                        end, arg)
                    end
                end
            },
            poke={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,pokemon,...)
                    if _S.images.sprites.pokemon[pokemon] then
                        local arg={...}
                        executeCommand(player, function(targetName)
                            local target = targetName and players[targetName] or player
                            _S.images.selectImage(target,pokemon,"pokemon")
                        end, arg)
                    end
                end
            }
        },
        newGame=function()
            for name in pairs(tfm.get.room.playerList) do
                local player=players[name]
                if player.sprite then
                    if player.activeSegments.prophunt then
                        player.sprite=nil
                    else
                        _S.images.showImage(player)
                    end
                end
            end
        end,
        playerRespawn=function(player)
            if player.sprite then
                _S.images.showImage(player)
            end
        end,
        playerGetCheese=function(player)
            if player.sprite then
                _S.images.showImage(player)
            end
        end
    },
    showImage=function(player)
        if player.sprite then
            for k,v in pairs({"img","cheese","forecheese"}) do
                if player.sprite[v] then
                    tfm.exec.removeImage(player.sprite[v])
                    player.sprite[v]=nil
                end
            end
        end
        local direction=player.facingRight and "right" or "left"
        local cheese,forecheese
        if tfm.get.room.playerList[player.name].hasCheese then
            local spriteIndex=_S.images.sprites[player.sprite.category][player.sprite.id]
            if spriteIndex.cheese then cheese=_S.images.sprites[spriteIndex.cheese[1]][spriteIndex.cheese[2]][direction] or _S.images.sprites[spriteIndex.cheese[1]][spriteIndex.cheese[2]] end
            if spriteIndex.forecheese then forecheese=_S.images.sprites[spriteIndex.forecheese[1]][spriteIndex.forecheese[2]][direction] or _S.images.sprites[spriteIndex.forecheese[1]][spriteIndex.forecheese[2]] end
        end
        local directory=_S.images.sprites[player.sprite.category][player.sprite.id][direction] or _S.images.sprites[player.sprite.category][player.sprite.id]
        local dirroot=_S.images.sprites[player.sprite.category][player.sprite.id]
        player.sprite.facingRight=direction=="right" and true
        if cheese then
            player.sprite.cheese=tfm.exec.addImage(cheese.img..".png","$"..player.name,cheese.x or -50,cheese.y or -50)
        end
        player.sprite.img=tfm.exec.addImage(directory.img..".png",(directory.l or "%")..player.name,directory.x or dirroot.x or -50,directory.y or dirroot.y or -50)
        if forecheese then
            player.sprite.forecheese=tfm.exec.addImage(forecheese.img..".png","$"..player.name,forecheese.x or -50,forecheese.y or -50)
        end
    end,
    selectImage=function(player,id,category)
        if category and id then
            if _S.images.sprites[category][id] and (_S.images.sprites[category][id].img or _S.images.sprites[category][id].right) then
                if player.sprite then
                    _S.images.removeImage(player)
                end
                player.sprite={
                    category=category,
                    id=id
                }
                _S.images.showImage(player)
            end
        elseif id then
            for key,value in pairs(_S.images.sprites) do
                if value[id] and (value[id].img or value[id].right) then
                    if player.sprite then
                        _S.images.removeImage(player)
                    end
                    player.sprite={
                        category=key,
                        id=id
                    }
                    _S.images.showImage(player)
                    break
                end
            end
        else
            local tbl={}
            for key in pairs(_S.images.sprites.main) do
                table.insert(tbl,key)
            end
            if player.sprite then
                _S.images.removeImage(player)
            end
            player.sprite={
                category="main",
                id=tbl[math.random(#tbl)]
            }
            _S.images.showImage(player)
        end
    end,
    removeImage=function(player)
        if player.sprite then
            for k,v in ipairs({"img","cheese","forecheese"}) do
                if player.sprite[v] then
                    tfm.exec.removeImage(player.sprite[v])
                end
            end
            player.sprite=nil
        end
    end,
}


--[[ src/segments/inspect.lua ]]--

_S.inspect = {
    defaultPlayer=function(player)
        player.activeSegments.inspect=true
    end,
    callbacks={
        newGame=function()
            ui.removeTextArea(-20)
        end,
        mouse={
            pr=1,
            fnc=function(player,x,y)
                if player.shift and not player.activeSegments.draw then
                    local theta,c,s,cx,cy
                    for i=#map.grounds,1,-1 do
                        local ground=map.grounds[i]
                        theta=math.rad(ground.rotation or 0)
                        c,s=math.cos(-theta),math.sin(-theta)
                        cx=ground.x+c*(x-ground.x)-s*(y-ground.y)
                        cy=ground.y+s*(x-ground.x)+c*(y-ground.y)
                        if (ground.type==13 and pythag(x,y,ground.x,ground.y,ground.length/2)) or (math.abs(cx-ground.x)<ground.length/2 and math.abs(cy-ground.y)<ground.height/2) then
                            local str=""
                            local properties={"type","id","x","y","height","length","friction","restitution","rotation","dynamic","mass","color"}
                            for _,property in ipairs(properties) do
                                if ground[property] then
                                    str=str.."<N>"..translate(property,player.lang)..": <VP>"..(
                                        (property=="color" and "<font color='#"..ground[property].."'>#"..(ground[property]).."</font>") or
                                        (property=="dynamic" and (ground["dynamic"]==0 and "False" or "True")) or
                                        (property=="type" and translate("groundList",player.lang)[ground[property]]) or
                                        (ground[property])
                                    ).."\n"
                                end
                            end
                            local w,h=map.length or 800,map.height or 400
                            ui.addTextArea(-20,"<font size='12'>"..str.."</font>",player.name,(x+150<=w and x) or (x<0 and 0) or (w-150),(y+180<=h and y>20 and y) or (y<20 and 25) or (h-180),nil,nil,nil,nil,0.5,false)
                            return
                        end
                    end
                end
                ui.removeTextArea(-20,player.name)
            end
        },
    }
}


--[[ src/segments/insta.lua ]]--

_S.insta = {
    callbacks={
        summoningStart=function(player,type,x,y,ang) 
            tfm.exec.addShamanObject(type,x,y,ang)
        end
    }
}



--[[ src/segments/lightning.lua ]]--

_S.lightning = {
    ids={0,1,9},
    move=3,
    ms=3/20,
    ma=(3/20)/1200,
    players={},
    defaultPlayer=function(player)
        _S.lightning.players[player] = os.time()
    end,
    callbacks={
        mouse={
            pr=-21,
            fnc=function(player,x,y)
                if player.ctrl and not player.shift then
                    if _S.lightning.players[player] < os.time()-1000 then
                        _S.lightning.players[player] = os.time()
                        local p=tfm.get.room.playerList[player.name]
                        _S.lightning.drawLightining(p.x,p.y,x,y,_S.lightning.ids[math.random(#_S.lightning.ids)])
                    end
                end
            end
        }
    },
    drawLine=function(x1,y1,x2,y2,spaces,id)
        id = id or 9
        spaces = spaces or 3
        local distance = _S.lightning.getDistance(x1,y1,x2,y2)
        local numOfParticles = math.floor(distance/spaces)
        local angle = _S.lightning.getAngle(x1,y1,x2,y2)
        for i=0,numOfParticles do
            local dotX = x1+math.cos(angle)*(i*spaces)
            local dotY = y1+math.sin(angle)*(i*spaces)
            tfm.exec.displayParticle(id,dotX,dotY,math.random()*_S.lightning.ms-_S.lightning.ms/2,math.random()*_S.lightning.ms-_S.lightning.ms/2,math.random()*_S.lightning.ma-_S.lightning.ma/2,math.random()*_S.lightning.ma-_S.lightning.ma/2)
        end
    end,
    getDistance=function(x1,y1,x2,y2)
        return math.sqrt(math.abs(x1-x2)^2+math.abs(y1-y2)^2)
    end,
    getAngle=function(x1,y1,x2,y2)
        return math.atan2(y2-y1,x2-x1)
    end,
    radToDeg=function(i)
        i = i*180/math.pi
        i = i<0 and i+360 or i
        return i
    end,
    degToRad=function(i)
        return i*math.pi/180
    end,
    drawLightining=function(x1,y1,x2,y2,id)
        local ang = _S.lightning.getAngle(x1,y1,x2,y2)
        local dis = _S.lightning.getDistance(x1,y1,x2,y2)
        local rd = function() return math.random()*25+25 end
        local ra = function() return math.pi/(math.random()*120+30) end
        local wave = {}
        local addWave = function(k,xx,yy) wave[k] = {x=xx,y=yy} end
        addWave(0,x1,y1)
        local td = 0
        local randomDistance = rd()
        local randomAngle = ra()*((dis-td)/100)
        local zigZag = math.random()<0.5 and 1 or -1
        local ca = ang + randomAngle*zigZag
        while randomDistance<dis-td do
            td = td + randomDistance
            local tx = x1+math.cos(ca)*td
            local ty = y1+math.sin(ca)*td
            addWave(#wave+1,tx,ty)
            randomDistance = rd()
            randomAngle = ra()*((dis-td)/100)
            zigZag = zigZag * -1
            ca = ang + randomAngle*zigZag
        end
        addWave(#wave+1,x2,y2)
        for i=0,#wave-1 do
            local cw = wave[i]
            local nw = wave[i+1]
            _S.lightning.drawLine(cw.x,cw.y,nw.x,nw.y,3,id)
        end
    end
}


--[[ src/segments/map-image.lua ]]--

_S.mapImage = {
    disabled=true,
    images={},
    layers={fg = "!", bg="?", gr="_"},
    callbacks={
        newGame=function()
            local layers = getValueFromXML(tfm.get.room.xmlMapInfo.xml, "IL") or false
            if layers then
                -- imgUrl,imgLayer,imgX,imgY;img...
                layers = string.split(layers, ";")
                _S.mapImage.images = {}
                for index, layerData in pairs(layers) do
                    table.insert(_S.mapImage.images, layerData)
                    layerData = string.split(layerData, ",")
                    local layerType = (_S.mapImage.layers[layerData[2]:sub(0,2)] or "")..(layerData[2]:sub(3) or "1")
                    tfm.exec.addImage(layerData[1] or "", layerType, layerData[3] or 0, layerData[4] or 0, nil)
                end
            end
        end,
        newPlayer=function(player)
            for index, layerData in pairs(_S.mapImage.images) do
                layerData = string.split(layerData, ",")
                local layerType = (_S.mapImage.layers[layerData[2]:sub(0,2)] or "")..(layerData[2]:sub(3) or "")
                tfm.exec.addImage(layerData[1] or "", layerType, layerData[3] or 0, layerData[4] or 0, player.name)
            end
        end,
    },
}



--[[ src/segments/meep.lua ]]--

_S.meep = {
    callbacks={
        newGame=function()
            for name,player in pairs(players) do
                if player.activeSegments.meep then
                    tfm.exec.giveMeep(name)
                end
            end
        end,
        keyboard={
            [KEYS.SPACE]=function(player,down,x,y)
                if player.meepTimer<os.time()-10100 and player.meepPower then -- time for the meep bar to restore
                    player.meepTimer=os.time()
                    for k,v in pairs(tfm.get.room.playerList) do
                        if player.name~=k then
                            local xdiff=tfm.get.room.playerList[k].x-x
                            local ydiff=tfm.get.room.playerList[k].y-(y+10)
                            local length=math.sqrt(xdiff^2+ydiff^2)
                            if length <= 90 then -- distance obviously
                                tfm.exec.movePlayer(k, 0, 0, false, (xdiff/(math.abs(xdiff)+math.abs(ydiff)))*player.meepPower, (ydiff/(math.abs(xdiff)+math.abs(ydiff)))*player.meepPower, false)
                            end -- the two 250 values determine the power (they must be the same)
                        end
                    end
                end
            end
        }
    }
}



--[[ src/segments/movecheese.lua ]]--

_S.movecheese = {
    disabled=true,
    cheesePositions={X={},Y={}},
    currentIndex=1,
    currentPos={-500, -500},
    loop=false,
    order=0,
    delay=1000,
    lastMove=os.time(),
    image=-1,
    --[[ TAGS:
        segments="movecheese" - enable the cheese movement
        CheeseX="x1,x2,x3" - horizontal coordinates for the cheese,
        CheeseY="y1,y2,y3" - vertical coordinates for the cheese
        FirstCoord="x,y" - sets the first coordinate for the cheese
        in both you can use:
            fixed - to make the cheese stay in the last location
            random - to make the coordinate be random
        Loop="0 or 1" - enables the move loop(0 false, 1 true)
        Delay="seconds" - the change delay, in seconds
        Order="type" - sets how the cheese should move:
            0 - following the xml order
            1 - when a player gets the cheese
            2 - random coordinate movement, given by the xml
            3 - when a player gets close to the cheese
    ]]--
    callbacks={
        newGame=function()
            local canLoop=getValueFromXML(tfm.get.room.xmlMapInfo.xml, "Loop") or 1
            local moveDelay=getValueFromXML(tfm.get.room.xmlMapInfo.xml, "Delay") or 1000
            local moveOrder=getValueFromXML(tfm.get.room.xmlMapInfo.xml, "Order") or 0
            local vX = getValueFromXML(tfm.get.room.xmlMapInfo.xml, "CheeseX") or "400"
            local vY= getValueFromXML(tfm.get.room.xmlMapInfo.xml, "CheeseY") or "200"
            local firstCoord = string.split(getValueFromXML(tfm.get.room.xmlMapInfo.xml, "FirstCoord") or "", ",")
            _S.movecheese.cheesePositions.X=string.split(vX, ",")
            _S.movecheese.cheesePositions.Y=string.split(vY, ",")
            _S.movecheese.loop=canLoop == 1
            _S.movecheese.order=tonumber(moveOrder) or 0
            _S.movecheese.delay=tonumber(moveDelay) or 1000
            _S.movecheese.lastMove = os.time()
            _S.movecheese.callbacks.moveCheese(table.unpack(firstCoord))
        end,
        eventLoop=function(time,remain)
            time = math.ceil(time/1000)
            local X = 0
            local Y = 0
            if time > 1 then
                    if _S.movecheese.currentIndex <= #_S.movecheese.cheesePositions.X or _S.movecheese.currentIndex <= #_S.movecheese.cheesePositions.Y then
                        if _S.movecheese.lastMove < os.time()-_S.movecheese.delay then
                            if _S.movecheese.order == 0 then
                                _S.movecheese.callbacks.moveCheese()
                            elseif _S.movecheese.order == 2 then
                                _S.movecheese.currentIndex = math.random(math.max(#_S.movecheese.cheesePositions.X, #_S.movecheese.cheesePositions.Y))
                                _S.movecheese.callbacks.moveCheese()
                            end
                        end
                    else
                        if _S.movecheese.loop then
                            _S.movecheese.currentIndex = 1
                        end
                    end
                for player, data in pairs(tfm.get.room.playerList) do
                    if not data.hasCheese then
                        if pythag(_S.movecheese.currentPos[1], _S.movecheese.currentPos[2], data.x, data.y, 25) then
                            tfm.exec.giveCheese(player)
                            if _S.movecheese.order == 1 then    
                                _S.movecheese.callbacks.moveCheese()
                            end
                        end
                        if _S.movecheese.order == 3 then
                            if pythag(_S.movecheese.currentPos[1], _S.movecheese.currentPos[2], data.x, data.y, 50) then
                                _S.movecheese.callbacks.moveCheese()
                            end
                        end
                    end
                end
            end
        end,
        moveCheese=function(givenX, givenY)
            _S.movecheese.lastMove = os.time()
            local i = _S.movecheese.currentIndex
            local X = givenX or _S.movecheese.cheesePositions.X[i] or _S.movecheese.cheesePositions.X[i-1]
            local Y = givenY or _S.movecheese.cheesePositions.Y[i] or _S.movecheese.cheesePositions.Y[i-1]
            if X == "fixed" then
                X = _S.movecheese.currentPos[1] or 0
            end
            if Y == "fixed" then
                Y = _S.movecheese.currentPos[2] or 0
            end 
            if X == "random" then
                X = math.random(50, map.length-50)
            end
            if Y == "random" then
                Y = math.random(50, map.height-50)
            end 
            tfm.exec.removeImage(_S.movecheese.image)
            X = tonumber(X) or 0
            Y = tonumber(Y) or 0
            _S.movecheese.image = tfm.exec.addImage("1507b11c813.png", "!100", (X)-23, (Y)-19, nil)
            _S.movecheese.currentPos = {X, Y}
            if not givenX then
                _S.movecheese.currentIndex = _S.movecheese.currentIndex+1
            end
        end,
    },
}


--[[ src/segments/nogravmove.lua ]]--

_S.nogravmove = {
    disabled=true,
    callbacks={
        newGame=function()
            local attachImage = getValueFromXML(tfm.get.room.xmlMapInfo.xml, "spriteImage") or "broom"
            for name,player in pairs(players) do
                _S.images.selectImage(player,attachImage)
            end
        end,
        roundEnd=function()
            for name,player in pairs(players) do
                player.sprite=nil
            end
        end,
        keyboard={
            [KEYS.UP]=function(player,down,x,y)
                if down then
                    tfm.exec.movePlayer(player.name,0,0,false,0,-50,false)
                else
                    tfm.exec.movePlayer(player.name,0,0,false,-1,-1,false)
                    tfm.exec.movePlayer(player.name,0,0,false,1,1,true)
                end
            end,
            [KEYS.DOWN]=function(player,down,x,y)
                if down then
                    tfm.exec.movePlayer(player.name,0,0,false,0,50,false)
                else
                    tfm.exec.movePlayer(player.name,0,0,false,-1,-1,false)
                    tfm.exec.movePlayer(player.name,0,0,false,1,1,true)
                end
            end,
            [KEYS.LEFT]=function(player,down,x,y)
                if down then
                    tfm.exec.movePlayer(player.name,0,0,false,-50,0,false)
                else
                    tfm.exec.movePlayer(player.name,0,0,false,-1,-1,false)
                    tfm.exec.movePlayer(player.name,0,0,false,1,1,true)
                end
            end,
            [KEYS.RIGHT]=function(player,down,x,y)
                if down then
                    tfm.exec.movePlayer(player.name,0,0,false,50,0,false)
                else
                    tfm.exec.movePlayer(player.name,0,0,false,-1,-1,false)
                    tfm.exec.movePlayer(player.name,0,0,false,1,1,true)
                end
            end,
        }
    }
}


--[[ src/segments/omo.lua ]]--

_S.omo = {
    defaultPlayer=function(player)
        player.activeSegments.omo=true
    end,
    welcomed={},
    startID=100,
    emotes={"omo","@_@","@@","è_é","e_e","#_#",";A;","owo","(Y)(omo)(Y)","©_©","OmO","0m0","°m°","(´°?°`)","~(-_-)~","{^-^}"},
    display=function(name,str,x,y,size,border,fixed)
        local i=_S.omo.startID
        size=size or 32
        for xoff=-1,1 do
            for yoff=-1,1 do
                i=i+1
                if not (xoff==0 and yoff==0) then
                    ui.addTextArea(i,"<p align='center'><b><font size='"..size.."' color='#000000' face='Soopafresh,Segoe,Verdana'>"..str.."</font></b></p>",name,x+(xoff*(border or 1))-250,y+(yoff*(border or 1))-50,500,nil,nil,nil,0,fixed)
                end
            end
        end
        ui.addTextArea(i+1,"<p align='center'><b><font size='"..size.."' face='Soopafresh,Segoe,Verdana'>"..str.."</font></b></p>",name,x-250,y-50,500,nil,nil,nil,0,fixed)
        if not name then _S.omo.welcomed={} end
    end,
    callbacks={
        newGame=function()
            for name,player in pairs(players) do
                if _S.omo and (not _S.omo.welcomed[name] or _S.omo.welcomed[name]<os.time()-3000) then
                    _S.omo.welcomed[name]=nil
                    local id=_S.omo.startID
                    for i=1,10 do
                        ui.removeTextArea(id+i,name)
                    end
                end
            end
        end,
        newPlayer=function(player)
            _S.omo.welcomed[player.name]=os.time()
            _S.omo.display(player.name,moduleName,400,200,100,3,true)
        end,
        mouse={
            pr=1,
            fnc=function(player,x,y)
                if player.omo then
                    local str=player.omo.str or _S.omo.emotes[math.random(#_S.omo.emotes)]
                    _S.omo.display(nil,str,x,y,player.omo.size or 64,3)
                    if player.omo.str then player.omo=nil end
                    return true
                end
            end
        },
        eventLoop=function(time,remaining)
            for name,time in pairs(_S.omo.welcomed) do
                if time<os.time()-10000 then
                    _S.omo.welcomed[name]=nil
                    for i=1,10 do
                        ui.removeTextArea(_S.omo.startID+i,name)
                    end
                    break
                end
            end
        end,
        chatCommand={
            omo={
                rank=RANKS.ROOM_ADMIN,
                hide=true,
                fnc=function(player,...)
                    local arg={...}
                    local size
                    for k,v in ipairs(arg) do
                        if v:match("%[size=(%d+)]") then
                            size=v:match("%[size=(%d+)]")
                            table.remove(arg,k)
                        elseif v=="[br]" then
                            arg[k]="\n"
                        end 
                    end
                    activateSegment(player.name,"omo")
                    player.omo={str=#arg>0 and table.concat(arg," "),size=size}
                end
            },
            clear={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player)
                    local id=_S.omo.startID
                    for i=1,10 do
                        ui.removeTextArea(id+i)
                    end
                end
            }
        }
    }
}


--[[ src/segments/pet.lua ]]--

_S.pet = {
    pets={},
    defaultPlayer=function(player)
        player.activeSegments.pet=true
    end,
    callbacks={
        newGame=function()
            for name,pet in pairs(_S.pet.pets) do
                pet.id=tfm.exec.addShamanObject(6300,map.spawns[1] and map.spawns[1].x or 400,map.spawns[1] and map.spawns[1].y or 200,0,0,false)
                pet.farAway=0
                _S.pet.showImage(pet,"right")
            end
        end,
        eventLoop=function(time,remaining)
            for name,pet in pairs(_S.pet.pets) do
                if tfm.get.room.objectList[pet.id] and pet.stay > 0 and tfm.get.room.playerList[name] then
                    local x = 0
                    local y = 0
                    if pet.stay == 1 then
                        x=-(tfm.get.room.objectList[pet.id].x-tfm.get.room.playerList[name].x)
                        y=-(tfm.get.room.objectList[pet.id].y-tfm.get.room.playerList[name].y)
                    
                    elseif pet.stay == 2 then
                        if math.random(10) < 5 then
                            if math.random(10) < 2 then
                                x=-(tfm.get.room.objectList[pet.id].x-math.random(800))
                            else
                                x=(tfm.get.room.objectList[pet.id].x-math.random(800))
                            end
                            y=(tfm.get.room.objectList[pet.id].y-math.random(400))
                        else
                            if math.random(5) < 2 then
                                if _S.images.sprites[pet.sprite.category][pet.sprite.id].petaction then
                                    _S.images.sprites[pet.sprite.category][pet.sprite.id].petaction(pet)
                                end
                            else
                                if math.random(0, 1) == 1 then
                                    x=-(tfm.get.room.objectList[pet.id].x-math.random(-50, 50))
                                else
                                    x=(tfm.get.room.objectList[pet.id].x-math.random(-50, 50))
                                end                                 
                            end
                        end
                    elseif pet.stay == 3 then
                        if tfm.get.room.objectList[pet.treat] then
                            x=-(tfm.get.room.objectList[pet.id].x-tfm.get.room.objectList[pet.treat].x)
                            y=-(tfm.get.room.objectList[pet.id].y-tfm.get.room.objectList[pet.treat].y)
                            if pythag(tfm.get.room.objectList[pet.id].x, tfm.get.room.objectList[pet.id].y, tfm.get.room.objectList[pet.treat].x, tfm.get.room.objectList[pet.treat].y, 60) then
                                pet.ttick = pet.ttick+1
                                tfm.exec.displayParticle(30, tfm.get.room.objectList[pet.treat].x, tfm.get.room.objectList[pet.treat].y, 0, -1, 0, 0)
                                if pet.ttick == 20 then
                                    for i = 0, 10 do
                                        tfm.exec.displayParticle(5, tfm.get.room.objectList[pet.treat].x, tfm.get.room.objectList[pet.treat].y, math.cos(i), math.sin(i), 0, 0)
                                    end
                                    tfm.exec.removeObject(pet.treat)
                                    pet.treat=false
                                    pet.stay=2
                                end
                            end
                        else
                            pet.treat=false
                            pet.stay=2
                        end
                    elseif pet.stay == 4 then
                        if tfm.get.room.objectList[pet.ball] then
                            x=-(tfm.get.room.objectList[pet.id].x-tfm.get.room.objectList[pet.ball].x)
                            y=-(tfm.get.room.objectList[pet.id].y-tfm.get.room.objectList[pet.ball].y)  
                            if pythag(tfm.get.room.objectList[pet.id].x, tfm.get.room.objectList[pet.id].y, tfm.get.room.objectList[pet.ball].x, tfm.get.room.objectList[pet.ball].y, 20) then
                                bx=(tfm.get.room.objectList[pet.ball].x-tfm.get.room.objectList[pet.id].x)
                                by=(tfm.get.room.objectList[pet.ball].y-tfm.get.room.objectList[pet.id].y)  
                                tfm.exec.moveObject(pet.ball, 0, 0, false, bx, by, true)
                            end 
                        else
                            pet.treat=false
                            pet.stay=2
                        end
                    end
                    if (math.abs(x)>300 or math.abs(y)>300) then
                        pet.farAway=pet.farAway+1
                        if pet.farAway==16 then
                            if pet.stay ~= 2 then
                                tfm.exec.moveObject(pet.id,tfm.get.room.playerList[name].x,tfm.get.room.playerList[name].y,false,0,0)
                                for i = 0, 5 do
                                    tfm.exec.displayParticle(9, tfm.get.room.playerList[name].x, tfm.get.room.playerList[name].y, math.cos(i), math.sin(i), 0, 0)
                                end
                            end
                            pet.farAway=0
                        end
                    end
                    local maxpower=30
                    local highest=0
                    if math.abs(x)>math.abs(y) then highest=math.abs(x) else highest=math.abs(y) end
                    local multiplier=highest/maxpower
                    if x==0 then x=1 end if y==0 then y=1 end
                    --if (tfm.get.room.objectList[pet.id].x or 0 >(map.length or 800)-100 and x>0) or (tfm.get.room.objectList[pet.id].x or 0 <100 and x<0) then x=x*-1 end
                    
                    if pet.stay==1 then
                        tfm.exec.moveObject(pet.id,0,0,false,math.abs(x)<120 and 1 or (x/multiplier),(y/multiplier)+(math.abs(x)>120 and -40 or 1),false)
                    elseif pet.stay==2 then
                        tfm.exec.moveObject(pet.id,0,0,false,math.abs(x)<70 and 1 or (x/multiplier),(y/multiplier)+(math.abs(x)>120 and -40 or 1),false)
                    elseif pet.stay==3 then
                        tfm.exec.moveObject(pet.id,0,0,false,math.abs(x)<70 and 1 or (x/multiplier),(y/multiplier)+(math.abs(x)>120 and -40 or 1),false)
                    elseif pet.stay==4 then
                        tfm.exec.moveObject(pet.id,0,0,false,math.abs(x)<90 and 1 or (x/multiplier),(y/multiplier)+(math.abs(x)>120 and -40 or 1),false)
                    end
                    local direction=x<0 and "left" or "right"
                    if pet.direction~=direction then
                        pet.direction=direction
                        _S.pet.showImage(pet,direction)
                    end
                    
                end
            end
        end,
        playerLeft=function(player)
            if _S.pet.pets[player.name] then
                tfm.exec.removeObject(_S.pet.pets[player.name].id)
                _S.pet.pets[player.name]=nil
            end
        end,
        chatCommand={
            pet={
                rank=RANKS.ROOM_ADMIN,
                fnc=function(player,...)
                    local arg={...}
                    if arg[1]=="here" and _S.pet.pets[player.name] then
                        tfm.exec.moveObject(_S.pet.pets[player.name].id,tfm.get.room.playerList[player.name].x,tfm.get.room.playerList[player.name].y)
                    elseif arg[1]=="spawn" and not _S.pet.pets[player.name] then
                        local category="main"
                        local id="pusheen"
                        if arg[2] then
                            for cat,tbl in pairs(_S.images.sprites) do
                                if tbl[arg[2]:lower()] then
                                    category=cat
                                    id=arg[2]:lower()
                                    break
                                end
                            end
                        end
                        _S.pet.pets[player.name]={
                            name=player.name,
                            id=tfm.exec.addShamanObject(6300,tfm.get.room.playerList[player.name].x,tfm.get.room.playerList[player.name].y,0,0,0,false),
                            direction="right",
                            sprite={
                                category=category,
                                id=id
                            },
                            farAway=0,
                            stay = 1,
                            treat = false,
                            ttick = 0,
                            ball = false
                        }
                        _S.pet.showImage(_S.pet.pets[player.name],"right")
                        for i = 0, 10 do
                            tfm.exec.displayParticle(9, tfm.get.room.playerList[player.name].x, tfm.get.room.playerList[player.name].y, math.cos(i), math.sin(i), 0, 0)
                        end
                    elseif arg[1]=="despawn" and _S.pet.pets[player.name] then
                        tfm.exec.removeObject(_S.pet.pets[player.name].id)
                        if _S.pet.pets[player.name].ball then
                            tfm.exec.removeObject(_S.pet.pets[player.name].ball)
                        end
                        if _S.pet.pets[player.name].treat then
                            tfm.exec.removeObject(_S.pet.pets[player.name].treat)
                        end
                        _S.pet.pets[player.name]=nil
                    end
                    if _S.pet.pets[player.name] then
                        if not _S.pet.pets[player.name].treat then
                            if arg[1]=="stay" and _S.pet.pets[player.name]  then
                                _S.pet.pets[player.name].stay=0
                            elseif arg[1]=="follow" and _S.pet.pets[player.name] then
                                _S.pet.pets[player.name].stay=1
                            elseif arg[1]=="free" and _S.pet.pets[player.name] then
                                _S.pet.pets[player.name].stay=2
                            elseif arg[1]=="ball" and _S.pet.pets[player.name] then
                                if arg[2]=="here" then
                                    pos = tfm.get.room.playerList[player.name]
                                    tfm.exec.moveObject(_S.pet.pets[player.name].ball, pos.x, pos.y-40, false, 0, 0, false)
                                elseif arg[2]=="set" then
                                    _S.pet.pets[player.name].ball=tonumber(arg[3]) or _S.pet.pets[player.name].ball
                                    _S.pet.pets[player.name].stay=4
                                elseif arg[2]=="id" then
                                    tfm.exec.chatMessage(_S.pet.pets[player.name].ball, player.name)
                                else
                                    if _S.pet.pets[player.name].ball then
                                        tfm.exec.removeObject(_S.pet.pets[player.name].ball)
                                        _S.pet.pets[player.name].ball=false
                                    end
                                    pos = tfm.get.room.playerList[player.name]
                                    _S.pet.pets[player.name].ball = tfm.exec.addShamanObject(6, pos.x, pos.y-40, 0, 0, 0)
                                    tfm.exec.addImage("1515419b6f5.png", "#".._S.pet.pets[player.name].ball, -15, -15, nil)
                                    _S.pet.pets[player.name].stay=4
                                    tfm.exec.addPhysicObject(1, 0, map.height/2, {height = map.height*2, miceCollision = false, type = 12})
                                    tfm.exec.addPhysicObject(2, map.length, map.height/2, {height = map.height*2, miceCollision = false, type = 12})
                                end
                            end
                        else
                            tfm.exec.chatMessage(translate("peteating",player.lang), player.name)
                        end
                    end
                    if arg[1]=="treat" and _S.pet.pets[player.name] then
                        if _S.pet.pets[player.name].treat then
                            tfm.exec.removeObject(_S.pet.pets[player.name].treat)
                            _S.pet.pets[player.name].treat=false
                        else
                            pos = tfm.get.room.playerList[player.name]
                            _S.pet.pets[player.name].treat = tfm.exec.addShamanObject(1, pos.x, pos.y, 0, 0, 0)
                            tfm.exec.addImage("1514f246497.png", "#".._S.pet.pets[player.name].treat, -15, -15, nil)
                            _S.pet.pets[player.name].stay=3
                            _S.pet.pets[player.name].ttick=0
                        end
                    end
                end
            }
        }
    },
    showImage=function(pet,direction)
        if pet.sprite.img then tfm.exec.removeImage(pet.sprite.img) end
        local directory=_S.images.sprites[pet.sprite.category][pet.sprite.id][direction] or _S.images.sprites[pet.sprite.category][pet.sprite.id]
        local dirroot=_S.images.sprites[pet.sprite.category][pet.sprite.id]
        pet.sprite.img=tfm.exec.addImage(directory.img..".png","#"..pet.id,directory.x or dirroot.x or -50,4+(directory.y or dirroot.y or -50))
    end,
}


--[[ src/segments/projection.lua ]]--

-- Simulates the shaman skill "projection"

_S.projection = {
    callbacks={
        keyboard={
            [KEYS.LEFT]=function(player,down,x,y) 
                if down then
                    if player.dash and player.dash.direction=="left" and player.dash.time>os.time()-250 and (player.lastDash and player.lastDash<os.time()-3000 or not player.lastDash) then
                        tfm.exec.movePlayer(player.name, x-100, y)
                        player.lastDash=os.time()
                        for i=1,6 do
                            tfm.exec.displayParticle(3,x,y,math.random(-1,1),math.random(-1,1),0,0)
                            tfm.exec.displayParticle(35,x-50,y,0,0,0,0)
                        end
                    end
                    player.dash={time=os.time(),direction="left"}
                end
            end,
            [KEYS.RIGHT]=function(player,down,x,y) 
                if down then
                    if player.dash and player.dash.direction=="right" and player.dash.time>os.time()-250 and (player.lastDash and player.lastDash<os.time()-3000 or not player.lastDash) then
                        tfm.exec.movePlayer(player.name, x+100, y)
                        player.lastDash=os.time()
                        for i=1,6 do
                            tfm.exec.displayParticle(3,x,y,math.random(-1,1),math.random(-1,1),0,0)
                            tfm.exec.displayParticle(35,x+50,y,0,0,0,0)
                        end
                    end
                    player.dash={time=os.time(),direction="right"}
                end
            end,
        }
    }
}


--[[ src/segments/prophunt.lua ]]--

_S.prophunt = {
    hunters={},
    props={
        --name={x=,y=,img=}
    },
    defaultPlayer=function(player)
        --player.activeSegments.prophunt=true
        player.prophunt={
            ids={}
        }
    end,
    callbacks={
        newGame=function()
            --[[
            _S.prophunt.props={}
            for n,p in pairs(_S.prophunt.hunters) do
                tfm.exec.setPlayerScore(n,0)
            end
            local hunter=highscore()
            _S.prophunt.hunters={}
            _S.prophunt.hunters[hunter]={lives=3}
            _S.blind.textarea(hunter)
            --hearts(hunter)
            ]]
        end,
        keyboard={
            [KEYS.E]=function(player,down,x,y)
                if down and not tfm.get.room.playerList[player.name].isDead then
                    local closest
                    for _,deco in pairs(map.decorations) do
                        if pythag(x,y,deco.x,deco.y,20) and _S.images.sprites.props[deco.id] then
                            local d=distance(deco.x,deco.y,x,y)
                            if not closest or d<closest.distance then
                                closest={id=deco.id,distance=d}
                            end
                        end
                    end
                    if closest then
                        _S.images.selectImage(player,closest.id,"props")
                    end
                end
            end,
            [KEYS.SPACE]=function(player,down,x,y)
                if down and not tfm.get.room.playerList[player.name].isDead and player.sprite then
                    if _S.hide.hidden[player.name] then
                        tfm.exec.movePlayer(player.name,_S.prophunt.props[player.name].x,_S.prophunt.props[player.name].y)
                        tfm.exec.removeImage(_S.prophunt.props[player.name].img)
                        _S.hide.hidden[player.name]=nil
                        _S.prophunt.props[player.name]=nil
                    else
                        _S.hide.hidden[player.name]=true
                        _S.hide.movePlayer(player)
                        local image=_S.images.sprites[player.sprite.category][player.sprite.id]
                        local directory=image[player.facingRight and "right" or "left"] or image
                        _S.prophunt.props[player.name]={
                            x=x,
                            y=y,
                            img=tfm.exec.addImage(directory.img..".png","?50",x+(directory.x or image.x or -50),y+(directory.y or image.y or -50))
                        }
                    end
                end
            end,
        }
    }
}


--[[ src/segments/rain.lua ]]--

_S.rain = {
    disabled=true,
    ticks=0,
    ID=40,
    callbacks={
        eventLoop=function(time,remaining)
            _S.rain.ticks=_S.rain.ticks+1
            if _S.rain.ticks%2==0 then
                tfm.exec.addShamanObject(_S.rain.ID, math.random()*map.length, -800, math.random()*360)
            end
            
        end
    }
}


--[[ src/segments/rainbow.lua ]]--

_S.rainbow = {
    players={},
    defaultPlayer=function(player)
        _S.rainbow.players[player] = os.time()
    end,
    callbacks={
        keyboard={
            [KEYS.SPACE]=function(player,down,x,y)
                if down then
                    if _S.rainbow.players[player] < os.time()-500 then
                        _S.rainbow.players[player] = os.time()
                        for a = math.pi, 2*math.pi, math.pi/50 do
                            local s = 3
                            vx1, vy1 = s*math.cos(a), s*math.sin(a)
                            local m = -3/100
                            vx,vy=vx1,vy1
                            tfm.exec.displayParticle(1, x, y+12, vx, vy, m*vx, m*vy)
                            vx,vy=vx1*1.1,vy1*1.1
                            tfm.exec.displayParticle(9, x, y+12, vx, vy, m*vx, m*vy)
                            vx,vy=vx1*1.1,vy1*1.2
                            tfm.exec.displayParticle(11, x, y+12, vx, vy, m*vx, m*vy)
                            vx,vy=vx1*1.3,vy1*1.3
                            tfm.exec.displayParticle(13, x, y+12, vx, vy, m*vx, m*vy)
                        end
                    end
                end
            end,
        },
    },
}


--[[ src/segments/ratapult.lua ]]--

_S.ratapult = {
    toDespawn={},
    defaultPlayer=function(player)
        player.activeSegments.ratapult=false
        player.ratapult={
            timestamp=os.time(),
            cooldown=1500,
            spawnLength=3000,
        }
    end,
    callbacks={
        keyboard={
            [KEYS.DOWN]=function(player,down,x,y)
                if not tfm.get.room.playerList[player.name].isDead then
                    if down then
                        player.ratapult.timestamp=os.time()
                    else
                        if os.time()-player.ratapult.timestamp-player.ratapult.cooldown>=0 then
                            local power=(os.time()+player.ratapult.cooldown-player.ratapult.timestamp)/100
                            if power>75 then power=75 end
                            table.insert(_S.ratapult.toDespawn,{
                                id=tfm.exec.addShamanObject(10,player.facingRight and x+30 or x-30,y,0,player.facingRight and power or -power),
                                despawn=os.time()+player.ratapult.spawnLength
                            })
                            player.ratapult.timestamp=os.time()
                        end
                    end
                end
            end,
        },
    },
}


--[[ src/segments/retro.lua ]]--

_S.retro = {
    disabled=true,
    callbacks={
        newGame=function()
            for _,deco in ipairs(map.holes) do
                tfm.exec.addImage(_S.images.sprites.transformice.retrohole.img..".png","_100",deco.x+_S.images.sprites.transformice.retrohole.x,deco.y+_S.images.sprites.transformice.retrohole.y-15)
            end
        end,
    }
}


--[[ src/segments/speed.lua ]]--

_S.speed = {
    callbacks={
        keyboard={
            [KEYS.LEFT]=function(player,down,x,y) 
                if down then
                    tfm.exec.movePlayer(player.name, 0, 0, false, -player.speedPower, 0, false)
                end
            end,
            [KEYS.RIGHT]=function(player,down,x,y)
                if down then
                    tfm.exec.movePlayer(player.name, 0, 0, false, player.speedPower, 0, false)
                end
            end,
        }
    }
}


--[[ src/segments/splashscreen.lua ]]--

_S.splashScreen = {
    disabled=true,
    welcomed={},
    callbacks={
        newPlayer=function(player)
            _S.splashScreen.welcomed[player.name]={time=os.time(),img=tfm.exec.addImage("150da3fae92.png","&100",100,100,player.name)}
        end,
        eventLoop=function(time,remaining)
            for name,tbl in pairs(_S.splashScreen.welcomed) do
                if tbl.time<os.time()-10000 then
                    for i=1,10 do
                        tfm.exec.removeImage(tbl.img)
                    end
                    _S.splashScreen.welcomed[name]=nil
                    break
                end
            end
        end,
    }
}



--[[ src/segments/tp.lua ]]--

_S.tp = {
    callbacks={
        mouse={
            pr=1,
            fnc=function(player,x,y)
                for _,n in pairs(player.tp) do
                    tfm.exec.movePlayer(n,x,y)
                end
                player.tp=nil
                player.activeSegments.tp=false
            end
        }
    }
}


--[[ src/segments/treelights.lua ]]--

_S.treelights = {
    disabled=true,
    decorations={},
    ids={0,1,2,4,9},
    callbacks={
        newGame=function()
            _S.treelights.decorations={}
            for _,deco in pairs(map.decorations) do
                if deco.id==57 then
                    table.insert(_S.treelights.decorations,deco)
                end
            end
        end,
        eventLoop=function(time,remaining)
            for _, deco in pairs(_S.treelights.decorations) do
                for k = 10,0,-1 do
                    tfm.exec.displayParticle(_S.treelights.ids[math.random(#_S.treelights.ids)],deco.x+math.random(-40,40),deco.y+math.random(-120,0),0,0,0,0)
                    tfm.exec.displayParticle(11,deco.x-2,deco.y-183,math.cos(k),math.sin(k),0,0)
                end
            end
        end
    }
}


--[[ src/segments/map-modes/all-shaman.lua ]]--

_S.mapMode_all_shaman = {
    disabled=true,
    callbacks={
        newGame=function()
            for n,p in pairs(tfm.get.room.playerList) do
                tfm.exec.setShaman(n)
            end
        end,
        roundEnd=function()
            for n,p in pairs(tfm.get.room.playerList) do
                if p.isShaman then
                    tfm.exec.setPlayerScore(n,0,true)
                end
            end
        end,
        playerDied=function(player)
            if alivePlayers()<=2 and currentTime<20000 then
                tfm.exec.setGameTime(5)
            end
        end
    }
}



--[[ src/segments/map-modes/bootcamp.lua ]]--

_S.mapMode_bootcamp = {
    disabled=true,
    ticks=0,
    callbacks={
        newGame=function()
            _S.mapMode_bootcamp.ticks=0
            tfm.exec.setGameTime(999999)
        end,
        eventLoop=function(time,remaining)
            _S.mapMode_bootcamp.ticks=_S.mapMode_bootcamp.ticks+1
            if _S.mapMode_bootcamp.ticks==10 then
                for n,p in pairs(tfm.get.room.playerList) do
                    if p.isDead then
                        tfm.exec.respawnPlayer(n)
                    end
                end
            end
        end,
        playerWon=function(player)
            tfm.exec.setPlayerScore(player.name,10,true)
        end,
        playerDied=function(player)
            tfm.exec.setPlayerScore(player.name,-1,true)
        end
    }
}



--[[ src/segments/map-modes/dual-shaman.lua ]]--

_S.mapMode_dual_shaman = {
    disabled=true,
    callbacks={
        newGame=function()
            _S.mapMode_normal.callbacks.newGame()
            local scores=sortScores()
            tfm.exec.setShaman(scores[1].name)
            tfm.exec.setShaman(scores[2].name)
        end,
        roundEnd=function()
            for n,p in pairs(tfm.get.room.playerList) do
                if p.isShaman then
                    tfm.exec.setPlayerScore(n,0)
                end
            end
        end,
        playerDied=function(player)
            _S.mapMode_normal.callbacks.playerDied(player)
        end,
        playerWon=function(player)
            _S.mapMode_normal.callbacks.playerWom(player)
        end,
    }
}



--[[ src/segments/map-modes/normal.lua ]]--

_S.mapMode_normal = {
    disabled=true,
    playersWon=0,
    endCondition=function()
        tfm.exec.setPlayerScore(player.name,-1,true)
        if (tfm.get.room.playerList[player.name].isShaman or playersAlive()==2) and currentTime20000 then
            tfm.exec.setGameTime(20)
        elseif playersAlive()==1 and currentTime20000 then
            tfm.exec.setGameTime(20)
        elseif playersAlive()==0 and currentTime5000 then
            tfm.exec.setGameTime(5)
        end
    end,
    callbacks={
        newGame=function()
            _S.mapMode_normal.playersWon=0
        end,
        playerDied=function(player)
            tfm.exec.setPlayerScore(player.name,1,true)
            _S.mapMode_normal.endCondition()
        end,
        playerWon=function(player)
            if _S.mapMode_normal.playersWon==0 then
                tfm.exec.setPlayerScore(player.name,16,true)
            elseif _S.mapMode_normal.playersWon==1 then
                tfm.exec.setPlayerScore(player.name,14,true)
            elseif _S.mapMode_normal.playersWon==2 then
                tfm.exec.setPlayerScore(player.name,12,true)
            else
                tfm.exec.setPlayerScore(player.name,10,true)
            end
            _S.mapMode_normal.playersWon=_S.mapMode_normal.playersWon+1
            _S.mapMode_normal.endCondition()
        end,
    }
}


--[[ src/segments/map-modes/racing.lua ]]--

_S.mapMode_racing = {
    disabled=true,
    callbacks={
        newGame=function()
            tfm.exec.setGameTime(60)
        end
    }
}


--[[ src/segments/map-modes/shaman.lua ]]--

_S.mapMode_shaman = {
    disabled=true,
    callbacks={
        newGame=function()
            _S.mapMode_normal.callbacks.newGame()
            local scores=sortScores()
            tfm.exec.setShaman(scores[1].name)
        end,
        roundEnd=function()
            for n,p in pairs(tfm.get.room.playerList) do
                if p.isShaman then
                    tfm.exec.setPlayerScore(n,0)
                end
            end
        end,
        playerDied=function(player)
            _S.mapMode_normal.callbacks.playerDied(player)
        end,
        playerWon=function(player)
            _S.mapMode_normal.callbacks.playerWom(player)
        end,
    }
}



--[[ src/segments/map-modes/survivor.lua ]]--

_S.mapMode_survivor = {
    disabled=true,
    endCondition=function()
        local alive=false
        for n,p in pairs(tfm.get.room.playerList) do
            if not p.isDead and not p.isShaman then
                alive=true
                break
            end
        end
        if not alive then
            tfm.exec.setGameTime(5)
        end
    end,
    callbacks={
        roundEnd=function()
            for n,p in pairs(tfm.get.room.playerList) do
                if not p.isDead and not p.isShaman then
                    tfm.exec.setPlayerScore(n,10,true)
                end
            end
        end,
        playerDied=function(player)
            tfm.exec.setPlayerScore(n,1,true)
            _S.mapMode_survivor.endCondition()
        end,
    }
}



--[[ src/segments/map-modes/tribe.lua ]]--

_S.mapMode_tribe = {
    disabled=true,
    callbacks={
        newGame=function()
            tfm.exec.setGameTime(999999)
        end,
        playerDied=function(player)
            tfm.exec.respawnPlayer(player.name)
        end
    }
}



--[[ src/segments/map-modes/vampire.lua ]]--

_S.mapMode_vampire = {
    disabled=true,
    ticks=0,
    endCondition=function()
        local alive=false
        for n,p in pairs(tfm.get.room.playerList) do
            if not p.isDead and not p.isVampire then
                alive=true
                break
            end
        end
        if not alive then
            tfm.exec.setGameTime(5)
        end
    end,
    callbacks={
        newGame=function()
            tfm.exec.setGameTime(120)
        end,
        eventLoop=function(time,remaining)
            _S.mapMode_vampire.ticks=_S.mapMode_vampire.ticks+1
            if _S.mapMode_vampire.ticks==30 then
                local tbl={}
                for n,p in pairs(tfm.get.room.playerList) do
                    if not p.isDead then
                        table.insert(tbl,n)
                    end
                end
                tfm.exec.setVampirePlayer(tbl[math.random(#tbl)])
            end
        end,
        roundEnd=function()
            for n,p in pairs(tfm.get.room.playerList) do
                if not p.isDead and not p.isVampire then
                    tfm.exec.setPlayerScore(n,10,true)
                end
            end
        end,
        playerDied=function(player)
            tfm.exec.setPlayerScore(n,1,true)
            _S.mapMode_vampire.endCondition()
        end,
        playerVampire=function(player)
            _S.mapMode_vampire.endCondition()
        end
    }
}



--[[ src/after.lua ]]--

-- Script to provision the module and initialise necessary methods

bindChatCommands()

for name,player in pairs(tfm.get.room.playerList) do
    eventNewPlayer(name)
end

selectMap()