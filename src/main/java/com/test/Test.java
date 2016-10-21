package com.test;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.LifecycleManager;
import com.google.appengine.api.LifecycleManager.ShutdownHook;

public class Test extends HttpServlet {

	private static final Logger log = Logger.getLogger(Test.class.getName());
	
	static {
		LifecycleManager.getInstance().setShutdownHook(new ShutdownHook() {
			public void shutdown() {
				log.info("Shutdown Hook - Start");
				Queue queue = QueueFactory.getQueue("test");
				queue.add(TaskOptions.Builder.withUrl("/test"));
				log.info("task - queued");
				log.info("Shutdown Hook - OK");
			}
		});

		log.info("Shutdown Hook - Set");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("doGet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("doPost");
	}
}
