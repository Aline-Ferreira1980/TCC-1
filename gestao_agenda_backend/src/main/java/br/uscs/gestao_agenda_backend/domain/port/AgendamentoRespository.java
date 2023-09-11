package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRespository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.estagiario.id = :estagiarioId AND a.inicioAgendamento = :dataAgendamento")
    public Optional<Agendamento> findByEstagiarioIdAndDataConsulta (
            @Param("estagiarioId") Long estagiarioId,
            @Param("dataAgendamento")LocalDateTime dataAgendamento
    );

    @Query("SELECT a FROM Agendamento a " +
            "JOIN a.estagiario e " +
            "WHERE e.email = :emailEstagiario AND a.inicioAgendamento = :dataAgendamento")
    public Optional<Agendamento> findByEstagiarioEmailAndDataAgendamento (
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
    public Optional<Agendamento> findByDataAgendamentoIndependenteIntegrantes (
            @Param("emailEstagiario") String emailEstagiario,
            @Param("emailPaciete") String emailPaciete,
            @Param("idSala") Long idSala,
            @Param("dataAgendamento") LocalDateTime dataAgendamento
    );

    @Query("SELECT a FROM Agendamento a " +
            "WHERE (a.paciente.id = :id OR a.estagiario.id = :id)")
    Optional<Agendamento> findAgendamentoByUserId(@Param("id") Long id);
}
