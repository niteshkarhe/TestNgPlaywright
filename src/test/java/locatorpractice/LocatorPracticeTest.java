package locatorpractice;

import java.util.regex.Pattern;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.AppTest;
import appprocessor.PlaywrightCommands;

public class LocatorPracticeTest extends AppTest
{
	@Test(testName="This is to verify form locators")
	public void VerifyFormLocators() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://selectorshub.com/xpath-practice-page/");
		Locator emailInput = page.locator("input[name='email']");
		Locator passwordInput = page.getByTitle("Password");
		Locator companyInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your company"));
		Locator mobileInput = page.locator("//div[@class='element-companyId']//input[@name='mobile number']");
		Locator countryInput = page.locator(".element-companyId")
			    .locator("label")
			    .filter(new Locator.FilterOptions().setHasText("Country"))
			    .locator("input");
		Locator submitBtnInput = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
		
		EnterText(emailInput, "nkarhe@gmail.com", 5);
		EnterText(passwordInput, "ModiChutya", 5);
		EnterText(companyInput, "Deutche", 5);
		EnterText(mobileInput, "9022041789", 5);
		EnterText(countryInput, "India", 5);
		submitBtnInput.click(new Locator.ClickOptions().setTimeout(3000));
		Thread.sleep(5000);
	}
	
	@Test(testName="Verify the table locators")
	public void VerifyUserTableLocators() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://selectorshub.com/xpath-practice-page/");
		Locator tableFirstRowCheckboxInput = page.locator("#resultTable tbody").getByRole(AriaRole.CHECKBOX).first();
		Locator tableThirdRowCheckboxInput = page.locator("#resultTable tbody")
											     .getByRole(AriaRole.CHECKBOX)
											     .nth(2);
		
		scrollIntoView(tableFirstRowCheckboxInput);
		tableFirstRowCheckboxInput.check();
		tableThirdRowCheckboxInput.check();
		Thread.sleep(5000);
	}
	
	@Test(testName="Verify the table locators")
	public void VerifyDropdownDisabledElementPopupAlert() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://selectorshub.com/xpath-practice-page/");
		// Locator sectionHeaderLocator = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Dropdown, Disabled element, Popup Alert and Complex Element"));
		// Locator sectionHeaderLocator = page.locator("h6.elementor-heading-title:has-text('Dropdown, Disabled element, Popup Alert and Complex Element')");
		Locator sectionHeaderLocator = page.locator("h6.elementor-heading-title:text('Disabled element, Popup')");
		Locator parentSectionLocator = page.locator("div.elementor-element.e-child[data-element_type='container']")
			    .filter(new Locator.FilterOptions().setHas(sectionHeaderLocator));
		
		
		scrollIntoView(sectionHeaderLocator);
		parentSectionLocator.highlight();
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