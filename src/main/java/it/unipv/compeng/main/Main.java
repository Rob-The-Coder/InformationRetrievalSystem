package it.unipv.compeng.main;
import it.unipv.compeng.model.index.PositionalBtreeIndex;
import it.unipv.compeng.model.index.SoundedIndex;
import it.unipv.compeng.model.term.StringTerm;

public class Main{
  public static void main(String[] args){
    String[] words={"rack", "face", "meat", "tune", "mist", "late", "ratt", "man", "drug", "peak", "suit", "fall", "spit", "cage", "high", "bear", "goat", "trap", "urge", "rush", "tape"};
    PositionalBtreeIndex btreeIndex=new PositionalBtreeIndex(2);
    SoundedIndex soundedIndex=new SoundedIndex(words.length);
    
    for(String word : words) {
      btreeIndex.addToDictionary(new StringTerm(word));
      soundedIndex.addToDictionary(new StringTerm(word));
    }//end-for
    
    btreeIndex.traverseDictionary();
    for(String word:words){
      System.out.println(soundedIndex.search(new StringTerm(word)));
    }
  }
}
