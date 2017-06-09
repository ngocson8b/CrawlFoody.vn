package main;

import java.io.IOException;

import common.Constants;
import foodyCrawler.Information;
public class InfoGetterMainHCM {

	public static void main(String[] args) throws InterruptedException, IOException {
		System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER);
		System.setProperty("file.encoding","UTF-8");
			Information T = new Information();
				
			for (String cat: Constants.CAT_LIST_HCM){
				String[] arrCat = cat.split("@");
				T.getInfo(Constants.HCM_LINK_PATH, Constants.HCM_INFO_PATH, Constants.HCM_CODE, arrCat[0]);	
				T.row = Constants.ROW;
				T.page = Constants.PAGE;
			}
			
	}
}