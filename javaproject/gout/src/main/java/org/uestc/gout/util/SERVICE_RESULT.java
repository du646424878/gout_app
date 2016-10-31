package org.uestc.gout.util;

/**
 * service层返回的结果
 * <p>
 * 成功就是success<br>
 * 失败对应其他的枚举型,没有failed或者false
 * 
 * @author suntao
 *
 */
public enum SERVICE_RESULT {

	/**
	 * 未授权
	 */
	NO_AUTH,
	/**
	 * 密码错误
	 */
	PASSWORD_ERROR,
	/**
	 * 没有这个用户
	 */
	NO_SUCH_USER,
	/**
	 * 参数错误
	 */
	PARAM_ERROR,
	/**
	 * 成功
	 */
	SUCCESS,
	/**
	 * 待加入的用户已经存在
	 */
	BEING_ADDED_USER_HAS_EXISTED,
	/**
	 * 未预料的错误
	 */
	UNEXPTED_ERROR

}
