package med.voll.api.controller;

import med.voll.api.domain.consultaModel.DadosAgendamentoConsulta;
import med.voll.api.domain.consultaModel.DadosDetalhamentoConsulta;
import med.voll.api.domain.medicoModel.Especialidade;
import med.voll.api.service.AgendamentoConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> jsonAgendamento;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> jsonDetalhamento;

    @MockBean
    private AgendamentoConsultaService agendamentoConsultaService;

    @Test
    @DisplayName("Deveria devolver código http 400 quando infomações estão inválidas")
    void agendarCenario1() throws Exception {
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200 quando infomações estão válidas")
    void agendarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.DERMATOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2L, 5L, data);

        when(agendamentoConsultaService.agendar(any())).thenReturn(dadosDetalhamento);

        var response = mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAgendamento.write(new DadosAgendamentoConsulta(2l, 5l, data, especialidade))
                                .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = jsonDetalhamento.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}