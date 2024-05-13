package med.voll.api.domain.enderecoModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(

        @NotBlank(message = "O logradouro não pode ser vazio!")
        String logradouro,

        @NotBlank(message = "O bairro não pode ser vazio!")
        String bairro,

        @NotBlank(message = "O cep não pode ser vazio!")
        @Pattern(regexp = "\\d{8}", message = "O cep deve conter 8 dígitos!")
        String cep,
        String complemento,

        @NotBlank(message = "A cidade não pode ser vazio!")
        String cidade,

        @NotBlank(message = "O estado não pode ser vazio!")
        String uf,
        String numero) {
}
