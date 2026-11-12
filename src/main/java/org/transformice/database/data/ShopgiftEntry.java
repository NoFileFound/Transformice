package org.transformice.database.data;

// Imports
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity(value = "shopgifts")
public class ShopgiftEntry {
    @Id private int id;
    private String receiver;
    private String sender;
    private String senderPlayerLook;
    private boolean isShamanItem;
    private int itemId;
    private String message;
    public boolean hasRespond;
    public boolean hasOpened;
    public String senderMessage;

    /**
     * Creates a new shop gift.
     */
    public ShopgiftEntry() {
        // Required by Morphia.
    }

    /**
     * Creates a new shop gift.
     * @param shopGiftId The shop gift's id.
     */
    public ShopgiftEntry(final int shopGiftId, final String receiver, final String sender, final String senderPlayerLook, final boolean isShamanItem, final int itemId, final String message) {
        this();
        this.id = shopGiftId;
        this.receiver = receiver;
        this.sender = sender;
        this.senderPlayerLook = senderPlayerLook;
        this.isShamanItem = isShamanItem;
        this.itemId = itemId;
        this.message = message;
    }

    /**
     * Gets the shop gift's id.
     * @return The shop gift's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the receiver.
     * @return The receiver.
     */
    public String getReceiver() {
        return this.receiver;
    }

    /**
     * Gets the sender.
     * @return The sender.
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Gets the sender's player look.
     * @return The sender's player look.
     */
    public String getSenderPlayerLook() {
        return this.senderPlayerLook;
    }

    /**
     * Checks if the item is regular or shaman one.
     * @return True if its shaman item or else regular.
     */
    public boolean getIsShamanItem() {
        return this.isShamanItem;
    }

    /**
     * Gets the item's id.
     * @return The item's id.
     */
    public int getItemId() {
        return this.itemId;
    }

    /**
     * Gets the gift's message.
     * @return The gift's message.
     */
    public String getMessage() {
        return this.message;
    }
}