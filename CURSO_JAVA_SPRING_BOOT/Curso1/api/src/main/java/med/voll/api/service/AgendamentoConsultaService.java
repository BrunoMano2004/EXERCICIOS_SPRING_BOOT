package med.voll.api.service;

import med.voll.api.domain.consultaModel.Consulta;
import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.domain.consultaModel.DadosDetalhamentoConsulta;
import med.voll.api.domain.consultaModel.validacoesConsulta.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medicoModel.Medico;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoConsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) throws ValidacaoException {

        Medico medico;
        if (dados.idMedico() != null) {
            medico = medicoRepository.findById(dados.idMedico()).orElseThrow(() -> new ValidacaoException("Médico não encontrado!"));
        } else {
            medico = escolherMedico(dados);
        }

        validadores.forEach(v -> {
            try {
                v.validar(dados);
            } catch (ValidacaoException e) {
                throw new RuntimeException(e);
            }
        });

        var paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(() -> new ValidacaoException("Paciente não encontrado!"));

        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) throws ValidacaoException{
        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhida!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data()).orElseThrow(() -> new ValidacaoException("Nenhum médico disponível nesta data!"));
    }
}
