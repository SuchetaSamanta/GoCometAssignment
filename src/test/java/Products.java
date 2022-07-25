
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Products {
    WebDriver driver;
    String url = "https://www.demoblaze.com/index.html";

    public Products(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean searchForProduct(String Category, String Product) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //List of product categories present under Category section

        List<WebElement> ProductCategory = this.driver.findElements(By.xpath("//a[@id='itemc']"));
        for(WebElement products: ProductCategory){
            if(products.getText().trim().equalsIgnoreCase(Category)){
                System.out.println("Category: " + Category);
                products.click();
            }
        }
        synchronized (wait){
            wait.wait(3000);
        }

        //It holds the list of products under each category present in the homepage
        List<WebElement> products = this.driver.findElements(By.xpath("//a[@class='hrefch']"));

        //Traversing the loop to find the desired product
        for(WebElement product : products){
            if(product.getText().trim().contains(Product)){
                System.out.println(Product + " :is present in the Website");
                product.click();
                return true;
            }
        }
        System.out.println(Product + " :does not exist");
        return true;
    }

    public Boolean productDescription(String Product){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='more-information']/p")));
        WebElement descriptionOfProduct = this.driver.findElement(By.xpath("//div[@id='more-information']/p"));
        System.out.println("Description of the product: "+Product);
        System.out.println(descriptionOfProduct.getText());
        return true;

    }

    public Boolean addProductToCart(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Add to cart']")));
        //Find the Add_to_Cart webElement and click it
        WebElement addToCart = this.driver.findElement(By.xpath("//a[text()='Add to cart']"));
        addToCart.click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = this.driver.switchTo().alert();
        String alertMessage = alert.getText();
        System.out.println("Is the product added to the cart: " + alertMessage);
        alert.accept();

        //find the cart icon and click on it and move to cart page
        WebElement cart = this.driver.findElement(By.id("cartur"));
        cart.click();

        if(alertMessage.equals("Product added.")){
            return true;
        } else return false;
    }

    public void listOfProducts(String Category) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        //It holds the list of categories present in the homepage
        List<WebElement> categories = this.driver.findElements(By.xpath("//a[@id='itemc']"));


        for (WebElement sub_category : categories) {
            if (sub_category.getText().trim().equalsIgnoreCase(Category)) {
                System.out.println("Category: " + sub_category.getText());
                sub_category.click();
                break;
            }
        }

        synchronized (wait){
            wait.wait(3000);
        }

        //It holds the list of products under each category present in the homepage
        List<WebElement> products = this.driver.findElements(By.cssSelector(".hrefch"));

        System.out.println("List of Products available in the Website: ");
        for (WebElement desired_product : products) {
            System.out.println(desired_product.getText());
        }
        System.out.println();
    }
}