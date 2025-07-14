package org.transformice.connection;

// Imports
import java.util.Arrays;
import org.bytearray.ByteArray;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.transformice.Application;

public class Decoder extends FrameDecoder {
    private static final byte[] POLICY_REQUEST = "<policy-file-request/>".getBytes();

    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer) {
        if (buffer.readableBytes() < 2) {
            return null;
        }

        if (buffer.readableBytes() >= POLICY_REQUEST.length + 1) {
            buffer.markReaderIndex();
            byte[] incoming = new byte[POLICY_REQUEST.length + 1];
            buffer.readBytes(incoming);
            if (Arrays.equals(Arrays.copyOf(incoming, POLICY_REQUEST.length), POLICY_REQUEST) && incoming[POLICY_REQUEST.length] == 0) {
                Application.getLogger().warn("Policy file request.");
                channel.write("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>").addListener(ChannelFutureListener.CLOSE);
                return null;
            }

            buffer.resetReaderIndex();
        }

        int length = 0;
        int shift = 0;
        while (true) {
            if (!buffer.readable()) {
                buffer.resetReaderIndex();
                return null;
            }

            byte b = buffer.readByte();
            length |= (b & 0x7F) << shift;
            if ((b & 0x80) == 0) {
                break;
            }

            shift += 7;
            if (shift > 28) {
                throw new RuntimeException("VLQ length field too long");
            }
        }

        if (buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return null;
        }

        byte[] packetData = new byte[length + 1];
        buffer.readBytes(packetData);
        return new ByteArray(packetData);
    }
}