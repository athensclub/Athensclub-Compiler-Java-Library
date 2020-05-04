package athensclub.compiler.provider;

import athensclub.compiler.provider.FractionableNumberProvider.FractionableVisitor;
import athensclub.compiler.token.Tokenizer;
import athensclub.compiler.token.Visitor;

/**
 * A provider helping in tokenizing complexable number.
 * 
 * <p>
 * Complexable number is either fractionable number or fraction number added or
 * subtracted by fractionable number.
 * </p>
 * 
 * @author Athensclub
 *
 */
public class ComplexableNumberProvider extends athensclub.compiler.token.TokenProvider {

    /**
     * A cache instance of complexable number provider
     */
    public static final ComplexableNumberProvider INSTANCE = new ComplexableNumberProvider();

    /**
     * Token created by complexable number provider.
     * 
     * @author Athensclub
     *
     */
    public static class Token extends athensclub.compiler.token.Token {

	private FractionableNumberProvider.Token real, imaginary;

	private boolean subtract;

	public Token(FractionableNumberProvider.Token r, FractionableNumberProvider.Token im, boolean isSubtract) {
	    super(r + (im == null ? "" : (isSubtract ? "-" : "+") + im + "i"));
	    this.real = r;
	    this.imaginary = im;
	    subtract = isSubtract;
	}

	/**
	 * Check whether this complex number is in form of a+bi(false) or a-bi(true)
	 * where b is positive in both cases.
	 * 
	 * @return
	 */
	public boolean isSubtraction() {
	    return subtract;
	}

	/**
	 * Get the real number part of this complex number. Never be null.
	 * 
	 * @return
	 */
	public FractionableNumberProvider.Token getReal() {
	    return real;
	}

	/**
	 * Get the imaginary number part of this complex number. Can be null if can not
	 * find imaginary number part.
	 * 
	 * @return
	 */
	public FractionableNumberProvider.Token getImaginary() {
	    return imaginary;
	}

    }

    public static class ComplexableVisitor implements Visitor {

	private FractionableNumberProvider.Token real, imaginary;

	private char sign = 0;

	@Override
	public void onBegin(Tokenizer tokenizer, StringBuilder string) {
	    FractionableVisitor visitor = new FractionableVisitor();
	    tokenizer.setCurrent(string);
	    tokenizer.visit(visitor);
	}

	@Override
	public void onEnter(Tokenizer tokenizer, athensclub.compiler.token.Token token) {
	    if (token instanceof FractionableNumberProvider.Token) {
		if (real == null) {
		    real = (FractionableNumberProvider.Token) token;
		    if (tokenizer.eof()) {
			tokenizer.endVisit();
		    }
		} else {
		    imaginary = (FractionableNumberProvider.Token) token;
		}
	    } else {
		throw new IllegalStateException(
			"Complexable Visitor should not be exit to other visitor other than Fractionable Visior: "
				+ token);
	    }
	}

	@Override
	public void nextChar(Tokenizer tokenizer, char next) {
	    if (sign == 0) {
		if (next == '+' || next == '-') {
		    sign = next;
		} else {
		    tokenizer.pushPreviousChar(next);
		    tokenizer.endVisit();
		}
	    } else {
		if (imaginary == null) {
		    if (Character.isDigit(next)) {
			StringBuilder str = new StringBuilder();
			str.append(next);
			onBegin(tokenizer, str);
		    } else {
			tokenizer.pushPreviousChar(next);
			tokenizer.pushPreviousChar(sign);
			tokenizer.endVisit();
		    }
		} else {
		    if (next != 'i') {
			tokenizer.pushPreviousChar(next);
			String topushback = imaginary.getString();
			for (int i = topushback.length() - 1; i >= 0; i--) {
			    tokenizer.pushPreviousChar(topushback.charAt(i));
			}
			tokenizer.pushPreviousChar(sign);
			imaginary = null;
		    }
		    tokenizer.endVisit();
		}
	    }
	}

	@Override
	public athensclub.compiler.token.Token createToken() {
	    return new Token(real, imaginary, sign == '-');
	}

    }

    @Override
    public boolean matchToken(Tokenizer tokenizer, StringBuilder string) {
	if ((string.length() == 1 && Character.isDigit(string.charAt(0)))
		|| (string.length() == 2 && string.charAt(0) == '-' && Character.isDigit(string.charAt(1)))) {
	    tokenizer.visit(new ComplexableVisitor());
	}
	return false;
    }

    @Override
    public athensclub.compiler.token.Token createToken(StringBuilder string) {
	throw new IllegalStateException(
		"Complexable Number provider should not create token by itself,Instead it should create token from its visitor.");
    }

}
