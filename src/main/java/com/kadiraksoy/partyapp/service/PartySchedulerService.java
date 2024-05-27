package com.kadiraksoy.partyapp.service;

import com.kadiraksoy.partyapp.model.party.Party;
import com.kadiraksoy.partyapp.repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PartySchedulerService {

    private final PartyRepository partyRepository;

    public PartySchedulerService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @Scheduled(cron = "0 0 * * * *") // Her saat başı çalışır
    public void deactivatePastParties() {
        Date now = new Date();
        List<Party> parties = partyRepository.findByEventDateBeforeAndActiveTrue(now);

        for (Party party : parties) {
            party.setActive(false);
            partyRepository.save(party);
            log.info("Party with id " + party.getId() + " has been deactivated.");
        }
    }
}
