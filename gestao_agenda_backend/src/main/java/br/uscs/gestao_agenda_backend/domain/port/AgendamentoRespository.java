package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRespository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a " +
            "JOIN a.estagiario e " +
            "WHERE e.email = :emailEstagiario AND a.inicioAgendamento = :dataAgendamento")
    Optional<Agendamento> findByEstagiarioEmailAndDataAgendamento (
            @Param("emailEstagiario") String emailEstagiario,
            @Param("dataAgendamento") LocalDateTime dataAgendamento
    );

    @Query("SELECT a FROM Agendamento a " +
            "JOIN a.estagiario e " +
            "JOIN a.paciente p " +
            "JOIN a.sala s " +
            "WHERE a.inicioAgendamento = :dataAgendamento "+
            "AND (e.email = :emailEstagiario " +
            "OR p.email = :emailPaciete " +
            "OR s.id = :idSala )")
    Optional<Agendamento> findByDataAgendamentoIndependenteIntegrantes (
            @Param("emailEstagiario") String emailEstagiario,
            @Param("emailPaciete") String emailPaciete,
            @Param("idSala") Long idSala,
            @Param("dataAgendamento") LocalDateTime dataAgendamento
    );

    @Query("SELECT a FROM Agendamento a " +
            "WHERE (a.paciente.id = :id OR a.estagiario.id = :id)")
    Optional<List<Agendamento>> findAgendamentosByUserId(@Param("id") Long id);

    Optional<List<Agendamento>> findBySalaId(long sala_id);

    @Query("SELECT a FROM Agendamento a WHERE a.inicioAgendamento BETWEEN :inicio AND :fim")
    Optional<List<Agendamento>> findAgendamentosByTimeRange(@Param("inicio") LocalDateTime inicio,
                                                  @Param("fim") LocalDateTime fim);


    @Query("SELECT a FROM Agendamento a " +
            "WHERE (a.paciente.id = :userId OR a.estagiario.id = :userId) " +
            "AND a.inicioAgendamento BETWEEN :dataInicio AND :dataFim")
    Optional<List<Agendamento>> findAgendamentoByEstagiarioIdAndDateRange(
            @Param("userId") Long userId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );
}
