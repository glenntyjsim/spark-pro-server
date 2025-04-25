package com.example.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.login.model.User;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user, String token, String baseUrl) {
        String url = baseUrl + "/api/auth/verify?token=" + token;
        String subject = "Email Verification";
        String body = "Click the link to verify your email: " + url;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendNewPasswordEmail(User user, String newPassword) {
        String subject = "Your New Password";
        String body = "Here is your newly generated password: " + newPassword + "\n\n"
                    + "Please login and change it as soon as possible.\n\n"
                    + "Regards,\nSparkPro Team";
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendContactEmail(String name, String email, String message) {
        String subject = "New Contact Message from " + name;
        String body = "You have received a new message from: " + name + "\nEmail: " + email + "\n\nMessage:\n" + message;

        SimpleMailMessage contactMessage = new SimpleMailMessage();
        contactMessage.setTo("sparkpromailer@gmail.com");
        contactMessage.setSubject(subject);
        contactMessage.setText(body);
        mailSender.send(contactMessage);
    }
}