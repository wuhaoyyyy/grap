package com.purang.grab.url;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.SimplePageProcessor;

public class UrlChinaMoney {
	
	private String mainUrl;
	
	
	
	public void getSubUrl(){
		Spider spider = Spider.create(new SimplePageProcessor("http://www.oschina.net/", "http://www.oschina.net/*")).addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(1);
            }
        }).thread(1);
	}
	
	
	
	

}
