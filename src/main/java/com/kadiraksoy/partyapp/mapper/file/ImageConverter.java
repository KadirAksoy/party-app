//package com.kadiraksoy.partyapp.mapper.file;
//
//import com.kadiraksoy.partyapp.dto.file.ImageRequestDto;
//import com.kadiraksoy.partyapp.dto.file.ImageResponseDto;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//import java.awt.*;
//
//
//@Component
//public class ImageConverter {
//
//    private final ModelMapper modelMapper;
//
//    public ImageConverter(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//    public Image convertToEntity(ImageRequestDto imageRequestDTO) {
//        return modelMapper.map(imageRequestDTO, Image.class);
//    }
//
//    public ImageResponseDto convertToDTO(Image image) {
//        return modelMapper.map(image, ImageResponseDto.class);
//    }
//}
