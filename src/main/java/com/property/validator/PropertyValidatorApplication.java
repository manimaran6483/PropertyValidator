package com.property.validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.property.validator.model.Request;

@SpringBootApplication
public class PropertyValidatorApplication {

	public static void main(String[] args) {
		//SpringApplication.run(PropertyValidatorApplication.class, args);
		System.out.println(args.toString());
		Controller cntlr = new Controller();
		Request request = new Request();
		request.setRootFolder(args[0]);
		request.setReferenceFileName(args[1]);
		cntlr.validateProperties(request);
	}

}
