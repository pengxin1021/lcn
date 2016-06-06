package com.lcn.service;

import java.util.List;

import com.lcn.entity.ShareResource;

public interface ResourceService {
	
	public Long insertResource(ShareResource resource);
	
	public ShareResource selectByPrimaryKey(Long resourceId);
	
	public void updateResource(ShareResource resource);
	
	public List<ShareResource> search(ShareResource resource);
	
	public List<ShareResource> selectNew(ShareResource resource);
}
