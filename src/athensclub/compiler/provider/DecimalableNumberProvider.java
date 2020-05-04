package athensclub.compiler.provider;

import athensclub.compiler.token.TokenProvider;
import athensclub.compiler.token.Tokenizer;
import athensclub.compiler.token.Visitor;

/**
 * A provider that help tokenizing decimalable number.
 * 
 * <p>
 * A decimalable number is a number that can be positive or negative, can has
 * decimal point or not have one.
 * </p>
 * 
 * @author Athensclub
 *
 */
public class DecimalableNumberProvider extends TokenProvider {

    /**
     * A cache instance of {@link DecimalNumberProvider}
     */
    public static final DecimalableNumberProvider INSTANCE = new DecimalableNumberProvider();

    /**
     * Token created by decimalable number provider.
     * 
     * @author Athensclub
     *
     */
    public static class Token extends athensclub.compiler.token.Token {

	private IntegerNumberProvider.Token integeral,fractional;
	
	public Token(IntegerNumberProvider.Token integral,IntegerNumberProvider.Token fractional) {
	    super(integral.getString() + (fractional == null ? "" : "." + fractional.getString()));
	    this.integeral = integral;
	    this.fractional = fractional;
	}

	public IntegerNumberProvider.Token getFractional() {
	    return fractional;
	}
	
	public IntegerNumberProvider.Token getIntegeral() {
	    return integeral;
	}
    }
    public static class DecimalableVisitor implements Visitor {

	private IntegerNumberProvider.Token integral, fractional;

	private boolean hasDot;
	
	@Override
	public void onBegin(Tokenizer tokenizer, StringBuilder string) {
	    IntegerNumberProvider.IntegerVisitor visitor = new IntegerNumberProvider.IntegerVisitor();
	    tokenizer.setCurrent(string);
	    tokenizer.visit(visitor);
	}

	@Override
	public void onEnter(Tokenizer tokenizer, athensclub.compiler.token.Token token) {
	    if (token instanceof IntegerNumberProvider.Token) {
		IntegerNumberProvider.Token t = (IntegerNumberProvider.Token) token;
		if(integral == null) {
		    integral = t;
		}else {
		    fractional = t;
		    tokenizer.endVisit();
		}
	    } else {
		throw new IllegalStateException(
			"Decimalable visitor should not be exited to any other visitor other than Integer Number provider's visitor.");
	    }
	}

	@Override
	public void nextChar(Tokenizer tokenizer, char next) {
	   if(!hasDot) {
	       if(next == '.') {
		   hasDot = true;
	       }else {
		   tokenizer.pushPreviousChar(next);
		   tokenizer.endVisit();
	       }
	   }else {
	       if(Character.isDigit(next)) {
		   onBegin(tokenizer, new StringBuilder(Character.toString(next)));
	       }else {
		   tokenizer.pushPreviousChar(next);
		   tokenizer.pushPreviousChar('.');
	       }
	   }
	}

	@Override
	public athensclub.compiler.token.Token createToken() {
	    return new Token(integral,fractional);
	}

    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if ((string.length() == 1 && Character.isDigit(string.charAt(0)))
		|| (string.length() == 2 && string.charAt(0) == '-' && Character.isDigit(string.charAt(1)))) {
	    tokenizer.visit(new DecimalableVisitor());
	}
	return false;
    }

    @Override
    public Token createToken(StringBuilder string) {
	throw new IllegalArgumentException(
		"Decimal Number provider should not create token by itself,Insteead it should create token from its visitor");
    }

}
