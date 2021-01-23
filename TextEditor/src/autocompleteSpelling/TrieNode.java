package autocompleteSpelling;

import java.util.HashMap;
import java.util.Set;

class TrieNode {
	private HashMap<Character, TrieNode> children; 
	private String text;  
	private boolean isWord;
	
	// Creates a new TrieNode 
	public TrieNode()
	{
		children = new HashMap<Character, TrieNode>();
		text = "";
		isWord = false;
	}
	
	// Create a new TrieNode given a text String to store in it 
	public TrieNode(String text)
	{
		this();
		this.text = text;
	}
	
	/*
	 * Takes the next character as input and 
	 * Returns the TrieNode that is the child when you follow the 
	 * link from the given Character
	 */
	public TrieNode getChild(Character c)
	{
		return children.get(c);
	}
	
	/*
	 * Takes a character as input and 
	 * Returns the newly created node, if c wasn't already
	 * in the trie. returns null otherwise.
	 */
	public TrieNode insert(Character c)
	{
		if (children.containsKey(c)) {
			return null;
		}
		
		TrieNode next = new TrieNode(text + c.toString());
		children.put(c, next);
		return next;
	}
	
	// Returns the text string at this node 
    public String getText()
	{
		return text;
	}
	
    // Sets whether or not this node ends a word in the trie. 
	public void setEndsWord(boolean b)
	{
		isWord = b;
	}
	
	// Returns whether or not this node ends a word in the trie. 
	public boolean endsWord()
	{
		return isWord;
	}
	
	// Returns the set of characters that have links from this node 
	public Set<Character> getValidNextCharacters()
	{
		return children.keySet();
	}

}

