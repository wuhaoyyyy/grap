import org.apache.http.client.HttpClient;

import com.purang.grab.pageprocessor.PageProcessorChinaMoney;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


public class Test {

	public static void main(String[] args) {
		
		Spider spider = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn/index.html"));
		String[] strarr=new String[5];
		spider.addUrl(strarr);
		
		
		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn/index.html")).addUrl("http://www.chinamoney.com.cn/index.html").addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(resultItems.get("urls"));
                System.out.println(resultItems.get("texts"));
                System.out.println(resultItems.get("types"));
            }
        }).thread(1);
        spider2.start();
        

//		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn")).addUrl("http://www.chinamoney.com.cn").addPipeline(new Pipeline() {
//            public void process(ResultItems resultItems, Task task) {
//                System.out.println(resultItems.get("title"));
//            }
//        }).thread(1);
//        spider2.start();
	}
	
}
