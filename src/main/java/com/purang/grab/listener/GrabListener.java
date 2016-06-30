package com.purang.grab.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.purang.grab.pageprocessor.PageProcessorChinaMoney;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.SimplePageProcessor;

public class GrabListener implements ServletContextListener  {

	public GrabListener() {
		
	}
	public void contextInitialized(ServletContextEvent arg0) {
//		Spider spider = Spider.create(new SimplePageProcessor("http://www.oschina.net/", "http://www.oschina.net/*")).addPipeline(new Pipeline() {
//            public void process(ResultItems resultItems, Task task) {
//                //System.out.println(resultItems.get("title"));
//            }
//        }).thread(1);
//        spider.start();
		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn")).addUrl("http://www.chinamoney.com.cn").addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(resultItems.get("title"));
            }
        }).thread(1);
        spider2.start();

	}
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}
}
