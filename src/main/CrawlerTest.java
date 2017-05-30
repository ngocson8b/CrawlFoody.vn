package main;

import java.io.IOException;
import java.nio.charset.Charset;

import common.Constants;
import foodyCrawler.Link;

public class CrawlerTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER);
		System.setProperty("file.encoding","UTF-8");
//		Field charset = Charset.class.getDeclaredField("defaultCharset");
//		charset.setAccessible(true);
//		charset.set(null,null);
		Link link = new Link("Test");
		//link.getLink("https://www.foody.vn/ha-noi/nha-hang?ds=Restaurant&vt=row&st=1&c=1&page=", "&provinceId=218&categoryId=1&append=true", 10);
		//Information info = new Information();
		//info.getInfo("1");
	}
}
