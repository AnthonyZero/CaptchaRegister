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
import com.pingjin.mvnbook.account.service.SignUpRequest;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet {
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
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		String email=request.getParameter("email");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");  
        String captchaKey = request.getParameter("captcha_key");  
        String captchaValue = request.getParameter("captcha_value");
        if (id == null || id.length() == 0 || email == null || email.length() == 0 || name == null || name.length() == 0 || password == null  
                || password.length() == 0 || confirmPassword == null || confirmPassword.length() == 0 || captchaKey == null  
                || captchaKey.length() == 0 || captchaValue == null || captchaValue.length() == 0) {  
            response.sendError(400, "Parameter Incomplete.");  
            return;  
        }  
        
        AccountServiceImpl service=(AccountServiceImpl) context.getBean("accountService");
        SignUpRequest req = new SignUpRequest();  
        
        req.setId(id);  
        req.setEmail(email);  
        req.setName(name);  
        req.setPassword(password);  
        req.setConfirmPassword(confirmPassword);  
        req.setCaptchaKey(captchaKey);  
        req.setCaptchaValue(captchaValue);
        
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        /*System.out.println(getServletContext().getRealPath("/")+ "activate");*/
        req.setActivateServiceUrl(basePath + "activate");
        
        try {  
            service.signUp(req);  
            response.getWriter().print("Account is created, please check your mail box for activation link.");  
        } catch (AccountServiceException e) {  
            response.sendError(400, e.getMessage());  
            return;  
        }  
	}

}
