package com.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.LifecycleManager;
import com.google.appengine.api.LifecycleManager.ShutdownHook;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(Test.class.getName());

	static {
		LifecycleManager.getInstance().setShutdownHook(new ShutdownHook() {
			public void shutdown() {
				try {
					log.info("Shutdown Hook - Begin");

					log.info("Shutdown Hook - Datastore - start");
					Entity test = new Entity("Test");
					test.setProperty("time", new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
					DatastoreServiceFactory.getDatastoreService().put(test);
					log.info("Shutdown Hook - Datastore - end");

					log.info("Shutdown Hook - TaskQueue - start");
					Queue queue = QueueFactory.getQueue("test");
					queue.add(TaskOptions.Builder.withUrl("/test"));
					log.info("Shutdown Hook - TaskQueue - end");

					log.info("Shutdown Hook - End");
				} catch (Exception e) {
					log.log(Level.SEVERE, "hook Exception", e);
				}
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
