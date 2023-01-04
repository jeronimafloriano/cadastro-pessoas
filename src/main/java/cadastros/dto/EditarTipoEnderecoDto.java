package cadastros.dto;


import cadastros.domain.model.TipoEndereco;

import javax.validation.constraints.NotNull;

public class EditarTipoEnderecoDto {

    @NotNull
    private TipoEndereco tipoEndereco;

    public EditarTipoEnderecoDto(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    protected EditarTipoEnderecoDto(){}

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }
}
