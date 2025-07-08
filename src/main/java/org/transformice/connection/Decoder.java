package org.transformice.connection;

// Imports
import org.bytearray.ByteArray;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class Decoder extends FrameDecoder {
    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer) {
        if (buffer.readableBytes() < 2) {
            return null;
        }

        // policy file request.
        if(buffer.toString().startsWith("3C 70 6F 6C 69 63 79 2D 66 69 6C 65 2D 72 65 71 75 65 73 74 2F 3E")) { // <policy-file-request/>
            buffer.discardReadBytes();
            channel.write("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>").addListener(ChannelFutureListener.CLOSE);
            return null;
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

        return new ByteArray(buffer.readBytes(length + 1).array());
    }
}