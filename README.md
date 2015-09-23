# cucumber-jvm-xls
Export Cucumber Scenario Results to Excel

## Overview
The Cucumber XLS output formatter provides a simple report after your testing results. A workbook is created for the test and is saved in the target/ directory as 'Cucumber.xlsx'. For each feature, a worksheet is created which contains the results of each scenario. 

## Configuration

It's easy to add the XLS output to your cucumber project. All you have to do is to download and install the cucumber-jvm-xls JAR file into your repo, and add the dependency to your Maven project. 

```
...
    <dependency>
      <groupId>info.cukes</groupId>
      <artifactId>cucumber-jvm-xls</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
...
```

Then, either provide a Cucumber.options plugin command line option to reference the `info.cukes.formatter.xls.XLSOutputFormatter`, or specify it in your Test class like this:

```
...
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"info.cukes.formatter.xls.XLSOutputFormatter"})
public class SomeTest {  
...
```

## Output

The formatter will then create an XLS file located in your project's `target/` directory called 'Cucumber-XLS.xls'.

Easy, eh?