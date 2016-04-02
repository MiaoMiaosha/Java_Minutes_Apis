package com.minutes.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.minutes.domain.User;
import com.minutes.util.Constant;
import com.minutes.util.MyBatisUtil;
import com.minutes.util.ResponseJsonUtils;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private User user;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> dataString = new HashMap<String,Object>();
		data.put("code", Constant.METHODERROR_CODE);
		
		data.put("description", Constant.METHODERROR);
		data.put("message", Constant.METHODERROR_MEG);
		data.put("data",dataString);
	    ResponseJsonUtils.json(response, data);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId;
		String token;
		try{//避免用户非法输入
		 userId =Integer.parseInt( request.getParameter("userId"));
		 token = request.getParameter("token");
		}catch(Exception ex){
			userId=-1;
			token="";
		}
		
		
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> dataString = new HashMap<String,Object>();
		
		if(deleteToken(userId,token)){//用户注销成功
			data.put("code", Constant.OK_CODE);
			data.put("description", Constant.OK);
			data.put("message", Constant.OK_MEG);
			data.put("data",dataString);
		}else{//用户注销失败
			data.put("code", Constant.TOKENERROR_CODE);
			data.put("description", Constant.TOKENERROR);
			data.put("message", Constant.TOKENERROR_MEG);
			data.put("data",dataString);
		}
	    ResponseJsonUtils.json(response, data);

	}

	private boolean deleteToken(int id,String token) {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		String statement = "com.minutes.mapping.userMapper.deleteToken";
		User user = new User();
		user.setId(id);
		user.setToken(token);
		int result = sqlSession.update(statement,user);
		if(result==1)return true;
		else return false;
	}

}
