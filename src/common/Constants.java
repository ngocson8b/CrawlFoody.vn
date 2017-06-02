package common;

public class Constants {
	
	
	//Backup folder
	public static String BACKUP_FILE = "data/backup";
	
	// folder for getting store
	public static String PROCESS_FOLDER = "data/process";
	
	public static String GECKO_DRIVER = "drivers/geckodriver.exe";
	
	
	public static String FOODY_URL = "https://foody.vn"; 
	public static String HTML_SPLIT_CLASS = "ri-avatar";
	
	// Ha noi
	public static String HN_LINK_PATH = "data/hanoi/links/";
	public static String HN_INFO_PATH = "data/hanoi/info/";
	public static String HN_URL_FIRST = "https://www.foody.vn/ha-noi/@item?ds=@cat&vt=row&st=1&c=@cate&page=";
	public static String HN_URL_LAST = "&provinceId=218&categoryId=@cate&append=true";
	public static String HN_CODE = "hn";
	public static String HN_RES_NAME = "Restaurant";
	
	//Da nang
	public static String DN_LINK_PATH = "data/danang/links/";
	public static String DN_INFO_PATH = "data/danang/info/";
	public static String DN_URL_FIRST = "https://www.foody.vn/da-nang/@item?ds=@cat&vt=row&st=1&c=cate&page=";
	public static String DN_URL_LAST = "&provinceId=219&categoryId=@cate&append=true";
	public static String DN_CODE = "dn";
	public static String DN_RES_NAME = "Restaurant";
	
	//Ho chi minh
	public static String HCM_LINK_PATH = "data/hochiminh/links/";
	public static String HCM_INFO_PATH = "data/hochiminh/info/";
	public static String HCM_URL_FIRST = "https://www.foody.vn/ho-chi-minh/@item?ds=@cat&vt=row&st=1&c=@cate&page=";
	public static String HCM_URL_LAST = "&provinceId=217&categoryId=@cate&append=true";
	public static String HCM_CODE = "hcm";
	public static String HCM_RES_NAME = "Restaurant";
	
	
	
	public static String[] CAT_LIST_HN = {
//				"nha-hang@84@1",
			  "sang-trong@7@12"
//			  ,"buffet@7@39"
//			  ,"an-vat-via-he@84@11"
//			  ,"an-chay@7@56"
//			  ,"cafe@84@2"
//			  ,"quan-an@84@3"
//			  ,"bar-pub@20@4"
//			  ,"quan-nhau@84@54"
//			  ,"beer-club@6@43"
//			  ,"tiem-banh@63@6"
//			  ,"shop-online@81@27"
//			  ,"giao-com-van-phong@5@28"
//			  ,"foodcourt@2@79"
			  };
	
	public static String[] CAT_LIST_DN = {
//				"nha-hang@42@1",
			  "buffet@2@39"
//			  ,"an-vat-via-he@84@11"
//			  ,"an-chay@6@56"
//			  ,"cafe@84@2"
//			  ,"quan-an@84@3"
//			  ,"bar-pub@8@4"
//			  ,"quan-nhau@76@54"
//			  ,"beer-club@2@43"
//			  ,"tiem-banh@16@6"
//			  ,"shop-online@23@27"
									  };
	
	public static String[] CAT_LIST_HCM = {
//			"nha-hang@84@1"
//			  ,"sang-trong@7@12"
//			  ,"buffet@12@39"
//			  ,"an-vat-via-he@84@11"
			  "an-chay@26@56"
			  ,"cafe@84@2"
			  ,"quan-an@84@3"
			  ,"bar-pub@29@4"
			  ,"quan-nhau@84@54"
//			  ,"beer-club@9@43"
//			  ,"tiem-banh@84@6"
//			  ,"tiec-tan-noi@4"
//			  ,"shop-online@84@27"
//			  ,"giao-com-van-phong@12@28"
//			  ,"foodcourt@3@79"
			  };
}
