package com.purang.grab.pageprocessor;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.purang.grab.rule.FieldRule;
import com.purang.grab.rule.GrabRule;
import com.purang.grab.rule.PagerRule;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.UrlUtils;
public class DataProcess implements PageProcessor {

    private Site site;
    private Map<String,String> defaultValue=new HashMap<String, String>();

	private List<FieldRule> fieldRuleList=new ArrayList<FieldRule>();
	private PagerRule pageRule=new PagerRule();
	private List<GrabRule> urlList=new ArrayList<GrabRule>();
	
    public DataProcess() {

    }

    
	public Map<String, String> getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(Map<String, String> defaultValue) {
		this.defaultValue = defaultValue;
	}


	public List<FieldRule> getFieldRuleList() {
		return fieldRuleList;
	}


	public void setFieldRuleList(List<FieldRule> fieldRuleList) {
		this.fieldRuleList = fieldRuleList;
	}


	public PagerRule getPageRule() {
		return pageRule;
	}


	public void setPageRule(PagerRule pageRule) {
		this.pageRule = pageRule;
	}

	

	public List<GrabRule> getUrlList() {
		return urlList;
	}


	public void setUrlList(List<GrabRule> urlList) {
		this.urlList = urlList;
	}


	public void setSite(Site site) {
		this.site=site;
	}
	@Override
	public Site getSite() {
		return this.site;
	}
	
	@Override
	public void process(Page page) {
		
		Object level=page.getRequest().getExtra("level");

		if(level==null){
			List<String> nextPage=pageRule.getNextPageList(page.getHtml());
			if(nextPage.equals("")) return;
			else page.addTargetRequests(nextPage);
			
			for(FieldRule fieldRule:fieldRuleList){
				page.putField(fieldRule.getTitle() , page.getHtml().xpath(fieldRule.getName()).all());
			}
			
			
		}
		

		for(GrabRule grabRule:urlList){
			Html html=new Html(page.getRawText());
			List<String> jsUrlList=new ArrayList<String>();
			List<String> jsFunctionList=html.xpath(grabRule.getValue()).all();
			for(String jsFunction:jsFunctionList){
				if(jsFunction.indexOf("viewDraft")>0){
					int codeBeginIndex=jsFunction.indexOf("'");
					int codeEndIndex=jsFunction.indexOf("'", codeBeginIndex+1);
					String code=jsFunction.substring(codeBeginIndex+1, codeEndIndex);
					String jsUrl=new StringBuffer("/fe/Channel/47916?innerCode=").append(code).append("&bondInfoType=6").toString();
					//jsUrl=this.site.getDomain()+jsUrl;
					jsUrlList.add(jsUrl);
					//进入页面下载文件
//                    page.addTargetRequest(jsUrl);
					Request request=new Request(UrlUtils.canonicalizeUrl(jsUrl, "http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/beDraftByTremList.jsp?searchTypeCode=100041"));
					request.putExtra("level", "2");
					page.addTargetRequest(request);
					
				}
				else if(jsFunction.indexOf("viewBond")>0){
					int codeBeginIndex=jsFunction.indexOf("'");
					int codeEndIndex=jsFunction.indexOf("'", codeBeginIndex+1);
					String code=jsFunction.substring(codeBeginIndex+1, codeEndIndex);
					
					int bondNameBeginIndex=jsFunction.indexOf("'", codeEndIndex+1);
					int bondNameEndIndex=jsFunction.indexOf("'",bondNameBeginIndex+1);
					String bondName=jsFunction.substring(bondNameBeginIndex+1, bondNameEndIndex);
					//URLEncoder.encode(value, "utf-8");
					String jsUrl=new StringBuffer("/fe/Channel/12438?bondCode=").append(code).append("&bondName=").append(bondName).toString();
					//jsUrl=this.site.getDomain()+jsUrl;
					//page.addTargetRequest(jsUrl);
					Request request=new Request(UrlUtils.canonicalizeUrl(jsUrl, "http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/beDraftByTremList.jsp?searchTypeCode=100041"));
					request.putExtra("level", "2");
					page.addTargetRequest(request);
				}
			}
			
		}
		//href为javascript的
		Selectable selectable=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links();
		if(selectable.toString()!=null&&selectable.toString().indexOf("download")>0){
			String fileDownloadUrl=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a").links().toString();
			String fileName=page.getHtml().xpath("/html/body/div/div[1]/div[4]/p/a/font/text()").toString();
			String filedic="D://grab_download/";
			try {  
	            HttpClient client = new DefaultHttpClient();  
	            HttpGet httpget = new HttpGet(selectable.toString());  
	            HttpResponse response = client.execute(httpget);  
	  
	            HttpEntity entity = response.getEntity();  
	            InputStream is = entity.getContent();  
	            
	            String filepath = filedic+fileName;
	            File file = new File(filepath);  
	            file.getParentFile().mkdirs();  
	            FileOutputStream fileout = new FileOutputStream(file);  
	            /** 
	             * 根据实际运行效果 设置缓冲区大小 
	             */  
	            byte[] buffer=new byte[50];  
	            int ch = 0;  
	            while ((ch = is.read(buffer)) != -1) {  
	                fileout.write(buffer,0,ch);  
	            }  
	            is.close();  
	            fileout.flush();  
	            fileout.close();  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
			
			
		}
		
		
	}
}
