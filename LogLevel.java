import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// ENUM for log levels with severity
enum LogLevel {
    DEBUG(1), INFO(2), WARNING(3), ERROR(4);

    private final int severity;
    LogLevel(int severity) { this.severity = severity; }
    public int getSeverity() { return severity; }
    public boolean isLoggable(LogLevel currentLevel) {
        return this.severity >= currentLevel.getSeverity();
    }
}

// Log Message
class LogMessage {
    private final LogLevel level;
    private final String message;

    public LogMessage(LogLevel level, String message, Object... args) {
        this.level = level;
        this.message = format(message, args);
    }

    private String format(String template, Object... args) {
        return String.format(template.replace("{}", "%s"), args);
    }

    public LogLevel getLevel() { return level; }
    public String getMessage() { return message; }
}

// Logging Platform Interface
interface LogPlatform {
    void log(LogMessage message);
}

class ConsolePlatform implements LogPlatform {
    public void log(LogMessage message) {
        System.out.println("[CONSOLE] [" + message.getLevel() + "] " + message.getMessage());
    }
}

class FilePlatform implements LogPlatform {
    private final BufferedWriter writer;

    public FilePlatform(String path) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(path, true));
    }

    public void log(LogMessage message) {
        try {
            writer.write("[FILE] [" + message.getLevel() + "] " + message.getMessage());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class NetworkPlatform implements LogPlatform {
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public NetworkPlatform() {
        Thread processor = new Thread(() -> {
            while (true) {
                try {
                    String msg = queue.take();
                    System.out.println("[NETWORK] " + msg);
                } catch (InterruptedException ignored) {}
            }
        });
        processor.setDaemon(true);
        processor.start();
    }

    public void log(LogMessage message) {
        queue.offer("[" + message.getLevel() + "] " + message.getMessage());
    }
}

class LoggerConfig {
    private final Map<LogLevel, List<LogPlatform>> config = new EnumMap<>(LogLevel.class);
    private LogLevel currentLevel = LogLevel.INFO;

    public void setLevel(LogLevel level) {
        currentLevel = level;
    }

    public LogLevel getLevel() {
        return currentLevel;
    }

    public void addPlatform(LogLevel level, LogPlatform platform) {
        config.computeIfAbsent(level, k -> new ArrayList<>()).add(platform);
    }

    public void removePlatform(LogLevel level, LogPlatform platform) {
        List<LogPlatform> list = config.get(level);
        if (list != null) list.remove(platform);
    }

    public List<LogPlatform> getPlatformsForLevel(LogLevel level) {
        return config.getOrDefault(level, Collections.emptyList());
    }
}

class LogService {
    private final LoggerConfig config;

    public LogService(LoggerConfig config) {
        this.config = config;
    }

    public void log(LogLevel level, String message, Object... args) {
        if (!level.isLoggable(config.getLevel())) return;

        LogMessage log = new LogMessage(level, message, args);
        for (LogPlatform platform : config.getPlatformsForLevel(level)) {
            platform.log(log);
        }
    }

    public void info(String msg, Object... args) {
        log(LogLevel.INFO, msg, args);
    }

    public void debug(String msg, Object... args) {
        log(LogLevel.DEBUG, msg, args);
    }

    public void warning(String msg, Object... args) {
        log(LogLevel.WARNING, msg, args);
    }

    public void error(String msg, Object... args) {
        log(LogLevel.ERROR, msg, args);
    }

    public void setLevel(LogLevel level) {
        config.setLevel(level);
    }

    public void attachPlatform(LogLevel level, LogPlatform platform) {
        config.addPlatform(level, platform);
    }

    public void deattachPlatform(LogLevel level, LogPlatform platform) {
        config.removePlatform(level, platform);
    }
}

// Main class with entry point
class LogLevel_Main {
    public static void main(String[] args) throws Exception {
        LoggerConfig config = new LoggerConfig();
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
    }
}
