package com.py.model.baidu;


public class BaiduResult {

	private BaiduLocation location;
	
	private int precise;
	
	private int confidence;
	
	private String level;

	public BaiduLocation getLocation() {
		return location;
	}

	public void setLocation(BaiduLocation location) {
		this.location = location;
	}

	public int getPrecise() {
		return precise;
	}

	public void setPrecise(int precise) {
		this.precise = precise;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
