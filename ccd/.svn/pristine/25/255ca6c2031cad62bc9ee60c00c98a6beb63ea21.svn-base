package com.pcs.util.faultmonitor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;

public class GzipFaultFileUtil {

	private static final String NOTIFICATION_VERSION = "Notification_Version";

	public static List<String> readZipFile(File file) throws Exception{
		List<String> fileContent = new ArrayList<String>();
		if(GzipUtils.isCompressedFilename(file.getName())){

			FileInputStream reader = new FileInputStream(file);
			GzipCompressorInputStream compressorInputStream = new GzipCompressorInputStream(reader,false);
			Reader decoder = new InputStreamReader(compressorInputStream,"UTF-8");
			BufferedReader bufferedReader =new BufferedReader(decoder);

			String faultZipLine = null;
			boolean register = false;
			while((faultZipLine=bufferedReader.readLine())!= null){
				System.err.println(faultZipLine);
				if(faultZipLine.contains(NOTIFICATION_VERSION)){
					register = true;
					faultZipLine = faultZipLine.substring(faultZipLine.indexOf(NOTIFICATION_VERSION)).trim();
				}
				if(register){
					if(faultZipLine.trim().length() >0)
						fileContent.add(faultZipLine.trim());
				}
			}
			bufferedReader.close();
			bufferedReader = null;
			compressorInputStream.close();
			compressorInputStream = null;
			reader.close();
			reader = null;
			return fileContent;
		}else{
			throw new Exception("Not A Gzip File!!");
		}
	}

}
