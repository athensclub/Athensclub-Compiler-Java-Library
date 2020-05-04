package athensclub.compiler.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import athensclub.compiler.syntax.Block;
import athensclub.compiler.syntax.BlockCreator;
import athensclub.compiler.syntax.BlockProvider;
import athensclub.compiler.syntax.Parser;
import athensclub.compiler.token.Token;

/**
 * A provider that will match statement with list of strings example: if
 * statement is ["a","b","c"] and string list is ["s"] then this return false
 * but if statement is ["d"] and string list is ["d"] then this return true
 * 
 * @author Athensclub
 *
 */
public class StringListBlockProvider extends BlockProvider {

    private ArrayList<String> begin = new ArrayList<>();

    private ArrayList<String> end = new ArrayList<>();

    private BlockCreator creator;

    /**
     * Append statement string to the begin statement
     * 
     * @param statement:
     *            statement sting to be appended to begin statement
     */
    public void addBeginStringStatement(String... statement) {
	for (String str : statement) {
	    begin.add(str);
	}
    }

    /**
     * Append statement string to the end statement
     * 
     * @param statement:
     *            statement sting to be appended to end statement
     */
    public void addEndStringStatement(String... statement) {
	for (String str : statement) {
	    end.add(str);
	}
    }

    /**
     * Set the {@code BlockCreator} for this block type if the block matches
     * 
     * @param creator:
     *            the {@code BlockCreator} for this block type if the block matches
     */
    public void setCreator(BlockCreator creator) {
	this.creator = creator;
    }

    @Override
    public boolean matchBeginBlock(Parser parser, LinkedList<Token> statement) {
	if (statement.size() != begin.size()) {
	    return false;
	}
	Iterator<Token> it = statement.iterator();
	int i = 0;
	while (it.hasNext()) {
	    String next = ((Token) it.next()).getString();
	    if (!begin.get(i).equals(next)) {
		return false;
	    }
	    i++;
	}
	return true;
    }

    @Override
    public boolean matchEndBlock(Parser parser, LinkedList<Token> statement) {
	if (statement.size() != end.size()) {
	    return false;
	}
	Iterator<Token> it = statement.iterator();
	int i = 0;
	while (it.hasNext()) {
	    String next = ((Token) it.next()).getString();
	    if (!end.get(i).equals(next)) {
		return false;
	    }
	    i++;
	}
	return true;
    }

    @Override
    public Block createBlock(LinkedList<Token> begin) {
	return creator == null ? super.createBlock(begin) : creator.createBlock(begin);
    }

}
