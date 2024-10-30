package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.HashTable;
import it.unipv.compeng.model.postinglist.SkippingPostingList;
import it.unipv.compeng.model.term.Term;

public class SoundedIndex extends Index{
  public SoundedIndex(int size) {
    super(new HashTable(size));
  }

  @Override
  public SkippingPostingList getPostingList() {
    return null;
  }

  @Override
  public void addToPostingList() {
    
  }

  @Override
  public void addToDictionary(Term term) {
    classifyTerm(term);

    super.dictionary.insert(term, new SkippingPostingList());
  }

  @Override
  public Term search(Term term) {
    classifyTerm(term);

    return super.dictionary.search(term);
  }

  @Override
  public void traverseDictionary() {

  }

  private void classifyTerm(Term t){
    //Theoretically term should arrive case folded
    StringBuilder stringBuilder=t.toStringBuilder();

    //Step 1
    char alfa=stringBuilder.charAt(0);

    //Step 2
    for(int i=0; i<stringBuilder.length(); i+=1){
      char currChar=stringBuilder.charAt(i);
      if(currChar=='a' || currChar=='e' || currChar=='i' || currChar=='o' || currChar=='u' || currChar=='h' || currChar=='w' || currChar=='y'){
        stringBuilder.setCharAt(i, '0');
      }else if(currChar=='b' || currChar=='f' || currChar=='p' || currChar=='v'){
        stringBuilder.setCharAt(i, '1');
      }else if(currChar=='c' || currChar=='g' || currChar=='j' || currChar=='k' || currChar=='q' || currChar=='s' || currChar=='x' || currChar=='z'){
        stringBuilder.setCharAt(i, '2');
      }else if(currChar=='d' || currChar=='t'){
        stringBuilder.setCharAt(i, '3');
      }else if(currChar=='l'){
        stringBuilder.setCharAt(i, '4');
      }else if(currChar=='m' || currChar=='n'){
        stringBuilder.setCharAt(i, '5');
      }else if(currChar=='r'){
        stringBuilder.setCharAt(i, '6');
      }//end-if
    }//end-for

    //Step 3
    for(int i=1; i<stringBuilder.length(); i+=1){
      if(stringBuilder.charAt(i)==stringBuilder.charAt(i-1)){
        stringBuilder.deleteCharAt(i);
      }//end-if
    }//end-for

    //Step 4
    for(int i=0; i<stringBuilder.length(); i+=1){
      if(stringBuilder.charAt(i)=='0'){
        stringBuilder.deleteCharAt(i);
      }//end-if
    }//end-for

    //Step 5
    stringBuilder.insert(0, alfa);

    //Step 6
    if(stringBuilder.length()>4){
      stringBuilder.delete(4, stringBuilder.length());
    }//end-if

    t.setString(stringBuilder.toString());
  }
}
