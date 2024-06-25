class Identifiers:
    class bulle:
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
        
    class tribulle:
        class recv:
            ST_ChangerDeGenre = 10
            ST_AjoutAmi = 18
            ST_RetireAmi = 20
            ST_DemandeEnMariage = 22
            ST_RepondDemandeEnMariage = 24
            ST_DemandeDivorce = 26
            ST_ListeAmis = 28
            ST_FermeeListeAmis = 30
            ST_AjoutNoire = 42
            ST_RetireListeNoire = 44
            ST_ListeNoire = 46
            ST_EnvoitMessageCanal = 48
            ST_EnvoitMessageTribu = 50
            ST_EnvoitMessagePrive = 52
            ST_RejoindreCanal = 54
            ST_QuitterCanal = 56
            ST_DemandeMembresCanal = 58
            ST_DefinitModeSilence = 60
            ST_InviterMembre = 78
            ST_RepondInvitationTribu = 80
            ST_QuitterTribu = 82
            ST_CreerTribu = 84
            ST_ChangerMessageJour = 98
            ST_ExclureMembre = 104
            ST_DemandeInformationsTribu = 108
            ST_FermeeTribu = 110
            ST_AffecterRang = 112
            ST_ModifierDroitRang = 114
            ST_RenommerRang = 116
            ST_AjouterRang = 118
            ST_SupprimerRang = 120
            ST_InverserOrdreRangs = 122
            ST_DesignerChefSpirituel = 126
            ST_DissoudreTribu = 128
            ST_ListeHistoriqueTribu = 132
        class send:
            ET_ResultatChangementGenre = 11
            ET_SignaleChangementGenre = 12
            ET_ResultatAjoutAmi = 19
            ET_ResultatSuppressionAmi = 21
            ET_ResultatDemandeEnMariage = 23
            ET_ResultatReponseDemandeEnMariage = 25
            ET_ResultatDemandeOuvertureListeAmis = 29
            ET_ResultatDemandeFermetureListeAmis = 31
            ET_SignalementConnexionAmi = 32
            ET_SignalementDeconnexionAmi = 33
            ET_ResultatListeAmis = 34
            ET_SignalementModificationAmi = 35
            ET_SignalementAjoutAmi = 36
            ET_SignalementSuppressionAmi = 37
            ET_SignalementDemandeEnMariage = 38
            ET_SignalementMariage = 39
            ET_SignalementRefusMariage = 40
            ET_SignalementDivorce = 41
            ET_ResultatAjoutListeNoire = 43
            ET_ResultatSuppressionListeNoire = 45
            ET_ResultatConsultationListeNoire = 47
            ET_ResultatEnvoiMessageChat = 49
            ET_ResultatEnvoiMessagePrive = 53
            ET_ResultatRejoindreCanalPublique = 55
            ET_ResultatQuitterCanalPublique = 57
            ET_ResultatListerCanalPublique = 59
            ET_ResultatDefinirModeSilence = 61
            ET_SignalementRejoindreCanalPublique = 62
            ET_SignalementQuitterCanalPublique = 63
            ET_SignalementMessageChat = 64
            ET_SignalementMessageTribu = 65
            ET_SignalementMessagePrive = 66
            ET_ResultatInvitationTribu = 79
            ET_ResultatRepondsInvitationTribu = 81
            ET_ResultatDemandeQuitterTribu = 83
            ET_SignaleInvitationTribu = 86
            ET_ResultatDemandeCreerTribu = 85
            ET_SignaleReponseInvitationTribu = 87
            ET_SignaleConnexionMembre = 88
            ET_SignaleInformationsMembreTribu = 89
            ET_SignaleDeconnexionMembre = 90
            ET_SignaleNouveauMembre = 91
            ET_SignaleDepartMembre = 92
            ET_SignaleExclusionMembre = 93
            ET_ResultatChangerMessageJour = 99
            ET_ResultatExclureMembre = 105
            ET_ResultatOuvertureInterfaceTribu = 109
            ET_ResultatFermetureInterfaceTribu = 111
            ET_ResultatAffecterRang = 113
            ET_SignaleChangementRang = 124
            ET_SignaleChangementMessageJour = 125
            ET_ResultatDesignerChef = 127
            ET_ResultatDemandeDissoudreTribu = 129
            ET_SignaleChangementParametresTribu = 130
            ET_SignaleChangementParametresMembre = 131
            ET_ResultatHistoriqueTribu = 133

    class recv:
        # Old Protocol
        Old_Protocol = (1, 1)
    
        # Room packets
        Enter_Room = (5, 38)
    
        # Chat packets
        Send_Staff_Chat_Message = (6, 10)
        Execute_Command = (6, 26)
        
        # Player packets
        Player_Shop_List = (8, 20)
        Player_Report = (8, 25)
        Init_Ping_System = (8, 30)
    
        # Shop packets
        Shop_Equip_Clothe = (20, 6)
        Shop_Save_Clothe = (20, 7)
        Shop_Info = (20, 15)
        Shop_Equip_Item = (20, 18)
        Buy_Shop_Item = (20, 19)
        Shop_Custom_Item = (20, 21)
        Buy_Shop_Clothe = (20, 22)
        Buy_Shop_Shaman_Item = (20, 23)
        Shop_Equip_Shaman_Item = (20, 24)
        Buy_Shop_Shaman_Custom = (20, 25)
        Shop_Custom_Shaman_Item = (20, 26)
        Shop_Send_Gift = (20, 28)
        Shop_Gift_Result = (20, 29)
        
        # Modopwet
        Open_Modopwet = (25, 2)
        Modopwet_Notifications = (25, 12)
        Modopwet_Delete_Report = (25, 23)
        Modopwet_Watch = (25, 24)
        Modopwet_BanHack = (25, 25)
        Modopwet_Change_Langue = (25, 26)
        Modopwet_Chat_Log = (25, 27)
        
        # Login packets
        Create_Account = (26, 7)
        Login_Account = (26, 8)
        Player_FPS_Info = (26, 13)
        Create_Survey = (26, 16)
        Survey_Answer = (26, 17)
        Survey_Result = (26, 18)
        Create_Account_Captcha = (26, 20)
        Login_Time = (26, 26)
        Player_IPS_Info = (26, 28)
        Request_Info = (26, 40)

    
        # Informations
        Correct_Version = (28, 1)
        Game_Log = (28, 4)
        Player_Ping = (28, 6)
        Change_Shaman_Type = (28, 10)
        Send_Code = (28, 16)
        Computer_Info = (28, 17)
        Change_Shaman_Color = (28, 18)
        Slash_Command = (28, 48)
        Verify_Email_Address = (28, 64)
        
        # Cafe packets
        Reload_Cafe = (30, 40)
        Open_Cafe_Topic = (30, 41)
        Create_New_Cafe_Post = (30, 43)
        Create_New_Cafe_Topic = (30, 44)
        Open_Cafe = (30, 45)
        Vote_Cafe_Post = (30, 46)
        Delete_Cafe_Post = (30, 47)
        Delete_All_Cafe_Message = (30, 48)
        
        # Inventory, Consumables and Trades
        Open_Inventory = (31, 1)
        Use_Consumable = (31, 3)
        Equip_Consumable = (31, 4)
        Trade_Invite = (31, 5)
        Cancel_Trade = (31, 6)
        Trade_Add_Consusmable = (31, 8)
        Trade_Result = (31, 9)
        
        # Tribulle
        Parse_Tribulle_Old = (60, 1)
        Parse_Tribulle = (60, 3)
        
        # New game packets
        Open_Dressing = (100, 30)
        Shop_View_Full_Look = (100, 31)
        Change_Shaman_Badge = (100, 79)
        
        # New-New game packets
        Report_Cafe_Post = (149, 4)
        Open_Cafe_Warnings = (149, 5)
        Verify_Cafe_Post = (149, 6)
        Sonar_Information = (149, 8)
        Open_A801_Outfits = (149, 12)
        Add_Outfit = (149, 13)
        Remove_Outfit = (149, 14)
        View_Cafe_Posts = (149, 15)
        Open_A801_Promotions = (149, 16)
        Remove_Sale = (149, 17)
        Add_Sale = (149, 18)
        Ranking = (149, 21)
        Shop_Purchase_Emote = (149, 25)
        Shop_Set_Favorite_Item = (149, 27)

        
        # Language Packets
        Set_Language = (176, 1)
        Language_List = (176, 2)
        Open_Community_Partner = (176, 4)
        PreLogin_Verification = (176, 47)
        
    class send:
        # Chat packets
        Tribe_Message = [6, 8] # Old packet 
        Chat_Message = [6, 9]
        Send_Staff_Chat_Message = [6, 10]
        Recv_Message = [6, 20]
        
        # Player packets
        Titles_List = [8, 14]
        Profile = [8, 16]
        Player_Shop_List = [8, 20]
        Anim_Donation = [8, 50]
    
        # idk 💀
        Banner_Login = [16, 9]
        
        # Shop packets
        Item_Buy = [20, 2]
        Promotion = [20, 3]
        Shop_Info = [20, 15]
        Mouse_Look = [20, 17]
        Shaman_Look = [20, 24]
        Shaman_Items = [20, 27]
        Gift_result = [20, 29]
        Shop_Gift = [20, 30]
        
        # Modopwet
        Modopwet_Open = [25, 2]
        Modopwet_Reports_Community_Count = [25, 3]
        Modopwet_Banned = [25, 5]
        Modopwet_Disconnected = [25, 6]
        Modopwet_Deleted = [25, 7]
        Modopwet_Room_Mods = [25, 8]
        Modopwet_Update_Language = [25, 9]
        Modopwet_Chat_Log = [25, 10]
        Watch_Player = [25, 11]
        Modopwet_Add_Language = [25, 12]
            
        # Login packets
        Gain_Give = [26, 1]
        Player_Identification = [26, 2]
        Correct_Version = [26, 3]
        Login_Result = [26, 12]
        Player_FPS_Info = [26, 13]
        New_Survey = [26, 16]
        Survey_Answer = [26, 17]
        Account_Registration_Captcha = [26, 20]
        Player_IPS_Info = [26, 28]
        Login_Souris = [26, 33]
        
        Time_Stamp = [28, 2]
        Promotion_Popup = [28, 3]
        Message_Langue = [28, 5]
        Ping = [28, 6]
        Shaman_Type = [28, 10]
        Email_Address_Code_Validated = [28, 12]
        Email_Address_Verified = [28, 13]
        Log_Message = [28, 46]
        Request_Info = [28, 50]
        Set_Allow_Email_Address = [28, 62]
        Verify_Email_Popup = [28, 64]
        Server_Restart = [28, 88]
        Connect_To_Server = [28, 98]

        # Cafe packets
        Cafe_Topics_List = [30, 40]
        Open_Cafe_Topic = [30, 41]
        Open_Cafe = [30, 42]
        Cafe_New_Post = [30, 44]
        Delete_Cafe_Message = [30, 47]
        
        # Inventory, Consumables and Trades
        Inventory = [31, 1]
        Update_Inventory_Consumable = [31, 2]
        Use_Inventory_Consumable = [31, 3]
        Trade_Invite = [31, 5]
        Trade_Result = [31, 6]
        Trade_Start = [31, 7]
        Trade_Add_Consumable = [31, 8]
        Trade_Confirm = [31, 9]
        Trade_Close = [31, 10]
        
        # Bulle
        Init_Bulle_Connection = [44, 1]
        
        # Tribulle
        Old_Tribulle = [60, 1]
        Rejoindre_Canal_Publique = [60, 2]
        New_Tribulle = [60, 3]
        Switch_Tribulle = [60, 4]
        
        # Transformice
        Amount_To_Export_Map = [100, 6]
        Open_Dressing = [100, 30]
        Buy_Full_Look = [100, 31]
        Question_Popup = [100, 50]
        New_Consumable = [100, 67]
        Change_Title = [100, 72]
        Image_Login = [100, 99]
        
        # New packets
        Send_Cafe_Warnings = [144, 11]
        MiniBox_New = [144, 17]
        Open_A801_Outfits_Window = [144, 22]
        Load_Shaman_Object_Cache = [144, 27]
        Open_A801_Promotions_Window = [144, 29]
        Load_Fur_Cache = [144, 34]
        Set_News_Popup_Flyer = [144, 35]
        Ranking = [144, 36]
        Emote_Panel = [144, 44]
        
        Start_Sonar = [145, 174]
        End_Sonar = [145, 181]
        
        # Language and community packets
        Set_Language = [176, 5]
        Language_List = [176, 6]
        PreLogin_Verification = [176, 7]
        Community_Partners = [176, 8]
        Open_Link = [176, 9]
        
    class old:
        class recv:
            Load_Map = (14, 6)
            Leave_Map_Editor = (14, 26)
            
            Drawing_Clear = (25, 3)
            Drawing_Init = (25, 4)
            Drawing_Point = (25, 5)
    
        class send:

        
            Load_Map_Result = [14, 8]
            Map_Editor = [14, 14]
            
            Ban_Consideration = [26, 9]
            Player_Ban_Login = [26, 18]