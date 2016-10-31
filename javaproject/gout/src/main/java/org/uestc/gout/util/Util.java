package org.uestc.gout.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.HexUtils;

import com.alibaba.fastjson.JSON;

/**
 * 一些常用方法
 * <p>
 * 可以不用
 * 
 * @author suntao
 *
 */
public class Util {

	/**
	 * 加密算法
	 * <p>
	 * 可能会在以后更改<br>
	 * 所以需要加密/验证的地方都需要使用此方法
	 * 
	 * @param str
	 * @return
	 */
	public static String strEncrypt(String str) {
		String result = null;
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			byte[] digested = mDigest.digest(str.getBytes());
			result = HexUtils.toHexString(digested);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 产生一个UUID
	 * 
	 * @return
	 */
	public static String generateUUID() {
		String result = null;
		result = UUID.randomUUID().toString().replaceAll("-", "");
		return result;

	}

	/**
	 * 从request获取jsondata字符串
	 * 
	 * @param request
	 * @return
	 */
	public static String fromRequestReadJsondataStr(HttpServletRequest request) {
		return request.getParameter("jsondata");
	}

	/**
	 * 从request获取jsondata字符串定义的对象
	 * 
	 * @param request
	 * @param objclass
	 * @return
	 */
	public static <T> T fromRequestReadJsondataObject(HttpServletRequest request, Class<T> objclass) {
		T result = null;
		String jsondataStr = fromRequestReadJsondataStr(request);
		if (jsondataStr != null) {
			result = JSON.parseObject(jsondataStr, objclass);
		}
		return result;
	}

	public static String beautyServiceResultStr(SERVICE_RESULT value) {
		return value.toString().toLowerCase().replaceAll("_", " ");
	}

}
