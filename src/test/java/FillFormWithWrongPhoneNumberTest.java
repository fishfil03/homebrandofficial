import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.ProductPage;
import pageObjects.WearPage;

public class FillFormWithWrongPhoneNumberTest {
    private static final String query = "Футболка Oversize";
    private static final String errorText = "Укажите, пожалуйста, корректный номер телефона";
    private static final WebDriver driver = new ChromeDriver();
    private static final WearPage wearPage = new WearPage(driver);
    private static ProductPage productPage;

    @BeforeAll
    public static void checkout() {
        wearPage.get();
        wearPage.search(query);

        productPage = wearPage.clickOnFirstProduct();
        productPage.addToCart();
        productPage.checkout();
        productPage.fillFormWithInvalidPhone();
        productPage.submitForm();
    }

    @Test
    public void isPhoneErrorCorrect() {
        Assertions.assertEquals(errorText, productPage.getPhoneError());
    }

    @Test
    public void isDeliveryPhoneErrorCorrect() {
        Assertions.assertEquals(errorText, productPage.getDeliveryPhoneError());
    }

    @Test
    public void isBottomPhoneErrorCorrect() {
        Assertions.assertEquals(errorText, productPage.getBottomPhoneError());
    }

    @AfterAll
    public static void quit() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
