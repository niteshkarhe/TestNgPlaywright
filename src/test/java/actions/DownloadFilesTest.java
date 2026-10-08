package actions;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.AppTest;

public class DownloadFilesTest extends AppTest
{
	@Test(testName="This is to verify various Playwright download actions")
	public void VerifyLocatorMatchingTechnique() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://demo.automationtesting.in/FileDownload.html");
		Locator downloadBtnLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Download"));
		Download download = page.waitForDownload(() -> {
			downloadBtnLink.click();
		});
		
		download.saveAs(Paths.get("E:\\NK\\Playwright\\Study Material", download.suggestedFilename()));
	}
}
