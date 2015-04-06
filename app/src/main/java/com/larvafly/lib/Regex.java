package com.larvafly.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	public boolean idcheck(String id)
	{
		//6~15자 숫자+영문 첫글자 숫자 x

		//String regEx = "^[_a-zA-Z0-9-\\.]{6,15}";
		String regEx = "^[a-zA-Z0-9]{6,15}";
		return Pattern.matches(regEx, id);

	}
	
	public boolean phonenumbercheck(String phonenumber)
	{//[]
		//8~16 자 숫자+영문+특문 
		//String regEx = "^[_a-zA-Z0-9-\\.]{8,16}";
		String regEx = "^[0-9]{10,13}";
		return Pattern.matches(regEx, phonenumber);
	}
	
	public boolean pwdcheck(String pwd)
	{//[]
		//8~16 자 숫자+영문+특문 
		//String regEx = "^[_a-zA-Z0-9-\\.]{8,16}";
		String regEx = "^[a-zA-Z0-9`~!@#$%^&*()-\\]\\[_=+|{};:\\"+'"'+"]{8,16}";
		return Pattern.matches(regEx, pwd);
	}
	public boolean emailcheck(String email)
	{
		//숫자+영문 @ 영문.영문
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		boolean isNormal = m.matches();
		return isNormal;
	}
	
	public boolean namecheck(String nick)
	{
		//한글로2~10글자   한글+숫자+영어
		//String regEx = "^[_a-zA-Z0-9-\\.]{8,16}";
		String regEx = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,12}";
		return Pattern.matches(regEx, nick);
	}

}
