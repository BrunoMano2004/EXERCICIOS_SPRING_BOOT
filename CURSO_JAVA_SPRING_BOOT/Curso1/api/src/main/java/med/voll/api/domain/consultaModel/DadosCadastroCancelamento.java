package med.voll.api.domain.consultaModel;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroCancelamento(@NotNull Long idConsulta,@NotNull(message = "O motivo é obrigatório!") Motivo motivo) {
    public DadosCadastroCancelamento(ConsultaCancelada consulta){
        this(consulta.getConsulta().getId(), consulta.getMotivo());
    }
}
