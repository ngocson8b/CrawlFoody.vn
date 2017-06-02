//package main;
//
//import common.Constants;
//import foodyCrawler.CrawlerLinksGetter;
//
//public class LinksGetterMainHN {
//
//	public static void main(String[] args) throws InterruptedException {
//		
//		// Crawler cho nhà hàng
//		 CrawlerLinksGetter T = null;
//		 
//		 for (String cat: Constants.CAT_LIST_HN) {    
//			 
//			 String[] arrCat = cat.split("@");
//			 
//			 T = new CrawlerLinksGetter(Constants.HN_RES_NAME
//                     ,Constants.HN_URL_FIRST
//                     ,Constants.HN_URL_LAST
//                     ,Integer.parseInt(arrCat[1])
//                     ,arrCat[0]
//                     ,Constants.HN_CODE
//                     ,Constants.HN_LINK_PATH
//					 ,arrCat[2]);
//			 
//			 	T.start();
//			 	T.join();
//		    }	      
//	}
//
//} 
