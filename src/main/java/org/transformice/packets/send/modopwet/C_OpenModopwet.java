package org.transformice.packets.send.modopwet;

// Imports
import static org.transformice.utils.Utils.getUnixTime;
import java.util.List;
import org.bytearray.ByteArray;
import org.transformice.database.collections.Sanction;
import org.transformice.database.embeds.ReportReporter;
import org.transformice.packets.SendPacket;

public final class C_OpenModopwet implements SendPacket {
    private final ByteArray byteArray = new ByteArray();

    @SuppressWarnings("unchecked")
    public C_OpenModopwet(List<Object[]> reports) {
        this.byteArray.writeByte(reports.size());
        for (Object[] report : reports) {
            List<String> playerRoomNameMods = (List<String>) report[4];
            List<ReportReporter> playerReporters = (List<ReportReporter>) report[6];
            Sanction myMuteSanction = (Sanction) report[7];

            this.byteArray.writeByte(playerReporters.getLast().getReportType()); // category
            this.byteArray.writeUnsignedShort((int) report[0]);
            this.byteArray.writeString((String) report[2]);
            this.byteArray.writeString((String) report[1]);
            this.byteArray.writeString((String) report[3]);
            this.byteArray.writeByte(playerRoomNameMods.size());
            for (String playerRoomNameMod : playerRoomNameMods) {
                this.byteArray.writeString(playerRoomNameMod);
            }
            this.byteArray.writeInt((int) report[5]);
            this.byteArray.writeByte(playerReporters.size());
            for (ReportReporter reporter : playerReporters) {
                this.byteArray.writeString(reporter.getPlayerName());
                this.byteArray.writeShort(reporter.getPlayerKarma());
                this.byteArray.writeString(reporter.getReportComment());
                this.byteArray.writeByte(reporter.getReportType());
                this.byteArray.writeShort((short) ((getUnixTime() - reporter.getReportAge()) / 60));
            }
            this.byteArray.writeBoolean(myMuteSanction != null);
            if (myMuteSanction != null) {
                this.byteArray.writeString(myMuteSanction.getAuthor());
                this.byteArray.writeShort((short)(((myMuteSanction.getExpirationDate() - getUnixTime()) / 3600) + 1));
                this.byteArray.writeString(myMuteSanction.getReason());
            }
        }
    }

    @Override
    public int getC() {
        return 25;
    }

    @Override
    public int getCC() {
        return 2;
    }

    @Override
    public byte[] getPacket() {
        return this.byteArray.toByteArray();
    }
}