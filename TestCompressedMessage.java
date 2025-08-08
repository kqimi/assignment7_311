public class TestCompressedMessage {
    public static void main(String[] args) {
        // Set up people
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        Person charlie = new Person("Charlie");
        Person dana = new Person("Dana");

        // Create graph and add people
        Graph graph = new Graph();
        graph.addPerson(alice);
        graph.addPerson(bob);
        graph.addPerson(charlie);
        graph.addPerson(dana);

        // Prepare messages
        sendRLEMessage("Alice", "Bob", "heyyyyyyy how are youuu", graph);
        sendRLEMessage("Bob", "Charlie", "loooool that was fuuunnnyyyy", graph);
        sendRLEMessage("Charlie", "Dana", "haaaahahaha this is soooo coooool", graph);
        sendRLEMessage("Dana", "Alice", "yeeees let's gooooo!!!", graph);

        // Print inboxes and decode messages
        printInboxWithDecoding(alice);
        printInboxWithDecoding(bob);
        printInboxWithDecoding(charlie);
        printInboxWithDecoding(dana);
    }

    // Helper to create and send a compressed RLE message
    public static void sendRLEMessage(String sender, String receiver, String originalMessage, Graph graph) {
        String compressed = RLECompression.encode(originalMessage);
        Metadata meta = new Metadata("rle");
        Message message = new Message(sender, receiver, meta, compressed);
        graph.sendMessage(message);
        System.out.println("[" + sender + "] sent RLE message to [" + receiver + "]");
    }

    // Helper to decode and display messages in a person's inbox
    public static void printInboxWithDecoding(Person person) {
        System.out.println("\n--- Inbox of " + person.id + " ---");
        for (Message msg : person.inbox) {
            String decoded = msg.metadata.type.equals("rle") ? RLECompression.decode(msg.body) : msg.body;
            System.out.println("From " + msg.senderId + ": " + decoded);
        }
    }
}
