package cat.tecnocampus.notes.usersAdapters;

import cat.tecnocampus.notes.application.portsOut.CallUserExists;
import cat.tecnocampus.notes.domain.Note;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class userExistsAdapter implements CallUserExists {
    private RestTemplate restTemplate;
    private CircuitBreakerFactory circuitBreakerFactory;

    public userExistsAdapter(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public String sendNote(Note note) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("users");
        return circuitBreaker.run(
                () -> restTemplate.getForObject("http://localhost:8081/users/exists/" + note.getUserName(), String.class),
                throwable -> {
                    System.out.println(throwable.getMessage());
                    return  "opened";});
    }
}
