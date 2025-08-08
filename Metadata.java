public class Metadata {
    public String type;  // e.g., "rle"
    public int originalLength;  // For compression algorithms
    public double compressionRatio;  // For compression algorithms
    
    // Basic constructor
    public Metadata(String type) {
        this.type = type;
        this.originalLength = -1;
        this.compressionRatio = 1.0;
    }
    
    // Constructor for compression
    public Metadata(String type, int originalLength, double compressionRatio) {
        this.type = type;
        this.originalLength = originalLength;
        this.compressionRatio = compressionRatio;
    }
}
