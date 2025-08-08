public class RLECompression {

    // Compress the message using run-length encoding
    public static String encode(String input) {
        if (input == null || input.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 1; i <= input.length(); i++) {
            if (i == input.length() || input.charAt(i) != input.charAt(i - 1)) {
                sb.append(input.charAt(i - 1)).append(':').append(count).append(';');
                count = 1;
            } else {
                count++;
            }
        }

        return sb.toString();
    }

    // Decompress a run-length encoded message
    public static String decode(String encoded) {
        if (encoded == null || encoded.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int i = 0;

        try {
            while (i < encoded.length()) {
                char c = encoded.charAt(i++);
                
                if (i >= encoded.length() || encoded.charAt(i) != ':') {
                    throw new IllegalArgumentException("Expected ':' after character at position " + (i-1));
                }
                i++; 
                
                StringBuilder countStr = new StringBuilder();
                while (i < encoded.length() && encoded.charAt(i) != ';') {
                    countStr.append(encoded.charAt(i++));
                }
                
                if (i >= encoded.length() || encoded.charAt(i) != ';') {
                    throw new IllegalArgumentException("Expected ';' after count");
                }
                i++; 
                
                int count = Integer.parseInt(countStr.toString());
                sb.append(String.valueOf(c).repeat(count));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid RLE format: " + encoded, e);
        }

        return sb.toString();
    }

    // Calculate compression ratio
    public static double getCompressionRatio(String original, String compressed) {
        if (original == null || compressed == null || original.isEmpty()) return 0.0;
        return (double) compressed.length() / original.length();
    }
}
