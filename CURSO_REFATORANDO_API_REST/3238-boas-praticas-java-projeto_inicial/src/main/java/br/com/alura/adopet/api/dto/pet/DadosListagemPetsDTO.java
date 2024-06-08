package br.com.alura.adopet.api.dto.pet;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosListagemPetsDTO(
        Long id,

        TipoPet tipo,

        String nome,

        String raca,

        Integer idade) {

        public DadosListagemPetsDTO(Pet pet){
                this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade());
        }
}
