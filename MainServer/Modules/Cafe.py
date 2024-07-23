#coding: utf-8
import datetime
import pymongo

# Modules
from Modules.Identifiers import Identifiers
from Modules.ByteArray import ByteArray

# Utils
from Utils.Time import Time

class Cafe:
    def __init__(self, client):
        self.client = client
        self.server = client.server
        self.canUseCafe = False
        self.isModerator = False

        # Cafe permissions
        
    def checkPerm(self) -> bool:
        if self.server.isDebug:
            return True
            
        if self.client.isGuest:
            return False
            
        if self.client.privLevel >= 2:
            return True
            
        elif len(self.client.privRoles) > 0:
            return True
            
        return (self.client.cheeseCount > 1000 and self.client.playerTime > 3600 * 30)
        
    def checkModeratorPerm(self) -> bool:
        return self.client.privLevel >= 8 or self.client.isPrivMod
        
    def getTopicNormalPosts(self, topicID) -> int:
        info = self.server.cursor['cafeposts'].count_documents({"TopicID":topicID, "Type":0})
        return info
        
    def makeCafePermissions(self):
        self.canUseCafe = self.checkPerm()
        self.isModerator = self.checkModeratorPerm()
        
        # Cafe operations
        
    def createNewCafePost(self, topicID, message):
        if not self.canUseCafe:
            return
    
        if not self.server.checkMessage(message):
            self.server.cursor['cafeposts'].insert_one({
                    "PostID" :                  self.server.lastCafePostID,
                    "TopicID" :                 topicID,
                    "Author" :                  self.client.playerName,
                    "Moderator" :               "",
                    "Message" :                 message,
                    "Date":                     Time.getTime(),
                    "Points" :                  0,
                    "Votes":                    "",
                    "Type":                     0
            })
            self.server.lastCafePostID += 1
            self.server.cursor['cafetopics'].update_one({"TopicID":topicID}, {"$set": { 'LastPostName': self.client.playerName}, "$inc" : {'Posts': 1}})
            commentCount = self.server.cursor['cafeposts'].count_documents({'TopicID': topicID})
            self.openCafeTopic(topicID)
            for player in self.server.players.copy().values():
                if player.isCafeOpened:
                    player.sendPacket(Identifiers.send.Cafe_New_Post, ByteArray().writeInt(topicID).writeUTF(self.client.playerName).writeInt(commentCount).toByteArray())
        
    def createNewCafeTopic(self, title, message):
        if not self.canUseCafe:
            return
            
        if not self.server.checkMessage(title):
            self.server.cursor['cafetopics'].insert_one({
                    "TopicID" :                 self.server.lastCafeTopicID,
                    "Title" :                   title,
                    "Author" :                  self.client.playerName,
                    "Posts" :                   0,
                    "LastPostName" :            "",
                    "Date":                     Time.getTime(),
                    "Langue":                   self.client.playerLangue
            })
            self.createNewCafePost(self.server.lastCafeTopicID, message)
        self.server.lastCafeTopicID += 1
        self.loadCafeMode()
        
    def deleteCafePost(self, postID):
        if not self.isModerator:
            return
            
        topicID = self.server.cursor['cafeposts'].find_one({'PostID': postID})["TopicID"]
        Posts = self.server.cursor['cafetopics'].find_one({'TopicID': topicID})["Posts"]
        
        self.server.cursor['cafeposts'].delete_one({'PostID': postID})
        self.client.sendPacket(Identifiers.send.Delete_Cafe_Message, ByteArray().writeInt(topicID).writeInt(postID).toByteArray())
        Posts -= 1
        if Posts == 0:
            self.server.cursor['cafetopics'].delete_one({"TopicID":topicID})
            self.loadCafeMode()
        else:
            self.server.cursor['cafetopics'].update_one({"TopicID":topicID}, {"$set": { 'Posts': Posts}})
            self.openCafeTopic(topicID)
        
    def deleteAllCafePosts(self, topicID, playerName):
        if not self.isModerator:
            return
            
        deleted_posts = 0
        Posts = self.server.cursor['cafetopics'].find_one({'TopicID': topicID})["Posts"]
        info = self.server.cursor['cafeposts'].find({'TopicID': topicID, 'Author':playerName})
        for i in info:
            deleted_posts += 1
        Posts -= deleted_posts
        self.server.cursor['cafeposts'].delete_many({'TopicID': topicID, 'Author':playerName})
        if Posts == 0:
            self.server.cursor['cafetopics'].delete_one({"TopicID":topicID})
            self.loadCafeMode()
        else:
            self.server.cursor['cafetopics'].update_one({"TopicID":topicID}, {"$set": { 'Posts': Posts}})
            self.openCafeTopic(topicID)
        
    def loadCafeMode(self):
        if not self.canUseCafe:
            self.client.sendLangueMessage("", "<ROSE>$PasAutoriseParlerSurServeur")
            
        self.client.sendPacket(Identifiers.send.Open_Cafe, ByteArray().writeBoolean(self.canUseCafe).toByteArray())
        packet = ByteArray().writeBoolean(self.canUseCafe).writeBoolean(self.isModerator)
        cursor = self.server.cursor["cafetopics"].find().sort("Date", pymongo.DESCENDING).limit(20)
        for topic in cursor:
            if topic["Langue"] == self.client.playerLangue or self.client.privLevel >= 9:
                packet.writeInt(topic["TopicID"]).writeUTF(topic["Title"]).writeInt(self.server.getPlayerID(topic["Author"])).writeInt(topic["Posts"] if self.isModerator else self.getTopicNormalPosts(topic["TopicID"])).writeUTF(topic["LastPostName"]).writeInt(Time.getSecondsDiff(topic["Date"]))
        
        self.client.sendPacket(Identifiers.send.Cafe_Topics_List, packet.toByteArray())
        self.sendCafeWarnings()
        
    def openCafeTopic(self, topicID):
        if any(post['topicID'] == topicID for post in self.server.reportedCafePosts):
            needTakeAction = True
        else:
            needTakeAction = False
        
        packet = ByteArray().writeBoolean(True).writeInt(topicID).writeBoolean(needTakeAction and self.isModerator).writeBoolean(self.canUseCafe)
        cursor = self.server.cursor["cafeposts"].find({'TopicID':topicID}).sort("PostID", pymongo.ASCENDING)
        for post in cursor:
            if post["Type"] == 2 and not self.isModerator:
                continue
    
            canVote = True
            if not self.canUseCafe:
                canVote = False
            elif str(self.client.playerID) not in post["Votes"].split(","):
                canVote = False

            packet.writeInt(post["PostID"]).writeInt(self.server.getPlayerID(post["Author"])).writeInt(Time.getSecondsDiff(post["Date"])).writeUTF(post["Author"]).writeUTF(post["Message"]).writeBoolean(canVote).writeShort(post["Points"]).writeUTF(post["Moderator"] if self.isModerator else "").writeByte(post["Type"] if self.isModerator else 0)
        self.client.sendPacket(Identifiers.send.Open_Cafe_Topic, packet.toByteArray())

    def reportCafePost(self, topicID, postID):
        topicName = self.server.cursor['cafetopics'].find_one({'TopicID': topicID})["Title"]
        postMessage = self.server.cursor['cafeposts'].find_one({'PostID': postID})["Message"]
        isModerated = self.server.cursor['cafeposts'].find_one({'PostID': postID})["Moderator"]
        if isModerated == "":
            self.server.sendStaffMessage(f"A new cafe report was made by {self.client.playerName} on topic {topicName}. Message: {postMessage}.", "PrivMod|Mod|Admin")
            self.server.reportedCafePosts.append({"topicID": topicID, "postID": postID})
            self.server.cursor['cafeposts'].update_one({"TopicID":topicID, "PostID":postID}, {"$set": {'Type':1}})
            if self.client.isCafeOpened:
                self.openCafeTopic(topicID)

    def verifyCafePost(self, topicID, isDelete):
        if not self.isModerator:
            return
            
        postID = next((post['postID'] for post in self.server.reportedCafePosts if post['topicID'] == topicID), -1)
        if postID > 0:
            self.server.cursor['cafeposts'].update_one({"TopicID":topicID, "PostID":postID}, {"$set": {'Type':2 if isDelete else 0, 'Moderator':self.client.playerName}})
        self.server.reportedCafePosts = [post for post in self.server.reportedCafePosts if post['topicID'] != topicID]
        self.openCafeTopic(topicID)

    def viewCafePosts(self, playerName):
        if not self.isModerator:
            return
    
        cursor = self.server.cursor['cafeposts'].find({"Author":playerName})
        topicName = self.server.cursor['cafetopics'].find_one({"TopicID":cursor[0]["TopicID"]})["Title"]
        message = ""
        for post in cursor:
            status = ""
            msg = post['Message'].replace('\r', '')
            message += f"Message: {msg} | "
            message += f"Topic: <J>{topicName}</J> | "
            if post["Moderator"] != "":
                message += f"Moderated by: <ROSE>{post['Moderator']}</ROSE> | "
            message += f"Date: <BL>{datetime.datetime.fromtimestamp(post['Date']).strftime('%m/%d/%Y %H:%M:%S')}</BL> | "
            message += f"Status: {'Active' if post['Type'] == 0 else 'Moderated'}"
            message += "\n"
        
        self.client.sendPacket(Identifiers.send.MiniBox_New, ByteArray().writeShort(600).writeUTF("View Posts of player " + playerName).writeUTF(message).toByteArray())

    def voteCafePost(self, topicID, postID, mode):
        if not self.canUseCafe:
            return
            
        info = self.server.cursor['cafeposts'].find_one({"TopicID":topicID})
        if info:
            points = info["Points"]
            votes = info["Votes"]
            Author = info["Author"]
        
            if self.client.playerName != Author:
                if not str(self.client.playerID) in votes:
                    votes += str(self.client.playerID) if votes == "" else "," + str(self.client.playerID)
                    if mode:
                        points += 1
                    else:
                        points -= 1
                self.server.cursor['cafeposts'].update_one({"TopicID":topicID, "PostID":postID}, {"$set": {'Votes': votes, 'Points':points}})
                self.openCafeTopic(topicID)

        # Cafe packets
    def sendCafeWarnings(self):
        if not self.canUseCafe:
            return
            
        count = self.server.cursor['cafeposts'].count_documents({'Type':2, 'Author':self.client.playerName})
        self.client.sendPacket(Identifiers.send.Send_Cafe_Warnings, ByteArray().writeShort(count).toByteArray())
        