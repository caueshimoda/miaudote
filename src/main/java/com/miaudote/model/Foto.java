package com.miaudote.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="fotos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_animal", nullable = false)
    private Animal animal;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] foto;

    @CreationTimestamp
    @Column(name="data_cad", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

}
