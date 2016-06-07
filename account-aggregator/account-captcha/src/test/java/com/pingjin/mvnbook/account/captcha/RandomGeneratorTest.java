package com.pingjin.mvnbook.account.captcha;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RandomGeneratorTest {
	
	@Test
	public void testGetRandomString() throws Exception{
		Set<String> randoms=new HashSet<String>();
		for(int i=0;i<100;i++){
			String random=RandomGenerator.getRandomString();
			System.out.println(random);
			randoms.add(random);
		}
	}
}
