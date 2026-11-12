package org.transformice.data.objects;

public final class RoomPlayerInfo {
    public String playerName;
    public int sessionId;
    public boolean isShaman;
    public PlayerState playerState;
    public short playerScore;
    public int cheeseCount;
    public PlayerTitleInfo titleInfo;
    public PlayerGender playerGender;
    public String playerAvatarId;
    public String playerMouseLook;
    public boolean unk1 = false;
    public int playerMouseColor;
    public int playerShamanColor;
    public int playerStaffNickNameColor;
    public int playerNickNameColor;
    public int playerContextId;

    /**
     * Creates a new player information in the room.
     * @param playerName The player's nickname.
     * @param sessionId The player's session id.
     * @param isShaman True if the player is a shaman in the room.
     * @param playerState The player's state in the room. (Alive, Dead or Invisible).
     * @param playerScore The player's score.
     * @param cheeseCount The player's cheese count.
     * @param titleInfo The player's title information.
     * @param genderType The player's gender.
     * @param avatarId The player's avatar id.
     * @param mouseLook The player's clothes (mouse look).
     * @param mouseColor The player's mouse color.
     * @param shamanColor The player's shaman color.
     * @param staffNickNameColor The player's nickname color (For staff only).
     * @param nickNameColor The player's nickname color.
     * @param contextId The player's context id.
     */
    public RoomPlayerInfo(String playerName, int sessionId, boolean isShaman, PlayerState playerState, short playerScore, int cheeseCount, PlayerTitleInfo titleInfo, PlayerGender genderType, String avatarId, String mouseLook, int mouseColor, int shamanColor, int staffNickNameColor, int nickNameColor, int contextId) {
        this.playerName = playerName;
        this.sessionId = sessionId;
        this.isShaman = isShaman;
        this.playerState = playerState;
        this.playerScore = playerScore;
        this.cheeseCount = cheeseCount;
        this.titleInfo = titleInfo;
        this.playerGender = genderType;
        this.playerAvatarId = avatarId;
        this.playerMouseLook = mouseLook;
        this.playerMouseColor = mouseColor;
        this.playerShamanColor = shamanColor;
        this.playerStaffNickNameColor = staffNickNameColor;
        this.playerNickNameColor = nickNameColor;
        this.playerContextId = contextId;
    }
}