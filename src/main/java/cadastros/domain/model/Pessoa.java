package cadastros.domain.model;


import io.swagger.v3.oas.annotations.tags.Tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import java.util.*;

@Entity
@Table(name = "pessoas")
@Tag(name = "API", description = "API REST CADASTRO DE PESSOAS")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    @Column(length = 10)
    private Date nascimento;

    @ManyToMany(mappedBy = "pessoas")
    private List<Endereco> enderecos = new ArrayList<>();

    public Pessoa(String nome, Date nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public List<Endereco> cadastrarEndereco(Endereco endereco){
        this.enderecos.add(endereco);
        return this.enderecos;
    }
}
