package com.interview.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileProcessingUtil {

	
	
	public static boolean fileSaved(MultipartFile file) {
		boolean result = false ; 
		
		FileOutputStream fileOutputStream = null;
		String uploadedFileLocation = "D:\\Interview\\";
		String fileLocation = uploadedFileLocation
				+ file.getOriginalFilename();
		try {

			byte[] bytes = file.getBytes();

			fileOutputStream = new FileOutputStream(fileLocation);
			fileOutputStream.write(bytes);

			if (fileOutputStream != null) {
				fileOutputStream.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			result =  false ;
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result ;
	
	}
}
