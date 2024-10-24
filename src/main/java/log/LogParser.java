package log;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogParser {
    private LogData<String> apiKeyMap;
    private LogData<Integer> statusMap;
    private LogData<String> serviceIdMap;
    private LogData<Date> dateMap;
    private LogData<String> browserMap;

    public LogParser(LogData<String> apiKeyMap, LogData<Integer> statusMap, LogData<String> serviceIdMap, LogData<Date> dateMap, LogData<String> browserMap) {
        this.apiKeyMap = apiKeyMap;
        this.statusMap = statusMap;
        this.serviceIdMap = serviceIdMap;
        this.dateMap = dateMap;
        this.browserMap = browserMap;
    }

    public void parseLog(List<String> logs) throws MalformedURLException, ParseException {
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i) != null) {
                String[] split = logs.get(i).split("]\\["); // 구분자로 로그를 나누기
                putStatus(split);
                putApiKeyAndServiceId(split);
                putBrowserType(split);
                putDate(split);
            }
        }

    }

    private LogData<Date> putDate(String[] split) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateMap.putLogParserMap(dateFormat.parse(split[3].replace("]", "").trim()));
        return dateMap;
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
    }

    private void putStatus(String[] split) {
        statusMap.putLogParserMap(Integer.parseInt(split[0].replace("[", "").trim()));
    }
}
