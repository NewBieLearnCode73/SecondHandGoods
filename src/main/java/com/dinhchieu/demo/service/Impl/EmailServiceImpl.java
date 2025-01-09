package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.service.EmailService;
import com.dinhchieu.demo.utils.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String EMAIL;


    @Override
    public void sendMessage(String from,String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            emailSender.send(message);
            System.out.println("Email sent successfully");
        }
        catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending email");
        }
    }

    @Override
    public void sendActivationEmail(String email, String token) {
        String Subject = "Activate your account";

        String activationUrl = Constants.FRONT_END_URL + "/active?email=" + email + "&token=" + token;

        String text = "<html><body>" +
                "Please using this token to activate for account with <" + email + ">:<br>" +
                "<b>" + token + "</b><br>" +
                "Or you can click to this link to activate your account:<br>" +
                "<a href=\"" + activationUrl + "\">Activation Acoount</a>" +
                "</body></html>";

        sendMessage(EMAIL, email, Subject, text);
    }
}
