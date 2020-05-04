package athensclub.compiler.syntax;

import java.util.LinkedList;

import athensclub.compiler.token.Token;

/**
 * Interface responsible for making classes able to create statement
 * 
 * @author Athensclub
 *
 */
public interface StatementCreator {

    /**
     * Create new statement of this creator's wanted statement type with given list
     * of token as statement
     * 
     * @param statement:
     *            list of token representing statement
     * @return new statement of this creator's wanted statement type with given list
     *         of token as statement
     */
    public default Statement createStatement(LinkedList<Token> statement) {
	return new Statement(statement);
    }

}
