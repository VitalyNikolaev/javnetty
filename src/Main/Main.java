package Main;
import io.netty.buffer.ByteBuf;

public class Main {
    private static int threadsCount = Runtime.getRuntime().availableProcessors();
    private static int port = 80;
    private static String rootDir;

    public static void main(String[] args) {
        final setUpFromCommandLine settings = new setUpFromCommandLine();
        settings.parseCommands(args);
        System.out.println("args = [" + threadsCount + "]");
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
