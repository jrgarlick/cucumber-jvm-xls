package info.cukes.formatter.xls;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class XLSOutputFormatter implements Reporter, Formatter {

    private static final int SCENARIO_COL = 0;
    private static final int STEP_COL = 1;
    private static final int RESULT_COL = 2;
    private static final int DURATION_COL = 3;
    private static final int ERROR_COL = 4;

    private String outputFileName = "target/Cucumber.xls";

    private WritableWorkbook workbook;
    private WritableSheet worksheet;
    private int currentSheet = 0;
    private int currentRow = 1;
    private int resultsRow = 1;

    public XLSOutputFormatter() throws IOException {
        System.out.println("XLSOutputFormatter ----> CONSTRUCTOR");
        File outputFile = new File(outputFileName);
        workbook = Workbook.createWorkbook(outputFile);
//        worksheet = workbook.getSheet(currentSheet);
    }

    public void background(Background background) {
        System.out.println("XLSOutputFormatter ----> BACKGROUND: "+background.getName());
    }

    public void close() {
        System.out.println("XLSOutputFormatter ----> CLOSE");
        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public void done() {
        System.out.println("XLSOutputFormatter ----> DONE");
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> ENDOFSCENARIO: "+scenario.getName());
        currentRow++;
    }

    public void eof() {
        System.out.println("XLSOutputFormatter ----> ENDOFFILE");
    }

    public void examples(Examples examples) {
        System.out.println("XLSOutputFormatter ----> EXAMPLES: "+examples.getName());
    }

    public void feature(Feature feature) {
        System.out.println("XLSOutputFormatter ----> FEATURE: "+feature.getName());
        worksheet = workbook.createSheet(feature.getName(), currentSheet++);
//        worksheet.setName(feature.getName());
        currentRow = 1;
    }

    public void scenario(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> SCENARIO: "+scenario.getName());
        addCell(currentRow++, SCENARIO_COL, scenario.getName());
        resultsRow = currentRow;
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        System.out.println("XLSOutputFormatter ----> SCENARIO OUTLINE: "+scenarioOutline.getName());
    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> STARTOFSCENARIOLIFECYCLE: "+scenario.getName());
    }

    public void step(Step step) {
        System.out.println("XLSOutputFormatter ----> STEP: "+step.getName());
        addCell(currentRow++, STEP_COL, step.getKeyword()+" "+step.getName());
    }

    public void syntaxError(String arg0, String arg1, List<String> arg2, String arg3, Integer arg4) {
        System.out.println("XLSOutputFormatter ----> SYNTAXERROR");
    }

    public void uri(String uri) {
        System.out.println("XLSOutputFormatter ----> URI: "+uri);
    }

    public void after(Match match, Result result) {
        System.out.println("XLSOutputFormatter ----> AFTER: "+match.getLocation()+" : "+result.getStatus());
    }

    public void before(Match match1, Result result1) {
        System.out.println("XLSOutputFormatter ----> BEFORE: "+match1.getLocation()+" : "+result1.getStatus());
    }

    public void embedding(String s, byte[] bytes) {
        System.out.println("XLSOutputFormatter ----> EMBEDDING: "+s);
    }

    public void match(Match match) {
        System.out.println("XLSOutputFormatter ----> MATCH: "+match.getLocation());
    }

    public void result(Result result) {
        System.out.println("XLSOutputFormatter ----> RESULT: "+result.getStatus()+" : "+result.getErrorMessage());
        addCell(resultsRow, RESULT_COL, result.getStatus());
        addCell(resultsRow, DURATION_COL, String.valueOf(result.getDuration()));

        if (result.getErrorMessage() != null) {
            addCell(resultsRow, ERROR_COL, result.getErrorMessage());
        }
        resultsRow++;
    }

    public void write(String string) {
        System.out.println("XLSOutputFormatter ----> WRITE: "+string);
    }


    private void addCell(int row, int col, String value) {
        Label labelCell = new Label(col, row, value);
        try {
            worksheet.addCell(labelCell);
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

}
