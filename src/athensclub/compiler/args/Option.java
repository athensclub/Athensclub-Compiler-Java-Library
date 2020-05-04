package athensclub.compiler.args;

/**
 * An option. An option will has name starts with "-" and followed by its name
 * and followed by value.
 * 
 * @author Athensclub
 *
 */
public class Option {

    private String name, value;

    /**
     * Create new option
     */
    public Option(String n, String val) {
	name = n;
	value = val;
    }

    public String getName() {
	return name;
    }

    public String getValue() {
	return value;
    }

}
