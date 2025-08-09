import java.security.*;
import java.util.HashMap;
import java.util.Map;

public class SignedMessaging {

    // Generate RSA keypair (2048 bits)
    public static KeyPair generateRSAKeyPair() throws GeneralSecurityException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }

    // Verify signature using sender's public key.
    public static boolean verifySignature(PublicKey senderPublicKey, Message message, byte[] signature)
            throws GeneralSecurityException {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(senderPublicKey);
        verifier.update(message.toCanonicalBytes());
        return verifier.verify(signature);
    }

    public static void main(String[] args) throws Exception {
        // Create two users
        User alice = new User("alice", generateRSAKeyPair());
        User bob   = new User("bob", generateRSAKeyPair());

        System.out.println("Alice public key: " + alice.publicKeyBase64());
        System.out.println("Bob public key: " + bob.publicKeyBase64());
        System.out.println();

        // Build a message from Alice to Bob
        Map<String, String> metadata = new HashMap<>();
        metadata.put("timestamp", Long.toString(System.currentTimeMillis()));
        metadata.put("original_length", "42");

        Message msg = new Message(alice.id, bob.id, metadata, "Meet at 7pm behind the library.");

        // Alice signs the message
        byte[] signature = alice.sign(msg.toCanonicalBytes());
        SignedMessage signedMsg = new SignedMessage(msg, signature);

        System.out.println("Signed message:");
        System.out.println(signedMsg);
        System.out.println();

        // Bob verifies
        boolean verified = verifySignature(alice.getPublicKey(), signedMsg.message, signedMsg.signatureBytes());
        System.out.println("Verification result (should be true): " + verified);

        // Tamper with message
        Message tampered = new Message(alice.id, bob.id, metadata, "Meet at 8pm behind the library.");
        boolean tamperedVerified = verifySignature(alice.getPublicKey(), tampered, signedMsg.signatureBytes());
        System.out.println("Tampered verification result (should be false): " + tamperedVerified);
    }
}
