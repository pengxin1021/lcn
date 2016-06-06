package com.lcn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lcn.controller.support.WebControllerSupport;
import com.lcn.entity.ShareResource;
import com.lcn.entity.User;
import com.lcn.service.CrawlerService;
import com.lcn.service.ResourceService;
import com.lcn.service.UserService;
import com.lcn.util.LuceneUtil;

@Controller
@RequestMapping("/jsp")
public class WebController extends WebControllerSupport{
	
	@Autowired
	UserService userService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	CrawlerService crawlerService;
	
	@RequestMapping(value="/index")
	public ModelAndView newest(HttpServletRequest request, Model model, String searchkey, int sort){
		List<ShareResource> shareResourceList = LuceneUtil.search("resourceName", sort, searchkey, 1);
		for(ShareResource shareResource : shareResourceList){
			ShareResource resource = resourceService.selectByPrimaryKey(shareResource.getResourceId());
			shareResource.setResourceSort(resource.getResourceSort());
			shareResource.setCreateTime(resource.getCreateTime());
			User user = userService.selectByPrimaryKey(resource.getUserId());
			shareResource.setUser(user);
		}
		ModelAndView mView = new ModelAndView();
		mView.addObject("shareResourceList", shareResourceList);
		if (shareResourceList.size() > 0 && shareResourceList.get(0) != null) {
			sort = shareResourceList.get(0).getResourceSort();
		}else{
			sort = 0;
		}
		mView.addObject("sort", sort);
		mView.setViewName("/result");
		return mView;
	}
	
	@RequestMapping(value="/home")
	public ModelAndView getNew(HttpServletRequest request){
		ShareResource resource = new ShareResource();
		int sort = 0;
		if (!StringUtils.isEmpty(request.getParameter("sort"))) {
			sort = Integer.parseInt(request.getParameter("sort"));
			resource.setResourceSort(sort);
		}
		
		List<ShareResource> newList = resourceService.selectNew(resource);
		for(ShareResource shareResource : newList){
			User user = userService.selectByPrimaryKey(shareResource.getUserId());
			shareResource.setUser(user);
		}
		ModelAndView mView = new ModelAndView();
		mView.addObject("newList", newList);
		mView.addObject("sort", sort);
		mView.setViewName("/index");
		return mView;
	}
	
	@RequestMapping(value="/baidu")
	public ModelAndView baidu(HttpServletRequest request, Model model){
		crawlerService.crawlerBaiduyun();
		ModelAndView mView = new ModelAndView();
		mView.setViewName("/baidu");
		return mView;
	}
}
