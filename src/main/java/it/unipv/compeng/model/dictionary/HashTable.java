package it.unipv.compeng.model.dictionary;

import it.unipv.compeng.model.postinglist.PostingList;
import it.unipv.compeng.model.term.Term;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable extends Dictionary<Term, PostingList> implements Serializable{
  /********************************/
  //NESTED INNER CLASS
  /********************************/
  public class HashCell implements Serializable{
    /********************************/
    //Attributes
    /********************************/
    private final ArrayList<HashTableNode> chain;
    /********************************/
    //Constructor
    /********************************/
    public HashCell(){
      chain=new ArrayList<>();
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
    
    public Term search(Term t){
//      int i=0;
//
//      while(i<chain.size() && !chain.get(i).getTerm().equals(term)){
//        i+=1;
//      }//end-while
//
//      if(i<chain.size()){
//        return chain.get(i).getTerm();
//      }else{
//        return null;
//      }//end-if

      //Binary search term in chain
      int left=0, right=chain.size()-1, i=-1;
      boolean found=false;
      while(left<=right && !found){
        int mid=left+(right-left)/2;

        // Check if x is present at mid
        if(t.compareTo(chain.get(mid).term)==0){
          found=true;
          i=mid;
        }//end-if

        // If x greater, ignore left half
        if(chain.get(mid).getTerm().compareTo(t)<0){
          left=mid + 1;
          // If x is smaller, ignore right half
        }else{
          right=mid - 1;
        }//end-if
      }//end-while

      if(i!=-1){
        return chain.get(i).term;
      }else{
        return null;
      }//end-if
    }
    /********************************/
  }
  /********************************/
  //NESTED INNER CLASS
  /********************************/
  private class HashTableNode implements Serializable{
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
  private HashCell[] cells;
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
    String s=term.toString();
    int p=53;
    int m=100000000+9;
    int hash=0;

    for(int i=0; i<s.length(); i+=1){
      hash+=(int)(s.charAt(i)*Math.pow(p, i));
    }//end-for
    return hash%m;

//    return (int) Math.floor(m*((term.hashCode()*A) % 1));
  }
  @Override
  public void insert(Term term, PostingList postingList){
    int index=computeHash(term);

    while(index>cells.length){
      HashCell[] newCells=new HashCell[cells.length*2];
      System.arraycopy(cells, 0, newCells, 0, cells.length);
      cells=newCells;
    }//end-while

    HashCell cell;
    if((cell=cells[index])==null){
      //Node not present, inserting
      cells[index]=new HashCell();
      cells[index].insert(term, postingList);
    }else{
      //Node present, identifying correct posting list
      int i=0;

      while(i<cell.chain.size() && !cell.chain.get(i).getTerm().equals(term)){
        i+=1;
      }//end-while

      if(i<cell.chain.size()){
        postingList=cell.chain.get(i).postingList;
      }//end-if
    }//end-if
    postingList.addToPostingList(term);
  }

  @Override
  public Term search(Term term) {
    return cells[computeHash(term)].search(term);
  }

  @Override
  public void traverse() {

  }

  @Override
  public void addToPostingList(Term t){
    HashCell hashTableNode=cells[computeHash(t)];
    int i=0;

    while(i<hashTableNode.chain.size() && !hashTableNode.chain.get(i).getTerm().equals(t)){
      i+=1;
    }//end-while

    if(i<hashTableNode.chain.size()){
      hashTableNode.chain.get(i).postingList.addToPostingList(t);
    }else{

    }//end-if
  }

  @Override
  public PostingList getPostingList(Term t){
    HashCell hashTableNode=cells[computeHash(t)];
    int i=0;

    if(hashTableNode!=null){

      for(int j=0; j<hashTableNode.chain.size(); j+=1){
        System.out.println(hashTableNode.chain.get(j).term);
      }//end-for

      while(i<hashTableNode.chain.size() && !hashTableNode.chain.get(i).getTerm().equals(t)){
        i+=1;
      }//end-while

      if(i<hashTableNode.chain.size()){
        return hashTableNode.chain.get(i).postingList;
      }//end-if
    }//end-if
    return null;
  }

  /********************************/
}
