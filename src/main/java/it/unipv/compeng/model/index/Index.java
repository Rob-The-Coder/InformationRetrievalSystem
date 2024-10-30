package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.Dictionary;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

abstract class Index{
  protected Dictionary<Term, PostingList> dictionary;

  public Index(Dictionary<Term, PostingList> dictionary) {
    this.dictionary = dictionary;
  }

  public abstract PostingList getPostingList();

  public abstract void addToPostingList();

  public abstract void addToDictionary(Term t);
  public abstract Term search(Term term);

  public abstract void traverseDictionary();

  public Dictionary<? extends Term, ? extends PostingList> getDictionary(){
    return dictionary;
  }
}
