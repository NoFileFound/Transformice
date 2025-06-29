package org.transformice.libraries;

// Imports
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import org.transformice.Application;

public final class JakartaMail {
    private static Session session;

    /**
     * Inits the smtp config.
     */
    public static void initSmtpConfig() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Application.getPropertiesInfo().email_info.smtpHost);
        props.put("mail.smtp.port", Application.getPropertiesInfo().email_info.smtpPort);

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Application.getPropertiesInfo().email_info.smtpUsername, Application.getPropertiesInfo().email_info.smtpPassword);
            }
        });
    }

    /**
     * Sends an email message.
     * @param toEmail The sender.
     * @param subject The email subject. (title).
     * @param messageBody The email content. (message)
     * @return Is sending the message successfull.
     */
    public static boolean sendMessage(String toEmail, String subject, String messageBody) {
        if (session == null) {
            return false;
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Application.getPropertiesInfo().email_info.smtpUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

            return true;
        } catch (MessagingException ignored) {
            return false;
        }
    }
}