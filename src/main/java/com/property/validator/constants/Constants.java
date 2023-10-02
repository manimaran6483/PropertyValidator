package com.property.validator.constants;

import java.util.ArrayList;
import java.util.List;


public class Constants {

	public static final List<String> envList = new ArrayList<>();
	public static final String DOT_PROPERTIES = ".properties";
	
	static {
		envList.add("breakfix");
		envList.add("dev1");
		envList.add("dev2");
		envList.add("int1");
		envList.add("int2");
		envList.add("opstest");
		envList.add("opstest2");
		envList.add("prod");
		envList.add("qa1");
		envList.add("qa2");
		envList.add("releasetest");
		envList.add("stage");		
		envList.add("uat1");
		envList.add("uat2");
	}
	
	
}
