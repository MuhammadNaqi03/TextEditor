package fleschIndex;

import java.util.List;

public class DocumentProcessor extends Document {

	private int numWords = 0 ;  // The number of words in the document
	private int numSentences = 0 ;  // The number of sentences in the document
	private int numSyllables = 0 ;  // The number of syllables in the document
	
	public DocumentProcessor(String text)
	{
		super(text);
		processText();
	}
	
	
	/*
	 * Takes a string which contains alphabetic characters or
	 * sentence ending punctuation.
	 * Returns true if it has alphabetic characters
	 * Returns false otherwise
	 */
	private boolean isWord(String tok)
	{
		/*
		 * if it has punctuation then it's not a word
		 */
		return !(tok.indexOf("!") >=0 || tok.indexOf(".") >=0 || tok.indexOf("?")>=0);
	}
	
	
	private void processText()
	{
		/*
		 * To find out is a sentence we have to consider two facts:
		 * 1. User uses punctuation marks to point sentence end -
		 * When we find a punctuation mark we will conclude it as a sentence
		 * 2. User forgets to put a punctuation mark -
		 * In this case we have to count the number of element in the list
		 * If it's the same as the size of the list, then we reach at the very end 
		 */
		
		//Splitting words into punctuation and characters
		List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
		int numOfElem = 0;

		for(String token: tokens) {
			//For each element the numOfElem will be increased
			numOfElem +=1;
			
			if(isWord(token)) {

				numWords +=1;
				numSyllables += countSyllables(token);
				
				//For Sentence without punctuation at the very end
				if(tokens.size() == numOfElem) {
					numSentences +=1;
					
				}
				
			}
			
			// If we find punctuation mark then the we reach the sentence end.
			else {
				numSentences +=1;

			}
			
		}
		
	}

	
	@Override
	public int getNumSentences() {
		return numSentences;
	}


	@Override
	public int getNumWords() {
	    return numWords;
	}


	@Override
	public int getNumSyllables() {
        return numSyllables;
	}
	

	public static void main(String[] args)
	{
		//For debugging
	    testCase(new DocumentProcessor("This is a test.  How many???  "
                + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new DocumentProcessor(""), 0, 0, 0);
        testCase(new DocumentProcessor("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new DocumentProcessor("many???  Senteeeeeeeeeences are"), 6, 3, 2); 
        testCase(new DocumentProcessor("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		testCase(new DocumentProcessor("Segue"), 2, 1, 1);
		testCase(new DocumentProcessor("Sentence"), 2, 1, 1);
		testCase(new DocumentProcessor("Sentences?!"), 3, 1, 1);
		testCase(new DocumentProcessor("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
		testCase(new DocumentProcessor("Time to get weeeeeeeeeeeird, with, the. Punctuation"),
		         9, 7, 2);
		//
	}
	

}
