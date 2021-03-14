package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import main.BaseDriver;
import main.ExtentReport;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.IOException;

public class Listeners_Spree extends ExtentReport implements ITestListener {

    ExtentReports extent=ExtentReport.extentreport();
    ExtentTest etest;
    @Override
    public void onTestStart(ITestResult result) {
      etest=extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        Reporter.log(result.getMethod().getMethodName()+" " + "PASSED");
        etest.log(Status.PASS,"PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenpath = null;
        Reporter.log(result.getMethod().getMethodName() +" "+ "Failed");
        try {
            String filepath= BaseDriver.screenshot(result.getMethod().getMethodName());
            screenpath= BaseDriver.attachScreenshot(filepath);
            //System.out.println(screenpath);
            Reporter.log(screenpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        etest.fail(result.getThrowable());
       // etest.addScreenCaptureFromPath(screenpath).toString();
        //etest.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(screenpath).build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
            extent.flush();
    }
}
