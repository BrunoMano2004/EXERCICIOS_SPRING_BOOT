package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AbrigoController.class)
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private Abrigo abrigo;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Test
    void deveriaRetornar200SemErroAoChamarMetodoCadastrarAbrigo() throws Exception {
        String json = """
                    {
                        "nome" : "Abrigo",
                        "telefone" : "11979895501",
                        "email" : "anrigo@email.com"
                    }
                """;

       var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

       assertEquals(200, resp.getStatus());
    }

    @Test
    void deveriaRetornar400ComErroAoChamarMetodoCadastrarAbrigo() throws Exception {
        String json = "{}";

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, resp.getStatus());
    }

    @Test
    void listarPetsPorAbrigo() throws Exception {

        String nome = "Abrigo feliz";

        var resp = mvc.perform(
                get("/abrigos/{nome}/pets", nome))
                .andReturn().getResponse();

        assertEquals(200, resp.getStatus());
    }

    @Test
    void deveriaRetornarErro400ComJsonInvalido() throws Exception {

        String json = "{}";

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, resp.getStatus());
    }

    @Test
    void deveriaRetornarErro400ComNomeInvalido() throws Exception {

        String json = """
                    {
                        "nome" : "",
                        "telefone" : "11979895501",
                        "email" : "email@email.com"
                    }
                """;

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, resp.getStatus());
    }

    @Test
    void deveriaRetornarErro400ComEmailInvalido() throws Exception {

        String json = """
                    {
                        "nome" : "Abrigo",
                        "telefone" : "11979895501",
                        "email" : ""
                    }
                """;

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, resp.getStatus());
    }

    @Test
    void deveriaRetornarErro400ComTelefoneInvalido() throws Exception {

        String json = """
                    {
                        "nome" : "Abrigo",
                        "telefone" : "",
                        "email" : "email@email.com"
                    }
                """;

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, resp.getStatus());
    }

    @Test
    void deveriaRetornar200ComJsonValido() throws Exception{

        String json = """
                    {
                        "nome" : "Abrigo",
                        "telefone" : "11979895501",
                        "email" : "email@email.com"
                    }
                """;

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, resp.getStatus());
    }

    @Test
    void deveriaRetornarListaDeAbrigoDto() throws Exception {
        AbrigoDto dto1 = new AbrigoDto(new Abrigo(new CadastroAbrigoDto("Abrigo1", "11999999999", "email@email.com")));
        AbrigoDto dto2 = new AbrigoDto(new Abrigo(new CadastroAbrigoDto("Abrigo2", "11999999999", "email@email.com")));
        List<AbrigoDto> dtos = Arrays.asList(dto1, dto2);

        when(abrigoService.listar()).thenReturn(dtos);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(dtos);

        mvc.perform(
                get("/abrigos")
        ).andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void deveriaRetornarErro404QuandoAbrigoNaoEncontradoAoCadastrarPetNoAbrigo() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(TipoPet.CACHORRO, "cachorro", "raca", 10, "cor", 12.20F);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        when(abrigoService.carregarAbrigo("Abrigo Feliz")).thenThrow(ValidacaoException.class);

        mvc.perform(
                post("/abrigos/{idOuNome}/pets", "Abrigo Feliz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveriaRetornar200QuandoAbrigoAoCadastrarPetNoAbrigoSemErro() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(TipoPet.CACHORRO, "cachorro", "raca", 10, "cor", 12.20F);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                post("/abrigos/{idOuNome}/pets", "Abrigo Feliz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());
    }
}