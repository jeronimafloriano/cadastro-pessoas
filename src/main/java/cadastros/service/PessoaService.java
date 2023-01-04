package cadastros.service;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.domain.repository.PessoaRepository;
import cadastros.dto.PessoaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class PessoaService {

    @Autowired
    PessoaRepository repository;

    public List<Pessoa> listarTodos(Pageable paginacao){
        Page<Pessoa> pessoas = repository.findAll(paginacao);
        return pessoas.stream().collect(toList());
    }

    public Pessoa listarPorId(Long id){
        return repository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pessoa não encontrada com o ID informado."));
    }

    public List<Endereco> listarEnderecosPorPessoa(Long id){
        Pessoa pessoa = this.listarPorId(id);
        return pessoa.getEnderecos();
    }

    public List<Pessoa> buscarPor(Pessoa filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example, Sort.by("id").ascending());
    }

    public Pessoa cadastrar(PessoaDto dto){
        Pessoa pessoa = new Pessoa(dto.getNome(), dto.getNascimento());
        return repository.save(pessoa);
    }

    public void editar(Long id, PessoaDto dto){
        Pessoa pessoa = this.listarPorId(id);
        pessoa.setNome(dto.getNome());
        pessoa.setNascimento(dto.getNascimento());
        repository.save(pessoa);
    }


}
