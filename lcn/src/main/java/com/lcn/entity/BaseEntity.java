package com.lcn.entity;

@SuppressWarnings("serial")
public abstract class BaseEntity extends BaseParam{
	
	/**
	 * ��ȡ��������
	 * @return id
	 */
	public abstract Object getPrimaryKey();
}
