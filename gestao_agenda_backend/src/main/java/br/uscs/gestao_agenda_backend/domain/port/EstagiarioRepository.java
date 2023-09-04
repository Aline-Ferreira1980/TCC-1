package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstagiarioRepository extends JpaRepository <Estagiario, Long>{
    Estagiario findByEmail(String email);

}
