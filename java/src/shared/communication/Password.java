package shared.communication;

/**
 * A container for a File name.
 */
public class Password implements Name{
    private String password;
    /**
     * Validate that a FileName only contains valid characters.
     *  
     * @return Boolean
     */
    public boolean valid() {
        return true;
    }
}
