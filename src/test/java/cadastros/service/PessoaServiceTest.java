package cadastros.service;


import cadastros.domain.model.Pessoa;
import cadastros.domain.repository.PessoaRepository;
import cadastros.dto.PessoaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
import static org.mockito.Mockito.reset;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    PessoaRepository repository;

    @InjectMocks
    PessoaService service;

    List<Pessoa> pessoas;

    @BeforeEach
    void setUp(){
        Pessoa pessoa = new Pessoa("Paulo", LocalDate.of(1975, 01,06));
        Pessoa pessoa2 = new Pessoa("Maria", LocalDate.of(1986, 10,11));

        this.pessoas = new ArrayList<>();

        pessoas.add(pessoa);
        pessoas.add(pessoa2);
    }

    @DisplayName("Teste de listagem de pessoa por ID")
    @Test
    void deveListarPessoaPorIdAoBuscarIdExistente() {
        //given
        Pessoa pessoa = new Pessoa("João", LocalDate.of(1956,8,02));
        given(repository.findById(1L)).willReturn(Optional.of(pessoa));

        //when
        var result = service.listarPorId(1L);

        //then
        then(repository).should().findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(pessoa);
    }

    @Test
    void deveListarPessoaPorFiltroInformado() {
        //given
        Pessoa pessoa = this.pessoas.get(0);

       ExampleMatcher matcher = ExampleMatcher
               .matching()
               .withIgnoreCase()
               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

       Example example = Example.of(pessoa, matcher);

        given(repository.findAll(example)).willReturn(List.of(pessoa));

        //when
        List<Pessoa> result = service.buscarPor(pessoa);

        //then
        assertThat(result).hasSize(1);
        assertThat(result).contains(pessoa);
    }

    @DisplayName("Teste de listagem de pessoas por ID, buscando por um ID inexistente.")
    @Test
    void deveLancarExcecaoAoBuscarPessoaPorIdInexistente() {
        Executable criar = () -> service.listarPorId(2L);
        assertThrows(ResponseStatusException.class, criar);
    }

    @Test
    void deveListarTodasAsPessoasCadastradas() {
        //given
        given(repository.findAll()).willReturn(pessoas);

        //when
        List<Pessoa> result = service.listarTodos();

        //then
        then(repository).should(atLeastOnce()).findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void deveCadastrarPessoaAoInformarTodosOsCamposValidos() {
        //given
        var pessoa = this.pessoas.get(0);
        PessoaDto dto = new PessoaDto(pessoa.getNome(), pessoa.getNascimento());
        given(repository.save(any(Pessoa.class))).willReturn(pessoa);

        //when
        var result = service.cadastrar(dto);

        //then
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(pessoa);
    }

    @Test
    void deveEditarPessoaAoSelecionarPessoaExistente() {
        //given
        var pessoa = this.pessoas.get(0);
        PessoaDto dto = new PessoaDto("Marcos", LocalDate.of(1994, 01, 01));
        given(repository.findById(1L)).willReturn(Optional.of(pessoa));

        //when
        service.editar(1L, dto);

        //then
        then(repository).should().save(pessoa);
        then(repository).shouldHaveNoMoreInteractions();
        assertThat(pessoa.getNome()).isEqualTo("Marcos");
        assertThat(pessoa.getNascimento()).isEqualTo(LocalDate.of(1994, 01, 01));
    }
}