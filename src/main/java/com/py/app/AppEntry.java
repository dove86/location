package com.py.app;

import com.py.common.DailyCounter;
import com.py.common.PyConstants;
import com.py.coordinate.core.handler.FileHandler;
import com.py.utils.HttpUtils;
import com.py.utils.LoggerUtils;
import com.py.utils.PyDateUtils;
import com.py.utils.PyFileUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppEntry {

	private static volatile DailyCounter dailyCounter = DailyCounter.init();
	
	public static void main(String[] args) {

		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("参数错误，请提供输入 和输出 目录参数!");
		}
		String inputPath = args[0];
		String outPutPath = args[1];
		LoggerUtils.info("inputPath:"+inputPath);
		LoggerUtils.info("outPutPath:"+outPutPath);
		int theadNum = 20;
		if (args.length > 2) {

			theadNum = Integer.valueOf(args[2]);
		}
		LoggerUtils.info("theadNum:"+theadNum);
		int daily_reqs_times_limit = PyConstants.DAILY_REQS_TIMES_LIMIT;
		if (args.length > 3) {

			daily_reqs_times_limit = Integer.valueOf(args[3]);
		}
		long recheck_interval = PyConstants.RECKECK_INTERVAL;
		LoggerUtils.info("daily_reqs_times_limit:"+daily_reqs_times_limit);
		if (args.length > 4) {
			
			recheck_interval = Integer.valueOf(args[4])*1000*60;//分钟   为单位
		}
		LoggerUtils.info("recheck_interval:"+recheck_interval/(1000*60) +"分钟");
		LoggerUtils.info("date format:"+PyDateUtils.getDateFormat(recheck_interval));

		PyFileUtils.createDiretoryIfNotExits(outPutPath);
		File directory = new File(inputPath);
		String[] fileSuffix = new String[] { PyConstants.IN_FILE_NAME_EXTENSION };
		List<File> files = (List<File>) FileUtils.listFiles(directory, fileSuffix, false);
		if (files == null || files.size() == 0) {
			LoggerUtils.error("没有找到任何" + PyConstants.IN_FILE_NAME_EXTENSION + " 文件，解析目录：" + inputPath);
			return;
		}

		
		FileHandler fileHandler = new FileHandler();
		int reqCounter = PyFileUtils.readReqCounter(recheck_interval);
		LoggerUtils.info("初始reqCounter："+reqCounter);
		dailyCounter.set(reqCounter);
		ExecutorService executor = Executors.newFixedThreadPool(theadNum);
		for (File file : files) {
			fileHandler.handleFile(dailyCounter, executor, file, outPutPath,daily_reqs_times_limit,recheck_interval);
		}
		PyFileUtils.writeReqCounter(dailyCounter.get(), recheck_interval);
		HttpUtils.closeClient();
		executor.shutdown();

	}

	
}
