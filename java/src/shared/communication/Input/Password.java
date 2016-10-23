package shared.communication;

public class Password implements Input{
    private String password;

    public Password(){};
    public Password(String password) throws InvalidInputException {
        setPassword(password);
    }
    
    public void setPassword(String password) throws InvalidInputException {
        if (!valid(password)) {
            throw new InvalidInputException("Invalid Password");   
        }
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
    
    /**
     * Validate a password.
     *
     * Passwords should not contain characters that could cause SQL injetion. ( ' " )
     *
     * @param password 
     *  
     * @return boolean
     */
    public boolean valid(String password) {
        boolean isValid = true;

        String [] invalid = {"'", "\"", ")", "(", "<", ">"};
        for (int i = 0; i < invalid.length; i++) {
            if (password.contains(invalid[i])) {
                isValid = false;;
            }
        }
        return isValid;
    }

    public String toString() {
        return this.password;
    }
}
