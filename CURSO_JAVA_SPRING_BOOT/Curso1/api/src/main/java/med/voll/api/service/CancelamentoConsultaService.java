package med.voll.api.service;

import med.voll.api.domain.consultaModel.ConsultaCancelada;
import med.voll.api.domain.consultaModel.DadosCadastroCancelamento;
import med.voll.api.domain.consultaModel.validacoesCancelamento.ValidadorCancelmentoDeConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaCanceladaRepository;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelamentoConsultaService {

    @Autowired
    private ConsultaCanceladaRepository consultaCanceladaRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorCancelmentoDeConsulta> validacoes;

    public DadosCadastroCancelamento cancelar(DadosCadastroCancelamento dados){
        var consulta = consultaRepository.findById(dados.idConsulta()).orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));

        validacoes.forEach(v -> {
            try {
                v.validar(dados);
            } catch (ValidacaoException e) {
                throw new RuntimeException(e);
            }
        });

        consultaCanceladaRepository.save(new ConsultaCancelada(null, consulta, dados.motivo()));

        return dados;
    }
}
