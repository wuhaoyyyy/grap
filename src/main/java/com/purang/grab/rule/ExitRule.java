package com.purang.grab.rule;

import com.purang.grab.util.CommonUtils;

import us.codecraft.webmagic.selector.Html;

public abstract class ExitRule {
	
	protected Html html;
	protected String name;
	protected String rule;
	protected String ruleType;
	protected String ruleResult;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public Html getHtml() {
		return html;
	}
	public void setHtml(Html html) {
		this.html = html;
	}

	public String getRuleResult() {
		return CommonUtils.getSelectorResult(this.html, this.rule, this.ruleType);
	}
	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}
	
	//得到该rule的结果，然后判断是否退出
	public Boolean getExit(){
		if(this.html.css(rule)!=null){
			return false;
		}
		else{
			return true;
		}
	}
	

}
