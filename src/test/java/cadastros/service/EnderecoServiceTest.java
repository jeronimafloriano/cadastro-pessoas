package cadastros.service;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.domain.model.TipoEndereco;
import cadastros.domain.repository.EnderecoRepository;
import cadastros.dto.CadastrarEnderecoDto;
import cadastros.dto.EditarEnderecoDto;
import cadastros.dto.EditarTipoEnderecoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    EnderecoRepository repository;

    @InjectMocks
    EnderecoService service;

    List<Endereco> enderecos;

    @BeforeEach
    void setUp(){
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

    @DisplayName("Teste de listagem de endereço por ID")
    @Test
    void deveListarEnderecoPorIdAoBuscarIdExistente() {
        //given
        Endereco endereco = Endereco.builder().logradouro("Av Castelo Branco")
                .cep(11111111)
                .numero(11111)
                .cidade("Goiania")
                .tipoEndereco(TipoEndereco.PRINCIPAL)
                .build();

        given(repository.findById(1L)).willReturn(Optional.of(endereco));

        //when
        var result = service.listarEnderecoPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull().isEqualTo(endereco);
    }

    @DisplayName("Teste de listagem de endereço por ID, buscando por um ID inexistente.")
    @Test
    void deveLancarExcecaoAoBuscarEnderecoPorIdInexistente() {
        Executable criar = () -> service.listarEnderecoPorId(2L);
        assertThrows(ResponseStatusException.class, criar);
    }

    @DisplayName("Testa o lançamento de exceção ao tentar cadastrar mais de um endereço como endereço principal")
    @Test
    void deveLancarExcecaoAoCadastrarMaisDeUmEnderecoPrincipal() {
        Pessoa pessoa = new Pessoa();
        pessoa.cadastrarEndereco(enderecos.get(0));

        CadastrarEnderecoDto dto = new CadastrarEnderecoDto("Rua 10", 74125896,
                312, "Natal", TipoEndereco.PRINCIPAL);


        Executable criar = () -> service.cadastrar(pessoa, dto);
        assertThrows(ResponseStatusException.class, criar);
    }


    @DisplayName("Testa o lançamento de exceção ao tentar cadastrar mais de um endereço como endereço principal")
    @Test
    void deveLancarExcecaoAoEditarEnderecoInformandoMaisDeUmEnderecoComoPrincipal() {
        Pessoa pessoa = new Pessoa();
        pessoa.cadastrarEndereco(enderecos.get(0));

        EditarTipoEnderecoDto dto = new EditarTipoEnderecoDto(TipoEndereco.PRINCIPAL);


        Executable criar = () -> service.editarTipo(1L, 2L, dto);
        assertThrows(ResponseStatusException.class, criar);
    }

    @DisplayName("Teste de listagem de endereço por ID de uma pessoa cadastrada e vinculada ao endereço.")
    @Test
    void deveListarEnderecoPorPessoaIdAoBuscarIdExistente() {
        //given
        var endereco = this.enderecos.get(0);
        given(repository.findByPessoasId(1L)).willReturn(enderecos);

        //when
        var result = service.listarEnderecoPorPessoaId(1L);

        //then
        then(repository).should(times(1)).findByPessoasId(1L);
        assertThat(result)
                .hasSize(2)
                .contains(endereco);
    }

    @DisplayName("Teste de listagem de endereços cadastrados.")
    @Test
    void deveListarTodosEnderecosCadastrados() {
        //given
        given(repository.findAll()).willReturn(enderecos);

        //when
        List<Endereco> result = service.listarTodos();

        //then
        then(repository).should(atLeastOnce()).findAll();
        assertThat(result).hasSize(2);
    }

    @DisplayName("Teste de cadastro de endereço.")
    @Test
    void deveCadastrarEnderecoAoInformarTodosOsCamposValidos() {
        //given
        var endereco = this.enderecos.get(0);
        CadastrarEnderecoDto dto = new CadastrarEnderecoDto(endereco.getLogradouro(), endereco.getCep(),
                                endereco.getNumero(),endereco.getCidade(), endereco.getTipoEndereco());
        Pessoa pessoa = new Pessoa();
        given(repository.save(any(Endereco.class))).willReturn(endereco);

        //when
        var result = service.cadastrar(pessoa, dto);

        //then
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).
                isNotNull()
                .isEqualTo(endereco);
    }

    @DisplayName("Teste de edição de um endereço existente.")
    @Test
    void deveEditarTipoDoEndereco() {
        //given
        var endereco = this.enderecos.get(0);
        EditarEnderecoDto dto = new EditarEnderecoDto(endereco.getLogradouro(), 752258990,
                14789,endereco.getCidade());

        given(repository.findById(1L)).willReturn(Optional.of(endereco));

        //when
        service.editar(1L, dto);

        //then
        then(repository).should().save(endereco);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(endereco.getCep()).isEqualTo(752258990);
        assertThat(endereco.getNumero()).isEqualTo(14789);
    }
}