package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.domain.consultaModel.DadosCadastroCancelamento;
import med.voll.api.domain.consultaModel.DadosDetalhamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.service.AgendamentoConsultaService;
import med.voll.api.service.CancelamentoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private CancelamentoConsultaService cancelamentoConsultaService;

    @Autowired
    private AgendamentoConsultaService agendamentoConsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) throws ValidacaoException {
        var dto = agendamentoConsultaService.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCadastroCancelamento dados) throws ValidacaoException{
        var dto = cancelamentoConsultaService.cancelar(dados);
        return ResponseEntity.ok(dto);
    }
}
