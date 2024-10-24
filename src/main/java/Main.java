import log.LogAnalyzer;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        LogAnalyzer logAnalyzer = new LogAnalyzer("src/main/resources/input.txt",
                "src/main/resources/output.txt");

        logAnalyzer.readAndParseLog();
        logAnalyzer.readAndParseLog();
        logAnalyzer.readAndParseLog();
        logAnalyzer.readAndParseLog();
        logAnalyzer.readAndParseLog();

        logAnalyzer.analyzeLogData();

        logAnalyzer.close();

    }
}
