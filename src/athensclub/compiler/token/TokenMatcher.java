package athensclub.compiler.token;

public interface TokenMatcher {

    /**
     * Check if the given string matches with the syntax of this token
     * @param string
     * @return
     */
    public boolean matchToken(Tokenizer tokenizer,StringBuilder string);
    
}
