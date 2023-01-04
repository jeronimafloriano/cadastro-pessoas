package cadastros.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditarEnderecoDto {

    @NotEmpty(message = "Obrigatório informar o logradouro.")
    private String logradouro;

    @NotNull(message = "Obrigatório informar o cep.")
    private Integer cep;

    @NotNull(message = "Obrigatório informar o numero.")
    private Integer numero;

    @NotEmpty(message = "Obrigatório informar a cidade.")
    private String cidade;

    public EditarEnderecoDto(String logradouro, Integer cep, Integer numero, String cidade) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
    }

    protected EditarEnderecoDto(){}

    public String getLogradouro() {
        return logradouro;
    }


    public Integer getCep() {
        return cep;
    }


    public Integer getNumero() {
        return numero;
    }


    public String getCidade() {
        return cidade;
    }

}
