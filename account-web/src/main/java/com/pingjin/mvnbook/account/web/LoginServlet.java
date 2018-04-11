package com.pingjin.mvnbook.account.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pingjin.mvnbook.account.service.AccountServiceException;
import com.pingjin.mvnbook.account.service.AccountServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ApplicationContext context;  
	  
    @Override  
    public void init() throws ServletException {  
        super.init();  
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
    }  
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String id = request.getParameter("id");  
	     String password = request.getParameter("password");  
	  
	     if (id == null || id.length() == 0 || password == null || password.length() == 0) {  
	         response.sendError(400, "incomplete parameter");  
	         return;  
	     }  
	  
	     AccountServiceImpl service = (AccountServiceImpl) context.getBean("accountService");  
	  
	     try {  
	          service.login(id, password);  
	          response.getWriter().print("Login Successful!");  
	     } catch (AccountServiceException e) {  
	       response.sendError(400, e.getMessage());  
	     }  
	}

}
