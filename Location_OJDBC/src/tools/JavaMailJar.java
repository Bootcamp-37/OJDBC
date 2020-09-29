/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author ZFH
 */
public class JavaMailJar {
    public static void sendMail(String recepient, String subject, String content){
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        
        String fromEmail = "zakkyhermansyah@gmail.com";
        String fromPassword = "*****************";
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        });
        Message message = preparedMessage(session,fromEmail,recepient,subject,content);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(JavaMailJar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getRandom(){
        Random ran = new Random();
        int number = ran.nextInt(999999);
        return String.format("%06d", number);
    }
    
    private static Message preparedMessage(Session session, String email, String recepient, String subject, String content){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subject);
            message.setText(content);
            return message;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
    
}
