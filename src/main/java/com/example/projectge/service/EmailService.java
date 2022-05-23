package com.example.projectge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String From,String toEmail,String subject,String body){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom(From);
        msg.setTo(toEmail);
        msg.setSubject(subject);
        msg.setText(body);

        javaMailSender.send(msg);

        System.out.println("Mail sent succesfully");
    }


}
