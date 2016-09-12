package main;
import Server.Server;

public class Main {
    private static int threadsCount = Runtime.getRuntime().availableProcessors();
    private static int port = 8080;
    private static String rootDir;

    public static void main(String[] args) throws Exception {
        final setUpFromCommandLine settings = new setUpFromCommandLine();
        settings.parseCommands(args);
        final Server server = new Server();
        System.out.println(String.format("Server with %d threads started on http://localhost:%d ",
                threadsCount, port));
        server.run(port);

    }


    private static class setUpFromCommandLine {
        void parseCommands(String[] args) {
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

        }
    }
}
