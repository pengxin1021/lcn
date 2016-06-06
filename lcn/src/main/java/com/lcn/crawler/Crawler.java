package com.lcn.crawler;

import java.util.HashMap;
import java.util.Map;

import com.lcn.controller.support.WebControllerSupport;

public class Crawler extends WebControllerSupport{
	
//	private String cookie = "BAIDUID=5B49063EAE6394609773E3926DB0D617:FG=1; BIDUPSID=5B49063EAE6394609773E3926DB0D617; PSTM=1461158460; BDUSS=01SMC1NdG9mNXlYNllXeDM1S3hKNW1uUUx4dDFJZWlXekpmSkNnd3hjbE5EejlYQVFBQUFBJCQAAAAAAAAAAAEAAAD-aD0wRnBoaW4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAE2CF1dNghdXb; PANWEB=1; Hm_lvt_1d15eaebea50a900b7ddf4fa8d05c8a0=1461341090,1461341947,1461341960,1461763032; Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47=1461341090,1461341947,1461341960,1461763033; bdshare_firstime=1461332974373; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1461332974; cflag=3%3A3; H_PS_PSSID=19638_1436_19781_19803_19805_19807_19842_17001_14983_11933_19297_19809; Hm_lpvt_1d15eaebea50a900b7ddf4fa8d05c8a0=1461763032; Hm_lpvt_f5f83a6d8b15775a02760dc5f490bc47=1461763033";
	
	public final Map<String, String> getHeaders(String host, String cookie) {
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
