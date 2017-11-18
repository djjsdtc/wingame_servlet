package org.wingame.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String md5(String str){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			digest.update(str.getBytes());
			byte[] bytes = digest.digest();
			return MD5Util.getHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getHexString(byte[] bytes){
		if(bytes == null) return null;
		StringBuffer strBuf = new StringBuffer(bytes.length * 2);
		for(int i = 0;i < bytes.length;i++){
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if(hex.length() == 2) strBuf.append(hex);
			else strBuf.append("0" + hex);
		}
		return strBuf.toString();
	}
}
