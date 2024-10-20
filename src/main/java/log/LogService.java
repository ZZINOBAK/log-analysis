package log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class LogService {
    private final LogReader logReader;
    private final LogWriter logWriter;
    private final LogVO logVO;
    int maxValue = 0;

    public LogService(LogReader logReader, LogWriter logWriter, LogVO logVO) {
        this.logReader = logReader;
        this.logWriter = logWriter;
        this.logVO = logVO;
    }

    public void readAllLog() throws IOException, ParseException {
        logReader.readAllLog(logVO);
    }

    public void mostApiKey() throws IOException {
        String maxKey = null;
        for (Map.Entry<String, Integer> entry : logVO.getApiKeyMap().entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        logWriter.writeMostApiKey(maxKey);
    }

    public void codeStatus() throws IOException {
        List<Map.Entry<Integer, Integer>> codes = logVO.getStatusMap().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey()) // 키 기준으로 정렬
                .collect(Collectors.toList()); // List로 변환
        logWriter.writeCodeStatus(codes);
    }

    public void serviceId() throws IOException {
        // 값(value) 기준으로 정렬하고 상위 3개 항목 추출
        List<Map.Entry<String, Integer>> topThree = logVO.getServiceIdMap().entrySet()
                .stream()  // Map의 엔트리를 Stream으로 변환
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // 값 기준 내림차순 정렬
                .limit(3) // 상위 3개만 가져오기
                .collect(Collectors.toList()); // List로 수집
        logWriter.writeServiceId(topThree);
    }

    public void peakTime() throws IOException {
        Date maxKey = null;
        for (Map.Entry<Date, Integer> entry : logVO.getDateMap().entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String peakTime = formatter.format(maxKey);
        logWriter.writePeakTime(peakTime);
    }

    public void browserType() throws IOException {
        // 전체 사용 횟수 계산
        int totalUsage = logVO.getBrowserMap().values().stream().mapToInt(Integer::intValue).sum();

        // 각 브라우저의 사용 비율 계산 및 출력
        List<Map.Entry<String, Double>> percentages = logVO.getBrowserMap().entrySet()
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

