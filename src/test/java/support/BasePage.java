package support;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageUIs.BasePageUI;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.Arrays;

public class BasePage {
    public WebDriverWait wait;
    public WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstance.LONG_TIMEOUT));
    }
    public WebDriverWait getWait(){
        if (wait == null){
            wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstance.LONG_TIMEOUT));
        }
        return wait;
    }
    public void waitForElementVisible(String locator){
        getWait().until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locator)));
    }
    public boolean waitForListElementInVisible(String locator){
       return getWait().until(ExpectedConditions.invisibilityOfAllElements(getListElement((locator))));
    }
    public void waitForElementClickAble(String locator ,String...elementName){
        getWait().until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicString(locator,elementName))));
    }
    public void waitForElementSelected(String locator, String...elementName){
        getWait().until(ExpectedConditions.elementToBeSelected(getByLocator(getDynamicString(locator,elementName))));
    }
    public boolean waitAllLoadingIconInvisible(){
       return waitForListElementInVisible(BasePageUI.LOADING_ICON);
    }
    public void waitForElementAttributeNotEmpty(String locator, String attributeName, String... dynamicValues) {
        String finalLocator = getDynamicString(locator, dynamicValues);
        getWait().until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                String value = getElement(finalLocator).getAttribute(attributeName);
                return value != null && !value.trim().isEmpty();
            }

            @Override
            public String toString() {
                return String.format("attribute '%s' to be not empty for element %s", attributeName, finalLocator);
            }
        });
    }

    public void sendKeyToElement(String locator, String keyToSend) {
        WebElement element = getElement(locator);
        element.clear();
        if (!element.getAttribute("value").isEmpty()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
        }
        element.sendKeys(keyToSend);
    }
    public void clickToElement(String locator){
        driver.findElement(getByLocator(locator)).click();
    }
    public String getDynamicString(String locator, String...name){
        return String.format(locator,name);
    }
    public List<WebElement> getListElement(String locator){
        List<WebElement> listElement = driver.findElements(getByLocator(locator));
        return  listElement;
    }
    public String getElementText(String locator){
        return getElement(locator).getText();
    }
    public WebElement getElement(String locator){
        return  driver.findElement(getByLocator(locator));
    }
    public String getElementAttribute(String locator){
        return driver.findElement(getByLocator(getDynamicString(locator))).getAttribute("value");
    }
    public String getElementAttribute(String locator, String attributeName){
        return  getElement(locator).getAttribute(attributeName);
    }

    public String getElementValueByJS(String locator) {
        waitForElementAttributeNotEmpty(locator,"value");
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].value;", getElement(locator));
    }
    @Step("Upload files: {0}")
    public void uploadMultipleFiles(String... fileName){
        String filePath = GlobalConstance.UPLOAD_PATH;
        String fullFileName = "";

        for (String file: fileName) {
            fullFileName += filePath + file + "\n";
        }

        fullFileName = fullFileName.trim();
        getElement(BasePageUI.UPLOAD_FILE_TYPE).sendKeys(fullFileName);
    }
    public Dimension getElementSize(String locator){
        return getElement(locator).getSize();
    }
    public By getByLocator(String locatorType){
        if(locatorType == null || locatorType.isEmpty()){
            throw new RuntimeException("Locator cannot be null or empty ");
        }
        String type = locatorType.toLowerCase();
        if (type.startsWith("id=")){
            return By.id(locatorType.substring(3));
        }
        if (type.startsWith("class=")){
            return By.className(locatorType.substring(6));
        }
        if (type.startsWith("name=")){
            return By.name(locatorType.substring(5));
        }
        if (type.startsWith("css=")){
            return By.cssSelector(locatorType.substring(4));
        }
        if (type.startsWith("xpath=")){
            return By.xpath(locatorType.substring(6));
        }
        else {
            throw new RuntimeException("Locator type is not support !");
        }
    }
    public boolean isSuccessMessageDisplayed() {
        waitForElementVisible(BasePageUI.SUCCESS_MESSAGE);
        return isElementDisPlayed(BasePageUI.SUCCESS_MESSAGE);
    }
    public void selectItemInCustomDropdown(String parentLocator, String childLocator, String expectedItem){
        getElement((parentLocator)).click();
        wait = new WebDriverWait(driver,Duration.ofSeconds(GlobalConstance.LONG_TIMEOUT));
        sleepInSecond(1);
        List<WebElement> allItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childLocator)));
        sleepInSecond(1);
        for (WebElement item :allItems) {
            if (item.getText().trim().equals(expectedItem)){
                item.click();
                break;
            }
        }
    }
    public void sleepInSecond(long time){
        try {
            Thread.sleep(time * 1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public boolean isElementSelected(String locator,String elementName){
        return getElement(getDynamicString(locator,elementName)).isSelected();
    }
    public void clickToElementByJS(String locator,String elementName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getElement(getDynamicString(locator,elementName)));
        sleepInSecond(1);
    }
    public void closeBrowser(){
        driver.quit();
    }
    public void getURL(String url){
        driver.get(url);
    }
    public boolean isElementDisPlayed(String locator){
        if (driver.findElements(getByLocator(locator)).size() > 0){
            return true;
        }
        return false;
    }
}
