import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.WearPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongsleeveSearchTest {
    private static final String query = "Лонгслив White&Green";
    private static final WebDriver driver = new ChromeDriver();;
    private static final WearPage wearPage = new WearPage(driver);

    @BeforeAll
    public static void searchQuery(){
        wearPage.get();
        wearPage.search(query);
    }

    @Test
    public void foundOnlyOneProduct(){
        assertEquals(1, wearPage.getProductsCount());
    }

    @Test
    public void nameEqualsQuery(){
        foundOnlyOneProduct();
        assertEquals(query, wearPage.getProductName());
    }

    @Test
    public void priceEquals2800(){
        foundOnlyOneProduct();
        assertEquals(2800, wearPage.getProductPrice());
    }

    @AfterAll
    public static void quit() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}
