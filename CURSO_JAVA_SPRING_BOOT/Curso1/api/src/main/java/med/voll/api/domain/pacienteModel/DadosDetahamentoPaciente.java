package med.voll.api.domain.pacienteModel;

public record DadosDetahamentoPaciente(Long id, String nome, String email, String telefone, String cpf) {
    public DadosDetahamentoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
