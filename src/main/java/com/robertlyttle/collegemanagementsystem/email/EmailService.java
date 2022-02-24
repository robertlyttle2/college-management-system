package com.robertlyttle.collegemanagementsystem.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String email, String name, String username, String password) throws MessagingException {
        final String SUBJECT = "Password confirmation";
        final String MESSAGE_BODY =
                "Hi " + name + ", \n\nYour login details for the student portal are: \n\nUsername: " + username + "\nPassword: : " + password + "\n\n" +
                        "Please store these details in a safe place. If you have any issues logging in, please contact an administrator." +
                        "\n\nRegards,\nBelfast College IT Support";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("support@belfast-college.ac.uk");
            helper.setTo(email);
            helper.setSubject(SUBJECT);
            helper.setText(MESSAGE_BODY);
            mailSender.send(message);
            log.info("Password email sent");
        } catch (MessagingException exception) {
            log.info("Failed to send email");
            throw new MessagingException(exception.getMessage());
        }

    }

}
