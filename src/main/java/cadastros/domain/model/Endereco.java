package cadastros.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 8)
    private Integer cep;

    @Column(length = 6)
    private Integer numero;

    @Column(length = 100)
    private String cidade;

    @ManyToMany
    @JsonIgnore
    private List<Pessoa> pessoas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TipoEndereco tipoEndereco;

    protected Endereco(){}

    private Endereco(String logradouro, final Integer cep, Integer numero, String cidade, TipoEndereco tipoEndereco) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tipoEndereco = tipoEndereco;
    }

    public static EnderecoBuilder builder(){
        return new EnderecoBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public List<Pessoa> vincularPessoa(Pessoa pessoa){
        this.pessoas.add(pessoa);
        return pessoas;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    @Override
    public String toString() {
        return " ID do endereço: " + this.id
                + " Logradouro: " + this.logradouro
                + " CEP: " + this.cep
                + " Numero: " + this.numero
                + "Cidade: " + this.cidade;
    }

    public static class EnderecoBuilder {

        private String logradouro;
        private Integer cep;

        private Integer numero;
        private String cidade;
        private TipoEndereco tipoEndereco;

        EnderecoBuilder (){}

        public EnderecoBuilder logradouro(final String logradouro){
            this.logradouro = logradouro;
            return this;
        }

        public EnderecoBuilder cep(final Integer cep){
            this.cep = cep;
            return this;
        }

        public EnderecoBuilder numero(final Integer numero){
            this.numero = numero;
            return this;
        }

        public EnderecoBuilder cidade(final String cidade){
            this.cidade = cidade;
            return this;
        }

        public EnderecoBuilder tipoEndereco(final TipoEndereco tipoEndereco){
            this.tipoEndereco = tipoEndereco;
            return this;
        }

        @Override
        public String toString() {
            return " EnderecoBuilder: "
                    + " Logradouro: " + this.logradouro
                    + " CEP: " + this.cep
                    + " Numero: " + this.numero
                    + "Cidade: " + this.cidade;
        }

        public Endereco build() {
            return new Endereco(
                    Objects.requireNonNull(this.logradouro),
                    Objects.requireNonNull(this.cep),
                    Objects.requireNonNull(this.numero),
                    Objects.requireNonNull(this.cidade),
                    Objects.requireNonNull(this.tipoEndereco));
        }
    }

}
