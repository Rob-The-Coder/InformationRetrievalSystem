package it.unipv.compeng.model.index;

import it.unipv.compeng.model.dictionary.PrefixBTree;
import it.unipv.compeng.model.postinglist.PlainPostingList;
import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.preprocessing.PorterStringPreprocessor;
import it.unipv.compeng.model.term.Term;

/***********************************************************/
//CONCRETE IMPLEMENTATION OF AN INDEX USING THE PREFIX BTREE AS
//DICTIONARY
/***********************************************************/
public class BTreeIndex extends Index{
  /********************************/
  //Constructors
  /********************************/
  public BTreeIndex(){
    super(new PrefixBTree(5), new PorterStringPreprocessor());
  }
  public BTreeIndex(int t){
    super(new PrefixBTree(t), new PorterStringPreprocessor());
  }
  /********************************/
  //Getter/Setter
  /********************************/
  @Override
  public PostingList getPostingList(Term t){
    return super.dictionary.getPostingList(t);
  }

  @Override
  public PostingList getPostingList(String s) {
    Term t=getCorrectTerm(s);
    if(t!=null){
      return getPostingList(t);
    }else{
      return null;
    }//end-if
  }
  /********************************/
  //Methods
  /********************************/
  @Override
  public void addToDictionary(Term t){
    super.dictionary.insert(t, new PlainPostingList(3));
  }
  /********************************/
}
