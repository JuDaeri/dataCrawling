package com.jht.dupchecker;

import com.jht.dupchecker.http.HttpDupCheckHandler;

public class DupCheckerMain {
	public static void main(String[] args) {
		System.out.println("dupChecker started..");
		HttpDupCheckHandler httpHandler=new HttpDupCheckHandler();
		httpHandler.serverStart();
	}
}
