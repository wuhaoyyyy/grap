package com.purang.grab.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.purang.grab.pageprocessor.PageProcessorChinaMoney;
import com.purang.grab.pageprocessor.PageProcessorPreProcess;
import com.purang.grab.pageprocessor.PreProcess;

public class GrabServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(GrabServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String taskName=req.getParameter("taskname");
		String operation=req.getParameter("operation");
		logger.info(taskName+":"+operation);
		if(operation.equals("start")){
			Resource rs = new FileSystemResource("tasks/"+taskName+"/"+taskName+"-beans.xml");
			BeanFactory taskBeanFactory = new XmlBeanFactory(rs);
			PreProcess preProcess=(PreProcess)taskBeanFactory.getBean("preprocess");
			for(PageProcessorPreProcess pageProcessorPreProcess:preProcess.getPreprocessList()){
				Spider spider = Spider.create(pageProcessorPreProcess).addPipeline(new Pipeline() {
		            public void process(ResultItems resultItems, Task task) {
		                 System.out.println(resultItems.get("preprocess"));
		            }
		        }).thread(1);
		        spider.start();
				spider.getStatus();
			}
		}
		else if(operation.equals("close")){
	        //spider2.close();
		}
	}
	
}
