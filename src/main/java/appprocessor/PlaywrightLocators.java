package appprocessor;

import com.microsoft.playwright.Locator;

import lombok.Getter;
import lombok.Setter;

public class PlaywrightLocators
{
	@Getter
	@Setter
	public String name;
	
	@Getter
	@Setter
	public Locator selector;
	
	public PlaywrightLocators(String name, Locator locator)
	{
		this.name = name;
		this.selector = locator.describe(name);
	}
	
	protected String spacify(String text)
	{
		StringBuilder newText = new StringBuilder(text.length() * 2);
		newText.append(text.charAt(0));
		for (int i = 1; i < text.length(); i++)
		{
			if (Character.isUpperCase(text.charAt(i)) && text.charAt(i - 1) != ' ')
			{
				newText.append(' ');
			}
			
			newText.append(text.charAt(i));
		}
		
		return newText.toString();
	}
}
