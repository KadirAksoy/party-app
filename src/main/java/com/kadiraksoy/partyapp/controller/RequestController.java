package com.kadiraksoy.partyapp.controller;

import com.kadiraksoy.partyapp.dto.request.RequestDto;
import com.kadiraksoy.partyapp.dto.request.RequestResponseDto;
import com.kadiraksoy.partyapp.service.RequestService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/send")
    public ResponseEntity<RequestResponseDto> sendRequestToBeAdmin(@RequestBody RequestDto requestDto) {
        try {
            RequestResponseDto responseDto = requestService.sentRequestToBeAdmin(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/accept/{email}")
    public ResponseEntity<String> acceptToAdmin(@PathVariable String email) {
        try {
            String response = requestService.acceptToAdmin(email);
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("An error occurred while sending email.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        try {
            requestService.deleteRequest(id);
            return ResponseEntity.noContent().build();
        } catch (MessagingException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestResponseDto>> getAllRequests() {
        List<RequestResponseDto> requests = (List<RequestResponseDto>) requestService.getAll();
        return ResponseEntity.ok(requests);
    }

}
