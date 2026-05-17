package dev.giannishadjizorzis;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    public static final int MAX_MESSAGE_CONTENT_SIZE = 160;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "sender_number", nullable = false)
    private String senderNumber;

    @Column(name = "receiver_number", nullable = false)
    private String receiverNumber;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Column(name = "error_description")
    private String errorDescription;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @PrePersist
    private void recordWhenWasCreated() {
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return this.id; }
    public String getSenderNumber() { return this.senderNumber; }
    public String getReceiverNumber() { return this.receiverNumber; }
    public String getContent() { return this.content; }
    public MessageStatus getStatus() { return this.status; }
    public String getErrorDescription() { return this.errorDescription; }
    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public LocalDateTime getSentAt() { return this.sentAt; }
    public LocalDateTime getDeliveredAt() { return this.deliveredAt; }

    public void setId(UUID id) { this.id = id; }
    public void setSenderNumber(String senderNumber) { this.senderNumber = senderNumber; }
    public void setReceiverNumber(String receiverNumber) { this.receiverNumber = receiverNumber; }
    public void setContent(String content) { this.content = content; }
    public void setStatus(MessageStatus status) { this.status = status; }
    public void setErrorDescription(String errorDescription) { this.errorDescription = errorDescription; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
}
