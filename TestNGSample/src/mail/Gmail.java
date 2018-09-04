package mail;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;

public class Gmail {

	@Functionality(module= {"login1"},story= {"12341"})
	public static void main(String[] args) throws MessagingException, IOException, ParseException {
		Gmail gmail = new Gmail();
		IMAPFolder folder = null;
		Store store = null;
		String subject = null;
		Flag flag = null;
		try {
			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imaps");

			Session session = Session.getDefaultInstance(props, null);

			store = session.getStore("imaps");
			store.connect("imap.googlemail.com", "summasumma923@gmail.com", "Summa923");

			folder = (IMAPFolder) store.getFolder("inbox"); // This doesn't work for other email account
			// folder = (IMAPFolder) store.getFolder("inbox"); This works for both email
			// account

			if (!folder.isOpen())
				folder.open(Folder.READ_WRITE);
			Message[] messages = folder.getMessages();
			System.out.println("No of Messages : " + folder.getMessageCount());
			System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
			System.out.println(messages.length);

			for (int i = messages.length - 1; i >= 0; i--) {

				System.out.println("*****************************************************************************");
				System.out.println("MESSAGE " + (i + 1) + ":");
				Message msg = messages[i];
				// System.out.println(msg.getMessageNumber());
				// Object String;
				// System.out.println(folder.getUID(msg)

				subject = msg.getSubject();

				System.out.println("Subject: " + subject);
				System.out.println("From: " + msg.getFrom()[0]);
				System.out.println("To: " + msg.getAllRecipients()[0]);
				System.out.println("Date: " + msg.getReceivedDate());
				System.out.println("Size: " + msg.getSize());
				System.out.println(msg.getFlags());
				// System.out.println("Body: \n"+ msg.ATTACHMENT);
				String mailBody = gmail.getTextFromMessage(msg);
				System.out.println("Body: \n" + mailBody);
				// System.out.println("Body: \n"+ msg.getInputStream().toString());
				// System.out.println(msg.getContentType());
				String status ="";
				try {
					 status = mailBody.split("Status:")[1].split("\\[")[0];
				}
				catch(Exception e) {
					
				}
				
				if (subject.contains("[JIRA]") && status.toLowerCase().contains("closed")) {
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
					// Date d = sdf.format(obj)parse(msg.getReceivedDate().toString());
					System.out.println("date******" + sdf.format(msg.getReceivedDate()));

					String contents = new String(Files.readAllBytes(Paths.get("C:\\Mail_Execute\\lastExecutedDate.txt")));
					System.out.println(contents);
					try {
						Date lastExecuted = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(contents.trim());
						String mailDateStr = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(msg.getReceivedDate());
						Date mailDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(mailDateStr.trim());
						if (mailDate.after(lastExecuted)) {
							String testCase_Execute = subject.split("\\)")[1].trim();
							System.out.println(testCase_Execute);
							
							PrintWriter writer = new PrintWriter("C:\\Mail_Execute\\lastExecutedDate.txt");
							writer.print(
									new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(msg.getReceivedDate()).toString());
							writer.close();
							
						}
					} catch (Exception e) {
						PrintWriter writer = new PrintWriter("C:\\Mail_Execute\\lastExecutedDate.txt");
						writer.print(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(msg.getReceivedDate()).toString());
						writer.close();
					}
					
				}
			}
		} finally {
			if (folder != null && folder.isOpen()) {
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}
		}

	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}