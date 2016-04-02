package com.minutes.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	
	public static SqlSessionFactory getSqlSessionFactory(){
		String resource="/com/minutes/conf/conf.xml";
		//InputStream is=MyBatisUtil.class.getClassLoader().getResourceAsStream(resource);
		InputStream is=MyBatisUtil.class.getResourceAsStream(resource);       
		SqlSessionFactory sf=new SqlSessionFactoryBuilder().build(is);
		return sf;
	}
	public static SqlSession getSqlSession(){
		return getSqlSessionFactory().openSession();
	}
	
	public static SqlSession getSqlSession(boolean autoCommit){
		return getSqlSessionFactory().openSession(autoCommit);
	}

}
