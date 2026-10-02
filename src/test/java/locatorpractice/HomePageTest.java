package locatorpractice;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import appprocessor.AppTest;
import pages.HomePage;

public class HomePageTest extends AppTest
{
	@Test(testName="Verify portal menus are displayed correctly")
	public void VerifyPortalMenus()
	{
		Page page = createNewPage();
		HomePage hmPg = new HomePage(page);
		hmPg.LoginToPortalAndNavigateToHomepage();
		hmPg.VerifyThatPortalHasCorrectMenus();
	}
}