package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.application.dto.in.Telefone;
import br.uscs.gestao_agenda_backend.domain.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

}
