package locatorapi;

import java.util.List;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;

import appprocessor.AppTest;

public class LocatorApiTest extends AppTest
{
	@Test(testName="This is to verify various Playwright actions")
	public void VerifyLocatorMatchingTechnique() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		
		// all() method
		List<Locator> shippingMethodOptions = page.locator("fieldset:has(legend:text-is('Shipping Method'))")
											.locator("label").all();
		System.out.println("Shipping Methods options: " + shippingMethodOptions.size());
		for (Locator element : shippingMethodOptions)
		{
			element.scrollIntoViewIfNeeded();
			element.highlight();
			Thread.sleep(2000);
			element.hideHighlight();
		}
		
		// allInnerTexts() method
		System.out.println("##### allInnerText() #####");
		List<String> navigationList = page.locator("#role-locators div.grid div")
										  .filter(new Locator.FilterOptions().setHas(
										   page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Navigation"))))
										  .allInnerTexts();
		for (String navigationName : navigationList)
		{
			System.out.println(navigationName);
		}
		
		// allTextContents() method
		System.out.println("##### allTextContents() #####");
		List<String> navigationContents = page.locator("#role-locators div.grid div")
										  .filter(new Locator.FilterOptions().setHas(
										   page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Navigation"))))
										  .allTextContents();
		for (String navigationName : navigationContents)
		{
			System.out.println(navigationName);
		}
		
		// and() method
		Locator toggleButton = page.getByRole(AriaRole.BUTTON).and(page.getByText("Toggle Button"));
		toggleButton.scrollIntoViewIfNeeded();
		toggleButton.highlight();
		Thread.sleep(3000);
		toggleButton.hideHighlight();
		
		//ariaSnapshot() method
		System.out.println("##### ariaSnapshot() #####");
		Locator formElements = page.locator("#role-locators div.grid div:nth-child(2)");
		System.out.println(formElements.ariaSnapshot());
		
		// blur() method
		toggleButton.blur();
		Thread.sleep(3000);
		
		// boundingBox() method
		System.out.println("##### boundingBox() #####");
		BoundingBox buttonBox = toggleButton.boundingBox();
		double ht = buttonBox.height;
		double width = buttonBox.width;
		double x = buttonBox.x;
		double y = buttonBox.y;
		System.out.println("Height of Button: " + ht + " and Width of Button: " + width + " and x: " + x + " and y: " + y);
		
		// check() method
		page.getByLabel("Accept terms").check();
		Thread.sleep(3000);
		
		// clear() method
		page.getByRole(AriaRole.TEXTBOX).clear();
		
		// click() method
		toggleButton.click();
		
		
	}
}