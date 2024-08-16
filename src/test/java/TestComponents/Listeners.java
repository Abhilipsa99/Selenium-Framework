package TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ExtentReports.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result){
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); //unique thread id
    }

    @Override
    public void onTestSuccess(ITestResult result){
        test.log(Status.PASS,"Test Passed");
    }

    @SneakyThrows
    @Override
    public void onTestFailure(ITestResult result){
        extentTest.get().fail(result.getThrowable());
        driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        String filePath = getScreenshot(result.getMethod().getMethodName(),driver);
        extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext contex){
        extent.flush();
    }
}
