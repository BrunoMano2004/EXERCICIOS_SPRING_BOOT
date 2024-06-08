package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private Abrigo abrigo;

    @MockBean
    private AbrigoService abrigoService;

    @Test
    void deveriaRetornar200SemErro() throws Exception {
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
    void deveriaRetornar400ComErro() throws Exception {
        String json = "{}";

        var resp = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, resp.getStatus());
    }

    @Test
    void listarPets() throws Exception {

        String nome = "Abrigo feliz";

        var resp = mvc.perform(
                get("/abrigos/{nome}/pets", nome))
                .andReturn().getResponse();

        assertEquals(200, resp.getStatus());
    }

    @Test
    void cadastrarPet() {
    }
}