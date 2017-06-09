package main;

import java.io.IOException;

import common.Constants;
import foodyCrawler.Information;
public class InfoGetterMainHN {

	public static void main(String[] args) throws InterruptedException, IOException {
		System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER);
		System.setProperty("file.encoding","UTF-8");
			Information T = new Information();
				
			for (String cat: Constants.CAT_LIST_HN){
				String[] arrCat = cat.split("@");
				T.getInfo(Constants.HN_LINK_PATH, Constants.HN_INFO_PATH, Constants.HN_CODE, arrCat[0]);	
				T.row = Constants.ROW;
				T.page = Constants.PAGE;
			}
			
	}
}