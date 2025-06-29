package org.transformice.connection;

// Imports
import java.nio.charset.StandardCharsets;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class Encoder extends SimpleChannelHandler {
    @Override
    public void writeRequested(ChannelHandlerContext context, MessageEvent e) {
        if (e.getMessage() instanceof String) {
            Channels.write(context, e.getFuture(), ChannelBuffers.copiedBuffer((String)e.getMessage(), StandardCharsets.UTF_8));
        } else {
            Channels.write(context, e.getFuture(), e.getMessage());
        }
    }
}