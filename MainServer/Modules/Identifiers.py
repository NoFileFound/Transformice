class Identifiers:
    class recv:
        # Login packets
        Create_Account = (26, 7)
        Login_Account = (26, 8)
        Create_Account_Captcha = (26, 20)
        Login_Time = (26, 26)
        Player_FPS_Info = (26, 13)
        Player_IPS_Info = (26, 28)
        Request_Info = (26, 40)
        
        # Player packets
        Player_Shop_List = (8, 20)
        
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
    
        # Informations
        Correct_Version = (28, 1)
        Game_Log = (28, 4)
        Send_Code = (28, 16)
        Computer_Info = (28, 17)
        
        # Cafe packets
        Reload_Cafe = (30, 40)
        Open_Cafe_Topic = (30, 41)
        Create_New_Cafe_Post = (30, 43)
        Create_New_Cafe_Topic = (30, 44)
        Open_Cafe = (30, 45)
        Vote_Cafe_Post = (30, 46)
        Delete_Cafe_Post = (30, 47)
        Delete_All_Cafe_Message = (30, 48)
        
        # New game packets
        Open_Dressing = (100, 30)
        
        # New-New game packets
        Report_Cafe_Post = (149, 4)
        Open_Cafe_Warnings = (149, 5)
        Verify_Cafe_Post = (149, 6)
        Open_A801_Outfits = (149, 12)
        Add_Outfit = (149, 13)
        Remove_Outfit = (149, 14)
        View_Cafe_Posts = (149, 15)
        Open_A801_Promotions = (149, 16)
        Remove_Sale = (149, 17)
        Add_Sale = (149, 18)
        Shop_Purchase_Emote = (149, 25)
        Shop_Set_Favorite_Item = (149, 27)

        
        # Language Packets
        Set_Language = (176, 1)
        Language_List = (176, 2)
        Open_Community_Partner = (176, 4)
        PreLogin_Verification = (176, 47)
        
        
    class send:        
        Recv_Message = [6, 20]
        
    
        Banner_Login = [16, 9]
        
        # Player packets
        Player_Shop_List = [8, 20]
        
        # Shop packets
        Item_Buy = [20, 2]
        Promotion = [20, 3]
        Shop_Info = [20, 15]
        Shaman_Look = [20, 24]
        Shaman_Items = [20, 27]
        Gift_result = [20, 29]
        Shop_Gift = [20, 30]
            
        # Login packets
        Player_Identification = [26, 2]
        Correct_Version = [26, 3]
        Login_Result = [26, 12]
        Player_FPS_Info = [26, 13]
        Account_Registration_Captcha = [26, 20]
        Player_IPS_Info = [26, 28]
        Login_Souris = [26, 33]
        
        Time_Stamp = [28, 2]
        Promotion_Popup = [28, 3]
        Message_Langue = [28, 5]
        Email_Address_Code_Validated = [28, 12]
        Email_Address_Verified = [28, 13]
        Request_Info = [28, 50]
        
        # Cafe packets
        Cafe_Topics_List = [30, 40]
        Open_Cafe_Topic = [30, 41]
        Cafe_New_Post = [30, 44]
        Open_Cafe = [30, 42]
        Delete_Cafe_Message = [30, 47]
        
        # Bulle
        Init_Bulle_Connection = [44, 1]
        
        # Tribulle
        Old_Tribulle = [60, 1]
        New_Tribulle = [60, 3]
        Switch_Tribulle = [60, 4]
        
        
        Open_Dressing = [100, 30]
        Image_Login = [100, 99]
        
        
        
        Send_Cafe_Warnings = [144, 11]
        Open_A801_Outfits_Window = [144, 22]
        Open_A801_Promotions_Window = [144, 29]
        Emote_Panel = [144, 44]
        
        # Language and community packets
        Set_Language = [176, 5]
        Language_List = [176, 6]
        PreLogin_Verification = [176, 7]
        Community_Partners = [176, 8]
        Open_Link = [176, 9]