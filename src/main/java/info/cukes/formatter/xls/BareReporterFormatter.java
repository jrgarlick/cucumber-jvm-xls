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

import java.util.List;

public abstract class BareReporterFormatter implements Reporter, Formatter {

    @Override
    public void background(Background background1) {
        // NOOP
    }

    @Override
    public void close() {
        // NOOP
    }

    @Override
    public void done() {
        // NOOP
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario1) {
        // NOOP
    }

    @Override
    public void eof() {
        // NOOP
    }

    @Override
    public void examples(Examples examples1) {
        // NOOP
    }

    @Override
    public void feature(Feature feature1) {
        // NOOP
    }

    @Override
    public void scenario(Scenario scenario1) {
        // NOOP
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenariooutline) {
        // NOOP
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario1) {
        // NOOP
    }

    @Override
    public void step(Step step1) {
        // NOOP
    }

    @Override
    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {
        // NOOP
    }

    @Override
    public void uri(String s) {
        // NOOP
    }

    @Override
    public void after(Match match1, Result result1) {
        // NOOP
    }

    @Override
    public void before(Match match1, Result result1) {
        // NOOP
    }

    @Override
    public void embedding(String s, byte[] abyte0) {
        // NOOP
    }

    @Override
    public void match(Match match1) {
        // NOOP
    }

    @Override
    public void result(Result result1) {
        // NOOP
    }

    @Override
    public void write(String s) {
        // NOOP
    }

}
