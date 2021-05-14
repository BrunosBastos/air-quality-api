package tqs.airquality.web;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


import static org.hamcrest.MatcherAssert.assertThat;


public class StepsDefinition {

    private WebDriver webDriver;
    private int requests = 0;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) throws Throwable {
        webDriver = new FirefoxDriver();
        webDriver.get(url);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @And("I check that {string} value is greater than {int}")
    public void iCheckThatValueIsEqualTo(String arg0, int arg1) throws Throwable {
        if(arg0.equals("requests"))
            requests = Integer.parseInt(webDriver.findElement(By.id(arg0)).getText());
        assertThat(webDriver.findElement(By.id(arg0)).getText(), greaterThanOrEqualTo(Integer.toString(arg1)));
    }

    @And("I insert {string} in {string}")
    public void iInsertIn(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        webDriver.findElement(By.id(arg1)).sendKeys(arg0);
    }

    @And("I press {string}")
    public void iPress(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        webDriver.findElement(By.id(arg0)).click();
    }

    @Then("I should see that result contains {string}")
    public void iShouldSeeThatResultContains(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        {
            WebDriverWait wait = new WebDriverWait(webDriver, 30);
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".card-body > .row:nth-child(1) > .col:nth-child(1)"), "Aveiro"));
            //assertThat(webDriver.findElement(By.cssSelector(".card-body > .row:nth-child(1) > .col:nth-child(1)")).getText(),is( "Aveiro"));
        }

    }

    @Then("I should see that cache has changed")
    public void iShouldSeeThatCacheHasChanged() {
        {
            WebDriverWait wait = new WebDriverWait(webDriver, 5);
            WebElement request = webDriver.findElement(By.id("requests"));
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(request,Integer.toString(requests))));
        }
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        {
            WebDriverWait wait = new WebDriverWait(webDriver, 30);
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".row:nth-child(3) h1"), "City Data not found"));
            //assertThat(webDriver.findElement(By.cssSelector(".card-body > .row:nth-child(1) > .col:nth-child(1)")).getText(),is( "Aveiro"));
        }

    }

    @Then("I close browser")
    public void iCloseBrowser() {
        webDriver.close();
    }
}
