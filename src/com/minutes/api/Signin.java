package com.minutes.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;

import com.minutes.domain.User;
import com.minutes.util.MD5Util;
import com.minutes.util.MyBatisUtil;
import com.minutes.util.ResponseJsonUtils;

/**
 * Servlet implementation class Signin
 */
@WebServlet("/Signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private User usr;
    private String tel;
    private String verifyTel;
    private  String recvCode;
    private  String verify;
    private String token;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		 tel = request.getParameter("tel");
		 recvCode = request.getParameter("code");
		verify = (String) session.getAttribute("verifyCode");
		verifyTel =(String) session.getAttribute("verifyTel");
		 //verify ="1234";
		
		 
		 Map<String, Object> data = new HashMap<String, Object>();
		 Map<String, Object> dataString = new HashMap<String, Object>();
		
	if(recvCode.equals(verify) && verifyTel.equals(tel)){//验证码、电话与所传相等
		    if((usr=isUser(tel))!=null){//如果是老用户
		    	 if(updateToken(CalculateToken())){//更新token成功，则向客户端写正确信息
			dataString.put("userId", usr.getId());
			dataString.put("tel", usr.getTel());
			dataString.put("nickname", usr.getNickname());
			dataString.put("gender", usr.getGender());
			dataString.put("fromScore", usr.getFromScore());
			dataString.put("toScore", usr.getToScore());
			dataString.put("createAt", usr.getCreateAt());
			dataString.put("token",token);
			dataString.put("type", usr.getType());


			
			data.put("code", 200);
			data.put("description", "ok");
			data.put("message", "成功");
			data.put("data",dataString);
			//更新成功后才删除
			session.removeAttribute("verifyCode"); 
			session.removeAttribute("verifyTel");
			
		    	 }//更新token
		    	 else{//更新token失败
		    		 
		    	 }
		}
		else{//新用户
			int newId = selectLastId()+1;
			int createAt =(int) (System.currentTimeMillis() / 1000);
			token = CalculateToken();
			
			//添加新用户
			addNewUserToDB(newId,createAt,token,tel);
			
			dataString.put("userId", newId);
			dataString.put("tel", tel);
			dataString.put("createAt", createAt);
			dataString.put("token", token);
			dataString.put("type", 0);

			
			data.put("code", 200);
			data.put("description", "ok");
			data.put("message", "成功");
			data.put("data",dataString);
			
			//更新成功后才删除
			session.removeAttribute("verifyCode"); 
			session.removeAttribute("verifyTel");
		    }
		
		}//提交与缓存code相等
		else{
			//验证码错误			
			data.put("code", 1008);
			data.put("description", "validError");
			data.put("message", "电话号码/验证码错误");
			data.put("data",dataString);
		}
	   //最后都要返回数据
	    ResponseJsonUtils.json(response, data);
	}
	private void addNewUserToDB(int newId, int createAt,String getToken,String telNum) {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		String statement = "com.minutes.mapping.userMapper.addUser";
		User user = new User();
		user.setCreateAt(createAt);
		user.setId(newId);
		user.setToken(getToken);
		user.setTel(telNum);
		//result可作用户添加成功判断
		int result = sqlSession.insert(statement,user);
		sqlSession.close();
	}

	//返回最后一项userId
	private int selectLastId() {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		String statement = "com.minutes.mapping.userMapper.getLastUser";
		int result = sqlSession.selectOne(statement);
		sqlSession.close();
		return result;

	}
	//计算token值并返回
	private String CalculateToken() {
		int time=(int) (System.currentTimeMillis() / 1000);
		String total=tel+verify+time;
		return MD5Util.MD5(total);
	}
	//在数据库更新token
	private boolean updateToken(String s){
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		String statement = "com.minutes.mapping.userMapper.updateToken";
		User user = new User();
		user.setToken(s);
		user.setTel(tel);
		int result = sqlSession.update(statement,user);
		sqlSession.close();
		
		System.out.println(token+"\n"+"result is:"+result);
		if(result==1){
			token=s;
			System.out.println(token);
			return true;}
		else return false;
	}
    //如果老用户，返回对象
	private  User isUser(String tel){
		SqlSession sqlSession=MyBatisUtil.getSqlSession(true);
		String statement="com.minutes.mapping.userMapper.getUser";
		User user = new User();
		user.setTel(tel);
		Object obj=sqlSession.selectOne(statement,user);//数据库中有重复号码会报错
		sqlSession.close();
		if(obj !=null)return (User)obj;
		else return null;
	}

}
