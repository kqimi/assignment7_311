import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Message {
    public final String senderId;
    public final String receiverId;
    public final Map<String, String> metadata;
    public final String body;

    public Message(String senderId, String receiverId, Map<String, String> metadata, String body) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.metadata = metadata;
        this.body = body;
    }

    // Create a canonical byte representation for hashing/signing.Sorts metadata keys to ensure consistency.
    public byte[] toCanonicalBytes() {
        StringBuilder sb = new StringBuilder();
        sb.append(senderId).append('|').append(receiverId).append('|');
        metadata.keySet().stream().sorted().forEach(k -> {
            sb.append(k).append('=').append(metadata.get(k)).append(';');
        });
        sb.append('|').append(body);
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", metadata=" + metadata +
                ", body='" + body + '\'' +
                '}';
    }
}
