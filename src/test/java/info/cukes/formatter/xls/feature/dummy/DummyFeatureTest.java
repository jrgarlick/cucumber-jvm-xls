package info.cukes.formatter.xls.feature.dummy;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * This is an empty feature test
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"info.cukes.formatter.xls.XLSOutputFormatter"})
public class DummyFeatureTest {

}
