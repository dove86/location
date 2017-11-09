package com.py.coordinate.core.handler;

import com.google.gson.Gson;
import com.py.common.DailyCounter;
import com.py.common.PyConstants;
import com.py.model.baidu.Location;
import com.py.utils.LoggerUtils;
import com.py.utils.PyStringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.py.common.PyConstants.FIELD_WRAPPER;
import static com.py.common.PyConstants.PATH_SEPARATOR;

public class FileHandler {

	public void handleFile(DailyCounter dailyCounter, ExecutorService executor, File file, String outPutPath,
			int daily_reqs_times_limit, long recheck_interval) {
		try {

			LoggerUtils.info("\n-----------------------------------------------------------------file start-------------------------------------------------------------------------------\n");
			LoggerUtils.info("parsing file:[" + file.getAbsolutePath() + "] start..............");
			long startTime = System.currentTimeMillis();
			List<String> lineList = FileUtils.readLines(file, PyConstants.ENCODING);
//			List<LineHandler> lineHandlers = new ArrayList<LineHandler>();
			List<Future<Location>> results = new ArrayList<>();
			int lineNumber = 0;
			for (String line : lineList) {
				lineNumber++;
				if (StringUtils.isBlank(line)) {
					continue;
				}
				LineHandler lineHandler = new LineHandler(line, file.getAbsolutePath(), lineNumber, dailyCounter, daily_reqs_times_limit, recheck_interval);
//				lineHandlers.add(lineHandler);
				results.add(executor.submit(lineHandler));
			}
//			List<Future<Location>> results = executor.invokeAll(lineHandlers);



			Vector<Location> successList = new Vector<Location>();
			Vector<Location> errorList = new Vector<>();
			for (Future<Location> result : results) {

				Location location = result.get();
				if (StringUtils.isBlank(location.getMsg())) {
					successList.addElement(location);
				} else {
					errorList.addElement(location);

				}
				LoggerUtils.debug(new Gson().toJson(location));
			}

			long endTime = System.currentTimeMillis();
			double timeEaplse = (endTime - startTime) / 1000;
			LoggerUtils.info("timeEaplse:" + timeEaplse);
			double perSecondHandledReq = results.size() / timeEaplse;
			LoggerUtils.info("perSecondHandledReq:" + perSecondHandledReq);

			writeToFile(successList, errorList, file.getAbsolutePath(), outPutPath);
			file.renameTo(new File(file.getAbsolutePath() + PyConstants.IN_FILE_PARSED_NAME_SUFFIX));// 处理完
																										// 修改文件名

		} catch (Exception e) {
			LoggerUtils.error(e.getMessage());
			e.printStackTrace();
		} finally {
			LoggerUtils.info("parse file:[" + file.getAbsolutePath() + "] end..............");
			LoggerUtils.info("\n-----------------------------------------------------------------file end-------------------------------------------------------------------------------\n");
		}
	}

