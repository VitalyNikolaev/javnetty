package decoders;

import HTTP.ResponseHeaders;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HeadersEncoder extends MessageToByteEncoder<ResponseHeaders> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseHeaders msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.toString().getBytes());
    }
}
