package com.kadiraksoy.partyapp.repository;

import com.kadiraksoy.partyapp.dto.request.RequestResponseDto;
import com.kadiraksoy.partyapp.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findByEmail(String email);
}
