package com.virtualpay.io.callbacks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CallbackHandler implements HttpHandler {

	public CallbackHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	// handling calllbackRequests
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Callback request received");
		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(exchange.getRequestBody(), "UTF-8")); String line = "";
		 * StringBuilder buffer = new StringBuilder(); while((line = br.readLine()) !=
		 * null) { buffer.append(line); } buffer.toString();
		 * System.out.println("Response is :  " + buffer.toString());
		 */
		// responding to the request sent
		/*
		 * JSONObject jsonObject=new JSONObject(); jsonObject.put("code","0");
		 * jsonObject.put("message","okay");
		 * 
		 * String res=jsonObject.toString(); exchange.sendResponseHeaders(200,
		 * res.length());
		 * 
		 */
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod == "POST") {
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
//			BufferedReader br = new BufferedReader(isr);
//			String query = br.readLine();
//			parseQuery(query, parameters);
			StringBuilder strBuilder = new StringBuilder();
			String inputLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
			while ((inputLine = br.readLine()) != null) {
				strBuilder.append(inputLine);
			}
			String urString = URLDecoder.decode(strBuilder.toString(), "UTF-8");
			System.out.println("-------------- WEBHOOK TRANSACTION -------------");
			System.out.println(urString.toString());
		} else {
//			Map<String, Object> parameters = new HashMap<String, Object>();
//			URI requestedUri = exchange.getRequestURI();
//			String query = requestedUri.getRawQuery();
//			parseQuery(query, parameters);			StringBuilder strBuilder = new StringBuilder();
			StringBuilder strBuilder = new StringBuilder();
			String inputLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
			while ((inputLine = br.readLine()) != null) {
				strBuilder.append(inputLine);
			}

			String urString = URLDecoder.decode(strBuilder.toString(), "UTF-8");
			System.out.println("-------------- WEBHOOK TRANSACTION -------------");
			System.out.println(urString.toString());

		}
		String res = "{ \r\n" + "   \"code\": \"0\",\r\n" + "   \"message\": \"okay\",\r\n" + "}";
		exchange.sendResponseHeaders(200, res.length());
		OutputStream os = exchange.getResponseBody();
		os.write(res.getBytes("UTF-8"));
		os.close();

	}

	public static void parseQuery(String query, Map<String, Object> parameters) throws IOException {
		if (query != null) {
			String pairs[] = query.split("[&]");
			for (String pair : pairs) {
				String param[] = pair.split("[=]");
				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], "UTF-8");
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], "UTF-8");
				}

				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);

					} else if (obj instanceof String) {
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}

			}
			System.out.println(parameters);
		}

	}

}
