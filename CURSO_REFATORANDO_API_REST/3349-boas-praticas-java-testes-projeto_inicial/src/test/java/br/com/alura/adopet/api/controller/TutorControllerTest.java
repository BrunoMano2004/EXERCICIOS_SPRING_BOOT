package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TutorController.class)
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService tutorService;

    @Test
    void deveriaRetornarCodigo200AoCadastrarTutorSemErro() throws Exception{
        CadastroTutorDto dto = new CadastroTutorDto("Tutor", "11999999999", "email@email.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarTutorComErro() throws Exception{
        mvc.perform(
                post("/tutores")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarTutorComNomeInvalido() throws Exception{
        CadastroTutorDto dto = new CadastroTutorDto("", "11999999999", "email@email.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarTutorComTelefoneInvalido() throws Exception{
        CadastroTutorDto dto = new CadastroTutorDto("Tutor", "", "email@email.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarTutorComEmailInvalido() throws Exception{
        CadastroTutorDto dto = new CadastroTutorDto("Tutor", "11999999999", "");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo200AoAtualizarTutorSemErro() throws Exception{
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(10L, "Tutor", "11999999999", "email@email.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        var resp = mvc.perform(
                put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarCodigo400AoAtualizarTutorComErro() throws Exception{
        mvc.perform(
                put("/tutores")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo400AoAtualizarTutorComNomeInvalido() throws Exception{
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(10L, "Tutor", "11999999999", "");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo400AoAtualizarTutorComTelefoneInvalido() throws Exception{
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(10L, "Tutor", "11999999999", "");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarCodigo400AoAtualizarTutorComEmailInvalido() throws Exception{
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(10L, "Tutor", "11999999999", "");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        mvc.perform(
                put("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isBadRequest());
    }
}