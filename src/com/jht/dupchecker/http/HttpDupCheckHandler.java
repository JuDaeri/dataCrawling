package com.jht.dupchecker.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.jht.dupchecker.fileio.RandomAccessToFile;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpDupCheckHandler {
	RandomAccessToFile rtf=new RandomAccessToFile();
	public void serverStart(){
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext("/dupcheck", new MyHandler());
			server.setExecutor(null); // creates a default executor
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) throws Exception {
		HttpDupCheckHandler httpDupChecker=new HttpDupCheckHandler();
		httpDupChecker.serverStart();

	}

	class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			String method = t.getRequestMethod();		
			Map<String,String> queryMap=queryToMap(t.getRequestURI().getQuery());
			System.out.println("query Map : "+queryMap);
			boolean isDuplicated=rtf.dupCheck(queryMap.get("url"), queryMap.get("content"));
			String response = "";
			if(isDuplicated){
				response="true";
			}else{
				response="false";
			}
			
			t.sendResponseHeaders(200, response.length());
			
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	/**
	 * returns the url parameters in a map
	 * 
	 * @param query
	 * @return map
	 */
	public static Map<String, String> queryToMap(String query) {
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length > 1) {
				result.put(pair[0], pair[1]);
			} else {
				result.put(pair[0], "");
			}
		}
		return result;
	}

}