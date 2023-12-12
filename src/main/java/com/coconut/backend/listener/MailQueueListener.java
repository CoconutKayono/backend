package com.coconut.backend.listener;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {
    @Resource
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String username;

    @RabbitHandler
    public void sendMailMessage(Map<String, Object> data) {
        String email = (String) data.get("email");
        Integer code = (Integer) data.get("code");
        String type = (String) data.get("type");
        SimpleMailMessage message = switch (type) {
            case "register" -> createMessage("欢迎注册博客网站",
                    "您的邮件注册验证码为" + code + "有效时间3分钟，为了保障您的安全，请勿向他人泄露验证码。", email);
            case "reset" -> createMessage("您的密码重置邮件",
                    "您好，您正在进行重置密码操作，验证码为" + code + "有效时间3分钟，如非本人操作，请无视。", email);
            default -> null;
        };
        if (message == null) return;
        javaMailSender.send(message);
    }

    private SimpleMailMessage createMessage(String subject, String text, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text);
        message.setTo(to);
        message.setFrom(username);
        return message;
    }
}
