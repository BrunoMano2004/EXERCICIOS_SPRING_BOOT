package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Test
    void naoDeveriaCausarExcecaoDeValidacaoComTutorComMenosDe5Adocoes(){
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10L, 10L, "motivo qualquer");
        when(adocaoRepository.numeroDeAdocoesFeitasPorUmTutorComStatusAprovado(dto.idTutor())).thenReturn(4);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void deveriaCausarExcecaoDeValidacaoComTutorComMaisDe5Adocoes(){
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10L, 10L, "motivo qualquer");
        when(adocaoRepository.numeroDeAdocoesFeitasPorUmTutorComStatusAprovado(dto.idTutor())).thenReturn(5);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
        assertEquals("Tutor chegou ao limite máximo de 5 adoções!", exception.getMessage());
    }
}