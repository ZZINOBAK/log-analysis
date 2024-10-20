package log;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReader {
    private final BufferedReader br;

    public LogReader(BufferedReader br) {
        this.br = br;
    }

    public void readAllLog(LogVO logVO) throws IOException, ParseException {
        String line;
        while ((line = br.readLine()) != null) {
            // 구분자로 로그를 나누기
            String[] split = line.split("]\\[");

            logVO.putStatusMap(Integer.parseInt(split[0].replace("[", "").trim()));

            String url = split[1];
            Pattern pattern = Pattern.compile("search/([^?]*)\\?apikey=([^&]*)");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                logVO.putServiceIdMap(matcher.group(1));
                logVO.putApiKeyMap(matcher.group(2));
            }
            logVO.putBrowserMap(split[2].replace("]", "").trim());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logVO.putDateMap(dateFormat.parse(split[3].replace("]", "").trim()));
        }

    }

}
