package log;

import java.util.HashMap;
import java.util.Map;

public class LogParser<K> {
    private final Map<K, Integer> logParserMap;

    public LogParser() {
        this.logParserMap = new HashMap<>();
    }

    public Map<K, Integer> getLogParserMap() {
        return logParserMap;
    }

    public void putLogParserMap(K key) {
        logParserMap.put(key, logParserMap.getOrDefault(key, 0) + 1);
    }

}
