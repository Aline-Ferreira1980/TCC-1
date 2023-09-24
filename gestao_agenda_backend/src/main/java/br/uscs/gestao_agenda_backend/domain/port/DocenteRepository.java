package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.Docente;
import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Docente findByEmail(String email);
    List<Docente> findByServicosAcronimo(String acronimo);
    List<Docente> findByServicosEmpty();
}
