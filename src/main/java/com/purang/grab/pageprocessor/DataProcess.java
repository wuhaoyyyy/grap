package com.purang.grab.pageprocessor;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import com.purang.grab.rule.GrabRule;

public class DataProcess implements PageProcessor {
	
	private String url;
	private String sql;
	private List<GrabRule> ruleList=new ArrayList<GrabRule>();
	private List<GrabRule> exitList=new ArrayList<GrabRule>();
    private Site site;
	
    public DataProcess() {
        this.site = Site.me().setSleepTime(10000).setCharset("UTF-8").setUseGzip(false).addHeader("Accept-Encoding", "deflate").setTimeOut(50000);
	}
    
	@Override
	public Site getSite() {
		return this.site;
	}
	
	@Override
	public void process(Page page) {
		//page.addTargetRequest(requestString);
//		if(page.getHtml().css("[title=\"下一页\"]")!=null){
//			page.addTargetRequests(page.getHtml().css("[title=\"下一页\"]").links().all());
//		}
		
		Html html=new Html(page.getRawText());
		//href为javascript的
		
		
		
		for(GrabRule grabRule:exitList){
			if(page.getHtml().css(grabRule.getValue())!=null){
				page.addTargetRequests(page.getHtml().css(grabRule.getValue()).links().all());
			}
		}
		for(GrabRule grabRule:ruleList){
			page.putField(grabRule.getName() , page.getHtml().xpath(grabRule.getValue()).all());
		}
	}

	public List<GrabRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<GrabRule> ruleList) {
		this.ruleList = ruleList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<GrabRule> getExitList() {
		return exitList;
	}

	public void setExitList(List<GrabRule> exitList) {
		this.exitList = exitList;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	

}
