package main;

import common.Constants;
import foodyCrawler.CrawlerLinksGetter;

public class LinksGetterMainHCM {

	public static void main(String[] args) throws InterruptedException {
		
		// Crawler cho nhà hàng
		 CrawlerLinksGetter T = null;
		 
		 for (String cat: Constants.CAT_LIST_HCM) {    
			 
			 String[] arrCat = cat.split("@");
			 
			 T = new CrawlerLinksGetter(Constants.HCM_RES_NAME
                     ,Constants.HCM_URL_FIRST
                     ,Constants.HCM_URL_LAST
                     ,Integer.parseInt(arrCat[1])
                     ,arrCat[0]
                     ,Constants.HCM_CODE
                     , Constants.HCM_LINK_PATH
                     ,arrCat[2]);
			 
			 	T.start();
			 	T.join();
		    }	      
	}

}
