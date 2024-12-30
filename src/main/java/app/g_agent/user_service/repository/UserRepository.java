package app.g_agent.user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import app.g_agent.user_service.model.Role;
import app.g_agent.user_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
		
    Optional<User> findByEmail(String email);
    Optional<User> findByIdAndOrganizationId(Long id, Long organizationId);
}
