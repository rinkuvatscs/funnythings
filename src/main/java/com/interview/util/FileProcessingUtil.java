package com.interview.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileProcessingUtil {

	public static boolean fileSaved(MultipartFile file, String fileLocation) {
		boolean result = false;

		FileOutputStream fileOutputStream = null;
		fileLocation = fileLocation + file.getOriginalFilename();
		try {

			byte[] bytes = file.getBytes();

			fileOutputStream = new FileOutputStream(fileLocation);
			fileOutputStream.write(bytes);

			if (fileOutputStream != null) {
				fileOutputStream.flush();
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public static boolean deleteFile(String fileLocation) {
		boolean result = false ; 
		java.io.File file = new java.io.File(fileLocation) ;
		if(file.exists()) {
			result = file.delete();
		}
		
		return result ;
	}
}
