package com.lcn.dao;

import java.util.List;

import com.lcn.entity.BaseEntity;
import com.lcn.entity.BaseParam;

public interface BaseDao<T, PK> {
	
	T selectByPrimaryKey(PK id);
	
	int insertSelective(BaseEntity entity);
	
	int updateByPrimaryKeySelective(BaseEntity entity);
	
	List<T> search(BaseParam param);
	
	int deleteByPrimaryKey(PK id);

	int deleteByPrimaryKeyReal(PK id);
}
