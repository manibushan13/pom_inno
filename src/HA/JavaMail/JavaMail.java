package HA.JavaMail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import HA.Properties.logApp;

public class JavaMail {

	public static void main(String[] args) {

		// Recipient's email ID needs to be mentioned.
		String to = "mani.bushan814@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "mani.bushan814@gmail.com";
		final String username = "mani.bushan814@gmail.com";
		final String password = "mani@123";

		String host = "smtp.gmail.com";

		Properties props = new Properties(System.getProperties());
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
	    props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
        session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Testing Subject");

			// Now set the actual message
			message.setText("Hello, this is sample for to check send email using JavaMailAPI ");
			message.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(null, username, password);
			logApp.logger.info("Mail Connected");
			System.out.println("Mail Connected");

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
