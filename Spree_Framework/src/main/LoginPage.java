package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver)
    {
        this.driver=driver;
    }
    private By email=By.cssSelector("input#spree_user_email");
    private By password=By.cssSelector("input#spree_user_password");
    private By btnLogin =By.xpath("//input[@name='commit']");
    private By error=By. cssSelector("div.alert");


    public synchronized void enterEmailId(String mailId)
    {
        BaseDriver.getDriver().findElement(email).sendKeys(mailId);
    }

    public synchronized void enterPwd(String pwd)
    {
        BaseDriver.getDriver().findElement(password).sendKeys(pwd);
    }

    public synchronized WebElement signin()
    {
        return BaseDriver.getDriver().findElement(btnLogin);
    }

    public synchronized WebElement errorMsg(){
        return BaseDriver.getDriver().findElement(error);
    }


}
