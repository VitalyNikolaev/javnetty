package Channel;

/**
 * Created by nikolaev on 14.09.16.
 */

import HTTP.HTTP;
import decoders.StringFromBytes;
import decoders.StringToHttp;
import decoders.WriteToChannel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;



public class Channel extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringFromBytes());
        pipeline.addLast(new StringToHttp());
    }
}