import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.purang.grab.pageprocessor.DataProcess;
import com.purang.grab.pageprocessor.PageProcessorChinaMoney;
import com.purang.grab.pageprocessor.PageProcessorPreProcess;
import com.purang.grab.pageprocessor.PreProcess;
import com.purang.grab.rule.GrabRule;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;


public class Test {

	public static void main(String[] args) {
		
//		Spider spider = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn/index.html"));
//		String[] strarr=new String[5];
//		spider.addUrl(strarr);
//		
//		
//		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn/index.html")).addUrl("http://www.chinamoney.com.cn/index.html").addPipeline(new Pipeline() {
//            public void process(ResultItems resultItems, Task task) {
//                System.out.println(resultItems.get("urls"));
//                System.out.println(resultItems.get("texts"));
//                System.out.println(resultItems.get("types"));
//            }
//        }).thread(1);
//        spider2.start();
        

//		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn")).addUrl("http://www.chinamoney.com.cn").addPipeline(new Pipeline() {
//            public void process(ResultItems resultItems, Task task) {
//                System.out.println(resultItems.get("title"));
//            }
//        }).thread(1);
//        spider2.start();
		
		String taskName="testtask";
		Resource rs = new FileSystemResource(System.getProperty("user.dir")+"\\tasks\\"+taskName+"\\"+taskName+"-beans.xml");
		//Resource rs = new FileSystemResource("E:\\workspace_extend\\grap\\tasks\\testtask\\testtask-beans.xml");
		BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
		final DataProcess dataProcess=(DataProcess)taskBeanFactory.getBean("dataprocess");
		Spider spider = Spider.create(dataProcess).addUrl(dataProcess.getUrl()).addPipeline(new Pipeline() {
        public void process(ResultItems resultItems, Task task) {
        	for(GrabRule grabRule:dataProcess.getRuleList()){
        		System.out.println(grabRule.getName()+":"+resultItems.get(grabRule.getName()));
        	}
   		}
		}).thread(1);
		spider.start();
		
		
		
		//PreProcess preProcess=(PreProcess)taskBeanFactory.getBean("process");
//		for(PageProcessorPreProcess pageProcessorPreProcess:preProcess.getPreprocessList()){
//			Spider spider = Spider.create(pageProcessorPreProcess).addPipeline(new Pipeline() {
//	            public void process(ResultItems resultItems, Task task) {
//	                 System.out.println(resultItems.get("preprocess"));
//	            }
//	        }).thread(1);
//	        spider.start();
//			spider.getStatus();
//		}
	}
	
}
