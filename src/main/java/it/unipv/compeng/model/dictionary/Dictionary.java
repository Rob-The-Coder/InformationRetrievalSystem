package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

public abstract class Dictionary<T extends Term, P extends PostingList>{
  public abstract void insert(T term, P postingList);

  public abstract T search(T t);

  public abstract void traverse();
}
