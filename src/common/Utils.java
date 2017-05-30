package common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static void backUpFile(String moveFile, String desFolder)
	{
		File  des = new File(desFolder);
		if(!des.exists())
		{
			des.mkdirs();
		}
		
		File mvFile = new File(moveFile);
		if(mvFile.exists())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
			String desFile = desFolder + "/" + sdf.format(new Date()) + "_" + mvFile.getName();
			mvFile.renameTo(new File(desFile));
		}
	}
	
	public static void moveFile(String moveFile, String desFolder) throws IOException
	{
		File  des = new File(desFolder);
		if(!des.exists())
		{
			des.mkdirs();
		}
		
		File mvFile = new File(moveFile);
		if(mvFile.exists())
		{
			String sDesFile = desFolder + "/" + mvFile.getName();
			File desFile = new File(sDesFile);
			if(desFile.exists()){
				desFile.delete();
			}
			
			//mvFile.renameTo(desFile);
			Files.copy(mvFile.toPath(), desFile.toPath());
		}
	}
}
