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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class XLSOutputFormatter implements Reporter, Formatter {

    private static final int SCENARIO_COL = 0;
    private static final int STEP_COL = 1;
    private static final int RESULT_COL = 2;
    private static final int DURATION_COL = 3;

    private String outputPath = "target/Cucumber-"+System.currentTimeMillis()+".xls";

    private Workbook workbook;
    private Sheet worksheet;
    private Drawing drawing;
    private int currentRow = 1;
    private int resultsRow = 1;

    public XLSOutputFormatter() throws IOException {
        System.out.println("XLSOutputFormatter ----> CONSTRUCTOR");
        workbook = new HSSFWorkbook();
    }

    @Override
    public void background(Background background) {
        System.out.println("XLSOutputFormatter ----> BACKGROUND: "+background.getName());
    }

    @Override
    public void close() {
        System.out.println("XLSOutputFormatter ----> CLOSE");
        try {
            File outputFile = new File(outputPath);
            FileOutputStream out = new FileOutputStream(outputFile);
            workbook.write(out);
            workbook.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void done() {
        System.out.println("XLSOutputFormatter ----> DONE");
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> ENDOFSCENARIO: "+scenario.getName());
        currentRow++;
    }

    @Override
    public void eof() {
        System.out.println("XLSOutputFormatter ----> ENDOFFILE");
    }

    @Override
    public void examples(Examples examples) {
        System.out.println("XLSOutputFormatter ----> EXAMPLES: "+examples.getName());
    }

    @Override
    public void feature(Feature feature) {
        System.out.println("XLSOutputFormatter ----> FEATURE: "+feature.getName());
        worksheet = workbook.createSheet(feature.getName());
        drawing = worksheet.createDrawingPatriarch();
//        worksheet.setName(feature.getName());
        currentRow = 1;
    }

    @Override
    public void scenario(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> SCENARIO: "+scenario.getName());
        addCell(currentRow++, SCENARIO_COL, scenario.getName());
        resultsRow = currentRow;
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        System.out.println("XLSOutputFormatter ----> SCENARIO OUTLINE: "+scenarioOutline.getName());
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> STARTOFSCENARIOLIFECYCLE: "+scenario.getName());
    }

    @Override
    public void step(Step step) {
        System.out.println("XLSOutputFormatter ----> STEP: "+step.getName());
        addCell(currentRow++, STEP_COL, step.getKeyword()+step.getName());
    }

    @Override
    public void syntaxError(String arg0, String arg1, List<String> arg2, String arg3, Integer arg4) {
        System.out.println("XLSOutputFormatter ----> SYNTAXERROR");
    }

    @Override
    public void uri(String uri) {
        System.out.println("XLSOutputFormatter ----> URI: "+uri);
    }

    @Override
    public void after(Match match, Result result) {
        System.out.println("XLSOutputFormatter ----> AFTER: "+match.getLocation()+" : "+result.getStatus());
    }

    @Override
    public void before(Match match1, Result result1) {
        System.out.println("XLSOutputFormatter ----> BEFORE: "+match1.getLocation()+" : "+result1.getStatus());
    }

    @Override
    public void embedding(String s, byte[] bytes) {
        System.out.println("XLSOutputFormatter ----> EMBEDDING: "+s);
    }

    @Override
    public void match(Match match) {
        System.out.println("XLSOutputFormatter ----> MATCH: "+match.getLocation());
    }

    @Override
    public void result(Result result) {
        System.out.println("XLSOutputFormatter ----> RESULT: "+result.getStatus()+" : "+result.getErrorMessage());
        addCell(resultsRow, RESULT_COL, result.getStatus(), result.getErrorMessage());

        DecimalFormat format = new DecimalFormat("#,##0.###");
        addCell(resultsRow, DURATION_COL, format.format((result.getDuration() / 1000000000d))+"s");

//        if (result.getErrorMessage() != null) {
//            addCell(resultsRow, ERROR_COL, result.getErrorMessage());
//        }
        resultsRow++;
    }

    @Override
    public void write(String string) {
        System.out.println("XLSOutputFormatter ----> WRITE: "+string);
    }


    private void addCell(int rownum, int colnum, String value) {
        addCell(rownum, colnum, value, null);
    }

    private void addCell(int rownum, int colnum, String value, String commentText) {
        Row row = worksheet.getRow(rownum);
        if (row == null) {
            row = worksheet.createRow(rownum);
        }
        Cell cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }

        cell.setCellValue(value);
        if (commentText != null) {
            HSSFClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5);
            Comment comment = drawing.createCellComment(anchor);
            comment.setString(workbook.getCreationHelper().createRichTextString(commentText));
            comment.setAuthor("Cucumber-XLS");
            cell.setCellComment(comment);
        }
    }

}
