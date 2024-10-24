package it.unipv.compeng.main;
import it.unipv.compeng.model.index.PositionalBtreeIndex;
import it.unipv.compeng.model.term.StringTerm;

public class Main{
  public static void main(String[] args){
    PositionalBtreeIndex btreeIndex=new PositionalBtreeIndex(2);
    String[] words={"rack", "face", "meat", "tune", "mist", "late", "ratt", "man", "drug", "peak", "suit", "fall", "spit", "cage", "high", "bear", "goat", "trap", "urge", "rush", "tape"};

    for(int i=0; i<words.length; i+=1){
      btreeIndex.addToDictionary(new StringTerm(words[i]));
    }//end-for

//    for(String s:words){
//      System.out.println(bTree.search(new ConcreteKey(s)));
//    }

    btreeIndex.traverseDictionary();
  }
}
