package com.kadiraksoy.partyapp.service;


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

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ImageData saveImageData(MultipartFile file, Long partyId) throws IOException{
        byte[] image = ImageUtils.compressImage(file.getBytes());

        Party party = null;
        if(party == null){
            ImageData newImageData = ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(image)
                    .partyId(partyId)
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .build();

            return imageRepository.save(newImageData);
        }
        throw new PartyNotFoundException("Category not found with id:" + partyId);
    }

    @Transactional
    public ImageData updateImageData(Long id, MultipartFile file, Long partyId) throws IOException {
        ImageData imageDataToUpdate = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image data not found with id: " + id));


        Party party = null;
        if(party == null){
            byte[] compressedImage = ImageUtils.compressImage(file.getBytes());

            imageDataToUpdate.setName(file.getOriginalFilename());
            imageDataToUpdate.setType(file.getContentType());
            imageDataToUpdate.setImageData(compressedImage);
            imageDataToUpdate.setPartyId(partyId);
            imageDataToUpdate.setUpdatedTime(LocalDateTime.now());
            return imageRepository.save(imageDataToUpdate);
        }
        throw new PartyNotFoundException("Category not found with id:" + partyId);

    }

    public void deleteImageData(Long id) {
        imageRepository.deleteById(id);
    }

    public byte[] getImageData(Long id) {
        var compressedImage = imageRepository.findById(id);
        return ImageUtils.decompressImage(compressedImage.orElseThrow().getImageData());
    }

}
