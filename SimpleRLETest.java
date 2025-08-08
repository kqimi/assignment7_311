public class SimpleRLETest {
    public static void main(String[] args) {
        System.out.println("=== RLE Compression Test ===\n");
        
        // Setup people and graph
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        Graph graph = new Graph();
        graph.addPerson(alice);
        graph.addPerson(bob);
        
        // Test different types of messages
        String[] testMessages = {
            "helloooooo!!!",           // Original test
            "aaaaaaaaaaaabbbbcccccc",   // Long runs
            "abcdefg",                 // No repetition
            "aaa",                     // Simple case
            "aaaaaaaaaaaaa"            // Very long run
        };
        
        for (String original : testMessages) {
            System.out.println("Testing: \"" + original + "\"");
            
            // Compress message
            String compressed = RLECompression.encode(original);
            double ratio = RLECompression.getCompressionRatio(original, compressed);
            
            // Create message with metadata
            Metadata meta = new Metadata("rle", original.length(), ratio);
            Message message = new Message("Alice", "Bob", meta, compressed);
            
            System.out.println("  Original length: " + original.length());
            System.out.println("  Compressed: \"" + compressed + "\" (length: " + compressed.length() + ")");
            System.out.println("  Compression ratio: " + String.format("%.2f", ratio));
            
            // Send message
            graph.sendMessage(message);
            
            // Bob checks inbox and decodes
            for (Message m : bob.inbox) {
                if (m.metadata.type.equals("rle")) {
                    String decoded = RLECompression.decode(m.body);
                    boolean success = original.equals(decoded);
                    System.out.println("  [Bob] Decoded: \"" + decoded + "\"");
                    System.out.println("  Test result: " + (success ? "PASSED ✓" : "FAILED ✗"));
                }
            }
            bob.inbox.clear(); // Clear for next test
            System.out.println();
        }
        
        System.out.println("=== RLE Testing Complete ===");
    }
}
