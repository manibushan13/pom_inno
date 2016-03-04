package HA.JavaMail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import HA.Properties.logApp;

public class Mail_Sending {
	static String subject="Sample Mail";
	static String attachmentPath=System.getProperty("user.dir")+"\\test-output\\Execution_report20-01-2016_12-40-55"+".html";
	public static String to="mmulka@innominds.com",cc="mmulka@innominds.com";
	public static void main(String[] args) {
		triggerSendMail();

	}
	public static void triggerSendMail()
	{
		final String userName="mmulka@innominds.com";
		final String passWord= "Frant3ic";
		String host="smtp.office365.com";
		String port="587";
		String starttls="true";
		String auth="true";
		boolean debug=true;

		//Object Instantiation of a properties file.
		Properties props = new Properties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", host);

		if(!"".equals(port)){
			props.put("mail.smtp.port", port);
		}

		if(!"".equals(starttls)){
//			props.put("mail.smtp.starttls.enable",starttls);
			props.put("mail.smtp.auth", auth);
		}
		props.put("mail.smtp.ssl.enable", "false");

		if(debug){
			props.put("mail.smtp.debug", "true");
		}
		else{
			props.put("mail.smtp.debug", "false");
		}

		//			if(!"".equals(port)){
		//				props.put("mail.smtp.socketFactory.port", port);
		//			}

		//			if(!"".equals(fallback)){
		//				props.put("mail.smtp.socketFactory.fallback", "true");
		//			}
		

		try{
			// Get the Session object.
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, passWord);
				} 
			});
			session.setDebug(true);
			//Session session = Session.getDefaultInstance(props, null);
			MimeMessage msg = new MimeMessage(session);
			msg.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			msg.setFrom(new InternetAddress(userName));

			msg.addRecipients(Message.RecipientType.TO, to);
			//msg.addRecipients(Message.RecipientType.CC, cc);
			msg.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, passWord);
			logApp.logger.info("Mail Connected");
			System.out.println("Mail Connected");

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			logApp.logger.info("Mail Sent");
			System.out.println("Mail Sent");
		} 

		catch (Exception e){
			e.printStackTrace();
			logApp.logger.error(e);				
		}
	}
	
}