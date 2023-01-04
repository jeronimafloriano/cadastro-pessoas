package cadastros.dto;


import cadastros.domain.model.TipoEndereco;

import javax.validation.constraints.*;

public class CadastrarEnderecoDto {

    @NotEmpty(message = "Obrigatório informar o logradouro.")
    private String logradouro;

    @NotNull(message = "Obrigatório informar o cep.")
    private Integer cep;

    @NotNull(message = "Obrigatório informar o numero.")
    private Integer numero;

    @NotEmpty(message = "Obrigatório informar a cidade.")
    private String cidade;

    @NotNull(message = "Obrigatório informar se é o endereco principal para a pessoa cadastrada.")
    private TipoEndereco tipoEndereco;

    public CadastrarEnderecoDto(String logradouro, Integer cep, Integer numero, String cidade, TipoEndereco tipoEndereco) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tipoEndereco = tipoEndereco;
    }

    protected CadastrarEnderecoDto(){}

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

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }
}
