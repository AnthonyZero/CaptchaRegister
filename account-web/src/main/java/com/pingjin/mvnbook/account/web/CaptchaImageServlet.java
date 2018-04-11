package com.pingjin.mvnbook.account.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pingjin.mvnbook.account.service.AccountServiceException;
import com.pingjin.mvnbook.account.service.AccountServiceImpl;

/**
 * Servlet implementation class CaptchaImageServlet
 */
public class CaptchaImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ApplicationContext context;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaptchaImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		context=WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String key=request.getParameter("key");
		if(key==null||key.length()==0){
			response.sendError(400, "No Captcha Key Found");
		}
		else{
			AccountServiceImpl service=(AccountServiceImpl) context.getBean("accountService");
			try{
				response.setContentType("image/jpeg");
				OutputStream out=response.getOutputStream();
				out.write(service.generateCaptchaImage(key));
				out.close();
			}
			catch(AccountServiceException e)
			{
				response.sendError(404, e.getMessage());
			}
		}
		/*String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		System.out.println(getServletContext().getRealPath("/")+ "activate");
		System.out.println(request.getContextPath());
		System.out.println(basePath);*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
