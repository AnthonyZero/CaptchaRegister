package com.pingjin.mvnbook.account.captcha;

import java.util.List;

public interface AccountCaptchaService {
	
	/** 
     * 生成随机验证码 
     *  
     * @return 
     * @throws AccountCaptchaException 
     */  
    String generateCaptchaKey() throws AccountCaptchaException; 
    
    /** 
     * 生成验证码图片 
     *  
     * @param captchaKey 
     * @return 
     * @throws AccountCaptchaException 
     */  
    byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;
    
    /** 
     * 验证用户反馈的主键和值 
     *  
     * @param captchaKey 
     * @param captchaValue 
     * @return 
     * @throws AccountCaptchaException 
     */  
    boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;
    
    /** 
     * 获得自定义验证码 
     *  
     * @return 
     */  
    List<String> getPreDefinedTexts();  
  
    /** 
     * 设置自定义验证码 
     *  
     * @param preDefinedTexts 
     */  
    void setPreDefinedTexts(List<String> preDefinedTexts);
}
