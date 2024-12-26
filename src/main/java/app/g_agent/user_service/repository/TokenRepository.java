package app.g_agent.user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import app.g_agent.user_service.model.TokenBlackList;
import app.g_agent.user_service.model.User;

@Service
public interface TokenRepository extends JpaRepository<TokenBlackList, UUID> {

	Optional<TokenBlackList> findByToken(String token);
}
