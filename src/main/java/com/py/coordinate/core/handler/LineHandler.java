package com.py.coordinate.core.handler;

import com.py.common.DailyCounter;
import com.py.common.PyConstants;
import com.py.coordinate.core.requester.BaiduCoordinateRequester;
import com.py.coordinate.core.requester.CoordinateRequester;
import com.py.model.AddressInfo;
import com.py.model.baidu.Location;
import com.py.utils.LoggerUtils;
import com.py.utils.PyDateUtils;
import com.py.utils.PyFileUtils;
import com.py.utils.PyStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Callable;

public class LineHandler implements Callable<Location> {

//	private Logger logger = Logger.getLogger(LineHandler.class);
	
	private String line;
	private String originalFileName;
	private int  lineNumber;
	private DailyCounter dailyCounter;
	private int  daily_reqs_times_limit;
	private long  recheck_interval;
	


	//String line,String originalFileName,AtomicInteger counter,int daily_reqs_times_limit,int recheck_interval
	public LineHandler(String line,String originalFileName,int lineNumber,DailyCounter dailyCounter,int daily_reqs_times_limit,long recheck_interval) {
		super();
		this.line = line;
		this.originalFileName = originalFileName;
		this.lineNumber = lineNumber;
		this.dailyCounter = dailyCounter;
		this.daily_reqs_times_limit = daily_reqs_times_limit;
		this.recheck_interval = recheck_interval;
	}

	//String line,String originalFileName,int lineNumber,AtomicInteger counter,int daily_reqs_times_limit,int recheck_interval
	private Location handleLine() {
//		logger.info("\n\t\t\t\t\t----------------------line begin----------------\t\t\t\t\t\n");
//		logger.info("line：["+line+"]");
		if (!StringUtils.equals(dailyCounter.getDateStr(), PyDateUtils.getCurrentDateStr())) {
			LoggerUtils.info("日期改变， 计数器将重置");
			dailyCounter.resetCounter();
		}
		if (StringUtils.isBlank(line)) {
			return null;
		}
		AddressInfo addressInfo = parseLine(line);
		if (addressInfo !=null) {
			
			if (dailyCounter.get()<daily_reqs_times_limit) {
				dailyCounter.count();
			}
			while (dailyCounter.get() >= daily_reqs_times_limit) {
				LoggerUtils.warn("达到每天最大请求量" + daily_reqs_times_limit);
				LoggerUtils.warn("程序将在" + recheck_interval / (1000 * 60) + "分钟后 重试");
				PyFileUtils.writeReqCounter(dailyCounter.get(),recheck_interval);
				try {
					Thread.sleep(recheck_interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
					LoggerUtils.error(e.getMessage());
				}
				int reqCounter = PyFileUtils.readReqCounter(recheck_interval);// 读取 存入的时间
				dailyCounter.set(reqCounter);// 重置计数器
				if (!StringUtils.equals(dailyCounter.getDateStr(), PyDateUtils.getCurrentDateStr())) {//  若计数器时间与当前时间不一致  重置计数器
					LoggerUtils.info("日期改变， 计数器将重置");
					dailyCounter.resetCounter();
				}
				LoggerUtils.info("counter:"+dailyCounter.get());
				LoggerUtils.info("当前时间:"+PyDateUtils.getCurrentDateTimeStr());
				PyFileUtils.writeReqCounter(reqCounter,recheck_interval);//重新写入
			}
			CoordinateRequester coordinateRequester = new BaiduCoordinateRequester();
//			CoordinateRequester coordinateRequester = new GaodeCoordinateRequester();
			
			Location location = coordinateRequester.getLocation(addressInfo);
			location.setOriginalFile(this.originalFileName);
			location.setLineNumber(lineNumber);
			return location;
		}
		else{
			Location location = new Location();
			location.setLineNumber(this.lineNumber);
			location.setOriginalFile(this.originalFileName);
			location.setMsg(line+" doesn't match the format [\"XXXXXXXXXX\"，\"XXXXXXXXXX\"]");
			return location;
		}

	}

	private AddressInfo parseLine(String line) {
		// logger.info("\n\t\t\t\t\t----------------------line
		// start----------------\t\t\t\t\t\n");
		// logger.info("lineContent:[" + line + "]");
		try {
			String[] cols = line.split(PyConstants.SPLIT_PATTERN);
			String id = PyStringUtils.removeWrapper(cols[0]);
			String address = PyStringUtils.removeWrapper(cols[1]);
			return new AddressInfo(id, address);
		} catch (Exception e) {
			LoggerUtils.error("line parse error,line detail["+line+"],"+e.getMessage());
			return null;
		}
	}

	@Override
	public Location call() throws Exception {
		return handleLine();
	}
}
