package appprocessor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import extentreports.ExtentManager;

import configsetup.ReportConfig;
import lombok.Getter;
import lombok.Setter;

public class BaseTest 
{
	private static int reportFlushed = 0;
	
	// To lock object for synchronizing ExtentReports operation across threads
	protected static final Object reportLock = new Object();
	
	public static final ConcurrentHashMap<String, ExtentTest> testReportCache = new ConcurrentHashMap<String, ExtentTest>();
	
	@Getter
	@Setter
	public static ThreadLocal<String> testReportKey = new ThreadLocal<String>();
	
	public static ReportConfig reportConfig = new ReportConfig();
	
	@Getter
	@Setter
	public static ExtentReports suiteReport;
	
	@Getter
	@Setter
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	
	@Getter
	@Setter
	public static ThreadLocal<Boolean> hasFailures = new ThreadLocal<Boolean>();
	
	@Getter
	@Setter
	public static List<String> TestNames = new ArrayList<String>();
	
	static
	{
		synchronized (reportLock)
		{
			setSuiteReport(ExtentManager.createExtentReports());
		}
	}
	
	/***
	 * 
	 * Gets the current test report with cache fallback for parallel execution.
	 * Used the key stored in ThreadLocal to ensure consistent lookup.
	 */
	public static ExtentTest getCurrentTestReport()
	{
		if (testReport.get() != null)
		{
			return testReport.get();
		}
		
		ExtentTest key = testReport.get();
		if (key == null || key.toString().isEmpty())
		{
			return null;
		}
		
		if (testReportCache.containsKey(key))
		{
			ExtentTest cachedReport = testReportCache.get(key);
			testReport.set(cachedReport);
			return cachedReport;
		}
		
		return null;
	}
	
	public static void setCurrentTestReport(ExtentTest test, Method method)
	{
		if (test == null)
		{
			return;
		}
		
		try
		{
			ITestResult result = Reporter.getCurrentTestResult();
			String className = result.getTestClass().getName();
			String testMethodName = result.getMethod().getMethodName();
			String fullyQualifiedName = className + "." + testMethodName;
			String key = "Test_" + fullyQualifiedName;
			testReportKey.set(key);
			testReportCache.put(key, test);
			testReport.set(test);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void TestMethodSetup(Method method)
	{
		System.out.println("#### Before Method to setup ExtentReport");
		hasFailures.set(false);
		try
		{
			Test testngTest = method.getAnnotation(org.testng.annotations.Test.class);
			String testMethodName = method.getName();
			String testCaseName = testngTest.testName();
			String finalTestCaseName = testCaseName != null ? testCaseName : testMethodName;
			synchronized(reportLock)
			{
				testReport.set(suiteReport.createTest(finalTestCaseName));
			}
			
			setCurrentTestReport(testReport.get(), method);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void TestMethodTearDown(ITestResult result)
	{
		try
		{
			System.out.println("#### After Method to end test with result");
			getTestNames().remove(result.getTestName());
			int testStatus = result.getStatus();
			String stackTrace = result.getThrowable() == null
					? "" 
					: "<pre>" + Arrays.toString(result.getThrowable().getStackTrace()) + "</pre>";
			
			Map<Integer, Status> logStatus = new HashMap<Integer, Status>();
			logStatus.put(result.FAILURE, Status.FAIL);
			logStatus.put(result.SUCCESS, Status.PASS);
			logStatus.put(result.SKIP, Status.SKIP);
			logStatus.put(result.SUCCESS_PERCENTAGE_FAILURE, Status.WARNING);
			
			ExtentTest testReport = getCurrentTestReport();
			if (testReport == null)
			{
				return;
			}
			
			synchronized(reportLock)
			{
				testReport.log(logStatus.get(testStatus), "Test Complete. Result: " + logStatus.get(testStatus) + stackTrace);	
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@AfterSuite(alwaysRun = true)
	public void EndSuite()
	{
		System.out.println("#### After Suite to flush ExtentReport");
		getSuiteReport().flush();
	}
}