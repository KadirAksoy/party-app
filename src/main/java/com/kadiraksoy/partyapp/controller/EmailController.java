package com.kadiraksoy.partyapp.controller;

import com.kadiraksoy.partyapp.dto.email.EmailResponseDto;
import com.kadiraksoy.partyapp.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String email,
                                            @RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String message) {
        try {
            emailService.sendMail(email, firstName, lastName, message);
            return ResponseEntity.ok("Email sent successfully.");
        } catch (MessagingException e) {
            log.error("Error sending email: ", e);
            return ResponseEntity.status(500).body("Failed to send email.");
        }
    }

    @PostMapping("/send-to-all")
    public ResponseEntity<EmailResponseDto> sendEmailToAllUsers(@RequestParam String message) {
        EmailResponseDto responseDto = emailService.sendMailAllUsers(message);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/send-to-request")
    public ResponseEntity<String> sendEmailToRequest(@RequestParam String email) {
        try {
            emailService.sendMailToRequest(email);
            return ResponseEntity.ok("Request email sent successfully.");
        } catch (MessagingException e) {
            log.error("Error sending request email: ", e);
            return ResponseEntity.status(500).body("Failed to send request email.");
        }
    }

    @PostMapping("/send-to-admin")
    public ResponseEntity<String> sendEmailToAdmin(@RequestParam String email) {
        try {
            emailService.sendMailToAdmin(email);
            return ResponseEntity.ok("Admin email sent successfully.");
        } catch (MessagingException e) {
            log.error("Error sending admin email: ", e);
            return ResponseEntity.status(500).body("Failed to send admin email.");
        }
    }

    @PostMapping("/send-to-delete")
    public ResponseEntity<String> sendEmailToDelete(@RequestParam String email) {
        try {
            emailService.sendMailToDelete(email);
            return ResponseEntity.ok("Delete request email sent successfully.");
        } catch (MessagingException e) {
            log.error("Error sending delete request email: ", e);
            return ResponseEntity.status(500).body("Failed to send delete request email.");
        }
    }
}
