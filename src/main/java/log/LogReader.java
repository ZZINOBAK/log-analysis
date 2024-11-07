package log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LogReader {
    private final BufferedReader br;

    public LogReader(String ipFilePath) throws FileNotFoundException {
        this.br = new BufferedReader(new FileReader(ipFilePath));
    }

    public List<String> readLog(List<String> logs, int chunk) throws IOException {
        String line;
        for (int i = 1; i <= chunk; i++) {
            line = br.readLine();  // 한 줄 읽기
            if (line == null) {  // 더 이상 읽을 줄이 없으면 종료
                break;
            }
            logs.add(line);  // 읽은 줄을 리스트에 추가
        }
        return logs;
    }
}
