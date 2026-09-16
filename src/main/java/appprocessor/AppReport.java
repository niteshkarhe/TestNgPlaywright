package appprocessor;

import java.util.Base64;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import lombok.Getter;
import lombok.Setter;

public class AppReport 
{
	private String testReportKey;
	
	@Getter
	@Setter
	public Page page;
	
	public AppReport(Page page)
	{
		this.page = page;
		this.testReportKey = BaseTest.testReportKey.get();
	}
	
	public void Log(String message, Status logStatus)
	{
		ExtentTest currentTest = this.getTestReport();
		if (currentTest == null)
		{
			return;
		}
		
		if (BaseTest.testReport.get() != null)
		{
			synchronized (BaseTest.reportLock)
			{
				currentTest.log(logStatus, message);
			}
		}
	}
	
	public void LogResult(boolean hasFailures, String message, PlaywrightLocators... locator)
	{
		boolean shouldCaptureScreenshot = false;
		ExtentTest currentTest = this.getTestReport();
		if (currentTest == null)
		{
			return;
		}
		
		String result = hasFailures ? "FAIL" : "PASS";
		Status logStatus = hasFailures ? Status.FAIL : Status.PASS; 
		
		synchronized(BaseTest.reportLock)
		{
			currentTest.log(logStatus, message);
		}
		
		if (hasFailures && AppTest.playwrightConfig.isScreenCapture())
		{
			shouldCaptureScreenshot = true;
		}
		
		if (shouldCaptureScreenshot)
		{
			this.saveScreenshot(locator);
		}
	}
	
	private ExtentTest getTestReport()
	{
		ExtentTest test = BaseTest.testReport.get();
		if (test != null)
		{
			return test;
		}
		
		if (this.testReportKey == null || this.testReportKey.isEmpty())
		{
			return null;
		}
		
		if (BaseTest.testReportCache.containsKey(this.testReportKey))
		{
			ExtentTest cachedTest = BaseTest.testReportCache.get(this.testReportKey);
			BaseTest.testReport.set(cachedTest);
			return cachedTest;
		}
		
		return null;
	}
	
	public void saveScreenshot(PlaywrightLocators... locator)
	{
		ExtentTest currentTest = this.getTestReport();
		if (currentTest == null || this.page == null)
		{
			return;
		}
		
		int width = 250;
		int height = 150;
		byte[] screenshotBytes;
		try
		{
			if (locator.length > 0)
			{
				Locator playwrightLocator = locator[0].selector;
				if (playwrightLocator.isVisible())
				{
					screenshotBytes = playwrightLocator.screenshot();
					width = 350;
					height = 50;
				}
				else
				{
					screenshotBytes = page.screenshot();
				}
			}
			else
			{
				screenshotBytes = page.screenshot();
			}
			
			String screenshot = Base64.getEncoder().encodeToString(screenshotBytes);
			synchronized (BaseTest.reportLock)
			{
				currentTest.log(Status.INFO, "<a href=data:image/png;base64," + screenshot + " data-featherlight=\"image\"> <img alt=\"Embedded Image\" src=\"data:image/png;base64," + screenshot + "\" width=\"" + width + "\" height=\"" + height + "\"/></a>");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
