package com.resonate;

import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;

import com.resonate.objects.User;  
      
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
	
	public static void UserJoinedEmail(User u, String to) {
		String subject = "Welcome to Resonate, " + u.getName() + "!";
		String body =   "<html>" +
						"<body style=\"margin:0; padding: 0;\">" +
						"<center>" +
						"<div style=\"background-color: #000000; width: 100%;\">" +
						"<img src=\"http://localhost:8080/Resonate/images/logo.png\" style=\"width: 300px;height: 158px;\" />" +
						"</div>" +
						"<br />" +
						"<h1>Welcome to Resonate!</h1>" +
						"<p>You've joined the biggest group of collaborative artists on the web!*<br />" +
						"Click <a href=\"http://localhost:8080/Resonate/login.jsp?authenticate=" + u.getUsername() +"\">Here</a>" +
						" to confirm your email and login." +
						"<br /><br />" +
						"If the link does not work, please copy \"http://localhost:8080/login.jsp?authenticate="+ u.getUsername() +"\"" +
						" and paste to your browser." +
						"</p>" +
						"<br /><br />" +
						"<p style=\"font-size: 8px\">*We have no data to prove this.</p>" +
						"</center>" +
						"</body>" +
						"</html>";
		SendMail(to, subject, body);
	}
}  