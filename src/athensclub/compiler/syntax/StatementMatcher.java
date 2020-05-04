package athensclub.compiler.syntax;

import java.util.LinkedList;

import athensclub.compiler.token.Token;

/**
 * Interface responsible for making classes be able to match statement with
 * their wanted statement type
 * 
 * @author Athensclub
 *
 */
public interface StatementMatcher {

    /**
     * Return whether the given list of tokens matches with this statement type's
     * syntax
     * 
     * @param tokens:
     *            the list of token to be matched
     * @return whether the given list of tokens matches with this statement type's
     *         syntax
     */
    public boolean matchStatement(LinkedList<Token> tokens);

}
