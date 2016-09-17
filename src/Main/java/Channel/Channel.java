package Channel;

/**
 * Created by nikolaev on 14.09.16.
 */

import decoders.HeadersEncoder;
import decoders.SendResponse;
import decoders.StringToHttp;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;


public class Channel extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringToHttp());
        pipeline.addLast(new HeadersEncoder());
        pipeline.addLast(new SendResponse());

    }
}