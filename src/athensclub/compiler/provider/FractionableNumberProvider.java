package athensclub.compiler.provider;

import athensclub.compiler.provider.DecimalableNumberProvider.DecimalableVisitor;
import athensclub.compiler.token.TokenProvider;
import athensclub.compiler.token.Tokenizer;
import athensclub.compiler.token.Visitor;

/**
 * A class that help tokenizing a decimal number with and without fraction.
 * <p>
 * A fractionable number is either a decimal number or a decimal number divided
 * by decimal number.
 * </p>
 * 
 * @see DecimalableNumberProvider
 * 
 * @author Athensclub
 *
 */
public class FractionableNumberProvider extends TokenProvider {

    /**
     * A cache instance of {@link FractionableNumberProvider}
     */
    public static final FractionableNumberProvider INSTANCE = new FractionableNumberProvider();

    /**
     * Token created by this fractionable number provider.
     * 
     * @author Athensclub
     *
     */
    public static class Token extends athensclub.compiler.token.Token {

	private DecimalableNumberProvider.Token numerator, denominator;

	public Token(DecimalableNumberProvider.Token num, DecimalableNumberProvider.Token denom) {
	    super(num + (denom == null ? "" : "/" + denom));
	    this.numerator = num;
	    this.denominator = denom;
	}

	/**
	 * Get the numerator of this token.Never be null, if this is not fraction, it
	 * will be decimalable token
	 * 
	 * @return
	 */
	public DecimalableNumberProvider.Token getNumerator() {
	    return numerator;
	}

	/**
	 * Get the denominator of this token.Will be null if it can not find the
	 * denominator.
	 * 
	 * @return
	 */
	public DecimalableNumberProvider.Token getDenominator() {
	    return denominator;
	}

    }

    public static class FractionableVisitor implements Visitor {

	private DecimalableNumberProvider.Token num, denom;

	private boolean expectNumber;

	private boolean hasMinus;

	@Override
	public void onBegin(Tokenizer tokenizer, StringBuilder string) {
	    DecimalableVisitor visitor = new DecimalableVisitor();
	    tokenizer.setCurrent(string);
	    tokenizer.visit(visitor);
	}

	@Override
	public void onEnter(Tokenizer tokenizer, athensclub.compiler.token.Token token) {
	    if (token instanceof DecimalableNumberProvider.Token) {
		if (num == null) {
		    num = (DecimalableNumberProvider.Token) token;
		    if (tokenizer.eof()) {
			tokenizer.endVisit();
		    }
		} else {
		    denom = (DecimalableNumberProvider.Token) token;
		    tokenizer.endVisit();
		}
	    } else {
		throw new IllegalStateException(
			"Fractionable Visitor should not exit to any other visitor other than Decimal Visitor.");
	    }
	}

	@Override
	public void nextChar(Tokenizer tokenizer, char next) {
	    if (!expectNumber) {
		if (next == '/') {
		    expectNumber = true;
		} else {
		    tokenizer.pushPreviousChar(next);
		    tokenizer.endVisit();
		}
	    } else {
		if (next == '-') {
		    if (!hasMinus) {
			hasMinus = true;
		    } else {
			tokenizer.pushPreviousChar(next); // duplicate '-'
			tokenizer.pushPreviousChar('-'); // The first valid (now invalid) '-'
			tokenizer.pushPreviousChar('/');
			tokenizer.endVisit();
		    }
		} else if (Character.isDigit(next)) {
		    StringBuilder str = new StringBuilder();
		    if (hasMinus) {
			str.append('-');
		    }
		    str.append(next);
		    onBegin(tokenizer, str);
		} else {
		    tokenizer.pushPreviousChar(next);
		    if (hasMinus) {
			tokenizer.pushPreviousChar('-');
		    }
		    tokenizer.pushPreviousChar('/');
		    tokenizer.endVisit();
		}
	    }
	}

	@Override
	public athensclub.compiler.token.Token createToken() {
	    return new Token(num, denom);
	}

    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if ((string.length() == 1 && Character.isDigit(string.charAt(0)))
		|| (string.length() == 2 && string.charAt(0) == '-' && Character.isDigit(string.charAt(1)))) {
	    tokenizer.visit(new FractionableVisitor());
	}
	return false;
    }

    @Override
    public Token createToken(StringBuilder string) {
	throw new IllegalStateException(
		"Fractionable Number provider should not create toke by itself,Instead it should create token from its visitor.");
    }

}
