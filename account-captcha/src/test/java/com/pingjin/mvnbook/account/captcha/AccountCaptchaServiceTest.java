package com.pingjin.mvnbook.account.captcha;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountCaptchaServiceTest {
	
	private AccountCaptchaServiceImpl service;  
	  
    @Before  
    public void prepare() throws Exception {  
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");  
        service = (AccountCaptchaServiceImpl) ctx.getBean("accountCaptchaService");  
    }  
  
    @Test  
    public void testGenerateCaptcha() throws Exception {  
        String captchaKey = service.generateCaptchaKey();  
        System.out.println(captchaKey+" "+service.getCaptchaMap().get(captchaKey)); 
        
        byte[] captchaImage = service.generateCaptchaImage(captchaKey);  
        /*System.out.println(captchaImage.length); */ 
  
        File image = new File("target/" + captchaKey + ".jpg");  
        OutputStream output = null;  
        try {  
            output = new FileOutputStream(image);  
            output.write(captchaImage);  
        } finally {  
            if (output != null) {  
                output.close();  
            }  
        }  
        System.out.println(image.exists() && image.length() > 0);  
    }  
  
    @Test  
    public void testValidateCaptchaCorrect() throws Exception {  
        List<String> preDefinedTexts = new ArrayList<String>();  
        preDefinedTexts.add("12345");  
        preDefinedTexts.add("abcde");  
        service.setPreDefinedTexts(preDefinedTexts);  
  
        //生成Key返回的同时，得到Key对应的Text并把他们加入到Map中
        String captchaKey = service.generateCaptchaKey();  
        service.generateCaptchaImage(captchaKey);  
        System.out.println(service.validateCaptcha(captchaKey, "12345")); 
  
        captchaKey = service.generateCaptchaKey();  
        service.generateCaptchaImage(captchaKey);  
        System.out.println(service.validateCaptcha(captchaKey, "abcde"));  
    }  
  
    @Test  
    public void testValidateCaptchaIncorrect() throws Exception {  
        List<String> preDefinedTexts = new ArrayList<String>();  
        preDefinedTexts.add("12345");  
        service.setPreDefinedTexts(preDefinedTexts);  
  
        String captchaKey = service.generateCaptchaKey();  
        service.generateCaptchaImage(captchaKey);  
        System.out.println(service.validateCaptcha(captchaKey, "67890"));  
    }  
}
