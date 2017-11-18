package org.wingame.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;

public class MailUtil {
	private static class MyAuthenticator extends Authenticator {
		private String name, password;

		public MyAuthenticator(String name, String password) {
			this.name = name;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(name, password);
		}
	}
	
	public static boolean SendMail(String receiver, String title, String context){
		try {
			// 第一步，设置SMTP服务器的信息
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.qq.com");
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");
			
			// 第二步，创建身份验证类
			String mailpassword = new String(Base64.decodeBase64("ZGpqc2R0YzE5OTIxMjEx"));
			Authenticator authenticator = new MyAuthenticator("358930328@qq.com",mailpassword);
			
			// 第三步，创建发送邮件类
			Session mailsession = Session.getInstance(props, authenticator);
			
			// 第四步，创建邮件主体类
			Message message = new MimeMessage(mailsession);
			message.addFrom(new Address[] { new InternetAddress("358930328@qq.com") });
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject(title);
			message.setSentDate(new Date());
			message.setText(context);
			
			// 第五步，发送邮件
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
