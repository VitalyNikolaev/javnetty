package decoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolaev on 15.09.16.
 */
public class WriteToChannel extends ChannelOutboundHandlerAdapter{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("ctx = [" + ctx + "], msg = [" + msg + "], promise = [" + promise + "]");
        ChannelFuture future = ctx.writeAndFlush(1);
        future.addListener(ChannelFutureListener.CLOSE);

    }
}
