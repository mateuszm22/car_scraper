package utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmailUtils {

    ResourceBundle bundle = ResourceBundle.getBundle("projectprivate");
    String sendToEmail = bundle.getString("sendToEmail");
    String sendFromEmail = bundle.getString("sendFromEmail");
    String sendFromPassword = bundle.getString("sendFromPassword");
    String name = bundle.getString("name");
    String xlsxFilePath = bundle.getString("xlsxFilePath");
    String host = "smtp.gmail.com";

    public void sendEmail() {

        // Setup mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendFromEmail, sendFromPassword);
            }
        });

        // Used sendToEmail debug SMTP issues
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendFromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(sendToEmail)
            );
            message.setSubject("Pojawiło się nowe ogłoszenie!");

            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            try {
                File excelFile =new File(xlsxFilePath);

                attachmentPart.attachFile(excelFile);
                textPart.setText(name + ","
                        + "\n\nPojawiło się nowe ogłoszenie samochodu, sprawdź:" + "\n\nLINK DO NOWEJ FURKI!"
                        + "\n\nW załączniku również wszystkie wyniki");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
