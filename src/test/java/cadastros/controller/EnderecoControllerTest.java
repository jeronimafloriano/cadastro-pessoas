package cadastros.controller;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.domain.model.TipoEndereco;
import cadastros.domain.repository.EnderecoRepository;
import cadastros.dto.CadastrarEnderecoDto;
import cadastros.dto.EditarEnderecoDto;
import cadastros.dto.EditarTipoEnderecoDto;
import cadastros.service.EnderecoService;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static cadastros.config.TestesConfig.objectToJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EnderecoControllerTest {

    @Mock
    EnderecoService service;

    @Mock
    EnderecoRepository repository;

    @Mock
    PessoaService pessoaService;

    @InjectMocks
    EnderecoController controller;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    List<Endereco> enderecos;

    public static final String PATH = "/enderecos";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Endereco endereco = Endereco.builder().logradouro("Av Castelo Branco")
                .cep(11111111)
                .numero(11111)
                .cidade("Goiania")
                .tipoEndereco(TipoEndereco.PRINCIPAL)
                .build();

        Endereco endereco2 = Endereco.builder().logradouro("Rua 10")
                .cep(2222222)
                .numero(3333)
                .cidade("Osasco")
                .tipoEndereco(TipoEndereco.SECUNDARIO)
                .build();

        this.enderecos = new ArrayList<>();
        enderecos.add(endereco);
        enderecos.add(endereco2);
    }

    @DisplayName("Testa a listagem de um endereço ao informar um ID existente.")
    @Test
    void deveListarEderecoPorId() throws Exception {
        //given
        Endereco endereco = this.enderecos.get(0);
        given(service.listarEnderecoPorId(3L)).willReturn(endereco);

        //when
        mockMvc.perform(get(PATH + "/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarEnderecoPorId(longArgumentCaptor.capture());
        assertThat(longArgumentCaptor.getValue()).isEqualTo(3);
    }

    @DisplayName("Testa a listagem de todos os endereços cadastrados.")
    @Test
    void deveListarTodosOsEnderecosCadastrados() throws Exception {
        //given
        Endereco endereco = this.enderecos.get(0);
        given(service.listarTodos()).willReturn(List.of(endereco));

        //when
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(service).should().listarTodos();
        assertThat(service.listarTodos()).contains(endereco);
    }

    @DisplayName("Testa o cadastramento de um endereço.")
    @Test
    void deveCadastrarEndereco() throws Exception {
        //given
        Pessoa pessoa = new Pessoa("Paulo", LocalDate.of(1999, 03, 10));

        Endereco endereco = this.enderecos.get(0);
        CadastrarEnderecoDto dto = new CadastrarEnderecoDto(endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(),
                endereco.getCidade(), endereco.getTipoEndereco());

        given(pessoaService.listarPorId(3L)).willReturn(pessoa);

        //when
        mockMvc.perform(request(HttpMethod.POST, PATH + "/pessoa/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        verify(service).cadastrar(any(Pessoa.class), any(CadastrarEnderecoDto.class));
    }

    @DisplayName("Testa a edição de um endereço cadastrado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveEditarEndereco() throws Exception {
        // given
        Pessoa pessoa = new Pessoa("Paulo", LocalDate.of(1999, 03, 10));
        Endereco endereco = this.enderecos.get(0);
        EditarEnderecoDto dto = new EditarEnderecoDto(endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(),
                endereco.getCidade());

        given(service.listarEnderecoPorId(7L)).willReturn(endereco);

        // when
        mockMvc.perform(put(PATH + "/7")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        then(service).should().editar(longArgumentCaptor.capture(), any(EditarEnderecoDto.class));
        assertThat(longArgumentCaptor.getValue()).isEqualTo(7);
    }

    @DisplayName("Testa a edição do tipo de um endereço cadastrado.")
    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void deveEditarTipoEndereco() throws Exception {
        // given
        Endereco endereco = this.enderecos.get(0);
        EditarTipoEnderecoDto dto = new EditarTipoEnderecoDto(endereco.getTipoEndereco());

        given(service.listarEnderecoPorId(8L)).willReturn(endereco);

        // when
        mockMvc.perform(put(PATH + "/8/pessoa/1/tipoEndereco")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectToJson(dto)))
                .andExpect(status().is2xxSuccessful());

        //then
        then(service).should().editarTipo(longArgumentCaptor.capture(), anyLong(), any(EditarTipoEnderecoDto.class));
        assertThat(longArgumentCaptor.getValue()).isEqualTo(8);
    }

}