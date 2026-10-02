package locatorpractice;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.AppTest;
import appprocessor.PlaywrightCommands;

public class LocatorOperatorTest extends AppTest
{
	@Test(testName="This is to verify element is Contact link is highlighted using locator matching technique")
	public void VerifyLocatorMatchingTechnique() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		
		 Locator gridElement = page.locator("#role-locators div.grid div");
		 Locator contactDivElement = gridElement.filter(new
		 Locator.FilterOptions().setHas( page.getByRole(AriaRole.HEADING, new
		 Page.GetByRoleOptions().setName("Navigation")))); 
		 Locator elementToHighlight = contactDivElement.locator("ul li")
				 .filter(new Locator.FilterOptions().setHasText("Contact"));
		 
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}
	
	@Test(testName="This is to verify element using two and more locator")
	public void VerifyElementIsLocatedUsingMultipleLocators() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		
		 Locator gridElement = page.locator("#title-locators");
		 Locator elementToHighlight = gridElement.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Save"))
				 								.and(page.getByTitle("Click to save your changes"));
		 
		elementToHighlight.scrollIntoViewIfNeeded();
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}
	
	@Test(testName="This is to verify element using one of the two alternative locators")
	public void VerifyElementIsLocatedUsingOneOfTheALternativeLocators() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		
		 Locator gridElement = page.locator("#title-locators");
		 Locator elementToHighlight = gridElement.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Save"))
				 								.or(page.getByTitle("Click to save your changes"));
		 
		elementToHighlight.scrollIntoViewIfNeeded();
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}
}