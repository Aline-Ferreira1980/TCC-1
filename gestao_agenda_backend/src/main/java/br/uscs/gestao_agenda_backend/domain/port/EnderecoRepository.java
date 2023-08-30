package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.application.dto.in.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
