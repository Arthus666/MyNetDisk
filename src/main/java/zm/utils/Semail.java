package zm.utils;

import org.apache.commons.mail.HtmlEmail;

/**
 * @author Arthus
 */
public class Semail {

	public static boolean sendEmail(String emailaddress, int code){
		try {
			//不用更改
			HtmlEmail email = new HtmlEmail();
			//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
			email.setHostName("smtp.qq.com");
			email.setCharset("UTF-8");
			// 收件地址
			email.addTo(emailaddress);

			//此处填邮箱地址和用户名,用户名可以任意填写
			email.setFrom("1305663753@qq.com", "MyNetDisk");

			//此处填写邮箱地址和客户端授权码
			email.setAuthentication("1305663753@qq.com", "thsogqapjisciddi");

			//此处填写邮件名，邮件名可任意填写
			email.setSubject("注册验证");

			//此处填写邮件内容
			email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + '\n'+code);
 
			email.send();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
