package info.cukes.formatter.xls;

public class ScenarioStat {

    private String name;
    private int steps = 0;
    private int passed = 0;
    private int errors = 0;
    private int failures = 0;
    private int skipped = 0;
    private long runTime = 0;
    private String result = "passed";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void incrementSteps() {
        steps++;
    }

    public int getSteps() {
        return steps;
    }
    public void setSteps(int tests) {
        this.steps = tests;
    }

    public void incrementErrors() {
        this.errors++;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void incrementFailures() {
        this.failures++;
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public void incrementSkipped() {
        this.skipped++;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }


    public void addRunTime(Long duration) {
        this.runTime += duration;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public void incrementPassed() {
        this.passed++;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return this.result;
    }


    public ScenarioStat add(ScenarioStat that) {
        ScenarioStat newStat = new ScenarioStat();
        newStat.steps = this.steps + that.steps;
        newStat.passed = this.passed + that.passed;
        newStat.errors = this.errors + that.errors;
        newStat.failures = this.failures + that.failures;
        newStat.skipped = this.skipped + that.skipped;
        newStat.runTime = this.runTime + that.runTime;
        if ("failed".equals(that.getResult())) {
            this.result = that.result;
        }
        return newStat;
    }

}
