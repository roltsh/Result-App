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
        config.computeIfAbsent(level, k -> new ArrayList<>());
        if (!config.get(level).contains(platform)) {
            config.get(level).add(platform);
        }
    }

    public void removePlatform(LogLevel level, LogPlatform platform) {
        List<LogPlatform> list = config.get(level);
        if (list != null) list.remove(platform);
    }

    public void detachPlatformFromAllLevels(LogPlatform platform) {
        for (LogLevel level : LogLevel.values()) {
            removePlatform(level, platform);
        }
    }

    public List<LogPlatform> getPlatformsForLevel(LogLevel level) {
        return config.getOrDefault(level, Collections.emptyList());
    }

    public void loadFromConfigFile(String filePath) throws IOException {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(filePath)) {
            props.load(reader);
        }

        String levelStr = props.getProperty("log.level", "INFO").toUpperCase();
        setLevel(LogLevel.valueOf(levelStr));

        loadPlatformsFromConfig(props);
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
            attachPlatformToLevels(console, lvl);
        }
        if (fileLevelStr != null) {
            LogLevel lvl = LogLevel.valueOf(fileLevelStr.toUpperCase());
            attachPlatformToLevels(file, lvl);
        }
        if (networkLevelStr != null) {
            LogLevel lvl = LogLevel.valueOf(networkLevelStr.toUpperCase());
            attachPlatformToLevels(network, lvl);
        }
    }

    private void attachPlatformToLevels(LogPlatform platform, LogLevel minLevel) {
        for (LogLevel l : LogLevel.values()) {
            if (l.getSeverity() >= minLevel.getSeverity()) {
                addPlatform(l, platform);
            }
        }
    }
}
