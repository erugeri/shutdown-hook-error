package com.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.LifecycleManager;
import com.google.appengine.api.LifecycleManager.ShutdownHook;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(Test.class.getName());

	private static final String MEMCACHE_KEY = "key";
	
	static {
		LifecycleManager.getInstance().setShutdownHook(new ShutdownHook() {
			public void shutdown() {
				try {
					log.info("Shutdown Hook - Begin");

					log.info("Shutdown Hook - MemCache - start");
					MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
					Integer key = (Integer) syncCache.get(MEMCACHE_KEY);
					if (key == null) {
						key = 0;
					}
					syncCache.put(MEMCACHE_KEY, key + 1);
					log.info("Shutdown Hook - MemCache - end");

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
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		Integer key = (Integer) syncCache.get(MEMCACHE_KEY);
		String output = "key="+key;
		resp.getOutputStream().write(output.getBytes());
	}
}
