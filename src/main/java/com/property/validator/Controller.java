package com.property.validator;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
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
		
		if(request.getSkipTests() !=null && "true".equalsIgnoreCase(request.getSkipTests()) ){
			res = validateService.skipTestForOtherEnv(request);
		}else {
			res = validateService.validate(request);
		}
		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}*/
	
	
	public void validateProperties(Request request) {
		try{
			ObjectMapper mapper = new ObjectMapper();
			
			Response res = null;
			
			res = validateRequest(request);
			
			System.out.println("******************************************REQUEST******************************************");
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
			
			if (res.getStatus().equalsIgnoreCase("1")) {
				
				System.out.println("BAD REQUEST");
				
			}else {
				
				ValidateService validateService = new ValidateService();
				res = validateService.validate(request);
				
			}
			
			System.out.println("******************************************RESPONSE******************************************");
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
			
			System.exit(Integer.parseInt(res.getStatus()));
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		
	}

	private Response validateRequest(Request request){
		if(request == null){
			return new Response("Request is empty","1");
		}else if (StringUtils.isBlank(request.getRootFolder())) {
			return new Response("Folder Path is not sent","1");
		}else if(!new File(request.getRootFolder()).isDirectory()){
			return new Response("Folder Path is not a Directory","1");
		}else if (StringUtils.isBlank(request.getReferenceFileName())) {
			return new Response("Reference File Name is not sent","1");
		} else if (!Constants.envList.contains(request.getReferenceFileName())) {
			return new Response("Invalid Reference File Name is sent","1");
		}
		
		return new Response("","0");

	}
	
}
