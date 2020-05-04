package athensclub.compiler.token;

import java.io.IOException;
import java.io.Reader;

/**
 * A {@code Tokenizer} class that tokenizes a stream of strings from
 * {@code Reader}
 * 
 * @author Athensclub
 *
 */
public class StreamTokenizer extends Tokenizer {

    private boolean eof;

    private Reader input;

    /**
     * Create tokenizer that tokenize the string from the stream
     * 
     * @param stream:
     *                    the stream that is going to get tokenized
     */
    public StreamTokenizer(Reader stream) {
	input = stream;
    }

    /**
     * Reset the stream to be tokenized to be the given stream
     * 
     * @param reader
     */
    public void reset(Reader reader) {
	input = reader;
    }
    
    @Override
    public void close() throws IOException {
        input.close();
    }

    @Override
    protected char nextCharInStream() throws IOException {
	try {
	    int next = input.read();
	    if(next == -1) {
		throw new IOException("Eof");
	    }
	    return (char) next;
	} catch (IOException e) {
	    eof = true;
	    throw e;
	}
    }

    @Override
    public boolean eof() {
	return eof;
    }

}
