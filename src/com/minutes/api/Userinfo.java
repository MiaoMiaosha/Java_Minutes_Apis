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
@WebServlet("/Userinfo")
public class Userinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private User user;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Userinfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				User user;
				if((user=selectInfo(userId,token))!=null){//查询用户成功
					
					dataString.put("userId", user.getId());
					dataString.put("tel", user.getTel());
					dataString.put("nickname", user.getNickname());
					dataString.put("gender", user.getGender());
					dataString.put("creatAt", user.getCreateAt());
					dataString.put("fromScore", user.getFromScore());
					dataString.put("toScore", user.getToScore());
					
					data.put("code", Constant.OK_CODE);
					data.put("description", Constant.OK);
					data.put("message", Constant.OK_MEG);
					data.put("data",dataString);
				}else{//查询用户失败
					data.put("code", Constant.TOKENERROR_CODE);
					data.put("description", Constant.TOKENERROR);
					data.put("message", Constant.TOKENERROR_MEG);
					data.put("data",dataString);
				}
			    ResponseJsonUtils.json(response, data);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> dataString = new HashMap<String,Object>();
		data.put("code", Constant.METHODERROR_CODE);
		
		data.put("description", Constant.METHODERROR);
		data.put("message", Constant.METHODERROR_MEG);
		data.put("data",dataString);
	    ResponseJsonUtils.json(response, data);

	}

	private User selectInfo(int id,String token) {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		String statement = "com.minutes.mapping.userMapper.userinfo";
		User user = new User();
		user.setId(id);
		user.setToken(token);
		User recv;
		recv = sqlSession.selectOne(statement,user);
		if(recv!=null)return recv;
		else return null;
	}

}
