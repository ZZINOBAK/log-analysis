package log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LogWriter {
    private final BufferedWriter fw;

    public LogWriter(BufferedWriter fw) {
        this.fw = fw;
    }

    public void writeMostApiKey(String mostApiKey) throws IOException {
        fw.write("최대호출 APIKEY");
        fw.newLine();
        fw.newLine();
        fw.write(mostApiKey);
        fw.newLine();
        fw.newLine();
    }

    public void writeCodeStatus(List<Map.Entry<Integer, Integer>> codeStatus) throws IOException {
        fw.write("상태코드 별 횟수");
        fw.newLine();
        fw.newLine();
        for (Map.Entry<Integer, Integer> entry : codeStatus) {
            fw.write(entry.getKey() + " : " + entry.getValue());
            fw.newLine();
        }
        fw.newLine();
    }

    public void writeServiceId(List<Map.Entry<String, Integer>> topThree) throws IOException {
        fw.write("상위 3개의 API ServiceID와 각각의 요청 수");
        fw.newLine();
        fw.newLine();
        for (Map.Entry<String, Integer> entry : topThree) {
            fw.write(entry.getKey() + " : " + entry.getValue());
            fw.newLine();
        }
        fw.newLine();
    }

    public void writePeakTime(String peakTime) throws IOException {
        fw.write("피크 시간대");
        fw.newLine();
        fw.newLine();
        fw.write(peakTime);
        fw.newLine();
        fw.newLine();
    }

    public void writeBrowserType(List<Map.Entry<String, Double>> browserType) throws IOException {
        fw.write("웹 브라우저 별 사용비율");
        fw.newLine();
        fw.newLine();
        for (Map.Entry<String, Double> entry : browserType) {
            String browser = entry.getKey();
            Double percentage = entry.getValue();
            // 소수점 1자리까지 포맷팅
            fw.write(browser + " : " + String.format("%.1f", percentage));
            fw.newLine();
        }
        fw.newLine();
    }

    public void close() throws IOException {
        fw.close();
    }

}
