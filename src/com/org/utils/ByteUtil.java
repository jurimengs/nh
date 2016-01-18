package com.org.utils;


public class ByteUtil {

	//private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7','8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	//合并字节的数组
	public static byte[] union(byte[] bs1,byte[] bs2){
		byte[] bs = new byte[bs1.length+bs2.length];
		for (int i = 0; i < bs1.length; i++)
			bs[i] = bs1[i];
		for (int i = 0; i < bs2.length; i++)
			bs[bs1.length+i] = bs2[i];
		return bs;
	}
	
	//截取指定长度字节数组
	public static byte[] sub(byte[] bs,int first,int length){
		length = length >(bs.length - first)?(bs.length-first):length;
		byte[] sub = new byte[length];
		for (int i = 0; i < length; i++) {
			sub[i] = bs[first+i];
		}
		return sub;
	} 
	
	/**
	 * 二进制数组转base64字符串
	 * @param bsb
	 * @return 
	 */
	public static String bytes2Base64str(byte[] bsb){
		if(bsb != null){
			return Base64Encoder.encode(bsb);
		}
		return null;
	}
	/**
	 * base64字符串转二进制数组
	 * @param base64str
	 * @return
	 */
	public static byte[] bas64str2Bytes(String base64str){
		if(base64str != null){
			return Base64Decoder.decode(base64str);
		}
		return null;
	}
	
	/**
	 * 二进制数组转十六机制字符串
	 * @param b
	 * @return
	 */
	public static String bytes2HexStr(byte[] bs) {
		StringBuffer sb=new StringBuffer();
    	for (int i = 0; i < bs.length; i++) {
    		int nn = bs[i]< 0 ? bs[i] + 256 : bs[i];
    		String t = Integer.toHexString(nn).toUpperCase();
    		sb.append(t.length()<2?"0"+t:t);
		}
    	return sb.toString();
	}
	
	/**
	 * 十六进制转二进制数组
	 * @param s
	 * @return
	 */
	public static final byte[] hex2Bytes(String s) {
		if(s == null){
			return null;
		}
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),16);
		}
		return bytes;
	}
	
	
	/**
     * Ascii转换为16进制方式
     * 例如：字符AB转换为0xAB对应的字符。
     * @param bs 待转换的byte数组
     * @return 压缩为16进制后的byte数组
     */
    public static byte[] ascii2hex(byte[] bs) {
        byte[] res = new byte[bs.length / 2];
        for (int i = 0, n = bs.length; i < n; i += 2) {
            res[i / 2] = (byte) (Integer.parseInt(new String(bs, i, 2), 16));
        }
        return res;
    }
    
    /**
     * 16进制转换为Ascii
     * 例如： 字符0xAB转换为Ascii的两个字符AB
     * @param bs 待转换的16进制的数组
     * @return 解压后的Ascii数组
     */
    public static byte[] hex2ascii(byte[] bs) {
        byte[] res = new byte[bs.length * 2];
        for (int i = 0; i < bs.length; i++) {
            int ti = bs[i];
            ti = ti < 0 ? ti + 256 : ti;
            String t = Integer.toHexString(ti);
            if (t.length() < 2) t = "0" + t;
            res[i * 2] = (byte) t.charAt(0);
            res[i * 2 + 1] = (byte) t.charAt(1);
        }
        return res;
    }
    
    public static void main(String []args){
    	//System.out.println(Integer.toHexString(29999));
    	//System.out.println(Integer.toString(15, 2));
    }
    
}
