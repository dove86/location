package com.py.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class Test {

	public static void main(String[] args) {
		
//		DailyCounter dailyCounter = DailyCounter.init();
//		while (true) {
//
//			int cnt = dailyCounter.count();
//
//			System.out.println(new Gson().toJson(dailyCounter));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			if (!StringUtils.equals(dailyCounter.getDateStr(), PyDateUtils.getCurrentDateStr())) {
//				dailyCounter.resetCounter();
//			}
			
//		}
		String parsedFileNameSuffix = "parsed";

		Collection<File> files =  FileUtils.listFiles(new File("E:\\addressIn"),new String[]{parsedFileNameSuffix},false);
		Iterator<File> fileIterator = files.iterator();
		while(fileIterator.hasNext()){
			File file =fileIterator.next();
			String newFileName = file.getAbsolutePath().replace("."+parsedFileNameSuffix,"");
			System.out.println(newFileName);
			file.renameTo(new File(newFileName));
		}
	}
}
