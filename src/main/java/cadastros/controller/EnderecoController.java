package cadastros.controller;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.dto.EnderecoDto;
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
@RequestMapping(value = "/enderecos", consumes = "application/json")
public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "/listar/{id}")
    @ApiOperation("Obter detalhes de um endereco")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pessoa encontrada"),
            @ApiResponse(code = 404, message = "Pessoa não encontrado para o ID informado")
    })
    public Endereco listarPorId(@PathVariable Long id){
        return service.listarEnderecoPorId(id);
    }

    @GetMapping(value = "/listar/pessoa/{id}")
    @ApiOperation("Obter detalhes de um endereco")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereco encontrado"),
            @ApiResponse(code = 404, message = "Endereco não encontrado para o ID da pessoa informada")
    })
    public List<Endereco>  listarPorPessoaId(@PathVariable Long id){
        return service.listarEnderecoPorPessoaId(id);
    }


    @GetMapping(value = "/listar")
    @ApiOperation("Listar todos os enderecos cadastrados")
    public List<Endereco> listarTodos(){
        return service.listarTodos();
    }

    @PostMapping("/cadastrar/pessoa/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco cadastrar(@PathVariable Long id, @RequestBody @Valid EnderecoDto enderecoDto){
        Pessoa pessoa = pessoaService.listarPorId(id);
        return service.cadastrar(pessoa, enderecoDto);
    }

    @PutMapping("/{id}")
    @ApiOperation("Editar o cadastro de uma pessoa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody EnderecoDto enderecoDto){
        service.editar(id, enderecoDto);
    }

}
