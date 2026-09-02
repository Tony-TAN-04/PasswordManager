package password_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import password_manager.model.User;
import password_manager.model.UserSession;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {

    Optional<UserSession> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUser(User user);
}