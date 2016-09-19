package decoders;

import HTTP.ContentType;
import HTTP.Request;
import HTTP.ResponseHeaders;
import HTTP.TemplateResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import static HTTP.ResponseHeaders.CONTENT_LENGTH;
import static HTTP.ResponseHeaders.CONTENT_TYPE;
import static Server.Config.getIndex;
import static Server.Config.getRootDir;


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

        File file = new File(getRootDir() + path);
        if (!file.exists()) {
            sendError(ctx, 404);
            return;
        }

        if (file.isDirectory()) {
            File indexFile = new File(file.getPath() + "/" + getIndex());
            if (indexFile.exists()) {
                file = indexFile;
            } else {
                sendError(ctx, 403);
                return;
            }
        }
        writeFileResponse(ctx, file, request);
    }

    private void writeFileResponse(ChannelHandlerContext ctx, File file, Request request) throws IOException {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException ignore) {
            sendError(ctx, 404);
            return;
        }

        long fileLength = randomAccessFile.length();

        ResponseHeaders response = new ResponseHeaders(200);
        response.headers.put(CONTENT_LENGTH, String.valueOf(fileLength));
        String extension = FilenameUtils.getExtension(file.getPath());
        response.headers.put(CONTENT_TYPE, ContentType.getContentType(extension));

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
