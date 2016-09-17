package decoders;

import HTTP.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HeadersEncoder extends MessageToByteEncoder<Response> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        System.out.println("ctx = [" + ctx + "], msg = [" + msg + "], out = [" + out + "]");
        out.writeBytes(msg.toString().getBytes());
    }
}
