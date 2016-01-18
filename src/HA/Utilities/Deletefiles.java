package HA.Utilities;

import java.io.File;

import HA.Properties.logApp;

public class Deletefiles {
	
	public static void startingwith(String path,String name)
	{
	
//		File directory = new File("C:/Users/yrram/Downloads");
		File directory = new File(path);
		File[] files = directory.listFiles();
		for (File f : files)
		{
			if (f.getName().startsWith(name))
			{
				f.delete();
				logApp.logger.info("file deleted"+f.getAbsolutePath());
			}
		}
		/*Driver.logger.info("Below are Files after deletion in directory");
		for (File f : files)
		{
			Driver.logger.info(f.getAbsolutePath());
		}*/
	}

}
