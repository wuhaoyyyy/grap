package com.purang.grab.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class PageProcessorPreProcess implements PageProcessor {

	private String selectorType;
	private String selectorText;
	
	
	@Override
	public void process(Page page) {
		
	}

	@Override
	public Site getSite() {
		return null;
	}

}
