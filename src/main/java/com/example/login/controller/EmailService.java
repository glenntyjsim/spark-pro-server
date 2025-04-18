package com.example.login.controller;

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
        message.setTo(user.getEmail()); // assuming username is the email
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}