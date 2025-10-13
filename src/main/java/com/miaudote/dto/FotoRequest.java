package com.miaudote.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class FotoRequest {

    private Long animalId;
    private byte[] foto;
    private MultipartFile arquivo;

}
