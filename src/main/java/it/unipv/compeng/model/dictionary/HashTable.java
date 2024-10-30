package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

import java.util.LinkedList;

public class HashTable extends Dictionary<Term, PostingList>{
  /********************************/
  //NESTED INNER CLASS
  /********************************/
  public class HashCell{
    /********************************/
    //Attributes
    /********************************/
    private final LinkedList<HashTableNode> chain;
    /********************************/
    //Constructor
    /********************************/
    public HashCell(){
      chain=new LinkedList<>();
    }
    /********************************/
    //Getter/Setter
    /********************************/

    /********************************/
    //Methods
    /********************************/
    public void insert(Term term, PostingList postingList){
      chain.add(new HashTableNode(term, postingList));
    }
    
    public Term search(Term term){
      int i=0;

      while(i<chain.size() && !chain.get(i).getTerm().equals(term)){
        i+=1;
      }//end-while
      
      if(i<chain.size()){
        return chain.get(i).getTerm();
      }else{
        return null;
      }//end-if
    }
    /********************************/
  }
  /********************************/
  //NESTED INNER CLASS
  /********************************/
  private class HashTableNode{
    /********************************/
    //Attributes
    /********************************/
    private final Term term;
    private final PostingList postingList;
    /********************************/
    //Constructor
    /********************************/
    public HashTableNode(Term term, PostingList postingList){
      this.term=term;
      this.postingList=postingList;
    }
    /********************************/
    //Getter/Setter
    /********************************/
    public Term getTerm() {
      return term;
    }
    /********************************/
    //Methods
    /********************************/
    
    /********************************/
  }
  /********************************/
  //Attributes
  /********************************/
  private final HashCell[] cells;
  private final double A=(Math.sqrt(5)-1)/2;
  private final int p=15;
  private final double m;
  /********************************/
  //Constructor
  /********************************/
  public HashTable(int size){
    //m=Math.pow(2, p);
    m=size;
    cells=new HashCell[(int) (2*m)];
  }
  /********************************/
  //Getter/Setter
  /********************************/
  
  /********************************/
  //Methods
  /********************************/
  private int computeHash(Term term){
    return (int) Math.floor(m*((term.hashCode()*A) % 1));
  }
  @Override
  public void insert(Term term, PostingList postingList) {
    int index=computeHash(term);

    if(cells[index]==null){
      cells[index]=new HashCell();
    }//end-if
    cells[index].insert(term, postingList);
  }

  @Override
  public Term search(Term term) {
    return cells[computeHash(term)].search(term);
  }

  @Override
  public void traverse() {

  }
  /********************************/
}
