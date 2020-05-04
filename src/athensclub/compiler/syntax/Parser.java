package athensclub.compiler.syntax;

import java.util.ArrayList;
import java.util.LinkedList;

import athensclub.compiler.AParsingException;
import athensclub.compiler.token.Token;
import athensclub.compiler.token.Tokenizer;

/**
 * The main class used to parse code using tokens provided by tokenizer
 * 
 * @author Athensclub
 *
 */
public class Parser {

    private Tokenizer tokenizer;

    private BlockCreator globalBlockCreator = BlockCreator.DEFAULT;

    private ArrayList<BlockProvider> blockProvider = new ArrayList<>();

    private ArrayList<StatementProvider> statementProvider = new ArrayList<>();

    private LinkedList<Token> buffer = new LinkedList<>();

    private LinkedList<Token> currentStatement = new LinkedList<>();
    private LinkedList<Block> subBlocks = new LinkedList<>();

    private boolean autoCompleteOnEof;

    private boolean autoClose = true;

    /**
     * Set whether this parser will automatically end current block when it reach
     * end of file or to throw an exception
     * 
     * @param autoCompleteOnEof:
     *                               whether this parser will automatically end
     *                               current block when it reach end of file or to
     *                               throw an exception
     */
    public void setAutoCompleteOnEof(boolean autoCompleteOnEof) {
	this.autoCompleteOnEof = autoCompleteOnEof;
    }

    /**
     * Set whether this parser will automatically close the tokenizer's data stream
     * (by invoking {@link athensclub.compiler.token.Tokenizer#close()} method) when
     * it finishes parsing (when the method {@link Parser#parse()} is called and finished
     * invoking}.
     * 
     * @param autoClose
     */
    public void setAutoClose(boolean autoClose) {
	this.autoClose = autoClose;
    }

    /**
     * Create new instance of parser that parse tokens provided by tokenizer
     * 
     * @param tokenizer:
     *                       the tokenizer that is going to provide tokens for this
     *                       parser
     */
    public Parser(Tokenizer tokenizer) {
	this.tokenizer = tokenizer;
    }

    /**
     * Reset the tokenizier that is used for providing tokens for this parser to be
     * the given tokeniizer
     * 
     * @param tokenizer
     */
    public void reset(Tokenizer tokenizer) {
	this.tokenizer = tokenizer;
    }

    /**
     * Add a new type of BlockProvider to this parser ie. define new type of block
     * statement
     * 
     * @param provider
     */
    public void addBlockProvider(BlockProvider provider) {
	blockProvider.add(provider);
    }

    /**
     * Add a bew type of BlockProvider to this parser ie. define new type of
     * statement
     * 
     * @param provider
     */
    public void addStatementProvider(StatementProvider provider) {
	statementProvider.add(provider);
    }

    /**
     * push the statement token back 1 token and save it in buffer to be retrieved
     * in next statement
     */
    public void previousToken() {
	buffer.add(currentStatement.removeLast());
    }

    /**
     * Set the block creator that creates Global Block the given global block
     * creator must accept null as block begin statement
     * 
     * @param globalBlockCreator:
     *                                the block creator that will create Global
     *                                Block for this parser
     */
    public void setGlobalBlockCreator(BlockCreator globalBlockCreator) {
	this.globalBlockCreator = globalBlockCreator;
    }

    /**
     * Get the block creator that creates Global Block for this parser
     * 
     * @return the block creator that creates Global Block for this parser
     */
    public BlockCreator getGlobalBlockCreator() {
	return globalBlockCreator;
    }

    /**
     * Get the all the sub blocks that is this parser is parsing.Should be used as
     * unmodifiable list.Use peek() to see the inner-most block.
     * 
     * @return
     */
    public LinkedList<Block> getSubBlocks() {
	return subBlocks;
    }

    /**
     * Parse using token provided by tokenizer then return global block as
     * result.global block is a block which wrap everything in the given syntax
     * 
     * @return global block of the tokenizer's syntax
     * @throws Exception
     */
    public Block parse() throws Exception {
	Block global = globalBlockCreator.createBlock(null);
	currentStatement = new LinkedList<>();
	subBlocks = new LinkedList<>();
	Token current = null;
	buffer.clear();
	while (true) {
	    if (!buffer.isEmpty()) {
		current = buffer.removeLast();
	    } else {
		current = tokenizer.nextToken();
	    }

	    if (buffer.isEmpty() && current == null) {
		break;
	    }
	    currentStatement.add(current);
	    for (BlockProvider provider : blockProvider) {
		if (provider.matchBeginBlock(this, currentStatement)) {
		    Block block = provider.createBlock(currentStatement);
		    block.setProvider(provider);
		    subBlocks.push(block);
		    currentStatement = new LinkedList<>();
		} else if (provider.matchEndBlock(this, currentStatement)) {
		    if (!subBlocks.isEmpty()) {
			if (provider.equals(subBlocks.peek().getProvider())) {
			    Block finish = subBlocks.pop();
			    if (!subBlocks.isEmpty()) {
				subBlocks.peek().addStatement(finish);
			    } else {
				global.addStatement(finish);
			    }
			    currentStatement = new LinkedList<>();
			} else {
			    throw new AParsingException("Unexpected end of block: " + currentStatement);
			}
		    } else {
			throw new AParsingException("Unexpected end of block: " + currentStatement);
		    }

		}
	    }
	    if (!currentStatement.isEmpty()) {
		for (StatementProvider provider : statementProvider) {
		    if (provider.matchStatement(currentStatement)) {
			Statement statement = provider.createStatement(currentStatement);
			currentStatement = new LinkedList<>();
			if (!subBlocks.isEmpty()) {
			    subBlocks.peek().addStatement(statement);
			} else {
			    global.addStatement(statement);
			}
		    }
		}
	    }
	}
	if (!subBlocks.isEmpty()) {
	    if (subBlocks.size() == 1 && autoCompleteOnEof) {
		global.addStatement(subBlocks.pop());
	    } else {
		throw new AParsingException("Amount of begin block does not match end block");
	    }
	}
	if(autoClose) {
	    tokenizer.close();
	}
	return global;
    }

}
