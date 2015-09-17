package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;

import java.io.IOException;

public class XLSOutputFormatter extends BareReporterFormatter {

    private String outputPath = "target/Cucumber.xls";

    private CucumberWorkbookSession workbookSession;
    private FeatureWorksheetSession featureSession;

    public XLSOutputFormatter() throws IOException {
        System.out.println("XLSOutputFormatter ----> CONSTRUCTOR");
        workbookSession = CucumberWorkbookSession.getInstance();
    }

    @Override
    public void feature(Feature feature) {
        try {
            System.out.println("XLSOutputFormatter ----> FEATURE: "+feature.getName());
            featureSession = workbookSession.beginNewFeature(feature);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scenario(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> SCENARIO: "+scenario.getName());
        try {
            featureSession.startOfScenario(scenario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step(Step step) {
        System.out.println("XLSOutputFormatter ----> STEP: "+step.getName());
        try {
            featureSession.addStep(step);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void result(Result result) {
        System.out.println("XLSOutputFormatter ----> RESULT: "+result.getStatus()+" : "+result.getErrorMessage());
        try {
            featureSession.addResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> ENDOFSCENARIO: "+scenario.getName());
        try {
            featureSession.endOfScenario(scenario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        System.out.println("XLSOutputFormatter ----> CLOSE");
        try {
            workbookSession.save(outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
