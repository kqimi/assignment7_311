import javax.crypto.Cipher;
import java.security.*;
import java.util.*;

class Person {
    String name;
    PublicKey publicKey;
    PrivateKey privateKey;
    List<Person> connections = new ArrayList<>();

    Person(String name) throws Exception {
        this.name = name;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }
}

class Message {
    String sender, receiver, metadata;
    byte[] body;

    Message(String sender, String receiver, String metadata, byte[] body) {
        this.sender = sender;
        this.receiver = receiver;
        this.metadata = metadata;
        this.body = body;
    }
}

public class SecureGraph {
    static byte[] encrypt(String msg, PublicKey key) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, key);
        return c.doFinal(msg.getBytes());
    }
    static String decrypt(byte[] data, PrivateKey key) throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE, key);
        return new String(c.doFinal(data));
    }

    // Simulate routing: BFS finds receiver, forwarding encrypted data
    static void sendMessage(Person start, Person target, String text) throws Exception {
        Queue<Person> q = new LinkedList<>();
        Set<Person> visited = new HashSet<>();
        Map<Person, Person> prev = new HashMap<>();
        q.add(start);
        visited.add(start);

        while (!q.isEmpty()) {
            Person cur = q.poll();
            if (cur == target) break;
            for (Person neighbor : cur.connections)
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    prev.put(neighbor, cur);
                    q.add(neighbor);
                }
        }

        // Encrypt for final recipient only
        byte[] encrypted = encrypt(text, target.publicKey);
        Message msg = new Message(start.name, target.name, "RSA-2048", encrypted);

        // Trace route
        Person cur = target;
        List<Person> path = new ArrayList<>();
        while (cur != null) {
            path.add(cur);
            cur = prev.get(cur);
        }
        Collections.reverse(path);

        System.out.println("Routing path: " +
                path.stream().map(p -> p.name).reduce((a,b)->a+" -> "+b).orElse(""));

        // Only final recipient decrypts
        String decrypted = decrypt(msg.body, target.privateKey);
        System.out.println(target.name + " received: " + decrypted);
    }

    public static void main(String[] args) throws Exception {
        // Build graph
        Person John = new Person("John");
        Person Foo = new Person("Foo");
        Person Bar = new Person("Bar");

        John.connections.add(Foo);
        Foo.connections.add(Bar);


        sendMessage(John, Bar, "example encrypted message");
    }
}
