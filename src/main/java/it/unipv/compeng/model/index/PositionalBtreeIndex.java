package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.*;
import it.unipv.compeng.model.postinglist.PositionalPostingList;
import it.unipv.compeng.model.term.Term;

public class PositionalBtreeIndex extends Index<BTree, PositionalPostingList>{

  public PositionalBtreeIndex(int t){
    super(new BTree(t));
  }

  @Override
  public void addToDictionary(Term t){
    super.dictionary.insert(t);
  }

  @Override
  public void traverseDictionary(){
    super.dictionary.traverse();
  }
}
