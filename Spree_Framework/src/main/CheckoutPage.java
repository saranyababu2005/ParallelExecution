package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    WebDriver driver;

    public CheckoutPage(WebDriver driver)
    {
        this.driver=driver;
    }

    private By orderTotal=By.cssSelector("span#summary-order-total");
    private By cart=By.xpath("//a[@href='/cart']");
    private By emptyCart=By.xpath("//input[@value='Empty Cart']");

    public synchronized String getOrderTotal(){
        return BaseDriver.getDriver().findElement(orderTotal).getText();
    }

    public synchronized void emptyCart() throws InterruptedException {
        BaseDriver.getDriver().findElement(cart).click();
        //Thread.sleep(3000);
        BaseDriver.getDriver().findElement(emptyCart).click();
    }
}
