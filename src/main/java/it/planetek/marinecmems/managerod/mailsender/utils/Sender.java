package it.planetek.marinecmems.managerod.mailsender.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
@Service
public class Sender {

    @Autowired
    private JavaMailSender javaMailSender;

    /***
     * Send mail to specified address, with provided message
     * @param toAddress the address the email will be sent to
     * @param message the message will be attached to sent mail
     * @param fromAddress the address the email will be sent from
     * @param subject the subject the mail will be sent with
     */
    public boolean sendMail(String fromAddress, String subject, String toAddress, String message) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(message, "text/html");
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setFrom(fromAddress);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }
}
