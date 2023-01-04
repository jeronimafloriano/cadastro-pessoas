package cadastros.controller;

import static cadastros.config.TestesConfig.objectToJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

import cadastros.domain.model.Pessoa;
import cadastros.domain.repository.PessoaRepository;
import cadastros.dto.PessoaDto;
import cadastros.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    @Mock
    PessoaService service;

    @Mock
    PessoaRepository repository;

    @InjectMocks
    PessoaController controller;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    public static final String PATH = "/pessoas";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @DisplayName("Testa a listagem de uma pessoa ao informar um ID existente.")
    @Test
    void deveListarPessoaPorId() throws Exception {
        //given
        Pessoa pessoa = new Pessoa("João", LocalDate.of(1956,8,02));
        given(service.listarPorId(3L)).willReturn(pessoa);

        //when
        mockMvc.perform(get(PATH + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarPorId(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(3);
    }

    @DisplayName("Testa a listagem de todas as pessoas cadastrados.")
    @Test
    void deveListarTodasAsPessoasCadastradas() throws Exception {
        //given
        Pessoa pessoa = new Pessoa("João", LocalDate.of(1956,8,02));
        Pageable paginacao = PageRequest.of(0,5, Sort.Direction.ASC, "id");
        given(service.listarTodos(paginacao)).willReturn(List.of(pessoa));

        //when
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pagina", "0")
                        .param("qtdItens", "5")
                        .param("ordenacao", "id"))
                .andExpect(status().isOk());

        //then
        then(service).should().listarTodos(paginacao);
        assertThat(service.listarTodos(paginacao)).contains(pessoa);
    }

    @DisplayName("Testa o cadastramento de uma pessoa.")
    @Test
    void deveCadastrarPessoa() throws Exception {
        PessoaDto dto = new PessoaDto("Paulo", LocalDate.of(1999, 03, 10));

        mockMvc.perform(request(HttpMethod.POST, PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        verify(service).cadastrar(any(PessoaDto.class));
    }

    @DisplayName("Testa a edição de uma pessoa cadastrada.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveEditarPessoa() throws Exception {
        // given
        Pessoa pessoa = new Pessoa("Jose", LocalDate.of(1999, 03, 10));
        PessoaDto dto = new PessoaDto("Paulo", LocalDate.of(1999, 03, 10));
        given(service.listarPorId(7L)).willReturn(pessoa);

        // when
        mockMvc.perform(put(PATH + "/7")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("name", pessoa.getNome())
                        .param("nascimento", "1999-03-10")
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        then(service).should().editar(longArgumentCaptor.capture(), any(PessoaDto.class));
        assertThat(longArgumentCaptor.getValue()).isEqualTo(7);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveListarPessoaPorFiltroInformado() throws Exception {
        //given
        Pessoa pessoa = new Pessoa("João", LocalDate.of(1956,8,02));

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(pessoa, matcher);

        given(service.buscarPor(pessoa)).willReturn(List.of(pessoa));

        //when
        mockMvc.perform(get(PATH + "/filtrar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        assertThat(service.buscarPor(pessoa)).contains(pessoa);
    }

}