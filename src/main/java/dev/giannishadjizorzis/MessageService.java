package dev.giannishadjizorzis;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MessageService {

    @Inject
    MessageRepository repository;

    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequest request) {
        Message message = new Message();
        message.setSenderNumber(request.getSenderNumber());
        message.setReceiverNumber(request.getReceiverNumber());
        message.setContent(request.getContent());
        message.setStatus(MessageStatus.SENDING);

        boolean delivered = Math.random() > 0.2;
        if (delivered) {
            message.setStatus(MessageStatus.DELIVERED);
            message.setDeliveredAt(LocalDateTime.now());
        } else {
            message.setStatus(MessageStatus.FAILED);
            message.setErrorDescription("Simulated delivery failure");
        }

        message.setSentAt(LocalDateTime.now());
        repository.persist(message);

        return toResponse(message);
    }

    public List<Message> listAll() {
        return repository.findAll();
    }

    public Optional<Message> findById(UUID id) {
        return repository.findById(id);
    }

    private SendMessageResponse toResponse(Message message) {
        SendMessageResponse response = new SendMessageResponse();
        response.setMessageId(message.getId());
        response.setStatus(message.getStatus());
        response.setErrorDescription(message.getErrorDescription());
        response.setSentAt(message.getSentAt());
        return response;
    }
}
