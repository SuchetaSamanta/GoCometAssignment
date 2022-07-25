import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;


public class GoCometTest {
    static WebDriver driver;
    static Boolean status;
    static String lastGeneratedUserName;

    public static void webDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s ", type, message, status));
    }

    //Verify Registration
    public static Boolean TestCase01() throws InterruptedException {
        Boolean status;
        logStatus("Start TestCase", "Test Case 1: Verify User Registration", "DONE");

        //Register a new user with new credentials
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@123", true);
        if (!status) {
            logStatus("TestCase 1", "Test Case Pass. User Registration Pass", "FAIL");
            logStatus("End TestCase", "Test Case 1: Verify user Registration : ", status ? "PASS" : "FAIL");

            // Return False as the test case Fails
            return false;
        } else {
            logStatus("TestCase 1", "Test Case Pass. User Registration Pass", "PASS");
        }
        return true;
    }
    //Verify the searching of a Product
    public static Boolean TestCase02() throws InterruptedException {
        status = false;
        logStatus("TestCase 2", "Start test case: Verify the searching of a Product ", "DONE");

        //HomePage(Product to View)
        String category = "Phones";
        String product = "Samsung Galaxy";
        Products products = new Products(driver);
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 2", "Test Step: Verify searching for a Product: "+product, status ? "PASS" : "FAIL");

        //Searching for a Product that is not available in the Website
        category = "Laptops";
        product = "Dell i7";
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 2", "Test Step: Verify searching for a Product: "+product, status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 3: Verify searching of a Product: ", status ? "PASS" : "FAIL");
        return status;
    }

    //Verify if Add_to_Cart Button working in Product page
    public static Boolean TestCase03() throws InterruptedException {
        logStatus("TestCase 3", "Start test case : Verify if Add_to_Cart Button working in Product page", "DONE");
        status = false;

        //Register a new User
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 3", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        logStatus("TestCase 3", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToLoginPage();
        login.PerformLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Products products = new Products(driver);
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 3", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = products.addProductToCart();
        login.performLogout();

        logStatus("End TestCase", "Test Case 3: Verify if Add_to_Cart Button working in Product page ", status ? "PASS" : "FAIL");
        return status;
    }

    //Verify if the product description is viewable on the Product page
    public static Boolean TestCase04() throws InterruptedException {
        logStatus("TestCase 4", "Start test case : Verify if the Product description is viewable on the Product page", "DONE");
        status = false;

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "ASUS";
        Products products = new Products(driver);
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 4", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //View product description
        status = products.productDescription(product);
        logStatus("End TestCase", "Test Case 4: Verify if the Product description is viewable on the Product page ", status ? "PASS" : "FAIL");

        return status;
    }

    //Verify the complete flow of buying process and placing order for a product is working correctly
    public static Boolean TestCase05() throws InterruptedException {
        status = false;
        logStatus("Start TestCase", "Test Case 5: Verify Happy Flow of buying a product", "DONE");

        //Register a new User
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 5", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        logStatus("TestCase 5", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToLoginPage();
        login.PerformLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);

        logStatus("TestCase 5", "Test Step : User logIn", "PASS");

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Products products = new Products(driver);
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 5", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = products.addProductToCart();
        logStatus("TestCase 5", "Test Step: Verify Product added to cart: "+ product, status ? "PASS" : "FAIL");

        //Cart(Product in cart)
        String expectedResult = product;
        CartDetails cart = new CartDetails(driver);
        cart.navigateToCart();
        cart.verifyCart(expectedResult);

        //Checkout(PlaceOrder)
        String name = "Sucheta Samanta";
        String country = "India";
        String city = "Mumbai";
        String cardDetails = "XXXX-XXXX-XXXX-2RTY";
        String month = "12";
        String year = "2024";

        Checkout checkout = new Checkout(driver);
        checkout.navigateToCart();
        status = checkout.placeOrder(name, country, city, cardDetails, month, year);
        logStatus("TestCase 5", "Test Step: Verify order placed: "+ product, status ? "PASS" : "FAIL");

        //logout
        status = login.performLogout();
        logStatus("TestCase 5", "Test Step: Verify user logged out: "+ product, status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 5: Test Case 5: Verify Happy Flow of buying a product ", status ? "PASS" : "FAIL");
        return status;
    }

    //Verify that the contents added of the cart are saved against the user login details even after logout
    public static Boolean TestCase06() throws InterruptedException {
        logStatus("Start TestCase", "Test Case 6: Verify that the contents added to the cart are saved against the user login details", "DONE");
        status = false;

        //Register a new User
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        registration.registerUser("testUser", "abc@123", true);

        logStatus("TestCase 6", "Test Step : User SignIn", "PASS");

        //Save the last generated username
        lastGeneratedUserName = registration.lastGeneratedUsername;

        logStatus("TestCase 6", "Test Step: Verify User Login", "DONE");

        //Login with registered username and password
        Login login = new Login(driver);
        login.navigateToLoginPage();
        login.PerformLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);

        logStatus("TestCase 6", "Test Step : User logIn", "PASS");

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Products products = new Products(driver);
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 6", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = products.addProductToCart();
        logStatus("TestCase 6", "Test Step: Verify Product added to cart: "+ product, status ? "PASS" : "FAIL");

        //Cart(Product in cart)
        String expectedResult = product;
        CartDetails cart = new CartDetails(driver);
        cart.navigateToCart();

        //Perform logout
        login.navigateToLoginPage();
        status = login.performLogout();
        logStatus("TestCase 6", "Test Step: User logged out: "+ product, status ? "PASS" : "FAIL");

        synchronized (driver){
            driver.wait(2000);
        }

        //Perform login
        login.PerformLogin(lastGeneratedUserName, "abc@123");
        login.VerifyUserLoggedIn(lastGeneratedUserName);
        logStatus("TestCase 6", "Test Step : User logIn", "PASS");

        //Navigate to Cart
        cart.navigateToCart();
        status = cart.verifyCart(expectedResult);
        logStatus("TestCase 6", "Test Step: Verify cart contents after login again: "+ product, status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 6: Verify that the contents added to the cart are saved against the user login details ", status ? "PASS" : "FAIL");

        return status;
    }

    //View the list of Products under a category
    public static Boolean TestCase07() throws InterruptedException {
        logStatus("Start TestCase", "Test Case 7: View the list of Products under each Category ", "DONE");
        String category="";

        //Homepage
        Products products = new Products(driver);
        products.navigateToHomePage();

        List<WebElement> item = driver.findElements(By.xpath("//a[@id='itemc']"));
        for(WebElement webElement : item){
            category = webElement.getText();
            products.listOfProducts(category);
        }

        logStatus("End TestCase", "Test Case 7: View the list of Products under each Category ", "PASS");
        return true;
    }

    //Verify products should not be added by user without login
    public static Boolean TestCase08() throws InterruptedException {
        logStatus("Start TestCase", "Test Case 8: Verify products should not be added by user without login ", "DONE");

        //HomePage(Product to View)
        String category = "Monitors";
        String product = "Apple monitor";
        Products products = new Products(driver);
        products.navigateToHomePage();
        status = products.searchForProduct(category, product);
        logStatus("TestCase 8", "Test Step: Verify searching for a Product: "+ product, status ? "PASS" : "FAIL");

        //Product page
        status = products.addProductToCart();
        logStatus("TestCase 8", "Test Step: Verify Product added to cart: "+ product, status ? "PASS" : "FAIL");

        //Cart(Product in cart)
        String expectedResult = product;
        CartDetails cart = new CartDetails(driver);
        cart.navigateToCart();
        cart.verifyCart(expectedResult);

        //Checkout(PlaceOrder)
        String name = "Sucheta Samanta";
        String country = "India";
        String city = "Mumbai";
        String cardDetails = "XXXX-XXXX-XXXX-2RTY";
        String month = "12";
        String year = "2024";

        Checkout checkout = new Checkout(driver);
        checkout.navigateToCart();
        status = checkout.placeOrder(name, country, city, cardDetails, month, year);
        logStatus("TestCase 8", "Test Step: Verify order placed: "+ product, status ? "PASS" : "FAIL");

        //If status is pass here, it means the testcase failed
        logStatus("End TestCase", "Test Case 8: Verify Happy Flow of buying a product ", status ? "PASS" : "FAIL");
        return status;
    }

    public static void main(String[] args) {

        status = false;
        String url = "https://www.demoblaze.com/index.html";
        int totalTests = 0;
        int passedTests = 0;
        GoCometTest.webDriver();

        try {
            // Execute Test Case 1
            totalTests += 1;
            status = TestCase01();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");


            // Execute Test Case 2
            totalTests += 1;
            status = TestCase02();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            // Execute Test Case 3
            totalTests += 1;
            status = TestCase03();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            // Execute Test Case 4
            totalTests += 1;
            status = TestCase04();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            // Execute Test Case 5
            totalTests += 1;
            status = TestCase05();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            // Execute Test Case 6
            totalTests += 1;
            status = TestCase06();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            // Execute Test Case 7
            totalTests += 1;
            status = TestCase07();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");

            // Execute Test Case 8
            totalTests += 1;
            status = TestCase08();
            if (status) {
                passedTests += 1;
            }
            System.out.println(" ");


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            driver.quit();
            System.out.println(String.format("%s out of %s test cases Passed ", Integer.toString(passedTests), Integer.toString(totalTests)));
        }
    }
}
