package configsetup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaywrightConfig
{
    private String applicationUrl;
    private String browserName;
    private String browserVersion;
    private String browserScope;
    private String environment;
    private long webDriverMaxWait;
    private int implicitWait;
    private int pageLoadWait;
    private boolean headlessBrowser;
    private String fileDownloadPath;
    private boolean incognitoMode;
    private boolean screenCapture;
    private String windowSize;
}
