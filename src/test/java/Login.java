import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login {
    WebDriver driver;
    String url = "https://www.demoblaze.com/index.html";

    public Login(WebDriver driver){
        this.driver = driver;
    }

    public void navigateToLoginPage() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogin(String Username, String Password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        //Find Log-in button
        WebElement LoginBtn = this.driver.findElement(By.id("login2"));
        LoginBtn.click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

            //Enter text to userId box
            WebElement username_txt_box = this.driver.findElement(By.id("loginusername"));
            username_txt_box.sendKeys(Username);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));

            //Enter password to the text box
            WebElement password_txt_box = this.driver.findElement(By.id("loginpassword"));
            password_txt_box.sendKeys(Password);

            //Find the Login button and click it
            WebElement LoginBtn1 = this.driver.findElement(By.xpath("//button[@onclick='logIn()']"));
            LoginBtn1.click();
            wait.until(ExpectedConditions.invisibilityOf(LoginBtn1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.VerifyUserLoggedIn(Username);
    }
        public Boolean VerifyUserLoggedIn(String Username) {
            try {
                // Find the username label (present on the top right of the page)
                WebElement username_label = this.driver.findElement(By.id("nameofuser"));
                return username_label.getText().equals(Username);
            } catch (Exception e) {
                return false;
            }

        }

    public Boolean performLogout() throws InterruptedException {

        navigateToLoginPage();

        synchronized (driver){
            driver.wait(2000);
        }

        //Find WebElement of logout icon and click on it
        WebElement logout = this.driver.findElement(By.xpath("//a[@id='logout2']"));
        logout.click();
        return true;
    }
}
