package br.uscs.gestao_agenda_backend.adapter.out.database.repository;

import br.uscs.gestao_agenda_backend.adapter.out.database.entity.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<SalaEntity, Integer> {
}
