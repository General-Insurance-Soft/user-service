package app.g_agent.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.g_agent.user_service.model.Authority;
import app.g_agent.user_service.model.Role;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
