package med.voll.api.domain.medicoModel;

import med.voll.api.domain.enderecoModel.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String Email, String telefone, String crm, Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(), medico.getEspecialidade(), medico.getEndereco());
    }
}
