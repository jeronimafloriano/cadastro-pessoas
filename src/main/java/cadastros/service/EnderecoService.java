package cadastros.service;

import cadastros.domain.model.Endereco;
import cadastros.domain.model.Pessoa;
import cadastros.domain.model.TipoEndereco;
import cadastros.domain.repository.EnderecoRepository;
import cadastros.dto.CadastrarEnderecoDto;
import cadastros.dto.EditarEnderecoDto;
import cadastros.dto.EditarTipoEnderecoDto;
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

    public Endereco cadastrar(Pessoa pessoa, CadastrarEnderecoDto dto){
        Endereco endereco = Endereco.builder()
                .logradouro(dto.getLogradouro())
                .cep(dto.getCep())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .tipoEndereco(dto.getTipoEndereco())
                .build();

        existeEnderecoPrincipal(pessoa);

        if(existeEnderecoPrincipal(pessoa) && dto.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Já existe um endereço cadastrado como principal para a pessoa informada");
        }

        pessoa.cadastrarEndereco(endereco);
        endereco.vincularPessoa(pessoa);

        return repository.save(endereco);
    }

    public void editar(Long id, EditarEnderecoDto dto){
        Endereco endereco = this.listarEnderecoPorId(id);
        endereco.setCep(dto.getCep());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setCidade(dto.getCidade());
        repository.save(endereco);
    }

    public void editarTipo(Long idEndereco, Long idPessoa, EditarTipoEnderecoDto dto){
        Endereco endereco = this.listarEnderecoPorId(idEndereco);

        var pessoa = endereco.getPessoas()
                .stream()
                .filter(p -> p.getId().equals(idPessoa))
                .findFirst().orElseThrow();

        if(existeEnderecoPrincipal(pessoa) && dto.getTipoEndereco().equals(TipoEndereco.PRINCIPAL)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Já existe um endereço cadastrado como principal para a pessoa informada");
        }

        endereco.setTipoEndereco(dto.getTipoEndereco());
        repository.save(endereco);
    }

    private boolean existeEnderecoPrincipal(Pessoa pessoa){
        var enderecoPrincipal = pessoa.getEnderecos()
                .stream()
                .filter(e -> e.getTipoEndereco().equals(TipoEndereco.PRINCIPAL))
                .count();

        return enderecoPrincipal > 0;
    }



}
