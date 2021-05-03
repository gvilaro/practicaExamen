package cat.tecnocampus.notes.application.service;

import cat.tecnocampus.notes.application.portsIn.DeleteUserNotes;
import cat.tecnocampus.notes.application.service.NoteUseCases;
import cat.tecnocampus.notes.configuration.ReceiverDeleteNotesChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ReceiverDeleteNotesChannel.class)
public class DeleteUserNotesService implements DeleteUserNotes {
    private NoteUseCases noteUseCases;

    private static Logger logger = LoggerFactory.getLogger(DeleteUserNotesService.class);

    public DeleteUserNotesService(NoteUseCases noteUseCases) {
        this.noteUseCases = noteUseCases;
    }

    @Override
    @StreamListener(ReceiverDeleteNotesChannel.DELETE_CHANNEL)
    public void deleteUserNotes(String username) {
        noteUseCases.deleteUserNotes(username);
        logger.info(username + " notes haven been deleted");
    }
}
