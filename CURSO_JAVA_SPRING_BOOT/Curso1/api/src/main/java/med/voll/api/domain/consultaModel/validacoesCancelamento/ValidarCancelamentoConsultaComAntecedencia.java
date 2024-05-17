package med.voll.api.domain.consultaModel.validacoesCancelamento;

import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.domain.consultaModel.DadosCadastroCancelamento;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarCancelamentoConsultaComAntecedencia implements ValidadorCancelmentoDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCadastroCancelamento dados) throws ValidacaoException {
        var agora = LocalDateTime.now();
        var dataConsulta = consultaRepository.getReferenceById(dados.idConsulta()).getData();
        var diferenca = Duration.between(agora, dataConsulta).toHours();
        if (diferenca < 24){
            throw new ValidacaoException("A consulta deve ser desmarcada com 24 horas de antecedência!");
        }
    }
}
