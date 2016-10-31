package com.du.gout_app.common;

public class Message<T> {
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

}