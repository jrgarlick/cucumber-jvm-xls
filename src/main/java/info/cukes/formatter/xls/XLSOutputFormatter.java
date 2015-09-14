package info.cukes.formatter.xls;

import gherkin.formatter.Formatter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

import java.util.List;

public class XLSOutputFormatter implements Formatter {

    public XLSOutputFormatter() {
        System.out.println("XLSOutputFormatter ----> CONSTRUCTOR");
    }

    public void background(Background background) {
        System.out.println("XLSOutputFormatter ----> BACKGROUND: "+background.getName());
    }

    public void close() {
        System.out.println("XLSOutputFormatter ----> CLOSE");
    }

    public void done() {
        System.out.println("XLSOutputFormatter ----> DONE");
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> ENDOFSCENARIO: "+scenario.getName());
        System.out.println("XLSOutputFormatter ----> ENDOFSCENARIO COMMENTS: ");
        for (Comment c : scenario.getComments()) {
            System.out.println(c.getLine()+": "+c.getValue());
        }
    }

    public void eof() {
        System.out.println("XLSOutputFormatter ----> ENDOFFILE");
    }

    public void examples(Examples examples) {
        System.out.println("XLSOutputFormatter ----> EXAMPLES: "+examples.getName());
    }

    public void feature(Feature feature) {
        System.out.println("XLSOutputFormatter ----> FEATURE: "+feature.getName());
    }

    public void scenario(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> SCENARIO: "+scenario.getName());
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        System.out.println("XLSOutputFormatter ----> SCENARIO OUTLINE: "+scenarioOutline.getName());
    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        System.out.println("XLSOutputFormatter ----> STARTOFSCENARIOLIFECYCLE: "+scenario.getName());
    }

    public void step(Step step) {
        System.out.println("XLSOutputFormatter ----> STEP: "+step.getName());
    }

    public void syntaxError(String arg0, String arg1, List<String> arg2, String arg3, Integer arg4) {
        System.out.println("XLSOutputFormatter ----> SYNTAXERROR");
    }

    public void uri(String uri) {
        System.out.println("XLSOutputFormatter ----> URI: "+uri);
    }
}
