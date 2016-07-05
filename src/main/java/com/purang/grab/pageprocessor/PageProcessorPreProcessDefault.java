package com.purang.grab.pageprocessor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

public class PageProcessorPreProcessDefault extends PageProcessorPreProcess{

	private String startUrl;
    private Site site; 
	public PageProcessorPreProcessDefault(String startUrl,int sleepTime,String charset,Boolean useGzip,HashMap<String, String> header) {
		super();
		this.startUrl=startUrl;
        this.site = Site.me().setSleepTime(sleepTime).setCharset(charset).setUseGzip(useGzip);
        Iterator<String> iterator = header.keySet().iterator();
        while(iterator.hasNext()){
        	String key=iterator.next();
        	String value=header.get(key);
        	this.site.addHeader(key, value);
        }
	}

	@Override
	public void process(Page page) {
        //List<String> requests = page.getHtml().links().regex(urlPattern).all();
		List<String> requests =Lists.newArrayList();
		requests.add(startUrl);
        page.addTargetRequests(requests);
        //
        page.putField("title", page.getHtml().xpath("//*[@id=\"nav\"]/ul/li[2]/ul/li[4]/ul/li/a"));
        page.putField("html", page.getHtml().toString());
        page.putField("content", page.getHtml().smartContent());

        page.putField("urls", page.getHtml().css("ul.sub02").links());
	}
	
	@Override
	public Site getSite() {
		return this.site;
	}

}
