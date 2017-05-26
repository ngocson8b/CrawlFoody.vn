package foodyCrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.*;

public class Information {
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private int row = 1;
	private int page = 1;
			
	//CSV file header
	private static final String FILE_HEADER = "Mã thương hiệu,Mã cửa hàng,Tên cửa hàng,Trạng thái,Mã bưu chính,Tỉnh/Thành phố,Địa chỉ,Tòa nhà,Vĩ độ,Kinh độ,Phạm vi check in,Truy cập,Số điện thoại,FAX,Tên quản lý cửa hàng,Tên quản lý cửa hàng,Ngày nghỉ,Thông tin đỗ xe,Thông tin chỗ ngồi,Phòng của trẻ nhỏ,Địa chỉ email,Điều khoản sử dụng,Chính sách bảo mật,Freeword,SEO từ khóa 1,SEO từ khóa 2,SEO từ khóa 3";

	public void getInfo(String idAdress) throws IOException, InterruptedException{
		String fileInputPath = "C:/Users/MaiPL/Documents/link.txt";
        FileInputStream fileReader = new FileInputStream(new File(fileInputPath));        
        File fileOutputPath = new File("C:/Users/MaiPL/Documents/data1.csv");		
		PrintWriter pw = new PrintWriter(fileOutputPath,"UTF-8");

		//Write the CSV file header
		pw.write(FILE_HEADER.toString());
		//Add a new line separator after the header
		pw.write(NEW_LINE_SEPARATOR.toString());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fileReader));
		String link;
		while ((link = br.readLine()) != null) {			
            link = link.trim();
            if (link != null && !link.isEmpty()) {
            		WebDriver driver=new FirefoxDriver();
            		driver.get(link);
            		login(driver);
            		//get data from website
            		Store store = execData(driver,idAdress, row) ;
            		row++;
            		//write to file csv
            		if(row%3==0) {
            			page++;
            			fileOutputPath = new File("C:/Users/MaiPL/Documents/data"+ page + ".csv");
            			pw = new PrintWriter(fileOutputPath,"UTF-8");
            			//Write the CSV file header
            			pw.write(FILE_HEADER.toString());
            			//Add a new line separator after the header
            			pw.write(NEW_LINE_SEPARATOR.toString());
            		}
            		pw.write(writeToBuilder(store).toString());            		
            		//close FirefoxDriver
            		driver.close();
            }
        }
