package com.foodie.orders.util;

import com.foodie.orders.domain.Order;
import com.foodie.orders.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class
EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

public void sendOrderConfirmationEmail(String emailId, Order order) throws MessagingException {
    MimeMessage mimeMessage=javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setTo(emailId);
    mimeMessageHelper.setSubject("Order Confirmation");
    mimeMessageHelper.setText("Your Order of items:\n" + order.getItem()+"\n is confirmed by Restaurant \n for a Address:\n"+order.getAddress().getUnitNo()+" " +order.getAddress().getBuildingName()+",\n"+order.getAddress().getStreet()+", near "+
                    order.getAddress().getLandMark()+",\n"+order.getAddress().getArea()+",\n"+order.getAddress().getCity()+" "+order.getAddress().getPinCode()+"\n"+"Thank you"+"\n"+"-Team Spicy Nutrient");
    javaMailSender.send(mimeMessage);
}

    public void sendOrderPlacedEmail(String ownerId, Order order) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(ownerId);
        mimeMessageHelper.setSubject("New Order");
        mimeMessageHelper.setText("New Order of \n" + order.getItem()+"\n is arrived and pending confirmation \n for a Address:\n"+order.getAddress().getUnitNo()+" " +order.getAddress().getBuildingName()+",\n"+order.getAddress().getStreet()+", near "+
                order.getAddress().getLandMark()+",\n"+order.getAddress().getArea()+",\n"+order.getAddress().getCity()+" "+order.getAddress().getPinCode()+"\n"+"Thank you"+"\n"+"-Team Spicy Nutrient");
        javaMailSender.send(mimeMessage);
    }


    public void sendOrderCanceledEmailToRestaurant(String ownerId, Order order) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(ownerId);
        mimeMessageHelper.setSubject("Order Canceled");
        mimeMessageHelper.setText("Order of items:\n" + order.getItem()+"\nfor a Address:\n"+order.getAddress().getUnitNo()+" " +order.getAddress().getBuildingName()+",\n"+order.getAddress().getStreet()+", near "+
                order.getAddress().getLandMark()+",\n"+order.getAddress().getArea()+",\n"+order.getAddress().getCity()+" "+order.getAddress().getPinCode()+"\n"+"has been canceled by customer."+"\n"+"Thank you"+"\n"+"-Team Spicy Nutrient");
        javaMailSender.send(mimeMessage);
    }

}
