package org.transformice.packets.recv.login;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bytearray.ByteArray;
import org.transformice.Application;
import org.transformice.Client;
import org.transformice.libraries.SrcRandom;
import org.transformice.packets.RecvPacket;

// Packets
import org.transformice.packets.send.login.C_CaptchaRequest;

@SuppressWarnings("unused")
public final class S_CaptchaRequest implements RecvPacket {
    @Override
    public void handle(Client client, int fingerPrint, ByteArray data) {
        StringBuilder CC = new StringBuilder();
        List<String> keys = new ArrayList<>(Application.getCaptchaInfo().keySet());
        for (int i = 0; i < 4; i++) {
            CC.append(keys.get(SrcRandom.RandomNumber(0, keys.size()-1)));
        }

        String[] words = CC.toString().split("");
        int px = 0, py = 1;
        List<String> lines = new ArrayList<>();

        for (int count = 1; count <= 16; count++) {
            int wc = 1;
            List<String> values = new ArrayList<>();

            for (String word : words) {
                Map<String, String> wsMap = Application.getCaptchaInfo().get(word);
                if (count > wsMap.size()) {
                    count = wsMap.size();
                }
                String ws = wsMap.get(String.valueOf(count));
                List<String> parts = Arrays.asList(ws.split(","));
                if (wc > 1) {
                    values.addAll(parts.subList(1, parts.size()));
                } else {
                    values.addAll(parts);
                }
                wc++;
            }

            lines.add(String.join(",", values));
            if (px < values.size()) {
                px = values.size();
            }
            py++;
        }
        px += 2;

        client.registerCaptcha = CC.toString();
        client.sendPacket(new C_CaptchaRequest(px, py, lines));
    }

    @Override
    public int getC() {
        return 26;
    }

    @Override
    public int getCC() {
        return 20;
    }
}