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
import com.minutes.util.MyBatisUtil;
import com.minutes.util.ResponseJsonUtils;
import com.minutes.util.SendMessage;

/**
 * Servlet implementation class Valid
 */
@WebServlet("/Valid")
public class Valid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Valid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String tel = request.getParameter("tel");
		String verify = SendMessage.getVerify();
		boolean isSent=SendMessage.send(tel);
		
		if(isSent && tel!=null){
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> dataString = new HashMap<String, Object>();
		data.put("code", 200);
		data.put("description", "ok");
		data.put("message", "成功");
		data.put("data","");
		
		System.out.println(verify);
		HttpSession session = request.getSession();
		session.setAttribute("verifyCode", verify);
		session.setAttribute("verifyTel", tel);
		ResponseJsonUtils.json(response, data);
		}
		else{
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> dataString = new HashMap<String, Object>();	
			data.put("code", 1006);
			data.put("description", "sendSmsError");
			data.put("message", "发送短信失败");
			data.put("data","");
			ResponseJsonUtils.json(response, data);
		}
	}
	
	

}
