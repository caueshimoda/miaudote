package com.miaudote.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Arrays;

@Entity
@Table(name="adocoes_solicitadas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdocaoSolicitada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_animal", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "id_adotante", nullable = false)
    private Adotante adotante;

    @Column(name = "status_adocao")
    private String status;
    
    @Column(name = "data_finalizacao")
    private LocalDate dataFinalizacao;

    public boolean isValidAdocaoSolicitada() {

        return isValidStatus() && isValidDataFinalizacao();
    }

    public boolean isValidStatus() {

        if (status == null)
            return false;

        List<String> validos = Arrays.asList("Em andamento", "Na fila", "Finalizada com adoção", 
                                            "Finalizada com adoção de terceiros", "Finalizada por desistência do parceiro", 
                                            "Finalizada por desistência do adotante"); 

        return validos.contains(status);
    }

    public boolean isValidDataFinalizacao() {
        if (dataFinalizacao == null) {
            if (status == null || status.substring(0, "Finalizada".length()).equals("Finalizada"))
                return false;
            return true;
        }

        return Validacao.isValidData(dataFinalizacao);
    }



}
