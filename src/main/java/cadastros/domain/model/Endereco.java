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

    public Endereco(){}

    public Endereco(String logradouro, Integer cep, Integer numero, String cidade, boolean isPrincipal) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.isPrincipal = isPrincipal;
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
}
