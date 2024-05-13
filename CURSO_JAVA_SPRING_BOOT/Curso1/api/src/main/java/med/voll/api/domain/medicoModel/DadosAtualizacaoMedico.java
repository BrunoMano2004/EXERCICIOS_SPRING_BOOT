package med.voll.api.domain.medicoModel;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enderecoModel.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
