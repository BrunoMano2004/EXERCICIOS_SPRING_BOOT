package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AdocaoRepositoryTest {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveriaRetornarNumeroDeAdocoesFeitasPorUmTutor(){
        Abrigo abrigo = cadastrarAbrigo("Abrigo Feliz", "11999999999", "email@email");
        Tutor tutor = cadastrarTutor("Tutor", "11888888888", "gmail@gmail.com");

        Pet pet1 = cadastrarPet(TipoPet.CACHORRO, "Rex", "Golden", 4, "Golden", 15.3F, abrigo);
        Pet pet2 = cadastrarPet(TipoPet.GATO, "Tigreza", "Preto", 6, "Preto", 8.00F, abrigo);
        cadastrarAdocao(pet1, tutor);
        cadastrarAdocao(pet2, tutor);

        assertEquals(2, repository.numeroDeAdocoesFeitasPorUmTutorComStatusAprovado(tutor.getId()));
    }

    private Tutor cadastrarTutor(String nome, String telefone, String email){
        Tutor tutor = new Tutor(new CadastroTutorDto(nome, telefone, email));
        em.persist(tutor);
        return tutor;
    }

    private Abrigo cadastrarAbrigo(String nome, String telefone, String email){
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(nome, telefone, email));
        em.persist(abrigo);
        return abrigo;
    }

    private Adocao cadastrarAdocao(Pet pet, Tutor tutor){
        Adocao adocao = new Adocao(tutor, pet, "motivo qualquer");
        adocao.setStatus(StatusAdocao.APROVADO);
        em.persist(adocao);
        return adocao;
    }

    private Pet cadastrarPet(TipoPet tipo, String nome, String raca, int idade, String cor, float peso, Abrigo abrigo){
        Pet pet = new Pet(new CadastroPetDto(tipo, nome, raca, idade, cor, peso), abrigo);
        em.persist(pet);
        return pet;
    }
}