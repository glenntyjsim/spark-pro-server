// package com.example.login.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.JavaMailSenderImpl;

// import java.util.Properties;

// @Configuration
// public class MailConfig {

//     @Bean
//     public JavaMailSender javaMailSender() {
//         JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
//         // Set the properties for the mail sender
//         mailSender.setHost("smtp.gmail.com");
//         mailSender.setPort(587);
//         mailSender.setUsername(System.getenv("SMTP_USERNAME")); 
//         mailSender.setPassword(System.getenv("SMTP_PASSWORD"));
        
//         Properties props = mailSender.getJavaMailProperties();
//         props.put("mail.transport.protocol", "smtp");
//         props.put("mail.smtp.auth", "true");
//         props.put("mail.smtp.starttls.enable", "true");
//         props.put("mail.debug", "true");

//         return mailSender;
//     }
// }
