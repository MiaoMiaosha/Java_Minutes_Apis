package com.minutes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixTime {
	
	public static void main(String args[]) throws ParseException{
		System.out.println(getCurrentTime());
		System.out.println(TimeStamp2Date("1472805180"));
		System.out.println(Date2TimeStamp("2016-09-02 16:33:00"));
		
	}
	public static int getCurrentTime(){
		return (int) (System.currentTimeMillis() / 1000);
	}
	//时间戳to日期
	public static String TimeStamp2Date(String timestampString){ 
		  String formats= "yyyy-MM-dd HH:mm:ss";
		  Long timestamp = Long.parseLong(timestampString)*1000;    
		  String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));    
		  return date;    
		} 
	
	//日期to时间戳
	public static int Date2TimeStamp(String date) throws ParseException{
		String format="yyyy-MM-dd HH:mm:ss";
		long epoch = new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
		
		return (int) (epoch/1000);	}

}
