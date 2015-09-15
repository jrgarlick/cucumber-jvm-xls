# cucumber-jvm-xls
Export Cucumber Scenario Results to Excel

## Overview
The Cucumber XLS output formatter provides a simple report after your testing results. A workbook is created for the test and is saved in the target/ directory as 'Cucumber.xlsx'. For each feature, a worksheet is created which contains the results of each scenario. 

## Configuration

It's easy to add the XLS output to your cucumber project. All you have to do is to download and install the cucumber-jvm-xls JAR file, and include the current POM into your Maven project. Then, either provide a Cucumber.options plugin command line option to reference the `info.cukes.formatter.xls.XLSOutputFormatter`, or specify it in your Test class like this:

```
...
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"info.cukes.formatter.xls.XLSOutputFormatter"})
public class SomeTest {  
...
```

