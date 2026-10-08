package actions;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.AppTest;

public class UploadFilesTest extends AppTest
{
	@Test(testName="This is to verify upload file functionality")
	public void VerifyUploadFileFunctionality() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		Locator uploadBtn = page.locator("#singleFileInput");
		uploadBtn.scrollIntoViewIfNeeded();
		FileChooser fileChooser = page.waitForFileChooser(() -> uploadBtn.click());
		fileChooser.setFiles(Paths.get("E:\\NK\\Playwright\\Study Material\\Playwright Architecture New.docx"));
		Thread.sleep(3000);
	}
}
