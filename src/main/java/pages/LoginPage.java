package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

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
		logoutBtnRole = new PlaywrightLocators("Logout Button Role", this.page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Logout")));
	}
	
	private Page page;
	private PlaywrightLocators emailTextBoxId;
	private PlaywrightLocators passwordTextBoxId;
	private PlaywrightLocators loginBtnId;
	private PlaywrightLocators logoutBtnRole;
	
	public void LoginEventHubPortal()
	{
		try
		{
			this.NavigateApplicationUrl();
			PlaywrightWaits wait = new PlaywrightWaits(this.page);
			if (!wait.waitForVisible(logoutBtnRole, 3, false))
			{
				this.EnterText(emailTextBoxId, "brookemilan@mailfirefly.com", 10);
				this.EnterText(passwordTextBoxId, "MyLife92@", 10);
				this.Click(loginBtnId, 10);	
				wait.sleep(5);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
