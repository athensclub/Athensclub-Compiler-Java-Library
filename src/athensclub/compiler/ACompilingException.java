package athensclub.compiler;

/**
 * 
 * A class representing exceptions in a10lib's compiler framework
 * 
 * @author Athensclub
 *
 */
@SuppressWarnings("serial")
public class ACompilingException extends Exception{

    public ACompilingException() {
	super();
    }
    
    public ACompilingException(String str) {
	super(str);
    }
    
}
