package br.uscs.gestao_agenda_backend.domain.port;

import br.uscs.gestao_agenda_backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    UserDetails findByEmail(String email);
}
