package cat.tecnocampus.users.deleteUserNotesAdapter;

import cat.tecnocampus.users.application.portsOut.DeleteUserNotes;
import cat.tecnocampus.users.configuration.DeleteUserNotesChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(DeleteUserNotesChannel.class)
public class DeleteUserNotesAdapter implements DeleteUserNotes {
    private MessageChannel deletingChannel;

    public DeleteUserNotesAdapter(DeleteUserNotesChannel channels) {
        this.deletingChannel = channels.senderDeletingChannel();
    }

    public void deleteUserNotes(String message) {
        deletingChannel.send(MessageBuilder.withPayload(message).build());
    }
}
