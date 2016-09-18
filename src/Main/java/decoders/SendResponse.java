package decoders;

import HTTP.Request;
import HTTP.ResponseHeaders;
import HTTP.TemplateResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static HTTP.ResponseHeaders.CONTENT_LENGTH;
import static HTTP.ResponseHeaders.CONTENT_TYPE;
import static main.Main.index;
import static main.Main.rootDir;

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
        if (!file.exists()) {
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
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException ignore) {
            sendError(ctx, 404);
            return;
        }

        long fileLength = randomAccessFile.length();

        ResponseHeaders response = new ResponseHeaders(200);
        response.headers.put(CONTENT_LENGTH, String.valueOf(fileLength));

        Path filePath = Paths.get(file.getPath());
        String contentType =  Files.probeContentType(filePath);
        response.headers.put(CONTENT_TYPE, contentType);

        ChannelFuture future;
        if (request.getMethod().equals("HEAD")) {
            future = ctx.writeAndFlush(response);
        } else {
            ctx.write(response);
            future = ctx.writeAndFlush(new DefaultFileRegion(randomAccessFile.getChannel(), 0, fileLength));
        }
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
