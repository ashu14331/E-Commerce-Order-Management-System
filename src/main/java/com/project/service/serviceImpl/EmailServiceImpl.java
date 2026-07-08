package com.project.service.serviceImpl;

import com.project.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationEmail(String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Welcome to Our Platform – Registration Successful");
        simpleMailMessage.setText(
                "Welcome! Your account has been created successfully.\n" +
                        "You can now access our services.\n" +
                        "Best Regards,\n" +
                        "Support Team");
        javaMailSender.send(simpleMailMessage);
    }
}
