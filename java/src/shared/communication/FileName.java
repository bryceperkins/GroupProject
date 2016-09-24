package shared.communication;

/**
 * A container for a File name.
 */
public class FileName implements Name{
    private String name;
    /**
     * Validate that a FileName only contains valid characters.
     *  
     * @return Boolean
     */
    public boolean valid(){
        return true;
    }
}
