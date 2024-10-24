package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.*;
import it.unipv.compeng.model.postinglist.PositionalPostingList;
import it.unipv.compeng.model.term.Term;

public class PositionalBtreeIndex extends Index<PrefixBTree, PositionalPostingList>{

  public PositionalBtreeIndex(int t){
    super(new PrefixBTree(t));
  }

  @Override
  public PositionalPostingList getPostingList() {
    return null;
  }

  @Override
  public void addToPostingList() {

  }

  @Override
  public void addToDictionary(Term t) {
    super.dictionary.insert(t, new PositionalPostingList());
  }

  @Override
  public Term search(Term term) {
    return super.dictionary.search(term);
  }

  @Override
  public void traverseDictionary(){
    super.dictionary.traverse();
  }
}
