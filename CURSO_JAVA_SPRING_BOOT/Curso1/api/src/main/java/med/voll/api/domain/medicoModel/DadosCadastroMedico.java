package med.voll.api.domain.medicoModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.enderecoModel.DadosEndereco;

public record DadosCadastroMedico(

        @NotBlank(message = "O nome não pode ser vazio!")
        String nome,

        @NotBlank(message = "O email não pode ser vazio!")
        @Email(message = "Deve estar no formato de email!")
        String email,

        @NotBlank(message = "O telefone não pode ser vazio!")
        String telefone,

        @NotBlank(message = "O crm não pode ser vazio!")
        @Pattern(regexp = "\\d{4,6}", message = "O crm deve conter entre 4 e 6 números")
        String crm,

        @NotNull(message = "A especialidade não pode ser vazio!")
        Especialidade especialidade,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}
