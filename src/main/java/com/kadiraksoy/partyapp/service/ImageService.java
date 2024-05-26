package com.kadiraksoy.partyapp.service;


import com.kadiraksoy.partyapp.dto.party.PartyResponseDto;
import com.kadiraksoy.partyapp.exception.PartyNotFoundException;
import com.kadiraksoy.partyapp.model.file.ImageData;
import com.kadiraksoy.partyapp.model.party.Party;
import com.kadiraksoy.partyapp.repository.ImageRepository;
import com.kadiraksoy.partyapp.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;
    private final PartyService partyService;

    public ImageService(ImageRepository imageRepository, PartyService partyService) {
        this.imageRepository = imageRepository;
        this.partyService = partyService;
    }

    @Transactional
    public ImageData saveImageData(MultipartFile file) throws IOException, PartyNotFoundException{
        byte[] image = ImageUtils.compressImage(file.getBytes());



        ImageData newImageData = ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(image)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();
        return imageRepository.save(newImageData);

    }

    @Transactional
    public ImageData updateImageData(Long id, MultipartFile file) throws IOException {
        ImageData imageDataToUpdate = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image data not found with id: " + id));



        byte[] compressedImage = ImageUtils.compressImage(file.getBytes());

        imageDataToUpdate.setName(file.getOriginalFilename());
        imageDataToUpdate.setType(file.getContentType());
        imageDataToUpdate.setImageData(compressedImage);

        imageDataToUpdate.setUpdatedTime(LocalDateTime.now());
        return imageRepository.save(imageDataToUpdate);



    }

    public void deleteImageData(Long id) {
        imageRepository.deleteById(id);
    }

    public byte[] getImageData(Long id) {
        var compressedImage = imageRepository.findById(id);
        return ImageUtils.decompressImage(compressedImage.orElseThrow().getImageData());
    }

}
