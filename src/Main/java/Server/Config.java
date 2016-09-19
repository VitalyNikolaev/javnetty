package Server;

/**
 * Created by nikolaev on 19.09.16.
 */
public class Config {
    private static int threadsCount = Runtime.getRuntime().availableProcessors();
    private static int port = 8080;
    private static String rootDir = System.getProperty("user.dir");
    private static String index = "index.html";

    public static void initConfig(String[] args) throws IllegalArgumentException {
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

    public static int getThreadsCount() {
        return threadsCount;
    }

    public static String getRootDir() {
        return rootDir;
    }

    public static String getIndex() {
        return index;
    }

    public static int getPort() {
        return port;
    }

}
