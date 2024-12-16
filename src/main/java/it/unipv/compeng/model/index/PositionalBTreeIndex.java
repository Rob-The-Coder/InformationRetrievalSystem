package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.*;
import it.unipv.compeng.model.postinglist.PositionalPostingList;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;
import it.unipv.compeng.model.term.Term;

public class PositionalBTreeIndex extends Index{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructor
  /********************************/
  public PositionalBTreeIndex(int t){
    super(new PrefixBTree(t), new PorterStringPreprocessor(null));
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public PositionalPostingList getPostingList(Term t) {
    return (PositionalPostingList)super.dictionary.getPostingList(t);
  }

  @Override
  public PostingList getPostingList(String s) {
    System.out.println(getCorrectTerm(s));
    return getPostingList(getCorrectTerm(s));
  }

  @Override
  public void addToDictionary(Term t) {
    super.dictionary.insert(t, new PositionalPostingList());
  }

  /********************************/
}
