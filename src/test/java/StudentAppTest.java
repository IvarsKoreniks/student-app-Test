import com.github.javafaker.Faker;
import constants.AllConstants;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import page_objects.AddStudentPage;
import page_objects.AllStudentsPage;
import page_objects.Notifications;

import java.lang.reflect.Method;
import java.time.Duration;

import static constants.AllConstants.MessageConstants.STUDENT_ADDED_TO_THE_SYSTEM;
import static constants.AllConstants.MessageConstants.STUDENT_SUCCESSFULLY_ADDED;
import static org.testng.Assert.assertTrue;
import static utils.ConfigHelper.getConfig;
import static constants.AllConstants.GenderConstants.MALE;
import static org.testng.Assert.assertEquals;
import static utils.DriverManager.*;

public class StudentAppTest {

    private WebDriverWait driverWait;
    private AllStudentsPage allStudentsPage;
    private AddStudentPage addStudentPage;
    private Notifications notifications;
    private final Faker dataFaker = new Faker();

    @BeforeMethod(alwaysRun = true)
    public void initialize(Method method) {
        testName = (method.getName());
        driverWait = new WebDriverWait(getInstance(), Duration.ofSeconds(5));
        getInstance().get(getConfig().getString("student.app.hostname"));
        allStudentsPage = new AllStudentsPage();
        addStudentPage = new AddStudentPage();
        notifications = new Notifications();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!getConfig().getBoolean("student.app.run.locally")) markRemoteTest(result);
        closeDriver();
    }

    @Test(description = "Add student and check successful message")
    public void openStudentApp() {
        allStudentsPage.waitAndClickStudentButton();

        String name = dataFaker.name().firstName();
        String email = dataFaker.internet().emailAddress();

        addStudentPage.waitAndSetValueForNameField(name);
        addStudentPage.waitAndSetValueForEmailField(email);
        addStudentPage.waitAndSetGender(MALE);
        addStudentPage.waitAndClickSubmitButton();

        assertEquals(notifications.getMessageFromNotification(), STUDENT_SUCCESSFULLY_ADDED);
        assertEquals(notifications.getDescriptionFromNotification(), name + STUDENT_ADDED_TO_THE_SYSTEM);

        notifications.getPopUpCloseButton().click();
        assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }

    @Test(description = "Check title")
    public void checkTitle() {
        assertEquals(getInstance().getTitle(), "acodemy - React App");
    }

    @Test(description = "Check error message then name, email and gender fields not added")
    public void checkErrorMessage() {
        allStudentsPage.waitAndClickStudentButton();
        addStudentPage.waitAndClickSubmitButton();

        assertEquals(notifications.getNameFieldErrorMessage(), "Please enter student name");
        assertEquals(notifications.getEmailFieldErrorMessage(), "Please enter student email");
        assertEquals(notifications.getGenderFieldErrorMessage(), "Please select a gender");
    }

//    @Test(description = "User delete")
//    public void userDelete() {
//        allStudentsPage.waitAndClickOnDeleteStudent();
//        assertEquals(notifications.getStudentDeleteNotificationMessage(), "Are you sure to delete ");
//    }

}