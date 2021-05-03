package cat.tecnocampus.users.application.portsOut;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserDeleteMessage {
    String DELETE_CHANNEL = "senderDeletingChannel";

    @Output(DELETE_CHANNEL)
    MessageChannel senderDeletingChannel();
}
