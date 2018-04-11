package com.pingjin.mvnbook.account.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pingjin.mvnbook.account.captcha.AccountCaptchaServiceImpl;

public class AccountServiceTest {

	/*private GreenMail greenMail;*/  
	  
    private AccountServiceImpl accountService;  
  
    @Before  
    public void prepare() throws Exception {  
        String[] springConfigFiles = { "account-email.xml", "account-persist.xml", "account-captcha.xml", "account-service.xml" };  
  
        ApplicationContext ctx = new ClassPathXmlApplicationContext(springConfigFiles);  
  
        AccountCaptchaServiceImpl accountCaptchaService = (AccountCaptchaServiceImpl) ctx.getBean("accountCaptchaService");  
  
        List<String> preDefinedTexts = new ArrayList<String>();  
        preDefinedTexts.add("12345");  
        preDefinedTexts.add("abcde");  
        accountCaptchaService.setPreDefinedTexts(preDefinedTexts);  
  
        accountService = (AccountServiceImpl) ctx.getBean("accountService");  
  
        /*greenMail = new GreenMail(ServerSetup.SMTP);  
        greenMail.setUser("15823789581@163.com", "pingjin150");  
        greenMail.start(); */ 
  
        File persistDataFile = new File("target/test-classes/persist-data.xml");  
        if (persistDataFile.exists()) {  
            persistDataFile.delete();  
        }  
    }  
  
    @Test  
    public void testAccountService() throws Exception {  
        // 1. Get captcha  
        String captchaKey = accountService.generateCaptchaKey();  
        accountService.generateCaptchaImage(captchaKey);  
        String captchaValue = "12345";  
  
        // 2. Submit sign up Request  
        SignUpRequest signUpRequest = new SignUpRequest();  
        signUpRequest.setCaptchaKey(captchaKey);  
        signUpRequest.setCaptchaValue(captchaValue);  
        signUpRequest.setId("xuj");  
        signUpRequest.setEmail("736252868@qq.com");  
        signUpRequest.setName("xuj");  
        signUpRequest.setPassword("admin");  
        signUpRequest.setConfirmPassword("admin");  
        signUpRequest.setActivateServiceUrl("http://localhost:8080/account/activate");  
        accountService.signUp(signUpRequest); 
        
        String activationId="";
        Map<String, String> map=accountService.getActivationMap();
        for(String key:map.keySet()){
        	if(map.get(key).equals("xuj")){
        		activationId=key;
        		break;
        	}
        }
        String activationLink = signUpRequest.getActivateServiceUrl().endsWith("/") ? signUpRequest.getActivateServiceUrl() + activationId : signUpRequest  
                .getActivateServiceUrl() + "?key=" + activationId; 
  
        // 3. Read activation link  
        /*greenMail.waitForIncomingEmail(2000, 1); 
        Message[] msgs = greenMail.getReceivedMessages();  
        assertEquals(1, msgs.length);  
        assertEquals("Please Activate Your Account", msgs[0].getSubject());  
        String activationLink = GreenMailUtil.getBody(msgs[0]).trim();  */
  
        // 3a. Try login but not activated  
        try {  
            accountService.login("xuj", "admin");  
            System.out.println("Disabled account shouldn't be able to log in.");  
        } catch (AccountServiceException e) {  
        }  
  
        // 4. 激活此用户  
        String activationCode = activationLink.substring(activationLink.lastIndexOf("=") + 1);
        System.out.println(activationCode);
        accountService.activate(activationCode);  
  
        // 5. Login with correct id and password  
        accountService.login("xuj", "admin");  
  
        // 5a. Login with incorrect password  
        try {  
            accountService.login("xuj", "admin1");  
            System.out.println("Password is incorrect, shouldn't be able to login."); 
        } catch (AccountServiceException e) {  
        
        }  
  
    }  
  
    /*@After  
    public void stopMailServer() throws Exception {  
        greenMail.stop();  
    } */
}
