package dev.giannishadjizorzis;

import java.time.LocalDateTime;
import java.util.UUID;

public class SendMessageResponse {
    private UUID messageId;
    private MessageStatus status;
    private String errorDescription;
    private LocalDateTime sentAt;

    public UUID getMessageId() { return this.messageId; }
    public MessageStatus getStatus() { return this.status; }
    public String getErrorDescription() { return this.errorDescription; }
    public LocalDateTime getSentAt() { return this.sentAt; }

    public void setMessageId(UUID messageId) { this.messageId = messageId; }
    public void setStatus(MessageStatus status) { this.status = status; }
    public void setErrorDescription(String errorDescription) { this.errorDescription = errorDescription; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
