package log;

import java.util.HashMap;
import java.util.Map;

public class LogData<K> {
    private final Map<K, Integer> logParserMap;

    public LogData() {
        this.logParserMap = new HashMap<>();
    }

    public Map<K, Integer> getLogParserMap() {
        return logParserMap;
    }

    public void putLogParserMap(K key) {
        logParserMap.put(key, logParserMap.getOrDefault(key, 0) + 1);
    }

}
