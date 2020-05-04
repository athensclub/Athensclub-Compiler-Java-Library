package athensclub.compiler.provider;

import athensclub.compiler.ACompilerUtils;
import athensclub.compiler.token.TokenProvider;
import athensclub.compiler.token.Tokenizer;
import athensclub.compiler.token.Visitor;

/**
 * Provider that will create visitor that visit java-like string literal that is
 * stored in form of java string (for example: "\"Hello, World!\\n\"")
 * 
 * @author Athensclub
 *
 */
public class StringProvider extends TokenProvider {

    /**
     * A cache single instance of this string provider.
     */
    public static final StringProvider INSTANCE = new StringProvider();

    /**
     * A token class that represent string token.This token is created when
     * StringProvider found string token.
     * 
     * @author Athensclub
     *
     */
    public static class Token extends athensclub.compiler.token.Token {

	private String literal;

	public Token(String str, String literal) {
	    super(str);
	    this.literal = literal;
	}

	/**
	 * Get the parsed form of the string literal
	 * 
	 * @return
	 */
	public String getLiteralValue() {
	    return literal;
	}

    }

    private static class StringVisitor implements Visitor {

	private StringBuilder string;

	private StringBuilder result;

	private boolean escape;

	@Override
	public void onBegin(Tokenizer tokenizer, StringBuilder string) {
	    this.string = string;
	    result = new StringBuilder();
	}

	@Override
	public void nextChar(Tokenizer tokenizer, char next) {
	    string.append(next);
	    if (escape) {
		Character c = ACompilerUtils.escapes.get(next);
		if (c == null) {
		    throw new IllegalArgumentException("Illegal escape character: \\" + c);
		}
		result.append(c);
		escape = false;
	    } else {
		if (next == '\\') {
		    escape = true;
		} else if (next == '"') {
		    tokenizer.endVisit();
		} else {
		    result.append(next);
		}
	    }
	}

	@Override
	public Token createToken() {
	    return new Token(string.toString(), result.toString());
	}

	@Override
	public void onEnter(Tokenizer tokenizer, athensclub.compiler.token.Token token) {
	    throw new IllegalStateException("StringProvider Visitor should not be exited to other visitor.");
	}

    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if (string.length() > 0 && string.charAt(0) == '"') {
	    tokenizer.visit(new StringVisitor());
	}
	return false;
    }

    @Override
    public Token createToken(StringBuilder string) {
	throw new IllegalArgumentException(
		"StringProvider does not create token by itself, It will create token from it's visitor");
    }

}
