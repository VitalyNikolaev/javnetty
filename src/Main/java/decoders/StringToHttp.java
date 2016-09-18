package decoders;

import HTTP.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by nikolaev on 15.09.16.
 */
public class StringToHttp extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        Request request = new Request(msg);
        out.add(request);
    }
}