package com.py.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.py.common.PyConstants;

public class PyFileUtils {

	/**
	 * @param content
	 * @param fileName
	 * @param append default false
	 */
	public static void writeToFile(String fileName,String content,boolean append){
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName,append));
			writer.write(content);
			writer.newLine();
			writer.flush();
			
		} catch (Exception e) {
			LoggerUtils.error(e.getMessage());
			throw new RuntimeException(e);
		}
		finally {
			if (writer !=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					LoggerUtils.error(e.getMessage());
				}
			}
		}
		
	}
	public static void writeToFile(String fileName,String content){
		writeToFile(fileName,content, false);
	}
	public static void createDiretoryIfNotExits(String path){
		File file = new File(path) ;
		createDiretoryIfNotExist(file);
	}
	public static void createDiretoryIfNotExist(File file){
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static synchronized void  writeReqCounter(int reqCounter, long recheck_interval) {
		PyFileUtils.writeToFile(getReqCounterLogFile(),
				PyDateUtils.format(new Date(), PyDateUtils.getDateFormat(recheck_interval)) + PyConstants.DELIMITER + reqCounter);
	}
	public static String getReqCounterLogFile() {
		String userDir = System.getProperty("user.dir");
		userDir += PyConstants.PATH_SEPARATOR + PyConstants.REQ_COUNTER_FILE_NAME;
//		LoggerUtils.info(userDir);
		return userDir;
	}
	public static synchronized int readReqCounter(long recheck_interval) {
		String currentDateStr = PyDateUtils.format(new Date(),PyDateUtils.getDateFormat(recheck_interval));
		File file = new File(getReqCounterLogFile());
		if (!file.exists()) {
			return 0;
		}
		List<String> lineList;
		try {
			lineList = FileUtils.readLines(new File(getReqCounterLogFile()),PyConstants.ENCODING);//Charset.defaultCharset()
			for (String line : lineList) {
				if (StringUtils.isNotBlank(line) && line.startsWith(currentDateStr)) {
					return Integer.parseInt(line.split(PyConstants.DELIMITER)[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			LoggerUtils.error(e.getMessage());
		}

		return 0;
	}
}
