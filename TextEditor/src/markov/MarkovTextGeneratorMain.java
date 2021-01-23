package markov;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class MarkovTextGeneratorMain implements MarkovTextGenerator {

	// This list object holds objects of words and their associated next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorMain(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	//Trains the generator by adding source text
	@Override
	public void train(String sourceText)
	{
		if(sourceText.length()!= 0) {
			/*
			 * Algorithm:
			 * split the text and then assign the first elem to starter
			 * set prevWord = starter
			 * initialise another variable w for the rest of the words 
			 * if the prevWord word is new 
			 *   add the word to the list
			 * else
			 *   add the w to the nextWord list.
			 * set prevWord = w
			 *   
			 */
			
			String [] words = sourceText.split("[\\s]+");  //split base on one or more spaces
			starter = words[0];
			String prevWord = starter;
			ListNode n;
			String w;
			
			for (int i = 1; i<=words.length;i++) {
				if(words.length == i)
					w = words[0];
				else
					w = words[i];
				n = nodeFinder(prevWord);
				
				if (n != null) {
					n.addNextWord(w);
				}
				
				//For null adding the node to listNode and also add that to wordList
				else {
					n = new ListNode(prevWord);
					n.addNextWord(w);
					wordList.add(n);
				}
				prevWord = w;
				
			}
			
		}
		
	}
	
	//Generates the number of words as given
	@Override
	public String generateText(int numWords) {
		
		/*
		 * Algorithm:
		 * set randomWord to empty string
		 * set currWord to starter
		 * while the input is greater than the generated words:
		 *   get the next words and concat them
		 * return radomWord  
		 */
		String randomWord = "";
		if(numWords ==0 || wordList.size() ==0) {
			return randomWord;
		}
		
		int wordCounter = 1;
		String currWord = starter;
		randomWord += currWord;
		while(wordCounter<numWords) {
			ListNode n = nodeFinder(currWord);
			currWord = n.getRandomNextWord(rnGenerator);
			randomWord = randomWord + " " + currWord;
			wordCounter +=1;
			
		}
		return randomWord;
	}
	
	
	//For debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	//Retrain the generator from scratch on the source text
	@Override
	public void retrain(String sourceText)
	{
		//For retraining with new text
		wordList = new LinkedList<ListNode>();
		train(sourceText);
	}
	
	//Returns the node which has the word if it's already in the list else return null
	private ListNode nodeFinder(String w) {
		for (ListNode n: wordList) {
			if(n.getWord().equals(w))
				return n;
		}
		return null;
	}
	
	//For debugging
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behaviour
		MarkovTextGeneratorMain gen = new MarkovTextGeneratorMain(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/*
 * Class implementation of the ListNode which links 
 * a word to the next words in the list
 */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		/*
		 * By using the size and generator a random index is being produced
		 * Then return the word of that random index
		 */
		int size = nextWords.size();
		int randomIndex = generator.nextInt(size);
	    return nextWords.get(randomIndex);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


