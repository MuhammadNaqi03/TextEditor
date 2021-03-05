
package autocompleteSpelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class NearbyWords implements SpellingSuggest {
	
	/*Threshold is for optimisation cause some words may not 
	 * satisfy the required number of suggestions.
	 * Thus it will break through the loop after threshold meets.
	 */
	
	private static final int THRESHOLD = 50000; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	
	/*
	 * Returns a list of Strings that are one modification 
	 * away from the original input string 
	 * wordsOnly is used to return only end word.
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> nearbyWordsList = new ArrayList<String>();
		   insertions(s, nearbyWordsList, wordsOnly);
		   substitution(s, nearbyWordsList, wordsOnly);
		   deletions(s, nearbyWordsList, wordsOnly);
		   return nearbyWordsList;
	}

	
	/*
	 * Adds the word to the nearbyWordsList which are 
	 * one distance away from the original string
	 * by substitution
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				
				//StringBuffer is used for easy insertion
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				/* if the item isn't in the list, isn't the original string, and
				 * is a real word and also wordsOnly is true then, add to the list
				 */
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}
	
	/*
	 * Adds the word to the nearbyWordsList which are 
	 * one distance away from the original string
	 * by insertion 
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		
		// for each letter in the s and for all possible insertion of characters
		for(int index = 0; index <= s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				
				//StringBuffer is used for easy insertion
				StringBuffer sb = new StringBuffer(s);
				sb.insert(index, (char)charCode);

				/* if the item isn't in the list, isn't the original string, and
				 * is a real word and also wordsOnly is true then, add to the list
				 */
		
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}

	/*
	 * Adds the word to the nearbyWordsList which are 
	 * one distance away from the original string
	 * by deletions 
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		
		for (int index = 0; index<s.length(); index++) {
			StringBuffer sb = new StringBuffer(s);
			sb.deleteCharAt(index);

			/* if the item isn't in the list, isn't the original string, and
			 * is a real word and also wordsOnly is true then, add to the list
			 */
			if(!currentList.contains(sb.toString()) && 
					(!wordsOnly||dict.isWord(sb.toString())) &&
					!s.equals(sb.toString())) {
				currentList.add(sb.toString());
			}
		}
	}

	/*
	 * Takes a word and the number of words to suggest as input
	 * Returns list of suggested words one mutation away from the
	 * original one
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {
		/*
		 * Create a queue to hold words to explore
         * Create a visited set to avoid looking at the same String repeatedly
         * Create list of real words to return when finished
         * Add the initial word to the queue and visited
         * while the queue has elements and we need more suggestions
         *   remove the word from the start of the queue and assign to current word
         *   get a list of neighbours (strings one mutation away from current word) 
         *   for each n in the list of neighbours:
         *     add n to the visited set
         *     add n to the back of the queue
         *     if n is a word in the dictionary:
         *       add n to the list of words to return
         * return the list of real words
		 */
		

		List<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same string multiple times
		List<String> wordsToSuggest = new LinkedList<String>();   // words to return
		List<String> distancedNeighbours = new LinkedList<String>(); //for adding neighbours for each words
		
		int wordCount = 0;   
		int thresholdCount = 0;
		
		// inserting first node
		queue.add(word);
		visited.add(word);
					
		/*will continue as long as the loop is not empty
		 * or wordCount becomes greater or equal numSuggestions
		 * or thresholdCount becomes greater or equal threshold
		 */
		while(!queue.isEmpty() && wordCount < numSuggestions && thresholdCount < THRESHOLD) {
			String currentWord = queue.remove(0); //FIFO
			//Find out the words 1 char distanced from the given word
			distancedNeighbours = distanceOne(currentWord,false);
			
			for (String dNeighbour: distancedNeighbours) {
				if(!visited.contains(dNeighbour)) {
					visited.add(dNeighbour);
					queue.add(dNeighbour);
					

					if(dict.isWord(dNeighbour)) {
						wordsToSuggest.add(dNeighbour);
						wordCount++;
						
					}
				}
				thresholdCount++;
			}
		}
		return wordsToSuggest;

	}	

   public static void main(String[] args) {
	   
	   //For debugging purpose
	   Dictionary d = new DictionaryBST();
	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
	   NearbyWords w = new NearbyWords(d);

	   String word = "goo";
	   List<String> l = w.distanceOne(word, true);
	   List<String> suggest = w.suggestions(word, 10);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);
	   
	   word = "dayy";
	   List<String> suggest2 = w.suggestions(word, 7);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest2);
	   
	   word = "beautifull";
	   List<String> suggest3 = w.suggestions(word, 5);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest3);
   }

}
