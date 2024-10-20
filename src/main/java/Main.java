import log.LogReader;
import log.LogService;
import log.LogVO;
import log.LogWriter;

import java.io.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        LogService logService = new LogService
                (new LogReader(new BufferedReader(new FileReader("CDGLogAnalysis/src/main/resources/input.txt"))),
                        new LogWriter(new BufferedWriter(new FileWriter("CDGLogAnalysis/src/main/resources/output.txt"))),
                        new LogVO());

        logService.readAllLog();

        logService.mostApiKey();
        logService.codeStatus();
        logService.serviceId();
        logService.peakTime();
        logService.browserType();

        logService.close();

    }
}
