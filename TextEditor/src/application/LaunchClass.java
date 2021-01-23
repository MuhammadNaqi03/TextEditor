package application;

import java.util.Random;


public class LaunchClass {
	
	public String dictFile = "data/dict.txt";
	
	public LaunchClass() {
		super();
	}
	
	public fleschIndex.Document getDocument(String text) {
		return new fleschIndex.DocumentProcessor(text);
	}
	
	public markov.MarkovTextGenerator getMTG() {
		return new markov.MarkovTextGeneratorMain(new Random());
	}
	
	
    public autocompleteSpelling.AutoComplete getAutoComplete() {
        autocompleteSpelling.AutoCompleteDictionary tr = new autocompleteSpelling.AutoCompleteDictionary();
        autocompleteSpelling.DictionaryLoader.loadDictionary(tr, dictFile);
        return tr;
    }
    
    public autocompleteSpelling.Dictionary getDictionary() {
        autocompleteSpelling.Dictionary d = new autocompleteSpelling.DictionaryBST();
        autocompleteSpelling.DictionaryLoader.loadDictionary(d, dictFile);
    	return d;
    }
    
    public autocompleteSpelling.SpellingSuggest getSpellingSuggest(autocompleteSpelling.Dictionary dic) {
    	return new autocompleteSpelling.NearbyWords(dic);
    
    }
}
