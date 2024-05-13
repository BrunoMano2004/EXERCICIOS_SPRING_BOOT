package med.voll.api.domain.pacienteModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.enderecoModel.DadosEndereco;
import med.voll.api.domain.enderecoModel.Endereco;

public record DadosCadastroPaciente(

        @NotBlank(message = "Nome não pode estar em branco!")
        String nome,

        @NotBlank(message = "Email não pode estar em branco!")
        @Email
        String email,

        @NotBlank(message = "Telefone não pode estar em branco!")
        String telefone,

        @NotBlank(message = "CPF não pode estar em branco!")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos!")
        String cpf,

        @NotNull
        @Valid
        DadosEndereco endereco
) {
}
