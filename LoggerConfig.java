package src;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LoggerConfig {
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
        List<LogPlatform> platforms = new ArrayList<>();
        
        // Add platforms for the specified level and all lower levels
        // following the hierarchy: ERROR <- WARNING <- INFO <- DEBUG
        for (Map.Entry<LogLevel, List<LogPlatform>> entry : config.entrySet()) {
            if (level.isLoggable(entry.getKey())) {
                platforms.addAll(entry.getValue());
            }
        }
        
        return platforms;
    }

public void loadPlatformsFromConfig(Properties props) throws IOException {
    String consoleLevelStr = props.getProperty("log.platform.console");
    String fileLevelStr = props.getProperty("log.platform.file");
    String networkLevelStr = props.getProperty("log.platform.network");

    LogPlatform console = new ConsolePlatform();
    LogPlatform file = new FilePlatform(props.getProperty("log.file.path", "logs.txt"));
    LogPlatform network = new NetworkPlatform();

    if (consoleLevelStr != null) {
        LogLevel lvl = LogLevel.valueOf(consoleLevelStr.toUpperCase());
        addPlatform(lvl, console);
    }
    if (fileLevelStr != null) {
        LogLevel lvl = LogLevel.valueOf(fileLevelStr.toUpperCase());
        addPlatform(lvl, file);
    }
    if (networkLevelStr != null) {
        LogLevel lvl = LogLevel.valueOf(networkLevelStr.toUpperCase());
        addPlatform(lvl, network);
    }
}
}
