package com.miaudote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdocaoSolicitadaRequest {

    private Long adotanteId;
    private Long animalId;

}
