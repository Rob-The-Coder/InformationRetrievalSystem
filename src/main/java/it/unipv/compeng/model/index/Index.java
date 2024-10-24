package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.Dictionary;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

abstract class Index<D extends Dictionary<Term, PostingList>, P extends PostingList>{
  protected final D dictionary;

  public Index(D dictionary) {
    this.dictionary = dictionary;
  }

  public abstract P getPostingList();

  public abstract void addToPostingList();

  public abstract void addToDictionary(Term t);
  public abstract Term search(Term term);

  public abstract void traverseDictionary();

  public D getDictionary(){
    return dictionary;
  }
}
