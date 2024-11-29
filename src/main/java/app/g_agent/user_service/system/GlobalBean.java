package app.g_agent.user_service.system;
  
import org.springframework.context.annotation.Bean;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

public class GlobalBean {

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
    
}
