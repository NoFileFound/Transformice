package org.transformice.data.objects;

public final class MonsterInfo {
    public int monsterId;
    public int posX;
    public int posY;

    /**
     * Creates a new monster information.
     * @param monsterId The monster's id.
     * @param posX The monster's X-position.
     * @param posY The monster's Y-position.
     */
    public MonsterInfo(int monsterId, int posX, int posY) {
        this.monsterId = monsterId;
        this.posX = posX;
        this.posY = posY;
    }
}