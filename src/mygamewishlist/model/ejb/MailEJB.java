package mygamewishlist.model.ejb;

import java.util.ArrayList;
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
	public void sendMailItemsOnSale(String destination, ArrayList<WishListGame> games, String store) throws MessagingException {
		Session session = createMailSession();
		
		Message messageContent = new MimeMessage(session);
		messageContent.setFrom(new InternetAddress(USERNAME));
		messageContent.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
		messageContent.setSubject("An item from your wishlist is on sale");

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		
		mimeBodyPart.setContent(generateMessage(games, store), "text/html");
		
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

	private String generateMessage(ArrayList<WishListGame> games, String store) {
		StringBuilder sb = new StringBuilder();
		
		Tr th = new Tr();
		th.addTd("");
		th.addTd("Name");
		th.addTd("Store");
		th.addTd("Current Price");
		th.addTd("Current Discount");
		th.addTd("Default Price");
		
		for (WishListGame wlg : games) {
			Tr tr = new Tr();
			tr.addTd(wlg.getImg());
			tr.addTd(wlg.getName());
			tr.addTd(wlg.getUrlStore());
			
			sb.append("Some of the items from your wishlist are on sale!");
				
		}
		
		return sb.toString();
	}
}
