package com.miaudote.dto;
import com.miaudote.model.Animal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnimalResponseDTO {

    private Long id;

    private Long parceiroId;

    private String parceiroNome;

    private String especie;

    private String nome;

    private String sexo;

    private String porte;

    private String status;
    
    private int idadeInicial;

    private String obs;

    private String descricao;

    public AnimalResponseDTO(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.especie = animal.getEspecie();
        this.sexo = animal.getSexo().name();
        this.porte = animal.getPorte().name();
        this.status = animal.getStatus().name();
        this.idadeInicial = animal.getIdadeInicial();
        this.obs = animal.getObs();
        this.descricao = animal.getDescricao();

        if (animal.getParceiro() != null) {
            this.parceiroId = animal.getParceiro().getId();
            this.parceiroNome = animal.getParceiro().getUsuario().getNome(); 
        }
    }

}
