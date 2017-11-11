package com.resonate;

import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;  
      
public class Mailer { 
    	
	static String host="smtp.gmail.com";  
	final static String user="resonate201@gmail.com";  
	final static String password="re201ate";  
	      
		
	public static boolean SendMail(String to, String subject, String body) {  
	  
		//Get the session object  
		Properties props = new Properties();  
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);  
		props.put("mail.smtp.auth", "true");  
		     
		Session session = Session.getDefaultInstance(props,  
		new javax.mail.Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(user,password);  
			}  
		});  
		  
		try {  
		MimeMessage message = new MimeMessage(session);  
		message.setFrom(new InternetAddress(user));  
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  
		message.setSubject(subject);  
		message.setContent(body,"text/html; charset=utf-8");  
		       
		//send the message  
		Transport.send(message);  
		return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}  
	}  
}  