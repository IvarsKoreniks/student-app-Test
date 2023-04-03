package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;

public class Notifications {

    private final WebDriverWait webDriverWait;

    public Notifications () {
        WebDriver driver = DriverManager.getInstance();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CLASS_NAME, using = "ant-notification-notice-message")
    WebElement notificationMessageElement;

    @FindBy(how = How.CLASS_NAME, using = "ant-notification-notice-description")
    WebElement notificationDescriptionElement;

    @FindBy(how = How.CLASS_NAME, using = "ant-notification-close-x")
    WebElement popUpCloseButton;

    @FindBy(how = How.ID, using = "name_help")
    WebElement nameFieldErrorMessage;

    @FindBy(how = How.ID, using = "email_help")
    WebElement emailFieldErrorMessage;

    @FindBy(how = How.ID, using = "gender_help")
    WebElement genderFieldErrorMessage;

    @FindBy(how = How.CLASS_NAME, using = "ant-popconfirm-message-title")
    WebElement studentDeleteNotificationMessage;

    public WebElement getPopUpCloseButton() {
        return popUpCloseButton;
    }

    public String getMessageFromNotification () {
        webDriverWait.until(ExpectedConditions.visibilityOf(notificationMessageElement));
        return notificationMessageElement.getText();
    }

    public String getDescriptionFromNotification () {
        webDriverWait.until(ExpectedConditions.visibilityOf(notificationDescriptionElement));
        return notificationDescriptionElement.getText();
    }

    public String getNameFieldErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(nameFieldErrorMessage));
        return nameFieldErrorMessage.getText();
    }

    public String getEmailFieldErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(emailFieldErrorMessage));
        return emailFieldErrorMessage.getText();
    }

    public String getGenderFieldErrorMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(genderFieldErrorMessage));
        return genderFieldErrorMessage.getText();
    }

    public String getStudentDeleteNotificationMessage() {
        webDriverWait.until(ExpectedConditions.visibilityOf(studentDeleteNotificationMessage));
        return studentDeleteNotificationMessage.getText();
    }


}
