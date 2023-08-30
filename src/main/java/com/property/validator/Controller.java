package com.property.validator;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.validator.constants.Constants;
import com.property.validator.model.Request;
import com.property.validator.model.Response;
import com.property.validator.service.ValidateService;

@RestController
public class Controller {

	/*@Autowired
	private ValidateService validateService;*/
	
	@Value("${snowflake.url}")
	private String url;

	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	/*
	 * Write Exception Handlers
	 * Return ResponseEntity with HTTP status instead of String
	 * Add Loggers
	 * Write Junits If needed
	 * 
	 */
	
	/*@PostMapping("/validate/propeties")
	public ResponseEntity<Response> validateProperties(@RequestBody Request request) {
		LOGGER.debug("Controller - Entering validateProperties");
		Response res = null;
		res = validateRequest(request);
		if (res.getStatus().equalsIgnoreCase("1")) {
			LOGGER.debug("Controller - BAD REQUEST");
			return new ResponseEntity<Response>(res, HttpStatus.BAD_REQUEST);
		}
		LOGGER.debug("Controller - Exiting validateProperties");
		return new ResponseEntity<Response>(validateService.validate(request), HttpStatus.OK);
	}*/
	
	
	public void validateProperties(@RequestBody Request request) {
		LOGGER.debug("Controller - Entering validateProperties");
		try{
			ObjectMapper mapper = new ObjectMapper();
			Response res = null;
			res = validateRequest(request);
			if (res.getStatus().equalsIgnoreCase("1")) {
				LOGGER.debug("Controller - BAD REQUEST");
				System.out.println(mapper.writeValueAsString(res));
			}
			LOGGER.debug("Controller - Exiting validateProperties");
			ValidateService validateService = new ValidateService();
			System.out.println(mapper.writeValueAsString(validateService.validate(request)));
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
	}

	private Response validateRequest(Request request){
		LOGGER.debug("Controller - Entering validateRequest");
		if(request == null){
			return new Response("Request is empty","1");
		}else if (StringUtils.isBlank(request.getRootFolder())) {
			return new Response("Root Folder is not sent in request","1");
		}else if(!new File(request.getRootFolder()).isDirectory()){
			return new Response("Root Folder is not a Directory","1");
		}else if (StringUtils.isBlank(request.getReferenceFileName())) {
			return new Response("Reference File Name is not sent in request","1");
		} else if (!Constants.envList.contains(request.getReferenceFileName())) {
			return new Response("Invalid Reference File Name is sent in request","1");
		} else if (StringUtils.isBlank(request.getTargetFileName())) {
			return new Response("Target File Name is not sent in request","1");
		} else if (request.getReferenceFileName().equalsIgnoreCase(request.getTargetFileName())) {
			return new Response("Both Reference and Target file are same","1");
		} else if (!Constants.envList.contains(request.getTargetFileName())
				&& !request.getTargetFileName().equalsIgnoreCase("all")) {
			return new Response("Invalid Target File Name is sent in request","1");
		}
		request.setReferenceFileName(request.getReferenceFileName()+Constants.DOT_PROPERTIES);
		if(!request.getTargetFileName().equalsIgnoreCase("all")){
			request.setTargetFileName(request.getTargetFileName()+Constants.DOT_PROPERTIES);
		}
		
		LOGGER.debug("Controller - Exiting validateRequest");
		
		return new Response("","0");

	}
	
}
