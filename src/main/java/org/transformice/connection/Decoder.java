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

        int len = buffer.readableBytes();
        ByteArray receivedBytes = new ByteArray(len);
        receivedBytes.writeBytes(buffer.readBytes(len).array());
        if(receivedBytes.toString().startsWith("3C 70 6F 6C 69 63 79 2D 66 69 6C 65 2D 72 65 71 75 65 73 74 2F 3E")) { // <policy-file-request/>
            buffer.discardReadBytes();
            channel.write("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>").addListener(ChannelFutureListener.CLOSE);
            return null;
        }

        return receivedBytes;
    }
}