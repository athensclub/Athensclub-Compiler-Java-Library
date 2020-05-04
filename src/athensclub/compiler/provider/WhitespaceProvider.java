package athensclub.compiler.provider;

import athensclub.compiler.token.Token;
import athensclub.compiler.token.TokenProvider;
import athensclub.compiler.token.Tokenizer;

/**
 * Provider that split tokens using white space
 * 
 * @author Athensclub
 *
 */
public class WhitespaceProvider extends TokenProvider {

    private TokenProvider tokenProvider;

    private boolean autoCreateOnEof;

    public static class WhitespaceDefaultToken extends Token {

	public WhitespaceDefaultToken(String str) {
	    super(str);
	}

    }

    /**
     * Set whether token would be automatically created when reach end of file or
     * tokenizer will throw an exception
     * 
     * @param autoCreateOnEof:
     *            whether token would be automatically created when reach end of
     *            file or tokenizer will throw an exception
     */
    public void setAutoCreateOnEof(boolean autoCreateOnEof) {
	this.autoCreateOnEof = autoCreateOnEof;
    }

    /**
     * Set a token provider that provide token for non-whitespace
     * 
     * @param tokenProvider:
     *            a token provider that provide token for non-whitespace
     */
    public void setTokenProvider(TokenProvider tokenProvider) {
	if (tokenProvider instanceof WhitespaceProvider) {
	    throw new IllegalArgumentException(
		    "Can not set token provider of WhitespaceProvider to be instance of WhitespaceProvider itself");
	}
	this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if (string.length() > 0 && Character.isWhitespace(string.charAt(string.length() - 1))) {
	    if (string.length() == 1) {
		string.deleteCharAt(0);
	    } else {
		try {
		    tokenizer.previousChar();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return true;
	    }
	}
	if (tokenizer.eof() && autoCreateOnEof) {
	    return true;
	}
	return tokenProvider == null ? false : tokenProvider.matchToken(tokenizer, string);
    }

    @Override
    public Token createToken(StringBuilder string) {
	return tokenProvider == null ? new WhitespaceDefaultToken(string.toString())
		: tokenProvider.createToken(string);
    }

}
