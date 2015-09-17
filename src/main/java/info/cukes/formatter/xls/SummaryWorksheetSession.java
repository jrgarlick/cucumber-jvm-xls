package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;


public class SummaryWorksheetSession extends WorksheetSession {

    private static final int FEATURE_COL = 0;
    private static final int SCENARIO_COL = 1;
    private static final int DURATION_COL = 2;
    private static final int RESULT_COL = 3;

    private static final int SUMMARY_LABEL_COL = 1;
    private static final int SUMMARY_VALUE_COL = 2;
    private static final int START_DATE_TIME_ROW = 3;
    private static final int END_DATE_TIME_ROW = 4;
    private static final int TOTAL_RUN_TIME_ROW = 5;
    private static final int TOTAL_FEATURES_ROW = 6;
    private static final int TOTAL_FAILURES_ROW = 7;
    private static final int TOTAL_ERRORS_ROW = 8;
    private static final int TOTAL_SUCCESS_RATE_ROW = 9;

    private static final int FEATURE_TITLE_ROW = 12;
    private static final int FEATURE_HEADING_ROW = 13;
    private static final int FIRST_FEATURE_RESULT_ROW = 16;
    private static final int FIRST_SCENARIO_STAT_COL = 2;


    private long startTime;
    private long endTime;
    private int currentRow = 0;

    public SummaryWorksheetSession(WorkbookSession workbookSession) {
        super(workbookSession, "Summary");
        startTime = System.currentTimeMillis();
        worksheet.setColumnWidth(FEATURE_COL, 1000);
        worksheet.setColumnWidth(SCENARIO_COL, 15000);
    }

    public void setTitleAndHeader() {
        Cell titleCell = setCell(currentRow++, FEATURE_COL, "Cucumber Test Execution Summary");
        formatCell(titleCell, CucumberWorkbookSession.STYLE_H1);

//        Cell resultHeaderCell = setCell(currentRow, RESULT_COL, "Outcome");
//        Cell timingHeaderCell = setCell(currentRow, DURATION_COL, "Timing");
//        formatCell(resultHeaderCell, "H3");
//        formatCell(timingHeaderCell, "H3");

//        currentRow = STARTING_SCENARIO_ROW;
    }

    public void addSummaryAndFeatureSections(Map<Feature, List<ScenarioStat>> featureStats) {
        endTime = System.currentTimeMillis();
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
                currentRow++;
            }
            currentRow++;
        }

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

        setCell(rownum, statCol++, "# Tests");
        setCell(rownum, statCol++, "# Failures");
        setCell(rownum, statCol++, "# Skipped");
        setCell(rownum, statCol++, "Run Time");

    }

    private void writeFeatureStatTotals(int rownum, ScenarioStat s) {
        int statCol = FIRST_SCENARIO_STAT_COL;

        DecimalFormat format = new DecimalFormat("#,##0.###");

        setCell(rownum, statCol++, s.getTests());
        setCell(rownum, statCol++, s.getFailures());
        setCell(rownum, statCol++, s.getSkipped());
        setCell(rownum, statCol++, format.format((s.getRunTime() / 1000000000d))+"s");
    }

}
