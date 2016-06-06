package com.lcn.entity;

import java.io.Serializable;

public class BaseParam implements Serializable{

	private static final long serialVersionUID = -7341577973969310725L;

	private int page = 1;	//页数
	
	private int pageSize = 10; 	//条数
	
	private String orders;	//排序: time.asc,id.desc
	
	private boolean autoCount = false;	//是否计算总条数

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
	
	
}
