package main;

import Server.Config;
import Server.NettyServerHandler;


public class Main {
    public static void main(String[] args) throws Exception {
        Config.initConfig(args);
        System.out.println(String.format("Server with %d threads and rootDir %s started on http://localhost:%d ",
                Config.getThreadsCount(), Config.getRootDir(), Config.getPort()));
        NettyServerHandler server = new NettyServerHandler();
        server.run(Config.getThreadsCount(), Config.getPort());
    }

}

