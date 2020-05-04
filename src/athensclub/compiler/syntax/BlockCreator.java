package athensclub.compiler.syntax;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import athensclub.compiler.token.Token;

/**
 * Create a Block syntax type
 * 
 * @author Athensclub
 *
 */
public interface BlockCreator {

    public static final BlockCreator DEFAULT = new BlockCreator() {
    };

    /**
     * Create block with the given begin statement
     * 
     * @param blockBegin:
     *            the begin statement of the block
     * @return block with the given begin statement
     */
    public default Block createBlock(LinkedList<Token> blockBegin) {
	return new Block();
    }

    /**
     * Create the block creator that will create an empty block of the given block
     * class type for example if you have class {@code SomeBlock extends Block{...}}
     * then you can use this method to create BlockCreator that create
     * {@code SomeBlock} using its default constructor
     * 
     * @param clazz:
     *            a type of Block created by blockCreator
     * @return the block creator that will create an empty block of the given block
     */
    public static <T extends Block> BlockCreator defaultInstanceOf(Class<T> clazz) {
	return new BlockCreator() {
	    @Override
	    public Block createBlock(LinkedList<Token> begin) {
		try {
		    return clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
			| InvocationTargetException | NoSuchMethodException | SecurityException e) {
		    e.printStackTrace();
		    return null;
		}
	    }
	};
    }

}
