import java.security.*;
import java.util.Base64;

public class User {
    public final String id;
    private final KeyPair keyPair;

    public User(String id, KeyPair keyPair) {
        this.id = id;
        this.keyPair = keyPair;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    // Sign arbitrary bytes using SHA256withRSA.
    public byte[] sign(byte[] data) throws GeneralSecurityException {
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(getPrivateKey());
        signer.update(data);
        return signer.sign();
    }

    // Return public key as Base64 string for sharing.
    public String publicKeyBase64() {
        return Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
    }

    @Override
    public String toString() {
        return "User{" + id + '}';
    }
}
