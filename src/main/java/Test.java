import com.purang.grab.pageprocessor.PageProcessorChinaMoney;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


public class Test {

	public static void main(String[] args) {
		Spider spider2 = Spider.create(new PageProcessorChinaMoney("http://www.chinamoney.com.cn/index.html")).addUrl("http://www.pbc.gov.cn/diaochatongjisi/116219/116319/3013637/index.html").addPipeline(new Pipeline() {
            public void process(ResultItems resultItems, Task task) {
                System.out.println(resultItems.get("title"));
            }
        }).thread(1);
        spider2.start();
	}

}
