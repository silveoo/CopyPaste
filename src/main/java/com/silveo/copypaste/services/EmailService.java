package com.silveo.copypaste.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;

    public void sendTestEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("thevolkovmax@gmail.com");
        message.setSubject("Письмо тестовое");
        message.setText("Это тестовое письмо. Если оно пришло - значит все работает!");
        javaMailSender.send(message);
    }

    public void sendConfirmationEmail(String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Подтверждение электронной почты");
        message.setText("Для подтверждения электронной почты перейдите по ссылке: localhost:8080/api/v1/author/confirm/" + token);
        javaMailSender.send(message);
    }
}
