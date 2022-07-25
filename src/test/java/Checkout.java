
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Checkout {
    WebDriver driver;
    String url = "https://www.demoblaze.com/cart.html#";

    public Checkout(WebDriver driver){
        this.driver = driver;
    }

    public void navigateToCart(){
        if(!this.driver.getCurrentUrl().equals(url)){
            this.driver.get(url);
        }
    }

    public Boolean placeOrder(String name, String countryName, String cityName, String cardDetails, String month, String year) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        WebElement placeTheOrder = this.driver.findElement(By.xpath("//button[contains(@class,'btn-success')]"));
        placeTheOrder.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));

        //Find WebElement related to name and input the parameter in textbox
        WebElement nameOfUser = this.driver.findElement(By.xpath("//input[@id='name']"));
        nameOfUser.clear();
        nameOfUser.sendKeys(name);

        //Find WebElement related to country and input the parameter in textbox
        WebElement country = this.driver.findElement(By.xpath("//input[@id='country']"));
        country.clear();
        country.sendKeys(countryName);

        //Find WebElement related to city and input the parameter in textbox
        WebElement city = this.driver.findElement(By.xpath("//input[@id='city']"));
        city.clear();
        city.sendKeys(cityName);

        //Find WebElement related to cardNumber and input the parameter in textbox
        WebElement cardNumber = this.driver.findElement(By.xpath("//input[@id='card']"));
        cardNumber.clear();
        cardNumber.sendKeys(cardDetails);

        //Find WebElement related to month and input the parameter in textbox
        WebElement month_card = this.driver.findElement(By.xpath("//input[@id='month']"));
        month_card.clear();
        month_card.sendKeys(month);

        //Find WebElement related to year and input the parameter in textbox
        WebElement year_card = this.driver.findElement(By.xpath("//input[@id='year']"));
        year_card.clear();
        year_card.sendKeys(year);

        //Find purchase_button and click it
        WebElement purchase = this.driver.findElement(By.xpath("//button[text()='Purchase']"));
        purchase.click();

        synchronized (driver){
            driver.wait(2000);
        }

        //Handle sweet Alert
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'sweet-alert')]")));
        WebElement sweetAlert = this.driver.findElement(By.xpath("//div[contains(@class,'sweet-alert')]"));
        System.out.println(sweetAlert.findElement(By.tagName("h2")).getText());

        WebElement okButton = sweetAlert.findElement(By.xpath("//button[@class='confirm btn btn-lg btn-primary']"));
        okButton.click();
        return true;
    }
}