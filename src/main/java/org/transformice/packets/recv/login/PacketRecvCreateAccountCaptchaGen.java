package org.transformice.packets.recv.login;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.psframework.network.PSPacket;
import org.psframework.network.PSReceivePacket;
import org.psframework.network.PSReceivePacketException;
import org.psframework.utils.PSRandom;
import org.transformice.GameServer;
import org.transformice.data.CaptchaInfo;
import org.transformice.network.NetByteArray;
import org.transformice.network.NetSession;
import org.transformice.packets.PacketOpcodes;

// Packets
import org.transformice.packets.send.login.PacketSendCreateAccountCaptchaGen;

@SuppressWarnings("unused")
@PSPacket(id = PacketOpcodes.RECV_CREATE_ACCOUNT_CAPTCHA_GEN)
public final class PacketRecvCreateAccountCaptchaGen extends PSReceivePacket<GameServer, NetSession, NetByteArray> {
    @Override
    public void handle(GameServer gameServer, NetSession client, NetByteArray psHeader, NetByteArray psData) throws PSReceivePacketException {
        Map<String, Map<String, String>> captchaInfo = CaptchaInfo.getCaptchaInfo();
        List<String> keys = new ArrayList<>(captchaInfo.keySet());
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 4; i++)
            captcha.append(keys.get(PSRandom.nextInt(0, keys.size() - 1)));

        List<String> lines = new ArrayList<>();
        int px = 0, py = 0;
        for (int count = 1; count <= 16; count++) {
            List<String> values = new ArrayList<>();
            int wc = 0;
            for (int i = 0; i < captcha.length(); i++) {
                String word = String.valueOf(captcha.charAt(i));
                Map<String, String> wsMap = captchaInfo.get(word);
                if (count > wsMap.size())
                    break;

                String ws = wsMap.get(String.valueOf(count));
                if (ws == null)
                    break;

                String[] parts = ws.split(",");
                values.addAll(Arrays.asList(parts).subList(wc == 0 ? 0 : 1, parts.length));
                wc++;
            }

            if (wc != captcha.length())
                break;

            lines.add(String.join(",", values));
            px = Math.max(px, values.size());
            py++;
        }

        client.setRegisterCaptchaCode(captcha.toString());
        client.sendData(new PacketSendCreateAccountCaptchaGen(px + 2, py + 1, lines));
    }
}