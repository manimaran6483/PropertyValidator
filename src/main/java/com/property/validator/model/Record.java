package com.property.validator.model;

import java.util.ArrayList;
import java.util.List;

public class Record {

	private String fileName;
	private List<String> missingProperties = new ArrayList<>();
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getMissingProperties() {
		return missingProperties;
	}
	public void setMissingProperties(List<String> missingProperties) {
		this.missingProperties = missingProperties;
	}
	
}
