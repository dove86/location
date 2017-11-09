package com.py.common;

import com.py.utils.LoggerUtils;
import com.py.utils.PyDateUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 潘凤辉 2017年9月11日
 */
public class DailyCounter {

	private AtomicInteger counter = new AtomicInteger();

	private String dateStr;

	public AtomicInteger getCounter() {
		return counter;
	}

	public void setCounter(AtomicInteger counter) {
		this.counter = counter;
	}

	public synchronized String getDateStr() {
		return dateStr;
	}

	public synchronized void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public synchronized void resetCounter() {

		LoggerUtils.warn("重置每日计数器");
		this.dateStr = PyDateUtils.getCurrentDateStr();
		this.counter.set(0);

	}

	public int count() {
		return this.counter.incrementAndGet();
	}

	public int get() {
		return this.counter.get();
	}

	public void set(int i) {
		this.counter.set(i);
	}

	private DailyCounter() {

	}

	public static DailyCounter init() {
		DailyCounter dailyCounter = new DailyCounter();
		dailyCounter.getCounter().set(0);
		dailyCounter.setDateStr(PyDateUtils.getCurrentDateStr());
		return dailyCounter;
	}

}
