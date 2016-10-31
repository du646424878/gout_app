package org.uestc.gout.model;

public class Page {
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getOrdercol() {
		return ordercol;
	}
	public void setOrdercol(String ordercol) {
		this.ordercol = ordercol;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	int start;
	int end;
	String ordercol;
	String order;
	String keyword;

}
