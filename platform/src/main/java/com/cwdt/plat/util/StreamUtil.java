package com.cwdt.plat.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {
	/**
	 *
	 * ByteArrayOutputStream 读取 InputStream
	 *
	 */
	public static byte[] readStream(InputStream inStream) throws Exception{
    	ByteArrayOutputStream outStream=new ByteArrayOutputStream();
    	byte[] buffer = new byte[1024];
    	int len=-1;
    	while((len=inStream.read(buffer))!=-1){
    		outStream.write(buffer,0,len);
    	}
    	byte[] ret=outStream.toByteArray();
    	outStream.close();
    	return ret;
    }
}
