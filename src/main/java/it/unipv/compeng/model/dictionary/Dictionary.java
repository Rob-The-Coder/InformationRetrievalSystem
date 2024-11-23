package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

import java.io.Serializable;

public abstract class Dictionary<T extends Term, P extends PostingList> implements Serializable{
  public abstract void insert(T term, P postingList);
  public abstract T search(T term);
  public abstract void traverse();
  public abstract void addToPostingList(Term t);
  public abstract PostingList getPostingList(Term t);
}
