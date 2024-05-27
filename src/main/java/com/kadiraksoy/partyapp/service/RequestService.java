package com.kadiraksoy.partyapp.service;


import com.kadiraksoy.partyapp.dto.request.RequestDto;
import com.kadiraksoy.partyapp.dto.request.RequestResponseDto;
import com.kadiraksoy.partyapp.mapper.request.RequestMapper;
import com.kadiraksoy.partyapp.model.request.Request;
import com.kadiraksoy.partyapp.model.user.Role;
import com.kadiraksoy.partyapp.model.user.User;
import com.kadiraksoy.partyapp.repository.RequestRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestService {

    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final EmailService emailService;
    private final UserService userService;

    public RequestService(RequestMapper requestMapper, RequestRepository requestRepository, EmailService emailService, UserService userService) {
        this.requestMapper = requestMapper;
        this.requestRepository = requestRepository;
        this.emailService = emailService;
        this.userService = userService;
    }


    public RequestResponseDto sentRequestToBeAdmin(RequestDto requestDto) throws MessagingException {
        Request request = requestMapper.dtoToEntity(requestDto);
        requestRepository.save(request);
        log.info("istek gönderildi.");

        emailService.sendMailToRequest(requestDto.getEmail());
        log.info("mail gönderildi.");

        return requestMapper.entityToResponseDto(request);
    }

    public String acceptToAdmin(String email) throws MessagingException {
        User user = userService.getUserByEmail(email);
        user.setRole(Role.ROLE_ADMIN);
        Request request = requestRepository.findByEmail(email);
        request.setAccept(true);
        userService.save(user);

        emailService.sendMailToAdmin(email);
        return "User admin yapıldı.";
    }


}
