package cadastros.service;

import cadastros.domain.model.Pessoa;
import cadastros.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository repository;

    public List<Pessoa> listarTodos(){
        return repository.findAll();
    }

    public Pessoa listarPorId(Long id){
        return repository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pessoa não encontrada com o ID informado."));
    }

    public List<Pessoa> buscarPor(Pessoa filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);
    }

    public Pessoa cadastrar(Pessoa pessoa){
        return repository.save(pessoa);
    }

    public void editar(Long id, Pessoa pessoa){
        repository.findById(id).map(pessoaEncontrada -> {
            pessoa.setId(pessoaEncontrada.getId());
            repository.save(pessoa);
            return pessoa;
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada com o ID informado."));
    }


}
