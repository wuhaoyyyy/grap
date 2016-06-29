package com.purang.grab.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.SimplePageProcessor;

public class GrabListener implements ServletContextListener  {

	public GrabListener() {
		
	}
	public void contextInitialized(ServletContextEvent arg0) {
		Spider spider = Spider.create(new SimplePageProcessor("http://www.oschina.net/", "http://www.oschina.net/*")).addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(1);
            }
        }).thread(1);
        spider.start();
        try {
			Thread.sleep(10000);
	        spider.stop();
	        Thread.sleep(10000);
	        spider.start();
	        Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}
}
