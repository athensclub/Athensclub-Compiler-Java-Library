package athensclub.compiler.provider;

import athensclub.compiler.token.TokenProvider;
import athensclub.compiler.token.Tokenizer;
import athensclub.compiler.token.Visitor;

/**
 * A provider that test for integer number
 * 
 * <p>
 * A integer number is a number that has no decimal value and can be positive or
 * negative.
 * </p>
 * 
 * @author Athensclub
 *
 */
public class IntegerNumberProvider extends TokenProvider {

    /**
     * A cached instance of integer number provider.
     */
    public static final IntegerNumberProvider INSTANCE = new IntegerNumberProvider();

    /**
     * Token created by integer number provider.
     * 
     * @author Athensclub
     *
     */
    public static class Token extends athensclub.compiler.token.Token {

	public Token(String str) {
	    super(str);
	}

    }

    public static class IntegerVisitor implements Visitor {

	private StringBuilder num;

	@Override
	public void onBegin(Tokenizer tokenizer, StringBuilder string) {
	    num = string;
	    if(tokenizer.eof()) {
		tokenizer.endVisit();
	    }
	}

	@Override
	public void onEnter(Tokenizer tokenizer, athensclub.compiler.token.Token token) {
	    throw new IllegalStateException("Integer Visitor should not be exited to another visitor.");
	}

	@Override
	public void nextChar(Tokenizer tokenizer, char next) {
	    if (Character.isDigit(next)) {
		num.append(next);
	    } else {
		tokenizer.pushPreviousChar(next);
		tokenizer.endVisit();
	    }
	}

	@Override
	public athensclub.compiler.token.Token createToken() {
	    return new Token(num.toString());
	}

    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if ((string.length() == 1 && Character.isDigit(string.charAt(0)))
		|| (string.length() == 2 && string.charAt(0) == '-' && Character.isDigit(string.charAt(1)))) {
	    tokenizer.visit(new IntegerVisitor());
	}
	return false;
    }

    @Override
    public athensclub.compiler.token.Token createToken(StringBuilder string) {
	throw new IllegalStateException(
		"Integer Number provider should not create toke by itself,Instead it should create token from its visitor.");

    }

}
