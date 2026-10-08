package actions;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import appprocessor.AppTest;

public class DialogTest extends AppTest 
{
	@Test(testName="This is to verify various Playwright actions")
	public void VerifyLocatorMatchingTechnique() throws InterruptedException
	{
		Page page = createNewPage();
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		Locator simpleAlertButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Simple Alert").setExact(true));
		simpleAlertButton.scrollIntoViewIfNeeded();
		simpleAlertButton.dblclick();
		
		Thread.sleep(2000);
		page.onceDialog(dialog -> {
			System.out.println("Simple Alert message: " + dialog.message());
			dialog.accept();
		});
		
		page.offDialog(null);
		
		Thread.sleep(3000);
		
		Locator confirmationAlertButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirmation Alert").setExact(true));
		confirmationAlertButton.click();
		Thread.sleep(2000);
		page.onceDialog(dialog -> {
			System.out.println("Confirmation Alert message: " + dialog.message());
			dialog.dismiss();
		});
		
		Thread.sleep(3000);
		
		Locator promptAlertButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Prompt Alert").setExact(true));
		promptAlertButton.dblclick();
		Thread.sleep(2000);
		page.onceDialog(dialog -> {
			System.out.println("Defult Value of Prompt Alert: " + dialog.defaultValue());
			System.out.println("Type of alert: " + dialog.type());
			dialog.accept("I am voldemort");
			System.out.println("New value entered in the Prompt Alert: " + dialog.defaultValue());
			dialog.dismiss();
		});
		
		Thread.sleep(3000);
	}
}
