package com.property.validator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Temp {

	static final String BASE_PATH = "C:/Users/mradha01/git/ifpdirectsalesapiv2/data/documents/ifpoffex/PDFJSONUW/2023/Feb/";
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("ZIPARI_ZIP2023020805.pdf");
		list.add("ZIPARI_ZIP2023020809.pdf");
		list.add("ZIPARI_ZIP2023020810.pdf");
		list.add("ZIPARI_ZIP2023020811.pdf");
		list.add("ZIPARI_ZIP2023020813.pdf");
		list.add("ZIPARI_ZIP2023020814.pdf");
		list.add("ZIPARI_ZIP2023020819.pdf");
		list.add("ZIPARI_ZIP2023020823.pdf");
		list.add("ZIPARI_ZIP20230222101.pdf");
		list.add("ZIPARI_ZIP20230222103.pdf");
		list.add("ZIPARI_ZIP2023020805.json");
		list.add("ZIPARI_ZIP2023020809.json");
		list.add("ZIPARI_ZIP2023020810.json");
		list.add("ZIPARI_ZIP2023020811.json");
		list.add("ZIPARI_ZIP2023020813.json");
		list.add("ZIPARI_ZIP2023020814.json");
		list.add("ZIPARI_ZIP2023020819.json");
		list.add("ZIPARI_ZIP2023020823.json");
		list.add("ZIPARI_ZIP20230222101.json");
		list.add("ZIPARI_ZIP20230222103.json");
		
		//File Look UP
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(sdf.format(new Date()));
		for(String s : list){
			File f = new File(BASE_PATH+s);
			if(f.exists()){
				System.out.println("File Exists : "+ s);
			}else{
				System.out.println("File is not present in NFS : "+ s);
			}
			
		}
		System.out.println(sdf.format(new Date()));
	}
	
}
