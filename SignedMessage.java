import java.util.Base64;

public class SignedMessage {
    public final Message message;
    public final String signatureBase64;

    public SignedMessage(Message message, byte[] signature) {
        this.message = message;
        this.signatureBase64 = Base64.getEncoder().encodeToString(signature);
    }

    public byte[] signatureBytes() {
        return Base64.getDecoder().decode(signatureBase64);
    }

    @Override
    public String toString() {
        return "SignedMessage{" +
                "message=" + message +
                ", signatureBase64='" + signatureBase64 + '\'' +
                '}';
    }
}
