package athensclub.compiler.token;

/**
 * a {@code Tokenizer} that tokenizes a string
 * 
 * @author Athensclub
 *
 */
public class StringTokenizer extends Tokenizer {

    private String string;

    private int index;
    
    /**
     * Reset the string to be tokenized to be the given string
     * @param str
     */
    public void reset(String str) {
	index = 0;
	string = str;
    }

    /**
     * Create new string tokenizer with the given string to be tokenized
     * 
     * @param str:
     *            the string to be tokenized
     */
    public StringTokenizer(String str) {
	string = str;
    }

    @Override
    public boolean eof() {
	return index == string.length();
    }

    @Override
    protected char nextCharInStream() {
	return string.charAt(index++);
    }

}
