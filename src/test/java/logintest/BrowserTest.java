package logintest;
import com.microsoft.playwright.*;

import appprocessor.AppTest;

import org.testng.annotations.Test;

public class BrowserTest extends AppTest
{
    @Test
    public void DemoTest()
    {
        try
        {
            Playwright createContext = Playwright.create();
            Browser browser = createContext.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext browserContext = browser.newContext();
            Page page = browserContext.newPage();
            page.navigate("https://eventhub.rahulshettyacademy.com/");

            System.out.println(page.title());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void Login()
    {
    	//
    }
}