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

import bomboshtml.body.A;
import bomboshtml.body.Img;
import bomboshtml.body.table.Tr;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SecretClass;
import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 *
 * Clase que sirve como puente para enviar correos a usuarios o al administrador
 */
@Stateless
@LocalBean
public class MailEJB {
	
	private static final MyLogger LOG = MyLogger.getLOG();
	private static final SecretClass SC = SecretClass.getSC();
	private static final int PORT = 587;
	private static final String USERNAME = "bumboxowatosciowowaty@gmail.com";
	private static final String HOST = "smtp.gmail.com";
	
	public MailEJB() {}

	/**
	 * Envia un correo con el código de verificacion al usuario, y devuelve
	 * el código.
	 * 
	 * @return String código de verificacion
	 * @throws MessagingException 
	 */
	public void sendMailItemsOnSale(User us, ArrayList<ScrapedGame> toSend) {
		Session session = createMailSession();

		try {
			Message messageContent = new MimeMessage(session);
			messageContent.setFrom(new InternetAddress(USERNAME));
			messageContent.setRecipients(Message.RecipientType.TO, InternetAddress.parse(us.getEmail()));
			messageContent.setSubject("An item from your wishlist is on sale");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			
	 		mimeBodyPart.setContent(generateMessage(toSend, us), "text/html;charset=utf-8");
	 		
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			messageContent.setContent(multipart);
			
			Transport.send(messageContent);
		} catch (MessagingException e) {
			LOG.logError(e.getMessage());
		}
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
				return new PasswordAuthentication(USERNAME, SC.mailPasswd);
			}
		});
		
		return session;
	}

	private String generateMessage(ArrayList<ScrapedGame> toSend, User us) {
		StringBuilder sb = new StringBuilder();

		sb.append("<h3>Hi ")
			.append(us.getName())
			.append(", some of the items from your wishlist are on sale!<h3>")
			.append("<table style='width: 100%; max-width: 100%; margin-bottom: 1rem;") 
			.append("background-color: transparent; border-collapse: collapse;") 
			.append("box-sizing: border-box; display: table; border-collapse: separate;") 
			.append("border-spacing: 2px; border-color: grey;'>");
		
		Tr th = new Tr();
		th.addTd("");
		th.addTd("Name");
		th.addTd("Store");
		th.addTd("Current Price");
		th.addTd("Current Discount");
		th.addTd("Default Price");
		
		sb.append(th.print());
		
		for (ScrapedGame sg : toSend) {
			
			Tr tr = new Tr();
			tr.addTd(new Img(sg.getImg(),sg.getFullName()));
			tr.addTd(new A(sg.getFullName(),sg.getUrl()));
			tr.addTd(sg.getStoreName());
			tr.addTd(sg.getCurrentPrice() + "€");
			tr.addTd(sg.getCurrentDiscount() + "%");
			tr.addTd(sg.getDefaultPrice() + "€");
				
			sb.append(tr.print());
		}
		
		return sb.append("</table>").toString();
	}
}
