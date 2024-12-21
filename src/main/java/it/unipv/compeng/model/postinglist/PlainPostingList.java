package it.unipv.compeng.model.postinglist;

import it.unipv.compeng.model.term.Term;
import it.unipv.compeng.model.utility.VariableByteCode;

import java.io.*;

public class PlainPostingList extends PostingList implements Serializable{
  /********************************/
  //Attributes
  /********************************/

  /********************************/
  //Constructors
  /********************************/
  public PlainPostingList(){
    this((double)1 /2);
  }
  public PlainPostingList(double scoreWeight) {
    this.scoreWeight = scoreWeight;
  }
  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public void addToPostingList(Term t){
    //Insert into t.position inside positions and increment frequency
    insertDocId(t);
  }

  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException{
    for(Node node:super.nodes){
      super.compressedDocIds.add(node.getDocId());
      super.compressedTermDocCounts.add(node.getTermDocCount().intValue());
    }//end-for

    out.writeObject(compressedDocIds);
    out.writeObject(compressedTermDocCounts);
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
    super.compressedDocIds = (VariableByteCode) in.readObject();
    super.compressedTermDocCounts= (VariableByteCode) in.readObject();
  }

  @Override
  public String toString(){
    return super.compressedDocIds.toString();
  }
  /********************************/
}
