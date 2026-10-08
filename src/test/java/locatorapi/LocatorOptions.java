package locatorapi;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.KeyboardModifier;
import com.microsoft.playwright.options.MouseButton;

import appprocessor.AppTest;

public class LocatorOptions extends AppTest
{
	@Test(testName="This is to verify Locator Opti")
	public void VerifyLocatorMatchingTechnique() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		page.onDialog(dialog -> {
	        System.out.println(dialog.message());
	        dialog.dismiss();
	      });
	      page.evaluate("alert('1')");
	    Thread.sleep(3000);
	}
}
