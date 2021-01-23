package autocompleteSpelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class AutoCompleteDictionary implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionary()
	{
		root = new TrieNode();
	}
	
	
	
    //Inserts the new word into the trie
	public boolean addWord(String word)
	{
		/*
	     * Algorithm:
	     * Searches if a word in a trie.
	     * It searches character by character and if it finds the word
	     * then doesn't add the word and returns false
	     * otherwise it adds the characters to trie and returns true.
	     */
		
		String wordLower = word.toLowerCase();
		char [] wordChar = wordLower.toCharArray();
		TrieNode curr = root; // We have to keep the root reference intact thus using a temporary node
		
		for (int i =0; i<wordChar.length; i++) {
			if (curr.getValidNextCharacters().contains(wordChar[i])){
				
				//assigning the curr to next node if we find a valid char within the trie
				curr = curr.getChild(wordChar[i]);		
			}
			else {
				
				//adding the char(s) if we didn't find it in the trie
				curr = curr.insert(wordChar[i]);
			}
			
		}
		
		//setting the word as an end word if it's not being one already
		if(!(curr.endsWord())) {
			curr.setEndsWord(true);
			size++;
			return true;	
		}
		
	    return false;
	}
	
	/*
	 * Returns the number of words stored in the dictionary.
	 * This is not necessarily the same as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/*
	 * Checks whether the given string is a word in the trie or not 
	 * return true if it is 
	 * return false otherwise
	 */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		String wordLower = s.toLowerCase();
		char [] wordChar = wordLower.toCharArray();
		TrieNode curr = root; 
		
		for (int i =0; i<wordChar.length; i++) {
			if (curr.getValidNextCharacters().contains(wordChar[i])){
				curr = curr.getChild(wordChar[i]);		
			}
			else {
				return false;
			}
		}
		return (curr.endsWord());
					
	}


	/*
	 * Takes a string prefix and number of completed words
	 * Returns a list of completed words
	 */
	 @Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	/*
    	 * This method should implement the following algorithm:
    	 * Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 * empty list
    	 * Once the stem is found, perform a breadth first search to generate completions
    	 * using the following algorithm:
    	 * Create a queue (LinkedList) and add the node that completes the stem to the back
    	 * of the list.
    	 * Create a list of completions to return (initially empty)
    	 * While the queue is not empty and you don't have enough completions:
    	 *   remove the first Node from the queue
    	 *   If it is a word, add it to the completions list
    	 *     Add all of its child nodes to the back of the queue
    	 * Return the list of completions
    	 */
    	 
    	 
    	 //Checking if the prefix is valid (it's in the trie)
    	 String prefixLower = prefix.toLowerCase();
    	 char [] prefixArr = prefixLower.toCharArray();
    	 TrieNode curr = root;
    	 List <String> suggestedWords = new LinkedList<String>();
    	 int wordCount = 0;
    	 
    	 for (int i = 0; i<prefixArr.length; i++) {
    		 if(curr.getValidNextCharacters().contains(prefixArr[i])) {
    			 curr = curr.getChild(prefixArr[i]);
    		 }
    		 else {
    			 return suggestedWords;
    		 }
    	 }
    	 
    	 //if it's an end word then add it to suggestedWords
    	 if(curr.endsWord()) {
    		 suggestedWords.add(curr.getText());
    		 wordCount +=1;
    	 }
    	 
    	 //Queue for holding the trieNodes and character list for holding the next valid characters
    	 List <TrieNode> nodeQueue = new LinkedList<TrieNode>();
    	 List <Character> validChildren = new LinkedList<Character>(curr.getValidNextCharacters());
    	 
    	 //Add the valid chars to the queue
    	 for (int i = 0; i<validChildren.size(); i++) {
    		 char validChild = validChildren.get(i);
    		 nodeQueue.add(curr.getChild(validChild));
    		 
    	 }
    	 
    	 //getting the elements back when the queue is not empty
    	 while(wordCount < numCompletions && !(nodeQueue.isEmpty())) {
    		 TrieNode removedNode = nodeQueue.remove(0);
    		 if(removedNode.endsWord()) {
    			 suggestedWords.add(removedNode.getText());
    			 wordCount +=1;
    		 }
    		 
    		 List <Character> newChildren = new LinkedList<Character>(removedNode.getValidNextCharacters());
    		 
    		//Add the new valid chars of the removed node to the end of the queue
        	 for (int i = 0; i<newChildren.size(); i++) {
        		 char validChild = newChildren.get(i);
        		 nodeQueue.add(removedNode.getChild(validChild));
        	 }
    	 }
    	 
         return suggestedWords;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	//Pre-order traversal from the curr node
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
	
}