package br.uscs.gestao_agenda_backend.adapter.out.database.repository;

import br.uscs.gestao_agenda_backend.adapter.out.database.entity.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {
}
