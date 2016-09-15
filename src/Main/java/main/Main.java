package main;
import Server.NettyServerHandler;

import static java.lang.System.exit;


public class Main {
    private static int threadsCount = Runtime.getRuntime().availableProcessors();
    private static int port = 8080;
    private static String rootDir;

    public static void main(String[] args) throws Exception {

        parseCommands(args);
        System.out.println(String.format("Server with %d threads started on http://localhost:%d ",
                threadsCount, port));
        NettyServerHandler server = new NettyServerHandler();
        server.run(threadsCount, port);
    }
    private static void parseCommands(String[] args) throws IllegalArgumentException {
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
//            if (rootDir == null) {
//                System.err.println("ERROR: Root Directory is not defined");
//                exit(1);
//            }
        }
    }

