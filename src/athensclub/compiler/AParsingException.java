package athensclub.compiler;

/**
 * A class representing exception of a10lib's compiler framework in
 * {@code a10lib.compiler.syntax} package
 * or {@code a10lib.compiler.ACompilingException} that occurs in
 * {@code a10lib.compiler.syntax.Parser} class
 * 
 * 
 * @author Athensclub
 *
 */
public class AParsingException extends ACompilingException {

    /**
     * 
     */
    private static final long serialVersionUID = 6857357703131421554L;

    public AParsingException() {
	super();
    }

    public AParsingException(String str) {
	super(str);
    }

}
