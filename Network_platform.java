package src;

import java.util.*;

public class NetworkPlatform implements LogPlatform {
    private final List<String> logs = new ArrayList<>();

    public void log(LogMessage message) {
        String entry = "[" + message.getLevel() + "] " + message.getMessage();
        logs.add(entry);
        System.out.println("[NETWORK] " + entry);
    }

    public List<String> getLogs() {
        return logs;
    }
}
