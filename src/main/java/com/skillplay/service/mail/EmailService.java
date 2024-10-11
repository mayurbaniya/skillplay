package com.skillplay.service.mail;

import com.skillplay.utils.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmailId;



    public boolean sendEmail(
            String to,
            String subject,
            String body
    ){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        try{
            javaMailSender.send(message);
            return true;

        }catch (Exception ex){

            ex.printStackTrace();
            return false;

        }
    }


}
