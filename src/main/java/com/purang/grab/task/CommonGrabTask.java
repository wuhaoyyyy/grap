package com.purang.grab.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.entity.BondAnnouncement;
import com.purang.grab.pageprocessor.DataProcess;
import com.purang.grab.rule.GrabRule;
import com.purang.grab.service.BondAnnouncementService;
import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.DateUtils;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class CommonGrabTask implements GrabTask{

	
	public static void main(String[] args) {
		CommonGrabTask commonGrabTask=new CommonGrabTask();
		commonGrabTask.start();
	}
	private String taskName="testtask";
	private String url="http://www.chinamoney.com.cn/fe/jsp/CN/chinamoney/notice/beDraftByTremList.jsp?searchTypeCode=100041";
	public CommonGrabTask() {
		
	}
	
	public void start(){
		final ApplicationContext ac = ApplicationContextUtils.getInstance() ;
		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\tasks\\"+taskName+"\\"+taskName+"-beans.xml");
		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
		final DataProcess dataProcess=(DataProcess)taskBeanFactory.getBean("dataprocess");
		Spider spider = Spider.create(dataProcess).addUrl(url).thread(5).addPipeline(new Pipeline() {
	        public void process(ResultItems resultItems, Task task) {
	        	List<String>  titleList=resultItems.get("标题");
	        	List<String>  dateList=resultItems.get("日期");
	        	if(titleList!=null&&titleList.size()>0){
		        	List<BondAnnouncement> bondAnnouncementList=new ArrayList<BondAnnouncement>();
		        	for(int i=0;i<titleList.size();i++){
		        		String title=titleList.get(i);
		        		String date=dateList.get(i);
		        		BondAnnouncement bondAnnouncement=new BondAnnouncement();
		        		bondAnnouncement.setAnnounceSource("2");
		        		bondAnnouncement.setAnnounceDate(DateUtils.getString(date, "yyyy-mm-dd", "yyyyMMdd"));
		        		bondAnnouncement.setAnnounceType1("201");//A62	公告类型：原始披露一级	债券类	201	债券发行
		        		bondAnnouncement.setBondType1("201");//A64	债券类型:原始披露一级	债券类	201	同业存单
		        		bondAnnouncement.setAnnounceTitle1(title);
		        		bondAnnouncement.setLink1("testttttttt");
		        		bondAnnouncementList.add(bondAnnouncement);
		        	}
		        	//spider.close();
		        	BondAnnouncementService service = (BondAnnouncementService) ac.getBean("bondAnnouncementService");
		        	service.insertList(bondAnnouncementList);
	        	}
	        }
		});
		spider.start();
	}
	
	
	
	
}
