package app.g_agent.user_service.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import app.g_agent.user_service.model.User;
@Repository
public interface UserRepository extends CrudRepository<User, UUID>{
   
 
}
