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

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class AllStudentsPage {

    private final WebDriverWait webDriverWait;

    public AllStudentsPage() {
        WebDriver driver = DriverManager.getInstance();
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//div[@class='ant-table-title']//button")
    public WebElement addStudentButton;

    @FindBy(how = How.XPATH, using = "//td[@class='ant-table-cell ant-table-column-sort']")
    public WebElement studentId;

    public void waitAndClickStudentButton () {
        webDriverWait.until(elementToBeClickable(addStudentButton));
        addStudentButton.click();
    }

    public String getStudentId() {
        webDriverWait.until(ExpectedConditions.visibilityOf(studentId));
        return studentId.getText();
    }

    public void waitAndClickOnDeleteStudent() {
        webDriverWait.until(elementToBeClickable(By.xpath("//*[@data-row-key='" + getStudentId() + "']//span[contains(text(),'Delete')]"))).click();
    }
}
