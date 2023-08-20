package br.uscs.gestao_agenda_backend.adapter.out.database.repository;

import br.uscs.gestao_agenda_backend.adapter.out.database.entity.MoradorCasaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorCasaRepository extends JpaRepository<MoradorCasaEntity, Integer> {
}
