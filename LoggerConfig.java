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

    public void loadFromConfigFile(String filePath) throws IOException {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(filePath)) {
            props.load(reader);
        }

        String levelStr = props.getProperty("log.level", "INFO").toUpperCase();
        setLevel(LogLevel.valueOf(levelStr));
    }
}