//		fileWriter.flush();
		pw.close();
		
	}
	
	public void login(WebDriver driver) throws InterruptedException {
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@id='catfish-banner']//div//span")).click();
		driver.findElement(By.linkText("Đăng nhập")).click();
		WebElement email = driver.findElement(By.id("Email"));
		email.sendKeys("maipl_tt@runsystem.net");            		  
		  WebElement password = driver.findElement(By.id("Password"));
		  password.sendKeys("123456");
		  Thread.sleep(1000);
		  WebElement password2 = driver.findElement(By.id("Password"));
		  password2.sendKeys("123456");		  
		  driver.findElement(By.id("signin_submit")).click();
		 Thread.sleep(2000);	
	}
	
	public Store execData(WebDriver driver, String idAdress, int row) throws InterruptedException{
		Store store = new Store();
		//main-info-title
 		String title = driver.findElement(By.xpath("//div[@class='main-info-title']//h1")).getText();
 		if(title == null && title.isEmpty()) title = "";
 		store.setStore_name(title);
 		
 		//get streetAddress
 		String streetAddress = driver.findElement(By.xpath("//span[@itemprop='streetAddress']")).getText();
 		if(streetAddress == null && streetAddress.isEmpty()) streetAddress = "";
 		store.setStore_building(streetAddress);
 		
 		//get addressLocality
 		String addressLocality = driver.findElement(By.xpath("//span[@itemprop='addressLocality']")).getText();
 		if(addressLocality == null && addressLocality.isEmpty()) addressLocality = "";
 		store.setStore_address(addressLocality);
 		
 		//get addressRegion
 		String addressRegion = driver.findElement(By.xpath("//span[@itemprop='addressRegion']")).getText(); 
 		if(addressRegion == null && addressRegion.isEmpty()) addressRegion = "";
 		store.setStore_prefectures(idAdress);
 		
 		//get latitude
 		String latitude = driver.findElement(By.xpath("//meta[@itemprop='latitude']")).getText();
 		if(latitude == null && latitude.isEmpty()) latitude = "";
 		store.setLatitude(latitude);
 		
 		//get longitude
 		String longitude = driver.findElement(By.xpath("//meta[@itemprop='longitude']")).getText();
 		if(longitude == null && longitude.isEmpty()) longitude = "";
 		store.setLongitude(longitude);
 		
 		//get micro-timesopen
 		String timesopen;
// 		System.out.println(driver.findElement(By.xpath("//div[@class='micro-timesopen']//span[3]")).getSize());
 		
 		try{
 			timesopen = driver.findElement(By.xpath("//div[@class='micro-timesopen']//span[3]")).getText();
 			
 		}
// 			timesopen = driver.findElement(By.xpath("//div[@class='micro-timesopen']//span[3]")).getText();
 		catch (Exception e)
 		{
 		timesopen = driver.findElement(By.xpath("//div[@class='micro-timesopen']//span[2]")).getText();
 		}
 		if(timesopen == null && timesopen.isEmpty()) timesopen = "";
 		System.out.println("TIME"+timesopen);
 		store.setStore_business_hours(timesopen);
 				
 		//get image
 		String image = driver.findElement(By.xpath("//div[@class='main-image']//div//a//img")).getAttribute("src");
 		if(image == null && image.isEmpty()) image = "";
 		store.setImage(image);           		

		
		//get phone                    		
		WebElement btnClickPhone = driver.findElement(By.id("show-phone-number"));
		btnClickPhone.click();
		Thread.sleep(2000);
		String phone = driver.findElement(By.xpath("//div[@class='microsite-popup-phone-number']//table")).getText();  
		phone=phone.replaceAll("\\r\\n|\\r|\\n", " - ");
//		phone=phone.replace(System.getProperty("line.separator"), "");	
		System.out.println("PHONE: " + phone);
		if(phone == null && phone.isEmpty()) phone = "";
		store.setStore_phone_no(phone);
		//set store id
		store.setStore_code(convertCode(row));
		System.out.println("DONE: " + row);
		
		return store;
	}
	public StringBuilder writeToBuilder(Store store){
		StringBuilder sb = new StringBuilder();
		//Mã thương hiệu
		sb.append("@VNLAB");
		sb.append(COMMA_DELIMITER);
		//Mã cửa hàng
		if(store.getStore_code() == null && store.getStore_code().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_code());
		sb.append(COMMA_DELIMITER);
		//Tên cửa hàng
		if(store.getStore_name() == null && store.getStore_name().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_name());	
		sb.append(COMMA_DELIMITER);
		//Trạng thái		
		sb.append("");		
		sb.append(COMMA_DELIMITER);
		//Mã bưu chính
		sb.append("");		
		sb.append(COMMA_DELIMITER);
		//Tỉnh/Thành phố
		if(store.getStore_prefectures() == null && store.getStore_prefectures().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_prefectures());
		sb.append(COMMA_DELIMITER);
		//Địa chỉ
		if(store.getStore_address() == null && store.getStore_address().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_address());
		sb.append(COMMA_DELIMITER);
		//Tòa nhà
		if(store.getStore_building() == null && store.getStore_building().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_building());
		sb.append(COMMA_DELIMITER);
		//Vĩ độ
		if(store.getLatitude() == null && store.getLatitude().isEmpty())
			sb.append("");
		else
			sb.append(store.getLatitude());
		sb.append(COMMA_DELIMITER);
		//Kinh độ
		if(store.getLongitude() == null && store.getLongitude().isEmpty())
			sb.append("");
		else
			sb.append(store.getLongitude());
		sb.append(COMMA_DELIMITER);
		//Phạm vi check in
		sb.append("500");		
		sb.append(COMMA_DELIMITER);
		//Truy cập
		sb.append("");		
		sb.append(COMMA_DELIMITER);
		//Số điện thoại
		if(store.getStore_phone_no() == null && store.getStore_phone_no().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_phone_no());
		sb.append(COMMA_DELIMITER);
		//FAX		
		sb.append("");		
		sb.append(COMMA_DELIMITER);
		//Tên quản lý cửa hàng
		sb.append("");	
		sb.append(COMMA_DELIMITER);
		//Thời gian
		if(store.getStore_business_hours() == null && store.getStore_business_hours().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_business_hours());	
		sb.append(COMMA_DELIMITER);		
		//Ngày nghỉ
		sb.append("");
		sb.append(COMMA_DELIMITER);
		//Thông tin đỗ xe
		sb.append("");	
		sb.append(COMMA_DELIMITER);
		//Thông tin chỗ ngồi
		sb.append("");	
		sb.append(COMMA_DELIMITER);
		//Phòng của trẻ nhỏ
		sb.append("");
		sb.append(COMMA_DELIMITER);
		//Địa chỉ email
		sb.append("");	
		sb.append(COMMA_DELIMITER);
		//Điều khoản sử dụng
		sb.append("");
		sb.append(COMMA_DELIMITER);
		//Chính sách bảo mật
		sb.append("");
		sb.append(COMMA_DELIMITER);
		//Freeword
		sb.append("");
		sb.append(COMMA_DELIMITER);
		//SEO từ khóa 1
		sb.append("");	
		sb.append(COMMA_DELIMITER);
		//SEO từ khóa 2
		sb.append("");
		sb.append(COMMA_DELIMITER);
		//SEO từ khóa 3
		sb.append("");	
		sb.append(NEW_LINE_SEPARATOR);
		return sb;
	}
	
	public String convertCode(int num){
		if(num<10) return "@00000" + num;
		else if(num>=10 && num<100) return "@0000" + num;
		else if(num>=100 && num<1000) return "@000" + num;
		else if(num>=1000 && num<10000) return "@00" + num;
		else if(num>=10000 && num<100000) return "@0" + num;
		else return "@" + num;
	}	
}

