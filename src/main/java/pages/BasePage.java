package pages;

import com.microsoft.playwright.Page;

import appprocessor.AppTest;
import appprocessor.PlaywrightCommands;

public class BasePage extends PlaywrightCommands
{
	private Page page;
	
	public BasePage(Page page)
	{
		super(page);
		this.page = page;
	}
	
	protected void NavigateApplicationUrl()
	{
		this.page.navigate(AppTest.playwrightConfig.getApplicationUrl());
	}
}
