package br.uscs.gestao_agenda_backend.adapter.out.database.repository;

import br.uscs.gestao_agenda_backend.adapter.out.database.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepostirory extends JpaRepository<DocenteEntity, Integer> {
}
