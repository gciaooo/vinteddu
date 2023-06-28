package it.unical.inf.gruppoea.vinteddu.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailManager {

    private final JavaMailSender javaMailSender;




    @Autowired
    public EmailManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(int n, String param, String to){
        EmailBuilder builder = new EmailBuilder();
        String body = builder.getTesto(n, param);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Vinteddu");
        message.setText(body);

        javaMailSender.send(message);

    }
}
