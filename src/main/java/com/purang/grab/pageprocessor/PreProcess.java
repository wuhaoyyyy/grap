package com.purang.grab.pageprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

public class PreProcess{

	private List<PageProcessorPreProcess> preprocessList=new ArrayList<PageProcessorPreProcess>();
	
	public List<PageProcessorPreProcess> getPreprocessList() {
		return preprocessList;
	}
	
	public void setPreprocessList(List<PageProcessorPreProcess> preprocessList) {
		this.preprocessList = preprocessList;
	}
	public PreProcess(List<PageProcessorPreProcess> preprocessList) {
		this.preprocessList=preprocessList;
	}
	
	

}
