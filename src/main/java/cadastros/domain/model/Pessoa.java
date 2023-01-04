package cadastros.domain.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
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
    @JsonFormat(pattern="yyyy-MM-dd", timezone="America/Sao_Paulo")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate nascimento;

    @ManyToMany(mappedBy = "pessoas")
    @JsonIgnore
    private List<Endereco> enderecos = new ArrayList<>();

    public Pessoa(String nome, LocalDate nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public Pessoa(){}

    public Pessoa(Long id, String nome, LocalDate nascimento) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<Endereco> cadastrarEndereco(Endereco endereco){
        var enderecoPrincipal = this.enderecos.stream().filter(e -> e.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)).count();
        if(endereco.getTipoEndereco().equals(TipoEndereco.PRINCIPAL) && enderecoPrincipal > 0){
            throw new RuntimeException("Já existe um endereço cadastrado como principal para a pessoa informada");
        }

        this.enderecos.add(endereco);
        return this.enderecos;
    }

    @Override
    public String toString() {
        return " ID da pessoa: " + this.id
                + " Nome: " + this.nome
                + " Data de Nascimento: " + this.nascimento;
    }

}
