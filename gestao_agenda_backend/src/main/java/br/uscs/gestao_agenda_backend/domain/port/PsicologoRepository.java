package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsicologoRepository extends JpaRepository <Psicologo, Long>{
    Psicologo findByEmail(String email);

    @Override
    Psicologo getById(Long aLong);
}
