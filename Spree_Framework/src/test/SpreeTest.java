package test;

import main.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Listeners(Listeners_Spree.class)
public class SpreeTest extends BaseDriver {
    WebDriver driver;
    HomePage homeSpree;
    LoginPage loginSpree;
    CartPage productCart;
    CheckoutPage checkOutDetails;

    public synchronized void objectCreation() throws IOException {
        BaseDriver.getDriver().manage().timeouts().implicitlyWait(12000, TimeUnit.MILLISECONDS);
        BaseDriver.getDriver().manage().timeouts().pageLoadTimeout(7000,TimeUnit.MILLISECONDS);
        BaseDriver.getDriver().manage().deleteAllCookies();
        System.out.println(Thread.currentThread().getId());
        loginSpree =new LoginPage(BaseDriver.getDriver());
        productCart =new CartPage(BaseDriver.getDriver());
        homeSpree =new HomePage(BaseDriver.getDriver());
        checkOutDetails =new CheckoutPage(BaseDriver.getDriver());
        loginSpree();
    }


    public synchronized void loginSpree(){
        String username,password;
      //  if(user.equals("User1")) {
            username = "saranyababu2005@gmail.com";
            password = "spree123";
       /* }
        else if(user.equals("User2"))
        {
            username = "saranyab@gmail.com";
            password = "spree123";
        }*/
        System.out.println(Thread.currentThread().getId());
        BaseDriver.getDriver().get(BaseDriver.browserFile.getProperty("qa_url"));

        homeSpree.loginLink().click();
        loginSpree.enterEmailId(username);
        loginSpree.enterPwd(password);
        loginSpree.signin().click();

       Assert.assertEquals(homeSpree.validateAlertMsg().getText(), "Logged in successfully");
        //  LoginTest.logout_spree(home_spree);
    }

    @AfterMethod
    public synchronized void logoutSpree()
    {
        homeSpree.logoutLink().click();
        Assert.assertEquals(homeSpree.validateAlertMsg().getText(),"Signed out successfully.");
        BaseDriver.getDriver().quit();
    }

    @DataProvider
    public Object[][] credentials()
    {
        Object[][] input=new Object[1][3];

        input[0][0]="saranyababu2005@gmail.com";
        input[0][1]="spree123";
        input[0][2]="valid";

        /*input[1][0]="saranyabab2005@gmail.com";
        input[1][1]="spree1234";
        input[1][2]="valid";

        input[2][0]="saranyababu2005@gmail.com";
        input[2][1]="spree123";
        input[2][2]="valid";*/

        return input;
    }

    @Test(dataProvider = "searchTestData",priority = 1)
    public void verifySearch(String product) throws IOException {
        objectCreation();
       // loginSpree("User1");
        System.out.println(Thread.currentThread().getId());
        List<String> searchItemResults;
        Boolean flag=null;

        homeSpree.search(product);
        homeSpree.btnSearch().click();
        searchItemResults = homeSpree.getSearchResults();
        flag= HomePage.validateSearch(searchItemResults,product);

         Assert.assertTrue(flag,"Search Results are related to "+product);
    }

    @DataProvider
    public Object[][] searchTestData()
    {
        Object[][] data=new Object[2][1];

        data[0][0]="Ruby";
        data[1][0]="Apache";

        return data;
    }

    @Test(dataProvider = "dataPriceRange",priority = 2)
    public void verifyDisplayedProducts(String category,String pricerange) throws IOException {
        objectCreation();
        //loginSpree("User1");
        System.out.println(Thread.currentThread().getId());
        List<Double> product_price;
        Boolean  matchedCriteria = null;

        homeSpree.selectCategory(category);
        homeSpree.selectPriceRange(pricerange);
        homeSpree.filterSearch().click();
        product_price= homeSpree.getPriceRanges();
        matchedCriteria= HomePage.validateProducts(pricerange,product_price);

        Assert.assertTrue(matchedCriteria,"Products are displayed in the given criteria");
    }
    @DataProvider
    public Object[][] dataPriceRange()
    {
        Object[][] data=new Object[3][2];

        data[0][0]="Bags";
        data[0][1]="$15.00 - $18.00";

        data[1][0]="Mugs";
        data[1][1]="$10.00 - $15.00";

        data[2][0]="Bags";
        data[2][1]="$20.00 or over";

        return data;
    }
   @Test(priority = 4)
    public void validateOrderAmount() throws InterruptedException, IOException {
       objectCreation();
       //loginSpree("User1");
       System.out.println(Thread.currentThread().getId());
//       Thread.currentThread().setPriority(1);
       String product="Ruby on Rails Mug";
       String price, totalOrder;

        homeSpree.selectDept("All departments");
        homeSpree.search(product);
        homeSpree.btnSearch().click();
        homeSpree.addToCart(product);

        price= productCart.getPrice();
        productCart.addToCart();
        productCart.checkOut();

        totalOrder = checkOutDetails.getOrderTotal();
        Assert.assertEquals(price, totalOrder);
        checkOutDetails.emptyCart();
    }
    @Test(priority = 5)
    public void verifyCartIsEmpty() throws InterruptedException, IOException {
        objectCreation();
        //loginSpree("User1");
        System.out.println(Thread.currentThread().getId());
      //  Thread.currentThread().setPriority(10);

        String product="Ruby on Rails Bag";
        int quantity=3;
        String message;

        homeSpree.selectDept("All departments");
        homeSpree.search(product);
        homeSpree.btnSearch().click();
        homeSpree.addToCart(product);

        productCart.addQuantity(quantity);
        productCart.addToCart();
        productCart.emptyCart();
        message= productCart.getMessage();
        Assert.assertEquals(message,"Your cart is empty");
    }
}
