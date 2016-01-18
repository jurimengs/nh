/**
 * Copyright : http://www.sandpay.com.cn , 2007-2014
 * Project : lms
 * $Id$
 * $Revision$
 * Last Changed by SJ at 2014年3月4日 下午4:07:24
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * SJ         2014年3月4日        Initailized
 */
package com.org.utils;

import java.io.UnsupportedEncodingException;

/**
 * 安全密钥类
 */
public class SecurityUtil {
	public static String createPersionalKey(String sessionId) {
		byte[] encoded;
		try {
			encoded = DesUtil.encryptMode(sessionId.getBytes("UTF-8"));
			String dnHex = ByteUtil.bytes2HexStr(encoded);
			System.out.println("hex后的字符串:" + dnHex);
			return dnHex;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
