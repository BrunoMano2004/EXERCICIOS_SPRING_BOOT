package br.com.alura.adopet.api.validacoes.tutor;

import br.com.alura.adopet.api.dto.tutor.CadastroTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTelefoneTutorJaCadastrado implements ValidacaoTutor {

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(CadastroTutorDTO dto){
        if (tutorRepository.existsByTelefone(dto.telefone())){
            throw new ValidacaoException("Telefone já cadastrado para outro tutor!");
        }
    }
}
