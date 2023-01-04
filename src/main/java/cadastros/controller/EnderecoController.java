package cadastros.controller;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.dto.CadastrarEnderecoDto;
import cadastros.dto.EditarEnderecoDto;
import cadastros.dto.EditarTipoEnderecoDto;
import cadastros.service.EnderecoService;
import cadastros.service.PessoaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "{id}", produces = "application/json")
    @ApiOperation("Obter detalhes de um endereco")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pessoa encontrada"),
            @ApiResponse(code = 404, message = "Pessoa não encontrado para o ID informado")
    })
    public Endereco listarPorId(@PathVariable Long id){
        return service.listarEnderecoPorId(id);
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Listar todos os enderecos cadastrados")
    public List<Endereco> listarTodos(){
        return service.listarTodos();
    }

    @PostMapping(value = "/pessoa/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco cadastrar(@PathVariable Long id, @RequestBody @Valid CadastrarEnderecoDto enderecoDto){
        Pessoa pessoa = pessoaService.listarPorId(id);
        return service.cadastrar(pessoa, enderecoDto);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Editar o cadastro de uma pessoa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody EditarEnderecoDto enderecoDto){
        service.editar(id, enderecoDto);
    }

    @PutMapping(value = "/{id}/pessoa/{idPessoa}/tipoEndereco", produces = "application/json")
    @ApiOperation("Altera o tipo de endereço cadastrado para uma pessoa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editarTipoEndereco(@PathVariable Long id, @PathVariable Long idPessoa, @RequestBody EditarTipoEnderecoDto tipoDto){
        service.editarTipo(id, idPessoa, tipoDto);
    }

}
