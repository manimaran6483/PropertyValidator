package com.property.validator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.property.validator.constants.Constants;
import com.property.validator.model.Record;
import com.property.validator.model.Request;
import com.property.validator.model.Response;

@Component
public class ValidateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateService.class);
	
	public Response validate(Request request) {

		String rootPath = request.getRootFolder();
		String referenceFile = request.getReferenceFileName().substring(0, request.getReferenceFileName().lastIndexOf("."));
		
		Response response = new Response();
		List<Record> recordList = new ArrayList<>();
		response.setDifferences(recordList);

		Set<Object> referencePropKeys = getKeys(request.getRootFolder(),request.getReferenceFileName()); // 15 properties
		boolean invalid = false;

		for (String file : Constants.envList) {
			if (!referenceFile.equalsIgnoreCase(file)) {
				File tempFile = new File(rootPath+ file + Constants.DOT_PROPERTIES);
				if(tempFile.exists()) {
					Set<Object> referencePropKeysCopy = new HashSet<>();
					referencePropKeysCopy.addAll(referencePropKeys);
					Record rec = validateProps(referencePropKeysCopy, rootPath, file + Constants.DOT_PROPERTIES);
					response.getDifferences().add(rec);
					if (!rec.getMissingProperties().isEmpty())
						invalid = true;
				}else {
					Record record = new Record();
					record.setFileName(file + Constants.DOT_PROPERTIES + " not present in GIT config folder.");
					record.setMissingProperties(new ArrayList<String>());
					response.getDifferences().add(record);
					invalid = true;
				}
			}
		}
		//String folderName = getFolderName(rootPath);
		if (invalid) {
			response.setMessage(" Service is not up to date.");
			response.setStatus("1");
		} else {
			response.setMessage(" Service is up to date. No differences found");
			response.setStatus("0");
		}
		
		return response;
		
	}

//	private String getFolderName(String rootPath) {
//		rootPath = rootPath.substring(0, rootPath.length()-1);
//		return rootPath.substring(rootPath.lastIndexOf("\\")+1, rootPath.length());
//	}

	private Record validateProps(Set<Object> referencePropKeys, String rootPath, String targetFileName) {
		List<String> diffList = new ArrayList<String>();
		
		Set<Object> targetPropKeys = getKeys(rootPath,targetFileName); // 14 properties 
		
		referencePropKeys.removeAll(targetPropKeys);
		
		for(Object k : referencePropKeys){
            String key = (String)k;
            diffList.add(key);
        }
		
		Record record = new Record();
		record.setFileName(targetFileName);
		record.setMissingProperties(diffList);
		return record;
	}

	private Set<Object> getKeys(String rootPath, String fileName){
		InputStream is = null;
		Properties propeties = null;
		try {
			propeties = new Properties();
			
			//is = new FileInputStream(new StringBuffer().append(rootPath).append(fileName).toString());
			is = new FileInputStream(new File(new StringBuffer().append(rootPath).append(fileName).toString()));

			propeties.load(is);
			
			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return propeties.keySet();
	}
}
