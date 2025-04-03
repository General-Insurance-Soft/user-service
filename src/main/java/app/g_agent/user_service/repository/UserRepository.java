package app.g_agent.user_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.g_agent.user_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByIdAndOrganizationId(Long id, Long organizationId);

	Optional<List<User>> findByOrganizationId(Long organizationId);

	@Query("SELECT c FROM User c WHERE c.id IN :ids")
	Optional<List<User>> findByIds(@Param("ids") List<Long> ids);
}
