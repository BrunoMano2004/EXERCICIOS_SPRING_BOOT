package med.voll.api.domain.consultaModel.validacoesCancelamento;

import med.voll.api.domain.consultaModel.DadosCadastroCancelamento;
import med.voll.api.infra.exception.ValidacaoException;

public interface ValidadorCancelmentoDeConsulta {

    public void validar(DadosCadastroCancelamento dados) throws ValidacaoException;
}
