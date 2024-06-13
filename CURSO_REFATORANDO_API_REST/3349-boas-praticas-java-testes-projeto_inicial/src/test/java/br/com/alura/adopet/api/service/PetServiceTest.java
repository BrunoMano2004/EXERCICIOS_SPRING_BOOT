package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void deveriaRetornarUmaListaDePetDTO(){
        CadastroPetDto cadastroPetDto1 = new CadastroPetDto(TipoPet.CACHORRO, "Rex", "Golden", 5, "Dourado", 10.22F);
        CadastroPetDto cadastroPetDto2 = new CadastroPetDto(TipoPet.GATO, "Matilda", "Golden", 4, "Preto", 5.00F);
        CadastroAbrigoDto cadastroAbrigoDto = new CadastroAbrigoDto("Abrigo bonito", "11979895501", "email@email.com");
        Abrigo abrigo = new Abrigo(cadastroAbrigoDto);
        Pet pet1 = new Pet(cadastroPetDto1, abrigo);
        Pet pet2 = new Pet(cadastroPetDto2, abrigo);

        List<Pet> pets = Arrays.asList(pet1, pet2);

        PetDto dto1 = new PetDto(pet1);
        PetDto dto2 = new PetDto(pet2);

        List<PetDto> dtos = Arrays.asList(dto1, dto2);

        when(petRepository.findAllByAdotadoFalse()).thenReturn(pets);

        assertEquals(dtos, petService.buscarPetsDisponiveis());
    }

    @Test
    void deveriaAcessarOMetodoSaveDoRepository(){
        CadastroPetDto cadastroPetDto = new CadastroPetDto(TipoPet.GATO, "Matilda", "Golden", 4, "Preto", 5.00F);
        CadastroAbrigoDto cadastroAbrigoDto = new CadastroAbrigoDto("Abrigo bonito", "11979895501", "email@email.com");
        Abrigo abrigo = new Abrigo(cadastroAbrigoDto);

        petService.cadastrarPet(abrigo, cadastroPetDto);

        then(petRepository).should().save(new Pet(cadastroPetDto, abrigo));
    }
}