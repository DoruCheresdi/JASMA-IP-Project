package com.example.jasmabackend.service.email;

import com.example.jasmabackend.exceptions.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String recipientEmail, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            // Tratați eroarea de trimitere a email-ului aici
            e.printStackTrace();

            System.out.println("Eroare la trimiterea email-ului către " + recipientEmail);
            e.printStackTrace();
            // Returnați un mesaj de eroare către utilizator
            throw new EmailSendingException("A apărut o eroare la trimiterea email-ului." +
                "Vă rugăm să încercați din nou mai târziu.");
        }
        javaMailSender.send(message);
    }
}
