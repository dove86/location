package com.py.model.baidu;

import lombok.Data;

@Data
public class Result {

	private Location location;
	
	private int precise;
	
	private int confidence;
	
	private String level;


}
