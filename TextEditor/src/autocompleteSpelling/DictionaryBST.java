package autocompleteSpelling;

import java.util.TreeSet;

public class DictionaryBST implements Dictionary 
{
   private TreeSet<String> dict;
	

   public DictionaryBST() {
	   dict = new TreeSet<String>();
   }
	
   
   /*
    * Adds word to the dictionary if it's not in it.
    * Returns true if added.
    * Returns false otherwise 
    */
    public boolean addWord(String word) {
    	String wordToFind = word.toLowerCase();
    	if(!dict.contains(wordToFind)) {
    		dict.add(wordToFind);
    		return true;
    	}
        return false;
    }


    // Return the number of words in the dictionary 
    public int size()
    {
        return dict.size();
    }

    // Is this a word according to this dictionary?
    public boolean isWord(String s) {
        return (dict.contains(s.toLowerCase()));
    }

}
