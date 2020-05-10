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
import bomboshtml.body.table.Tr;
import mygamewishlist.model.pojo.MyLogger;
import mygamewishlist.model.pojo.ScrapedGame;
import mygamewishlist.model.pojo.SecretClass;
import mygamewishlist.model.pojo.db.User;

/**
 * @author Patryk
 *
 * Class used for sending email to users
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
	 * Sends a mail to a user, user's email is stored in the User object,
	 * then mail's body is constructed with the toSend list.
	 * 
	 * @param us User
	 * @param toSend ArrayList<ScrapedGame>
	 */
	public void sendMailItemsOnSale(User us, ArrayList<ScrapedGame> toSend) {
		Session session = createMailSession();

		try {
			// create message
			Message messageContent = new MimeMessage(session);
			messageContent.setFrom(new InternetAddress(USERNAME));
			messageContent.setRecipients(Message.RecipientType.TO, InternetAddress.parse(us.getEmail()));
			messageContent.setSubject("An item from your wishlist is on sale");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			
			// add content to the message
	 		mimeBodyPart.setContent(generateMessage(toSend, us), "text/html;charset=utf-8");
	 		
	 		// add body to multipart
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			// add multipart to message content
			messageContent.setContent(multipart);
			
			// send message
			Transport.send(messageContent);
		} catch (MessagingException e) {
			LOG.logError(e.getMessage());
		}
	}
	
	/**
	 * Function that creates and returns a session
	 * 
	 * @return Session
	 */
	private Session createMailSession() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", HOST);
		prop.put("mail.smtp.port", PORT);
		prop.put("mail.smtp.ssl.trust", HOST);

		// open session from which the email will be sent
		return Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, SC.mailPasswd);
			}
		});
	}

	/**
	 * Generates main part of the e-mail's message
	 * 
	 * @param toSend ArrayList<ScrapedGame>
	 * @param us User
	 * @return
	 */
	private String generateMessage(ArrayList<ScrapedGame> toSend, User us) {
		StringBuilder sb = new StringBuilder();

		String thC = "</th>";
		String thO = "<th>";
		
		// generate the title and table with some inline styles, and the th
		sb.append("<h3>Hi ")
			.append(us.getName())
			.append(", some of the items from your wishlist are on sale!</h3>")
			.append("<table style='")
			.append("width: 100%; max-width: 100%; margin-bottom: 1rem;") 
			.append("background-color: transparent; border-collapse: collapse;") 
			.append("box-sizing: border-box; display: table; border-collapse: separate;") 
			.append("border-spacing: 2px; border-color: grey;'")
			.append("<tr>")
			.append(thO)
			.append(thC)
			.append(thO)
			.append("Name")
			.append(thC)
			.append(thO)
			.append("Store")
			.append(thC)
			.append(thO)
			.append("Current Price")
			.append(thC)
			.append(thO)
			.append("Current Discount")
			.append(thC)
			.append(thO)
			.append("Default Price")
			.append(thC)
			.append("</tr>");
		
		// add content to the table
		for (ScrapedGame sg : toSend) {
			Tr tr = new Tr();
			tr.addTd("<img src='" + sg.getImg() + "' alt='" + sg.getFullName() + "' width='125'>");
			tr.addTd(new A(sg.getFullName(),sg.getUrlStore() + sg.getUrlGame()));
			tr.addTd(sg.getStoreName());
			tr.addTd(sg.getCurrentPrice() + "€");
			tr.addTd(sg.getCurrentDiscount() + "%");
			tr.addTd(sg.getDefaultPrice() + "€");
				
			sb.append(tr.print());
		}
		
		return sb.append("</table>").toString();
	}
}
