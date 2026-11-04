package org.transformice.data.objects;

public enum EmailAddressRequestType {
    CANCEL_VERIFICATION(0),
    SEND_VERIFY_EMAIL_ADDRESS_OLD_PACKET(1),
    VERIFICATION_SUCCESSFULL(2),
    INTERNAL_ERROR(4),
    SEND_VERIFY_EMAIL_ADDRESS(5),
    //SEND_VERIFY_USERNAME_AND_EMAIL(6),
    //VERIFY_CODE_TO_CHANGE_PASSWORD(7),
    WRONG_INFORMATION(8),
    //UNK1(9),
    SEND_VERIFY_EMAIL_ADDRESS3(10);

    private final int id;
    EmailAddressRequestType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}