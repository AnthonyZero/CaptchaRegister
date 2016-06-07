package com.pingjin.mvnbook.account.persist;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountPersistServiceTest {
	private AccountPersistServiceImpl service;
	
	@Before
	public void prepare() throws Exception{
		File persistDataFile = new File ( "target/test-classes/persist-data.xml" );
		 if ( persistDataFile.exists() )  {  
	         persistDataFile.delete();  
	     }  
	          
	     ApplicationContext ctx = new ClassPathXmlApplicationContext( "account-persist.xml" );  
	  
	     service = (AccountPersistServiceImpl) ctx.getBean( "accountPersistService" );  
	          
	     Account account = new Account();  
	     account.setId("ping");  
	     account.setName("pingjin");  
	     account.setEmail("736252868@qq.com");  
	     account.setPassword("pingjin150");  
	     account.setActivated(true);  
	          
	     service.createAccount(account);
	}
	
	 @Test  
	 public void testReadAccount()  throws Exception{
		 Account account = service.readAccount( "ping" );  
		 System.out.println(account.getId());
		 System.out.println(account.getName());
		 System.out.println(account.getEmail());
		 System.out.println(account.getPassword());
		 System.out.println(account.isActivated());
	     /*assertNotNull( account );  
	     assertEquals( "xuj", account.getId() );  
	     assertEquals( "Xuj", account.getName() );  
	     assertEquals( "xuj@changeme.com", account.getEmail() );  
	     assertEquals( "this_should_be_encrypted", account.getPassword() );  
	     assertTrue( account.isActivated() ); */
	 } 
}
