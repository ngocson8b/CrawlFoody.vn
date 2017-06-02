//package main;
//
//import common.Constants;
//import foodyCrawler.CrawlerLinksGetter;
//
//public class LinksGetterMainDN {
//
//	public static void main(String[] args) throws InterruptedException {
//		
//		// Crawler cho nhà hàng
//		 CrawlerLinksGetter T = null;
//		 
//		 for (String cat: Constants.CAT_LIST_DN) {    
//			 
//			 String[] arrCat = cat.split("@");
//			 
//			 T = new CrawlerLinksGetter(Constants.DN_RES_NAME
//                     ,Constants.DN_URL_FIRST
//                     ,Constants.DN_URL_LAST
//                     ,Integer.parseInt(arrCat[1])
//                     ,arrCat[0]
//                     ,Constants.DN_CODE
//                     , Constants.DN_LINK_PATH
//                     ,arrCat[2]);
//			 
//			 	T.start();
//			 	T.join();
//		    }	      
//	}
//
//}
