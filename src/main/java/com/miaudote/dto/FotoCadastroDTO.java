package com.miaudote.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class FotoCadastroDTO {

    private Long animalId;
    private byte[] foto;
    private MultipartFile arquivo;

}
