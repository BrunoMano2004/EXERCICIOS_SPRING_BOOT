package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.PetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @Test
    void deveriaRetornarTodosOsPetsDisponiveis() throws Exception {
        List<PetDto> pets = getPetDtos();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(pets);
        
        when(petService.buscarPetsDisponiveis()).thenReturn(pets);

        mvc.perform(
                get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(content().json(json))
                .andExpect(status().isOk());
    }

    private static List<PetDto> getPetDtos() {
        PetDto dto1;
        dto1 = new PetDto(
                new Pet(
                        new CadastroPetDto(TipoPet.CACHORRO, "cachorro", "raca", 10, "cor", 12.20F), new Abrigo(new CadastroAbrigoDto("Abrigo", "1199999999", "email@email.com"))));

        PetDto dto2;
        dto2 = new PetDto(
                new Pet(
                        new CadastroPetDto(TipoPet.CACHORRO, "cachorro", "raca", 10, "cor", 12.20F), new Abrigo(new CadastroAbrigoDto("Abrigo", "1199999999", "email@email.com"))));
        List<PetDto> pets = Arrays.asList(dto2, dto1);
        return pets;
    }

}