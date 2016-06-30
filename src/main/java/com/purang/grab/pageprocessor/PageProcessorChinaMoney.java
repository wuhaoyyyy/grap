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
        this.site = Site.me().setSleepTime(5000).setDomain("www.chinamoney.com.cn");
	}

	@Override
	public void process(Page page) {
        //List<String> requests = page.getHtml().links().regex(urlPattern).all();
		List<String> requests =Lists.newArrayList();
		requests.add(startUrl);
        page.addTargetRequests(requests);
        page.putField("title", page.getHtml().xpath("//title"));
        page.putField("html", page.getHtml().toString());
        page.putField("content", page.getHtml().smartContent());
	}
	
	@Override
	public Site getSite() {
		return this.site;
	}

}
