class Identifiers:
    class bulle:
        BU_InitConnection = 1000
        BU_ConnectToGivenRoom = 1001
        BU_SendAnimZelda = 1003
        BU_SendRoomPrison = 1004
        BU_SendMuMute = 1005
        BU_SendModerationMesage = 1006
        BU_LoadMapEditor_Map = 1007
        BU_DrawingClear = 1008
        BU_DrawingStart = 1009
        BU_DrawingPoint = 1010
        BU_GetChatLog = 1011
        BU_ReceiveTitleID = 1012
        BU_RespawnPlayer = 1013
        BU_ChangeRoomTime = 1014
        BU_Clear_Room_Chat = 1015
        BU_SendRoomCreator = 1016
        BU_SendBanMessage = 1017
        BU_SendMute = 1018
        BU_DeleteMap = 1019
        BU_ChangePlayerLook = 1020
        BU_ChangeShamanLook = 1021
        BU_Interrupt_Connection = 1022
        BU_ChangeShamanBadge = 1023
        BU_ChangeShamanType = 1024
        BU_ChangeShamanColor = 1025
        BU_ManageFunCorpRoom = 1026
        BU_FunCorpGiveTransformationPowers = 1027
        BU_FunCorpGiveMeepPowers = 1028
        BU_FunCorpRoomEvent = 1029
        BU_FunCorpChangePlayerSize = 1030
        BU_FunCorpLinkMices = 1031
        BU_ChangeRoomMaximumPlayers = 1032
        BU_SendPlayerPet = 1033
        BU_SendPlayerFur = 1034
        BU_SendPlayerPencil = 1035
        BU_SendTrowableObject = 1036
        BU_SendPlayerEmote = 1037
        BU_SendBallonBadge = 1038
        BU_SendPlayerPlayedTime = 1039
        BU_SendPlayerCheeses = 1040
        BU_SendPlayerMicrophone = 1041
        BU_SendPlayerBonfire = 1042
        BU_UseInventoryConsumable = 1043
        BU_SendMistletoe = 1044
        BU_SendShopBadge = 1045
        BU_SendUnlockTitle = 1046
        BU_Change_Map = 1047
        BU_FunCorpChangeMouseColor = 1048
        BU_FunCorpChangeNickColor = 1049
        BU_ReceiveBulleInformation = 1050
        BU_UpdateShamanSkill = 1051
            
    
    class recv:
        # Old packet
        Old_Protocol = (1, 1)
    
        # Room
        Object_Sync = (4, 3)
        Mort = (4, 5)
        Shaman_Position = (4, 8)
        Crouch = (4, 9)
        
        Shaman_Message = (5, 9)
        Convert_Skill = (5, 14)
        Demolition_Skill = (5, 15)
        Projection_Skill = (5, 16)
        Enter_Hole = (5, 18)
        Get_Cheese = (5, 19)
        Place_Object = (5, 20)
        Ice_Cube = (5, 21)
        Defilante_Points = (5, 25)
        Restorative_Skill = (5, 26)
        Recycling_Skill = (5, 27)
        Gravitational_Skill = (5, 28)
        Antigravity_Skill = (5, 29)
        Handymouse_Skill = (5, 35)
        Send_Music = (5, 70)
        Send_PlayList = (5, 73)
        
        # Chat packets
        Send_Chat_Message = (6, 6)
        Execute_Command = (6, 26)
        
        # Player
        Player_Action = (8, 1)
        Player_Emotions = (8, 5)
        Player_Shaman_Fly = (8, 15)
        Player_Meep = (8, 39)
    
        Player_MS_Info = (26, 25)
        Player_IPS_Info = (26, 28)
        
        # Transformation
        Transformation_Object = (27, 11)
                
        # Mulodrome
        Mulodrome_Close = (30, 13)
        Mulodrome_Join = (30, 15)
        Mulodrome_Leave = (30, 17)
        Mulodrome_Play = (30, 20)
    
        # Bulle
        Receive_Bulle_Info = (44, 1)
        
        # New Packets
        Invocation = (100, 2)
        Remove_Invocation = (100, 3)
        Strm_Force_Next_Shaman = (100, 20)
        Crazzy_Packet = (100, 40)
        Map_Info = (100, 80)
        
        # New-New packets
        Attach_Ballon_To_Player = (149, 10)
        Detach_Ballon_To_Player = (149, 11)
        Player_Movement = (149, 26)

        
    class send:
        # Room packets
        Sync = [4, 3]
        Crouch = [4, 9]
        Shaman_Position = [4, 10]
    
        Rounds_Count = [5, 1]
        Shaman_Message = [5, 9]
        Convert_Skill = [5, 13]
        Skill_Object = [5, 14]
        Demolition_Skill = [5, 15]
        Projection_Skill = [5, 16]
        Map_Start_Timer = [5, 10]
        Spawn_Object = [5, 20]
        Enter_Room = [5, 21]
        Round_Time = [5, 22]
        Restorative_Skill = [5, 26]
        Recycling_Skill = [5, 27]
        Gravitation_Skill = [5, 28]
        Antigravity_Skill = [5, 29]
        Rollout_Mouse_Skill = [5, 30]
        Mouse_Size = [5, 31]
        Remove_All_Objects_Skill = [5, 32]
        Leaf_Mouse_Skill = [5, 33]
        Iced_Mouse_Skill = [5, 34]
        Handymouse_Skill = [5, 35]
        Spider_Mouse_Skill = [5, 36]
        Grapnel_Mouse_Skill = [5, 37]
        Evolution_Skill = [5, 38]
        #Room_Password = [5, 39]
        Skill = [5, 40]
        Reset_Shaman_Skills = [5, 42]
        Gatman_Skill = [5, 43]
        Bonfire_Skill = [5, 45]
        Soulmate = [5, 48]
        Teleport = [5, 50]
        Music_Video = [5, 72]
        Music_Playlist = [5, 73]
        Tutorial = [5, 90]
        
        # Chat packets
        Chat_Message = [6, 6]
        Message = [6, 9]
        Send_Staff_Chat_Message = [6, 10]
        Recv_Message = [6, 20]
        
        Room_Server = [7, 1]
        Room_Type = [7, 30]
        
        # Player packets
        Player_Action = [8, 1]
        Emotion = [8, 5]
        Player_Win = [8, 6]
        Enable_Skill = [8, 10]
        Shaman_Info = [8, 11]
        Shaman_Fly = [8, 15]
        #Meep_IMG = [8, 18]
        NPC = [8, 30]
        Meep = [8, 38]
        Can_Meep = [8, 39]
        Unlocked_Badge = [8, 42]
        Anim_Zelda = [8, 44]
        Vampire_Mode = [8, 66]

        # Modopwet
        Modopwet_Room_Password_Protected = [25, 4]
        Modopwet_Chat_Log = [25, 10]
        
        # Login packets
        Player_MS_Info = [26, 25]
        Player_IPS_Info = [26, 28]
        
        # Transformation
        Can_Transformation = [27, 10]
        Transformation = [27, 11]
        
        # Informations
        Message_Langue = [28, 5]
        Totem_Item_Count = [28, 11]
        
        # Lua
        Initialize_Lua_Scripting = [29, 1]
        Set_Name_Color = [29, 4]
        
        # Mulodrome
        Mulodrome_Result = [30, 4]
        Mulodrome_End = [30, 13]
        Mulodrome_Start = [30, 14]
        Mulodrome_Join = [30, 15]
        Mulodrome_Leave = [30, 16]
        Mulodrome_Winner = [30, 21]
        
        # Inventory, Consumables and Trades
        Use_Inventory_Consumable = [31, 3]

        # Transformice
        Invocation = [100, 2]
        Remove_Invocation = [100, 3]
        Jankenpon = [100, 5]
        Dead_Bubble = [100, 7]
        Crazzy_Packet = [100, 40]
        Pet = [100, 70]
        Baloon_Badge = [100, 71]
        Collectible_Action = [100, 101]
        
        # New packets
        Player_List = [144, 1]
        Player_Respawn = [144, 2]
        Player_Get_Cheese = [144, 6]
        Play_Shaman_Invocation_Sound = [144, 9]
        Attach_Ballon_Player = [144, 20]
        Detach_Ballon_Player = [144, 21]
        Player_Movement = [144, 48]
        
    class old:
        class recv:
            Player_Bomb_Explode = (4, 6) 

            Room_Anchors = (5, 7)
            Totem_Anchors = (5, 13)
            Room_Bombs = (5, 17)

            Vote_Map = (14, 4)
            Map_Editor_Validate_Map = (14, 10)
            Map_Editor_Map_Xml = (14, 11)
            Return_To_Map_Editor = (14, 14)
            Map_Editor_Export_Map = (14, 18)
            Map_Editor_Reset_Map = (14, 19)
    
        class send:
            Player_Bomb_Explode = [4, 6]
            Anchors = [5, 7]
            Bombs = [5, 17]
        
            Player_Died = [8, 5]
            Player_Disconnect = [8, 7]
            Unlocked_Title = [8, 14]
            Shaman_Perfomance = [8, 17]
            Save_Remaining = [8, 18]
            Sync = [8, 21]
            Catch_The_Cheese_Map = [8, 23]
            
            Vote_Box = [14, 4]
            Map_Exported = [14, 5]
            Load_Map_Result = [14, 8]
            Load_Map = [14, 9]
            Map_Editor = [14, 14]
            Map_Validated = [14, 17]
            Editor_Message = [14, 20]


            Totem = [22, 22]
        
            Drawing_Clear = [25, 3]
            Drawing_Start = [25, 4]
            Drawing_Point = [25, 5]