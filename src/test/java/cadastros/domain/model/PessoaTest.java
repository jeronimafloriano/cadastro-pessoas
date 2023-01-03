package cadastros.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PessoaTest {

    private final String nome = "Maria";
    private final LocalDate nascimento = LocalDate.of(1999,12,1);
    @Test
    void deveCriarPessoaComTodosOsCamposInformados(){
        Pessoa pessoa = new Pessoa(nome, nascimento);

        assertThat(pessoa.getNome()).isEqualTo(nome);
        assertThat(pessoa.getNascimento()).isEqualTo(nascimento);
    }

    @Test
    void deveCadastrarEnderecoParaPessoa() {
        Pessoa pessoa = new Pessoa(nome, nascimento);
        Endereco endereco = new Endereco();
        pessoa.cadastrarEndereco(endereco);

        assertThat(pessoa.getEnderecos()).contains(endereco);
    }

    @Test
    void deveAlterarOsCamposInformados(){
        Pessoa pessoa = new Pessoa(nome, nascimento);

        pessoa.setNome("Maria da Silva");
        pessoa.setNascimento(LocalDate.of(1980, 03, 25));

        assertThat(pessoa.getNome()).isEqualTo("Maria da Silva");
        assertThat(pessoa.getNascimento()).isEqualTo(LocalDate.of(1980, 03, 25));
    }
}