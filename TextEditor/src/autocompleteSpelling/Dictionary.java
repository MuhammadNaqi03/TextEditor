
package autocompleteSpelling;

public interface Dictionary {

	/*
	 * Adds the word to the dictionary.
	 * Returns true if the word was added to the dictionary
	 * Returns false otherwise
	 */
	public abstract boolean addWord(String word);

	// Is this a word according to this dictionary? 
	public abstract boolean isWord(String s);
	
	// Returns the number of words in the dictionary 
	public abstract int size();
	
}
