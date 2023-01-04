package cadastros.controller;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.dto.PessoaDto;
import cadastros.service.PessoaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Obter detalhes do cadastro de uma pessoa")
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Pessoa encontrada"),
            @ApiResponse(code  = 404, message = "Pessoa não encontrado para o ID informado")
    })
    public Pessoa listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @GetMapping(value = "/filtrar", produces = "application/json")
    @ApiOperation("Obter detalhes do cadastro de uma pessoa baseado no filtro informado.")
    @ApiResponses({
            @ApiResponse(code  = 200, message = "Pessoa encontrada"),
            @ApiResponse(code  = 404, message = "Pessoa não encontrado para o filtro informado")
    })
    public List<Pessoa> listarPorFiltro(Pessoa filtro){
        return service.buscarPor(filtro);
    }


    @GetMapping(produces = "application/json")
    @ApiOperation("Listar todas as pessoas cadastradas")
    public List<Pessoa> listarTodos(@RequestParam int pagina,
                                    @RequestParam int qtdItens, @RequestParam String ordenacao){

        Pageable paginacao = PageRequest.of(pagina,qtdItens, Sort.Direction.ASC, ordenacao);
        return service.listarTodos(paginacao);

    }

    @GetMapping(value = "/{id}/enderecos", produces = "application/json")
    @ApiOperation("Listar endereços cadastrados por pessoa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Endereco encontrado"),
            @ApiResponse(code = 404, message = "Endereco não encontrado para o ID da pessoa informada")
    })
    public List<Endereco>  listarEnderecos(@PathVariable Long id){
        return service.listarEnderecosPorPessoa(id);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa cadastrar(@RequestBody @Valid PessoaDto pessoaDto){
        return service.cadastrar(pessoaDto);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Editar o cadastro de uma pessoa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody PessoaDto pessoaDto){
        service.editar(id, pessoaDto);
    }


}
