package cat.tecnocampus.notes.usersAdapters;

import cat.tecnocampus.notes.application.portsOut.CallUserExists;
import cat.tecnocampus.notes.domain.Note;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class userExistsAdapter implements CallUserExists {
    private RestTemplate restTemplate;

    public userExistsAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String sendNote(Note note) {
        return restTemplate.getForObject("http://localhost:8080/users/exists/" + note.getUserName(), String.class);
    }
}
