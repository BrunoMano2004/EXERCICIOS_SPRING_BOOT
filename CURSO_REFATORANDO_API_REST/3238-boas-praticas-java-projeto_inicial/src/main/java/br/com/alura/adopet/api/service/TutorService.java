package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.AtualizacaoTutorDTO;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDTO;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.tutor.ValidacaoTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TutorService {

    @Autowired
    private List<ValidacaoTutor> validacoes;

    @Autowired
    private TutorRepository tutorRepository;

    public void cadastrar(CadastroTutorDTO dto){
        validacoes.forEach(v -> v.validar(dto));
        tutorRepository.save(new Tutor(dto));
    }

    public void atualizar(AtualizacaoTutorDTO dto){
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        tutor.atualizarTutor(dto);
    }
}
