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
        if (br.readLine() != null) {
            for (int i = 1; i <= chunk; i++) {
                logs.add(br.readLine());
            }
        } else if (br.readLine() == null) {
            br.close();
        }
        return logs;
    }
}
