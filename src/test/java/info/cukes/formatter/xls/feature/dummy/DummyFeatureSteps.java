package info.cukes.formatter.xls.feature.dummy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DummyFeatureSteps {

    @Given("^some opening criteria$")
    public void some_opening_criteria() throws Throwable {
        assertThat(true, is(true));
    }

    @Given("^some altering conditions$")
    public void some_altering_conditions() throws Throwable {
        assertThat(true, is(true));
    }

    @Given("^some more altering conditions$")
    public void some_more_altering_conditions() throws Throwable {
        assertThat(true, is(true));
    }

    @When("^an action is performed$")
    public void an_action_is_performed() throws Throwable {
        assertThat(true, is(true));
    }

    @When("^some other thing happens$")
    public void some_other_thing_happens() throws Throwable {
        assertThat(true, is(true));
    }

    @Then("^this testable outcome should be happening$")
    public void this_testable_outcome_should_be_happening() throws Throwable {
        assertThat(true, is(true));
    }

    @Then("^this horribly awful thing happens$")
    public void this_horribly_awful_thing_happens() throws Throwable {
        assertThat(true, is(false));
    }

    @Then("^this other testable outcome should be happening$")
    public void this_other_testable_outcome_should_be_happening() throws Throwable {
        assertThat(true, is(true));
    }

}

