package med.voll.api.domain.consultaModel.validacoes;

import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados) throws ValidacaoException;
}
