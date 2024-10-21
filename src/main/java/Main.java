import log.LogParser;
import log.LogReader;
import log.LogService;
import log.LogWriter;

import java.io.*;
import java.text.ParseException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        LogService logService = new LogService
                (new LogReader(new BufferedReader(new FileReader("CDGLogAnalysis/src/main/resources/input.txt"))),
                        new LogWriter(new BufferedWriter(new FileWriter("CDGLogAnalysis/src/main/resources/output.txt"))));

        LogParser<String> apiKeyMap = new LogParser<>();
        LogParser<Integer> statusMap = new LogParser<>();
        LogParser<String> serviceIdMap = new LogParser<>();
        LogParser<Date> dateMap = new LogParser<>();
        LogParser<String> browserMap = new LogParser<>();

        logService.readAllLog(apiKeyMap, statusMap, serviceIdMap, dateMap, browserMap);

        logService.mostApiKey(apiKeyMap);
        logService.codeStatus(statusMap);
        logService.serviceId(serviceIdMap);
        logService.peakTime(dateMap);
        logService.browserType(browserMap);

        logService.close();

    }
}
