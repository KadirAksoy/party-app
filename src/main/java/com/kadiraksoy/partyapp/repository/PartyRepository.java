package com.kadiraksoy.partyapp.repository;

import com.kadiraksoy.partyapp.model.party.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
