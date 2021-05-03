package cat.tecnocampus.users.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DeleteUserNotesChannel {
    String DELETE_CHANNEL = "senderDeletingChannel";

    @Output(DELETE_CHANNEL)
    MessageChannel senderDeletingChannel();
}
