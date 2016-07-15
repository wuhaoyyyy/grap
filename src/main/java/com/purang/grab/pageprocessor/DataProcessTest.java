package com.purang.grab.pageprocessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.FileCache;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import com.purang.grab.rule.GrabRule;

public class DataProcessTest implements PageProcessor {
	
	private String url;
	private String sql;
	private List<GrabRule> ruleResult=new ArrayList<GrabRule>();
	private List<GrabRule> ruleExit=new ArrayList<GrabRule>();//
	private List<GrabRule> urlList=new ArrayList<GrabRule>();
    private Site site;
	
    private List<Pipeline> pipelineList=new ArrayList<Pipeline>();
    
    public DataProcessTest() {
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
		page.getRequest().getExtras();
		for(GrabRule grabRule:ruleResult){
			page.putField(grabRule.getName() , page.getHtml().xpath(grabRule.getValue()).all());
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
					page.addTargetRequest(jsUrl);
					Request request=new Request(jsUrl);
					request.putExtra("", "");
					//request.setExtras(extras);
					
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
					jsUrlList.add(jsUrl);
					page.addTargetRequest(jsUrl);
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

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<GrabRule> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<GrabRule> urlList) {
		this.urlList = urlList;
	}
	
	

}
