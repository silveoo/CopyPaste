package com.silveo.copypaste.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;

    //forms a unique link with an uuid token generated in authorService, sending it by email
    public void sendConfirmationEmail(String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("CopyPaste: Подтверждение электронной почты");
        message.setText("Для подтверждения электронной почты перейдите по ссылке: http://localhost:8080/api/v1/author/confirm/" + token);
        javaMailSender.send(message);
    }

    //sends an email to pastes author
    public void sendNewCommentEmail(String email, String username, Long pasteId){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("CopyPaste - Новый комментарий к вашей записи!");
        message.setText("Пользователь " + username + " оставил(а) новый комментарий под вашей записью. \n" +
                        "Перейдите по ссылке, чтобы посмотреть: http://localhost:8080/api/v1/paste/" + pasteId);
        javaMailSender.send(message);
    }

    public void sendDeleteEmail(String email, String adminName, Long pasteId){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Copypaste - Ваша запись была удалена!");
        message.setText("Администратор " + adminName + " удалил(а) вашу запись http://localhost:8080/api/v1/paste/" + pasteId + "\n" +
                        "Если вы считаете, что вы не нарушали никаких правил - пожалуйста, обратитесь в тех. поддержку");
        javaMailSender.send(message);
    }
}
