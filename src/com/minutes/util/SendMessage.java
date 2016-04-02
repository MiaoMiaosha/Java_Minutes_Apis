package com.minutes.util;

import java.util.Random;

public class SendMessage {
	
	
	public static boolean send(String tel){
		return true;
	}
	public static String getVerify(){
		Random random = new Random();
		String num = random.nextInt(9999)+"";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<4-num.length();i++){
			sb.append("0");
		}
		num = sb.toString()+num;
		return num;
	}

}
