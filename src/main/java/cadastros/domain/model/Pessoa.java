package cadastros.domain.model;


import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "pessoas")
@Tag(name = "API", description = "API REST CADASTRO DE PESSOAS")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @NotEmpty(message = "Obrigatório informar o nome.")
    private String nome;

    @Column(length = 10)
    @NotNull(message = "Obrigatório informar o nome.")
    private Date nascimento;


    @ManyToMany(mappedBy = "pessoas")
    private List<Endereco> enderecos;

    public Pessoa(String nome, Date nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public Pessoa(String nome, Date nascimento, Endereco endereco) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.enderecos = new ArrayList<>();
        enderecos.add(endereco);
    }

    public Pessoa(){}
    public String getNome() {
        return nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
