package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;


public class SummaryWorksheetSession extends WorksheetSession {

    private static final int FEATURE_COL = 0;
    private static final int SCENARIO_COL = 1;

    private static final int SUMMARY_START_ROW = 3;
    private static final int SUMMARY_LABEL_COL = 1;
    private static final int SUMMARY_VALUE_COL = 2;

    private static final int FEATURE_HEADING_ROW = 13;
    private static final int FIRST_FEATURE_RESULT_ROW = 15;
    private static final int FIRST_SCENARIO_STAT_COL = 5;


    private Date startTime;
    private Date endTime;
    private int currentRow = 0;

    public SummaryWorksheetSession(WorkbookSession workbookSession) {
        super(workbookSession, "Summary");
        startTime = new Date();;
        worksheet.setColumnWidth(FEATURE_COL, 1000);
        worksheet.setColumnWidth(SCENARIO_COL, 5000);
    }

    public void setTitleAndHeader() {
        setCell(currentRow, FEATURE_COL, null);
        Cell titleCell = setCell(currentRow++, FEATURE_COL, "Test Execution Results");
        formatCell(titleCell, CucumberWorkbookSession.STYLE_H1);
    }

    public void addSummaryAndFeatureSections(Map<Feature, List<ScenarioStat>> featureStats) {
        ScenarioStat totalStats = new ScenarioStat();
        int totalFeatures = 0;
        int totalScenarios = 0;
        endTime = new Date();

        // Feature section
        currentRow = FIRST_FEATURE_RESULT_ROW;

        writeFeatureStatHeading(currentRow-1);

        for (Feature f : featureStats.keySet()) {
            Cell featureCell = setCell(currentRow, FEATURE_COL, f.getName());
            formatCell(featureCell, CucumberWorkbookSession.STYLE_H3);
            writeFeatureStatTotals(currentRow, addAllStats(featureStats.get(f)));

            currentRow++;

            for (ScenarioStat s : featureStats.get(f)) {
                setCell(currentRow, SCENARIO_COL, s.getName());
                writeFeatureStatTotals(currentRow, s);
                totalStats = totalStats.add(s);
                totalScenarios++;
                currentRow++;
            }
            currentRow++;
            totalFeatures++;
        }

        formatCell(setCell(FEATURE_HEADING_ROW, 0, "Feature Results"), CucumberWorkbookSession.STYLE_H2);

        // Summary Section
        currentRow = SUMMARY_START_ROW;
        String[] headers = new String[]{
            "Start Date/Time", "End Date/Time", "Total Features", "Total Scenarios",
            "Total Steps", "Total Passed", "Total Failures", "Total Skipped", "Total Run Time"};

        for (String label : headers) {
            formatCell(setCell(currentRow++, SUMMARY_LABEL_COL, label),
                    CucumberWorkbookSession.STYLE_H3);
        }

        currentRow = SUMMARY_START_ROW;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        DecimalFormat format = new DecimalFormat("#,##0.###");

        List<Cell> summaryCells = new ArrayList<Cell>();
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, dateFormat.format(startTime)));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, dateFormat.format(endTime)));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, totalFeatures));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, totalScenarios));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, totalStats.getSteps()));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, totalStats.getPassed()));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, totalStats.getFailures()));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, totalStats.getSkipped()));
        summaryCells.add(setCell(currentRow++, SUMMARY_VALUE_COL, format.format((totalStats.getRunTime() / 1000000000d))+"s"));
        formatCells(summaryCells, CucumberWorkbookSession.STYLE_ALIGN_LEFT);
    }

    private ScenarioStat addAllStats(List<ScenarioStat> stats) {
        ScenarioStat total = new ScenarioStat();
        for (ScenarioStat s : stats) {
            total = total.add(s);
        }
        return total;
    }

    private void writeFeatureStatHeading(int rownum) {
        int statCol = FIRST_SCENARIO_STAT_COL;

        formatCell(setCell(rownum, statCol++, "Result"),
                CucumberWorkbookSession.STYLE_H3_CENTERED);
        formatCell(setCell(rownum, statCol++, "# Steps"),
                CucumberWorkbookSession.STYLE_H3_CENTERED);
        formatCell(setCell(rownum, statCol++, "# Passed"),
                CucumberWorkbookSession.STYLE_H3_CENTERED);
        formatCell(setCell(rownum, statCol++, "# Failures"),
                CucumberWorkbookSession.STYLE_H3_CENTERED);
        formatCell(setCell(rownum, statCol++, "# Skipped"),
                CucumberWorkbookSession.STYLE_H3_CENTERED);
        formatCell(setCell(rownum, statCol++, "Run Time"),
                CucumberWorkbookSession.STYLE_H3_CENTERED);
    }

    private void writeFeatureStatTotals(int rownum, ScenarioStat s) {
        int statCol = FIRST_SCENARIO_STAT_COL;

        DecimalFormat format = new DecimalFormat("#,##0.###");

        Cell resultCell = setCell(rownum, statCol++, s.getResult());
        if (FeatureWorksheetSession.PASSED.equals(s.getResult())) {
            formatCell(resultCell, CucumberWorkbookSession.STYLE_PASSED);
        } else {
            formatCell(resultCell, CucumberWorkbookSession.STYLE_FAILED);
        }

        formatCell(setCell(rownum, statCol++, s.getSteps()),
                CucumberWorkbookSession.STYLE_CENTERED);
        formatCell(setCell(rownum, statCol++, s.getPassed()),
                CucumberWorkbookSession.STYLE_CENTERED);
        formatCell(setCell(rownum, statCol++, s.getFailures()),
                CucumberWorkbookSession.STYLE_CENTERED);
        formatCell(setCell(rownum, statCol++, s.getSkipped()),
                CucumberWorkbookSession.STYLE_CENTERED);
        formatCell(setCell(rownum, statCol++, format.format((s.getRunTime() / 1000000000d))+"s"),
                CucumberWorkbookSession.STYLE_CENTERED);
    }

}
