package athensclub.compiler.token;

/**
 * A class representing Token
 * 
 * @author Athensclub
 *
 */
public class Token {

    private String string;

    /**
     * Create a token with the given string representing token
     * 
     * @param str:
     *            string representing this token
     */
    public Token(String str) {
	string = str;
    }
    
    protected Token() {
	
    }

    /**
     * Get the string that is in source file when it matches with this token type
     * syntax
     * 
     * @return
     */
    public String getString() {
	return string;
    }

    @Override
    public String toString() {
	if(string == null) {
	    return getClass().getName() + " (string is null)";
	}
	return getString();
    }

    /**
     * Check whether this token is equals to other token By comparing the class of
     * this token to the other token then compare the string representing this token
     * to ther other's
     * 
     * @param other:
     *            the other token to get compared to this token
     * @return whether this token is equals to other token
     */
    public boolean equals(Token other) {
	if (other.getClass().equals(getClass())) {
	    return string.equals(other.string);
	}
	return false;
    }

    @Override
    public boolean equals(Object other) {
	if (other instanceof Token) {
	    return equals((Token) other);
	}
	return false;
    }

}
