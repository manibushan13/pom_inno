package HA.Utilities;

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

import HA.TestAutomation.HATF_properties;

public  class mailGeneration {

	public static HATF_properties _properties = new HATF_properties();
	public static String to="",cc="mmulka@innominds.com",subject="",attachmentPath="";

	

	public static void sendMail(String mailType) throws Exception
	{
		subject="Core Regression Test Suite on "+generateSubject()+" Release";
		attachmentPath=HTMLPreparation.generateMail(mailType);
		triggerSendMail();
	}

	private static String generateSubject() {

		String buildVersion="Sample_Build";
		String release = null;

		release=buildVersion;
		return release;
	}

	public static void triggerSendMail()
	{
		final String userName="QAautomationrun@hostanalytics.com";
		final String passWord= "automation@123";
		String host="smtp.gmail.com";
		String port="465";
		String starttls="true";
		String auth="true";
		boolean debug=true;
		String socketFactoryClass="javax.net.ssl.SSLSocketFactory";
		String fallback="false";
		

		//Object Instantiation of a properties file.
		Properties props = new Properties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", host);
		
		
		if(!"".equals(port)){
			props.put("mail.smtp.port", port);
		}

		if(!"".equals(starttls)){
			props.put("mail.smtp.starttls.enable",starttls);
			props.put("mail.smtp.auth", auth);
		}

		if(debug){
			props.put("mail.smtp.debug", "true");
		}
		else{
			props.put("mail.smtp.debug", "false");
		}

		if(!"".equals(port)){
			props.put("mail.smtp.socketFactory.port", port);
		}

		if(!"".equals(socketFactoryClass)){
			props.put("mail.smtp.socketFactory.class",socketFactoryClass);
		}

		if(!"".equals(fallback)){
			props.put("mail.smtp.socketFactory.fallback", fallback);
		}

		try{
			
			// Get the Session object.
		      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		         protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(userName, passWord);
		         }
		      });
		      

//			Session session = Session.getDefaultInstance(props, null);
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
			msg.addRecipients(Message.RecipientType.CC, cc);
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, passWord);
			
			System.out.println("connected mail ");
			
			transport.sendMessage(msg, msg.getAllRecipients());
			
			transport.close();
//			mail.logger.info("Mail Sent");
			System.out.println("Mail Sent");
		} 

		catch (Exception e){
			e.printStackTrace();
//			mail.logger.error(e);				
		}
	}

	public static void setReceivers(String mailType)
	{
		
	}

	public static void setSubject(String mailType)
	{

	}


}
