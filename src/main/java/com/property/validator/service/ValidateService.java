package com.property.validator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
		
		String referenceFile = request.getReferenceFileName();
		
		Response response = new Response();
		response.setStatus("0");
		
		Set<Object> referenceKeysSet = null;
		boolean invalid = false;
		
		try {
			Properties referenceProps = getKeys(rootPath, referenceFile+ Constants.DOT_PROPERTIES); //opstest.properties
			
			if(referenceProps != null) {
				
				referenceKeysSet = referenceProps.keySet();
				
			}else {
				
				Record record = new Record();
				record.setFileName("Exception Occured while reading " + referenceFile + Constants.DOT_PROPERTIES);
				response.getDifferences().add(record);
				invalid = true;
				
			}
			
			if(referenceKeysSet != null && !referenceKeysSet.isEmpty()) {
				
				for (String file : Constants.envList) {
					
						File tempFile = new File(rootPath+ file + Constants.DOT_PROPERTIES);
						if(tempFile.exists()) {
							
							Set<Object> referencePropKeysCopy = new HashSet<>();
							referencePropKeysCopy.addAll(referenceKeysSet);
							
							Record rec = validateFile(referencePropKeysCopy, rootPath, file + Constants.DOT_PROPERTIES);
							
							if (!rec.getMissingKeys().isEmpty()) {
								//updateFile(rec, rootPath, file + Constants.DOT_PROPERTIES);
								invalid = true;
								response.getDifferences().add(rec);
								rec.setFileName("Warning!!! "+ file + Constants.DOT_PROPERTIES + " is missing some keys.");
							}
							
							if(!rec.getMissingValues().isEmpty()) {
								invalid = true;
								
								if(response.getDifferences().stream().filter(e-> e.getFileName().contains(file)).count() > 0){
									rec.setFileName("Warning!!! "+ file + Constants.DOT_PROPERTIES + " is missing some keys and has empty values");
								}else {
									response.getDifferences().add(rec);
									rec.setFileName("Warning!!! "+ file + Constants.DOT_PROPERTIES + " has empty values for some keys.");
								}
							}
							
							
						}else {
							
							Record record = new Record();
							record.setFileName(file + Constants.DOT_PROPERTIES + " not present in GIT config folder.");
							response.getDifferences().add(record);
							
							invalid = true;
							
						}
					
				}
				
			}
			
			if (invalid) {
				response.setMessage("Config is not up to date.");
				response.setStatus("1");
			} else {
				response.setMessage("Config is up to date.");
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return response;
		
	}


	private Record validateFile(Set<Object> referencePropKeys, String rootPath, String targetFileName) {
		List<String> keyList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		
		Record record = new Record();
		record.setMissingKeys(keyList);
		record.setMissingValues(valueList);
		
		try {
			
			Properties targetProps = getKeys(rootPath,targetFileName);
			
			if(targetProps != null) {
				
				referencePropKeys.removeAll(targetProps.keySet());
				
				for(Object k : referencePropKeys){
		            String key = (String)k;
		            keyList.add(key);
		        }
				record.setFileName(targetFileName);
				
				
				for(Entry<Object,Object> entry: targetProps.entrySet()) {
					if(StringUtils.isBlank((String) entry.getValue())) {
						valueList.add((String) entry.getKey());
					}
				}
				
			}else {
				record.setFileName("Exception Occured while reading " + targetFileName + Constants.DOT_PROPERTIES);
			}
			
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return record;
	}

	private Properties getKeys(String rootPath, String fileName){
		
		InputStream is = null;
		Properties propeties = null;
		
		try {
			propeties = new Properties();
			
			is = new FileInputStream(new File(new StringBuffer().append(rootPath).append(fileName).toString()));

			propeties.load(is);
			
			is.close();

		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		return propeties;
	}

}
