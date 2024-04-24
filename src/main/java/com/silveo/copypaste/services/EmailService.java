package com.silveo.copypaste.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;
    public void sendConfirmationEmail(String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Подтверждение электронной почты");
        message.setText("Для подтверждения электронной почты перейдите по ссылке: http://localhost:8080/api/v1/author/confirm/" + token);
        javaMailSender.send(message);
    }
}
