package com.property.validator.model;

public class Request {

	private String rootFolder;
	private String referenceFileName;
	
	public Request() {
		super();
	}
	
	public Request(String rootFolder, String referenceFileName) {
		super();
		this.rootFolder = rootFolder;
		this.referenceFileName = referenceFileName;
	}
	
	@Override
	public String toString() {
		return "Request [rootFolder=" + rootFolder + ", referenceFileName=" + referenceFileName + "]";
	}
	
	public String getRootFolder() {
		return rootFolder;
	}
	public void setRootFolder(String rootFolder) {
		this.rootFolder = rootFolder;
	}
	public String getReferenceFileName() {
		return referenceFileName;
	}
	public void setReferenceFileName(String referenceFileName) {
		this.referenceFileName = referenceFileName;
	}
	
	
}
