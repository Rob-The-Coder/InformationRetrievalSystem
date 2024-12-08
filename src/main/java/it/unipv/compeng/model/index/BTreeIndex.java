package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.PrefixBTree;
import it.unipv.compeng.model.postinglist.PlainPostingList;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

public class BTreeIndex extends Index{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public BTreeIndex(){super(new PrefixBTree(2));}
  public BTreeIndex(int t){
    super(new PrefixBTree(t));
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public PostingList getPostingList(Term t){
    return super.dictionary.getPostingList(t);
  }

  @Override
  public void addToDictionary(Term t){
    super.dictionary.insert(t, new PlainPostingList());
  }
  /********************************/
}
