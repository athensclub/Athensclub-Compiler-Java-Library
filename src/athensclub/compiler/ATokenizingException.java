package athensclub.compiler;

/**
 * A class representing exception occurs during tokenizing
 * 
 * @author Athensclub
 *
 */
@SuppressWarnings("serial")
public class ATokenizingException extends ACompilingException {

    public ATokenizingException() {
	super();
    }

    public ATokenizingException(String str) {
	super(str);
    }

}
