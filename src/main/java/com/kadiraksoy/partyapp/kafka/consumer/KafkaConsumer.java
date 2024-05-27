package com.kadiraksoy.partyapp.kafka.consumer;

import com.kadiraksoy.partyapp.dto.kafka.JoinPartyDto;
import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import com.kadiraksoy.partyapp.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class KafkaConsumer {

    private final EmailService emailService;

    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    // containerfactory eklenebilir hata durumunda
    @KafkaListener(topics = "party-create-topic", groupId = "party-create-consumer-group")
    public void consumePartyCreateMessage(PartyRequestDto partyRequestDto){
        emailService.sendMailAllUsers("Partiye davetlisiniz:"
                + partyRequestDto.getTitle()
                + partyRequestDto.getDescription()
                + partyRequestDto.getEventDate());
        log.info("mailler gönderildi.");
    }

    @KafkaListener(topics = "party-join-topic", groupId = "party-join-consumer-group")
    public void consumePartyJoinMessage(JoinPartyDto joinPartyDto) throws MessagingException {
        emailService.sendMail(joinPartyDto.getEmail(),
                joinPartyDto.getFirstName(),
                joinPartyDto.getLastName(),
                "Partiye katıldınız.Parti bilgileri:"
                        + joinPartyDto.getTitle()
                        + joinPartyDto.getDescription()
                        + joinPartyDto.getEventDate());
        log.info("partiye katıldınız mesajı gönderildi.");
    }


}
