package it.unipv.compeng.model.postinglist;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.VariableByteCode;
import it.unipv.compeng.model.utility.iterator.VBCIterator.IVBCIterator;

import java.util.ArrayList;
public abstract class PostingList{
  /********************************/
  //Attributes
  /********************************/
  protected int termCollectionFrequency;
  protected final transient ArrayList<Integer> docIds;
  protected VariableByteCode compressedDocIds;
  /********************************/
  //Constructor
  /********************************/
  public PostingList(){
    this.termCollectionFrequency=0;
    this.docIds = new ArrayList<>();
    this.compressedDocIds=new VariableByteCode();
  }
  /********************************/
  //Getter/Setter
  /********************************/
  public int getDocId(int index){
    return compressedDocIds.getValue(index);
  }
  /********************************/
  //Methods
  /********************************/
  public int searchDocId(Term t){
    //Binary search if a node is present
    int left=0, right=docIds.size()-1, i=-1;
    boolean found=false;
    while(left<=right && !found){
      int mid=left+(right-left)/2;

      // Check if x is present at mid
      if(t.getDocId()==docIds.get(mid)){
        found=true;
        i=mid;
      }//end-if
      // If x greater, ignore left half
      if(docIds.get(mid)<t.getDocId()){
        left=mid + 1;
        // If x is smaller, ignore right half
      }else{
        right=mid - 1;
      }//end-if
    }//end-while

    return i;
  }

  public abstract void addToPostingList(Term t);

  public int[] toArray(){
    return compressedDocIds.toArray();
  }
  /********************************/
}
