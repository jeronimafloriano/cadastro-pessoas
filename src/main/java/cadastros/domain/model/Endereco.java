package cadastros.domain.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100)
    @NotEmpty(message = "Obrigatório informar o logradouro.")
    private String logradouro;

    @Column(length = 8)
    @NotEmpty(message = "Obrigatório informar o cep.")
    private Integer cep;
    @Column(length = 6)
    @NotEmpty(message = "Obrigatório informar o numero.")
    private Integer numero;

    @Column(length = 100)
    @NotEmpty(message = "Obrigatório informar a cidade.")
    private String cidade;

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
