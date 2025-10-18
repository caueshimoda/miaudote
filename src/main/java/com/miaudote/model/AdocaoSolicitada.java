package com.miaudote.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @CreationTimestamp
    @Column(name="data_cad", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    public boolean isValidAdocaoSolicitada() {

        return isValidStatus();
    }

    public boolean isValidStatus() {

        if (status == null)
            return false;

        return StatusAdocao.isValidStatus(status);
    }


}
