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
			// ��һ��������SMTP����������Ϣ
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.qq.com");
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");
			
			// �ڶ��������������֤��
			String mailpassword = new String(Base64.decodeBase64("ZGpqc2R0YzE5OTIxMjEx"));
			Authenticator authenticator = new MyAuthenticator("358930328@qq.com",mailpassword);
			
			// �����������������ʼ���
			Session mailsession = Session.getInstance(props, authenticator);
			
			// ���Ĳ��������ʼ�������
			Message message = new MimeMessage(mailsession);
			message.addFrom(new Address[] { new InternetAddress("358930328@qq.com") });
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			message.setSubject(title);
			message.setSentDate(new Date());
			message.setText(context);
			
			// ���岽�������ʼ�
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
