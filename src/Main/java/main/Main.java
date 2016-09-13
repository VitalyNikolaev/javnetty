package main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import Channel.Channel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import static java.lang.System.exit;


public class Main {
    private static int threadsCount = Runtime.getRuntime().availableProcessors();
    private static int port = 8080;
    private static String rootDir;
    private static final int BACKLOG_OPTION = 512;
    private static final int BUF_OPTION = 32000;

    public static void main(String[] args) throws Exception {
        final setUpFromCommandLine settings = new setUpFromCommandLine();
        settings.parseCommands(args);
        System.out.println(String.format("Server with %d threads started on http://localhost:%d ",
                threadsCount, port));

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(threadsCount);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new Channel()).option(ChannelOption.SO_BACKLOG, BACKLOG_OPTION)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_SNDBUF, BUF_OPTION)
                    .childOption(ChannelOption.SO_RCVBUF, BUF_OPTION)
                    .childOption(ChannelOption.SO_REUSEADDR, true);


            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            System.out.println("exiting");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


    private static class setUpFromCommandLine {
        void parseCommands(String[] args) throws IllegalArgumentException {
            for (int i = 0; i < args.length; i += 2) {
                switch (args[i]) {
                    case "-r":
                        rootDir = (args[i + 1]);
                        break;
                    case "-c":
                        threadsCount = (Integer.parseInt(args[i + 1]));
                        break;
                    case "-p":
                        port = (Integer.parseInt(args[i + 1]));
                        break;
                }
            }
            if (rootDir == null) {
                System.err.println("ERROR: Root Directory is not defined");
                exit(1);
            }
        }
    }
}
