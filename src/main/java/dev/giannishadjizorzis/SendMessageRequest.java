package dev.giannishadjizorzis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SendMessageRequest {
    private static final String PHONE_REGEXP = "^(\\+?357)?[29]\\d{7}$";

    @NotBlank(message = "Sender number is required")
    @Pattern(regexp = PHONE_REGEXP, message = "Invalid sender phone number")
    private String senderNumber;

    @NotBlank(message = "Receiver number is required")
    @Pattern(regexp = PHONE_REGEXP, message = "Invalid receiver phone number")
    private String receiverNumber;

    @NotBlank(message = "Message content is required")
    @Size(max = Message.MAX_MESSAGE_CONTENT_SIZE, message = "Message content exceeds the maximum allowed length of 160 characters")
    private String content;

    public String getSenderNumber() { return this.senderNumber; }
    public String getReceiverNumber() { return this.receiverNumber; }
    public String getContent() { return this.content; }
}
