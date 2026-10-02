package locatorpractice;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.AppTest;
import appprocessor.PlaywrightCommands;

public class LocatorFilterPracticeTest extends AppTest
{
	@Test(testName="This is to verify element is highlighted using setHasText()")
	public void VerifyFormLocators() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://selectorshub.com/xpath-practice-page/");
		// Locate element having text - "Inspect this element, you will see comment just below the html of this element in DOM"
		Locator parentDivLocator = page.locator("div.elementor-element.e-child > div > span");
		Locator elementToHighlight = parentDivLocator.filter(new Locator.FilterOptions().setHasText("Inspect this element, you will see comment just below the html of this element in DOM"));
		scrollIntoView(elementToHighlight);
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}
	
	@Test(testName="This is to verify element is highlighted using setHasNotText()")
	public void VerifyFilterByNotHavingText() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		// Locate element having text - "Inspect this element, you will see comment just below the html of this element in DOM"
		Locator parentSectionLocator = page.locator("#role-locators > div.grid");
		Locator elementToHighlight = parentSectionLocator.locator("button").filter(new Locator.FilterOptions().setHasNotText("Primary Action"));
		scrollIntoView(elementToHighlight);
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}
	
	@Test(testName="This is to verify element is highlighted using setHas() to filter child elements")
	public void VerifyFilterBySetHas() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		// Locate element having text - "Inspect this element, you will see comment just below the html of this element in DOM"
		Locator parentSectionLocator = page.locator("#role-locators > div.grid div");
		Locator childLocator = parentSectionLocator
				.filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.HEADING, 
						new Page.GetByRoleOptions().setName("Navigation"))));
		Locator elementToHighlight = childLocator.getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Products"));
		
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}
	
	@Test(testName="This is to verify element is highlighted using setHasNot() to filter child elements")
	public void VerifyFilterBySetHasNot() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		// Locate element having text - "Inspect this element, you will see comment just below the html of this element in DOM"
		Locator parentSectionLocator = page.locator("#role-locators > div.grid div");
		Locator childLocator = parentSectionLocator
				.filter(new Locator.FilterOptions().setHasNot(page.getByRole(AriaRole.HEADING, 
						new Page.GetByRoleOptions().setName("Buttons"))))
				.filter(new Locator.FilterOptions().setHasNot(page.getByRole(AriaRole.HEADING,
						new Page.GetByRoleOptions().setName("Navigation"))))	;
		Locator elementToHighlight = childLocator.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Username:"));
		
		elementToHighlight.highlight();
		Thread.sleep(5000);
	}

	private void EnterText(Locator locator, String value, float time)
	{
		locator.click(new Locator.ClickOptions().setTimeout(time * 1000));
		locator.fill(value, new Locator.FillOptions().setTimeout(time * 1000));
	}
	
	private void scrollIntoView(Locator locator)
	{
		locator.scrollIntoViewIfNeeded();
	}
}