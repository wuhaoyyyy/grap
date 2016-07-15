package com.purang.grab.rule;

import java.util.List;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.selector.Html;

public class PagerRule extends AbstractRule {
	
	public String getNextPage(Html html){
		return CommonUtils.getSelectorLinkResult(html, name, type);
	}
	public List<String> getNextPageList(Html html){
		return CommonUtils.getSelectorLinkListResult(html, name, type);
	}
}
