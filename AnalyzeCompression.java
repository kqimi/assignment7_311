public class AnalyzeCompression {
    public static void main(String[] args) {
        String[] messages = {
            "heyyyyyyy how are youuu",
            "loooool that was fuuunnnyyyy", 
            "haaaahahaha this is soooo coooool",
            "yeeees let's gooooo!!!"
        };
        
        System.out.println("=== RLE Compression Analysis ===\n");
        
        for (String msg : messages) {
            String compressed = RLECompression.encode(msg);
            double ratio = RLECompression.getCompressionRatio(msg, compressed);
            
            System.out.println("Original:   \"" + msg + "\" (" + msg.length() + " chars)");
            System.out.println("Compressed: \"" + compressed + "\" (" + compressed.length() + " chars)");
            System.out.println("Ratio: " + String.format("%.2f", ratio) + " (" + 
                             (ratio < 1.0 ? "GOOD compression" : "EXPANSION") + ")");
            System.out.println();
        }
    }
}
