package org.transformice.data.objects;

public final class CheesePosition {
    private final int posX;
    private final int posY;

    /**
     * Creates a new cheese position.
     * @param posX The cheese's position on X.
     * @param posY The cheese's position on Y.
     */
    public CheesePosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets the cheese's position on X-axis.
     * @return The position on X-axis.
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     * Gets the cheese on the Y-axis.
     * @return The cheese on the Y-axis.
     */
    public int getPosY() {
        return this.posY;
    }
}