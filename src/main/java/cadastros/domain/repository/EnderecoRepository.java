package cadastros.domain.repository;

import cadastros.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByPessoasId(Long id);
}
