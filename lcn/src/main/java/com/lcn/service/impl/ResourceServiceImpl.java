package com.lcn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcn.dao.ResourceDao;
import com.lcn.entity.ShareResource;
import com.lcn.service.ResourceService;
@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceDao resourceDao;
	
	@Override
	public Long insertResource(ShareResource resource) {
		resourceDao.insertSelective(resource);
		return (Long) resource.getPrimaryKey();
	}

	@Override
	public void updateResource(ShareResource resource) {
		resourceDao.updateByPrimaryKeySelective(resource);
	}

	@Override
	public List<ShareResource> search(ShareResource resource) {
		return resourceDao.search(resource);
	}

	@Override
	public ShareResource selectByPrimaryKey(Long resourceId) {
		return resourceDao.selectByPrimaryKey(resourceId);
	}

	@Override
	public List<ShareResource> selectNew(ShareResource resource) {
		return resourceDao.selectNew(resource);
	}

}
