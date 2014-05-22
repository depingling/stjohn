package com.cleanwise.service.api.util.synchronizer;

import java.io.*;

public class ResourceLoaderUtil {

	public ResourceLoaderUtil() {
		super();
	}

	public String loadText(String resourcePathName) throws IOException {
		InputStream inputStream = getInputStream(resourcePathName);
		if (inputStream == null) {
			throw new IOException("Resource name \"" + resourcePathName
					+ "\" not found");
		}
		return loadText(inputStream);

	}

	public String loadText(InputStream inputStrem) throws IOException {
		byte[] buf = new byte[120 * 1000];
		try {
			int total = 0;
			while (true) {
				int numRead = inputStrem.read(buf, total, buf.length - total);
				if (numRead <= 0) {
					break;
				}
				total += numRead;
			}
			byte[] bytes = new byte[total];
			System.arraycopy(buf, 0, bytes, 0, total);
			final String resultString = new String(bytes);
			return resultString;
		} catch (IOException e) {
			throw e;
		} finally {
			inputStrem.close();
		}
	}

	private InputStream getInputStream(String resourcePathName) {

     	return getClass().getResourceAsStream(resourcePathName);
		
		/* for debug only, comment previous line and uncomment next lines
		 * set filePath acccording to you local cvs sourses 
		 */  
	/*	
		String filePath = "C:/cleanwisecvs/stjohn/src";
		filePath = filePath + resourcePathName;
		try {
			return new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	*/
	}


}
		
