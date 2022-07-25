
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartDetails {
    WebDriver driver;
    String url = "https://www.demoblaze.com/cart.html#";

    public CartDetails(WebDriver driver){
        this.driver = driver;
    }

    public void navigateToCart(){
        if(!driver.getCurrentUrl().equals(this.url)){
            driver.get(this.url);
        }
    }

    public Boolean verifyCart(String expectedProducts ) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr/td[2]")));

        //Verify if the correct product is present in the cart
        WebElement cart = this.driver.findElement(By.xpath("//tbody/tr/td[2]"));

        if(cart.getText().contains(expectedProducts)){
            System.out.println("Product is present in the Cart");
        }
        return true;
    }
}