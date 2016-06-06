package com.lcn.dao;

import java.util.List;

import com.lcn.entity.ShareResource;

public interface ResourceDao extends BaseDao<ShareResource, Long>{
	public List<ShareResource> selectNew(ShareResource resource);
}