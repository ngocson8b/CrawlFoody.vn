package foodyCrawler;

import java.io.File;

import org.apache.log4j.Logger;

import common.Constants;
import common.Utils;

public class CrawlerLinksGetter extends Thread {
	 private Thread t;
	 private String urlFirst;
	 private String urlLast;
	 private int pageCount;
	 public String outputFileUrl;
	 
	 private String threadName;
	 
	 final static Logger logger = Logger.getLogger(CrawlerLinksGetter.class);
	 
	 public CrawlerLinksGetter( String name, String pUrlFirst, String pUrlLast, int pPageCount, String pItemName, String pCode, String pLinkPath, String pCate) {
	      urlFirst = pUrlFirst.replace("@item", pItemName).replace("@cat", name).replace("@cate", pCate);
	      urlLast = pUrlLast.replace("@cate", pCate);
	      pageCount = pPageCount;
	      threadName = pItemName;
	      outputFileUrl = pLinkPath + pCode + "_" + pItemName.toLowerCase() + ".txt";
	      
	      File linkPath = new File(pLinkPath);
	      if(!linkPath.exists()){
	    	  linkPath.mkdirs();
	      }
	      
	   }
	   
	   public void run() {
	    logger.info("Running " +  threadName );
	      try {
	    	  Utils.backUpFile(outputFileUrl, Constants.BACKUP_FILE);
	    	  
	    	  Link link = new Link(threadName);
	  		  link.getLink(urlFirst, urlLast, pageCount, outputFileUrl);
	            // Let the thread sleep for a while.
	           
	      }catch(Exception ex){
	    	  logger.info(ex);
	      }
	      
	      logger.info("Thread " +  threadName + " exiting.");
	   }
	   
	   public void start () {
		  
		   logger.info("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}
