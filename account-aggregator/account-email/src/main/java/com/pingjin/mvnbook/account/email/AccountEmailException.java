package com.pingjin.mvnbook.account.email;

public class AccountEmailException extends Exception{

	private static final long serialVersionUID = 6514881539290222459L;  
	  
    public AccountEmailException(String message) {  
        super(message);  
    }  
  
    public AccountEmailException(String message, Throwable throwable) {  
        super(message, throwable);  
    }  
}
