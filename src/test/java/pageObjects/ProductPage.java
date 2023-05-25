package pageObjects;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class ProductPage extends LoadableComponent<ProductPage> {
    private final WebDriver driver;

    @FindBy(className = "t-store__prod-popup__btn")
    private WebElement addToCartButton;

    @FindBy(className = "t706__carticon-wrapper")
    private WebElement cartButton;

    @FindBy(xpath = "//*[@class=\"t706__cartpage-open-form t-btn\" or @class=\"t706__sidebar-continue t-btn\"]")
    private List<WebElement> checkoutButtons;

    @FindBy(id = "form561378404")
    private WebElement form;

    @FindBy(id = "input_1496239431201")
    private WebElement fullNameInput;

    @FindBy(css = "#form561378404 .t-input.t-input-phonemask")
    private WebElement phoneInput;

    @FindBy(id = "input_1627385047591")
    private WebElement countryOrRegionMapInput;

    @FindBy(id = "input_1630305196291")
    private WebElement deliveryAddressInput;

    @FindBy(name = "tildadelivery-city")
    private WebElement cityInput;

    @FindBy(css = "#city-searchbox .searchbox-list-item.t-text")
    private WebElement cityHint;

    @FindBy(name = "tildadelivery-userinitials")
    private WebElement recipientInput;

    @FindBy(name = "tildadelivery-street")
    private WebElement streetInput;

    @FindBy(css = "#street-searchbox .searchbox-list-item.t-text")
    private WebElement streetHint;

    @FindBy(name = "tildadelivery-house")
    private WebElement houseInput;

    @FindBy(css = "#house-searchbox .searchbox-list-item.t-text")
    private WebElement houseHint;

    @FindBy(name = "tildadelivery-aptoffice")
    private WebElement apartmentInput;

    @FindBy(id = "input_1683878302422")
    private WebElement deliveryPhoneInput;

    @FindBy(id = "input_1683878343596")
    private WebElement emailInput;

    @FindBy(id = "error_1496239478607")
    private WebElement phoneError;

    @FindBy(id = "error_1683878302422")
    private WebElement deliveryPhoneError;

    @FindBy(css = "#form561378404 .t-form__inputsbox .t-form__errorbox-item.js-rule-error.js-rule-error-phone")
    private WebElement bottomPhoneError;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPhoneError() {
        return phoneError.getText();
    }

    public String getDeliveryPhoneError() {
        return deliveryPhoneError.getText();
    }

    public String getBottomPhoneError() {
        return bottomPhoneError.getText();
    }

    public void addToCart() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.and(ExpectedConditions.elementToBeClickable(addToCartButton),ExpectedConditions.visibilityOf(addToCartButton)));

        try {
            addToCartButton.click();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(cartButton));
    }

    public void checkout() {
        clickOnCartButton();
        clickOnCheckoutButton();
    }

    public void fillFormWithInvalidPhone() {
        fillMainPartOfFormWithInvalidPhone();
        fillDeliveryPartOfFormWithInvalidPhone();
    }

    public void submitForm(){
        form.submit();
    }
    private void fillMainPartOfFormWithInvalidPhone() {
        fullNameInput.sendKeys("Иванов Иван Иванович");
        phoneInput.sendKeys("0000000000");
        countryOrRegionMapInput.sendKeys("Волгоград");
        deliveryAddressInput.sendKeys("ул Саранская 117");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(cityInput));
    }

    private void fillDeliveryPartOfFormWithInvalidPhone() {
        enterCity();

        recipientInput.sendKeys("Иванов Иван Иванович");

        enterStreet();
        enterHouse();

        apartmentInput.sendKeys("-");
        deliveryPhoneInput.sendKeys("+7 (000) 000-00-00");
        emailInput.sendKeys("test@gmail.com");
    }

    private void enterStreet() {
        streetInput.sendKeys("ул Саранская");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(streetHint));
        streetHint.click();
    }

    private void enterCity() {
        cityInput.click();
        cityInput.clear();
        cityInput.sendKeys("Волгоград");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(cityHint));

        cityHint.click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(cityHint));

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(recipientInput));
    }

    private void enterHouse() {
        houseInput.sendKeys("117");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(houseHint));

        houseHint.click();
    }

    private void clickOnCartButton() {
        cartButton.click();
    }

    private void clickOnCheckoutButton() {
        for (WebElement checkoutButton : checkoutButtons) {
            if (checkoutButton.isDisplayed()){
                checkoutButton.click();
                break;
            }
        }

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(form));
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(addToCartButton));
        } catch (Exception e) {
            fail();
            System.out.println(e.getMessage());
        }
    }
}
