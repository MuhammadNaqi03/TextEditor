package fleschIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;
	
	protected Document(String text)
	{
		this.text = text;
	}
	
	
	/* 
	 * Takes a regular expression string and 
	 * Returns the tokens that match the regex pattern from the document string.
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	
	protected int countSyllables(String word)
	{
		/*
		 * Calculating syllable with the following rule:
		 * Each contiguous sequence of one or more vowels is a syllable,
		 * (Here considering y is also a member to account for)
		 * A lone "e" at the end of a word is not considered 
	     * a syllable unless the word has no other syllables.
		 */
		
		int numSyllables = 0;
		boolean newSyllable = true;
		String vowels = "aeiouy";
		char[] cArray = word.toCharArray();
		for (int i = 0; i < cArray.length; i++)
		{
		    if (i == cArray.length-1 && Character.toLowerCase(cArray[i]) == 'e' 
		    		&& newSyllable && numSyllables > 0) {
                numSyllables--;
            }
		    if (newSyllable && vowels.indexOf(Character.toLowerCase(cArray[i])) >= 0) {
				newSyllable = false;
				numSyllables++;
			}
			else if (vowels.indexOf(Character.toLowerCase(cArray[i])) < 0) {
				newSyllable = true;
			}
		}
		//System.out.println( "found " + numSyllables);
		return numSyllables;
	}

	
	
	
	
	//Returns the number of Words
	public abstract int getNumWords();
	
	//Returns the number of sentences
	public abstract int getNumSentences();
	
	//Returns the number of Syllables
	public abstract int getNumSyllables();
	
	//Returns the entire text of the document
	public String getText()
	{
		return this.text;
	}
	
	//Returns the Flesch Score of a given document(text)
	public double getFleschScore()
	{
		double numWords = (double)(getNumWords());
		double numSentences = (double)(getNumSentences());
		double numSyllables = (double)(getNumSyllables());
		double fleschScore = 206.835 - 1.015*(numWords/numSentences) - 84.6*(numSyllables/numWords);
	    return fleschScore;
	}
	
	/*
	 * For debugging
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	
}
