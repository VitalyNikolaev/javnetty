package decoders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import HTTP.HTTP;
import java.util.List;

/**
 * Created by nikolaev on 15.09.16.
 */
public class StringToHttp extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext ctx, String string, List<Object> out) throws Exception {
        HTTP request = new HTTP(string);
//        ctx.channel().write(request);
    }
}