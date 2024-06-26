package com.kadiraksoy.partyapp.service;


import com.kadiraksoy.partyapp.dto.email.EmailResponseDto;
import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;

    public EmailService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    public void sendMail(String email,String firstName, String lastName, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Welcome" + firstName + lastName );
        mimeMessageHelper.setText("<div>" + message + "</div>", true);

        javaMailSender.send(mimeMessage);
        log.info("mail gönderildi.");
    }

    public EmailResponseDto sendMailAllUsers(String message) {

        List<UserResponseDto> userList = userService.getAll();

        List<String> emailList = new ArrayList<>();
        for (UserResponseDto user : userList) {
            try {
                if(user.getActive().equals("true")) {
                    sendMail(user.getEmail(), user.getFirstName(), user.getLastName(), message);
                    emailList.add(user.getEmail());
                }
            } catch (MessagingException e) {
                System.err.println("Mail gönderilirken bir hata oluştu: " + user.getEmail());
            }
        }
        EmailResponseDto emailResponseDto = new EmailResponseDto(emailList, message);
        log.info("Emails and message: " + emailResponseDto);
        return emailResponseDto;
    }

    public void sendMailToRequest(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Admin");
        mimeMessageHelper.setText("<div> Admin olma talebi başarılı bir şekilde gönderildi.</div>", true);

        javaMailSender.send(mimeMessage);
        log.info("mail gönderildi.");
    }

    public void sendMailToAdmin(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Admin");
        mimeMessageHelper.setText("<div> Tebrikler, Admin oldunuzz.</div>", true);

        javaMailSender.send(mimeMessage);
        log.info("mail gönderildi.");
    }

    public void sendMailToDelete(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("İstek");
        mimeMessageHelper.setText("<div> İsteğiniz onaylanmadı.</div>", true);

        javaMailSender.send(mimeMessage);
        log.info("mail gönderildi.");
    }




}
