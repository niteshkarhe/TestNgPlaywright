package appprocessor;

import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import lombok.Setter;

public class PlaywrightCommands
{
	private Page page;
	
	@Getter
	@Setter
	protected AppReport report;
	
	public PlaywrightCommands(Page page)
	{
		this.page = page;
		AppReport baseReport = new AppReport(this.page);
		this.setReport(baseReport);
	}
	
	public void Clear(PlaywrightLocators locator, float time)
	{
		try
		{
			locator.selector.clear(new Locator.ClearOptions().setTimeout(time * 1000));
			this.report.LogResult(false, "Successfully cleared text from element: [" + locator.name + "]");
		}
		catch (Exception e)
		{
			this.report.LogResult(true, "Clear failed: Element: " + locator.name + " not clickable within timeout");
			e.printStackTrace();
		}
	}
	
	public void Click(PlaywrightLocators locator, float time)
	{
		try
		{
			locator.selector.click(new Locator.ClickOptions().setTimeout(time * 1000));
			this.report.LogResult(false, "Successfully clicked element: [" + locator.name + "]");
		}
		catch (Exception e)
		{
			this.report.LogResult(true, "Click failed: Element: [" + locator.name + "] is not clickable within timeout");
			e.printStackTrace();
		}
	}
	
	public void EnterText(PlaywrightLocators locator, String textToBeEntered, float time)
	{
		try
		{
			locator.selector.fill(textToBeEntered, new Locator.FillOptions().setTimeout(time * 1000));
			this.report.LogResult(false, "Successfully entered text: [" + textToBeEntered + "] into element [" + locator.name + "]");
		}
		catch (Exception e)
		{
			this.report.LogResult(true, "EnterText failed: Element: " + locator.name + " is not clickable within timeout");
		}
	}
	
	public String getTextOfElement(PlaywrightLocators locator, float time)
	{
		String text = "";
		try
		{
			text = locator.selector.innerText(new Locator.InnerTextOptions().setTimeout(time * 1000));
			this.report.Log("Successfully got the text: [" + text + "] of element [" + locator.name + "]", Status.INFO);
		}
		catch (Exception e)
		{
			this.report.LogResult(true, "getText failed: Element: [" + locator.name + "] get text failed");
		}
		
		return text;
	}
}