package com.ryan.util;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ryan.models.Result;
import com.sun.mail.smtp.SMTPTransport;

public class PasswordResetGenerator {
	
	public static Result<String> sendResetCode(String email, Result<String> result) {
		Properties props = System.getProperties();
		props.put("mail.smtps.host", System.getenv("MAILGUN_SMTP_SERVER"));
		props.put("mail.smtps.auth", "true");
		
		Session session = Session.getInstance(props);
		
		try {
			String resetCode = generateResetCode();
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("support@java-react-todo.herokuapp.com"));
			InternetAddress[] addrs = InternetAddress.parse(email, false);
			msg.setRecipients(Message.RecipientType.TO, addrs);
			
			msg.setSubject("Password Reset");
			msg.setText(String.format("Your password reset code is: %s", resetCode));
			msg.setSentDate(new Date());
			
			SMTPTransport transport = (SMTPTransport) session.getTransport("smtps");
			
			transport.connect(System.getenv("MAILGUN_SMTP_SERVER"), System.getenv("MAILGUN_SMTP_LOGIN"), System.getenv("MAILGUN_SMTP_PASSWORD"));
			transport.sendMessage(msg, msg.getAllRecipients());
			
			result.setPayload(resetCode);
			
			transport.close();
		} catch (MessagingException e) {
			result.addMessage("email", e.getMessage());
		}
		
		return result;
	}
	
	private static String generateResetCode() {
		String resetCode = "";
		Random rand = new Random();
		for (int i=0; i<6; i++) {
			resetCode += rand.nextInt(10);
		}
		return resetCode;
	}
}