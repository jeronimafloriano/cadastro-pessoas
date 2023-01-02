package cadastros.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(length = 100)
    @NotEmpty(message = "Obrigatório informar o logradouro.")
    private String logradouro;

    @Column(length = 8)
    @NotEmpty(message = "Obrigatório informar o cep.")
    @Size(min = 1, max = 8, message = "Endereco.cep.Tamanho")
    @Pattern(regexp = "[\\s]*[0-8]*[1-8]+", message="Informe apenas numeros")
    private Integer cep;
    @Column(length = 6)
    @NotEmpty(message = "Obrigatório informar o numero.")
    @Size(min = 1, max = 6, message = "Endereco.numero.Tamanho")
    @Pattern(regexp = "[\\s]*[0-6]*[1-6]+", message="Informe apenas numeros")
    private Integer numero;

    @Column(length = 100)
    @NotEmpty(message = "Obrigatório informar a cidade.")
    private String cidade;


    @ManyToMany
    private List<Pessoa> pessoas = new ArrayList<>();

    public Endereco(){}

    public Endereco(String logradouro, Integer cep, Integer numero, String cidade) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
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
}
