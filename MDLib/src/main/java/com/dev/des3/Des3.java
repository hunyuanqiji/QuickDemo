package com.dev.des3;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


/***
 * 
 * 
 * 3DES加密工具类
 * 
 * 
 * base64   16进制数据
 * 
 * 
 * 
 * @author Lyy
 * 
 * @date 2018-04-24
 */
public class Des3 {
	// 密钥 长度不得小于24  YmVpamluZ2thbmd3ZWlqaW5xaWFva2Vq
	private  final static String secretKey = "beijingkangweijinqiaokej";
	// 向量
	private final static String iv = "01234567";

	// 加解密统一使用的编码方式
	private final static String encoding = "UTF-8";
    //运算法则
	private static final String DES_ALGORITHM = "desede/CBC/PKCS5Padding";








	/**
	 * 3DES加密
	 * 
	 * @param plainText
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encode(String plainText) throws Exception {
		if(IsEmpty(secretKey)){
			return "";
		}
		Key deskey = generateKey(secretKey);
		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);

		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));

		return byte2hex(encryptData);
		

	}

	/**
	 * 3DES解密
	 *
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText) throws Exception {
		if(IsEmpty(secretKey)){
			return "";
		}
		Key deskey = generateKey(secretKey);
		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(hex2byte(encryptText));
		return new String(decryptData, encoding);
	}

	/**
	 * 3DES加密
	 *
	 * @param plainText
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64(String plainText) throws Exception {
		if(IsEmpty(secretKey)){
			return "";
		}
		Key deskey = generateKey(secretKey);

		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Base64.encode(encryptData).replace(" ", "");
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decodeBase64(String encryptText) throws Exception {
		if(IsEmpty(secretKey)){
			return "";
		}
		Key deskey = generateKey(secretKey);

		Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
		return new String(decryptData, encoding);
	}


	/**
	 * 获得秘密密钥
	 *
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 */
	private static Key generateKey(String secretKey)  throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {

		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		return keyfactory.generateSecret(spec);




	}



	/***
	 * 二进制转字符串 
	 * 16进制
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString();
	}

	/***
	 * 
	 * 二进制转字符串
	 * 
	 * @param
	 * @return
	 */
	public static byte[] hex2byte(String str) { // 字符串转二进制
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	public static String codeNum(String code) {
		code.toCharArray();
		System.out.print(Character.MAX_VALUE);
		return code;

	}
	
	/** 判断字符串是否为空 */
	public static boolean IsEmpty(final String object) {
		if ((object == null) || (object.length() <= 0) || object.equals("null")) {
			return true;
		}
		return false;
	}

	
}
