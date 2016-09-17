package decoders;

import HTTP.HTTP;
import HTTP.Request;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import static HTTP.Response.HEADER_CONTENT_LENGTH;
import static HTTP.Response.HEADER_CONTENT_TYPE;
import static HTTP.Response.OK;
import HTTP.Response;

/**
 * Created by nikolaev on 17.09.16.
 */
public class SendResponse extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        System.out.println("ctx = [" + ctx + "], request = [" + request + "]");

        writeResponse(ctx, request);
    }

    private void writeResponse(ChannelHandlerContext ctx, Request request) throws IOException {
        System.out.println("ctx = [" + ctx + "], request = [" + request + "]");
        Response httpHeader = new Response(OK);
        ChannelFuture future;
        future = ctx.writeAndFlush(httpHeader);
        future.addListener(ChannelFutureListener.CLOSE);

    }

}
