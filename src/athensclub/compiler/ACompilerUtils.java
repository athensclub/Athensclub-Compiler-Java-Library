package athensclub.compiler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utilities class for the library.
 * 
 * @author Athensclub
 *
 */
public final class ACompilerUtils {

    /**
     * A map of escape character to the character that compiler will create is
     * escaped using the said escape character.
     */
    public static final Map<Character, Character> escapes;

    /**
     * Inversed to value to key mapping of escapes
     */
    public static final Map<Character, Character> escapesInversed;

    static {
	HashMap<Character, Character> e = new HashMap<>();
	e.put('b', '\b');
	e.put('n', '\n');
	e.put('t', '\t');
	e.put('r', '\r');
	e.put('f', '\f');
	e.put('"', '"');
	e.put('\\', '\\');
	escapes = Collections.unmodifiableMap(e);
	escapesInversed = e.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    private ACompilerUtils() {
    }

}
