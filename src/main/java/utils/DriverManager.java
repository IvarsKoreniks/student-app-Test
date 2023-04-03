package utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import java.net.MalformedURLException;
import java.net.URL;

import static utils.ConfigHelper.getConfig;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public static String testName;

    public static WebDriver getInstance() {
        if(driverThreadLocal.get() == null) {
            if (getConfig().getBoolean("student.app.run.locally")) {
                driverThreadLocal.set(configureLocal());
            } else {
                driverThreadLocal.set(configureRemote());
            }
        }
        return driverThreadLocal.get();
    }

    public static void closeDriver() {
        driverThreadLocal.get().close();
        driverThreadLocal.remove();
    }

    public static RemoteWebDriver configureRemote() {
        URL url = null;
        try {
            url = new URL("url");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return new RemoteWebDriver(url, configureOption());
    }

    private static WebDriver configureLocal() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    public static MutableCapabilities configureCapabilities() {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", "");
        sauceOptions.setCapability("access_key", "");
        sauceOptions.setCapability("name", testName);
        sauceOptions.setCapability("browserVersion", "latest");
        return sauceOptions;
    }

    public static Capabilities configureOption() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("sauce:option", configureCapabilities());
        return options;
    }

    public static void markRemoteTest(ITestResult result) {
        String status = result.isSuccess() ? "passed" : "failed";
        ((JavascriptExecutor) getInstance()).executeScript("sauce:job-result=" + status);
    }
}