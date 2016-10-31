package shared.communication;

import java.util.regex.*;

public class UserName implements Input {
    private String name;
    private Pattern p = Pattern.compile("^[\\w-]{3,7}$");
    private Matcher m;

    public UserName(){ }
    public UserName(String name) throws InvalidInputException {
        setName(name); 
    }
    
    public void setName(String name) throws InvalidInputException {
        if (!valid(name)) {
            throw new InvalidInputException("Invalid Name entered");
        }
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Validate that a UserName only contains valid characters.
     *  
     * @return Boolean
     */
    public boolean valid(String name) {
        Matcher m = p.matcher(name);
        boolean isValid = m.matches();

        return isValid;
    }
    public String toString() {
        return this.name;
    }
}
