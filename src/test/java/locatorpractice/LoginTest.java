package locatorpractice;

import org.testng.annotations.Test;
import com.microsoft.playwright.Page;
import appprocessor.AppTest;
import pages.LoginPage;

public class LoginTest extends AppTest
{
	@Test(testName="This is login test")
	public void LoginToThePortal()
	{
		Page page = createNewPage();
		LoginPage lgPage = new LoginPage(page);
		lgPage.LoginEventHubPortal();
	}
}