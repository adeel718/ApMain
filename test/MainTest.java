import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainTest {
  private WebDriver driver;
  
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  
  @Before
  public void setUp() throws Exception {
	  System.setProperty("webdriver.chrome.driver", "C:\\Software\\selenium\\chromedriver.exe");
    driver = new ChromeDriver();
  }
// Search using keyword through Google search

  @Test 
  public void main() throws Exception {
        //Open Home Page
        driver.get("http://developer.apple.com/membercenter/index.action");
        //Enter text in search box
        WebElement emailLogin = driver.findElement(By.id("accountname"));
        WebElement passwordLogin = driver.findElement(By.id("accountpassword"));
        WebElement submitBtnLogin = driver.findElement(By.className("signin-button"));
        
        emailLogin.sendKeys("adeel718@googlemail.com");
        passwordLogin.sendKeys("_AA51adeel");
        submitBtnLogin.click();
        
        driver.get("https://developer.apple.com/account/ios/device/deviceList.action");
        
        HashMap<String, String> UDIDlist = new HashMap<String, String>();
        List<WebElement> UDIDlistElements = driver.findElements(By.className("ui-ellipsis"));
        
        for(WebElement l : UDIDlistElements){
        	if(l.getText().length() == 40){
        		System.out.println(l.getText());
        		UDIDlist.put(l.getText(), l.getText());
        	}
        }
        System.out.println("0");
        try {
        	System.out.println("1");
            // this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("2");
            // setup the connection with the DB.
            connect = DriverManager
                .getConnection("jdbc:mysql://217.199.187.65/cl51-udid?"
                    + "user=cl51-udid&password=aa51adeel");
            
	         // statements allow to issue SQL queries to the database
	         statement = connect.createStatement();
	         // resultSet gets the result of the SQL query
	         resultSet = statement
	            .executeQuery("select * from registration");
	         
	         while (resultSet.next()) {
	             // it is possible to get the columns via name
	             // also possible to get the columns via the column number
	             // which starts at 1
	             // e.g., resultSet.getSTring(2);
	             String email = resultSet.getString("txn_email");
	             String ebayId = resultSet.getString("txn_ebayid");
	             String UDID1 = resultSet.getString("Udid_1");
	             String UDID2 = resultSet.getString("Udid_2");
	             String UDID3 = resultSet.getString("Udid_3");
	             String UDID4 = resultSet.getString("Udid_4");
	             String UDID5 = resultSet.getString("Udid_5");
	             
	             if(UDIDlist.containsValue(UDID1)){
	            	 System.out.println("contains:, from list:" + UDID1);
	             } else {
	            	 if(!UDID1.equals("")) {
	            		 insertUDID(UDID1, ebayId);
	            	 }
	             }
	             
	             if(UDIDlist.containsValue(UDID2)){
	            	 System.out.println("contains:, from list:" + UDID2);
	             } else {
	            	 if(!UDID2.equals("")) {
	            		 insertUDID(UDID2, ebayId);
	            	 }
	             }
	             
	             if(UDIDlist.containsValue(UDID3)){
	            	 System.out.println("contains:, from list:" + UDID3);
	             } else {
	            	 if(!UDID3.equals("")) {
	            		 insertUDID(UDID3, ebayId);
	            	 }
	             }
	             
	             if(UDIDlist.containsValue(UDID4)){
	            	 System.out.println("contains:, from list:" + UDID4);
	             } else {
	            	 if(!UDID4.equals("")) {
	            		 insertUDID(UDID4, ebayId);
	            	 }
	             }
	             
	             if(UDIDlist.containsValue(UDID5)){
	            	 System.out.println("contains:, from list:" + UDID5);
	             } else {
	            	 if(!UDID5.equals("")) {
	            		 insertUDID(UDID5, ebayId);
	            	 }
	             }
	             
	         }
	         //List<WebElement> s = driver.findElements(By.linkText("6d164fc75bc0332d58f5d63a3861a7596d1af287"));
	         //System.out.println(s);

         
        } catch (Exception e){
        	System.out.println(e);
        }
        
  }
  
  private void insertUDID(String UDID, String ebayId) {
	  System.out.println("INSERT UDID start: id "+UDID);
	  driver.get("https://developer.apple.com/account/ios/device/deviceCreate.action");
	  
	  // Random
	  int random = (int)(100.0 * Math.random());
	  
	  WebElement UDIDdeviceName = driver.findElement(By.name("name"));
	  WebElement UDIDdeviceNumber = driver.findElement(By.name("deviceNumber"));
	  WebElement submitBtn = driver.findElement(By.className("submit"));
	  
	  UDIDdeviceName.sendKeys(ebayId+""+random);
	  UDIDdeviceNumber.sendKeys(UDID);
	  System.out.println("INSERT dname: "+ebayId+""+random);
	  System.out.println("INSERT dno: "+UDID);
	  submitBtn.click();
	  
	  driver.findElement(By.className("submit")).click();
	  System.out.println(driver.getCurrentUrl());
	  
	  String urlCurr = driver.getCurrentUrl();
	  String urlExp = "https://developer.apple.com/account/ios/device/deviceList.action";
	  if(urlCurr.equals(urlExp)) {
		  // Send UDID success mail
		  System.out.println("UDID reg success");
		  
	  } else {
		  // Send UDID error admin mail
		  System.out.println("UDID reg error");
	  }
  }
  

  @After
  public void tearDown() throws Exception {
	  driver.quit();
    }
  }