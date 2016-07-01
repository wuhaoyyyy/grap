package com.purang.grab.pageprocessor;

import java.util.List;

import com.google.common.collect.Lists;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

public class PageProcessorChinaMoney implements PageProcessor{

	private String startUrl;
    private Site site;
    
	public PageProcessorChinaMoney(String startUrl) {
		this.startUrl=startUrl;
        this.site = Site.me().setSleepTime(1000);
	}

	@Override
	public void process(Page page) {
        //List<String> requests = page.getHtml().links().regex(urlPattern).all();
		List<String> requests =Lists.newArrayList();
		requests.add(startUrl);
        page.addTargetRequests(requests);
		//债券发行与上市
        page.putField("title", page.getHtml().xpath("//*[@id=\"nav\"]/ul/li[2]/ul/li[4]/ul/li[1]/a"));
        page.putField("html", page.getHtml().toString());
        page.putField("content", page.getHtml().smartContent());
	}
	
	@Override
	public Site getSite() {
		return this.site;
	}

}
