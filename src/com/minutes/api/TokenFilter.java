package com.minutes.api;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.minutes.domain.User;
import com.minutes.util.MyBatisUtil;

/**
 * Servlet Filter implementation class TokenFilter
 */
@WebFilter("/TokenFilter")
public class TokenFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TokenFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request1 = (HttpServletRequest) request;   
		  HttpServletResponse response1 = (HttpServletResponse) response;
		  String token = null ;
		  try{
			   token = request1.getParameter("token");

		  }catch(Exception ex){
				System.out.println("输入出错？？");
			  //response1.sendRedirect(request1.getContextPath() + "/api/errorpage"); 
			  request.getRequestDispatcher("/errorpage").forward(request,response);
			  return;
		  }
		  
		  if(!checkToken(token)){
			  //response1.sendRedirect(request1.getContextPath() + "/api/errorpage"); 
			  request.getRequestDispatcher("/errorpage").forward(request,response);
			  return;
		  }
		  
		chain.doFilter(request1, response1);
	}

	private boolean checkToken(String token) {
		//验证token存在
		
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		String statement = "com.minutes.mapping.userMapper.tokenFilter";
		User recv;
		recv = sqlSession.selectOne(statement,token);
		if(recv!=null){
			try{
			//验证token没过期
			int currentTime = (int) (System.currentTimeMillis() / 1000);
			int token_time = recv.getToken_time();
			if(token_time<=currentTime){//token已到期
				System.out.println("token过期");
				return false;
			}else return true;
			
			}catch(Exception ex){//token_time为空时报错
				System.out.println("token_time为空");

				return false;
			}
		}
		else 
		{
			System.out.println("查询返回对象为空");
			return false;
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
