package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @Mock
    private TutorRepository repository;

    @InjectMocks
    private TutorService service;

    @Mock
    private Tutor tutor;

    @Test
    void deveriaAcessarOMetodoCadastrarDoRepository(){
        CadastroTutorDto dto = new CadastroTutorDto("Bruno", "11979895501", "email@email.com");

        when(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).thenReturn(false);

        service.cadastrar(dto);

        then(repository).should().save(new Tutor(dto));
    }

    @Test
    void deveriaCairNaExcecaoDeValidacaoComEmailOuTelefoneJaExistentes(){
        CadastroTutorDto dto = new CadastroTutorDto("Bruno", "11979895501", "email@email.com");

        when(repository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).thenReturn(true);

        var exception = assertThrows(ValidacaoException.class, () -> service.cadastrar(dto));
        assertEquals("Dados já cadastrados para outro tutor!", exception.getMessage());
    }
}