package com.property.validator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Temp {

	static final String BASE_PATH = "C:/Users/mradha01/git/ifpdirectsalesapiv2/data/documents/ifpoffex/PDFJSONUW/2023/Feb/";
	public static void main(String[] args) {
		
		LocalTime time1 = LocalTime.now(ZoneId.of("America/Los_Angeles"));
		System.out.println(time1.toString());
		
		
		Instant start = Instant.now();
		System.out.println(start.toString());
		start.atZone(ZoneId.of("America/Los_Angeles"));
		long startl = System.currentTimeMillis();
		System.out.println(start.toString());
		System.out.println(startl);
		System.out.println(new Date(startl));
		Instant end = Instant.now();
		long endl = System.currentTimeMillis();
		System.out.println(endl);
		System.out.println(end.toString());
		Duration timeElapsed = Duration.between(start, end);
		System.out.println(timeElapsed.toMillis());
		System.out.println("Current Millis : " + (endl-startl));
		
		
		
		LocalTime time2 = LocalTime.now(ZoneId.of("America/Los_Angeles"));
		System.out.println(time2.toString());
		LocalTime time3 = LocalTime.now(ZoneId.of("America/Los_Angeles"));
		System.out.println(time3.toString());
		
		System.out.println(time2.minusNanos(time1.toNanoOfDay()));
	}
	
}
