package com.journaldev.spring.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

public class RequestJson implements Serializable{

	private static final long serialVersionUID = -7788619177798333712L;
	
	private String destinationFile;
	private String status;
	public String getDestinationFile() {
		return destinationFile;
	}
	public void setDestinationFile(String destinationFile) {
		this.destinationFile = destinationFile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
