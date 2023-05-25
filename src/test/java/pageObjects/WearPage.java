package pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
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

public class WearPage extends LoadableComponent<WearPage> {
    private final WebDriver driver;

    @FindBy(className = "t-store__filter__search-mob-btn")
    private WebElement mobileSearchButton;

    @FindBy(name = "query")
    private WebElement searchInput;

    @FindBy(className = "t-store__filter__chosen-bar")
    private WebElement appliedFiltersPopup;

    @FindBy(className = "t-store__card")
    private List<WebElement> products;

    @FindBy(css = ".t-store__card .js-product-name")
    private WebElement productName;

    @FindBy(css = ".t-store__card .js-product-price")
    private WebElement productPrice;

    public WearPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void search(String query) {
        if (mobileSearchButton.isDisplayed()) {
            clickOnMobileSearchButton();
        }

        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(appliedFiltersPopup));
    }

    public int getProductsCount() {
        return products.size();
    }

    public String getProductName() {
        return productName.getText();
    }

    public int getProductPrice() {
        var priceWithoutWhiteSpaces = productPrice.getText().replaceAll("\\s+","");

        return Integer.parseInt(priceWithoutWhiteSpaces);
    }

    public ProductPage clickOnFirstProduct(){
        products.get(0).click();
        return new ProductPage(driver);
    }

    private void clickOnMobileSearchButton() {
        mobileSearchButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(searchInput));
    }

    @Override
    protected void load() {
        driver.get("https://homebrandofficial.ru/wear");
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.or(ExpectedConditions.visibilityOf(searchInput),
                            ExpectedConditions.visibilityOf(mobileSearchButton)));
        } catch (TimeoutException e) {
            fail();
            System.out.println(e.getMessage());
        }
    }
}
