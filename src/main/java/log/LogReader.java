package log;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogReader {
    private final BufferedReader br;

    public LogReader(BufferedReader br) {
        this.br = br;
    }

    public void readAllLog(LogParser<String> apiKeyMap, LogParser<Integer> statusMap,
                           LogParser<String> serviceIdMap, LogParser<Date> dateMap,
                           LogParser<String> browserMap) throws IOException, ParseException {
        String line;

        while ((line = br.readLine()) != null) {
            String[] split = line.split("]\\["); // 구분자로 로그를 나누기

            statusMap.putLogParserMap(Integer.parseInt(split[0].replace("[", "").trim()));

            URL urlObj = new URL(split[1]);
            String paths = urlObj.getPath();
            String queries = urlObj.getQuery();

            if (paths != null) {
                String search = paths.split("/")[2].trim();
                if (search.contains("apikey")) {
                    String[] apiKeyComponents = search.split("&")[0].split("=");
                    apiKeyMap.putLogParserMap(apiKeyComponents[1].trim());
                } else {
                    serviceIdMap.putLogParserMap(search);
                }
            }
            // 쿼리 파라미터 분석
            if (queries != null && queries.contains("apikey")) {
                String query = queries.split("&")[0].trim();
                if (query.startsWith("apikey=")) {
                    apiKeyMap.putLogParserMap(query.split("=")[1]);
                }
            }

            browserMap.putLogParserMap(split[2].replace("]", "").trim());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateMap.putLogParserMap(dateFormat.parse(split[3].replace("]", "").trim()));
        }
    }
}
