package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

public class FeatureWorksheetSession extends WorksheetSession {

    private static final int SCENARIO_COL = 0;
    private static final int STEP_COL = 1;
    private static final int DURATION_COL = 2;
    private static final int RESULT_COL = 3;

    private static final int STARTING_SCENARIO_ROW = 3;

    public static final String PASSED = "passed";
    public static final String FAILED = Result.FAILED;
    public static final String SKIPPED = "skipped"; // Result doesn't define it?

    private Feature feature;
    private List<ScenarioStat> stats = new ArrayList<ScenarioStat>();
    private ScenarioStat currentScenarioStat;
    private int currentRow = 0;
    private int resultsRow = 0;

    public FeatureWorksheetSession(WorkbookSession workbookSession, Feature feature) {
        super(workbookSession, feature.getName());
        this.feature = feature;
        currentScenarioStat = new ScenarioStat();
        
        worksheet.setColumnWidth(SCENARIO_COL, 1000);
        worksheet.setColumnWidth(STEP_COL, 15000);
    }

    public void setTitleAndHeader() {
        Cell titleCell = setCell(currentRow++, SCENARIO_COL, "Feature: "+feature.getName());
        formatCell(titleCell, CucumberWorkbookSession.STYLE_H2);

        Cell resultHeaderCell = setCell(currentRow, RESULT_COL, "Outcome");
        Cell timingHeaderCell = setCell(currentRow, DURATION_COL, "Timing");
        formatCell(resultHeaderCell, CucumberWorkbookSession.STYLE_H3_CENTERED);
        formatCell(timingHeaderCell, CucumberWorkbookSession.STYLE_H3_CENTERED);

        currentRow = STARTING_SCENARIO_ROW;
    }


    public void startOfScenario(Scenario scenario) {
        Cell scenarioCell = setCell(currentRow++, SCENARIO_COL, scenario.getName());
        formatCell(scenarioCell, CucumberWorkbookSession.STYLE_H3);

        resultsRow = currentRow;
        currentScenarioStat.setName(scenario.getName());
        stats.add(currentScenarioStat);
    }


    public void addStep(Step step) {
        setCell(currentRow++, STEP_COL, step.getKeyword()+step.getName());
        currentScenarioStat.incrementSteps();
    }

    public void addResult(Result result) {
        // set result cell
        String resultStatus = result.getStatus();
        Cell resultCell = setCell(resultsRow, RESULT_COL, resultStatus);
        if (result.getErrorMessage() != null) {
            this.setCellComment(resultCell, result.getErrorMessage(), "Cucumber-XLS");
        }

        // Set timing cell if there is any
        DecimalFormat format = new DecimalFormat("#,##0.###");
        if (result.getDuration() != null) {
            Cell timingCell = setCell(resultsRow, DURATION_COL, format.format((result.getDuration() / 1000000000d))+"s");
            formatCell(timingCell, CucumberWorkbookSession.STYLE_CENTERED);
            currentScenarioStat.addRunTime(result.getDuration());
        }

        // Format the result cell and Increment the outcome counters
        switch (resultStatus) {
        case FAILED :
            formatCell(resultCell, CucumberWorkbookSession.STYLE_FAILED);
            currentScenarioStat.incrementFailures();
            currentScenarioStat.setResult(FAILED);
            break;
        case SKIPPED :
            formatCell(resultCell, CucumberWorkbookSession.STYLE_SKIPPED);
            currentScenarioStat.incrementSkipped();
            break;
        default:
            formatCell(resultCell, CucumberWorkbookSession.STYLE_PASSED);
            currentScenarioStat.incrementPassed();
            break;
        }

        resultsRow++;
    }

    public void endOfScenario(Scenario scenario) {
        currentRow++;
    }


    public List<ScenarioStat> getFeatureStats() {
        return this.stats;
    }

}
