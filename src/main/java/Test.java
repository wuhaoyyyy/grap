import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.common.persistence.BaseService;
import com.purang.grab.common.persistence.DataSourceUtil;
import com.purang.grab.dao.BondAnnouncementDao;
import com.purang.grab.entity.BondAnnouncement;
import com.purang.grab.pageprocessor.DataProcess;
import com.purang.grab.pageprocessor.PageProcessorChinaMoney;
import com.purang.grab.pageprocessor.PageProcessorPreProcess;
import com.purang.grab.pageprocessor.PreProcess;
import com.purang.grab.rule.GrabRule;
import com.purang.grab.service.BondAnnouncementService;
import com.purang.grab.task.CommonGrabTask;
import com.purang.grab.util.ApplicationContextUtils;
import com.purang.grab.util.DateUtils;
import com.purang.grab.util.DistributeUniqueId;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class Test {

	protected static Logger logger = LoggerFactory.getLogger(Test.class);
	public static void main(String[] args) {
		final ApplicationContext ac = ApplicationContextUtils.getInstance() ;
		String taskName="testtask";
		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\tasks\\"+taskName+"\\"+taskName+"-beans.xml");
		//Resource rs = new FileSystemResource("E:\\workspace_extend\\grap\\tasks\\testtask\\testtask-beans.xml");
		//ResultItems result = spider .get("http://webmagic.io/docs/")
		//BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
		//CommonGrabTask task=(CommonGrabTask) taskBeanFactory.getBean("task");
		
		
		
		
		
		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
		final DataProcess dataProcess=(DataProcess)taskBeanFactory.getBean("dataprocess");
		
		Spider spider = Spider.create(dataProcess).addUrl(dataProcess.getUrl()).addPipeline(new Pipeline() {
	        public void process(ResultItems resultItems, Task task) {
	        	List<String>  titleList=resultItems.get("标题");
	        	List<String>  dateList=resultItems.get("日期");
	        	if(titleList.size()>0){
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
	        	
		}).thread(1);
		spider.start();
	}
	
}
