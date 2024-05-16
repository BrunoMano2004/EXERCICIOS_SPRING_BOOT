package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.domain.consultaModel.DadosDetalhamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.service.AgendamentoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendamentoConsultaService agendamentoConsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) throws ValidacaoException {
        var dto = agendamentoConsultaService.agendar(dados);
        return ResponseEntity.ok(dto);
    }
}
