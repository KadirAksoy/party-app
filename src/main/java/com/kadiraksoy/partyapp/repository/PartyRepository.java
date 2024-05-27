package com.kadiraksoy.partyapp.repository;

import com.kadiraksoy.partyapp.model.party.Party;
import jakarta.mail.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {


    Optional<Party> findByAdminId(Long adminId);
    Optional<List<Party>> findAllPartyById(Long adminId);
    List<Party> findByEventDateBeforeAndActiveTrue(Date now);
}
