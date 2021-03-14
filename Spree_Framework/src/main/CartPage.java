package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By title=By.cssSelector("h1.product-title");
    private By price=By.xpath("//span[@itemprop='price']");
    private By addProduct =By.cssSelector("button#add-to-cart-button");
    private By checkout=By.cssSelector("button#checkout-link");
    private By cart=By.xpath("//a[@href='/cart']");
    private By emptyCart=By.xpath("//input[@value='Empty Cart']");
    private By quanityText = By.cssSelector("input#quantity");
    private By emptyMsg =By.cssSelector("div.alert.alert-info");

    public synchronized String getTitle()
    {
        String text=BaseDriver.getDriver().findElement(title).getText();
        return text;
    }
    public synchronized String getPrice()
    {
        String price_value=BaseDriver.getDriver().findElement(price).getText();
        return price_value;
    }

    public synchronized void addToCart()
    {
        System.out.println("inside add to cart");
        BaseDriver.getDriver().findElement(addProduct).click();
    }

    public synchronized void checkOut()
    {
        BaseDriver.getDriver().findElement(checkout).click();
    }

    public synchronized void emptyCart() throws InterruptedException {
        //BaseDriver.getDriver().findElement(cart).click();
        //Thread.sleep(3000);
        BaseDriver.getDriver().findElement(emptyCart).click();
    }

    public synchronized void addQuantity(int quantity)
    {
        BaseDriver.getDriver().findElement(quanityText).clear();
        BaseDriver.getDriver().findElement(quanityText).sendKeys(String.valueOf(quantity));
    }

    public synchronized String getMessage()
    {
        return BaseDriver.getDriver().findElement(emptyMsg).getText();
    }

}
