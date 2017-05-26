package foodyCrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Link {
	public void getLink(String firstUrl, String lastUrl) throws IOException{
		int iCount=0;
		 String linkInput;
		 File newTextFile = new File("C:/Users/MaiPL/Documents/link.txt");
		 FileWriter fw = new FileWriter(newTextFile,true); 
		 
		 System.setProperty("webdriver.gecko.driver", "C:\\Users\\MaiPL\\Downloads\\geckodriver-v0.16.1-win64\\geckodriver.exe");
		 System.out.println("AAA");
		 for(iCount=1;iCount<84;iCount++) {
			 System.out.println(iCount);
			 linkInput = firstUrl + iCount + lastUrl;
			 Document doc = Jsoup.connect(linkInput).get();
			 Elements  links = doc.select("div[class='ri-avatar result-image'] a");
			 for (Element link : links) {
				 if(link.attr("href").indexOf("?") == -1) {
					 fw.write("https://foody.vn" + link.attr("href")+"\r\n");
				 }
		      }
		 }
	}
}
