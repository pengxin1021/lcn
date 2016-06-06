package com.lcn.entity;

@SuppressWarnings("serial")
public abstract class BaseEntity extends BaseParam{
	
	/**
	 * 获取对象主键
	 * @return id
	 */
	public abstract Object getPrimaryKey();
}
