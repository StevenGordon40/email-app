import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailClient extends MailClient{

	// Senders Email
	private String userEmail;

	// Senders AppPW (Gmail)
	private String userPassword;

	private Session session;

	// Gmail SMTP
	String host = "smtp.gmail.com";

	// System Properties
	Properties props = System.getProperties();

	public GMailClient() {

		userEmail = App.userProperties.getProperty("email");
		userPassword = App.userProperties.getProperty("password");

		// Setup mail server
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");

		// Get the Session object. and pass username and password
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userEmail, userPassword);
			}
		});

		// Used to debug SMTP issues
		session.setDebug(true);
	}

	@Override
	public void sendMessage(String recipientEmail, String subject, String body) {

		try {
			// Create a default MimeMessage object
			MimeMessage message = new MimeMessage(session);

			// Set From: header field
			message.setFrom(new InternetAddress(userEmail));

			// Set To: header field
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			// Set Subject: header field
			message.setSubject(subject);

			message.setContent("<h1>" + body + "</h1>", "text/html");

			System.out.println("sending...");

			// Send the message
			Transport.send(message);
			System.out.println("Sent message successfully...");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
