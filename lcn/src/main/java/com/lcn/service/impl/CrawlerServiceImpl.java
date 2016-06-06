package com.lcn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcn.crawler.CrawlerConstants;
import com.lcn.dao.ResourceDao;
import com.lcn.dao.UserDao;
import com.lcn.entity.ShareResource;
import com.lcn.entity.User;
import com.lcn.service.CrawlerService;
import com.lcn.util.HttpRestUtils;
import com.lcn.util.HttpRestUtils.HttpResult;
import com.lcn.util.LuceneUtil;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	
	private Logger log = Logger.getLogger(getClass());
	private String url = "http://yun.baidu.com/pcloud/feed/getdynamiclist?auth_type=1&filter_types=11000&query_uk=1748283618&category=0&limit=50&start=0&bdstoken=37684bcda619e63e0d75121fdd1df402&channel=chunlei&clienttype=0&web=";
	@Autowired
	ResourceDao resourceDao;
	@Autowired
	UserDao userDao;

	@Override
	public void crawlerBaiduyun() {
		int page = 1;
		boolean flag = true;
		while(flag){
			flag = getResource(url + page);
			page ++;
//			shareHome("1748283618", "1463842550314");
		}
		System.out.println("========================爬取完成");
	}
	
	private boolean getResource(String indexUrl){
		try {
			HttpResult result = HttpRestUtils.fetchGetURLContent(indexUrl, getHeaders("yun.baidu.com", CrawlerConstants.COOKIE_BAIDUHOME), "UTF-8", "UTF-8", false, null);
			if(result.getStatusCode() == 200){
				String source = result.getResultString();
				Document doc = Jsoup.parse(source);
				Elements elements = doc.select("body");
				String bodyString = elements.toString();
				if (bodyString.contains("[{")) {
					String jsonString = bodyString.substring(bodyString.indexOf("[{"), bodyString.lastIndexOf("}]") + 2);
					JSONArray jsonArray = new JSONArray(jsonString);
					for(Object object : jsonArray){
						JSONObject jsonObject = (JSONObject) object;
						Object uk = jsonObject.get("uk");
						Object feedTime = jsonObject.get("feed_time");
						shareHome(uk.toString(), feedTime.toString());
					}
				}
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void shareHome(String uk, String feedTime){
		String shareHomeUrl = "http://yun.baidu.com/pcloud/feed/getsharelist?t=" +feedTime + "&category=0&auth_type=1&request_location=share_home&start=0&limit=60&query_uk=" + uk + "&channel=chunlei&clienttype=0&web=1&bdstoken=28cd36954ca3c712c71424d803642a9a";
		log.info("shareHomeUrl=====" + shareHomeUrl);
		try {
			HttpResult result = HttpRestUtils.fetchGetURLContent(shareHomeUrl, getHeaders("yun.baidu.com", CrawlerConstants.COOKIE_SHAREHOME), "UTF-8", "UTF-8", false, null);
			if (result.getStatusCode() == 200) {
				String source = result.getResultString();
				Document doc = Jsoup.parse(source);
				System.out.println(doc);
				Elements elements = doc.select("body");
				String bodyString = elements.toString();
				if (bodyString.contains("[{")) {
					String jsonString = bodyString.substring(bodyString.indexOf("[{"), bodyString.lastIndexOf("}]") + 2);
					JSONArray jsonArray = new JSONArray(jsonString);
					for(Object object : jsonArray){
						JSONObject jsonObject = (JSONObject) object;
						String resourceName = jsonObject.getString("title");
						String resourceUrl = "http://yun.baidu.com/s/" + jsonObject.getString("shorturl");
						String userName = jsonObject.getString("username");
						int category = jsonObject.getInt("category");
						
						String userHomeUrl = "http://yun.baidu.com/share/home?uk=" + uk;
						log.info("userHomeUrl=====" + userHomeUrl);
						log.info("resourceName=====" + resourceName);
						log.info("userName=====" + userName);
						
						User user = new User();
						user.setUserName(userName);
						user.setUserHomeUrl(userHomeUrl);
						List<User> userList = userDao.search(user);
						Long userId = null;
						if (userList.size() == 0) {
							userDao.insertSelective(user);
							userId = (Long) user.getPrimaryKey();
//							LuceneUtilBeifen.creatIndex("userName", userName);
						}else{
							userId = userList.get(0).getUserId();
						}
						
						ShareResource shareResource = new ShareResource();
						shareResource.setResourceName(resourceName);
						shareResource.setResourceUrl(resourceUrl);
						shareResource.setUserId(userId);
//						shareResource.setResourceSort(getResourceSort(category));
						shareResource.setResourceSort(category);
						List<ShareResource> shareResourceList = resourceDao.search(shareResource);
						if (shareResourceList.size() == 0) {
							resourceDao.insertSelective(shareResource);
							LuceneUtil.creatIndex(shareResource);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private int getResourceSort(String category){
		int sort = 0;
		try {
			sort = Integer.parseInt(category);
		} catch (Exception e) {
			sort = 0;
		}
		return sort;
	}
	
	private final Map<String, String> getHeaders(String host, String cookie) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml,application/json, text/javascript, */*; q=0.01");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Connection", "keep-alive");
		headers.put("Host", host);
		headers.put("Referer", "http://yun.baidu.com/");
		headers.put("X-Requested-With", "XMLHttpRequest");
		headers.put("Cookie", cookie);
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:42.0) Gecko/20100101 Firefox/42.0");
		return headers;
	}

}
