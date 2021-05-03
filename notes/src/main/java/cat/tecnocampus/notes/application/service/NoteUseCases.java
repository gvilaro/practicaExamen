package cat.tecnocampus.notes.application.service;

import cat.tecnocampus.notes.application.portsOut.CallUserExists;
import cat.tecnocampus.notes.application.portsOut.NoteDAO;
import cat.tecnocampus.notes.domain.Note;
import cat.tecnocampus.notes.webAdapter.UserDoesNotExistException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteUseCases implements cat.tecnocampus.notes.application.portsIn.NoteUseCases {

    private final NoteDAO noteDAO;
    private CallUserExists userExistsAdapter;

    public NoteUseCases(NoteDAO noteDAO, CallUserExists userExistsAdapter) {
        this.noteDAO = noteDAO;
        this.userExistsAdapter = userExistsAdapter;
    }

    @Override
    public Note addNote(Note note) {
        noteDAO.insert(note);

        return note;
    }

    @Override
    public Note createNote(Note note) {
        //TODO 1: heu de refactoritzar aquesta crida de manera que segueixi l'arquitecutra hexagonal. Aquesta crida s'hauria
        // de fer en un adaptador i la seva interfície hauria d'estar en un port de sortida.
        // Aquí és on es comprova que l'usuari existeix.
        // Si traslladeu la crida a un fitxer diferent podeu moure també tots els TODOs associats

        //TODO 2: heu d'embolcallar la crida amb un circuit breaker de manera que si el circuit és obert es crei la nota igualment
        // però amb l'atribut checked a "false". Per exemple si el circuit està obert podeu fer que la crida retorni el String "obert"
        // i actuar en conseqüència

        //TODO 4.1: si implemeteu el discovery service feu servir l'adreça que us dóna aquest

        //TODO 4.2: si implemeteu el discovery service voledreu que el restTemplate estigui balancejat, és a dir, si hi ha més d'una instància
        // del microservei d'usuaris que les crides es vagin repartin entre ells
        String userExists = userExistsAdapter.sendNote(note);

        if (userExists.equals(false)) {
            throw new UserDoesNotExistException();
        }else if (userExists.equals("true")) {
            note.setChecked(true);
        }else if (userExists.equals("opened")) {
            note.setChecked(false);
        }

        return reallyCreateNote(note);
    }

    private Note reallyCreateNote(Note note){
        note.setDateCreation(LocalDateTime.now());
        note.setDateEdit(LocalDateTime.now());
        return addNote(note);
    }
    @Override
    public Note deleteNote(Note note) {
        noteDAO.deleteNote(note);
        return note;
    }

    @Override
    public int deleteUserNotes(String userName) {
        return noteDAO.deleteUserNotes(userName);
    }

    @Override
    public List<Note> getUserNotes(String userName) {
        return noteDAO.findByUsername(userName);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDAO.findAll();
    }

    @Override
    public int updateNoteExists(String userName) {
        return noteDAO.updateNoteExists(userName);
    }
}
