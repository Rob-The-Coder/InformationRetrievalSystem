package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

public abstract class Dictionary<T extends Term, P extends PostingList>{
  //public abstract void insert();

  public abstract void insert(T term);

  public abstract T search();

  public abstract void traverse();
}
