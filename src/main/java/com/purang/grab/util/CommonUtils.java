package com.purang.grab.util;

import us.codecraft.webmagic.selector.Html;

public class CommonUtils {
	
	public static String getSelectorResult(Html html,String rule,String ruleType){
		String text="";
		switch(ruleType){
			case "css":
				text=html.css(rule).toString();
				break;
			case "xpath":
				text=html.xpath(rule).toString();
				break;
		}
		return text;
		
	}

}
