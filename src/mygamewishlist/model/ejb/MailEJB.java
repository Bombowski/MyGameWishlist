package mygamewishlist.model.ejb;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mygamewishlist.model.pojo.GameOnSale;
import mygamewishlist.model.pojo.MyLogger;

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
	 */
	public void sendMail(String destination, GameOnSale gos) {
		// envío el correo al usuario
//		sendMail(destination);
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
	
	/**
	 * EnvÃ­a un correo a destination, con el titulo que sera title, y mensaje
	 * que sera message, si message = "", se envÃ­ara una lista con usuarios
	 * nuevos y usuarios que se han dado de baja.
	 * 
	 * @param destination String email del destinatario
	 * @param title String titulo
	 * @param message String mensaje
	 */
	private void sendMail(String destination, String message) {
		try {
			Session session = createMailSession();
			
			// creo el correo, digo cual serÃ¡ el recipiente, destinatario, y tÃ­tulo
			Message messageContent = new MimeMessage(session);
			messageContent.setFrom(new InternetAddress(USERNAME));
			messageContent.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
			messageContent.setSubject("An item from your wishlist is on sale");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			
			// aÃ±ado el mensaje
			mimeBodyPart.setContent(message, "text/html");
			
			// aÃ±ado el MimeBodyPart al MultiPart
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			// aÃ±ado el Multipart al correo
			messageContent.setContent(multipart);
			
			// envio el correo
			Transport.send(messageContent);

		} catch (Exception e) {
			LOG.logError(e.getMessage());
		}
	}
}
