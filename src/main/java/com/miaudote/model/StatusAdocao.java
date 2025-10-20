package com.miaudote.model;

import java.util.Arrays;
import java.util.List;

public class StatusAdocao {

    public static final String EM_ANDAMENTO = "Em andamento"; 

    public static final String FINALIZADA_ADOCAO = "Finalizada com adoção";

    public static final String FINALIZADA_PARCEIRO = "Finalizada por desistência do parceiro";

    public static final String FINALIZADA_ADOTANTE = "Finalizada por desistência do adotante";

    public static boolean isAberta(String status) {

        return status.equals(EM_ANDAMENTO);
    
    }

    public static boolean isValidStatus(String status) {

        List<String> abertas = Arrays.asList(EM_ANDAMENTO, FINALIZADA_ADOCAO,
                                            FINALIZADA_PARCEIRO, FINALIZADA_ADOTANTE); 

        return abertas.contains(status);
    
    }
    
}
