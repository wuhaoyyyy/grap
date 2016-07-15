package com.purang.grab.rule;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.selector.Html;

public abstract class AbstractRule implements Rule {
	
	protected String title=null;
	protected String name=null;
	protected String type=null;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public String getRuleResult(Html html) {
		return CommonUtils.getSelectorResult(html, this.name, this.type);
	}

	
}
