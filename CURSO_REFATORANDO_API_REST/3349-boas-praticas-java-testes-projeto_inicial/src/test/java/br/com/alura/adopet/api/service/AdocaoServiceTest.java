package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

    private AprovacaoAdocaoDto aprovacaoAdocaoDto;

    @Mock
    private Adocao adocao;

    private SolicitacaoAdocaoDto dto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @BeforeEach
    void setUp(){
        abrigo = mock(Abrigo.class);

        pet = mock(Pet.class);
        when(pet.getAbrigo()).thenReturn(abrigo);
    }

    @BeforeTestMethod()

    @Test
    void deveriaSalvarAdocaoAoSolicitar(){
        //ARRENGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
        when(petRepository.getReferenceById(dto.idPet())).thenReturn(pet);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);

        //ACT
        service.solicitar(dto);

        //ASSERT
        then(repository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        assertEquals(pet, adocaoSalva.getPet());
        assertEquals(tutor, adocaoSalva.getTutor());
        assertEquals(dto.motivo(), adocaoSalva.getMotivo());

    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar(){
        //ARRENGE
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
        when(petRepository.getReferenceById(dto.idPet())).thenReturn(pet);
        when(tutorRepository.getReferenceById(dto.idTutor())).thenReturn(tutor);

        validacoes.add(validador1);
        validacoes.add(validador2);

        //ACT
        service.solicitar(dto);

        //ASSERT
        then(validador1).should().validar(dto);
        then(validador2).should().validar(dto);
    }

    @Test
    void deveriaChamarMetodoEnviarEmailParaAprovacao(){
        this.aprovacaoAdocaoDto = new AprovacaoAdocaoDto(10L);
        when(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).thenReturn(adocao);
        when(adocao.getPet()).thenReturn(pet);
        when(pet.getAbrigo()).thenReturn(abrigo);
        when(adocao.getTutor()).thenReturn(tutor);
        when(adocao.getData()).thenReturn(LocalDateTime.of(2023, 6, 1, 12, 0));

        service.aprovar(aprovacaoAdocaoDto);

        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.");
    }

    @Test
    void deveriaChamarMetodoEnviarEmailParaReprovacao(){
        this.reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(10L, "motivo qualquer");
        when(repository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).thenReturn(adocao);
        when(adocao.getPet()).thenReturn(pet);
        when(pet.getAbrigo()).thenReturn(abrigo);
        when(adocao.getTutor()).thenReturn(tutor);
        when(adocao.getData()).thenReturn(LocalDateTime.of(2023, 6, 1, 12, 0));

        service.reprovar(reprovacaoAdocaoDto);

        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus());
    }

}