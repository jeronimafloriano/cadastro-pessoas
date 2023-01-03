package cadastros.service;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.domain.repository.EnderecoRepository;
import cadastros.dto.EnderecoDto;
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
                .isPrincipal(true)
                .build();

        Endereco endereco2 = Endereco.builder().logradouro("Rua 10")
                .cep(2222222)
                .numero(3333)
                .cidade("Osasco")
                .isPrincipal(false)
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
                .isPrincipal(true)
                .build();

        given(repository.findById(1L)).willReturn(Optional.of(endereco));

        //when
        var result = service.listarEnderecoPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(endereco);
    }

    @DisplayName("Teste de listagem de endereço por ID, buscando por um ID inexistente.")
    @Test
    void deveLancarExcecaoAoBuscarEnderecoPorIdInexistente() {
        Executable criar = () -> service.listarEnderecoPorId(2L);
        assertThrows(ResponseStatusException.class, criar);
    }

    @Test
    void deveListarEnderecoPorPessoaIdAoBuscarIdExistente() {
        //given
        var endereco = this.enderecos.get(0);
        given(repository.findByPessoasId(1L)).willReturn(enderecos);

        //when
        var result = service.listarEnderecoPorPessoaId(1L);

        //then
        then(repository).should(times(1)).findByPessoasId(1L);
        assertThat(result).hasSize(2);
        assertThat(result).contains(endereco);
    }

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

    @Test
    void deveCadastrarEnderecoAoInformarTodosOsCamposValidos() {
        //given
        var endereco = this.enderecos.get(0);
        EnderecoDto dto = new EnderecoDto(endereco.getLogradouro(), endereco.getCep(),
                                endereco.getNumero(),endereco.getCidade(), endereco.isPrincipal());
        Pessoa pessoa = new Pessoa();
        given(repository.save(any(Endereco.class))).willReturn(endereco);

        //when
        var result = service.cadastrar(pessoa, dto);

        //then
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(endereco);
    }

    @Test
    void deveEditarEnderecoAoSelecionarEnderecoExistente() {
        //given
        var endereco = this.enderecos.get(0);
        EnderecoDto dto = new EnderecoDto(endereco.getLogradouro(), 752258990,
                14789,endereco.getCidade(), endereco.isPrincipal());

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