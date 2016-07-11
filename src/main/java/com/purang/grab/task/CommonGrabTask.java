package com.purang.grab.task;

import java.util.Map;

import us.codecraft.webmagic.Request;

public class CommonGrabTask implements GrabTask{

	private String url;
    private Map<String, Object> extras;
	private Request request;
    
	public CommonGrabTask() {
		
	}
	public CommonGrabTask(String url,Map<String, Object> extras) {
		this.request=new Request(url);
		this.request.setExtras(extras);
	}
	
}
