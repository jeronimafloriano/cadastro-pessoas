package cadastros.domain.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100)
    @NotEmpty(message = "Obrigatório informar o nome.")
    private String nome;

    @Column(length = 10)
    @NotEmpty(message = "Obrigatório informar o nome.")
    private Date nascimento;
    @ManyToOne  // ManyToOne Muitas Pessoa para um endereco
    private Endereco endereco;

    public Pessoa(String nome, Date nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public Pessoa(String nome, Date nascimento, Endereco endereco) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
