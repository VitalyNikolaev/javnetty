package decoders;

import HTTP.Request;
import HTTP.TemplateResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import HTTP.ResponseHeaders;

import static HTTP.ResponseHeaders.CONTENT_LENGTH;
import static main.Main.rootDir;
import static main.Main.index;

/**
 * Created by nikolaev on 17.09.16.
 */
public class SendResponse extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {

        if (!request.isValidRequest()) {
            sendError(ctx, 400);
            return;
        }

        String path = request.getPath();
        if (path == null) {
            sendError(ctx, 403);
            return;
        }

        File file = new File(rootDir + path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, 404);
            return;
        }

        if (file.isDirectory()) {
            File indexFile = new File(file.getPath() + "/" + index);
            if (indexFile.exists()) {
                file = indexFile;
            } else {
                final String template = TemplateResponse.generatePage(200, "Server running, but no index.html");
                ResponseHeaders response = new ResponseHeaders(200);
                response.headers.put(CONTENT_LENGTH, String.valueOf(template.length()));
                ctx.write(template);
                ctx.writeAndFlush(Unpooled.wrappedBuffer(template.getBytes())).addListener(ChannelFutureListener.CLOSE);
                return;
            }
        }
        writeFileResponse(ctx, file, request);
    }

    private void writeFileResponse(ChannelHandlerContext ctx, File file, Request request) throws IOException {
        RandomAccessFile randomAccessFile = null;
        ResponseHeaders httpHeader = new ResponseHeaders(404);
        ChannelFuture future;
        future = ctx.writeAndFlush(httpHeader);
        future.addListener(ChannelFutureListener.CLOSE);
    }
    private static void sendError(ChannelHandlerContext ctx, int error) {
        final String errorBody = TemplateResponse.generatePage(error, ResponseHeaders.statusDescriptions.get(error));

        ResponseHeaders response = new ResponseHeaders(error);
        response.headers.put(CONTENT_LENGTH, String.valueOf(errorBody.length()));
        ctx.write(response);
        ctx.writeAndFlush(Unpooled.wrappedBuffer(errorBody.getBytes())).addListener(ChannelFutureListener.CLOSE);
    }

}
