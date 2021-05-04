package cat.tecnocampus.notes.usersAdapters;

import cat.tecnocampus.notes.application.portsOut.CallUserExists;
import cat.tecnocampus.notes.domain.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class userExistsAdapter implements CallUserExists {
    private RestTemplate restTemplate;
    private CircuitBreakerFactory circuitBreakerFactory;
    private String usersServiceUrl;

    public userExistsAdapter(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory,
                             @Value("${app.users-service.host}") String usersServiceUrl) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.usersServiceUrl = "http://" + usersServiceUrl + "users/exists/";
    }

    @Override
    public String sendNote(Note note) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("users");
        return circuitBreaker.run(
                () -> restTemplate.getForObject(usersServiceUrl + note.getUserName(), String.class),
                throwable -> {
                    System.out.println(throwable.getMessage());
                    return  "opened";});
    }
}
