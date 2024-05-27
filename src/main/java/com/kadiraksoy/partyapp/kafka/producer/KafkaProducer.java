package com.kadiraksoy.partyapp.kafka.producer;

import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    //Kafka ile iletişim kurmak için kullanılan KafkaTemplate nesnesini enjekte eder.
    // Bu nesne, mesajları belirtilen konulara göndermek için kullanılır.
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_NAME = "party-create-topic";

    public void producePartyRequestDtoData(PartyRequestDto partyRequestDto) {
        kafkaTemplate.send(TOPIC_NAME, partyRequestDto);
        log.info("İşlem başarılı.");
    }



}