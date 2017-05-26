package foodyCrawler;

import java.io.IOException;
import java.nio.charset.Charset;

public class CrawlerTest {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\MaiPL\\Downloads\\geckodriver-v0.16.1-win64\\geckodriver.exe");
		System.setProperty("file.encoding","UTF-8");
//		Field charset = Charset.class.getDeclaredField("defaultCharset");
//		charset.setAccessible(true);
//		charset.set(null,null);
//		Link link = new Link();
//		link.getLink("https://www.foody.vn/ha-noi/nha-hang?ds=Restaurant&vt=row&st=1&c=1&page=", "&provinceId=218&categoryId=1&append=true");
		Information info = new Information();
		info.getInfo("1");
	}
}
