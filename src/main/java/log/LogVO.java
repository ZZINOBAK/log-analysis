package log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LogVO {
    private final Map<String, Integer> apiKeyMap = new HashMap<>();
    private final Map<Integer, Integer> statusMap = new HashMap<>();
    private final Map<String, Integer> serviceIdMap = new HashMap<>();
    private final Map<String, Integer> browserMap = new HashMap<>();
    private final Map<Date, Integer> dateMap = new HashMap<>();

    public Map<String, Integer> getApiKeyMap() {
        return apiKeyMap;
    }

    public void putApiKeyMap(String apiKey) {
        apiKeyMap.put(apiKey, apiKeyMap.getOrDefault(apiKey, 0) + 1);
    }

    public Map<Integer, Integer> getStatusMap() {
        return statusMap;
    }

    public void putStatusMap(Integer status) {
        statusMap.put(status, statusMap.getOrDefault(status, 0) + 1);
    }

    public Map<String, Integer> getServiceIdMap() {
        return serviceIdMap;
    }

    public void putServiceIdMap(String serviceId) {
        serviceIdMap.put(serviceId, serviceIdMap.getOrDefault(serviceId, 0) + 1);
    }

    public Map<String, Integer> getBrowserMap() {
        return browserMap;
    }

    public void putBrowserMap(String browser) {
        browserMap.put(browser, browserMap.getOrDefault(browser, 0) + 1);
    }

    public Map<Date, Integer> getDateMap() {
        return dateMap;
    }

    public void putDateMap(Date date) {
        dateMap.put(date, dateMap.getOrDefault(date, 0) + 1);
    }
}
