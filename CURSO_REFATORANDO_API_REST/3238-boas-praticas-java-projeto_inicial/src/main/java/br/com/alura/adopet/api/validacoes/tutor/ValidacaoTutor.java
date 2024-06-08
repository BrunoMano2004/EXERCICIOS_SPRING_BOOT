package br.com.alura.adopet.api.validacoes.tutor;

import br.com.alura.adopet.api.dto.tutor.CadastroTutorDTO;

public interface ValidacaoTutor {
    void validar(CadastroTutorDTO dto);
}
