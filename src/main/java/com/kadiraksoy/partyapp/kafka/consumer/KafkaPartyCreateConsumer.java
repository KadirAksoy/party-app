package com.kadiraksoy.partyapp.kafka.consumer;

import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import com.kadiraksoy.partyapp.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class KafkaPartyCreateConsumer {

    private final EmailService emailService;

    public KafkaPartyCreateConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    // containerfactory eklenebilir hata durumunda
    @KafkaListener(topics = "party-create-topic", groupId = "party-create-consumer-group")
    public void consumeScriptData(PartyRequestDto partyRequestDto){
        emailService.sendMailAllUsers("Partiye davetlisiniz:"
                + partyRequestDto.getTitle()
                + partyRequestDto.getDescription()
                + partyRequestDto.getEventDate());
        log.info("mailler gönderildi.");
    }
}
