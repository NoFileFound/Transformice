package org.transformice.packets.send.room;

// Imports
import org.psframework.network.PSSendPacket;
import org.psframework.network.PSSendPacketException;
import org.psframework.utils.PSByteArray;
import org.transformice.packets.PacketOpcodes;

public final class PacketSendTutorialStep extends PSSendPacket {
    private final int tutorialStepId;

    /**
     * Creates a new packet of SEND_TUTORIAL_STEP.
     * @param tutorialStepId The tutorial step id.
     */
    public PacketSendTutorialStep(int tutorialStepId) {
        super(PacketOpcodes.SEND_TUTORIAL_STEP);
        this.tutorialStepId = tutorialStepId;
    }


    @Override
    public PSByteArray build() throws PSSendPacketException {
        return new PSByteArray().writeByte(this.tutorialStepId);
    }
}