package com.kadiraksoy.partyapp.repository;

import com.kadiraksoy.partyapp.model.file.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageData, Long> {
}
