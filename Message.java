public class Message {
    public String senderId;
    public String receiverId;
    public Metadata metadata;
    public String body;

    public Message(String senderId, String receiverId, Metadata metadata, String body) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.metadata = metadata;
        this.body = body;
    }
}
