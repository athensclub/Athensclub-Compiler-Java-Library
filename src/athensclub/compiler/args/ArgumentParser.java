package athensclub.compiler.args;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A parser that parses java program arguments.
 * 
 * @author Athensclub
 *
 */
public class ArgumentParser {

    private Set<String> available = new HashSet<>();

    /**
     * Add new available option to this parser. name must start with "-" and should
     * not have white space
     * 
     * @param name
     */
    public void addAvailableOption(String... n) {
	for (String name : n) {
	    if (!name.startsWith("-")) {
		throw new IllegalArgumentException("Option does not start with '-': " + name);
	    }
	    available.add(name);
	}
    }

    private boolean checkDuplicate(String str, ArrayList<Option> current) {
	for (Option o : current) {
	    if (o.getName().equals(str)) {
		return true;
	    }
	}
	return false;
    }

    public List<Option> parse(String[] args) {
	ArrayList<Option> result = new ArrayList<>();
	Iterator<String> it = Arrays.asList(args).iterator();
	while (it.hasNext()) {
	    String str = it.next();
	    if (checkDuplicate(str, result)) {
		throw new IllegalArgumentException("Duplicate option: " + str);
	    }
	    if (str.startsWith("-")) {
		if (!it.hasNext()) {
		    throw new IllegalArgumentException("Option does not has parameter: " + str);
		}
		String param = it.next();
		if (param.startsWith("-")) {
		    throw new IllegalArgumentException(
			    "Option is followed by another option without parameter: " + str);
		}
		result.add(new Option(str, param));
	    } else {
		throw new IllegalArgumentException("Unexpected option: " + str);
	    }
	}
	return Collections.unmodifiableList(result);
    }

}
