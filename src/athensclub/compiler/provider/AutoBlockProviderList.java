package athensclub.compiler.provider;

import java.util.ArrayList;
import java.util.LinkedList;

import athensclub.compiler.syntax.Block;
import athensclub.compiler.syntax.BlockProvider;
import athensclub.compiler.syntax.Parser;
import athensclub.compiler.token.Token;

/**
 * A Block provider that automatically end when another block begin is found
 * 
 * @author Athensclub
 *
 */
public class AutoBlockProviderList extends BlockProvider {

    private ArrayList<BlockProvider> providers = new ArrayList<>();

    private BlockProvider nextBlock;

    /**
     * Add a new block provider to this list
     * 
     * @param provider: a new block provider to be added this list
     */
    public void addProvider(BlockProvider... provider) {
	for (BlockProvider p : provider) {
	    providers.add(p);
	}
    }

    @Override
    public boolean matchBeginBlock(Parser parser, LinkedList<Token> statement) {
	for (BlockProvider provider : providers) {
	    if (provider.matchBeginBlock(parser, statement)) {
		if (nextBlock == null) {
		    nextBlock = provider;
		    return true;
		} else {
		    nextBlock = null;
		}
	    }
	}
	return false;
    }

    @Override
    public boolean matchEndBlock(Parser parser, LinkedList<Token> statement) {
	LinkedList<Token> tempStatement = new LinkedList<>();
	while (!statement.isEmpty()) {
	    tempStatement.addFirst(statement.removeLast());
	    for (BlockProvider provider : providers) {
		if (provider.matchBeginBlock(parser, tempStatement)) {
		    statement.addAll(tempStatement);
		    for (int i = 0; i < tempStatement.size(); i++) {
			parser.previousToken();
		    }
		    tempStatement.clear();
		    return true;
		}
	    }
	}
	statement.addAll(tempStatement);
	return false;
    }

    @Override
    public Block createBlock(LinkedList<Token> begin) {
	return nextBlock == null ? super.createBlock(begin) : nextBlock.createBlock(begin);
    }

}
