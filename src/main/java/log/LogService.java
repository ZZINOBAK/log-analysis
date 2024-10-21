package log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class LogService {
    private final LogReader logReader;
    private final LogWriter logWriter;

    public LogService(LogReader logReader, LogWriter logWriter) {
        this.logReader = logReader;
        this.logWriter = logWriter;
    }

    public void readAllLog(LogParser<String> apiKeyMap, LogParser<Integer> statusMap,
                           LogParser<String> serviceIdMap, LogParser<Date> dateMap,
                           LogParser<String> browserMap) throws IOException, ParseException {
        logReader.readAllLog(apiKeyMap, statusMap, serviceIdMap, dateMap, browserMap);
    }

    public void mostApiKey(LogParser<String> apiKeyMap) throws IOException {
        int maxValue = 0;
        String maxKey = null;
        for (Map.Entry<String, Integer> entry : apiKeyMap.getLogParserMap().entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        logWriter.writeMostApiKey(maxKey);
    }

    public void codeStatus(LogParser<Integer> statusMap) throws IOException {
        List<Map.Entry<Integer, Integer>> codes = statusMap.getLogParserMap().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // 키 기준으로 정렬
                .collect(Collectors.toList()); // List로 변환
        logWriter.writeCodeStatus(codes);
    }

    public void serviceId(LogParser<String> serviceIdMap) throws IOException {
        // 값(value) 기준으로 정렬하고 상위 3개 항목 추출
        List<Map.Entry<String, Integer>> topThree = serviceIdMap.getLogParserMap().entrySet()
                .stream()  // Map의 엔트리를 Stream으로 변환
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // 값 기준 내림차순 정렬
                .limit(3) // 상위 3개만 가져오기
                .collect(Collectors.toList()); // List로 수집
        logWriter.writeServiceId(topThree);
    }

    public void peakTime(LogParser<Date> dateMap) throws IOException {
        int maxValue = 0;
        Date maxKey = null;
        for (Map.Entry<Date, Integer> entry : dateMap.getLogParserMap().entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String peakTime = formatter.format(maxKey);
        logWriter.writePeakTime(peakTime);
    }

    public void browserType(LogParser<String> browserMap) throws IOException {
        // 전체 사용 횟수 계산
        int totalUsage = browserMap.getLogParserMap().values().stream().mapToInt(Integer::intValue).sum();

        // 각 브라우저의 사용 비율 계산 및 출력
        List<Map.Entry<String, Double>> percentages = browserMap.getLogParserMap().entrySet()
                .stream()
                .map(entry -> {
                    String browser = entry.getKey();
                    int usage = entry.getValue();
                    double percentage = (double) usage / totalUsage * 100;
                    return new AbstractMap.SimpleEntry<>(browser, percentage); // Map.Entry로 반환
                })
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // 비율 기준 내림차순 정렬
                .collect(Collectors.toList());
        logWriter.writeBrowserType(percentages);
    }

    public void close() throws IOException {
        logWriter.close();
    }


}

