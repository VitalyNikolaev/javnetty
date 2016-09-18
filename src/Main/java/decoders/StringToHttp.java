package decoders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import HTTP.Request;
import java.util.List;

/**
 * Created by nikolaev on 15.09.16.
 */
public class StringToHttp extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        Request request = new Request(msg);
//        try {
//            request = Request.fromString(msg);
//        } catch (Exception ignored) {
//            request = new Request(false);
//        }
        out.add(request);
    }
}