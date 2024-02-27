package com.property.validator.model;

import java.util.List;

public class Record {

	private String fileName;
	private List<String> missingKeys;
	private List<String> missingValues;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getMissingKeys() {
		return missingKeys;
	}
	public void setMissingKeys(List<String> missingKeys) {
		this.missingKeys = missingKeys;
	}
	public List<String> getMissingValues() {
		return missingValues;
	}
	public void setMissingValues(List<String> missingValues) {
		this.missingValues = missingValues;
	}
	
}
