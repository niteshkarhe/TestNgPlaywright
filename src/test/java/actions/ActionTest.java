package actions;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;

import appprocessor.AppTest;
import appprocessor.PlaywrightCommands;

public class ActionTest extends AppTest
{
	@Test(testName="This is to verify various Playwright actions")
	public void VerifyLocatorMatchingTechnique() throws InterruptedException
	{
		Page page = createNewPage();
		PlaywrightCommands cmd = new PlaywrightCommands(page);
		page.navigate("https://testautomationpractice.blogspot.com/p/playwrightpractice.html");
		
		// fill() action
		page.getByText("Username").fill("NiteshKarhe");
		Thread.sleep(3000);
		
		// Checkbox and Radio button
		page.getByLabel("Accept terms").check();
		if (page.getByLabel("Accept terms").isChecked())
		{
			System.out.println("Checkbox is Checked");
		}
		
		Thread.sleep(3000);
		
		// Mouse Click()
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit Form")).click();
		Thread.sleep(2000);
		// Mouse double click()
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit Form")).dblclick();
		Thread.sleep(2000);
		// Mouse Right click()
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit Form")).click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
		Thread.sleep(2000);
		
		page.keyboard().press("Escape");
		page.keyboard().press("Escape");
		
		// Type Characters
		page.getByLabel("Username:").pressSequentially("Nitesh Karhe");
		Thread.sleep(2000);
		// To perform Keyboard actions on any element
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Primary Action")).press("Enter");
		Thread.sleep(2000);
		// Select all text in a textbox
		page.getByLabel("Username:").fill("Nitesh47");
		page.getByLabel("Username:").press("Control+A");
		Thread.sleep(3000);
		
		// Upload Files
		// page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Upload Single File")).setInputFiles(Paths.get("E:\\NK\\Playwright\\Study Material\\Playwright Locators.docx"));
		
		
		//Drag and Drop
		/*
		 * Hover the element that will be dragged.
		 * Press left mouse button.
		 * Move mouse to the element that will receive the drop.
		 * Release left mouse button.
		 **/
		Locator draggableElement = page.locator("#draggable");
		Locator dropElement = page.locator("#droppable");
		draggableElement.dragTo(dropElement);
		Thread.sleep(2000);
		
		// Scrolling
		/*
		 * By default Playwright always scroll to the element before performing any action.
		 * If in special case we need to scroll then we can use locator.scrollIntoViewIfNeeded()
		 * 
		 * */
		 
		Locator staticWebTableTextElement = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Static Web Table"));
		staticWebTableTextElement.scrollIntoViewIfNeeded();
		
		// Use evaluate() method of locator to scroll
		Locator shippingMethodTitleElement = page.locator("legend:text-is('Shipping Method')");
		shippingMethodTitleElement.evaluate("e => e.scrollTop += 100");
		Thread.sleep(2000);
	}
}
