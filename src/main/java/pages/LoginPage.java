package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Page;

import appprocessor.PlaywrightLocators;
import appprocessor.PlaywrightWaits;

public class LoginPage extends BasePage
{
	public LoginPage(Page page)
	{
		super(page);
		this.page = page;
		emailTextBoxId = new PlaywrightLocators("Email Textbox Id", this.page.locator("#email"));
		passwordTextBoxId = new PlaywrightLocators("Passowrd Textbox Id", this.page.locator("#password"));
		loginBtnId = new PlaywrightLocators("Login Button Id", this.page.locator("#login-btn"));
		menuBtnId = new PlaywrightLocators("Menu Button Id", this.page.locator("#nav-home"));
		eventsBtnId = new PlaywrightLocators("Events Button Id", this.page.locator("#nav-events"));
		myBookingsBtnId = new PlaywrightLocators("My Bookings Button Id", this.page.locator("#nav-bookings"));
		apiDocksBtnXpath = new PlaywrightLocators("API Docs Button Xpath", this.page.locator("//a[text()='API Docs']"));
		adminBtnXpath = new PlaywrightLocators("Admin Button Xpath", this.page.locator("//button[text()='Admin']"));
		logoutBtnXpath = new PlaywrightLocators("Logout Button Xpath", this.page.locator("//button[text()='Logout']"));
	}
	
	private Page page;
	private PlaywrightLocators emailTextBoxId;
	private PlaywrightLocators passwordTextBoxId;
	private PlaywrightLocators loginBtnId;
	private PlaywrightLocators menuBtnId;
	private PlaywrightLocators eventsBtnId;
	private PlaywrightLocators myBookingsBtnId;
	private PlaywrightLocators apiDocksBtnXpath;
	private PlaywrightLocators adminBtnXpath;
	private PlaywrightLocators logoutBtnXpath;
	
	public void LoginEventHubPortal()
	{
		this.NavigateApplicationUrl();
		PlaywrightWaits wait = new PlaywrightWaits(this.page);
		if (!wait.waitForVisible(logoutBtnXpath, 1, false))
		{
			this.EnterText(emailTextBoxId, "brookemilan@mailfirefly.com", 10);
			this.EnterText(passwordTextBoxId, "MyLife92@", 10);
			this.Click(loginBtnId, 10);	
		}
	}
	
	public void VerifyThatPortalHasCorrectMenus()
	{
		List<String> expectedMenus = new ArrayList<String>(Arrays.asList("Home", "Events", "My Bookings", "API Docs", "Admin", "Unknown"));
		for (String menu : expectedMenus)
		{
			if (menu.equals("Home"))
			{
				String actualText = this.getTextOfElement(menuBtnId, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else if (menu.equals("Events"))
			{
				String actualText = this.getTextOfElement(eventsBtnId, 10);
				this.getTextomparisonLog(actualText, menu);
			}
			else if (menu.equals("My Bookings"))
			{
				String actualText = this.getTextOfElement(myBookingsBtnId, 10);
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