	private void writeToFile(Vector<Location> successList, Vector<Location> errorList, String orignalFielName,
			String targetFolder) {

		String successFileFullName = getOutputSuccessFileName(targetFolder, FilenameUtils.getBaseName(orignalFielName));
		String rawResponseFileFullName = getOutputRawResponseFileName(targetFolder, FilenameUtils.getBaseName(orignalFielName));
		String errorFileFullName = getOutputErrorFileName(targetFolder, FilenameUtils.getBaseName(orignalFielName));
		String errorSummaryFileFullName = getOutputErrorSummaryFileName(targetFolder);
		List<String> successrStrList = new ArrayList<>();
		List<String> allRawResponseStrList = new ArrayList<>();
		List<String> errorStrList = new ArrayList<>();
		List<String> errorSummaryStrList = new ArrayList<>();

		if (successList != null && successList.size() > 0) {
			for (Location location : successList) {
				successrStrList.add(getOutSuccessStr(location));
				allRawResponseStrList.add(getRawResponseStr(location));
			}
		}
		if (errorList != null && errorList.size() > 0) {
			for (Location location : errorList) {
				errorStrList.add(getOutErrorStr(location));
				errorSummaryStrList.add(getOutErrorSummaryStr(location));
				allRawResponseStrList.add(getRawResponseStr(location));
			}
		}
		try {
			if (successrStrList.size() > 0) {

				FileUtils.writeLines(new File(successFileFullName), PyConstants.ENCODING, successrStrList);
			}
			if (allRawResponseStrList.size() > 0) {
				
				FileUtils.writeLines(new File(rawResponseFileFullName), PyConstants.ENCODING, allRawResponseStrList);
			}
			if (errorStrList.size() > 0) {
				FileUtils.writeLines(new File(errorFileFullName), PyConstants.ENCODING, errorStrList);
				FileUtils.writeLines(new File(errorSummaryFileFullName), PyConstants.ENCODING, errorSummaryStrList,
						true);
			}

		} catch (Exception e) {
			LoggerUtils.error(e.getMessage());
			e.printStackTrace();
		}

	}

	private String getOutputSuccessFileName(String outPutPath, String dataFileName) {
		boolean hasPathSuffix = outPutPath.endsWith(PATH_SEPARATOR) || outPutPath.endsWith("\\");
		return outPutPath + (hasPathSuffix ? "" : PATH_SEPARATOR) + dataFileName
				+ PyConstants.OUT_SUCCESS_FILE_NAME_SUFFIX;
	}
	private String getOutputRawResponseFileName(String outPutPath, String dataFileName) {
		boolean hasPathSuffix = outPutPath.endsWith(PATH_SEPARATOR) || outPutPath.endsWith("\\");
		return outPutPath + (hasPathSuffix ? "" : PATH_SEPARATOR) + dataFileName
				+ PyConstants.OUT_ALL_RAW_RESPONSE_FILE_NAME_SUFFIX;
	}

	private String getOutputErrorFileName(String outPutPath, String dataFileName) {
		boolean hasPathSuffix = outPutPath.endsWith(PATH_SEPARATOR) || outPutPath.endsWith("\\");
		return outPutPath + (hasPathSuffix ? "" : PATH_SEPARATOR) + dataFileName
				+ PyConstants.OUT_ERROR_FILE_NAME_SUFFIX;
	}

	private String getOutputErrorSummaryFileName(String outPutPath) {
		boolean hasPathSuffix = outPutPath.endsWith(PATH_SEPARATOR) || outPutPath.endsWith("\\");
		return outPutPath + (hasPathSuffix ? "" : PATH_SEPARATOR) + PyConstants.OUT_ERROR_SUMMARY_FILE_NAME;
	}

	private static String getOutSuccessStr(Location location) {

		Object[] array = new Object[] { PyStringUtils.wraper(location.getId(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getAddress(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getLng(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getLat(), FIELD_WRAPPER) };

		return StringUtils.join(array, ",");
	}
	private static String getRawResponseStr(Location location) {
		
		Object[] array = new Object[] { PyStringUtils.wraper(location.getId(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getAddress(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getRawResponse(), FIELD_WRAPPER)};
		
		return StringUtils.join(array, ",");
	}

	private static String getOutErrorStr(Location location) {

		Object[] array = new Object[] { PyStringUtils.wraper(location.getId(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getAddress(), FIELD_WRAPPER) };

		return StringUtils.join(array, ",");
	}

	private static String getOutErrorSummaryStr(Location location) {

		Object[] array = new Object[] { PyStringUtils.wraper(location.getId(), FIELD_WRAPPER),
				PyStringUtils.wraper(location.getAddress(), FIELD_WRAPPER), "line:[" + location.getLineNumber() + "]",
				"file:[" + location.getOriginalFile() + "]" + location.getMsg() };

		return StringUtils.join(array, "\t");
	}
}
