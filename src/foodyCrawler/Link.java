package foodyCrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.Constants;
import common.Utils;

public class Link {
	final static Logger logger = Logger.getLogger(Link.class);
	private String catName;
	
	public Link(String name) {
		catName = name;
	}
	
	public void getLink(String firstUrl, String lastUrl, int pageCount, String fileOutPutUrl) throws IOException{
		logger.info("[" + catName + "]=================START=========================");
		try{
			int iCount=0;
			 String linkInput;
			 File newTextFile = new File(fileOutPutUrl);
			 FileWriter fw = new FileWriter(newTextFile,true); 
			 
			// System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER);
			 for(iCount=1;iCount <= pageCount;iCount++) {
				 
				 linkInput = firstUrl + iCount + lastUrl;
				 Document doc = Jsoup.connect(linkInput).get();
				 // Elements  links = doc.select("div[class='ri-avatar result-image'] a");
				 Elements links = doc.getElementsByClass(Constants.HTML_SPLIT_CLASS);
				 
				 logger.info("[" + catName + "] Page number: " + iCount);
				 logger.info("[" + catName + "] Number of Items:" + links.size());
				 for (Element link : links) {
					 link = link.select("a[href]").first();
					 if(link.attr("href").indexOf("?") == -1) {
						fw.append(Constants.FOODY_URL + link.attr("href")+"\r\n");
						logger.info("[" + catName + "]Url: " + link.attr("href"));
					 }
			      }
				 
				 Thread.sleep(20000);
				 
			 }
			 
			 fw.flush();
		     fw.close();
		}catch(Exception ex){
			logger.error(ex);
		}
		
		Utils.moveFile(fileOutPutUrl, Constants.PROCESS_FOLDER);
		
		logger.info("[" + catName + "]=================END=========================");	
	}
}
