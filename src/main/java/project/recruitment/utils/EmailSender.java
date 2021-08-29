package project.recruitment.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class EmailSender
{
    public static void SendEmail(final String receiverAddress, final String title, final String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("EMAIL", "PASSWORD");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("emailsendervalidation@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress));
        msg.setSubject(title);
        msg.setContent(title, "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        Transport.send(msg);
    }
}
