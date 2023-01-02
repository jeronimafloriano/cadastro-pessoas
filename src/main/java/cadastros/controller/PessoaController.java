package cadastros.controller;

import cadastros.domain.model.Pessoa;
import cadastros.dto.PessoaDto;
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
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping(value = "/listar/{id}")
    @ApiOperation("Obter detalhes do cadastro de uma pessoa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pessoa encontrada"),
            @ApiResponse(code = 404, message = "Pessoa não encontrado para o ID informado")
    })
    public Pessoa listarPorId(@PathVariable Long id){
        return service.listarPorId(id);
    }

    @GetMapping("/listar/filtrar")
    @ApiOperation("Obter detalhes do cadastro de uma pessoa baseado no filtro informado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pessoa encontrada"),
            @ApiResponse(code = 404, message = "Pessoa não encontrado para o filtro informado")
    })
    public List<Pessoa> listarPorFiltro(Pessoa filtro){
        return service.buscarPor(filtro);
    }


    @GetMapping(value = "/listar")
    @ApiOperation("Listar todas as pessoas cadastradas")
    public List<Pessoa> listarTodos(){
        return service.listarTodos();
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa cadastrar(@RequestBody @Valid PessoaDto pessoaDto){
        return service.cadastrar(pessoaDto);
    }

    @PutMapping("/{id}")
    @ApiOperation("Editar o cadastro de uma pessoa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editar(@PathVariable Long id, @RequestBody PessoaDto pessoaDto){
        service.editar(id, pessoaDto);
    }


}
