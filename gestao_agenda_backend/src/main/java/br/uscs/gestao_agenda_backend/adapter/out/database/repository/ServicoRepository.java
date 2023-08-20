package br.uscs.gestao_agenda_backend.adapter.out.database.repository;

import br.uscs.gestao_agenda_backend.adapter.out.database.entity.ServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<ServicoEntity, Integer> {
}
