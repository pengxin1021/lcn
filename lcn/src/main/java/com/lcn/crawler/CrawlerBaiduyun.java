package com.lcn.crawler;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcn.entity.ShareResource;
import com.lcn.entity.User;
import com.lcn.service.ResourceService;
import com.lcn.service.UserService;
import com.lcn.util.HttpRestUtils;
import com.lcn.util.HttpRestUtils.HttpResult;
import com.lcn.util.LuceneUtil;

public class CrawlerBaiduyun extends Crawler{
	
	private Logger log = Logger.getLogger(getClass());
	
	@Autowired
	ResourceService resourceService;
	@Autowired
	UserService userService;
	
	private String url = "http://yun.baidu.com/pcloud/feed/getdynamiclist?auth_type=1&filter_types=11000&query_uk=1748283618&category=0&limit=50&start=0&bdstoken=28cd36954ca3c712c71424d803642a9a&channel=chunlei&clienttype=0&web=1";
	
	public static void main(String[] args) {
		CrawlerBaiduyun cb = new CrawlerBaiduyun();
		cb.getResource();
	}
	
	public void getResource(){
		try {
			HttpResult result = HttpRestUtils.fetchGetURLContent(url, getHeaders("yun.baidu.com", CrawlerConstants.COOKIE_BAIDUHOME), "UTF-8", "UTF-8", false, null);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shareHome(String uk, String feedTime){
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
						
						String userHomeUrl = "http://yun.baidu.com/share/home?uk=" + uk;
						log.info("userHomeUrl=====" + userHomeUrl);
						log.info("resourceName=====" + resourceName);
						log.info("userName=====" + userName);
						
						User user = new User();
						user.setUserName(userName);
						user.setUserHomeUrl(userHomeUrl);
						List<User> userList = userService.search(user);
						Long userId = null;
						if (userList.size() == 0) {
							userId = userService.insertUser(user);
						}else{
							userId = userList.get(0).getUserId();
						}
						
						ShareResource shareResource = new ShareResource();
						shareResource.setResourceName(resourceName);
						shareResource.setResourceUrl(resourceUrl);
						shareResource.setUserId(userId);
						List<ShareResource> shareResourceList = resourceService.search(shareResource);
						if (shareResourceList.size() == 0) {
							resourceService.insertResource(shareResource);
							LuceneUtil.creatIndex(shareResource);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
