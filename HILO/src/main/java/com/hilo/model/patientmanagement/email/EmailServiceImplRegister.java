package com.hilo.model.patientmanagement.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImplRegister {
  @Autowired
  private JavaMailSender javaMailSender;

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("hilo.result@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    javaMailSender.send(message);
  }
}
