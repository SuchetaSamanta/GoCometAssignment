import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Timestamp;
import java.time.Duration;


public class Register {

    WebDriver driver;
    String url = "https://www.demoblaze.com/index.html";
    public String lastGeneratedUsername = "";

    public Register(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }

    public Boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Find the Sign up button and click it
        WebElement signUpBtn = driver.findElement(By.id("signin2"));
        signUpBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));

        // Find the Username Text Box
        WebElement username_txt_box = this.driver.findElement(By.id("sign-username"));

        // Get time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_data_username;
        if (makeUsernameDynamic)
            // Concatenate the timestamp to string to form unique timestamp
            test_data_username = Username + "_" + String.valueOf(timestamp.getTime());
        else
            test_data_username = Username;

        // Type the generated username in the username field
        username_txt_box.sendKeys(test_data_username);

        // Find the password Text Box
        WebElement password_txt_box = this.driver.findElement(By.id("sign-password"));
        String test_data_password = Password;

        // Enter the Password value
        password_txt_box.sendKeys(test_data_password);

        // Find the Sign up button
        WebElement Sign_up_button = this.driver.findElement(By.xpath("//button[text()='Sign up']"));

        // Click the register now button
        Sign_up_button.click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = this.driver.switchTo().alert();
        String alertMsg = alert.getText();

        System.out.println(alertMsg);
        alert.accept();
        this.lastGeneratedUsername = test_data_username;

        if (alertMsg.equalsIgnoreCase("Sign up successful.")) {
            return true;
        } else {
            WebElement closeBtn = this.driver.findElement(By.xpath("//button[text()='Sign up']//preceding-sibling::button[text()='Close']"));
            return false;
        }
    }
}
