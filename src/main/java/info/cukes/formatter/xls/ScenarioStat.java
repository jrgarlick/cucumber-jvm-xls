package info.cukes.formatter.xls;

public class ScenarioStat {

    private String name;
    private int tests = 0;
    private int errors = 0;
    private int failures = 0;
    private int skipped = 0;
    private long runTime = 0;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void incrementTests() {
        tests++;
    }

    public int getTests() {
        return tests;
    }
    public void setTests(int tests) {
        this.tests = tests;
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


    public ScenarioStat add(ScenarioStat that) {
        ScenarioStat newStat = new ScenarioStat();
        newStat.tests = this.tests + that.tests;
        newStat.errors = this.errors + that.errors;
        newStat.failures = this.failures + that.failures;
        newStat.skipped = this.skipped + that.skipped;
        newStat.runTime = this.runTime + that.runTime;
        return newStat;
    }
}
