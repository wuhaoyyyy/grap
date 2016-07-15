package com.purang.grab.util;

import java.util.List;

import us.codecraft.webmagic.selector.Html;

public class CommonUtils {
	
	public static String getSelectorResult(Html html,String name,String type){
		String text="";
		switch(type){
			case "css":
				text=html.css(name).toString();
				break;
			case "xpath":
				text=html.xpath(name).toString();
				break;
		}
		return text;
		
	}
	
	public static List<String> getSelectorListResult(Html html,String name,String type){
		List<String> result=null;
		switch(type){
			case "css":
				result=html.css(name).all();
				break;
			case "xpath":
				result=html.xpath(name).all();
				break;
		}
		return result;

	}
	
	public static String getSelectorLinkResult(Html html,String name,String type){
		String text="";
		switch(type){
			case "css":
				text=html.css(name).links().toString();
				break;
			case "xpath":
				text=html.xpath(name).links().toString();
				break;
		}
		return text;
		
	}
	public static List<String> getSelectorLinkListResult(Html html,String name,String type){
		switch(type){
			case "css":
				return html.css(name).links().all();
			case "xpath":
				return html.xpath(name).links().all();
		}
		return null;
	}

}
