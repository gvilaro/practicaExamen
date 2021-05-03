package cat.tecnocampus.users.messageAdapter;

import cat.tecnocampus.users.application.portsOut.UserDeleteMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(UserDeleteMessage.class)
public class DeletingMessageAdapter {
    private MessageChannel deletingChannel;

    public DeletingMessageAdapter(UserDeleteMessage channels) {
        this.deletingChannel = channels.senderDeletingChannel();
    }

    public void sendDeleting(String message) {
        deletingChannel.send(MessageBuilder.withPayload("Deleting " + message).build());
    }
}
