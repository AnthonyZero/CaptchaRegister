package com.pingjin.mvnbook.account.email;

public interface AccountEmailService {
	//收件地址，主题，内容
	void sendMail(String to,String subject,String htmlText) throws AccountEmailException;
}
