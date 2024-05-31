//package com.kadiraksoy.partyapp.kafka.producer;
//
//import com.kadiraksoy.partyapp.dto.kafka.JoinPartyDto;
//import com.kadiraksoy.partyapp.dto.party.PartyRequestDto;
//import com.kadiraksoy.partyapp.dto.user.UserResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class KafkaProducer {
//
//    //Kafka ile iletişim kurmak için kullanılan KafkaTemplate nesnesini enjekte eder.
//    // Bu nesne, mesajları belirtilen konulara göndermek için kullanılır.
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//    private static final String PARTY_CREATE_TOPIC = "party-create-topic";
//    private static final String PARTY_JOIN_TOPIC = "party-join-topic";
//
//    public void sendPartyCreateMessage(PartyRequestDto partyRequestDto) {
//        kafkaTemplate.send(PARTY_CREATE_TOPIC, partyRequestDto);
//        log.info("İşlem başarılı." + PARTY_CREATE_TOPIC);
//    }
//
//    public void sendPartyJoinMessage(JoinPartyDto joinPartyDto) {
//        kafkaTemplate.send(PARTY_JOIN_TOPIC, joinPartyDto);
//        log.info("İşlem başarılı." + PARTY_JOIN_TOPIC);
//    }
//
//
//
//}