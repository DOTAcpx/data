package com.cn.jm.core.tool.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.cn.jm.core.utils.util.Base64;


/**
 * Android平台DES加解密工具类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class ToolDES {

	/** 密钥算法名称 **/
	private final static String ALGORITHM = "ToolDES";

	/**
	 * 请求转换 的名称：加密/解密算法/工作模式/填充方式
	 **/
	private static final String TRANSFORMATION = "ToolDES/CBC/PKCS5Padding";

	/**
	 * 初始化向量(8位字节数组)
	 */
	private static final byte[] bInitValue = { 5, '#', 1, 'a', 0, '/', 'q', 'j' };

	/**
	 * DES算法，加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException
	 *             异常
	 */
	public static String encrypt(String key, String data) throws Exception {
		return Base64.encode(encrypt(key, data.getBytes("UTF-8")));
	}

	/**
	 * DES算法，加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException
	 *             异常
	 */
	public static byte[] encrypt(String key, byte[] data) throws Exception {
		try {
			// 指定算法名称构造生成SecureRandom实例
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			// 设置密钥参数,key的长度不能够小于8位字节
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			Key secretKey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			IvParameterSpec ips = new IvParameterSpec(bInitValue);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);

			// 执行加密操作
			byte[] bytes = cipher.doFinal(data);
			// Base64编码
			return bytes;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * DES算法，解密
	 * 
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             异常
	 */
	public static byte[] decrypt(String key, byte[] data) throws Exception {
		try {
			// 先Base64解码
			byte[] byteMi = Base64.decode(new String(data));

			// 指定算法名称构造生成SecureRandom实例
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance(ALGORITHM);

			// 设置密钥参数,key的长度不能够小于8位字节
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			Key secretKey = keyFactory.generateSecret(dks);

			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			IvParameterSpec ips = new IvParameterSpec(bInitValue);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);

			// 执行解密操作
			byte[] bytes = cipher.doFinal(byteMi);

			return bytes;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * DES算法，解密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws CryptException
	 *             异常
	 */
	public static String decrypt(String key, String data) throws Exception {
		return new String(decrypt(key, data.getBytes("UTF-8")), "UTF-8");
	}

	public static void main(String[] args) {
		try {

			System.err.println(encrypt("12345678", "abcndeidsandandjnende"));
//			byte[] dd = new String("abcndeidsandandjnende").getBytes();
//			System.err.println(encrypt("12345678", encrypt(dd, "12345678")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("ToolDES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("ToolDES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
