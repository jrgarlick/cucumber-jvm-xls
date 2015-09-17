package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;

import java.io.IOException;

public class XLSOutputFormatter extends BareReporterFormatter {

    private String outputPath = "target/Cucumber-XLS.xls";

    private CucumberWorkbookSession workbookSession;
    private FeatureWorksheetSession featureSession;

    public XLSOutputFormatter() throws IOException {
        workbookSession = CucumberWorkbookSession.getInstance();
    }

    @Override
    public void feature(Feature feature) {
        try {
            featureSession = workbookSession.beginNewFeature(feature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scenario(Scenario scenario) {
        try {
            featureSession.startOfScenario(scenario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step(Step step) {
        try {
            featureSession.addStep(step);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void result(Result result) {
        try {
            featureSession.addResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        try {
            featureSession.endOfScenario(scenario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            workbookSession.save(outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
