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
        this.site = Site.me().setSleepTime(10000).setCharset("UTF-8").setUseGzip(false).addHeader("Accept-Encoding", "deflate").setTimeOut(5000);
	}

	@Override
	public void process(Page page) {
		List<String> requests =Lists.newArrayList();
		requests.add(startUrl);
        page.addTargetRequests(page.getHtml().xpath("//*[@id=\"nav\"]/ul/li[2]/ul/li[4]/ul/li/a").links().all());
        
        
        page.putField("urls", page.getHtml().xpath("//*[@id=\"nav\"]/ul/li[2]/ul/li[4]/ul/li/a").links().all());
        page.putField("texts", page.getHtml().xpath("//*[@id=\"nav\"]/ul/li[2]/ul/li[4]/ul/li/a/text()").all());
        
        page.putField("types", page.getHtml().xpath("/html/body/table[3]/tbody/tr/td[3]/table/tbody/tr[1]/td/table/tbody/tr/td/@onclick").all());

	}
	
	@Override
	public Site getSite() {
		return this.site;
	}

}
