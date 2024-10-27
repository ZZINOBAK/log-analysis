package log;

import dataType.ServiceIdType;
import dataType.StatusType;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class LogParser {
    private LogData<String> apiKeyMap;
    private LogData<StatusType> statusMap;
    private LogData<ServiceIdType> serviceIdMap;
    private LogData<LocalDateTime> dateMap;
    private LogData<String> browserMap;

    public LogParser(LogData<String> apiKeyMap, LogData<StatusType> statusMap, LogData<ServiceIdType> serviceIdMap, LogData<LocalDateTime> dateMap, LogData<String> browserMap) {
        this.apiKeyMap = apiKeyMap;
        this.statusMap = statusMap;
        this.serviceIdMap = serviceIdMap;
        this.dateMap = dateMap;
        this.browserMap = browserMap;
    }

    public void parseLog(List<String> logs) {
        logs.stream() // logs를 Stream으로 변환
                .filter(Objects::nonNull) // null이 아닌 로그만 필터링
                .map(log -> log.split("]\\[")) // 로그를 구분자로 나누기
                .forEach(split -> { // 각 split 배열에 대해 작업 수행
                    putStatus(split);
                    try {
                        putApiKeyAndServiceId(split);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    putBrowserType(split);
                    putDate(split);
                });
    }

    private void putDate(String[] split) {
        LocalDateTime parsedDate = LocalDateTime.parse(split[3].replace("]", "").trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateMap.putLogParserMap(parsedDate);
    }

    private void putBrowserType(String[] split) {
        browserMap.putLogParserMap(split[2].replace("]", "").trim());
    }

    private void putApiKeyAndServiceId(String[] split) throws MalformedURLException {
        URL urlObj = new URL(split[1]);
        String paths = urlObj.getPath();
        String queries = urlObj.getQuery();

        if (paths != null) {
            String search = paths.split("/")[2].trim();
            if (search.contains("apikey")) {
                String[] apiKeyComponents = search.split("&")[0].split("=");
                apiKeyMap.putLogParserMap(apiKeyComponents[1].trim());
            } else {
                try {
                    serviceIdMap.putLogParserMap(ServiceIdType.valueOf(search.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    serviceIdMap.putLogParserMap(ServiceIdType.UNKNOWN);
                }
            }
            // 쿼리 파라미터 분석
            if (queries != null && queries.contains("apikey")) {
                String query = queries.split("&")[0].trim();
                if (query.startsWith("apikey=")) {
                    apiKeyMap.putLogParserMap(query.split("=")[1]);
                }
            }
        }
    }

    private void putStatus(String[] split) {
        try {
            boolean found = false; // 매칭 여부를 추적하는 변수
            for (StatusType status : StatusType.values()) {
                if (status.getCode() == Integer.parseInt(split[0].replace("[", "").trim())) {
                    statusMap.putLogParserMap(status);
                    found = true; // 매칭된 상태를 찾은 경우
                    break; // 매칭된 상태를 찾으면 반복 종료
                }
            }
            if (!found) {
                statusMap.putLogParserMap(StatusType.UNKNOWN); // 매칭되지 않는 경우 UNKNOWN 추가
            }
        } catch (NumberFormatException e) {
            // 숫자가 아닌 경우 UNKNOWN 상태 추가
            statusMap.putLogParserMap(StatusType.UNKNOWN);
        } catch (ArrayIndexOutOfBoundsException e) {
            // split 배열의 인덱스가 범위를 넘어서는 경우
            statusMap.putLogParserMap(StatusType.UNKNOWN);
        }
    }
}