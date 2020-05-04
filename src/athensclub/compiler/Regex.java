package athensclub.compiler;

import java.util.regex.Pattern;

/**
 * A utilities class that has predefined regular expression for matching common
 * expressions
 * 
 * @author Athensclub
 *
 */
public class Regex {

    /**
     * A regex that test for a number.
     * <p>
     * A number can be negative which has negative sign in front of it then any
     * number of digits then optionally has a floating point with any number of
     * digits.Should be used to match entire number string
     * </p>
     */
    public static final String NUMBER_REGEX = "-?[0-9]+(\\.[0-9]+)?";

    public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

    /**
     * A regex that test for number that can has fraction.
     * <p>
     * A number can be negative which has negative sign in front of it then any
     * number of digits then optionally has a floating point with any number of
     * digits.Should be used to match entire number string
     * </p>
     * <p>
     * A fractionable number can be the number stated above or a number divided by
     * other number.For example -8.23/2.56
     * <p>
     */
    public static final String FRACTIONABLE_NUMBER_REGEX = NUMBER_REGEX + "(/" + NUMBER_REGEX + ")?";

    public static final Pattern FRACTIONABLE_NUMBER_PATTERN = Pattern.compile(FRACTIONABLE_NUMBER_REGEX);

    /**
     * A regex that test for number that can be complex.
     * <p>
     * A number can be negative which has negative sign in front of it then any
     * number of digits then optionally has a floating point with any number of
     * digits.Should be used to match entire number string
     * </p>
     * <p>
     * A fractionable number can be the number stated above or a number divided by
     * other number.For example -8.23/2.56
     * <p>
     * <p>
     * complexable number can be fractionable number or fraction number added by or
     * subtracted by fractionable number multiplied by constant i. For example:
     * 8.4+3/2i
     * </p>
     */
    public static final String COMPLEXABLE_NUMBER_REGEX = FRACTIONABLE_NUMBER_REGEX + "([\\+-]"
	    + FRACTIONABLE_NUMBER_REGEX + "i)?";

    public static final Pattern COMPLEXABLE_NUMBER_PATTERN = Pattern.compile(COMPLEXABLE_NUMBER_REGEX);

    /**
     * A regex that test for racket identifier
     * <p>
     * A racket identifier is a token that can be used in racket to declare a
     * variable of any types (function,number,string etc.)
     * </p>
     */
    public static final String RACKET_IDENTIFIER_REGEX = "[a-zA-Z\\+\\-\\*/_>=<?][a-zA-Z\\+\\-\\*/0-9_>=<?]*";

    public static final Pattern RACKET_IDENTIFIER_PATTERN = Pattern.compile(RACKET_IDENTIFIER_REGEX);

    /**
     * Check if the entire region of the given CharSequence match the given pattern.
     * 
     * @param cs
     * @param p
     * @return
     */
    public static boolean matches(CharSequence cs, Pattern p) {
	return p.matcher(cs).matches();
    }

}
