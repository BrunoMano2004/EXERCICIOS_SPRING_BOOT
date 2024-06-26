package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.DadosListagemAbrigoDTO;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<DadosListagemAbrigoDTO> listar(){
        List<Abrigo> abrigos = abrigoRepository.findAll();
        List<DadosListagemAbrigoDTO> listaAbrigosDTO = abrigos.stream()
                .map(a -> new DadosListagemAbrigoDTO(a.getId(), a.getNome(), a.getTelefone(), a.getEmail()))
                .toList();
        return listaAbrigosDTO;
    }
}
