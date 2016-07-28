package com.jht.dupchecker.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.jht.dupchecker.imple.DupCheckUsingHashing;

public class RandomAccessToFile {
	RandomAccessFile raf = null;
	DupCheckUsingHashing dup = new DupCheckUsingHashing();
	
	public RandomAccessToFile() {
		File file = new File("random.txt");
		try {

			raf = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public byte[] seekUsingHashKey(int hashKey) {
		byte[] bites = new byte[20];
		try {
			raf.seek(hashKey);
			raf.read(bites);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bites;

	}

	public void writeUsingHashKey(int hashKey, byte[] content) {
		try {
			System.out.println(content);
			raf.seek(hashKey);
			raf.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
				
		RandomAccessToFile rtf = new RandomAccessToFile();
		rtf.dupCheck("www.naver.com", "햄토리");

		
	

	}

	public boolean isDuplicate(byte[] orgContent, byte[] newContent) {
		boolean isDuplicate = true;
		for(int i=0;i<orgContent.length;i++){
			if(orgContent[i] != newContent[i]){
				isDuplicate=false;
				break;
			}
		}
		return isDuplicate;
	}
	public void printByte(byte[] bites){
		for(byte b : bites){
			System.out.print(b+", ");
		}
		System.out.println();
	}
	
	public boolean dupCheck(String url,String content){
		int key = dup.getKey(url);
		byte[] content_byte = dup.messageDigesting(content);		
		byte[] orgContent=seekUsingHashKey(key);
		
		
		boolean isDupl=isDuplicate(orgContent, content_byte);
		if(!isDupl){
			writeUsingHashKey(key, content_byte);
		}
		printByte(content_byte);
		printByte(orgContent);
		System.out.println(isDupl);
		return isDupl;
	}

	public void makeDBFile() {
		//
		try {
			raf.seek((long) (7000000000l * 1.5) * 20);
			raf.write(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
