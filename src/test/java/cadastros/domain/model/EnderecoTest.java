package cadastros.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    private final String logradouro = "Rua 10";
    private final Integer cep = 12345678;
    private final Integer numero = 12345;
    private final String cidade = "São Paulo";


    @DisplayName("Teste de cadastro de endereço, informando todos os campos necessários.")
    @Test
    void deveCriarEnderecoComTodosOsCamposInformados(){
        Endereco endereco = Endereco.builder().logradouro(logradouro)
                .cep(cep)
                .numero(numero)
                .cidade(cidade)
                .tipoEndereco(TipoEndereco.PRINCIPAL)
                .build();

        assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
        assertThat(endereco.getCep()).isEqualTo(cep);
        assertThat(endereco.getNumero()).isEqualTo(numero);
        assertThat(endereco.getCidade()).isEqualTo(cidade);
    }

    @DisplayName("Testa o lançamento de exceção ao tentar cadastrar endereço sem informar todos os campos necessários.")
    @Test
    void naoDeveCriarEnderecoSemTodosOsCamposInformados(){
        Executable criar = () -> Endereco.builder().logradouro(logradouro).build();
        assertThrows(NullPointerException.class, criar);
    }

    @DisplayName("Testa a vinculação de um endereço à uma pessoa.")
    @Test
    void deveVincularUmaPessoaAUmEndereco() {
        Endereco endereco = Endereco.builder().logradouro(logradouro)
                .cep(cep)
                .numero(numero)
                .cidade(cidade)
                .tipoEndereco(TipoEndereco.PRINCIPAL)
                .build();

        Pessoa pessoa = new Pessoa();
        endereco.vincularPessoa(pessoa);

        assertThat(endereco.getPessoas()).contains(pessoa);
    }

    @DisplayName("Testa a edição do cadastro de um endereço.")
    @Test
    void deveAlterarOsCamposInformados(){
        Endereco endereco = Endereco.builder().logradouro(logradouro)
                .cep(cep)
                .numero(numero)
                .cidade(cidade)
                .tipoEndereco(TipoEndereco.PRINCIPAL)
                .build();

        endereco.setLogradouro("Av Castelo Branco");
        endereco.setCep(555555);
        endereco.setNumero(444);
        endereco.setCidade("Goiania");
        endereco.setTipoEndereco(TipoEndereco.SECUNDARIO);

        assertThat(endereco.getLogradouro()).isEqualTo("Av Castelo Branco");
        assertThat(endereco.getCep()).isEqualTo(555555);
        assertThat(endereco.getNumero()).isEqualTo(444);
        assertThat(endereco.getCidade()).isEqualTo("Goiania");
        assertThat(endereco.getTipoEndereco()).isEqualTo(TipoEndereco.SECUNDARIO);
    }


}