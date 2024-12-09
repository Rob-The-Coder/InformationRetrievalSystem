package it.unipv.compeng.model.postinglist;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.VariableByteCode;

import java.util.ArrayList;
public abstract class PostingList{
  /********************************/
  //Attributes
  /********************************/
  private int termCollectionFrequency;
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
  public int getTermCollectionFrequency(){
    return termCollectionFrequency;
  }

  public void setTermCollectionFrequency(int termCollectionFrequency){
    this.termCollectionFrequency=termCollectionFrequency;
  }

  /********************************/
  //Methods
  /********************************/
  public int insertDocId(Term t){
    this.termCollectionFrequency+=1;
    int pos=binarySearchDocId(t.getDocId());

    if(pos>=this.docIds.size() || this.docIds.get(pos)!=t.getDocId()){
      this.docIds.add(pos,  t.getDocId());
    }//end-if

    return pos;
  }

  private int binarySearchDocId(int target){
    // Initialize double closed interval [0, n-1]
    int left=0, right=docIds.size()-1;

    while(left<=right){
      int mid=left+(right-left)/2;

      // Check if x is present at mid
      if(target==docIds.get(mid)){
        return mid;
      }//end-if

      // If x greater, ignore left half
      if(docIds.get(mid)<target){
        left=mid + 1;
        // If x is smaller, ignore right half
      }else{
        right=mid - 1;
      }//end-if
    }//end-while

    return left;
  }

  public abstract void addToPostingList(Term t);

  public int[] toArray(){
    return compressedDocIds.toArray();
  }
  /********************************/
}
