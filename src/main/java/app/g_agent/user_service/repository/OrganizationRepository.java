package app.g_agent.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.g_agent.user_service.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
