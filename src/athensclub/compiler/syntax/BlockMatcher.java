package athensclub.compiler.syntax;

import java.util.LinkedList;

import athensclub.compiler.token.Token;

/**
 * Check if the given token list matches with block syntax
 * 
 * @author Athensclub
 *
 */
public interface BlockMatcher {

    /**
     * Check if the given statement matches with this block matcher's block begin
     * statement syntax
     * 
     * @param parser:
     *            the parser that is parsing the statement
     * @param statement:
     *            the statement that will use to check
     * @return if the given statement matches with this block matcher's block begin
     *         statement syntax
     */
    public boolean matchBeginBlock(Parser parser, LinkedList<Token> statement);

    /**
     * Check if the given statement matches with this block matcher's block end
     * statement syntax
     * 
     * @param parser:
     *            the parser that is parsing the statement
     * @param statement:
     *            the statement that will use to check
     * @return if the given statement matches with this block matcher's block end
     *         statement syntax
     */
    public boolean matchEndBlock(Parser parser, LinkedList<Token> statement);

}
