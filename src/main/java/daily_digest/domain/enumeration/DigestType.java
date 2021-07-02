package daily_digest.domain.enumeration;

/**
 * The DigestType enumeration.
 */
public enum DigestType {
    NEWBELIVER("初信"),
    SERVICEONE("事奉"),
    GENERAL("一般"),
    CUSTOMIZED("自訂");

    private final String value;

    DigestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
