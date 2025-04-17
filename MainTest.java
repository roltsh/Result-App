package src;

public class MainTest {
    public static void main(String[] args) throws Exception {
        // Set to true to see detailed output
        boolean verbose = true;
        if (verbose) System.out.println("Starting logger test...");
        LoggerConfig config = new LoggerConfig();
        config.loadFromConfigFile("logger.properties");

        LogService logger = new LogService(config);

        ConsolePlatform console = new ConsolePlatform();
        FilePlatform file = new FilePlatform("logs.txt");
        NetworkPlatform network = new NetworkPlatform();

        logger.attachPlatform(LogLevel.INFO, console);
        logger.attachPlatform(LogLevel.INFO, file);
        logger.attachPlatform(LogLevel.ERROR, network);

        logger.info("This is my first log with Id: {}", 101);
        logger.error("I got the error: {}", "NullPointerException");
        logger.debug("This is just to debug the object: {} with Id: {}", new Object(), 101);

        logger.setLevel(LogLevel.WARNING);
        logger.debug("This should not log");
        logger.error("Only this will log now due to level {}", LogLevel.WARNING);
        
        // Give network platform time to process messages
        if (verbose) System.out.println("Waiting for network platform to process messages...");
        Thread.sleep(1000);
        if (verbose) System.out.println("Test completed.");
    }
}
