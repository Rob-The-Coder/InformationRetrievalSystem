package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

import java.io.Serializable;

/***********************************************************/
//ABSTRACT CLASS Dictionary
/***********************************************************/
public abstract class Dictionary<T extends Term, P extends PostingList> implements Serializable{
  /********************************/
  //Methods
  /********************************/
  public abstract void insert(T term, P postingList);
  public abstract T search(T term);
  public abstract void traverse();
  public abstract P getPostingList(Term t);
  /********************************/
}
