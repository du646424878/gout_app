package org.uestc.gout.model;

/**
 * 客户端和服务端传递消息使用
 * 
 * @author suntao
 *
 * @param <T>
 */
public class ServerMessage<T> {
	public ServerMessage() {
		success = false;
		type = "err message";

	}

	public ServerMessage(T message, Boolean success, String type) {
		this();
		this.message = message;
		this.success = success;
		this.type = type;
	}

	/**
	 * 泛型对象
	 * <p>
	 * 可以为<br>
	 * Boolean<br>
	 * String<br>
	 * 自定义实体,例如User<br>
	 * List<自定义实体>
	 */
	T message;

	Boolean success;

	String type;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

};