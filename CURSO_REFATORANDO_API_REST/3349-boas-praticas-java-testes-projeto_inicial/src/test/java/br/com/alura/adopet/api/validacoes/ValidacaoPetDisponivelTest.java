package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                7l,
                2l,
                "Motivo qualquer"
        );

        when(petRepository.getReferenceById(dto.idPet())).thenReturn(pet);
        when(pet.getAdotado()).thenReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                7l,
                2l,
                "Motivo qualquer"
        );

        when(petRepository.getReferenceById(dto.idPet())).thenReturn(pet);
        when(pet.getAdotado()).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}