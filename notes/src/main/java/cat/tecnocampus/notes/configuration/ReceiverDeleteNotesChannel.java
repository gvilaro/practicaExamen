package cat.tecnocampus.notes.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceiverDeleteNotesChannel {
    String DELETE_CHANNEL = "receiverDeletingChannel";

    @Input(DELETE_CHANNEL)
    SubscribableChannel receiverDeletingChannel();
}
