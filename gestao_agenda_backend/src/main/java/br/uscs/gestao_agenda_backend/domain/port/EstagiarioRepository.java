package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstagiarioRepository extends JpaRepository <Estagiario, Long>{
    Optional<Estagiario> findByEmail(String email);

    List<Estagiario> findByServicosAcronimo(String acronimo);
    List<Estagiario> findByServicosEmpty();

//    List<Estagiario> findByProfessorResponsavelIsEmptyOrProfessorResponsavelIsNull();
    List<Estagiario> findByProfessorResponsavelIsNull();

}
