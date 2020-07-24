package com.virtualpay.io.callbacks;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.HttpServer;

public class HandlingCallbacks {

	private HttpServer server;
	int port = 8005;
	int nThreads = 8;

	public HandlingCallbacks() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
		try {
			InetAddress ip = InetAddress.getByName("uat.evirtualpay.com");
			System.out.println(ip.getHostAddress());
			server = HttpServer.create(new InetSocketAddress("167.71.162.91", port), 0);
			server.createContext("/", new RootHandler());
			server.createContext("/transactions/callback", new CallbackHandler());
			server.setExecutor(executor);
			System.out.println("Server is listening to: " + server.getAddress().toString());
			// start server
			server.start();

			System.out.println("Server started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
