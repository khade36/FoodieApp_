package com.user.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

public void sendOtpEmail(String emailId,String otp) throws MessagingException {
    MimeMessage mimeMessage=javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setTo(emailId);
    mimeMessageHelper.setSubject("Verify OTP");
    mimeMessageHelper.setText("Your OTP is" + otp);

//    mimeMessageHelper.setText("""
//<div>
//<a href="http://localhost:8080/verify-account?emailId=%s&otp=%s" target="_blank">click link to verify</a>
//</div>
//""".formatted(emailId,otp),true);

    javaMailSender.send(mimeMessage);
}
}
