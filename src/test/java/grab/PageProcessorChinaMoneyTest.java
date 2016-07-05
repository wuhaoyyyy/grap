package grab;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.purang.grab.pageprocessor.PageProcessorChinaMoney;

import junit.framework.TestCase;

public class PageProcessorChinaMoneyTest extends TestCase {

	public void test(){

		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn/index.html")).addUrl("http://www.chinamoney.com.cn/index.html").addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(resultItems.get("title"));
            }
        }).thread(1);
        spider2.start();
	}
}
