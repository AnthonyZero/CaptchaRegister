package com.pingjin.mvnbook.account.persist;

public class AccountPersistException extends Exception{
	
	private static final long serialVersionUID = 7208989305935509389L;  
	  
    public AccountPersistException(String message) {  
        super(message);  
    }  
  
    public AccountPersistException(String message, Throwable throwable) {  
        super(message, throwable);  
    }  
}
