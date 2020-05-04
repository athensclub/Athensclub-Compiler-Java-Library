package athensclub.compiler.token;

/**
 * Visitor object visit a token in a token then create the result token
 * 
 * @author Athensclub
 *
 */
public interface Visitor {

    /**
     * Called when this visitor begin visiting
     * 
     * @param string
     */
    public void onBegin(Tokenizer tokenizer,StringBuilder string);

    /**
     * Called when the next visitor start visiting and put this visitor on pause.
     * 
     * @param tokenizer
     * @param next
     */
    public default void onExit(Tokenizer tokenizer, Visitor next) {
    }

    /**
     * Called when the next visitor finished visiting and return the given token and begin this visitor's visiting again.
     * 
     * @param tokenizer
     * @param token
     */
    public void onEnter(Tokenizer tokenizer,Token token);

    /**
     * Receive next character in the stream
     * 
     * @param next
     */
    public void nextChar(Tokenizer tokenizer, char next);

    /**
     * Create token from the characters stream that this visitor received
     * 
     * @return
     */
    public Token createToken();

}
