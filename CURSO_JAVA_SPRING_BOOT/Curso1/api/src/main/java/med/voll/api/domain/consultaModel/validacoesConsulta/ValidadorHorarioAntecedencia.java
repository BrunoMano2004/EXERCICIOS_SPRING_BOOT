package med.voll.api.domain.consultaModel.validacoesConsulta;

import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados) throws ValidacaoException {

        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaMinutos < 30){
            throw new ValidacaoException("Consulta deve ser agendada com antecendia mínima de 30 minutos!");
        }
    }
}
