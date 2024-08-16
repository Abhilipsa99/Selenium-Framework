package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/Cucumber", glue="StepDefinitions",
monochrome = true, tags = "@ErrorValidations", plugin={"html:target/Cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests {
}
