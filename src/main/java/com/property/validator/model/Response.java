package com.property.validator.model;

import java.util.ArrayList;
import java.util.List;

public class Response {

	private String message;
	private String status;
	private List<Record> differences = new ArrayList<>();
	
	
	
	public Response(String message, String status) {
		super();
		this.message = message;
		this.status = status;
	}
	public Response() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Record> getDifferences() {
		return differences;
	}
	public void setDifferences(List<Record> differences) {
		this.differences = differences;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
