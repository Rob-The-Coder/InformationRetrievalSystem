package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.HashTable;
import it.unipv.compeng.model.postinglist.SkippingPostingList;
import it.unipv.compeng.model.term.Term;

public class SoundedIndex extends Index<HashTable, SkippingPostingList>{

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
  public void addToDictionary(Term t) {
    super.dictionary.insert(t, new SkippingPostingList());
  }

  @Override
  public Term search(Term term) {
    return super.dictionary.search(term);
  }

  @Override
  public void traverseDictionary() {

  }
}
