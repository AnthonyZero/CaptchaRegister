package com.pingjin.mvnbook.account.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pingjin.mvnbook.account.service.AccountService;
import com.pingjin.mvnbook.account.service.AccountServiceException;
import com.pingjin.mvnbook.account.service.AccountServiceImpl;

/**
 * Servlet implementation class ActivateServlet
 */
public class ActivateServlet extends HttpServlet {
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
    public ActivateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");  
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");  
        if (key == null || key.length() == 0) {  
            response.sendError(400, "No activation key provided.");  
            return;  
        }  
  
        AccountServiceImpl service = (AccountServiceImpl) context.getBean("accountService");  
  
        try {  
            service.activate(key);  
            response.getWriter().write("Account is activated, now you can login.");
            response.getWriter().write("<a href='http://localhost:8080/account-web/login.jsp'>点击登录</a>");
        } catch (AccountServiceException e) {  
            response.sendError(400, "Unable to activate account");  
            return;  
        }  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
