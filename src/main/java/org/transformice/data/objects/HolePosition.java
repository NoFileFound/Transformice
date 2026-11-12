package org.transformice.data.objects;

public final class HolePosition {
    private final int holeType;
    private final int posX;
    private final int posY;

    /**
     * Creates a new hole position.
     * @param holeType The hole's type.
     * @param posX The hole's position on X.
     * @param posY The hole's position on Y.
     */
    public HolePosition(int holeType, int posX, int posY) {
        this.holeType = holeType;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets the hole's type.
     * @return The hole's type.
     */
    public int getHoleType() {
        return this.holeType;
    }

    /**
     * Gets the position on X-axis.
     * @return The position on X-axis.
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     * Gets the position on Y-axis.
     * @return The position on Y-axis.
     */
    public int getPosY() {
        return this.posY;
    }
}