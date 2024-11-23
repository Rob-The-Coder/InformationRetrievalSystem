package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.Dictionary;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

import java.io.Serializable;

public abstract class Index implements Serializable{
  /********************************/
  //Attributes
  protected Dictionary<Term, PostingList> dictionary;
  /********************************/
  //Constructor
  /********************************/
  public Index(Dictionary<Term, PostingList> dictionary) {
    this.dictionary = dictionary;
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  public abstract PostingList getPostingList(Term t);

  public abstract void addToDictionary(Term t);

  public void traverseDictionary(){
    this.dictionary.traverse();
  }

//  public Dictionary<? extends Term, ? extends PostingList> getDictionary(){
//    return dictionary;
//  }
  /********************************/
}
