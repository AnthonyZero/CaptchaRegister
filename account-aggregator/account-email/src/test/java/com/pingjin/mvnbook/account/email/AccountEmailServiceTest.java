package com.pingjin.mvnbook.account.email;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {
	//GreenMail作为测试邮件服务器
	private GreenMail greenMail;  
	
	@Before
	public void startMailServer() throws Exception{
		greenMail=new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("15823789581@163.com", "pingjin150");
		greenMail.start();
	}
      
  
    @Test  
    public void testSendMail() throws Exception {  
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");  
        AccountEmailService accountEmailService = (AccountEmailService) ctx.getBean("accountEmailService");  
        String subject = "主题";  
        String htmlText = "<h3>I want to success</h3>";  
        
        accountEmailService.sendMail("736252868@qq.com", subject, htmlText);  
  
        /*greenMail.waitForIncomingEmail(2000, 1);  
  
        Message[] msgs = greenMail.getReceivedMessages();  
        assertEquals(1, msgs.length);  
        assertEquals(subject, msgs[0].getSubject());  
        assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim()); */
    }  
  
    @After  
    public void stopMainServer() throws Exception {  
        greenMail.stop();  
    }  
}
