package shared.communication;

import java.util.regex.*;

public class Password implements Input{
    private String password;
    private Pattern p = Pattern.compile("^[\\w-]*$");
    private Matcher m;

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
     * @param password 
     *  
     * @return boolean
     */
    public boolean valid(String password) {
        Matcher m = p.matcher(password);
        boolean isValid = m.matches();
        
        return isValid;
    }

    public String toString() {
        return this.password;
    }
}
