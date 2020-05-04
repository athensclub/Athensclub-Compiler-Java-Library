package athensclub.compiler.syntax;


import java.util.LinkedList;

import athensclub.compiler.token.Token;

/**
 * A class representing a statement
 * 
 * @author Athensclub
 *
 */
public class Statement {

    private LinkedList<Token> statement = new LinkedList<>();

    public Statement() {
    }

    public Statement(LinkedList<Token> statement) {
	this.statement = statement;
    }

    /**
     * Get all the token that made up to be this statement
     * 
     * @return all the token that made up to be this statement
     */
    public LinkedList<Token> getStatement() {
	return statement;
    }

    @Override
    public String toString() {
	return statement.toString();
    }

}
