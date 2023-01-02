package cadastros.dto;


import javax.validation.constraints.NotNull;
import java.util.Date;

public class PessoaDto {

    @NotNull(message = "Obrigatório informar o nome.")
    private String nome;

    @NotNull(message = "Obrigatório informar a data de nascimento.")
    private Date nascimento;

    public String getNome() {
        return nome;
    }

    public Date getNascimento() {
        return nascimento;
    }
}
