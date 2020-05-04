package athensclub.compiler.token;

public interface TokenCreator {

    /**
     * A token creator that just create basic token.
     */
    public static TokenCreator DEFAULT = new TokenCreator() {
    };

    /**
     * Create token from the given string
     * 
     * @param string
     * @return
     */
    public default Token createToken(StringBuilder string) {
	return new Token(string.toString());
    }
}
