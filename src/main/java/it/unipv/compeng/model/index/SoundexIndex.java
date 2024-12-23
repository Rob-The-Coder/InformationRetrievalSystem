package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.PrefixBTree;
import it.unipv.compeng.model.postinglist.PlainPostingList;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.preprocessing.PlainStringPreprocessor;
import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.Logger;

/***********************************************************/
//CONCRETE IMPLEMENTATION OF INDEX USING SOUNDEX CLASSIFICATION
/***********************************************************/
public class SoundexIndex extends Index{
  /********************************/
  //Constructors
  /********************************/
  public SoundexIndex(){
    super(new PrefixBTree(5), new PlainStringPreprocessor());
  }
  /********************************/
  //Getter/Setter
  /********************************/
  @Override
  public PostingList getPostingList(Term t) {
    if(!t.toStringBuilder().isEmpty()){
      classifyTerm(t);

      return super.dictionary.getPostingList(t);
    }//end-if
    return null;
  }

  @Override
  public PostingList getPostingList(String s){
    Term equivalentTerm=getCorrectTerm(s);

    if(equivalentTerm!=null){
      classifyTerm(equivalentTerm);
      Logger.getInstance().log(equivalentTerm.toString());
      return getPostingList(equivalentTerm);
    }else{
      return null;
    }//end-if
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public void addToDictionary(Term term) {
    if(!term.toStringBuilder().isEmpty()){
      classifyTerm(term);

      super.dictionary.insert(term, new PlainPostingList(0.5));
    }//end-if
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
    int i=1;
    while(i<stringBuilder.length()){
      if(stringBuilder.charAt(i)==stringBuilder.charAt(i-1)){
        stringBuilder.deleteCharAt(i);
      }else{
        i+=1;
      }//end-if
    }//end-while

    //Step 4
    i=0;
    while(i<stringBuilder.length()){
      if(stringBuilder.charAt(i)=='0'){
        stringBuilder.deleteCharAt(i);
      }else{
        i+=1;
      }//end-if
    }//end-while

    //Step 5
    stringBuilder.insert(0, alfa);

    //Step 6
    if(stringBuilder.length()>4){
      stringBuilder.delete(4, stringBuilder.length());
    }//end-if

    t.setString(stringBuilder.toString());
  }
  /********************************/
}
