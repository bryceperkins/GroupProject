package shared.communication;

public class UserName implements Input {
    private String name;

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
        String [] invalid = {"'", "\"" };
        for (int i = 0; i < invalid.length; i++) {
            if (name.contains(invalid[i])) {
                return false;
            }
        }
        return true;
    }
    public String toString() {
        return this.name;
    }
}
