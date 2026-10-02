package appprocessor;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import lombok.Getter;
import lombok.Setter;

public class PlaywrightWaits
{
	private Page page;
	
	@Getter
	@Setter
	private AppReport report;
	
	public PlaywrightWaits(Page initializedPage)
	{
		this.page = initializedPage;
		AppReport baseReport = new AppReport(this.page);
		this.setReport(baseReport);
	}
	
	public boolean waitForVisible(PlaywrightLocators locator, float time, boolean... needToLog)
	{
		boolean isElementVisible = false;
		try
		{
			locator.selector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(time * 1000));
			isElementVisible = true;
			this.report.LogResult(false, "Element " + locator.name + " is visible within " + time + "s");
		}
		catch (Exception e)
		{
			if (needToLog.length > 0)
			{
				if (needToLog[0])
				{
					this.report.LogResult(true, "Wait for visible failed: Element " + locator.name + " not visible within " + time + "s. ");
					e.printStackTrace();
				}
			}
		}
		
		return isElementVisible;
	}

	public boolean waitForHidden(PlaywrightLocators locator, float time, boolean... needToLog)
	{
		try
		{
			locator.selector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(time * 1000));
			this.report.LogResult(false, "Element " + locator.name + " is hidden within " + time + "s");
			return true;
		}
		catch (Exception e)
		{
			if (needToLog.length > 0)
			{
				if (needToLog[0])
				{
					this.report.LogResult(true, "Wait for hidden failed: Element " + locator.name + " is not hidden within " + time + "s. ");
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	public boolean waitForAttached(PlaywrightLocators locator, float time, boolean... needToLog)
	{
		try
		{
			locator.selector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED).setTimeout(time * 1000));
			this.report.LogResult(false, "Element " + locator.name + " is attached within " + time + "s");
			return true;
		}
		catch (Exception e)
		{
			if (needToLog.length > 0)
			{
				if (needToLog[0])
				{
					this.report.LogResult(true, "Wait for attached failed: Element " + locator.name + " is not attached within " + time + "s. ");
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	public boolean waitForDetached(PlaywrightLocators locator, float time, boolean... needToLog)
	{
		try
		{
			locator.selector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED).setTimeout(time * 1000));
			this.report.LogResult(false, "Element " + locator.name + " is detached within " + time + "s");
			return true;
		}
		catch (Exception e)
		{
			if (needToLog.length > 0)
			{
				if (needToLog[0])
				{
					this.report.LogResult(true, "Wait for detached failed: Element " + locator.name + " is not detached within " + time + "s. ");
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	public boolean waitUrlContains(String urlSubstring, float time, boolean... needToLog)
	{
		try
		{
			this.page.waitForURL(Pattern.compile(".*(" + urlSubstring + ").*"));
			this.report.LogResult(false, "URL now contains substring: [" + urlSubstring + "]");
			return true;
		}
		catch (Exception e)
		{
			if (needToLog.length > 0)
			{
				if (needToLog[0])
				{
					this.report.LogResult(true, "URL did not contains substring: [" + urlSubstring + "]");
					e.printStackTrace();
				}
			}
			
			return false;
		}
	}
	
	public void sleep(long timeInSec)
	{
		try
		{
			Thread.sleep(timeInSec * 1000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}