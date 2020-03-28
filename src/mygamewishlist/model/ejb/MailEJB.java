package mygamewishlist.model.ejb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import bomboshtml.body.table.Tr;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.db.WishListGame;

/**
 * @author Patryk
 *
 * Clase que sirve como puente para enviar correos a usuarios o al administrador
 */
@Stateless
@LocalBean
public class MailEJB {
	
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final int PORT = 587;
	private static final String USERNAME = "bumboxowatosciowowaty@gmail.com";
	private static final String PASSWORD = "JotDe100";
	private static final String HOST = "smtp.gmail.com";
	
	public MailEJB() {}

	/**
	 * Envia un correo con el código de verificacion al usuario, y devuelve
	 * el código.
	 * 
	 * @return String código de verificacion
	 * @throws MessagingException 
	 */
	public void sendMailItemsOnSale(String destination, Hashtable<Integer, ScrapedGame> toSend) throws MessagingException {
		Session session = createMailSession();
		
		Message messageContent = new MimeMessage(session);
		messageContent.setFrom(new InternetAddress(USERNAME));
		messageContent.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
		messageContent.setSubject("An item from your wishlist is on sale");

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		
		mimeBodyPart.setContent(generateMessage(toSend), "text/html");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
		
		messageContent.setContent(multipart);
		
		Transport.send(messageContent);
	}
	
	private Session createMailSession() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", HOST);
		prop.put("mail.smtp.port", PORT);
		prop.put("mail.smtp.ssl.trust", HOST);

		// Abro la session del correo del cual voy a mandar correos
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		
		return session;
	}

	private String generateMessage(Hashtable<Integer, ScrapedGame> toSend) {
		StringBuilder sb = new StringBuilder();
		
		Tr th = new Tr();
		th.addTd("");
		th.addTd("Name");
		th.addTd("Store");
		th.addTd("Current Price");
		th.addTd("Current Discount");
		th.addTd("Default Price");
		
		for (Integer i : toSend.keySet()) {
			ScrapedGame sg = toSend.get(i);
			
			Tr tr = new Tr();
			tr.addTd(sg.getImg());
			tr.addTd(sg.getFullName());
			tr.addTd(sg.getUrl());
			
			sb.append("Some of the items from your wishlist are on sale!");
				
		}
		
		return sb.toString();
	}
}
