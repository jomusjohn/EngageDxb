package page;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractPage {

    private static final Logger log = LoggerFactory.getLogger(AbstractPage.class);

    /**
     * Timeout (5 seconds), applies to all item searches
     */
    public static final Duration DEFAULT_IMPLICITLY_TIMEOUT = Duration.ofSeconds(5);
    /**
     * Timeout (30 seconds), used by default in methods with an explicit wait
     */
    public static final int DEFAULT_EXPLICIT_TIMEOUT = 30000;

    /**
     * Package to be included in screenshot path
     */
    public static final String SCREEN_INCLUDE_PACKAGE = "com.eveara.tests";

    /**
     * Selenium web driver
     */
    protected WebDriver driver;

    public AbstractPage(@Nonnull WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected AbstractPage(@Nonnull WebDriver driver, WebElement panel) {
        this.driver = driver;
        PageFactory.initElements(new DefaultElementLocatorFactory(panel), this);
    }

    /**
     * The method waits for the element to appear on the page
     *
     * @param parent  The parent relative to which to search
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @return element
     * @throws TimeoutException if the element was not found within timeOut
     */
    public static WebElement waitElement(SearchContext parent, By by, long timeOut) {
        return waitElement(parent, by, timeOut, true);
    }

    /**
     * The method waits for the element to appear on the page, the element can be "Disable"
     *
     * @param parent  The parent relative to which to search
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @return element
     * @throws TimeoutException if the element was not found within timeOut
     */
    public static WebElement waitDisableElement(SearchContext parent, By by, long timeOut) {
        return waitElement(parent, by, timeOut, false);
    }
    /**
     * The method waits for the element to appear on the page
     *
     * @param parent  The parent relative to which to search
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @param checkEnable to perform the check isEnabled or not
     * @return element
     * @throws TimeoutException if the element was not found within timeOut
     */
    public static WebElement waitElement(SearchContext parent, By by, long timeOut, boolean checkEnable) {
        WebElement result = null;
        long time = System.currentTimeMillis();
        while (result == null && System.currentTimeMillis() - time < timeOut) {
            try {
                List<WebElement> elements = parent.findElements(by);
                for (WebElement element : elements) {
                    if ((!checkEnable || element.isEnabled()) && element.isDisplayed()) {
                        if (result == null) {
                            result = element;
                        } else {
                            // more than one match found
                            result = null;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) {
                }
            }
        }
        if (result == null) {
            throw new TimeoutException("Element not fount by:" + by);
        }
        return result;
    }

    /**
     * The method waits for the element to appear on the page
     *
     * @param parent The parent relative to which to search
     * @param by     selector of identifying element
     * @return element
     * @throws TimeoutException if the element was not found within timeOut
     */
    public static WebElement waitElement(SearchContext parent, By by) {
        return waitElement(parent, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * The method waits for the element to appear on the page
     *
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @return element
     * @throws TimeoutException if the element was not found within timeOut
     */
    protected WebElement waitElement(By by, long timeOut) {
        return waitElement(driver, by, timeOut);
    }

    /**
     * The method waits for the element to appear on the page
     *
     * @param by selector of identifying element
     * @return element
     * @throws TimeoutException if the element was not found within timeOut
     */
    protected WebElement waitElement(By by) {
        return waitElement(driver, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Waiting for the appearance of a frame by frameBy and an element in it by elementBy.
     * Returns the found element or throws a TimeoutException,
     * if the element was not found {@code DEFAULT_EXPLICIT_TIMEOUT}.
     *
     * @param frameBy   selector of identifying the frame
     * @param elementBy selector of identifying the element
     * @return element
     */
    protected WebElement waitElementInFrame(By frameBy, By elementBy) {
        return waitElementInFrame(frameBy, elementBy, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * Waiting for the appearance of a frame by frameBy and an element in it by elementBy.
     * Returns the found element or throws a TimeoutException,
     * if the element was not found the specified timeout.
     *
     * @param frameBy   selector of identifying the frame
     * @param elementBy selector of identifying the element
     * @param timeout   maximum waiting time (ms)
     * @return element
     * @throws TimeoutException, if the element was not found within timeOut
     */
    protected WebElement waitElementInFrame(By frameBy, By elementBy, long timeout) {
        long time = System.currentTimeMillis();
        WebElement result = null;
        while (result == null && System.currentTimeMillis() - time < timeout) {
            try {
                driver.switchTo().parentFrame();
                final WebElement frame = driver.findElement(frameBy);
                driver.switchTo().frame(frame);
                result = driver.findElement(elementBy);
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) {
                }
            }
        }
        if (result == null) {
            throw new TimeoutException("Element not fount by frame:" + frameBy + " by:" + elementBy);
        }
        return result;
    }

    /**
     * The method waits for an element to appear on the page and clicks on it.
     *
     * @param parent  The parent relative to which to search
     * @param by      selector of identifying the element
     * @param timeOut maximum waiting time (ms)
     * @return element
     * @throws TimeoutException, if the element was not found within timeOut
     */
    public static WebElement waitElementAndClick(SearchContext parent, By by, long timeOut) {
        long time = System.currentTimeMillis();
        WebElement result = null;
        boolean click = false;
        while (!click && System.currentTimeMillis() - time < timeOut) {
            try {
                List<WebElement> elements = parent.findElements(by);
                for (WebElement element : elements) {
                    if (element.isEnabled() && element.isDisplayed()) {
                        if (result == null) {
                            result = element;
                        } else {
                            // more than one match found
                            result = null;
                            break;
                        }
                    }
                }
                if (result != null) {
                    result.click();
                    click = true;
                }
            } catch (Exception e) {
                log.debug("waitElementAndClick iteration", e);
            }
        }
        if (!click) {
            throw new TimeoutException("Failed to click on element by:" + by);
        }
        return result;
    }

    /**
     * Select content (Ctrl+A) and enter text.
     *
     * @param element input field
     * @param text    text
     */
    protected void selectAllAndSendKeys(WebElement element, String text) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"), text);
    }

    /**
     * The method waits for an element to appear on the page and clicks on it.
     *
     * @param parent The parent relative to which to search
     * @param by     selector of identifying the element
     * @return element
     * @throws TimeoutException, if the element was not found within timeOut
     */
    protected WebElement waitElementAndClick(SearchContext parent, By by) {
        return waitElementAndClick(parent, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * The method waits for an element to appear on the page and clicks on it.
     *
     * @param by selector of identifying the element
     * @return element
     * @throws TimeoutException, if the element was not found within timeOut
     */
    protected WebElement waitElementAndClick(By by) {
        return waitElementAndClick(driver, by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * The method expects the element to be hidden from the page
     *
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @throws TimeoutException if the element has not been hidden within the specified time
     */
    protected void waitInvisibilityElement(By by, long timeOut) {
        boolean hide;
        long time = System.currentTimeMillis();
        final WebDriver.Timeouts timeouts = driver.manage().timeouts();
        final Duration implicitWaitTimeout = timeouts.getImplicitWaitTimeout();
        timeouts.implicitlyWait(Duration.ofMillis(500));
        do {
            final List<WebElement> elements = driver.findElements(by);
            hide = elements.isEmpty();
            if (!hide) {
                hide = true;
                for (int i = 0; hide && i < elements.size(); i++) {
                    try {
                        hide = !elements.get(i).isDisplayed();
                    } catch (Exception ignore) {
                    }
                }
            }
        } while (!hide && System.currentTimeMillis() - time < timeOut);
        timeouts.implicitlyWait(implicitWaitTimeout);
        if (!hide) {
            throw new TimeoutException("Element not hide by:" + by + " time:" + (System.currentTimeMillis() - time) + "ms");
        }
    }

    /**
     * The method expects the element to be hidden from the page
     *
     * @param by selector of identifying element
     * @throws TimeoutException if the element has not been hidden within 30 seconds
     */
    protected void waitInvisibilityElement(By by) {
        waitInvisibilityElement(by, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * The method expects the element to be hidden from the page
     *
     * @param element element
     * @param timeOut maximum waiting time (ms)
     * @throws TimeoutException if the element has not been hidden within 2 minutes
     */
    public static void waitInvisibilityElement(WebElement element, long timeOut) {
        long time = System.currentTimeMillis();
        boolean hide;
        do {
            try {
                hide = !element.isDisplayed();
                if (!hide) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignore) {
                    }
                }
            } catch (Exception ignore) {
                hide = true;
            }
        } while (!hide && System.currentTimeMillis() - time < timeOut);
        if (!hide) {
            throw new TimeoutException("Element not hide:" + element);
        }
    }

    /**
     * The method expects the element to be hidden from the page
     *
     * @param element element
     * @throws TimeoutException if the element has not been hidden within 2 minutes
     */
    protected void waitInvisibilityElement(WebElement element) {
        waitInvisibilityElement(element, DEFAULT_EXPLICIT_TIMEOUT);
    }

    /**
     * The method expects the element to appear and disappear from the page
     *
     * @param parent  The parent relative to which to search
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @throws TimeoutException if the element has not been show and hidden within the specified time
     */
    public static void waitBlinkingElement(SearchContext parent, By by, long timeOut) {
        long time = System.currentTimeMillis();
        WebElement result = null;
        while (result == null && System.currentTimeMillis() - time < timeOut) {
            try {
                List<WebElement> elements = parent.findElements(by);
                for (WebElement element : elements) {
                    if (element.isEnabled() && element.isDisplayed()) {
                        if (result == null) {
                            result = element;
                        } else {
                            // more than one match found
                            result = null;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignore) {
                }
            }
        }
        if (result == null) {
            throw new TimeoutException("Element not fount by:" + by);
        }
        boolean hide;
        do {
            final List<WebElement> elements = parent.findElements(by);
            hide = true;
            for (int i = 0; hide && i < elements.size(); i++) {
                try {
                    hide = !elements.get(i).isDisplayed();
                } catch (Exception ignore) {
                }
            }
            //TODO
            System.out.println("!!!! " + System.currentTimeMillis() + " wait hide");
        } while (!hide && System.currentTimeMillis() - time < timeOut);
        if (!hide) {
            throw new TimeoutException("Element not hide by:" + by + " time:" + (System.currentTimeMillis() - time) + "ms");
        }
    }

    /**
     * The method expects the element to appear and disappear from the page
     *
     * @param by      selector of identifying element
     * @param timeOut maximum waiting time (ms)
     * @throws TimeoutException if the element has not been show and hidden within the specified time
     */
    protected void waitBlinkingElement(By by, long timeOut) {
        waitBlinkingElement(driver, by, timeOut);
    }

    /**
     * The method expects the element to appear and disappear from the page
     *
     * @param by selector of identifying element
     * @throws TimeoutException if the element has not been show and hidden within the specified time
     */
    protected void waitBlinkingElement(By by) {
        waitBlinkingElement(by, DEFAULT_EXPLICIT_TIMEOUT);
    }


    /**
     * Take a screenshot of an element
     * The screenshot is folded into folders to correspond of the stack trace
     *
     * @param element element
     * @return screenshot file
     * @throws Exception
     */
    public static File screen(TakesScreenshot element) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder fileName = new StringBuilder("report").append(File.separatorChar);
        fileName.append("screen").append(File.separatorChar);
        boolean findTest = false;
        boolean isScreenMethod = false;
        for (int i = stackTrace.length - 1; i >= 2 && !isScreenMethod; i--) {
            StackTraceElement stackTraceElement = stackTrace[i];
            final String testName = stackTraceElement.getClassName();
            if (!findTest) {
                findTest = testName.startsWith(SCREEN_INCLUDE_PACKAGE);
                if (findTest) {
                    fileName.append(testName.replace('.', '_')).append(File.separatorChar);
                    fileName.append(stackTraceElement.getMethodName()).append(File.separatorChar);
                    fileName.append(stackTraceElement.getLineNumber()).append('_');
                }
            } else {
                if (testName.startsWith(SCREEN_INCLUDE_PACKAGE)) {
                    isScreenMethod = "screen".equals(stackTraceElement.getMethodName());
                    if (!isScreenMethod) {
                        fileName.append(stackTraceElement.getMethodName()).append('_');
                        fileName.append(stackTraceElement.getLineNumber()).append('_');
                    }
                }
            }
        }
        fileName.setLength(fileName.length() - 1);
        final byte[] bytes = element.getScreenshotAs(OutputType.BYTES);
        int i = 0;
        int start = fileName.lastIndexOf(File.separator);
        if (start >= 0 && fileName.length() - start > 249) {
            fileName.setLength(start + 249);
        }
        String countFileName = fileName + ".png";

        File screen;
        while ((screen = new File(countFileName)).exists()) {
            i++;
            countFileName = fileName + "_" + i + ".png";
        }
        screen.getParentFile().mkdirs();
        try (final FileOutputStream out = new FileOutputStream(screen)) {
            out.write(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (final FileInputStream in = new FileInputStream(screen)) {
            Allure.addAttachment(countFileName, "image/png", in, ".png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return screen;
    }

    /**
     * Take a screenshot of an element
     * The screenshot is folded into folders to correspond of the stack trace
     *
     * @return screenshot file
     * @throws Exception
     */
    public File screen() {
        return screen((TakesScreenshot) driver);
    }

    /**
     * Switch to last opened window
     */
    protected WebDriver switchToLastTab() {
        final Iterator<String> iterator = driver.getWindowHandles().iterator();
        String last = null;
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return driver.switchTo().window(last);
    }

    /**
     * Switch to the window with the title
     */
    protected void waitTab(String expectedTitle, boolean ignoreCase) {
        long time = System.currentTimeMillis();

        while (System.currentTimeMillis() - time < DEFAULT_EXPLICIT_TIMEOUT) {
            for (String window : driver.getWindowHandles()) {
                final String title = driver.switchTo().window(window).getTitle();
                if ((ignoreCase && expectedTitle.equalsIgnoreCase(title)) ||
                        expectedTitle.equals(title)) {
                    return;
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
        throw new TimeoutException("Failed to wait tab by title:" + expectedTitle);
    }


}
