package it.unipv.compeng.model.postinglist;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.VariableByteCode;

import java.io.*;
import java.util.ArrayList;

public class PositionalPostingList extends PostingList implements Serializable {
  /********************************/
  //NESTED CLASS
  /********************************/
  private class Node implements Serializable{
    /********************************/
    //Attributes
    /********************************/
    private int termDocFrequency;
    private VariableByteCode compressedPositions;
    private transient final ArrayList<Integer> positions;
    /********************************/
    //Constructor
    /********************************/
    public Node(){
      this.termDocFrequency=1;
      this.positions=new ArrayList<>();
      this.compressedPositions=new VariableByteCode();
    }
    /********************************/
    //Getter/Setter
    /********************************/

    /********************************/
    //Methods
    /********************************/
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException{
      out.writeInt(this.termDocFrequency);
      for(Integer docId:positions){
        this.compressedPositions.add(docId);
      }//end-for
      out.writeObject(this.compressedPositions);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
      this.termDocFrequency=in.readInt();
      this.compressedPositions = (VariableByteCode) in.readObject();
      }
    /********************************/
  }
  /********************************/
  //Attributes
  /********************************/
  private ArrayList<Node> postings;
  /********************************/
  //Constructor
  /********************************/
  public PositionalPostingList(){
    super();
    postings=new ArrayList<>();
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException{
    out.writeInt(super.termCollectionFrequency);
    for(Integer docId:docIds){
      super.compressedDocIds.add(docId);
    }//end-for
    out.writeObject(super.compressedDocIds);
    out.writeObject(this.postings);
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
    super.termCollectionFrequency=in.readInt();
    super.compressedDocIds = (VariableByteCode) in.readObject();
    this.postings = (ArrayList<Node>)in.readObject();
  }

  @Override
  public void addToPostingList(Term t){
    super.termCollectionFrequency+=1;

    Node node=new Node();

    int i;
    boolean found=(i=searchDocId(t)) != -1;

    //Insert into t.position inside positions and increment frequency
    if(!found){
      //Node non-present
      super.docIds.add(t.getDocId());
      node.positions.add(t.getTermPositionInDocument());
      postings.add(node);
    }else{
      //Node present
      postings.get(i).termDocFrequency+=1;
      postings.get(i).positions.add(t.getTermPositionInDocument());
    }//end-if

//    //Search node with corresponding docId
//    int i=0;
//    while(i<postings.size() && postings.get(i).docId != docId){
//      i+=1;
//    }//end-while
//
//    //Insert into t.position inside positions and increment frequency
//    if(i == postings.size()){
//      //Node non-present
//      postings.add(new Node(docId));
//    }else{
//      //Node present
//      postings.get(i).termDocFrequency+=1;
//    }//end-if
//    postings.get(i).positions.add(t.getTermPositionInDocument());
  }
  /********************************/
}
