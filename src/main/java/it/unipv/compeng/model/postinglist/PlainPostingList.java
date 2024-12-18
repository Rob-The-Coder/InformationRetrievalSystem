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

  /********************************/
  //Getter/Setter
  /********************************/

  /********************************/
  //Methods
  /********************************/
  @Override
  public void addToPostingList(Term t){

    //Search if same docID was already added to term posting list
//    IVBCIterator iterator=super.variableByteCode.iterator();
//    boolean found=false;
//    while(iterator.hasNext() && !found){
//      if(t.getDocId()==iterator.next()){
//        found=true;
//      }//end-if
//    }//end-while



    //Insert into t.position inside positions and increment frequency
    insertDocId(t);
//    if(!found){
//      //Node non-present
//      super.docIds.add(t.getDocId());
////      super.compressedDocIds.add(t.getDocId());
//    }//end-if
  }

  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException{
    out.writeInt(super.getTermCollectionFrequency());
    for(Integer docId:docIds){
      super.compressedDocIds.add(docId);
    }//end-for
    out.writeObject(compressedDocIds);
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
    super.setTermCollectionFrequency(in.readInt());
    super.compressedDocIds = (VariableByteCode) in.readObject();
  }

  @Override
  public String toString(){
    return super.compressedDocIds.toString();
  }
  /********************************/
}
