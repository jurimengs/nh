package com.org.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class SHA1Util {
	private static final boolean hexcase = false;  
    private static final int chrsz = 8; 
	
    //test
    public static void main(String[] args) {
    	System.out.println(UUID.randomUUID());
		//String a = "9mlqybxpxrsnq5hx9ne7a6wh81y7cntnxedqgvj28fczc92w1e3d7eyk9mnt1lju6ddw4jf8rzcu4johl9yg4cmhigvvercyqmvonnwecdktkuu8191ndok";
		String a = "{\"mid\":\"1111111111\",\"shortName\":\"商户合作\",\"checkInOrg\":\"0001\",\"productId\":\"200010\",\"orderAmt\":\"000000000001\",\"currency\":\"156\",\"authCode\":\"335990071020023033\",\"goodsContent\":\"\",\"operType\":\"1\",\"operId\":\"\",\"attach\":\"\"}";
    	String b = hex_sha1(a);
		System.out.println("b:::"+b);
       /* String digest = new SHA1Util().getDigestOfString(a.getBytes());
        System.out.println("digest:::"+digest);*/
		for (int i = 0; i < 15; i++) {
			System.out.print("'" + i + "',");
		}
	}
    
    /**
     * 方法三：
     * @param byteArray
     * @return
     */
    public static String sha1 (String aim) {
    	String cipher = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte [] digest = md.digest(aim.getBytes());
			cipher = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return cipher;
    }
    
    public static String byteToStr(byte[] byteArray){
    	String str = "";
    	for (int i = 0; i < byteArray.length; i++) {
			str += byteToHexStr(byteArray[i]);
		} 
    	return str;
    }
    
    public static String byteToHexStr (byte b) {
    	char[] digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    	char[] temp = new char[2];
    	temp[0] = digit[(b >>> 4) & 0X0F];
    	temp[1] = digit[b & 0X0F];
    	return new String(temp);
    }
    
    
    //end
	 
    //方法一：
    // 得到字符串SHA-1值的方法  SHA加密
    public static String hex_sha1(String s) {  
        s = (s == null) ? "" : s;  
        return binb2hex(core_sha1(str2binb(s), s.length() * chrsz));  
    }  
 
    private static String binb2hex(int[] binarray) {  
        String hex_tab = hexcase ? "0123456789abcdef" : "0123456789abcdef";  
        String str = "";  
 
        for (int i = 0; i < binarray.length * 4; i++) {  
            char a = (char) hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xf);  
            char b = (char) hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xf);  
            str += (new Character(a).toString() + new Character(b).toString());  
        }  
        return str;  
    }  
 
    private static String binb2str(int[] bin) {  
        String str = "";  
        int mask = (1 << chrsz) - 1;  
 
        for (int i = 0; i < bin.length * 32; i += chrsz) {  
            str += (char) ((bin[i >> 5] >>> (24 - i % 32)) & mask);  
        }  
        return str;  
    }  
 
    private static int[] core_sha1(int[] x, int len) {  
        int size = (len >> 5);  
        x = strechbinarray(x, size);  
        x[len >> 5] |= 0x80 << (24 - len % 32);  
        size = ((len + 64 >> 9) << 4) + 15;  
        x = strechbinarray(x, size);  
        x[((len + 64 >> 9) << 4) + 15] = len;  
 
        int[] w = new int[80];  
        int a = 1732584193;  
        int b = -271733879;  
        int c = -1732584194;  
        int d = 271733878;  
        int e = -1009589776;  
 
        for (int i = 0; i < x.length; i += 16) {  
            int olda = a;  
            int oldb = b;  
            int oldc = c;  
            int oldd = d;  
            int olde = e;  
 
            for (int j = 0; j < 80; j++) {  
                if (j < 16) {  
                    w[j] = x[i + j];  
                } else {  
                    w[j] = rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);  
                }  
 
                int t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)), safe_add(safe_add(e, w[j]), sha1_kt(j)));  
 
                e = d;  
                d = c;  
                c = rol(b, 30);  
                b = a;  
                a = t;  
            }  
 
            a = safe_add(a, olda);  
            b = safe_add(b, oldb);  
            c = safe_add(c, oldc);  
            d = safe_add(d, oldd);  
            e = safe_add(e, olde);  
        }  
 
        int[] retval = new int[5];  
 
        retval[0] = a;  
        retval[1] = b;  
        retval[2] = c;  
        retval[3] = d;  
        retval[4] = e;  
 
        return retval;  
    }  
 
    private static int rol(int num, int cnt) {  
        return (num << cnt) | (num >>> (32 - cnt));  
    }  
 
    private static int safe_add(int x, int y) {  
        int lsw = (int) (x & 0xffff) + (int) (y & 0xffff);  
        int msw = (x >> 16) + (y >> 16) + (lsw >> 16);  
 
        return (msw << 16) | (lsw & 0xffff);  
    }  
 
    private static int sha1_ft(int t, int b, int c, int d) {  
        if (t < 20)  
            return (b & c) | ((~b) & d);  
 
        if (t < 40)  
            return b ^ c ^ d;  
 
        if (t < 60)  
            return (b & c) | (b & d) | (c & d);  
 
        return b ^ c ^ d;  
    }  
 
    private static int sha1_kt(int t) {  
        return (t < 20) ? 1518500249 : (t < 40) ? 1859775393 : (t < 60) ? -1894007588 : -899497514;  
    }  
 
    public static String str_sha1(String s) {  
        s = (s == null) ? "" : s;  
 
        return binb2str(core_sha1(str2binb(s), s.length() * chrsz));  
    }  
 
    private static int[] str2binb(String str) {  
        str = (str == null) ? "" : str;  
 
        int[] tmp = new int[str.length() * chrsz];  
        int mask = (1 << chrsz) - 1;  
 
        for (int i = 0; i < str.length() * chrsz; i += chrsz) {  
            tmp[i >> 5] |= ((int) (str.charAt(i / chrsz)) & mask) << (24 - i % 32);  
        }  
 
        int len = 0;  
        for (int i = 0; i < tmp.length && tmp[i] != 0; i++, len++)  
            ;  
 
        int[] bin = new int[len];  
 
        for (int i = 0; i < len; i++) {  
            bin[i] = tmp[i];  
        }  
 
        return bin;
    }  
 
    private static int[] strechbinarray(int[] oldbin, int size) {  
        int currlen = oldbin.length;  
 
        if (currlen >= size + 1) {  
            return oldbin;  
        }  
 
        int[] newbin = new int[size + 1];  
        for (int i = 0; i < size; newbin[i] = 0, i++)  
            ;  
 
        for (int i = 0; i < currlen; i++) {  
            newbin[i] = oldbin[i];  
        }  
 
        return newbin;  
    }
    
    
    //*******************************************************
    //方法二：
    private final int[] abcde = {
            0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0
        };
    // 摘要数据存储数组
    private int[] digestInt = new int[5];
    // 计算过程中的临时数据存储数组
    private int[] tmpData = new int[80];
    // 计算sha-1摘要
    private int process_input_bytes(byte[] bytedata)
    {
        // 初试化常量
        System.arraycopy(abcde, 0, digestInt, 0, abcde.length);
        // 格式化输入字节数组，补10及长度数据
        byte[] newbyte = byteArrayFormatData(bytedata);
        // 获取数据摘要计算的数据单元个数
        int MCount = newbyte.length / 64;
        // 循环对每个数据单元进行摘要计算
        for (int pos = 0; pos < MCount; pos++)
        {
            // 将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中
            for (int j = 0; j < 16; j++)
            {
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
            }
            // 摘要计算函数
            encrypt();
  }
        return 20;
    }
    // 格式化输入字节数组格式
    private byte[] byteArrayFormatData(byte[] bytedata)
    {
        // 补0数量
        int zeros = 0;
        // 补位后总位数
        int size = 0;
        // 原始数据长度
        int n = bytedata.length;
        // 模64后的剩余位数
        int m = n % 64;
        // 计算添加0的个数以及添加10后的总长度
        if (m < 56)
        {
            zeros = 55 - m;
            size = n - m + 64;
        }
        else if (m == 56)
        {
            zeros = 63;
            size = n + 8 + 64;
        }
        else
        {
            zeros = 63 - m + 56;
            size = (n + 64) - m + 64;
        }
        // 补位后生成的新数组内容
        byte[] newbyte = new byte[size];
        // 复制数组的前面部分
        System.arraycopy(bytedata, 0, newbyte, 0, n);
        // 获得数组Append数据元素的位置
        int l = n;
        // 补1操作
        newbyte[l++] = (byte) 0x80;
        // 补0操作
        for (int i = 0; i < zeros; i++)
        {
            newbyte[l++] = (byte) 0x00;
        }
        // 计算数据长度，补数据长度位共8字节，长整型
        long N = (long) n * 8;
        byte h8 = (byte) (N & 0xFF);
        byte h7 = (byte) ((N >> 8) & 0xFF);
        byte h6 = (byte) ((N >> 16) & 0xFF);
        byte h5 = (byte) ((N >> 24) & 0xFF);
        byte h4 = (byte) ((N >> 32) & 0xFF);
        byte h3 = (byte) ((N >> 40) & 0xFF);
        byte h2 = (byte) ((N >> 48) & 0xFF);
        byte h1 = (byte) (N >> 56);
        newbyte[l++] = h1;
        newbyte[l++] = h2;
        newbyte[l++] = h3;
        newbyte[l++] = h4;
        newbyte[l++] = h5;
        newbyte[l++] = h6;
        newbyte[l++] = h7;
        newbyte[l++] = h8;
        return newbyte;
    }
    private int f1(int x, int y, int z)
    {
        return (x & y) | (~x & z);
    }
    private int f2(int x, int y, int z)
    {
        return x ^ y ^ z;
    }
    private int f3(int x, int y, int z)
    {
        return (x & y) | (x & z) | (y & z);
    }
    private int f4(int x, int y)
    {
        return (x << y) | x >>> (32 - y);
    }
   
    // 单元摘要计算函数
    private void encrypt()
    {
        for (int i = 16; i <= 79; i++)
        {
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14] ^
                    tmpData[i - 16], 1);
        }
        int[] tmpabcde = new int[5];
        for (int i1 = 0; i1 < tmpabcde.length; i1++)
        {
            tmpabcde[i1] = digestInt[i1];
        }
        for (int j = 0; j <= 19; j++)
        {
            int tmp = f4(tmpabcde[0], 5) +
                f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                tmpData[j] + 0x5a827999;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int k = 20; k <= 39; k++)
        {
            int tmp = f4(tmpabcde[0], 5) +
                f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                tmpData[k] + 0x6ed9eba1;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int l = 40; l <= 59; l++)
        {
            int tmp = f4(tmpabcde[0], 5) +
                f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                tmpData[l] + 0x8f1bbcdc;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int m = 60; m <= 79; m++)
        {
            int tmp = f4(tmpabcde[0], 5) +
                f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] +
                tmpData[m] + 0xca62c1d6;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int i2 = 0; i2 < tmpabcde.length; i2++)
        {
            digestInt[i2] = digestInt[i2] + tmpabcde[i2];
        }
        for (int n = 0; n < tmpData.length; n++)
        {
            tmpData[n] = 0;
        }
    }
   
    // 4字节数组转换为整数
    private int byteArrayToInt(byte[] bytedata, int i)
    {
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16) |
        ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
    }
    // 整数转换为4字节数组
    private void intToByteArray(int intValue, byte[] byteData, int i)
    {
        byteData[i] = (byte) (intValue >>> 24);
        byteData[i + 1] = (byte) (intValue >>> 16);
        byteData[i + 2] = (byte) (intValue >>> 8);
        byteData[i + 3] = (byte) intValue;
    }
    // 将字节转换为十六进制字符串
    private static String byteToHexString(byte ib)
    {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                'd', 'e', 'f'
            };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
    // 将字节数组转换为十六进制字符串
    private static String byteArrayToHexString(byte[] bytearray)
    {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++)
        {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest;
    }
    // 计算sha-1摘要，返回相应的字节数组
    public byte[] getDigestOfBytes(byte[] byteData)
    {
        process_input_bytes(byteData);
        byte[] digest = new byte[20];
        for (int i = 0; i < digestInt.length; i++)
        {
            intToByteArray(digestInt[i], digest, i * 4);
        }
        return digest;
    }
    // 计算sha-1摘要，返回相应的十六进制字符串
    public String getDigestOfString(byte[] byteData)
    {
        return byteArrayToHexString(getDigestOfBytes(byteData));
    }
    
}
