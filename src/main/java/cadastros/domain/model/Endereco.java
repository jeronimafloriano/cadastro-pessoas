package cadastros.domain.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
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
    private List<Pessoa> pessoas = new ArrayList<>();

    private boolean isPrincipal;

    protected Endereco(){}

    private Endereco(String logradouro, final Integer cep, Integer numero, String cidade, boolean isPrincipal) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.isPrincipal = isPrincipal;


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

    public boolean isPrincipal() {
        return isPrincipal;
    }

    public void setPrincipal(boolean principal) {
        this.isPrincipal = principal;
    }

    public List<Pessoa> vincularPessoa(Pessoa pessoa){
        this.pessoas.add(pessoa);
        return pessoas;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
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
        private boolean isPrincipal;

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

        public EnderecoBuilder isPrincipal(final boolean isPrincipal){
            this.isPrincipal = isPrincipal;
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
                    Objects.requireNonNull(this.isPrincipal));
        }
    }

}
