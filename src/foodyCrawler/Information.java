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

import common.Constants;
import common.Utils;
import foodyCrawler.CrawlerLinksGetter;
import java.io.File;

import org.apache.log4j.Logger;


public class Information extends Thread {
	//Delimiter used in CSV file
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	public int row = 113;
	public int page = 4;
			
	//CSV file header
	private static final String FILE_HEADER = "Mã thương hiệu,Mã cửa hàng,Tên cửa hàng,Trạng thái,Mã bưu chính,Tỉnh/Thành phố,Địa chỉ,Tòa nhà,Vĩ độ,Kinh độ,Phạm vi check in,Truy cập,Số điện thoại,FAX,Tên quản lý cửa hàng,Thời gian hoạt động,Ngày nghỉ,Thông tin đỗ xe,Thông tin chỗ ngồi,Phòng của trẻ nhỏ,Địa chỉ email,Điều khoản sử dụng,Chính sách bảo mật,Freeword,SEO từ khóa 1,SEO từ khóa 2,SEO từ khóa 3";
	private static final String FILE_IMAGE_HEADER = "Mã cửa hàng,Link ảnh";
	//final static Logger logger = Logger.getLogger(CrawlerLinksGetter.class);
	public void getInfo(String pLinkPath, String pInfoPath, String pCode, String pItemName) throws IOException, InterruptedException{
		String fileInputPath = pLinkPath + pCode + "_" + pItemName.toLowerCase() + ".txt" ;
		System.out.println(fileInputPath);
        FileInputStream fileReader = new FileInputStream(new File(fileInputPath)); 
        File infoPath = new File(pInfoPath);
	      if(!infoPath.exists()){
	    	  infoPath.mkdirs();
	      }
        File fileOutputPath = new File(pInfoPath + pCode + "_" + pItemName.toLowerCase() + "_" + page + ".csv");
        File fileImageOutputPath = new File(pInfoPath + pCode + "_" + pItemName.toLowerCase() + "_" + page + "_image.csv");
        System.out.println(fileOutputPath);
		// PrintWriter pw = new PrintWriter(fileOutputPath,"UTF-8");
		FileWriter pw = new FileWriter(fileOutputPath,true); 
		FileWriter pwImage = new FileWriter(fileImageOutputPath,true);

//		Write the CSV file header
		pw.write(FILE_HEADER.toString());
		pwImage.write(FILE_IMAGE_HEADER.toString());

//		Add a new line separator after the header
		pw.write(NEW_LINE_SEPARATOR.toString());
		pwImage.write(NEW_LINE_SEPARATOR.toString());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fileReader));
		String link;
		while ((link = br.readLine()) != null) {			
            link = link.trim();
            if (link != null && !link.isEmpty()) {
            		WebDriver driver=new FirefoxDriver();
            		driver.get(link);
            		login(driver);
            		//get data from website
            		Store store = execData(driver, row) ;
            		row++;
            		//write to file csv  
            		if(row%50==0){
            			page++;
            			fileOutputPath = new File(pInfoPath + pCode + "_" + pItemName.toLowerCase() + "_" + page + ".csv");
            			fileImageOutputPath = new File(pInfoPath + pCode + "_" + pItemName.toLowerCase() + "_" + page + "_image.csv");
            			
            			pw = new FileWriter(fileOutputPath,true); 
            			pwImage = new FileWriter(fileImageOutputPath,true); 

//            			Write the CSV file header
            			pw.write(FILE_HEADER.toString());
            			pwImage.write(FILE_IMAGE_HEADER.toString());
//            			Add a new line separator after the header
            			pw.write(NEW_LINE_SEPARATOR.toString());
            			pwImage.write(NEW_LINE_SEPARATOR.toString());
            			
            		}
            		pw.write(writeToBuilder(store).toString());  
            		pwImage.write(writeImage(store).toString()); 
            		pw.flush();
            		pwImage.flush();
            		driver.close();
            		Thread.sleep(5000);
            }
        }
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
		  Thread.sleep(2000);
		  driver.findElement(By.id("signin_submit")).click();
		 Thread.sleep(2000);	
	}
	
	
	public Store execData(WebDriver driver, int row) throws InterruptedException{
		Store store = new Store();
		//main-info-title
 		String title = driver.findElement(By.xpath("//div[@class='main-info-title']//h1")).getText();
 		if(title == null && title.isEmpty()) title = "";
 		else title = title.replaceAll(",", "-");
 		store.setStore_name(title);
 		
 		//get streetAddress
 		String streetAddress = driver.findElement(By.xpath("//span[@itemprop='streetAddress']")).getText();
 		if(streetAddress == null && streetAddress.isEmpty()) streetAddress = "";
 		else streetAddress = streetAddress.replaceAll(",", "-");
 		store.setStore_building(streetAddress);
 		
 		//get addressLocality
 		String addressLocality = driver.findElement(By.xpath("//span[@itemprop='addressLocality']")).getText();
 		if(addressLocality == null && addressLocality.isEmpty()) addressLocality = "";
 		store.setStore_address(addressLocality);
 		
 		//get addressRegion
 		String addressRegion = driver.findElement(By.xpath("//span[@itemprop='addressRegion']")).getText(); 
 		if(addressRegion == null && addressRegion.isEmpty()) addressRegion = "";
 		store.setStore_prefectures(Constants.HN_PREFECTURES);
 		
 		//get latitude
 		String latitude = driver.findElement(By.xpath("//meta[@itemprop='latitude']")).getAttribute("content");
 		if(latitude == null && latitude.isEmpty()) latitude = "";
 		store.setLatitude(latitude);
 		
 		//get longitude
 		String longitude = driver.findElement(By.xpath("//meta[@itemprop='longitude']")).getAttribute("content");
 		if(longitude == null && longitude.isEmpty()) longitude = "";
 		store.setLongitude(longitude);
 		
 		//get micro-timesopen
 		String timesopen;
		try {
			timesopen = driver.findElement(By.xpath("//div[@class='micro-timesopen']//span[3]")).getText();

		} catch (Exception e) {
			timesopen = driver.findElement(By.xpath("//div[@class='micro-timesopen']//span[2]")).getText();
		}
		if (timesopen == null && timesopen.isEmpty())
			timesopen = "";
		// System.out.println("TIME"+timesopen);
		store.setStore_business_hours(timesopen);
 				
 		//get store_regular_holiday
 		String regular_holiday;
		try {
			regular_holiday = driver.findElement(By.xpath("//div[@class='new-detail-info-area'][5]//div[2]")).getText();
			if (regular_holiday == null && regular_holiday.isEmpty())
				regular_holiday = "";
			store.setStore_regular_holiday(regular_holiday);
		} catch (Exception e) {
			regular_holiday = "";
			store.setStore_regular_holiday(regular_holiday);
		}
 
 		//get seat
		String seat;
		try {
			seat = driver.findElement(By.xpath("//div[@class='new-detail-info-area'][7]//div[2]")).getText();
			if (seat == null && seat.isEmpty()) seat = "";
			else {
				 seat = seat.replaceAll(",", "-");	
			}
			store.setStore_seat(seat);
		} catch (Exception e) {
			seat = "";
			store.setStore_seat(seat);
		}

		// get parking_lot
		String car_parking_lot;
		String bike_parking_lot;
		String parking_lot;
		try {
			driver.findElement(By.xpath("//div[@class='microsite-box-content']//ul//li[@class='none']//img[@src='//static.foody.vn/s1/style/images/catico/facilities/2/chodauxehoi.png']"));
			car_parking_lot = "";
		} catch (Exception e) {
			car_parking_lot = "Có chỗ để ô tô";
		}
		try {

			driver.findElement(By.xpath("//div[@class='microsite-box-content']//ul//li[@class='none']//img[@src='//static.foody.vn/s1/style/images/catico/facilities/2/giuxemienphi.png']"));
			bike_parking_lot = "";
		} catch (Exception e) {
			bike_parking_lot = "Giữ xe máy miễn phí";
		}
		parking_lot = car_parking_lot + " - " + bike_parking_lot;
		store.setStore_parking_lot(parking_lot);

		// get kids room
		String kids_room;
		try {
			driver.findElement(By.xpath("//div[@class='microsite-box-content']//ul//li[@class='none']//img[@src='//static.foody.vn/s1/style/images/catico/facilities/2/vuichoitreem.png']"));
			kids_room = "";
		} catch (Exception e) {
			kids_room = "Có chỗ chơi cho trẻ em";
		}
		store.setStore_kids_room(kids_room); 
		
		//get image
		String image;
		try{
			image = driver.findElement(By.xpath("//div[@class='main-image']//div//a//img")).getAttribute("src");
	 		if(image == null && image.isEmpty()) image = "";
	 		store.setImage(image);     
		} catch (Exception e){
			image = "";
	 		store.setImage(image);     
		}
 		
		//get phone                    		
		WebElement btnClickPhone = driver.findElement(By.id("show-phone-number"));
		btnClickPhone.click();
		Thread.sleep(2000);
		String phone = "";
		try{
			phone = driver.findElement(By.xpath("//div[@class='microsite-popup-phone-number']//table//tbody//tr//td[2]")).getText();
			try{
//				phone=phone.replaceAll("\\r\\n|\\r|\\n", " - ");	
				phone = phone.replaceAll("[()]","");
				phone = phone.replaceAll(" ","");
//				if(phone == null && phone.isEmpty()) 
//					phone = "";
				
			} catch(Exception e2){
				phone = "";
			}
		} catch(Exception e){}
		store.setStore_phone_no(phone);
		
		//set store id
		store.setStore_code(convertCode(row));
		System.out.print("Done:" + row);
		return store;
	}
	
	public StringBuilder writeImage(Store store){
		StringBuilder sb = new StringBuilder();
		// Mã cửa hàng
		if(store.getStore_code() == null && store.getStore_code().isEmpty())
			sb.append("");
		else
			sb.append(store.getStore_code());
		sb.append(COMMA_DELIMITER);
		// Image
		if(store.getImage() == null && store.getImage().isEmpty())
			sb.append("");
		else
			sb.append(store.getImage());
		sb.append(NEW_LINE_SEPARATOR);
		return sb;
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
		if(store.getStore_regular_holiday() == null && store.getStore_regular_holiday().isEmpty())
			sb.append("");
		else 
			sb.append(store.getStore_regular_holiday());
		sb.append(COMMA_DELIMITER);
		//Thông tin đỗ xe
		if(store.getStore_parking_lot() == null && store.getStore_parking_lot().isEmpty())
			sb.append("");
		else 
			sb.append(store.getStore_parking_lot());
		sb.append(COMMA_DELIMITER);
		//Thông tin chỗ ngồi
		if(store.getStore_seat() == null && store.getStore_seat().isEmpty())
			sb.append("");
		else 
			sb.append(store.getStore_seat());
		sb.append(COMMA_DELIMITER);
		//Phòng của trẻ nhỏ
		if(store.getStore_kids_room() == null && store.getStore_kids_room().isEmpty())
			sb.append("");
		else 
			sb.append(store.getStore_kids_room());
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

