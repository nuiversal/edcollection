package com.winway.android.util;

import java.util.UUID;

public class UUIDGen {

	public static String randomUUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	public static void main(String[] args) {
		System.out.println(randomUUID());
	}
}
