package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoTutorComAdocaoEmAndamento validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Test
    void naoDeveriaCausarExcecaoDeValidacaoComTutorSemAdocaoEmAndamento(){
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10L, 10L, "motivo qualquer");
        when(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(false);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void deveriaCausarExcecaoDeValidacaoComTutorComAdocaoEmAndamento(){
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10L, 10L, "motivo qualquer");
        when(adocaoRepository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).thenReturn(true);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
        assertEquals("Tutor já possui outra adoção aguardando avaliação!", exception.getMessage());
    }
}