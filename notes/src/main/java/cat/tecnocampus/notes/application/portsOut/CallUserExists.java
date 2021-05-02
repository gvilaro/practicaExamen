package cat.tecnocampus.notes.application.portsOut;

import cat.tecnocampus.notes.domain.Note;

public interface CallUserExists {
    String sendNote(Note note);
}
