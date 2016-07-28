package com.jht.dupchecker.imple;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.jht.dupchecker.inter.DupChecker;

public class DupCheckUsingHashing implements DupChecker {

	public static void main(String[] args) {
		DupChecker dup = new DupCheckUsingHashing();
		int hashKey=dup.getKey("www.naver.com");
		System.out.println(hashKey);
	}

	@Override
	public int getKey(String url) {
		int hashKey=0;
		try {
			hashKey=byteToint(messageDigesting(url));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(hashKey<0){
			hashKey*=-1;
		}
		return hashKey*20;
	}
	
	@Override
	public boolean dupCheck(String content) {
		// TODO Auto-generated method stub
		return false;
	}


	public byte[] messageDigesting(String url){

		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		md.update(url.getBytes());
		byte[] result_naver = md.digest();

		return result_naver;
	}

	public int byteToint(byte[] arr) {
		return (arr[0] & 0xff) << 24 | (arr[1] & 0xff) << 16 | (arr[2] & 0xff) << 8 | (arr[3] & 0xff);
	}
}
