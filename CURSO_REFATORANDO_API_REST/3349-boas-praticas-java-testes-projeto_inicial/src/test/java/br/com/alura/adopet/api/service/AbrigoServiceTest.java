package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    Abrigo abrigo;

    @Test
    void deveriaRetornarListaDeAbrigos(){
        CadastroAbrigoDto cadastroAbrigoDto1 = new CadastroAbrigoDto("Abrigo feliz", "11979895501", "email@email.com");
        CadastroAbrigoDto cadastroAbrigoDto2 = new CadastroAbrigoDto("Abrigo canino", "11979895501", "email@email.com");
        Abrigo abrigo1 = new Abrigo(cadastroAbrigoDto1);
        Abrigo abrigo2 = new Abrigo(cadastroAbrigoDto2);
        AbrigoDto dto1 = new AbrigoDto(abrigo1);
        AbrigoDto dto2 = new AbrigoDto(abrigo2);
        List<Abrigo> abrigos = Arrays.asList(abrigo1, abrigo2);
        List<AbrigoDto> dtos = Arrays.asList(dto1, dto2);

        when(repository.findAll()).thenReturn(abrigos);

        assertEquals(dtos, service.listar());
    }

    @Test
    void deveriaCairNaExcecaoDeValidacaoComNumeroOuEmailJaExistentes(){
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "11979895501", "email@email.com");

        when(repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).thenReturn(true);

       var exception = assertThrows(ValidacaoException.class, () -> service.cadatrar(dto));
       assertEquals("Dados já cadastrados para outro abrigo!", exception.getMessage());
    }

    @Test
    void naoDeveriaCairNaExcecaoDeValidacaoComNumeroEEmailNaoExistentes(){
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "11979895501", "email@email.com");

        when(repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).thenReturn(false);

        assertDoesNotThrow(() -> service.cadatrar(dto));
    }

    @Test
    void deveriaAcessarOMetodoSaveDoRepository(){
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "11979895501", "email@email.com");
        this.abrigo = new Abrigo(dto);

        when(repository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).thenReturn(false);

        service.cadatrar(dto);

        then(repository).should().save(abrigo);
    }

    @Test
    void deveriaRetornarUmaListaDePetsDoAbrigoInformadoPeloId(){
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "11979895501", "email@email.com");
        this.abrigo = new Abrigo(dto);

        CadastroPetDto cadastroPetDto1 = new CadastroPetDto(TipoPet.CACHORRO, "Rex", "Golden", 5, "Dourado", 10.22F);
        CadastroPetDto cadastroPetDto2 = new CadastroPetDto(TipoPet.GATO, "Matilda", "Golden", 4, "Preto", 5.00F);
        Pet pet1 = new Pet(cadastroPetDto1, abrigo);
        Pet pet2 = new Pet(cadastroPetDto2, abrigo);

        List<Pet> pets = Arrays.asList(pet1, pet2);

        PetDto dto1 = new PetDto(pet1);
        PetDto dto2 = new PetDto(pet2);

        List<PetDto> dtos = Arrays.asList(dto1, dto2);

        when(repository.findById(10L)).thenReturn(Optional.of(abrigo));
        when(petRepository.findByAbrigo(abrigo)).thenReturn(pets);

        assertEquals(dtos, service.listarPetsDoAbrigo("10"));
    }

    @Test
    void deveriaRetornarUmaListaDePetsDoAbrigoInformadoPeloNome(){
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "11979895501", "email@email.com");
        this.abrigo = new Abrigo(dto);

        CadastroPetDto cadastroPetDto1 = new CadastroPetDto(TipoPet.CACHORRO, "Rex", "Golden", 5, "Dourado", 10.22F);
        CadastroPetDto cadastroPetDto2 = new CadastroPetDto(TipoPet.GATO, "Matilda", "Golden", 4, "Preto", 5.00F);
        Pet pet1 = new Pet(cadastroPetDto1, abrigo);
        Pet pet2 = new Pet(cadastroPetDto2, abrigo);

        List<Pet> pets = Arrays.asList(pet1, pet2);

        PetDto dto1 = new PetDto(pet1);
        PetDto dto2 = new PetDto(pet2);

        List<PetDto> dtos = Arrays.asList(dto1, dto2);

        when(repository.findByNome("Abrigo feliz")).thenReturn(Optional.of(abrigo));
        when(petRepository.findByAbrigo(abrigo)).thenReturn(pets);

        assertEquals(dtos, service.listarPetsDoAbrigo("Abrigo feliz"));
    }

    @Test
    void naoDeveriaRetornarUmaListaDePetsComAbrigoNaoEncontrado(){

        when(repository.findByNome(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(ValidacaoException.class, () -> service.listarPetsDoAbrigo(""));
        assertEquals("Abrigo não encontrado", exception.getMessage());
    }
}