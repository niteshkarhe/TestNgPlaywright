package appprocessor;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import configsetup.PlaywrightConfig;
import lombok.Getter;
import lombok.Setter;

public class AppTest extends BaseTest
{
    public static PlaywrightConfig playwrightConfig = initializeConfig();

    @Getter
    @Setter
    protected static Playwright playwrightContext;

    @Getter
    @Setter
    protected static Browser browser;

    @Getter
    @Setter
    protected static BrowserContext context;

    @Getter
    @Setter
    public static Page page;

    @BeforeSuite
    public static void startBrowserPage()
    {
        if (playwrightConfig.getBrowserScope() != null && playwrightConfig.getBrowserScope().equals("suite"))
        {
            playwrightContext = Playwright.create();
            initializePageBrowser();
        }
    }

    @AfterSuite
    public static void stopBrowserPage()
    {
        if (playwrightConfig.getBrowserScope() != null && playwrightConfig.getBrowserScope().equals("suite"))
        {
            if (context != null)
            {
                context.close();
            }

            if (browser != null)
            {
                browser.close();
            }

            if (playwrightConfig != null)
            {
                playwrightContext.close();
            }
        }
    }

    @BeforeClass
    public void startClass()
    {
    	if (playwrightConfig.getBrowserScope() != null && playwrightConfig.getBrowserScope().equals("class"))
    	{
    		playwrightContext = Playwright.create();
    		initializePageBrowser();
    	}
    }
    
    @AfterClass
    public void endClass()
    {
    	if (playwrightConfig.getBrowserScope() != null && playwrightConfig.getBrowserScope().equals("class"))
    	{
    		if (context != null)
            {
                context.close();
            }

            if (browser != null)
            {
                browser.close();
            }

            if (playwrightConfig != null)
            {
                playwrightContext.close();
            }
    	}
    }
    
    @BeforeMethod
    public void startTest()
    {
    	if (playwrightConfig.getBrowserScope() != null && playwrightConfig.getBrowserScope().equals("method"))
    	{
    		playwrightContext = Playwright.create();
    		initializePageBrowser();
    	}
    }
    
    @AfterMethod
    public void endTest()
    {
    	if (playwrightConfig.getBrowserScope() != null && playwrightConfig.getBrowserScope().equals("method"))
    	{
    		if (context != null)
            {
                context.close();
            }

            if (browser != null)
            {
                browser.close();
            }

            if (playwrightConfig != null)
            {
                playwrightContext.close();
            }
    	}
    }

    public static Page createNewPage()
    {
    	if (context == null)
    	{
    		throw new NullPointerException("Browser context is null. Ensure browser is initialized before create a new page.");
    	}
    	
    	Page page = context.newPage();
    	return page;
    }
    
    private static void initializePageBrowser()
    {
        try
        {
            browser = new AppTest().getBrowserReference();
            if (browser == null)
            {
                throw new NullPointerException("Browser instance is null. Ensure browser is initialized before creating new page");
            }

            context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null).setAcceptDownloads(true));
            page = context.newPage();
            if (page == null)
            {
                throw new NullPointerException("Page instance is null. Ensure page is initialized before creating new page.");
            }

            if (playwrightConfig.getApplicationUrl() != null && !playwrightConfig.getApplicationUrl().isEmpty())
            {
                page.navigate(playwrightConfig.getApplicationUrl());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Browser getBrowserReference()
    {
        switch (playwrightConfig.getBrowserName())
        {
            case "chrome": return getChromeBrowser();
            default: return getChromeBrowser();
        }
    }

    private Browser getChromeBrowser()
    {
        BrowserType.LaunchOptions chromeOptions = new BrowserType.LaunchOptions();
        try
        {
            chromeOptions.args = new ArrayList<String>(Arrays.asList("--disable-gpu", "--disable-extensions", "--disable-popup-blocking", "--start-maximized"));
            if (playwrightConfig.getFileDownloadPath() != null && !playwrightConfig.getFileDownloadPath().isEmpty())
            {
                chromeOptions.setDownloadsPath(Paths.get(playwrightConfig.getFileDownloadPath()));
            }

            if (playwrightConfig.isHeadlessBrowser())
            {
                chromeOptions.setHeadless(true);
            }
            else
            {
                chromeOptions.setHeadless(false);
            }

            if (playwrightConfig.getWindowSize() != null && !playwrightConfig.getWindowSize().isEmpty())
            {
                chromeOptions.args.add("--window-size" + playwrightConfig.getWindowSize());
            }

            if (playwrightConfig.isIncognitoMode())
            {
                chromeOptions.args.add("--incognito");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (playwrightContext == null)
        {
            throw new NullPointerException("Playwright instance is null. Ensure playwright is initialized before launching the browser.");
        }

        return playwrightContext.chromium().launch(chromeOptions);
    }

    private static PlaywrightConfig initializeConfig()
    {
        try
        {
            String rootDirPath = System.getProperty("user.dir");
            String projectConfigPath = rootDirPath + "\\src\\main\\java\\configs\\projectconfigs.json";
            JsonReader reader = new JsonReader(new FileReader(projectConfigPath));
            Gson gson = new Gson();
            return gson.fromJson(reader, PlaywrightConfig.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
