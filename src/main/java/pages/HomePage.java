package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.PlaywrightLocators;

public class HomePage extends BasePage
{
	public HomePage(Page page)
	{
		super(page);
		this.page = page;
		homeBtnId = new PlaywrightLocators("Menu Button Id", this.page.locator("#nav-home"));
		eventsBtnRole = new PlaywrightLocators("Events Button Id", this.page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Events")));
		myBookingsBtnTestId = new PlaywrightLocators("My Bookings Button Id", this.page.getByTestId("nav-bookings"));
		apiDocksBtnXpath = new PlaywrightLocators("API Docs Button Xpath", this.page.locator("//a[text()='API Docs']"));
		adminBtnXpath = new PlaywrightLocators("Admin Button Xpath", this.page.locator("//button[text()='Admin']"));
		logoutBtnRole = new PlaywrightLocators("Logout Button Role", this.page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logout")));
	}
	
	private Page page;
	private PlaywrightLocators homeBtnId;
	private PlaywrightLocators eventsBtnRole;
	private PlaywrightLocators myBookingsBtnTestId;
	private PlaywrightLocators apiDocksBtnXpath;
	private PlaywrightLocators adminBtnXpath;
	private PlaywrightLocators logoutBtnRole;
	private PlaywrightLocators bookingEventsRole;
	
	public void LoginToPortalAndNavigateToHomepage()
	{
		LoginPage lgPage = new LoginPage(this.page);
		lgPage.LoginEventHubPortal();
	}
	
	public void VerifyThatPortalHasCorrectMenus()
	{
		List<String> expectedMenus = new ArrayList<String>(Arrays.asList("Home", "Events", "My Bookings", "API Docs", "Admin", "Unknown"));
		for (String menu : expectedMenus)
		{
			if (menu.equals("Home"))
			{
				String actualText = this.getTextOfElement(homeBtnId, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else if (menu.equals("Events"))
			{
				String actualText = this.getTextOfElement(eventsBtnRole, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else if (menu.equals("My Bookings"))
			{
				String actualText = this.getTextOfElement(myBookingsBtnTestId, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else if (menu.equals("API Docs"))
			{
				String actualText = this.getTextOfElement(apiDocksBtnXpath, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else if (menu.equals("Admin"))
			{
				String actualText = this.getTextOfElement(adminBtnXpath, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else
			{
				this.report.LogResult(true, "This is Unknown menu and not present on the portal");
			}
		}
	}
	
	private void getTextomparisonLog(String actualText, String expectedText)
	{
		if (actualText.equals(expectedText))
		{
			this.report.LogResult(false, "Menu: [" + expectedText + "] displayed successfully");
		}
		else
		{
			this.report.LogResult(false, "Menu: [" + expectedText + "] not displayed");
		}
	}
}
