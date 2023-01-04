package cadastros.dto;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PessoaDto {

    @NotNull(message = "Obrigatório informar o nome.")
    private String nome;

    @NotNull(message = "Obrigatório informar a data de nascimento.")
    private LocalDate nascimento;

    public PessoaDto(String nome, LocalDate nascimento) {
        this.nome = nome;
        this.nascimento = nascimento;
    }

    protected PessoaDto(){}

    public String getNome() {
        return nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }
}
