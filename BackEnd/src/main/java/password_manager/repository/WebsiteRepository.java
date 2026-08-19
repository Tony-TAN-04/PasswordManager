package password_manager.repository;

import password_manager.model.Website;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteRepository extends JpaRepository<Website, Long> {
    
    List<Website> findByUserId(Long userId);
}
