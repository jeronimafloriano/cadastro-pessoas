package cadastros.service;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.domain.repository.EnderecoRepository;
import cadastros.dto.EnderecoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;


    public Endereco listarEnderecoPorId(Long id){
        return repository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Endereco não encontrada com o ID informado."));
    }

    public List<Endereco> listarEnderecoPorPessoaId(Long id){
        return repository.findByPessoasId(id);
    }

    public List<Endereco> listarTodos(){
        return repository.findAll();
    }

    public Endereco cadastrar(Pessoa pessoa, EnderecoDto dto){
        Endereco endereco = new Endereco(dto.getLogradouro(), dto.getCep(),
                                            dto.getNumero(), dto.getCidade(), dto.isPrincipal());
        pessoa.cadastrarEndereco(endereco);
        endereco.vincularPessoa(pessoa);

        return repository.save(endereco);
    }

    public void editar(Long id, EnderecoDto dto){
        Endereco endereco = this.listarEnderecoPorId(id);
        endereco.setCep(dto.getCep());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        endereco.setPrincipal(dto.isPrincipal());

        repository.save(endereco);
    }



}
