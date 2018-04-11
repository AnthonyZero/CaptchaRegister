package com.pingjin.mvnbook.account.service;

import java.util.HashMap;
import java.util.Map;

import com.pingjin.mvnbook.account.captcha.AccountCaptchaException;
import com.pingjin.mvnbook.account.captcha.AccountCaptchaServiceImpl;
import com.pingjin.mvnbook.account.captcha.RandomGenerator;
import com.pingjin.mvnbook.account.email.AccountEmailException;
import com.pingjin.mvnbook.account.email.AccountEmailServiceImpl;
import com.pingjin.mvnbook.account.persist.Account;
import com.pingjin.mvnbook.account.persist.AccountPersistException;
import com.pingjin.mvnbook.account.persist.AccountPersistServiceImpl;

public class AccountServiceImpl implements AccountService{
	private Map<String, String> activationMap = new HashMap<String, String>();
	
	private AccountPersistServiceImpl accountPersistService;  
	  
    private AccountEmailServiceImpl accountEmailService;  
  
    private AccountCaptchaServiceImpl accountCaptchaService;
    
    
    
    
    
	public Map<String, String> getActivationMap() {
		return activationMap;
	}

	public void setActivationMap(Map<String, String> activationMap) {
		this.activationMap = activationMap;
	}

	public AccountPersistServiceImpl getAccountPersistService() {
		return accountPersistService;
	}

	public void setAccountPersistService(AccountPersistServiceImpl accountPersistService) {
		this.accountPersistService = accountPersistService;
	}

	public AccountEmailServiceImpl getAccountEmailService() {
		return accountEmailService;
	}

	public void setAccountEmailService(AccountEmailServiceImpl accountEmailService) {
		this.accountEmailService = accountEmailService;
	}

	public AccountCaptchaServiceImpl getAccountCaptchaService() {
		return accountCaptchaService;
	}

	public void setAccountCaptchaService(AccountCaptchaServiceImpl accountCaptchaService) {
		this.accountCaptchaService = accountCaptchaService;
	}

	public String generateCaptchaKey() throws AccountServiceException {
		try {  
            return accountCaptchaService.generateCaptchaKey();  
        } catch (AccountCaptchaException e) {  
            throw new AccountServiceException("Unable to generate Captcha key.", e);  
        }  
	}

	public byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException {
		try {  
            return accountCaptchaService.generateCaptchaImage(captchaKey);  
        } catch (AccountCaptchaException e) {  
            throw new AccountServiceException("Unable to generate Captcha Image.", e);  
        }  
	}

	public void signUp(SignUpRequest signUpRequest) throws AccountServiceException {
		 try {  
	            if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {  
	                throw new AccountServiceException("2 passwords do not match.");  
	            }  
	  
	            if (!accountCaptchaService.validateCaptcha(signUpRequest.getCaptchaKey(), signUpRequest.getCaptchaValue())) {  
	  
	                throw new AccountServiceException("Incorrect Captcha.");  
	            }  
	  
	            Account account = new Account();  
	            account.setId(signUpRequest.getId());  
	            account.setEmail(signUpRequest.getEmail());  
	            account.setName(signUpRequest.getName());  
	            account.setPassword(signUpRequest.getPassword());  
	            account.setActivated(false);  
	  
	            //将用户信息保存
	            accountPersistService.createAccount(account);  
	            //生成一个随机的激活码并保存在临时的Map中
	            String activationId = RandomGenerator.getRandomString();  
	  
	            activationMap.put(activationId, account.getId());  
	            //基于该激活码和请求中的服务器URL创建一个激活链接，并且通过邮件发送给用户
	            String link = signUpRequest.getActivateServiceUrl().endsWith("/") ? signUpRequest.getActivateServiceUrl() + activationId : signUpRequest  
	                    .getActivateServiceUrl() + "?key=" + activationId;  
	            System.out.println(link);
	            accountEmailService.sendMail(account.getEmail(), "Please Activate Your Account", link);  
	        } catch (AccountCaptchaException e) {  
	            throw new AccountServiceException("Unable to validate captcha.", e);  
	        } catch (AccountPersistException e) {  
	            throw new AccountServiceException("Unable to create account.", e);  
	        } catch (AccountEmailException e) {  
	            throw new AccountServiceException("Unable to send actiavtion mail.", e);  
	        }  
		
	}

	public void activate(String activationId) throws AccountServiceException {
		//通过激活码对应得到用户ID
		String accountId = activationMap.get(activationId);  
		  
        if (accountId == null) {  
            throw new AccountServiceException("Invalid account activation ID.");  
        }  
  
        try {  
        	//如果找到就更新用户状态为激活
            Account account = accountPersistService.readAccount(accountId);  
            account.setActivated(true);  
            accountPersistService.updateAccount(account);  
        } catch (AccountPersistException e) {  
            throw new AccountServiceException("Unable to activate account.");  
        }  
	}

	public void login(String id, String password) throws AccountServiceException {
		try {  
            Account account = accountPersistService.readAccount(id);  
  
            if (account == null) {  
                throw new AccountServiceException("Account does not exist.");  
            }  
  
            if (!account.isActivated()) {  
                throw new AccountServiceException("Account is disabled.");  
            }  
  
            if (!account.getPassword().equals(password)) {  
                throw new AccountServiceException("Incorrect password.");  
            }  
        } catch (AccountPersistException e) {  
            throw new AccountServiceException("Unable to log in.", e);  
        }  
	}

}
