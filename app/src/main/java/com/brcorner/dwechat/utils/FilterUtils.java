package com.brcorner.dwechat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入过滤器
 */
public class FilterUtils {

	private FilterUtils()
	{
        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}
	/**
	 * 判断用户名是否有效
	 */
	public static int usernameFilter(String username) {
		if (username.length()>5&&username.length()<21) {
			String matchChar = "^[A-Za-z0-9@]{6,20}$";
			if (isPattern(matchChar, username)) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return -2;
		}

	}

	/**
	 *  判断密码是否有效
	 * @param password
	 * @return -1 长度不满足
	 * @return -0 含有非法字符
	 *  @return 1  有效密码
	 */
	public static int passwordFilter(String password) {
		// 长度是否满足 -1 长度不满足 -2含有非法字符
		Boolean lengthSuit = lengthJudge(password);
		if (!lengthSuit)
			return -1;
		// 是否含有特殊字符 0含有无效字符 1满足条件
		String matchCharPassword = "^[A-Za-z0-9]{6,20}$";
		if (isPattern(matchCharPassword, password))
			return 1;
		else
			return 0;
	}

	// 判断字符长度
	private static boolean lengthJudge(String s) {
		// 表示密码，6-20个字符
		if (s.length() > 5 && s.length() < 21) {
			return true;
		} else {
			return false;
		}
	}

	// 验证合法字符
	public static boolean isPattern(String rexp, String s) {
		Pattern p = Pattern.compile(rexp, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(s);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	//判断邮箱格式
public static Boolean isEmail(String mail){
	String matchCharEmail = "^[\u4E00-\u9FA5A-Za-z0-9_]+@[a-z0-9]{2,5}\\.[a-z]{2,3}$";
	if (isPattern(matchCharEmail, mail)) {
		return true;
	} else {
		return false;
	}
}

//判断手机格式
public static Boolean isPhone(String phone){
	if(phone != null)
	{
		phone = phone.replaceAll(" ","").trim();

		String matchCharPhone = "^[0-9]{11}$";
		if (isPattern(matchCharPhone, phone)) {
			return true;
		} else {
			return false;
		}
	}
	else
	{
		return false;
	}	
	
}

//判断验证码格式
public static Boolean isVerifyCode(String code){
	String matchCharPhone = "^[0-9]{6}$";
	if (isPattern(matchCharPhone, code)) {
		return true;
	} else {
		return false;
	}
}

}
